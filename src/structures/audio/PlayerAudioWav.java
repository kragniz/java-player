/*
 *  Player Java Client 3 - PlayerAudioWav.java
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
 * $Id: PlayerAudioWav.java 167 2011-11-16 04:31:29Z corot $
 *
 */

package javaclient3.structures.audio;

import javaclient3.structures.*;

/**
 * Raw audio data. This data is used in the PLAYER_AUDIO_CMD_WAV_PLAY, and
 * returned as PLAYER_AUDIO_DATA_WAV_REC when stream recording is enabled.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerAudioWav implements PlayerConstants {

    /** Raw data. */
    private byte[] data;
    /** Raw data format. */
    private int format;


    /**
     * @return  Raw data format.
     */
    public synchronized int getFormat () {
        return this.format;
    }

    /**
     * @param newFormat  Raw data format.
     */
    public synchronized void setFormat (int newFormat) {
        this.format = newFormat;
    }

    /**
     * @return  Samples - the number of bytes of raw data.
     */
    public synchronized int getData_count () {
        return (this.data == null)?0:data.length;
    }

    /**
     * @return  Data - an array of raw data.
     */
    public synchronized byte[] getData () {
        return this.data;
    }

    /**
     * @param newData  Data - an array of raw data.
     */
    public synchronized void setData (byte[] newData) {
        this.data = newData;
    }
}