/*
 *  Player Java Client 3 - JoystickInterface.java
 *  Copyright (C) 2002-2006 Radu Bogdan Rusu, Maxim Batalin
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
 * $Id: JoystickInterface.java 125 2011-03-24 02:24:05Z corot $
 *
 */
package javaclient3;

import java.io.IOException;

import javaclient3.structures.PlayerMsgHdr;
import javaclient3.structures.joystick.PlayerJoystickData;
import javaclient3.xdr.OncRpcException;
import javaclient3.xdr.XdrBufferDecodingStream;

/**
 * The joystick interface provides access to the state of a joystick. It allows
 * another driver or a (possibly off-board) client to read and use the state of
 * a joystick.
 * <br>
 * This interface accepts no commands or configuration requests.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class JoystickInterface extends PlayerDevice {

    private PlayerJoystickData pjdata;
    private boolean            readyPjdata = false;


    /**
     * Constructor for JoystickInterface.
     * @param pc a reference to the PlayerClient object
     */
    public JoystickInterface (PlayerClient pc) { super(pc); }

    /**
     * The joystick data packet, which contains the current state of the joystick.
     */
    public synchronized void readData (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype ()) {
                case PLAYER_JOYSTICK_DATA_STATE: {
                    this.timestamp = header.getTimestamp();

                    pjdata = new PlayerJoystickData ();

                    // Buffer for reading pos (8 integer values), scale (8 integer
                    // values), buttons and axes count (32 + 32 + 4 + 4 = 72)
                    byte[] buffer = new byte[72];
                    // Read pos [8], scale [8], buttons, axes_count
                    is.readFully (buffer, 0, 72);

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();
                    for (int i = 0; i < PLAYER_JOYSTICK_MAX_AXES; i++)
                        pjdata.setPos   (i, xdr.xdrDecodeInt ());
                    for (int i = 0; i < PLAYER_JOYSTICK_MAX_AXES; i++)
                        pjdata.setScale (i, xdr.xdrDecodeInt ());
                    pjdata.setButtons   (xdr.xdrDecodeInt ());
                    pjdata.setAxesCount (xdr.xdrDecodeInt ());
                    xdr.endDecoding   ();
                    xdr.close ();

                    readyPjdata = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new PlayerException
                ("[Joystick] : Error reading payload: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Joystick] : Error while XDR-decoding payload: " +
                        e.toString(), e);
        }
    }

    /**
     * Returns the joystick data (pos [8], scale [8], buttons, axes_count)
     * @return the joystick data
     */
    public synchronized PlayerJoystickData getData () { return pjdata; }

    /**
     * Check if data is available.
     * @return true if ready, false if not ready
     */
    public boolean isDataReady () {
        if (readyPjdata) {
            readyPjdata = false;
            return true;
        }
        return false;
    }
}
