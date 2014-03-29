/*
 *  Player Java Client 3 - PlayerWifiMacReq.java
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
 * $Id: PlayerWifiMacReq.java 167 2011-11-16 04:31:29Z corot $
 *
 */

package javaclient3.structures.wifi;

import javaclient3.structures.*;

/**
 * Request/reply:
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerWifiMacReq implements PlayerConstants {

    // MAC address.
    private byte[] mac;


    /**
     * @return  MAC address length.
     */
    public synchronized int getMac_count () {
        return (this.mac == null)?0:mac.length;
    }

    /**
     * @return  MAC address.
     */
    public synchronized byte[] getMac () {
        return this.mac;
    }

    /**
     * @param newMac  MAC address.
     */
    public synchronized void setMac (byte[] newMac) {
        this.mac = newMac;
    }
}