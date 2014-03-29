/*
 *  Player Java Client 3 - PlayerIMUDataFullState.java
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
 * $Id: PlayerIMUDataFullState.java 163 2011-11-16 04:25:06Z corot $
 *
 */

package javaclient3.structures.imu;

import javaclient3.structures.*;

/**
 * Data: all of the calibrated IMU data (PLAYER_IMU_DATA_FULLSTATE).
 * The imu interface returns the complete 3D coordinates + angles position in
 * space, as well as velocity and acceleration of the IMU sensor.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerIMUDataFullState implements PlayerConstants {

    // (x, y, z, roll, pitch, yaw) position [m, m, m, rad, rad, rad]
    private PlayerPose3d pos;
    // (x, y, z, roll, pitch, yaw) velocity [m/s, m/s, m/s, rad/s, rad/s, rad/s]
    private PlayerPose3d vel;
    // (x, y, z, roll, pitch, yaw) acceleration [m/s^2, m/s^2, m/s^2, rad/s^2, rad/s^2, rad/s^2]
    private PlayerPose3d acc;


    /**
     * @return  The complete pose of the IMU in 3D coordinates + angles
     * (x, y, z, roll, pitch, yaw) [m, m, m, rad, rad, rad]
     */
    public synchronized PlayerPose3d getPose () {
        return this.pos;
    }

    /**
     * @param newPos  The complete pose of the IMU in 3D coordinates + angles
     * (x, y, z, roll, pitch, yaw) [m, m, m, rad, rad, rad]
     */
    public synchronized void setPose (PlayerPose3d newPos) {
        this.pos = newPos;
    }

    /**
     * @return  The complete velocity of the IMU in 3D coordinates + angles
     * (x, y, z, roll, pitch, yaw) [m/s, m/s, m/s, rad/s, rad/s, rad/s]
     */
    public synchronized PlayerPose3d getVel () {
        return this.vel;
    }

    /**
     * @param newVel  The complete velocity of the IMU in 3D coordinates + angles
     * (x, y, z, roll, pitch, yaw) [m/s, m/s, m/s, rad/s, rad/s, rad/s]
     */
    public synchronized void setVel (PlayerPose3d newVel) {
        this.vel = newVel;
    }

    /**
     * @return  The complete acceleration of the IMU in 3D coordinates + angles
     * (x, y, z, roll, pitch, yaw) [m/s^2, m/s^2, m/s^2, rad/s^2, rad/s^2, rad/s^2]
     */
    public synchronized PlayerPose3d getAcc () {
        return this.acc;
    }

    /**
     * @param newAcc  The complete acceleration of the IMU in 3D coordinates + angles
     * (x, y, z, roll, pitch, yaw) [m/s^2, m/s^2, m/s^2, rad/s^2, rad/s^2, rad/s^2]
     */
    public synchronized void setAcc (PlayerPose3d newAcc) {
        this.acc = newAcc;
    }
}