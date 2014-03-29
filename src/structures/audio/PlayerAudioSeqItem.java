/*
 *  Player Java Client 3 - PlayerAudioSeqItem.java
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
 * $Id: PlayerAudioSeqItem.java 167 2011-11-16 04:31:29Z corot $
 *
 */

package javaclient3.structures.audio;

import javaclient3.structures.*;

/**
 * Player audio sequence item.
 * This class describes a single audio sequence element. The link field is used
 * for chord type playback when a series of notes are to be played together. Set
 * link to true for all but the last notes to be player together.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerAudioSeqItem implements PlayerConstants {

    /** Frequency of the note [Hz]. */
    private float frequency;
    /** Duration of the note [s]. */
    private float duration;
    /** Amplitude of the note [Db]. */
    private float amplitude;
    /** Link to next note, true to play both notes
     *  together (or if both were received together). */
    private boolean link;


    /**
     * @return  Frequency of the note [Hz].
     */
    public synchronized float getFrequency () {
        return frequency;
    }

    /**
     * @param newFrequency  Frequency of the note [Hz].
     */
    public synchronized void setFrequency (float newFrequency) {
        this.frequency = newFrequency;
    }

    /**
     * @return  Duration of the note [s].
     */
    public synchronized float getDuration () {
        return duration;
    }

    /**
     * @param newDuration  Duration of the note [s].
     */
    public synchronized void setDuration (float newDuration) {
        this.duration = newDuration;
    }

    /**
     * @return  Amplitude of the note [Db].
     */
    public synchronized float getAmplitude () {
        return amplitude;
    }

    /**
     * @param newAmplitude  Amplitude of the note [Db].
     */
    public synchronized void setAmplitude (float newAmplitude) {
        this.amplitude = newAmplitude;
    }

    /**
     * @return  Link to next note, true to play both notes
     * together (or if both were received together).
     */
    public synchronized boolean getLink () {
        return link;
    }

    /**
     * @param newLink  Link to next note, true to play both
     * notes together (or if both were received together).
     */
    public synchronized void setLink (boolean newLink) {
        this.link = newLink;
    }
}