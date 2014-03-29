/*
 *  Player Java Client 3 - PlayerRfidTag.java
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
 * $Id: PlayerRfidTag.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.rfid;

import javaclient3.structures.*;

/**
 * Structure describing a single RFID tag.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerRfidTag implements PlayerConstants {

    // Tag type.
    private int type;
    // The Globally Unique IDentifier (GUID) of the tag.
    private byte[] guid;


    /**
     * @return Tag type.
     */
    public synchronized int getType () {
        return this.type;
    }

    /**
     * @param newType Tag type.
     */
    public synchronized void setType (int newType) {
        this.type = newType;
    }

    /**
     * @return GUID count.
     */
    public synchronized int getGuid_count () {
        return (this.guid == null)?0:guid.length;
    }

    /**
     * @return The Globally Unique IDentifier (GUID) of the tag.
     */
    public synchronized byte[] getGuid () {
        return this.guid;
    }

    /**
     * @param newGuid The Globally Unique IDentifier (GUID) of the tag.
     */
    public synchronized void setGuid (byte[] newGuid) {
        this.guid = newGuid;
    }
}