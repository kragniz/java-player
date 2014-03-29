/*
 *  Player Java Client 3 - PlayerRangerDataIntns.java
 *  Copyright (C) 2010 Jorge Santos Simon
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
 * $Id: PlayerRangerDataIntns.java 153 2011-08-22 14:45:20Z corot $
 *
 */

package javaclient3.structures.ranger;

import javaclient3.structures.*;

/**
 * Data: intensities (PLAYER_RANGER_DATA_INTNS)
 * The ranger interface returns an array of intensity readings
 * from a robot's ranger device such as a laser scanner, sonar
 * array or IR array.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerRangerDataIntns implements PlayerConstants {

    // The intensity readings
    private double[] intns;


    /**
     * @return  The number of valid intensity readings.
     */
    public synchronized int getIntns_count () {
        return (this.intns == null)?0:this.intns.length;
    }

    /**
     * @return  The intensity readings [m]
     */
    public synchronized double[] getIntens () {
        return this.intns;
    }

    /**
     * @param newIntns  The intensity readings [m]
     */
    public synchronized void setIntns (double[] newIntns) {
        this.intns = newIntns;
    }

}