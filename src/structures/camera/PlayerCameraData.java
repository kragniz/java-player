/*
 *  Player Java Client 3 - PlayerCameraData.java
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
 * $Id: PlayerCameraData.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.camera;

import javaclient3.structures.*;

/**
 * Data: state (PLAYER_CAMERA_DATA_STATE)
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerCameraData implements PlayerConstants {

    // Image dimensions [pixels].
    private int width;
    // Image dimensions [pixels].
    private int height;
    // Image bits-per-pixel (8, 16, 24, 32).
    private int bpp;
    // Image format (must be compatible with depth).
    private int format;
    // Some images (such as disparity maps) use scaled pixel values;
    // for these images, fdiv specifies the scale divisor (i.e., divide
    // the integer pixel value by fdiv to recover the real pixel value).
    private int fdiv;
    // Image compression; @ref PLAYER_CAMERA_COMPRESS_RAW indicates no
    // compression.
    private int compression;
    // Compressed image data (byte-aligned, row major order).
    // Multi-byte image formats (such as MONO16) must be converted
    // to network byte ordering.
    private byte[] image;


    /**
     * @return  Image dimensions [pixels].
     */
    public synchronized int getWidth () {
        return this.width;
    }

    /**
     * @param newWidth  Image dimensions [pixels].
     */
    public synchronized void setWidth (int newWidth) {
        this.width = newWidth;
    }

    /**
     * @return  Image dimensions [pixels].
     */
    public synchronized int getHeight () {
        return this.height;
    }

    /**
     * @param newHeight  Image dimensions [pixels].
     */
    public synchronized void setHeight (int newHeight) {
        this.height = newHeight;
    }

    /**
     * @return  Image bits-per-pixel (8, 16, 24, 32).
     */
    public synchronized int getBpp () {
        return this.bpp;
    }

    /**
     * @param newBpp  Image bits-per-pixel (8, 16, 24, 32).
     */
    public synchronized void setBpp (int newBpp) {
        this.bpp = newBpp;
    }

    /**
     * @return  Image format (must be compatible with depth).
     */
    public synchronized int getFormat () {
        return this.format;
    }

    /**
     * @param newFormat  Image format (must be compatible with depth).
     */
    public synchronized void setFormat (int newFormat) {
        this.format = newFormat;
    }

    /**
     * @return  Some images (such as disparity maps) use scaled pixel values;
     *       for these images, fdiv specifies the scale divisor (i.e., divide
     *       the integer pixel value by fdiv to recover the real pixel value).
     */
    public synchronized int getFdiv () {
        return this.fdiv;
    }

    /**
     * @param newFdiv  Some images (such as disparity maps) use scaled pixel values;
     *       for these images, fdiv specifies the scale divisor (i.e., divide
     *       the integer pixel value by fdiv to recover the real pixel value).
     */
    public synchronized void setFdiv (int newFdiv) {
        this.fdiv = newFdiv;
    }

    /**
     * @return  Image compression; @ref PLAYER_CAMERA_COMPRESS_RAW indicates no
     *       compression.
     */
    public synchronized int getCompression () {
        return this.compression;
    }

    /**
     * @param newCompression  Image compression; @ref PLAYER_CAMERA_COMPRESS_RAW
     *       indicates no compression.
     */
    public synchronized void setCompression (int newCompression) {
        this.compression = newCompression;
    }

    /**
     * @return  Size of image data as stored in image buffer (bytes).
     */
    public synchronized int getImage_count () {
        return (this.image == null)?0:this.image.length;
    }

    /**
     * @return  Compressed image data (byte-aligned, row major order).
     *       Multi-byte image formats (such as MONO16) must be converted
     *       to network byte ordering.
     */
    public synchronized byte[] getImage () {
        return this.image;
    }

    /**
     * @param newImage  Compressed image data (byte-aligned, row major order).
     *       Multi-byte image formats (such as MONO16) must be converted
     *       to network byte ordering.
     */
    public synchronized void setImage (byte[] newImage) {
        this.image = newImage;
    }
}