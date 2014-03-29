/*
 *  Player Java Client 3 - StereoInterface.java
 *  Copyright (C) 2011 Jorge Santos Simon
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * $Id: StereoInterface.java 167 2011-11-16 04:31:29Z corot $
 *
 */
package javaclient3;

import java.io.IOException;
import java.util.logging.Logger;

import javaclient3.structures.PlayerMsgHdr;
import javaclient3.structures.camera.PlayerCameraData;
import javaclient3.structures.stereo.PlayerStereoData;
import javaclient3.structures.stereo.PlayerPointCloud3dStereoElement;
import javaclient3.xdr.OncRpcException;
import javaclient3.xdr.XdrBufferDecodingStream;

/**
 * Stereo imagery (left-right channels, disparity and a 3-D stereo point cloud).
 * The stereo interface is used to get synchronized access to the data provided
 * by a stereo camera, namely the left and right image channels, the disparity
 * image and the 3-D stereo point cloud. Image data can be in many formats (see
 * the @ref interface_camera interface).
 * <br><br>
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class StereoInterface extends PlayerDevice {

    private static final boolean isDebugging = PlayerClient.isDebugging;

    // Logging support
    private Logger logger = Logger.getLogger (GripperInterface.class.getName ());

    private PlayerStereoData psdata;
    private boolean          readyPsdata = false;


    /**
     * Constructor for StereoInterface.
     * @param pc A reference to the PlayerClient object.
     */
    public StereoInterface (PlayerClient pc) { super(pc); }

    /**
     * Read the stereo data.
     * @param header Player message header.
     * <br><br>
     * See the player_stereo_data structure from player.h.
     */
    public synchronized void readData (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype ()) {
                case PLAYER_STEREO_DATA_STATE: {
                    this.timestamp = header.getTimestamp();

                    psdata = new PlayerStereoData ();
                    psdata.setLeftChannel  (readCameraData ());
                    psdata.setRightChannel (readCameraData ());
                    psdata.setDisparity    (readCameraData ());

                    // Buffer for points_count, array_count
                    byte[] buffer = new byte[8];
                    // Read points_count, array_count
                    is.readFully (buffer, 0, 8);

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();
                    int pointsCount = xdr.xdrDecodeInt (); // points_count
                    xdr.endDecoding ();
                    xdr.close ();

                    // Buffer for reading point cloud and stereo mode (points_count*24 + 4)
                    buffer = new byte[pointsCount*24 + 4];
                    // Read points position and color and setero mode
                    is.readFully (buffer, 0, pointsCount*24 + 4);

                    PlayerPointCloud3dStereoElement[] points =
                        new PlayerPointCloud3dStereoElement[pointsCount];
                    for (int i = 0; i < pointsCount; i++) {
                        points[i] = new PlayerPointCloud3dStereoElement ();

                        xdr = new XdrBufferDecodingStream (buffer);
                        xdr.beginDecoding ();
                        points[i].setPx    (xdr.xdrDecodeFloat ());
                        points[i].setPy    (xdr.xdrDecodeFloat ());
                        points[i].setPz    (xdr.xdrDecodeFloat ());
                        points[i].setRed   (xdr.xdrDecodeByte  ());
                        points[i].setGreen (xdr.xdrDecodeByte  ());
                        points[i].setBlue  (xdr.xdrDecodeByte  ());
                        xdr.endDecoding ();
                        xdr.close ();
                    }

                    psdata.setMode (xdr.xdrDecodeInt ());

                    psdata.setPoints (points);

                    readyPsdata = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new PlayerException
                ("[Stereo] : Error reading payload: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Stereo] : Error while XDR-decoding payload: " +
                        e.toString(), e);
        }
    }

    /**
     * Get the stereo data.
     * @return An object of type PlayerStereoData containing the requested data.
     */
    public PlayerStereoData getData () { return this.psdata; }

    /**
     * Check if data is available.
     * @return True if ready, false if not ready.
     */
    public boolean isDataReady () {
        if (readyPsdata) {
            readyPsdata = false;
            return true;
        }
        return false;
    }

    /**
     * Read an image (PlayerCameraData structure) from the server.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    private PlayerCameraData readCameraData () throws OncRpcException, IOException {

        // Buffer for reading width, height, bpp, format, fdiv, compression, image_count
        byte[] buffer = new byte[28];
        // Read width, height, bpp, format, fdiv, compression, image_count
        is.readFully (buffer, 0, 28);

        PlayerCameraData image = new PlayerCameraData ();

        // Begin decoding the XDR buffer
        XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
        xdr.beginDecoding ();

        image.setWidth       (xdr.xdrDecodeInt ());
        image.setHeight      (xdr.xdrDecodeInt ());
        image.setBpp         (xdr.xdrDecodeInt ());
        image.setFormat      (xdr.xdrDecodeInt ());
        image.setFdiv        (xdr.xdrDecodeInt ());
        image.setCompression (xdr.xdrDecodeInt ());
        int imageCount      = xdr.xdrDecodeInt ();
        xdr.endDecoding   ();
        xdr.close ();

        // Read the length of the image array as reported by
        // the XDR encoded byte stream (xdr_bytes) - note that
        // an XDR encoded byte stream is simply the byte stream
        // itself prepended with its length. Check that
        // this matches up with imageCount. We could also have
        // used xdrDecodeByteVector for this but if we did we
        // wouldn't be able to do this check (that plus we
        // would have needed to allocate a suitably sized
        // buffer to create the xdr object anyway).
        is.readFully(buffer, 0, 4);
        xdr = new XdrBufferDecodingStream(buffer);
        xdr.beginDecoding();
        int imageCountFromXDR = xdr.xdrDecodeInt();
        xdr.endDecoding();
        xdr.close();

        if (imageCountFromXDR != imageCount)
        {
            throw new PlayerException
                ("[Camera] : Error reading image bytestream, header reports a length of " +
                 imageCount + " bytes, XDR reports a length of " + imageCountFromXDR + " bytes.");
        }

        // Buffer for reading image
        buffer = new byte[imageCount];

        is.readFully (buffer, 0, imageCount);
        image.setImage (buffer);

        return image;
    }
}
