/*
 *  Player Java Client 3 - PlayerSegment.java
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
 * $Id: PlayerSegment.java 130 2011-03-30 00:03:02Z corot $
 *
 */
package javaclient3.structures;

/**
 * A line segment, used to construct vector-based maps.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerSegment {
    private double x0;        // Endpoints [m]
    private double y0;        // Endpoints [m]
    private double x1;        // Endpoints [m]
    private double y1;        // Endpoints [m]


    /**
     * @return Endpoints [m]
     */
    public synchronized double getX0 () {
        return this.x0;
    }

    /**
     * @param newX0 Endpoints [m]
     */
    public synchronized void setX0 (double newX0) {
        this.x0 = newX0;
    }

    /**
     * @return Endpoints [m]
     */
    public synchronized double getY0 () {
        return this.y0;
    }

    /**
     * @param newY0 Endpoints [m]
     */
    public synchronized void setY0 (double newY0) {
        this.y0 = newY0;
    }

    /**
     * @return Endpoints [m]
     */
    public synchronized double getX1 () {
        return this.x1;
    }

    /**
     * @param newX1 Endpoints [m]
     */
    public synchronized void setX1 (double newX1) {
        this.x1 = newX1;
    }

    /**
     * @return Endpoints [m]
     */
    public synchronized double getY1 () {
        return this.y1;
    }

    /**
     * @param newY1 Endpoints [m]
     */
    public synchronized void setY1 (double newY1) {
        this.y1 = newY1;
    }
}