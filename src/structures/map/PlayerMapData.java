/*
 *  Player Java Client 3 - PlayerMapData.java
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
 * $Id: PlayerMapData.java 136 2011-05-04 00:11:57Z corot $
 *
 */

package javaclient3.structures.map;

import javaclient3.structures.*;

/**
 * Request/reply: get grid map tile
 * To request a grid map tile, send a PLAYER_MAP_REQ_GET_DATA request with
 * the tile origin and size you want.  Set data_count to 0 and leave the
 * data field empty.  The response will contain origin, size, and occupancy
 * data for a tile.  Note that the response tile may not be exactly the
 * same as the tile you requested (e.g., your requested tile is too large
 * or runs off the map).
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerMapData implements PlayerConstants {

    // The tile origin [pixels].
    private int col;
    // The tile origin [pixels].
    private int row;
    // The size of the tile [pixels].
    private int width;
    // The size of the tile [pixels].
    private int height;
    // Maximum/minimum value for each cell:
    //   (-range <= EMPTY < 0, unknown = 0, 0 < OCCUPIED <= range).
    private byte dataRange;
    // Cell occupancy value (empty = -1, unknown = 0, occupied = +1).
    private byte[] data;


    /**
     * @return  The tile origin [pixels].
     */
    public synchronized int getCol () {
        return this.col;
    }

    /**
     * @param newCol  The tile origin [pixels].
     */
    public synchronized void setCol (int newCol) {
        this.col = newCol;
    }

    /**
     * @return  The tile origin [pixels].
     */
    public synchronized int getRow () {
        return this.row;
    }

    /**
     * @param newRow  The tile origin [pixels].
     */
    public synchronized void setRow (int newRow) {
        this.row = newRow;
    }

    /**
     * @return  The size of the tile [pixels].
     */
    public synchronized int getWidth () {
        return this.width;
    }

    /**
     * @param newWidth  The size of the tile [pixels].
     */
    public synchronized void setWidth (int newWidth) {
        this.width = newWidth;
    }

    /**
     * @return  The size of the tile [pixels].
     */
    public synchronized int getHeight () {
        return this.height;
    }

    /**
     * @param newHeight  The size of the tile [pixels].
     */
    public synchronized void setHeight (int newHeight) {
        this.height = newHeight;
    }

    /**
     * @return  The number of cells
     */
    public synchronized int getData_count () {
        return (this.data == null)?0:data.length;
    }

    /**
     * @return  Cell occupancy value (empty = -1, unknown = 0, occupied = +1).
     */
    public synchronized byte[] getData () {
        return this.data;
    }

    /**
     * @param newData  Cell occupancy value (empty = -1, unknown = 0, occupied = +1).
     */
    public synchronized void setData (byte[] newData) {
        this.data = newData;
    }

    /**
     * @return Maximum/minimum value for each cell
     */
    public synchronized byte getDataRange() {
        return dataRange;
    }

    /**
     * @param newDataRange Maximum/minimum value for each cell
     */
    public synchronized void setDataRange(byte newDataRange) {
        this.dataRange = newDataRange;
    }
}