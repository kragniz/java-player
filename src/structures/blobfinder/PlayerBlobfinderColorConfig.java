/*
 *  Player Java Client 3 - PlayerBlobfinderColorConfig.java
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
 * $Id: PlayerBlobfinderColorConfig.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.blobfinder;

import javaclient3.structures.*;

/**
 * Request/reply: Set tracking color.
 * For some sensors (ie CMUcam), simple blob tracking tracks only one color.
 * To set the tracking color, send a PLAYER_BLOBFINDER_REQ_SET_COLOR request
 * with the format below, including the RGB color ranges (max and min).
 * Values of -1 will cause the track color to be automatically set to the
 * current window color. This is useful for setting the track color by
 * holding the tracking object in front of the lens. Null response.
 * <br>
 * For devices that can track multiple colors, channel attribute indicates
 * which color channel we are defining with this structure.
 * Single channel devices will ignore this field.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerBlobfinderColorConfig implements PlayerConstants {

    // Color channel defined on this structure
    private int channel;
    // RGB minimum and max values (0-255)
    private int rmin;
    // RGB minimum and max values (0-255)
    private int rmax;
    // RGB minimum and max values (0-255)
    private int gmin;
    // RGB minimum and max values (0-255)
    private int gmax;
    // RGB minimum and max values (0-255)
    private int bmin;
    // RGB minimum and max values (0-255)
    private int bmax;


    /**
     * @return  Color channel defined on this structure
     */
    public synchronized int getChannel () {
        return this.channel;
    }

    /**
     * @param newChannel  Color channel defined on this structure
     */
    public synchronized void setChannel (int newChannel) {
        this.channel = newChannel;
    }

    /**
     * @return  Red minimum value (0-255)
     */
    public synchronized int getRmin () {
        return this.rmin;
    }

    /**
     * @param newRmin  Red minimum value (0-255)
     */
    public synchronized void setRmin (int newRmin) {
        this.rmin = newRmin;
    }

    /**
     * @return  Red maximum value (0-255)
     */
    public synchronized int getRmax () {
        return this.rmax;
    }

    /**
     * @param newRmax   Red maximum value (0-255)
     */
    public synchronized void setRmax (int newRmax) {
        this.rmax = newRmax;
    }

    /**
     * @return  Green minimum (0-255)
     */
    public synchronized int getGmin () {
        return this.gmin;
    }

    /**
     * @param newGmin  Green minimum value (0-255)
     */
    public synchronized void setGmin (int newGmin) {
        this.gmin = newGmin;
    }

    /**
     * @return  Green maximum value (0-255)
     */
    public synchronized int getGmax () {
        return this.gmax;
    }

    /**
     * @param newGmax  Green maximum value (0-255)
     */
    public synchronized void setGmax (int newGmax) {
        this.gmax = newGmax;
    }

    /**
     * @return  Blue minimum value (0-255)
     */
    public synchronized int getBmin () {
        return this.bmin;
    }

    /**
     * @param newBmin  Blue minimum value (0-255)
     */
    public synchronized void setBmin (int newBmin) {
        this.bmin = newBmin;
    }

    /**
     * @return  Blue maximum value (0-255)
     */
    public synchronized int getBmax () {
        return this.bmax;
    }

    /**
     * @param newBmax  Blue maximum value (0-255)
     */
    public synchronized void setBmax (int newBmax) {
        this.bmax = newBmax;
    }

}
