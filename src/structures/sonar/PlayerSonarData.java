/*
 *  Player Java Client 3 - PlayerSonarData.java
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
 * $Id: PlayerSonarData.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.sonar;

import javaclient3.structures.*;

/**
 * Data: ranges (PLAYER_SONAR_DATA_RANGES)
 * The sonar interface returns up to PLAYER_SONAR_MAX_SAMPLES range
 * readings from a robot's sonars.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerSonarData implements PlayerConstants {

    // The range readings [m]
    private float[] ranges;


    /**
     * @return  The number of valid range readings.
     */
    public synchronized int getRanges_count () {
        return (this.ranges == null)?0:this.ranges.length;
    }

    /**
     * @return  The range readings [m]
     */
    public synchronized float[] getRanges () {
        return this.ranges;
    }

    /**
     * @param newRanges  The range readings [m]
     */
    public synchronized void setRanges (float[] newRanges) {
        this.ranges = newRanges;
    }
}