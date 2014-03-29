/*
 *  Player Java Client 3 - PlayerAudioSeq.java
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
 * $Id: PlayerAudioSeq.java 167 2011-11-16 04:31:29Z corot $
 *
 */

package javaclient3.structures.audio;

import javaclient3.structures.*;

/**
 * Player audio sequence (PLAYER_AUDIO_DATA_SEQ).
 * Describes a sequence of notes to be played or which have been received.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerAudioSeq implements PlayerConstants {

    /** The tones sequence */
    private PlayerAudioSeqItem[] tones;


    /**
     * @return  Number of tones.
     */
    public synchronized int getTones_count () {
        return (this.tones == null)?0:tones.length;
    }

    /**
     * @return  The tones sequence.
     */
    public synchronized PlayerAudioSeqItem[] getTones () {
        return this.tones;
    }

    /**
     * @param newTones  The tones sequence.
     */
    public synchronized void setTones (PlayerAudioSeqItem[] newTones) {
        this.tones = newTones;
    }
}