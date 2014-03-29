/*
 *  Player Java Client 3 - PlayerPointCloud3dStereoElement.java
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
 * $Id: PlayerPointCloud3dStereoElement.java 148 2011-08-22 14:37:59Z corot $
 *
 */

package javaclient3.structures.stereo;

import javaclient3.structures.*;

/**
 * 3D Point cloud element structure.
 * An element as stored in a 3D point cloud, containing a 3D position
 * plus other corresponding information.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerPointCloud3dStereoElement implements PlayerConstants {

    /** X coordinate [m]. */
    private float px;
    /** Y coordinate [m]. */
    private float py;
    /** Z-coordinate [m]. */
    private float pz;
    /** Red color channel. */
    private byte  red;
    /** Green color channel. */
    private byte  green;
    /** Blue color channel. */
    private byte  blue;

    /**
     * @return X-coordinate [m]
     */
    public synchronized float getPx () {
        return this.px;
    }

    /**
     * @param newPx X-coordinate [m]
     */
    public synchronized void setPx (float newPx) {
        this.px = newPx;
    }

    /**
     * @return Y-coordinate [m]
     */
    public synchronized float getPy () {
        return this.py;
    }

    /**
     * @param newPy Y-coordinate [m]
     */
    public synchronized void setPy (float newPy) {
        this.py = newPy;
    }

    /**
     * @return Z-coordinate [m]
     */
    public synchronized float getPz () {
        return this.pz;
    }

    /**
     * @param newPz Z-coordinate [m]
     */
    public synchronized void setPz (float newPz) {
        this.pz = newPz;
    }

    /**
     * @return Red color channel
     */
    public synchronized byte getRed () {
        return this.red;
    }

    /**
     * @param newRed Red color channel
     */
    public synchronized void setRed (byte newRed) {
        this.red = newRed;
    }

    /**
     * @return Green color channel
     */
    public synchronized byte getGreen () {
        return this.green;
    }

    /**
     * @param newGreen Green color channel
     */
    public synchronized void setGreen (byte newGreen) {
        this.green = newGreen;
    }

    /**
     * @return Blue color channel
     */
    public synchronized byte getBlue () {
        return this.blue;
    }

    /**
     * @param newBlue Blue color channel
     */
    public synchronized void setBlue (byte newBlue) {
        this.blue = newBlue;
    }
}