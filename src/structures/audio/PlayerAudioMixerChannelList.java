/*
 *  Player Java Client 3 - PlayerAudioMixerChannelList.java
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
 * $Id: PlayerAudioMixerChannelList.java 167 2011-11-16 04:31:29Z corot $
 *
 */

package javaclient3.structures.audio;

import javaclient3.structures.*;

/**
 * Player mixer channels (PLAYER_AUDIO_DATA_MIXER_CHANNEL).
 * Describes the state of a set of mixer channels.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerAudioMixerChannelList implements PlayerConstants {

    /** The channels list. */
    private PlayerAudioMixerChannel[] channels;


    /**
     * @return  Number of channels.
     */
    public synchronized int getChannels_count () {
        return (this.channels == null)?0:channels.length;
    }

    /**
     * @return  The channels list.
     */
    public synchronized PlayerAudioMixerChannel[] getChannels () {
        return this.channels;
    }

    /**
     * @param newChannels  The channels list.
     */
    public synchronized void setChannels (PlayerAudioMixerChannel[] newChannels) {
        this.channels = newChannels;
    }
}