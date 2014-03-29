/*
 *  Player Java Client 3 - PlayerHealthCpu
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
 * $Id: PlayerHealthCpu.java 153 2011-08-22 14:45:20Z corot $
 *
 */

package javaclient3.structures.health;

import javaclient3.structures.*;

/**
 * Structure describing the CPU load.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerHealthCpu implements PlayerConstants {

    // The idle CPU load.
    private float idle;
    // The system CPU load.
    private float system;
    // The user's CPU load.
    private float user;


    /**
     * @return The idle CPU load
     */
    public float getIdle() {
        return idle;
    }

    /**
     * @param newIdle The idle CPU load
     */
    public void setIdle(float newIdle) {
        this.idle = newIdle;
    }

    /**
     * @return The system CPU load
     */
    public float getSystem() {
        return system;
    }

    /**
     * @param newSystem The system CPU load
     */
    public void setSystem(float newSystem) {
        this.system = newSystem;
    }

    /**
     * @return The user CPU load
     */
    public float getUser() {
        return user;
    }

    /**
     * @param newUser The user CPU load
     */
    public void setUser(float newUser) {
        this.user = newUser;
    }
}