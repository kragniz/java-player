/*
 *  Player Java Client 3 - PlayerActarrayGeom.java
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
 * $Id: PlayerActarrayGeom.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.actarray;

import javaclient3.structures.*;

/**
 * Request/reply: get geometry
 * Send a null PLAYER_ACTARRAY_REQ_GET_GEOM request to receive the geometry in
 * this form.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerActarrayGeom implements PlayerConstants {

    // The geometry information for each actuator in the array.
    private PlayerActarrayActuatorGeom[] actuators;
    // The position of the base of the actarray.
    private PlayerPoint3d base_pos;
    // The orientation of the base of the actarray.
    private PlayerOrientation3d base_orientation;

    /**
     * @return  The number of actuators in the array.
     */
    public synchronized int getActuators_count () {
        return (this.actuators == null)?0:this.actuators.length;
    }

    /**
     * @return  The geometry information for each actuator in the array.
     */
    public synchronized PlayerActarrayActuatorGeom[] getActuators () {
        return this.actuators;
    }

    /**
     * @param newActuators  The geometry information for each actuator in the array.
     */
    public synchronized void setActuators (PlayerActarrayActuatorGeom[] newActuators) {
        this.actuators = newActuators;
    }

    /**
     * @return The orientation of the base of the actarray.
     */
    public synchronized PlayerOrientation3d getOrientation () {
        return base_orientation;
    }

    /**
     * @param newOrientation The orientation of the base of the actarray.
     */
    public synchronized void setOrientation (PlayerOrientation3d newOrientation) {
        this.base_orientation = newOrientation;
    }

    /**
     * @return The position of the base of the actarray.
     */
    public synchronized PlayerPoint3d getBasePos () {
        return base_pos;
    }

    /**
     * @param newBasePos The position of the base of the actarray.
     */
    public synchronized void setBasePos (PlayerPoint3d newBasePos) {
        this.base_pos = newBasePos;
    }
}