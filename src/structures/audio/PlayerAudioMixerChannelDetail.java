/*
 *  Player Java Client 3 - PlayerAudioMixerChannelDetail.java
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
 * $Id: PlayerAudioMixerChannelDetail.java 167 2011-11-16 04:31:29Z corot $
 *
 */

package javaclient3.structures.audio;

import javaclient3.structures.*;

/**
 * Player mixer channel detail. Describes a mixer channel.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerAudioMixerChannelDetail implements PlayerConstants {

    /** Descriptive channel name. */
    private String name;
    /** Channel type (input, output or special). */
    private byte   caps;


    /**
     * @return  Name length.
     */
    public synchronized int getName_count () {
        return (this.name == null)?0:name.length();
    }

    /**
     * @return  Descriptive channel name.
     */
    public synchronized String getName () {
        return name;
    }

    /**
     * @param newName  Descriptive channel name.
     */
    public synchronized void setName (String newName) {
        this.name = newName;
    }

    /**
     * @return  Channel type (input, output or special).
     */
    public synchronized byte getCaps () {
        return caps;
    }

    /**
     * @param newCaps  Channel type (input, output or special).
     */
    public synchronized void setCaps (byte newCaps) {
        this.caps = newCaps;
    }
}