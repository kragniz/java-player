/*
 *  Player Java Client 3 - PlayerPosition2dData.java
 *  Copyright (C) 2006 Radu Bogdan Rusu
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
 * $Id: PlayerPosition2dData.java 140 2011-05-09 19:49:30Z corot $
 *
 */

package javaclient3.structures.position2d;

import javaclient3.structures.*;

/**
 * Data: state (PLAYER_POSITION2D_DATA_STATE)
 * The position interface returns data regarding the odometric pose and
 * velocity of the robot, as well as motor stall information.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerPosition2dData implements PlayerConstants {

    // Position [m, m, rad] (x, y, yaw)
    private PlayerPose2d pos;
    // Translational velocities [m/s, m/s, rad/s] (x, y, yaw)
    private PlayerPose2d vel;
    // Are the motors stalled?
    private byte stall;


    /**
     * @return  position [m, m, rad] (x, y, yaw)
     */
    public synchronized PlayerPose2d getPos () {
        return this.pos;
    }

    /**
     * @param newPos  position [m, m, rad] (x, y, yaw)
     */
    public synchronized void setPos (PlayerPose2d newPos) {
        this.pos = newPos;
    }

    /**
     * @return  translational velocities [m/s, m/s, rad/s] (x, y, yaw)
     */
    public synchronized PlayerPose2d getVel () {
        return this.vel;
    }

    /**
     * @param newVel  translational velocities [m/s, m/s, rad/s] (x, y, yaw)
     */
    public synchronized void setVel (PlayerPose2d newVel) {
        this.vel = newVel;
    }

    /**
     * @return  Are the motors stalled?
     */
    public synchronized byte getStall () {
        return this.stall;
    }

    /**
     * @param newStall  Are the motors stalled?
     */
    public synchronized void setStall (byte newStall) {
        this.stall = newStall;
    }

    /**
     * return  A string representation of the position 2d data.
     */
    public String toString() {
        StringBuffer result = new StringBuffer("(PlayerPosition2dData");
        result.append("\n\tPosition: x=" + pos.getPx() + "m, y=" + pos.getPy()
                    + "m, yaw=" + pos.getPa() + "rad");
        result.append("\n\tVelocity: x=" + vel.getPx() + "m/s, y= " + vel.getPy()
                    + "m/s, yaw=" + vel.getPa() + "rad/s)");
        return result.toString();
    }
}