/*
 *  Player Java Client 3 - PlayerAudioSample.java
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
 * $Id: PlayerAudioSample.java 167 2011-11-16 04:31:29Z corot $
 *
 */

package javaclient3.structures.audio;

import javaclient3.structures.*;

/**
 * Player audio sample. Describes an audio sample. If the index is set to -1,
 * the next available slot is used and the index is returned in the response.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerAudioSample implements PlayerConstants {

    /** The audio sample data. */
    PlayerAudioWav sample;
    /** Index to store it at or retrieve from (-1 for next available where valid). */
    private int index;


    /**
     * @return  The audio sample data.
     */
    public synchronized PlayerAudioWav getSample() {
        return sample;
    }

    /**
     * @param newSample  The audio sample data.
     */
    public synchronized void setSample (PlayerAudioWav newSample) {
        this.sample = newSample;
    }

    /**
     * @return  Index to store it at or retrieve from (-1 for next available where valid).
     */
    public synchronized int getIndex () {
        return this.index;
    }

    /**
     * @param newIndex  Index to store it at or retrieve from (-1 for next available where
     *  valid).
     */
    public synchronized void setIndex (int newIndex) {
        this.index = newIndex;
    }
}