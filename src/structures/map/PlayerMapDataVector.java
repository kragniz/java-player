/*
 *  Player Java Client 3 - PlayerMapDataVector.java
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
 * $Id: PlayerMapDataVector.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.map;

import javaclient3.structures.*;

/**
 * Request/reply: get vector map
 * A vector map is represented as line segments.  To retrieve the vector map,
 * send a null PLAYER_MAP_REQ_GET_VECTOR request.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerMapDataVector implements PlayerConstants {

    // The minimum and maximum coordinates of all the line segments [meters]
    private float minx;
    // The minimum and maximum coordinates of all the line segments [meters]
    private float maxx;
    // The minimum and maximum coordinates of all the line segments [meters]
    private float miny;
    // The minimum and maximum coordinates of all the line segments [meters]
    private float maxy;
    // Line segments
    private PlayerSegment[] segments;


    /**
     * @return  The minimum and maximum coordinates of all the line segments [meters]
     */
    public synchronized float getMinx () {
        return this.minx;
    }

    /**
     * @param newMinx  The minimum and maximum coordinates of all the line segments [meters]
     */
    public synchronized void setMinx (float newMinx) {
        this.minx = newMinx;
    }

    /**
     * @return  The minimum and maximum coordinates of all the line segments [meters]
     */
    public synchronized float getMaxx () {
        return this.maxx;
    }

    /**
     * @param newMaxx  The minimum and maximum coordinates of all the line segments [meters]
     */
    public synchronized void setMaxx (float newMaxx) {
        this.maxx = newMaxx;
    }

    /**
     * @return  The minimum and maximum coordinates of all the line segments [meters]
     */
    public synchronized float getMiny () {
        return this.miny;
    }

    /**
     * @param newMiny  The minimum and maximum coordinates of all the line segments [meters]
     */
    public synchronized void setMiny (float newMiny) {
        this.miny = newMiny;
    }

    /**
     * @return  The minimum and maximum coordinates of all the line segments [meters]
     */
    public synchronized float getMaxy () {
        return this.maxy;
    }

    /**
     * @param newMaxy  The minimum and maximum coordinates of all the line segments [meters]
     */
    public synchronized void setMaxy (float newMaxy) {
        this.maxy = newMaxy;
    }

    /**
     * @return  The number of line segments
     */
    public synchronized int getSegments_count () {
        return (this.segments == null)?0:segments.length;
    }

    /**
     * @return  Line segments
     */
    public synchronized PlayerSegment[] getSegments () {
        return this.segments;
    }

    /**
     * @param newSegments  Line segments
     */
    public synchronized void setSegments (PlayerSegment[] newSegments) {
        this.segments = newSegments;
    }
}