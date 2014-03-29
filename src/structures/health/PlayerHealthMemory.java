/*
 *  Player Java Client 3 - PlayerHealthMemory
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
 * $Id: PlayerHealthMemory.java 153 2011-08-22 14:45:20Z corot $
 *
 */

package javaclient3.structures.health;

import javaclient3.structures.*;

/**
 * Structure describing the memory usage.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerHealthMemory implements PlayerConstants {

    // Total memory.
    private long total;
    // Used memory.
    private long used;
    //Free memory.
    private long free;


    /**
     * @return  The current total memory
     */
    public synchronized long getTotal() {
        return total;
    }

    /**
     * @param newTotal  The current total memory
     */
    public synchronized void setTotal(long newTotal) {
        this.total = newTotal;
    }

    /**
     * @return  The current used memory
     */
    public synchronized long getUsed() {
        return used;
    }

    /**
     * @param newUsed  The current used memory
     */
    public synchronized void setUsed(long newUsed) {
        this.used = newUsed;
    }

    /**
     * @return  The current free memory
     */
    public synchronized long getFree() {
        return free;
    }

    /**
     * @param newFree  The current free memory
     */
    public synchronized void setFree(long newFree) {
        this.free = newFree;
    }
}