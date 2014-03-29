/*
 *  Player Java Client 3 - PlayerOpaqueData.java
 *  Copyright (C) 2011 Dustin Webb
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
 * $Id: PlayerOpaqueData.java 50 2006-03-10 19:05:00Z veedee $
 *
 */
package javaclient3.structures.opaque;

import javaclient3.structures.*;

/**
 * The opaque interface returns a byte array.
 * @author Radu Bogdan Rusu, Dustin Webb
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerOpaqueData implements PlayerConstants {

    /** The data we send/receive */
    private byte[] data;


    /**
     * @return  Size of data as stored in buffer (bytes).
     */
    public synchronized int getData_count () {
        return (this.data == null)?0:data.length;
    }

    /**
     * @return  The data we send/receive
     */
    public synchronized byte[] getData () {
        return this.data;
    }

    /**
     * @param newData  The data we send/receive
     */
    public synchronized void setData (byte[] newData) {
        this.data = newData;
    }
}