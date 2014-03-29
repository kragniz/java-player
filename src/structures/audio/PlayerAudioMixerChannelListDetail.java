/*
 *  Player Java Client 3 - PlayerAudioMixerChannelListDetail.java
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
 * $Id: PlayerAudioMixerChannelListDetail.java 167 2011-11-16 04:31:29Z corot $
 *
 */

package javaclient3.structures.audio;

import javaclient3.structures.*;

/**
 * Player mixer channels detail. Describes a set of mixer channels.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerAudioMixerChannelListDetail implements PlayerConstants {

    /** List of channel descriptors. */
    private PlayerAudioMixerChannelDetail[] details;
    /** Default output channel (-1 for none). */
    private int default_output;
    /** Default input channel (-1 for none). */
    private int default_input;


    /**
     * @return  Number of channels in list.
     */
    public synchronized int getDetails_count () {
        return (this.details == null)?0:details.length;
    }

    /**
     * @return  List of channel descriptors.
     */
    public synchronized PlayerAudioMixerChannelDetail[] getDetails () {
        return details;
    }

    /**
     * @param newDetails  List of channel descriptors.
     */
    public synchronized void setDetails (PlayerAudioMixerChannelDetail[] newDetails) {
        this.details = newDetails;
    }

    /**
     * @return  Default output channel (-1 for none).
     */
    public synchronized int getDefaultOutput () {
        return default_output;
    }

    /**
     * @param newDefOutput  Default output channel (-1 for none).
     */
    public synchronized void setDefaultOutput (int newDefOutput) {
        this.default_output = newDefOutput;
    }

    /**
     * @return  Default input channel (-1 for none).
     */
    public synchronized int getDefaultInput () {
        return default_input;
    }

    /**
     * @param newDefInput  Default input channel (-1 for none).
     */
    public synchronized void setDefaultInput (int newDefInput) {
        this.default_input = newDefInput;
    }
}