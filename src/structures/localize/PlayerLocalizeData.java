/*
 *  Player Java Client 3 - PlayerLocalizeData.java
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
 * $Id: PlayerLocalizeData.java 134 2011-05-04 00:08:23Z corot $
 *
 */

package javaclient3.structures.localize;

import javaclient3.structures.*;

/**
 * Data: hypotheses (PLAYER_LOCALIZE_DATA_HYPOTHS)
 * The localize interface returns a data packet containing an an array
 * of hypotheses.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerLocalizeData implements PlayerConstants {

    // The number of pending (unprocessed) observations
    private int pending_count;
    // The time stamp of the last observation processed
    private double pending_time;
    // The array of the hypotheses.
    private PlayerLocalizeHypoth[] hypoths;


    /**
     * @return  The number of pending (unprocessed observations)
     */
    public synchronized int getPending_count () {
        return this.pending_count;
    }

    /**
     * @param newPending_count  The number of pending (unprocessed observations)
     */
    public synchronized void setPending_count (int newPending_count) {
        this.pending_count = newPending_count;
    }

    /**
     * @return  The time stamp of the last observation processed.
     */
    public synchronized double getPending_time () {
        return this.pending_time;
    }

    /**
     * @param newPending_time  The time stamp of the last observation processed.
     */
    public synchronized void setPending_time (double newPending_time) {
        this.pending_time = newPending_time;
    }

    /**
     * @return  The number of pose hypotheses.
     */
    public synchronized int getHypoths_count () {
        return (this.hypoths == null)?0:hypoths.length;
    }

    /**
     * @return  The array of the hypotheses.
     */
    public synchronized PlayerLocalizeHypoth[] getHypoths () {
        return this.hypoths;
    }

    /**
     * @param newHypoths  The array of the hypotheses.
     */
    public synchronized void setHypoths (PlayerLocalizeHypoth[] newHypoths) {
        this.hypoths = newHypoths;
    }
}