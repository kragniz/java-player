/*
 *  Player Java Client 3 - PlayerAudioState.java
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
 * $Id: PlayerAudioState.java 167 2011-11-16 04:31:29Z corot $
 *
 */

package javaclient3.structures.audio;

import javaclient3.structures.*;

/**
 * Player audio driver state. Describes the current state of the audio driver.
 * Usually only sent when state changes.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerAudioState implements PlayerConstants {

    /** The state of the driver: a bitmask of PLAYER_AUDIO_STATE_* values. */
    private int state;


    /**
     * @return  The state of the driver: a bitmask of PLAYER_AUDIO_STATE_* values.
     */
    public synchronized int getState () {
        return this.state;
    }

    /**
     * @param newState  The state of the driver: a bitmask of PLAYER_AUDIO_STATE_*
     *  values.
     */
    public synchronized void setState (int newState) {
        this.state = newState;
    }
}