/*
 *  Player Java Client 3 - PlayerLaserGeom.java
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
 * $Id: PlayerLaserGeom.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.laser;

import javaclient3.structures.*;

/**
 * Request/reply: Get geometry.
 * The laser geometry (position and size) can be queried by sending a
 * null PLAYER_LASER_REQ_GET_GEOM request.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerLaserGeom implements PlayerConstants {

    // Laser pose, in robot cs [m, m, m, rad, rad, rad]
    private PlayerPose3d pose;
    // Laser dimensions [m, m, m]
    private PlayerBbox3d size;


    /**
     * @return  Laser pose, in robot cs [m, m, m, rad, rad, rad].
     */
    public synchronized PlayerPose3d getPose () {
        return this.pose;
    }

    /**
     * @param newPose  Laser pose, in robot cs [m, m, m, rad, rad, rad].
     */
    public synchronized void setPose (PlayerPose3d newPose) {
        this.pose = newPose;
    }

    /**
     * @return  Laser dimensions [m, m, m].
     */
    public synchronized PlayerBbox3d getSize () {
        return this.size;
    }

    /**
     * @param newSize  Laser dimensions [m, m, m].
     */
    public synchronized void setSize (PlayerBbox3d newSize) {
        this.size = newSize;
    }
}