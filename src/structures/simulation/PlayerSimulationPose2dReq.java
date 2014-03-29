/*
 *  Player Java Client 3 - PlayerSimulationPose2dReq.java
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
 * $Id: PlayerSimulationPose2dReq.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.simulation;

import javaclient3.structures.*;

/**
 * Request/reply: get/set 2D pose of a named simulation object
 * To retrieve the pose of an object in a simulator, send a null
 * PLAYER_SIMULATION_REQ_GET_POSE2D request.  To set the pose of an object
 * in a simulator, send a PLAYER_SIMULATION_REQ_SET_POSE2D request (response
 * will be null).
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerSimulationPose2dReq implements PlayerConstants {

    // the identifier of the object we want to locate
    private char[] name;
    // the desired pose in [m, m, rad]
    private PlayerPose2d pose;


    /**
     * @return  Length of name
     */
    public synchronized int getName_count () {
        return (this.name == null)?0:this.name.length;
    }

    /**
     * @return  the identifier of the object we want to locate
     */
    public synchronized char[] getName () {
        return this.name;
    }

    /**
     * @param newName  the identifier of the object we want to locate
     */
    public synchronized void setName (char[] newName) {
        this.name = newName;
    }

    /**
     * @return  the desired pose in [m, m, rad]
     */
    public synchronized PlayerPose2d getPose () {
        return this.pose;
    }

    /**
     * @param newPose  the desired pose in [m, m, rad]
     */
    public synchronized void setPose (PlayerPose2d newPose) {
        this.pose = newPose;
    }
}