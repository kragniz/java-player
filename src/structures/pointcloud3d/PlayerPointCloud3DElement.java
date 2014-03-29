/*
 *  Player Java Client 3 - PlayerPointCloud3DElement.java
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
 * $Id: PlayerPointCloud3DElement.java 167 2011-11-16 04:31:29Z corot $
 *
 */

package javaclient3.structures.pointcloud3d;

import javaclient3.structures.*;

/**
 * 3D Pointcloud element structure.
 * An element as stored in a 3D pointcloud, containing a 3D position
 * plus other corresponding information.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerPointCloud3DElement implements PlayerConstants {

    PlayerPoint3d point;
    PlayerColor   color;

    /**
     * @return 3D position of the point.
     */
    public synchronized PlayerPoint3d getPoint () {
        return this.point;
    }

    /**
     * @param newPoint New 3D position for the point.
     */
    public synchronized void setPoint (PlayerPoint3d newPoint) {
        this.point = newPoint;
    }

    /**
     * @return Point's color.
     */
    public synchronized PlayerColor getColor () {
        return this.color;
    }

    /**
     * @param newColor New point's color.
     */
    public synchronized void setColor (PlayerColor newColor) {
        this.color = newColor;
    }
}