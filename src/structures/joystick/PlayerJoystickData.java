/*
 *  Player Java Client 3 - PlayerJoystickData.java
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
 * $Id: PlayerJoystickData.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.joystick;

import javaclient3.structures.*;

/**
 * Data: state (PLAYER_JOYSTICK_DATA_STATE)
 * The joystick data packet, which contains the current state of the
 * joystick
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerJoystickData implements PlayerConstants {

    // Current joystick position (unscaled)
    private int pos[] = new int[PLAYER_JOYSTICK_MAX_AXES];
    // Scaling factors
    private int scale[] = new int[PLAYER_JOYSTICK_MAX_AXES];
    // Button states (bitmask)
    private int buttons;
    // Number of axes
    private int axesCount;


    /**
     * @return  Current joystick positions (unscaled)
     */
    public synchronized int[] getPos () {
        return this.pos;
    }

    /**
     * @return  Current joystick position for the given axis (unscaled)
     */
    public synchronized int getPos (int axis) {
        return this.pos[axis];
    }

    /**
     * @param newPos  New joystick position for the given axis (unscaled)
     */
    public synchronized void setPos (int axis, int newPos) {
        this.pos[axis] = newPos;
    }

    /**
     * @return  Current joystick x-axis position (unscaled)
     */
    public synchronized int getXpos () {
        return this.pos[PLAYER_JOYSTICK_X_AXIS];
    }

    /**
     * @param newXpos  New joystick x-axis position (unscaled)
     */
    public synchronized void setXpos (int newXpos) {
        this.pos[PLAYER_JOYSTICK_X_AXIS] = newXpos;
    }

    /**
     * @return  Current joystick y-axis position (unscaled)
     */
    public synchronized int getYpos () {
        return this.pos[PLAYER_JOYSTICK_Y_AXIS];
    }

    /**
     * @param newYpos  New joystick y-axis position (unscaled)
     */
    public synchronized void setYpos (int newYpos) {
        this.pos[PLAYER_JOYSTICK_Y_AXIS] = newYpos;
    }

    /**
     * @return  Scaling factors
     */
    public synchronized int[] getScale () {
        return this.scale;
    }

    /**
     * @return  Scaling factor for the given axis
     */
    public synchronized int getScale (int axis) {
        return this.scale[axis];
    }

    /**
     * @param newScale  Scaling factor for the given axis
     */
    public synchronized void setScale (int axis, int newScale) {
        this.scale[axis] = newScale;
    }

    /**
     * @return  X-axis scaling factor
     */
    public synchronized int getXscale () {
        return this.scale[PLAYER_JOYSTICK_X_AXIS];
    }

    /**
     * @param newXscale  New x-axis scaling factor
     */
    public synchronized void setXscale (int newXscale) {
        this.scale[PLAYER_JOYSTICK_X_AXIS] = newXscale;
    }

    /**
     * @return  Y-axis scaling factor
     */
    public synchronized int getYscale () {
        return this.scale[PLAYER_JOYSTICK_Y_AXIS];
    }

    /**
     * @param newYscale  New y-axis scaling factor
     */
    public synchronized void setYscale (int newYscale) {
        this.scale[PLAYER_JOYSTICK_Y_AXIS] = newYscale;
    }

    /**
     * @return  Button states (bitmask)
     */
    public synchronized int getButtons () {
        return this.buttons;
    }

    /**
     * @param newButtons  Button states (bitmask)
     */
    public synchronized void setButtons (int newButtons) {
        this.buttons = newButtons;
    }

    /**
     * @return  Number of axes
     */
    public synchronized int getAxesCount () {
        return this.axesCount;
    }

    /**
     * @param newCount  Number of axes
     */
    public synchronized void setAxesCount (int newCount) {
        this.axesCount = newCount;
    }
}