/*
 *  Player Java Client 3 - PlayerHealthData
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
 * $Id: PlayerHealthData.java 153 2011-08-22 14:45:20Z corot $
 *
 */

package javaclient3.structures.health;

import javaclient3.structures.*;

/**
 * Data: state (PLAYER_HEALTH_DATA_STATE)
 * Structure describing the health's data packet, including CPU and memory usage.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerHealthData implements PlayerConstants {

    // The current CPU usage.
    private PlayerHealthCpu    cpu;
    // The physic memory stats.
    private PlayerHealthMemory mem;
    // The swap memory stats.
    private PlayerHealthMemory swap;


    /**
     * @return The current CPU usage
     */
    public synchronized PlayerHealthCpu getCpu() {
        return cpu;
    }

    /**
     * @param newCpu The new CPU usage
     */
    public synchronized void setCpu(PlayerHealthCpu newCpu) {
        this.cpu = newCpu;
    }

    /**
     * @return The current physic memory statistics
     */
    public synchronized PlayerHealthMemory getMem() {
        return mem;
    }

    /**
     * @param newMem The new physic memory statistics
     */
    public synchronized void setMem(PlayerHealthMemory newMem) {
        this.mem = newMem;
    }

    /**
     * @return The current swap memory statistics
     */
    public synchronized PlayerHealthMemory getSwap() {
        return swap;
    }

    /**
     * @param newSwap The new swap memory statistics
     */
    public synchronized void setSwap(PlayerHealthMemory newSwap) {
        this.swap = newSwap;
    }
}