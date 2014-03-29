/*
 *  Player Java Client 3 - PlayerActarrayActuator.java
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
 * $Id: PlayerActarrayActuator.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.actarray;

import javaclient3.structures.*;

/**
 * Structure containing a single actuator's information
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerActarrayActuator implements PlayerConstants {

    // The acceleration of the actuator in m/s^2 or rad/s^2 depending on the type.
    private float acceleration;
    // The current of the actuator in A.
    private float current;
    // The position of the actuator in m or rad depending on the type.
    private float position;
    // The speed of the actuator in m/s or rad/s depending on the type.
    private float speed;
    // The current state of the actuator.
    private byte state;


    /**
     * @return  The position of the actuator in m or rad depending on the type.
     */
    public synchronized float getPosition () {
        return this.position;
    }

    /**
     * @param newPosition  The position of the actuator in m or rad depending on the type.
     */
    public synchronized void setPosition (float newPosition) {
        this.position = newPosition;
    }

    /**
     * @return  The speed of the actuator in m/s or rad/s depending on the type.
     */
    public synchronized float getSpeed () {
        return this.speed;
    }

    /**
     * @param newSpeed  The speed of the actuator in m/s or rad/s depending on the type.
     */
    public synchronized void setSpeed (float newSpeed) {
        this.speed = newSpeed;
    }

    /**
     * @return  The current state of the actuator.
     */
    public synchronized byte getState () {
        return this.state;
    }

    /**
     * @param newState  The current state of the actuator.
     */
    public synchronized void setState (byte newState) {
        this.state = newState;
    }

    /**
     * @return  The acceleration of the actuator in m/s^2 or rad/s^2 depending on the type.
     */
    public synchronized float getAcceleration() {
        return acceleration;
    }

    /**
     * @param newAcceleration  The acceleration of the actuator in m/s^2 or rad/s^2 depending on the type.
     */
    public synchronized void setAcceleration(float newAcceleration) {
        this.acceleration = newAcceleration;
    }

    /**
     * @return  The current of the actuator in A.
     */
    public synchronized float getCurrent() {
        return current;
    }

    /**
     * @param newCurrent  The current of the actuator in A.
     */
    public synchronized void setCurrent(float newCurrent) {
        this.current = newCurrent;
    }
}