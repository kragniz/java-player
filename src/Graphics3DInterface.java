/*
 *  Player Java Client 3 - Graphics3DInterface.java
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
 * $Id: Graphics3DInterface.java 145 2011-08-22 14:25:17Z corot $
 *
 */
package javaclient3;

import java.io.IOException;

import javaclient3.structures.PlayerMsgHdr;
import javaclient3.structures.graphics3d.PlayerGraphics3dCmdDraw;
import javaclient3.xdr.OncRpcException;
import javaclient3.xdr.XdrBufferEncodingStream;

/**
 * The graphics3d interface provides an interface to graphics devices. Drivers
 * can implement this interface to provide clients and other drivers with
 * graphics output.
 * The interface uses an openGL style of command where a type is specified
 * along with a series of verticies. The interpretation depends on the command
 * type.
 * Graphics items should be accumulated until an explicit clear command is
 * issued.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 * TODO: implement PLAYER_GRAPHICS3D_CMD_TRANSLATE, PLAYER_GRAPHICS3D_CMD_ROTATE,
 *       PLAYER_GRAPHICS3D_CMD_PUSH and PLAYER_GRAPHICS3D_CMD_POP commands.
 */
public class Graphics3DInterface extends PlayerDevice {

    /**
     * Constructor for Graphics3DInterface.
     * @param pc a reference to the PlayerClient object
     */
    public Graphics3DInterface (PlayerClient pc) { super(pc); }

    /**
     * This interface produces no data.
     */
    public synchronized void readData (PlayerMsgHdr header) { }

    /**
     * Requests: This interface accepts no requests.
     * @param header Player header
     */
    public void handleResponse (PlayerMsgHdr header) { }


    /**
     * Command: Clear screen (PLAYER_GRAPHICS3D_CMD_CLEAR).
     * <br><br>
     */
    public void clearScreen () {
        try {
            sendHeader (PLAYER_MSGTYPE_CMD, PLAYER_GRAPHICS3D_CMD_CLEAR, 0);
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Graphics3D] : Couldn't send clear screen command: " +
                        e.toString(), e);
        }
    }

    /**
     * Command: Draw. (PLAYER_GRAPHICS3D_CMD_DRAW).
     * <br><br>
     * @param pgcp a PlayerGraphics3dCmdDraw structure holding the required
     * data
     */
    public void draw (PlayerGraphics3dCmdDraw pgcp) {
        try {
            int points = pgcp.getCount ();
            if (points > 64) points = 64;
            int size = 4 + 4 + 4 + (points * 24) + 16;
            sendHeader
                (PLAYER_MSGTYPE_CMD, PLAYER_GRAPHICS2D_CMD_POINTS, size);
            XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (size);
            xdr.beginEncoding (null, 0);
            xdr.xdrEncodeInt   (pgcp.getDraw_mode ());
            xdr.xdrEncodeInt   (points);
            for (int i = 0; i < points; i++) {
                xdr.xdrEncodeDouble (pgcp.getPoints ()[i].getPx ());
                xdr.xdrEncodeDouble (pgcp.getPoints ()[i].getPy ());
                xdr.xdrEncodeDouble (pgcp.getPoints ()[i].getPz ());
            }
            xdr.xdrEncodeByte
                ((byte)(pgcp.getColor ().getAlpha () & 0x000000FF));
            xdr.xdrEncodeByte
                ((byte)(pgcp.getColor ().getRed   () & 0x000000FF));
            xdr.xdrEncodeByte
                ((byte)(pgcp.getColor ().getGreen () & 0x000000FF));
            xdr.xdrEncodeByte
                ((byte)(pgcp.getColor ().getBlue  () & 0x000000FF));
            xdr.endEncoding ();

            os.write (xdr.getXdrData (), 0, xdr.getXdrLength ());
            xdr.close ();
            os.flush ();
        } catch (IOException e) {
            throw new PlayerException
                ("[Graphics3D] : Couldn't send draw command: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Graphics3D] : Error while XDR-encoding draw command: " +
                        e.toString(), e);
        }
    }
}
