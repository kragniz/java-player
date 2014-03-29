/*
 *  Player Java Client 3 - PlayerBumperDefine.java
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
 * $Id: PlayerBumperDefine.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.bumper;

import javaclient3.structures.*;

/**
 * The geometry of a single bumper
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerBumperDefine implements PlayerConstants {

    // the local pose of a single bumper
    private PlayerPose3d pose;
    // length of the sensor [m]
    private float length;
    // radius of curvature [m] - zero for straight lines
    private float radius;


    /**
     * @return  the local pose of a single bumper
     */
    public synchronized PlayerPose3d getPose () {
        return this.pose;
    }

    /**
     * @param newPose  the local pose of a single bumper
     */
    public synchronized void setPose (PlayerPose3d newPose) {
        this.pose = newPose;
    }

    /**
     * @return  length of the sensor [m]
     */
    public synchronized float getLength () {
        return this.length;
    }

    /**
     * @param newLength  length of the sensor [m]
     */
    public synchronized void setLength (float newLength) {
        this.length = newLength;
    }

    /**
     * @return  radius of curvature [m] - zero for straight lines
     */
    public synchronized float getRadius () {
        return this.radius;
    }

    /**
     * @param newRadius  radius of curvature [m] - zero for straight lines
     */
    public synchronized void setRadius (float newRadius) {
        this.radius = newRadius;
    }
}