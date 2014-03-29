/*
 *  Player Java Client 3 - Position3DInterface.java
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
 * $Id: Position3DInterface.java 166 2011-11-16 04:29:32Z corot $
 *
 */
package javaclient3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaclient3.structures.PlayerMsgHdr;
import javaclient3.structures.PlayerPose3d;
import javaclient3.structures.PlayerBbox3d;
import javaclient3.structures.position3d.PlayerPosition3dCmdPos;
import javaclient3.structures.position3d.PlayerPosition3dCmdVel;
import javaclient3.structures.position3d.PlayerPosition3dData;
import javaclient3.structures.position3d.PlayerPosition3dGeom;
import javaclient3.xdr.OncRpcException;
import javaclient3.xdr.XdrBufferDecodingStream;
import javaclient3.xdr.XdrBufferEncodingStream;

/**
 * The position3d interface is used to control mobile robot bases in
 * 3D (i.e., pitch and roll are important).
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class Position3DInterface extends AbstractPositionDevice {

    private static final boolean isDebugging = PlayerClient.isDebugging;

    // Logging support
    private Logger logger = Logger.getLogger (Position3DInterface.class.getName ());

    private PlayerPosition3dData pp3ddata;
    private boolean              readyPp3ddata = false;
    private PlayerPosition3dGeom pp3dgeom;
    private boolean              readyPp3dgeom = false;


    /**
     * Constructor for Position3DInterface.
     * @param pc a reference to the PlayerClient object
     */
    public Position3DInterface (PlayerClient pc) { super(pc); }

    /**
     * This interface returns data regarding the odometric pose and velocity
     * of the robot, as well as motor stall information.
     * @param header Player message header.
     */
    public synchronized void readData (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype ()) {
                case PLAYER_POSITION3D_DATA_STATE: {
                    this.timestamp = header.getTimestamp();

                    // Buffer for reading pos, vel and stall (48 + 48 + 4 = 100)
                    byte[] buffer = new byte[100];
                    // Read pos, vel and stall
                    is.readFully (buffer, 0, 100);

                    pp3ddata = new PlayerPosition3dData ();
                    PlayerPose3d pos = new PlayerPose3d ();
                    PlayerPose3d vel = new PlayerPose3d ();

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
                    pp3ddata.setPos (pos);
                    // (x, y, z, roll, pitch, yaw) velocities [m/s, rad/s]
                    vel.setPx     (xdr.xdrDecodeDouble ());
                    vel.setPy     (xdr.xdrDecodeDouble ());
                    vel.setPz     (xdr.xdrDecodeDouble ());
                    vel.setProll  (xdr.xdrDecodeDouble ());
                    vel.setPpitch (xdr.xdrDecodeDouble ());
                    vel.setPyaw   (xdr.xdrDecodeDouble ());
                    pp3ddata.setVel (vel);
                    // motors stall
                    pp3ddata.setStall (xdr.xdrDecodeByte ());
                    xdr.endDecoding   ();
                    xdr.close ();

                    readyPp3ddata = true;
                    break;
                }
                case PLAYER_POSITION3D_DATA_GEOMETRY: {
               this.timestamp = header.getTimestamp();

               readGeom ();
                    readyPp3ddata = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Error reading payload: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Position3D] : Error while XDR-decoding payload: " +
                        e.toString(), e);
        }
    }

    private void readGeom () {
        try {
            // Buffer for reading pose and size (48 + 24 = 72)
            byte[] buffer = new byte[72];
            // Read pose and size
            is.readFully (buffer, 0, 72);

            pp3dgeom = new PlayerPosition3dGeom ();
            PlayerPose3d pose = new PlayerPose3d ();
            PlayerBbox3d size = new PlayerBbox3d ();

            // Begin decoding the XDR buffer
            XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
            xdr.beginDecoding ();

            // pose of the robot base [m, m, m, rad, rad, rad]
            pose.setPx     (xdr.xdrDecodeDouble ());
            pose.setPy     (xdr.xdrDecodeDouble ());
            pose.setPz     (xdr.xdrDecodeDouble ());
            pose.setProll  (xdr.xdrDecodeDouble ());
            pose.setPpitch (xdr.xdrDecodeDouble ());
            pose.setPyaw   (xdr.xdrDecodeDouble ());
            pp3dgeom.setPose (pose);

            // dimensions of the base [m, m, m]
            size.setSw (xdr.xdrDecodeDouble ());
            size.setSl (xdr.xdrDecodeDouble ());
            size.setSh (xdr.xdrDecodeDouble ());
            pp3dgeom.setSize (size);

            xdr.endDecoding   ();
            xdr.close ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Error reading geometry data: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Position3D] : Error while XDR-decoding geometry data: " +
                        e.toString(), e);
        }
    }

    /**
     * Command: position (PLAYER_POSITION3D_CMD_SET_POS).
     * <br><br>
     * It accepts new positions and/or velocities for the robot's motors
     * (drivers may support position control, speed control or both).
     * <br><br>
     * See the player_position3d_cmd_pos structure from player.h.
     * @param pos A PlayerPose3d structure containing the required position data.
     * @param vel A PlayerPose3d structure containing the required velocity data.
     * @param state Motor state (zero is either off or locked, depending on the driver).
     */
    public void setPosition (PlayerPose3d pos, PlayerPose3d vel, int state) {
        try {
            sendHeader (PLAYER_MSGTYPE_CMD, PLAYER_POSITION3D_CMD_SET_POS, 96+4);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (96+4);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeDouble (pos.getPx     ());
            xdr.xdrEncodeDouble (pos.getPy     ());
            xdr.xdrEncodeDouble (pos.getPz     ());
            xdr.xdrEncodeDouble (pos.getProll  ());
            xdr.xdrEncodeDouble (pos.getPpitch ());
            xdr.xdrEncodeDouble (pos.getPyaw   ());
            xdr.xdrEncodeDouble (vel.getPx     ());
            xdr.xdrEncodeDouble (vel.getPy     ());
            xdr.xdrEncodeDouble (vel.getPz     ());
            xdr.xdrEncodeDouble (vel.getProll  ());
            xdr.xdrEncodeDouble (vel.getPpitch ());
            xdr.xdrEncodeDouble (vel.getPyaw   ());
            xdr.xdrEncodeByte ((byte)state);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Couldn't send position commands: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Position3D] : Error while XDR-encoding position commands: "
                        + e.toString(), e);
        }
    }

    /**
     * Command: position (PLAYER_POSITION3D_CMD_SET_POS).
     * <br><br>
     * It accepts new positions and/or velocities for the robot's motors
     * (drivers may support position control, speed control or both).
     * <br><br>
     * See the player_position3d_cmd_pos structure from player.h.
     * @param pp3dcp A PlayerPosition3dCmdPos structure holding the required data.
     */
    public void setPosition (PlayerPosition3dCmdPos pp3dcp) {
        setPosition(pp3dcp.getPos(), pp3dcp.getVel(), pp3dcp.getState());
    }

    /**
     * Command: velocity (PLAYER_POSITION3D_CMD_SET_VEL).
     * <br><br>
     * It accepts new velocities for the robot's motors (drivers may support speed
     * control).
     * <br><br>
     * See the player_position3d_cmd_vel structure from player.h.
     * @param vel A PlayerPose3d structure containing the required velocity data.
     * @param state Motor state (zero is either off or locked, depending on the driver).
     */
    public void setVelocity (PlayerPose3d vel, int state) {
        try {
            sendHeader (PLAYER_MSGTYPE_CMD, PLAYER_POSITION3D_CMD_SET_VEL, 48+4);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (48+4);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeDouble (vel.getPx     ());
            xdr.xdrEncodeDouble (vel.getPy     ());
            xdr.xdrEncodeDouble (vel.getPz     ());
            xdr.xdrEncodeDouble (vel.getProll  ());
            xdr.xdrEncodeDouble (vel.getPpitch ());
            xdr.xdrEncodeDouble (vel.getPyaw   ());
            xdr.xdrEncodeByte ((byte)state);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Couldn't send velocity commands: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Position3D] : Error while XDR-encoding velocity commands: "
                        + e.toString(), e);
        }
    }

    /**
     * Command: velocity (PLAYER_POSITION3D_CMD_SET_VEL).
     * <br><br>
     * It accepts new velocities for the robot's motors (drivers may support speed
     * control).
     * <br><br>
     * See the player_position3d_cmd_vel structure from player.h.
     * @param pp3dcv A PlayerPosition3dCmdVel structure holding the required data.
     */
    public void setVelocity (PlayerPosition3dCmdVel pp3dcv) {
        setVelocity(pp3dcv.getVel(), pp3dcv.getState());
    }

    /**
     * Request/reply: Query geometry.
     * <br><br>
     * To request robot geometry, send a null PLAYER_POSITION3D_GET_GEOM
     * request.
     */
    public void queryGeometry () {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_POSITION3D_GET_GEOM, 0);
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Couldn't send " +
                        "PLAYER_POSITION3D_GET_GEOM_REQ: " + e.toString(), e);
        }
    }

    /**
     * Request/reply: Motor power.
     * <br><br>
     * On some robots, the motor power can be turned on and off from software.
     * TO do so, send a PLAYER_POSITION3D_MOTOR_POWER request with the format
     * given below, and with the appropriate state (zero for motors off, and
     * non-zero for motors on). Null response.
     * <br><br>
     * Be VERY careful with this command! You are very likely to start the robot
     * running across the room at high speed with the battery charger still attached.
     * @param state 0 for off, 1 for on.
     */
    public void setMotorPower (int state) {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_POSITION3D_MOTOR_POWER, 4);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (4);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeByte ((byte)state);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Couldn't request " +
                        "PLAYER_POSITION3D_MOTOR_POWER: " + e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Position3D] : Error while XDR-encoding POWER request: "
                        + e.toString(), e);
        }
    }

    /**
     * Configuration request: Change position control.
     * <br><br>
     * To change control mode, send a PLAYER_POSITION3D_POSITION_MODE request.
     * Null response.
     * @param state 0 for velocity mode, 1 for position mode.
     */
    public void setControlMode (int state) {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_POSITION3D_POSITION_MODE, 4);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (4);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeInt(state);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Couldn't request " +
                        "PLAYER_POSITION3D_POSITION_MODE: " + e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Position3D] : Error while XDR-encoding POSITION_MODE " +
                        "request: " + e.toString(), e);
        }
    }

    /**
     * Configuration request: Change velocity control.
     * <br><br>
     * Some robots offer different velocity control modes. It can be changed
     * by sending a request with the format given below, including the
     * appropriate mode. No matter which mode is used, the external client
     * interface to the position3d device remains the same. Null response.
     * <br><br>
     * @param value Driver-specific.
     */
    public void setVelocityControl (int value) {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_POSITION3D_VELOCITY_MODE, 4);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (4);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeInt (value);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Couldn't request " +
                        "PLAYER_POSITION3D_VELOCITY_MODE: " + e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Position3D] : Error while XDR-encoding VELOCITY_MODE " +
                        "request: " + e.toString(), e);
        }
    }

    /**
     * Configuration request: Set odometry.
     * <br><br>
     * To set the robot's odometry to a particular state, send a
     * PLAYER_POSITION3D_SET_ODOM request. Null response.
     * @param pose A PlayerPose3d structure containing the pose data.
     */
    public void setOdometry (PlayerPose3d pose) {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_POSITION3D_SET_ODOM, 48);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (48);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeDouble (pose.getPx     ());
            xdr.xdrEncodeDouble (pose.getPy     ());
            xdr.xdrEncodeDouble (pose.getPz     ());
            xdr.xdrEncodeDouble (pose.getProll  ());
            xdr.xdrEncodeDouble (pose.getPpitch ());
            xdr.xdrEncodeDouble (pose.getPyaw   ());
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Couldn't request " +
                        "PLAYER_POSITION3D_SET_ODOM: " + e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Position3D] : Error while XDR-encoding SET_ODOM " +
                        "request: " + e.toString(), e);
        }
    }

    /**
     * Configuration request: Reset odometry.
     * <br><br>
     * To reset the robot's odometry to (x,y,theta) = (0,0,0), send a
     * PLAYER_POSITION3D_RESET_ODOM request. Null response.
     */
    public void resetOdometry () {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_POSITION3D_RESET_ODOM, 0);
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Couldn't request " +
                        "PLAYER_POSITION3D_RESET_ODOM: " + e.toString(), e);
        }
    }

    /**
     * Configuration request: Set velocity PID parameters.
     * @param kp P parameter.
     * @param ki I parameter.
     * @param kd D parameter.
     */
    public void setVelocityPIDParams (float kp, float ki, float kd) {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_POSITION3D_SPEED_PID, 12);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (12);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeFloat (kp);
            xdr.xdrEncodeFloat (ki);
            xdr.xdrEncodeFloat (kd);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Couldn't request " +
                        "PLAYER_POSITION3D_SPEED_PID: " + e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Position3D] : Error while XDR-encoding SPEED_PID " +
                        "request: " + e.toString(), e);
        }
    }

    /**
     * Configuration request: Set position PID parameters.
     * @param kp P parameter.
     * @param ki I parameter.
     * @param kd D parameter.
     */
    public void setPositionPIDParams (float kp, float ki, float kd) {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_POSITION3D_POSITION_PID, 12);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (12);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeFloat (kp);
            xdr.xdrEncodeFloat (ki);
            xdr.xdrEncodeFloat (kd);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Couldn't request " +
                        "PLAYER_POSITION3D_POSITION_PID: " + e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Position3D] : Error while XDR-encoding POSITION_PID " +
                        "request: " + e.toString(), e);
        }
    }

    /**
     * Configuration request: Set speed profile parameters.
     * @param speed Max speed [rad/s].
     * @param accel Max acceleration [rad/s^2].
     */
    public void setSpeedProfileParams (float speed, float accel) {
        try {
            sendHeader (PLAYER_MSGTYPE_REQ, PLAYER_POSITION3D_SPEED_PROF, 8);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (8);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeFloat (speed);
            xdr.xdrEncodeFloat (accel);
            xdr.endEncoding ();
            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Position3D] : Couldn't request " +
                        "PLAYER_POSITION3D_SPEED_PROF: " + e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Position3D] : Error while XDR-encoding SPEED_PROF " +
                        "request: " + e.toString(), e);
        }
    }

    /**
     * Handle acknowledgement response messages.
     * @param header Player message header.
     */
    protected void handleResponse (PlayerMsgHdr header) {
        switch (header.getSubtype ()) {
            case PLAYER_POSITION3D_GET_GEOM: {
                readGeom ();
                readyPp3dgeom = true;
                break;
            }
            case PLAYER_POSITION3D_MOTOR_POWER: {
                // null response
                break;
            }
            case PLAYER_POSITION3D_VELOCITY_MODE: {
                // null response
                break;
            }
            case PLAYER_POSITION3D_POSITION_MODE: {
                // null response
                break;
            }
            case PLAYER_POSITION3D_SET_ODOM: {
                // null response
                break;
            }
            case PLAYER_POSITION3D_RESET_ODOM: {
                // null response
                break;
            }
            case PLAYER_POSITION3D_SPEED_PID: {
                // null response
                break;
            }
            case PLAYER_POSITION3D_POSITION_PID: {
                // null response
                break;
            }
            case PLAYER_POSITION3D_SPEED_PROF: {
                // null response
                break;
            }
            default:{
                if (isDebugging)
                    logger.log (Level.FINEST, "[Position3D][Debug] : " +
                            "Unexpected response " + header.getSubtype () +
                            " of size = " + header.getSize ());
                break;
            }
        }
    }

    /**
     * Get the Position3D data.
     * @return An object of type PlayerPosition3DData containing the requested data.
     */
    public PlayerPosition3dData getData () { return this.pp3ddata; }

    /**
     * Get the geometry data.
     * @return An object of type PlayerPosition3DGeom containing the requested data.
     */
    public PlayerPosition3dGeom getGeom () { return this.pp3dgeom; }

    /**
     * Check if data is available.
     * @return True if ready, false if not ready.
     */
    public boolean isDataReady () {
        if (readyPp3ddata) {
            readyPp3ddata = false;
            return true;
        }
        return false;
    }

    /**
     * Check if geometry data is available.
     * @return True if ready, false if not ready.
     */
    public boolean isGeomReady () {
        if (readyPp3dgeom) {
            readyPp3dgeom = false;
            return true;
        }
        return false;
    }

    // used by HeadingControl and PositionControl - TODO refactorize
    public double getX     () {
        return this.pp3ddata.getPos ().getPx ();
    }

    public double getY     () {
        return this.pp3ddata.getPos ().getPy ();
    }

    public double getZ     () {
        return this.pp3ddata.getPos ().getPx ();
    }

    public double getYaw   () {
        return (float)((this.pp3ddata.getPos ().getPyaw   () * 180) / Math.PI);
    }

    public double getPitch () {
        return (float)((this.pp3ddata.getPos ().getPpitch () * 180) / Math.PI);
    }

    public double getRoll  () {
        return (float)((this.pp3ddata.getPos ().getProll  () * 180) / Math.PI);
    }

    public void setSpeed (double speed, double turnrate) {
        PlayerPose3d pp = new PlayerPose3d ();
        pp.setPx   (speed);
        pp.setPyaw (turnrate);
        setVelocity (pp, 1);
    }
}
