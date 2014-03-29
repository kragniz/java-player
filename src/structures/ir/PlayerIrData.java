/*
 *  Player Java Client 3 - PlayerIrData.java
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
 * $Id: PlayerIrData.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.ir;

import javaclient3.structures.*;

/**
 * Data: ranges (PLAYER_IR_DATA_RANGES)
 * The ir interface returns range readings from the IR array.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerIrData implements PlayerConstants {

    // voltages [V]
    private float[] voltages;
    // ranges [m]
    private float[] ranges;


    /**
     * @return  number of samples
     */
    public synchronized int getVoltages_count () {
        return (this.voltages == null)?0:this.voltages.length;
    }

    /**
     * @return  voltages [V]
     */
    public synchronized float[] getVoltages () {
        return this.voltages;
    }

    /**
     * @param newVoltages  voltages [V]
     */
    public synchronized void setVoltages (float[] newVoltages) {
        this.voltages = newVoltages;
    }

    /**
     * @return  number of samples
     */
    public synchronized int getRanges_count () {
        return (this.ranges == null)?0:this.ranges.length;
    }

    /**
     * @return  ranges [m]
     */
    public synchronized float[] getRanges () {
        return this.ranges;
    }

    /**
     * @param newRanges  ranges [m]
     */
    public synchronized void setRanges (float[] newRanges) {
        this.ranges = newRanges;
    }
}