/*
 *  Player Java Client 3 - IMUInterface.java
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
 * $Id: IMUInterface.java 164 2011-11-16 04:25:44Z corot $
 *
 */
package javaclient3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaclient3.structures.PlayerMsgHdr;
import javaclient3.structures.PlayerOrientation3d;
import javaclient3.structures.PlayerPose3d;
import javaclient3.structures.imu.PlayerIMUDataState;
import javaclient3.structures.imu.PlayerIMUDataCalib;
import javaclient3.structures.imu.PlayerIMUDataQuat;
import javaclient3.structures.imu.PlayerIMUDataEuler;
import javaclient3.structures.imu.PlayerIMUDataFullState;
import javaclient3.xdr.OncRpcException;
import javaclient3.xdr.XdrBufferDecodingStream;
import javaclient3.xdr.XdrBufferEncodingStream;

/**
 * Inertial Measurement Unit.
 * The imu interface provides access to an Inertial Measurement Unit sensor
 * (such as the XSens MTx/MTi).
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class IMUInterface extends PlayerDevice {

    private static final boolean isDebugging = PlayerClient.isDebugging;

    // Logging support
    private Logger logger = Logger.getLogger (IMUInterface.class.getName ());

    private PlayerIMUDataState     pIMUState;
    private boolean                readyPIMUState = false;
    private PlayerIMUDataCalib     pIMUCalib;
    private boolean                readyPIMUCalib = false;
    private PlayerIMUDataQuat      pIMUQuat;
    private boolean                readyPIMUQuat  = false;
    private PlayerIMUDataEuler     pIMUEuler;
    private boolean                readyPIMUEuler = false;
    private PlayerIMUDataFullState pIMUFull;
    private boolean                readyPIMUFull = false;



    /**
     * Constructor for IMUInterface.
     * @param pc A reference to the PlayerClient object.
     */
    public IMUInterface (PlayerClient pc) { super (pc); }

    /**
     * Read the IMU values for the selected data type.
     * @param header Player message header.
     */
    public synchronized void readData (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype()) {
                case PLAYER_IMU_DATA_STATE: {
                    this.timestamp = header.getTimestamp();

                    // Buffer for reading 3D pose (6 x 8 = 48)
                    byte[] buffer = new byte[48];

                    // Read 3D pose
                    is.readFully (buffer, 0, 48);

                    pIMUState = new PlayerIMUDataState ();
                    PlayerPose3d pos = new PlayerPose3d ();

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();

                    // (x, y, z, roll, pitch, yaw) position [m, m, m, rad, rad, rad]
                    pos.setPx     (xdr.xdrDecodeDouble ());
                    pos.setPy     (xdr.xdrDecodeDouble ());
                    pos.setPz     (xdr.xdrDecodeDouble ());
                    pos.setProll  (xdr.xdrDecodeDouble ());
                    pos.setPpitch (xdr.xdrDecodeDouble ());
                    pos.setPyaw   (xdr.xdrDecodeDouble ());
                    pIMUState.setPose (pos);

                    xdr.endDecoding   ();
                    xdr.close ();

                    readyPIMUState = true;
                    break;
                }
                case PLAYER_IMU_DATA_CALIB: {
                    this.timestamp = header.getTimestamp();

                    // Buffer for reading calibrated IMU data (9 x 4 = 36)
                    byte[] buffer = new byte[36];

                    // Read calibrated IMU data
                    is.readFully (buffer, 0, 36);

                    pIMUCalib = new PlayerIMUDataCalib ();

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();

                    // calibrated acceleration, gyroscope and magnetic values (x, y, z)
                    pIMUCalib.setAccel_x (xdr.xdrDecodeFloat ());
                    pIMUCalib.setAccel_y (xdr.xdrDecodeFloat ());
                    pIMUCalib.setAccel_z (xdr.xdrDecodeFloat ());
                    pIMUCalib.setGyro_x  (xdr.xdrDecodeFloat ());
                    pIMUCalib.setGyro_y  (xdr.xdrDecodeFloat ());
                    pIMUCalib.setGyro_z  (xdr.xdrDecodeFloat ());
                    pIMUCalib.setMagn_x  (xdr.xdrDecodeFloat ());
                    pIMUCalib.setMagn_y  (xdr.xdrDecodeFloat ());
                    pIMUCalib.setMagn_z  (xdr.xdrDecodeFloat ());

                    xdr.endDecoding   ();
                    xdr.close ();

                    readyPIMUCalib = true;
                    break;
                }
                case PLAYER_IMU_DATA_QUAT: {
                    this.timestamp = header.getTimestamp();

                    // Buffer for reading quaternions orientation data, including
                    // calibrated IMU values (36 + 4 x 4 = 52)
                    byte[] buffer = new byte[52];

                    // Read calibrated IMU and orientation quaternions data
                    is.readFully (buffer, 0, 52);

                    pIMUQuat = new PlayerIMUDataQuat ();

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();

                    // calibrated acceleration, gyroscope and magnetic values (x, y, z)
                    PlayerIMUDataCalib calib = new PlayerIMUDataCalib ();

                    calib.setAccel_x (xdr.xdrDecodeFloat ());
                    calib.setAccel_y (xdr.xdrDecodeFloat ());
                    calib.setAccel_z (xdr.xdrDecodeFloat ());
                    calib.setGyro_x  (xdr.xdrDecodeFloat ());
                    calib.setGyro_y  (xdr.xdrDecodeFloat ());
                    calib.setGyro_z  (xdr.xdrDecodeFloat ());
                    calib.setMagn_x  (xdr.xdrDecodeFloat ());
                    calib.setMagn_y  (xdr.xdrDecodeFloat ());
                    calib.setMagn_z  (xdr.xdrDecodeFloat ());

                    pIMUQuat.setCalib_data (calib);

                    // orientation quaternions values (q1, q2, q3 and q4)
                    float[] quaternions = new float[4];

                    quaternions [0] = xdr.xdrDecodeFloat ();
                    quaternions [1] = xdr.xdrDecodeFloat ();
                    quaternions [2] = xdr.xdrDecodeFloat ();
                    quaternions [3] = xdr.xdrDecodeFloat ();

                    pIMUQuat.setQuaternions (quaternions);

                    xdr.endDecoding   ();
                    xdr.close ();

                    readyPIMUQuat = true;
                    break;
                }
                case PLAYER_IMU_DATA_EULER: {
                    this.timestamp = header.getTimestamp();

                    // Buffer for reading orientation data as Euler angles, including
                    // calibrated IMU values (36 + 3 x 8 = 60)
                    byte[] buffer = new byte[60];

                    // Read calibrated IMU and orientation Euler angles data
                    is.readFully (buffer, 0, 60);

                    pIMUEuler = new PlayerIMUDataEuler ();

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();

                    // calibrated acceleration, gyroscope and magnetic values (x, y, z)
                    PlayerIMUDataCalib calib = new PlayerIMUDataCalib ();

                    calib.setAccel_x (xdr.xdrDecodeFloat ());
                    calib.setAccel_y (xdr.xdrDecodeFloat ());
                    calib.setAccel_z (xdr.xdrDecodeFloat ());
                    calib.setGyro_x  (xdr.xdrDecodeFloat ());
                    calib.setGyro_y  (xdr.xdrDecodeFloat ());
                    calib.setGyro_z  (xdr.xdrDecodeFloat ());
                    calib.setMagn_x  (xdr.xdrDecodeFloat ());
                    calib.setMagn_y  (xdr.xdrDecodeFloat ());
                    calib.setMagn_z  (xdr.xdrDecodeFloat ());

                    pIMUEuler.setCalib_data (calib);

                    // Euler angles (roll, pitch, yaw) [rad, rad, rad]
                    PlayerOrientation3d angles = new PlayerOrientation3d ();
                    angles.setProll  (xdr.xdrDecodeDouble ());
                    angles.setPpitch (xdr.xdrDecodeDouble ());
                    angles.setPyaw   (xdr.xdrDecodeDouble ());

                    pIMUEuler.setOrientation (angles);

                    xdr.endDecoding   ();
                    xdr.close ();

                    readyPIMUEuler = true;
                    break;
                }
                case PLAYER_IMU_DATA_FULLSTATE: {
                    this.timestamp = header.getTimestamp();

                    // Buffer for reading all the calibrated IMU data:
                    // pose, velocity and acceleration (3 x 6 x 8 = 144)
                    byte[] buffer = new byte[144];

                    // Read all the calibrated IMU data
                    is.readFully (buffer, 0, 144);

                    pIMUFull = new PlayerIMUDataFullState ();
                    PlayerPose3d pos = new PlayerPose3d ();
                    PlayerPose3d vel = new PlayerPose3d ();
                    PlayerPose3d acc = new PlayerPose3d ();

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();

                    // (x, y, z, roll, pitch, yaw) position [m, m, m, rad, rad, rad]
                    pos.setPx     (xdr.xdrDecodeDouble ());
                    pos.setPy     (xdr.xdrDecodeDouble ());
                    pos.setPz     (xdr.xdrDecodeDouble ());
                    pos.setProll  (xdr.xdrDecodeDouble ());
                    pos.setPpitch (xdr.xdrDecodeDouble ());
                    pos.setPyaw   (xdr.xdrDecodeDouble ());

                    // (x, y, z, roll, pitch, yaw) velocity
                    // [m/s, m/s, m/s, rad/s, rad/s, rad/s]
                    vel.setPx     (xdr.xdrDecodeDouble ());
                    vel.setPy     (xdr.xdrDecodeDouble ());
                    vel.setPz     (xdr.xdrDecodeDouble ());
                    vel.setProll  (xdr.xdrDecodeDouble ());
                    vel.setPpitch (xdr.xdrDecodeDouble ());
                    vel.setPyaw   (xdr.xdrDecodeDouble ());

                    // (x, y, z, roll, pitch, yaw) acceleration
                    // [m/s^2, m/s^2, m/s^2, rad/s^2, rad/s^2, rad/s^2]
                    acc.setPx     (xdr.xdrDecodeDouble ());
                    acc.setPy     (xdr.xdrDecodeDouble ());
                    acc.setPz     (xdr.xdrDecodeDouble ());
                    acc.setProll  (xdr.xdrDecodeDouble ());
                    acc.setPpitch (xdr.xdrDecodeDouble ());
                    acc.setPyaw   (xdr.xdrDecodeDouble ());

                    pIMUFull.setPose (pos);
                    pIMUFull.setVel  (vel);
                    pIMUFull.setAcc  (acc);

                    xdr.endDecoding   ();
                    xdr.close ();

                    readyPIMUFull = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new PlayerException
                ("[IMU] : Error reading payload: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[IMU] : Error while XDR-decoding payload: " +
                        e.toString(), e);
        }
    }

    /**
     * Configuration request: Change the data type to one of the predefined data
     * structures. We send a PLAYER_IMU_REQ_SET_DATATYPE request to switch between
     * calibrated data, 3D pose and orientation, Euler orientation or Quaternions
     * orientation in the data packet. Null response.
     * @param type Data type setting: 1 for pose/orientation, 2 for calibrated
     * (raw) data, 3 for quaternions, 4 for Euler angles.
     */
    public void setControlMode (byte type) {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_IMU_REQ_SET_DATATYPE, 4);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (4);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeByte (type);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[IMU] : Couldn't request PLAYER_IMU_REQ_SET_DATATYPE: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[IMU] : Error while XDR-encoding SET_DATATYPE " +
                        "request: " + e.toString(), e);
        }
    }

    /**
     * Configuration request: Reset orientation.
     * To reset the IMU's orientation, send a PLAYER_IMU_REQ_RESET_ORIENTATION
     * request. Null response.
     * @param value  Driver-specific.
     */
    public void resetOrientation (int value) {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_IMU_REQ_RESET_ORIENTATION, 4);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (4);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeInt (value);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[IMU] : Couldn't request PLAYER_IMU_REQ_RESET_ORIENTATION: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[IMU] : Error while XDR-encoding RESET_ORIENTATION " +
                        "request: " + e.toString(), e);
        }
    }

    /**
     * Handle acknowledgement response messages.
     * @param header Player header.
     */
    protected void handleResponse (PlayerMsgHdr header) {
        switch (header.getSubtype ()) {
            case PLAYER_IMU_REQ_SET_DATATYPE: {
                // null response
                break;
            }
            case PLAYER_IMU_REQ_RESET_ORIENTATION: {
                // null response
                break;
            }
            default:{
                if (isDebugging)
                    logger.log (Level.FINEST, "[IMU][Debug] : " +
                            "Unexpected response " + header.getSubtype () +
                            " of size = " + header.getSize ());
                break;
            }
        }
    }

    /**
     * Get the state data: the complete pose of the IMU in 3D coordinates + angles.
     * @return An object of type PlayerIMUDataState containing the requested data
     */
    public PlayerIMUDataState getState () { return this.pIMUState; }

    /**
     * Check if state data is available.
     * @return True if ready, false if not ready
     */
    public boolean isStateReady () {
        if (readyPIMUState) {
            readyPIMUState = false;
            return true;
        }
        return false;
    }

    /**
     * Get the calibration data: calibrated acceleration, gyroscope and magnetic
     * values from the IMU sensor.
     * @return An object of type PlayerIMUDataCalib containing the requested data
     */
    public PlayerIMUDataCalib getCalib () { return this.pIMUCalib; }

    /**
     * Check if calibration data is available.
     * @return True if ready, false if not ready
     */
    public boolean isCalibReady () {
        if (readyPIMUCalib) {
            readyPIMUCalib = false;
            return true;
        }
        return false;
    }

    /**
     * Get the quaternions orientation data: calibrated IMU values as well as
     * orientation data as quaternions.
     * @return An object of type PlayerIMUDataQuat containing the requested data
     */
    public PlayerIMUDataQuat getQuat () { return this.pIMUQuat; }

    /**
     * Check if quaternions orientation data is available.
     * @return True if ready, false if not ready
     */
    public boolean isQuatReady () {
        if (readyPIMUQuat) {
            readyPIMUQuat = false;
            return true;
        }
        return false;
    }

    /**
     * Get the Euler orientation data: calibrated IMU values as well as orientation
     * data as Euler angles.
     * @return An object of type PlayerIMUDataEuler containing the requested data
     */
    public PlayerIMUDataEuler getEuler () { return this.pIMUEuler; }

    /**
     * Check if Euler orientation data is available.
     * @return True if ready, false if not ready
     */
    public boolean isEulerReady () {
        if (readyPIMUEuler) {
            readyPIMUEuler = false;
            return true;
        }
        return false;
    }

    /**
     * Get full state data: all the calibrated IMU values.
     * @return An object of type PlayerIMUDataFullState containing the requested data
     */
    public PlayerIMUDataFullState getFullState () { return this.pIMUFull; }

    /**
     * Check if full state data is available.
     * @return True if ready, false if not ready
     */
    public boolean isFullStateReady () {
        if (readyPIMUFull) {
            readyPIMUFull = false;
            return true;
        }
        return false;
    }
}
