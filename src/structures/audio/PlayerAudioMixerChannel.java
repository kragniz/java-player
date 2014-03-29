/*
 *  Player Java Client 3 - PlayerAudioMixerChannel.java
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
 * $Id: PlayerAudioMixerChannel.java 167 2011-11-16 04:31:29Z corot $
 *
 */

package javaclient3.structures.audio;

import javaclient3.structures.*;

/**
 * Player mixer channel. Describes the state of a mixer channel.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerAudioMixerChannel implements PlayerConstants {

    /** Channel level (normalised 0 to 1). */
    private float amplitude;
    /** Active channel (set to false to mute channel). */
    private boolean active;
    /** Channel index. */
    private int index;


    /**
     * @return  Channel level (normalised 0 to 1).
     */
    public synchronized float getAmplitude () {
        return amplitude;
    }

    /**
     * @param newAmplitude  Channel level (normalised 0 to 1).
     */
    public synchronized void setAmplitude (float newAmplitude) {
        this.amplitude = newAmplitude;
    }

    /**
     * @return  Active channel (false means muted channel).
     */
    public synchronized boolean getActive () {
        return active;
    }

    /**
     * @param newActive  Active channel (set to false to mute channel).
     */
    public synchronized void setActive (boolean newActive) {
        this.active = newActive;
    }

    /**
     * @return  Channel index.
     */
    public synchronized int getIndex () {
        return index;
    }

    /**
     * @param newIndex  Channel index.
     */
    public synchronized void setIndex (int newIndex) {
        this.index = newIndex;
    }
}