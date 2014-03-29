/*
 *  Player Java Client 3 - LimbInterface.java
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
 * $Id: LimbInterface.java 125 2011-03-24 02:24:05Z corot $
 *
 */
package javaclient3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaclient3.structures.PlayerMsgHdr;
import javaclient3.structures.PlayerPoint3d;
import javaclient3.structures.limb.PlayerLimbData;
import javaclient3.structures.limb.PlayerLimbGeomReq;
import javaclient3.xdr.OncRpcException;
import javaclient3.xdr.XdrBufferDecodingStream;
import javaclient3.xdr.XdrBufferEncodingStream;

/**
 * The limb interface provides access to a multi-jointed limb.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class LimbInterface extends PlayerDevice {

    private static final boolean isDebugging = PlayerClient.isDebugging;

    // Logging support
    private Logger logger = Logger.getLogger (LimbInterface.class.getName ());

    private PlayerLimbData    pldata;
    private boolean           readyPldata = false;
    private PlayerLimbGeomReq plgeom;
    private boolean           readyPlgeom = false;

    /**
     * Constructor for LimbInterface.
     * @param pc a reference to the PlayerClient object
     */
    public LimbInterface (PlayerClient pc) { super (pc); }

    /**
     * Read the Limb data.
     */
    public synchronized void readData (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype ()) {
                case PLAYER_LIMB_DATA_STATE: {
                    this.timestamp = header.getTimestamp();

                    // Buffer for reading position, approach, orientation, state
                    byte[] buffer = new byte[76];
                    // Read position, approach, orientation, state (24 + 24 + 24 + 4)
                    is.readFully (buffer, 0, 76);

                    pldata = new PlayerLimbData ();

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();
                    PlayerPoint3d position = new PlayerPoint3d ();
                    position.setPx (xdr.xdrDecodeDouble ());
                    position.setPy (xdr.xdrDecodeDouble ());
                    position.setPz (xdr.xdrDecodeDouble ());

                    PlayerPoint3d approach = new PlayerPoint3d ();
                    approach.setPx (xdr.xdrDecodeDouble ());
                    approach.setPy (xdr.xdrDecodeDouble ());
                    approach.setPz (xdr.xdrDecodeDouble ());

                    PlayerPoint3d orientation = new PlayerPoint3d ();
                    orientation.setPx (xdr.xdrDecodeDouble ());
                    orientation.setPy (xdr.xdrDecodeDouble ());
                    orientation.setPz (xdr.xdrDecodeDouble ());

                    byte state = xdr.xdrDecodeByte ();
                    xdr.endDecoding   ();
                    xdr.close ();

                    pldata.setPosition (position);
                    pldata.setPosition (approach);
                    pldata.setPosition (orientation);
                    pldata.setState    (state);

                    readyPldata = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new PlayerException
                ("[Limb] : Error reading payload: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Limb] : Error while XDR-decoding payload: " +
                        e.toString(), e);
        }
    }

    /**
     * Command: home (PLAYER_LIMB_HOME_CMD)
     * <br><br>
     * Tells the end effector to return to its home position.
     */
    public void homeCmd () {
        try {
            sendHeader (PLAYER_MSGTYPE_CMD, PLAYER_LIMB_CMD_HOME, 0);
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Limb] : Couldn't send homing command: " +
                        e.toString(), e);
        }
    }

    /**
     * Command: stop (PLAYER_LIMB_STOP_CMD)
     * <br><br>
     * Tells the limb to stop moving immediatly.
     */
    public void stopCmd () {
        try {
            sendHeader (PLAYER_MSGTYPE_CMD, PLAYER_LIMB_CMD_STOP, 0);
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Limb] : Couldn't send stop command: " +
                        e.toString(), e);
        }
    }

    /**
     * Command: Set end effector pose (PLAYER_LIMB_SETPOSE_CMD).
     * <br><br>
     * Provides a fully-described pose (position, normal vector and
     * orientation vector) for the end effector to move to.
     * @param position Position of the end effector
     * @param approach Approach vector
     * @param orientation Orientation vector
     */
    public void setPose (PlayerPoint3d position,
            PlayerPoint3d approach, PlayerPoint3d orientation) {
        try {
            sendHeader (PLAYER_MSGTYPE_CMD, PLAYER_LIMB_CMD_SETPOSE, 72);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (72);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeDouble (position.getPx ());
            xdr.xdrEncodeDouble (position.getPy ());
            xdr.xdrEncodeDouble (position.getPz ());
            xdr.xdrEncodeDouble (approach.getPx ());
            xdr.xdrEncodeDouble (approach.getPy ());
            xdr.xdrEncodeDouble (approach.getPz ());
            xdr.xdrEncodeDouble (orientation.getPx ());
            xdr.xdrEncodeDouble (orientation.getPy ());
            xdr.xdrEncodeDouble (orientation.getPz ());
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Limb] : Couldn't send SETPOSE command: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Limb] : Error while XDR-encoding SETPOSE command: " +
                        e.toString(), e);
        }

    }

    /**
     * Command: Set end effector position (PLAYER_LIMB_SETPOSITION_CMD).
     * <br><br>
     * Set the position of the end effector without worrying about a specific
     * orientation.
     * @param position Position of the end effector
     */
    public void setPosition (PlayerPoint3d position) {
        try {
            sendHeader (PLAYER_MSGTYPE_CMD, PLAYER_LIMB_CMD_SETPOSITION, 24);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (24);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeDouble (position.getPx ());
            xdr.xdrEncodeDouble (position.getPy ());
            xdr.xdrEncodeDouble (position.getPz ());
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Limb] : Couldn't send SETPOSITION command: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Limb] : Error while XDR-encoding SETPOSITION command: " +
                        e.toString(), e);
        }

    }

    /**
     * Command: Vector move the end effector (PLAYER_LIMB_VECMOVE_CMD).
     * <br><br>
     * Set the position of the end effector without worrying about a specific
     * orientation.
     * @param direction Direction vector to move in
     * @param length Distance to move
     */
    public void vectorMove (PlayerPoint3d direction, float length) {
        try {
            sendHeader (PLAYER_MSGTYPE_CMD, PLAYER_LIMB_CMD_VECMOVE, 28);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (28);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeDouble (direction.getPx ());
            xdr.xdrEncodeDouble (direction.getPy ());
            xdr.xdrEncodeDouble (direction.getPz ());
            xdr.xdrEncodeFloat  (length);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Limb] : Couldn't send VECMOVE command: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Limb] : Error while XDR-encoding VECMOVE command: " +
                        e.toString(), e);
        }

    }

    /**
     * Request/reply: Power.
     * <br><br>
     * Turn the power to the limb by sending a PLAYER_LIMB_POWER_REQ request.
     * Be careful when turning power on that the limb is not obstructed from
     * its home position in case it moves to it (common behaviour). Null
     * response.
     * @param value Power setting; 0 for off, 1 for on.
     */
    public void setPower (int value) {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_LIMB_REQ_POWER, 4);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (4);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeByte ((byte)value);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Limb] : Couldn't request PLAYER_LIMB_POWER_REQ: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Limb] : Error while XDR-encoding POWER request: " +
                        e.toString(), e);
        }
    }

    /**
     * Request/reply: Brakes.
     * <br><br>
     * Turn the brakes of the limb on or off by sending a
     * PLAYER_LIMB_BRAKES_REQ request. Null response.
     * @param value Brakes setting; 0 for off, 1 for on.
     */
    public void setBrakes (int value) {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_LIMB_REQ_BRAKES, 4);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (4);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeByte ((byte)value);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Limb] : Couldn't request PLAYER_LIMB_BRAKES_REQ: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Limb] : Error while XDR-encoding BRAKES request: " +
                        e.toString(), e);
        }
    }

    /**
     * Request/reply: Query geometry.
     * <br><br>
     * Send a null PLAYER_LIMB_GEOM_REQ request to receive the
     * geometry in this form.
     */
    public void queryGeometry () {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_LIMB_REQ_GEOM, 0);
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Limb] : Couldn't request PLAYER_LIMB_GEOM_REQ: "
                        + e.toString(), e);
        }
    }

    /**
     * Request/reply: Speed.
     * <br><br>
     * Set the speed of the end effector for all subsequent movements by sending
     * a PLAYER_LIMB_SPEED_REQ request. Null response.
     * @param speed speed setting in m/s
     */
    public void setSpeed (float speed) {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_LIMB_REQ_SPEED, 4);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (4);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeFloat (speed);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Limb] : Couldn't request PLAYER_LIMB_SPEED_REQ: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Limb] : Error while XDR-encoding SPEED request: " +
                        e.toString(), e);
        }
    }

    /**
     * Handle acknowledgement response messages.
     * @param header Player header
     */
    protected void handleResponse (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype ()) {
                case PLAYER_LIMB_REQ_POWER: {
                    // null response
                    break;
                }
                case PLAYER_LIMB_REQ_BRAKES: {
                    // null response
                    break;
                }
                case PLAYER_LIMB_REQ_GEOM: {
                    // Buffer for reading basePos
                    byte[] buffer = new byte[24];
                    // Read basePos
                    is.readFully (buffer, 0, 24);

                    PlayerPoint3d basePose = new PlayerPoint3d ();

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();
                    basePose.setPx (xdr.xdrDecodeDouble ());
                    basePose.setPy (xdr.xdrDecodeDouble ());
                    basePose.setPz (xdr.xdrDecodeDouble ());
                    xdr.endDecoding   ();
                    xdr.close ();

                    plgeom = new PlayerLimbGeomReq ();
                    plgeom.setBasePos (basePose);
                    readyPlgeom = true;

                    break;
                }
                case PLAYER_LIMB_REQ_SPEED: {
                    // null response
                    break;
                }
                default:{
                    if (isDebugging)
                        logger.log (Level.FINEST, "[Limb][Debug] : " +
                                "Unexpected response " + header.getSubtype () +
                                " of size = " + header.getSize ());
                    break;
                }
            }
        } catch (IOException e) {
            throw new PlayerException
                ("[Limb] : Error reading payload: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Limb] : Error while XDR-decoding payload: " +
                        e.toString(), e);
        }
    }

    /**
     * Get the Limb data.
     * @return an object of type PlayerLimbData containing the requested data
     */
    public PlayerLimbData getData () { return this.pldata; }

    /**
     * Get the geometry data.
     * @return an object of type PlayerLimbGeomReq containing the requested data
     */
    public PlayerLimbGeomReq getGeom () { return this.plgeom; }

    /**
     * Check if data is available.
     * @return true if ready, false if not ready
     */
    public boolean isDataReady () {
        if (readyPldata) {
            readyPldata = false;
            return true;
        }
        return false;
    }

    /**
     * Check if geometry data is available.
     * @return true if ready, false if not ready
     */
    public boolean isGeomReady () {
        if (readyPlgeom) {
            readyPlgeom = false;
            return true;
        }
        return false;
    }
}
