/*
 *  Player Java Client 3 - PlayerPoint2d.java
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
 * $Id: PlayerPoint2d.java 130 2011-03-30 00:03:02Z corot $
 *
 */
package javaclient3.structures;

/**
 * A point in the plane.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerPoint2d {
    private double px;        // X [m]
    private double py;        // Y [m]


    /**
     * @return X [m]
     */
    public synchronized double getPx () {
        return this.px;
    }

    /**
     * @param newPx X [m]
     */
    public synchronized void setPx (double newPx) {
        this.px = newPx;
    }

    /**
     * @return Y [m]
     */
    public synchronized double getPy () {
        return this.py;
    }

    /**
     * @param newPy Y [m]
     */
    public synchronized void setPy (double newPy) {
        this.py = newPy;
    }

    @Override
    public String toString() {
        return "PlayerPoint2d { " + px + ", " + py + "}";
    }
}