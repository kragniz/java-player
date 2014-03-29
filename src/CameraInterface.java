/*
 *  Player Java Client 3 - CameraInterface.java
 *  Copyright (C) 2002-2006 Radu Bogdan Rusu, Maxim Batalin
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
 * $Id: CameraInterface.java 125 2011-03-24 02:24:05Z corot $
 *
 */
package javaclient3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaclient3.structures.PlayerMsgHdr;
import javaclient3.structures.camera.PlayerCameraData;
import javaclient3.xdr.OncRpcException;
import javaclient3.xdr.XdrBufferDecodingStream;

/**
 * The camera interface is used to see what the camera sees. It is intended
 * primarily for server-side (i.e., driver-to-driver) data transfers, rather
 * than server-to-client transfers. Image data can be in may formats (see
 * PlayerConstants),but is always packed (i.e., pixel rows are byte-aligned).
 * <br><br>
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 * TODO: implement PLAYER_CAMERA_REQ_SET_SOURCE and PLAYER_CAMERA_REQ_GET_SOURCE
 */
public class CameraInterface extends PlayerDevice {

    private static final boolean isDebugging = PlayerClient.isDebugging;

    // Logging support
    private Logger logger = Logger.getLogger (GripperInterface.class.getName ());

    private PlayerCameraData pcdata;
    private boolean          readyPcdata = false;


    /**
     * Constructor for CameraInterface.
     * @param pc a reference to the PlayerClient object
     */
    public CameraInterface (PlayerClient pc) { super(pc); }

    /**
     * Read the camera data.
     * <br><br>
     * See the player_camera_data structure from player.h
     */
    public synchronized void readData (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype ()) {
                case PLAYER_CAMERA_DATA_STATE: {
                    this.timestamp = header.getTimestamp();

                    // Buffer for reading width, height, bpp, format, fdiv, compression, image_count
                    byte[] buffer = new byte[28];
                    // Read width, height, bpp, format, fdiv, compression, image_count
                    is.readFully (buffer, 0, 28);

                    pcdata = new PlayerCameraData ();

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();

                    pcdata.setWidth       (xdr.xdrDecodeInt ());
                    pcdata.setHeight      (xdr.xdrDecodeInt ());
                    pcdata.setBpp         (xdr.xdrDecodeInt ());
                    pcdata.setFormat      (xdr.xdrDecodeInt ());
                    pcdata.setFdiv        (xdr.xdrDecodeInt ());
                    pcdata.setCompression (xdr.xdrDecodeInt ());
                    int imageCount = xdr.xdrDecodeInt ();
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
                    buffer = new byte[header.getSize () - 32];

                    is.readFully (buffer, 0, header.getSize () - 32);
                    pcdata.setImage (buffer);

                    readyPcdata = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new PlayerException
                ("[Camera] : Error reading payload: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Camera] : Error while XDR-decoding payload: " +
                        e.toString(), e);
        }
    }

    /**
     * Request/reply: Get current image frame.
     * <br><br>
     * The current image frame can be "manually" queried by
     * sending a null PLAYER_CAMERA_REQ_GET_IMAGE request.
     * <br><br>
     * See the player_camera_data structure from player.h
     */
    public void getImage () {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_CAMERA_REQ_GET_IMAGE, 0);
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Camera] : Couldn't send PLAYER_CAMERA_REQ_GET_IMAGE " +
                        "command: " + e.toString(), e);
        }
    }

    /**
     * Handle acknowledgement response messages
     * @param header Player header
     */
    public void handleResponse (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype ()) {
                case PLAYER_CAMERA_REQ_GET_IMAGE: {

                    this.timestamp = header.getTimestamp();

                    // Buffer for reading width, height, bpp, format, fdiv, compression, image_count
                    byte[] buffer = new byte[28];
                    // Read width, height, bpp, format, fdiv, compression, image_count
                    is.readFully (buffer, 0, 28);

                    pcdata = new PlayerCameraData ();

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();

                    pcdata.setWidth       (xdr.xdrDecodeInt ());
                    pcdata.setHeight      (xdr.xdrDecodeInt ());
                    pcdata.setBpp         (xdr.xdrDecodeInt ());
                    pcdata.setFormat      (xdr.xdrDecodeInt ());
                    pcdata.setFdiv        (xdr.xdrDecodeInt ());
                    pcdata.setCompression (xdr.xdrDecodeInt ());
                    int imageCount = xdr.xdrDecodeInt ();
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
                    buffer = new byte[header.getSize () - 32];

                    is.readFully (buffer, 0, header.getSize () - 32);
                    pcdata.setImage (buffer);

                    readyPcdata = true;
                    break;
                }
                default:{
                    if (isDebugging)
                        logger.log (Level.FINEST, "[Camera]Debug] : " +
                                "Unexpected response " + header.getSubtype () +
                                " of size = " + header.getSize ());
                    break;
                }
            }
        } catch (IOException e) {
            throw new PlayerException
                ("[Camera] : Error reading payload: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Camera] : Error while XDR-decoding payload: " +
                        e.toString(), e);
        }
    }

    /**
     * Get the Camera data.
     * @return an object of type PlayerCameraData containing the requested data
     */
    public PlayerCameraData getData () { return this.pcdata; }

    /**
     * Check if data is available.
     * @return true if ready, false if not ready
     */
    public boolean isDataReady () {
        if (readyPcdata) {
            readyPcdata = false;
            return true;
        }
        return false;
    }
}
