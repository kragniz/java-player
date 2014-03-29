/*
 *  Player Java Client 3 - PlayerStereoData.java
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
 * $Id: PlayerStereoData.java 167 2011-11-16 04:31:29Z corot $
 *
 */

package javaclient3.structures.stereo;

import javaclient3.structures.*;
import javaclient3.structures.camera.PlayerCameraData;

/**
 * Data: state (PLAYER_STEREO_DATA_STATE)
 * Set of data provided by a stereo camera, namely the left and right image
 * channels, the disparity image and the 3-D stereo point cloud.
 * @author Jorge Santos Simon
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerStereoData implements PlayerConstants {

    /** Left channel image. */
    PlayerCameraData                  lChannel;
    /** Right channel image. */
    PlayerCameraData                  rChannel;
    /** Disparity image. */
    PlayerCameraData                  disparity;
    /** 3-D stereo point cloud data. */
    PlayerPointCloud3dStereoElement[] points;
    /** Special mode flags: defines how images are being sent. */
    int                               mode;


    /**
     * @return  The left channel image.
     */
    public synchronized PlayerCameraData getLeftChannel() {
        return lChannel;
    }

    /**
     * @param newChannel  The new image for left channel.
     */
    public synchronized void setLeftChannel(PlayerCameraData newChannel) {
        this.lChannel = newChannel;
    }

    /**
     * @return  The right channel image.
     */
    public synchronized PlayerCameraData getRightChannel() {
        return rChannel;
    }

    /**
     * @param newChannel  The new image for right channel.
     */
    public synchronized void setRightChannel(PlayerCameraData newChannel) {
        this.rChannel = newChannel;
    }

    /**
     * @return  The disparity image.
     */
    public synchronized PlayerCameraData getDisparity() {
        return disparity;
    }

    /**
     * @param newDisparity  The disparity image.
     */
    public synchronized void setDisparity(PlayerCameraData newDisparity) {
        this.disparity = newDisparity;
    }

    /**
     * @return  The number of 3-D points.
     */
    public synchronized int getPoints_count() {
        return (this.points == null)?0:this.points.length;
    }

    /**
     * @return  The 3-D point cloud data.
     */
    public synchronized PlayerPointCloud3dStereoElement[] getPoints() {
        return points;
    }

    /**
     * @param newPoints  The new 3-D point cloud data.
     */
    public synchronized void setPoints(PlayerPointCloud3dStereoElement[] newPoints) {
        this.points = newPoints;
    }

    /**
     * @return  Special mode flags.
     */
    public synchronized int getMode() {
        return mode;
    }

    /**
     * @param newMode  Special mode flags.
     */
    public synchronized void setMode(int newMode) {
        this.mode = newMode;
    }
}