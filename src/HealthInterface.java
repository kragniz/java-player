/*
 *  Player Java Client 3 - HealthInterface.java
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
 * $Id: HealthInterface.java 69 2006-06-30 09:10:43Z veedee $
 *
 */
package javaclient3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaclient3.structures.PlayerMsgHdr;
import javaclient3.structures.health.PlayerHealthData;
import javaclient3.structures.health.PlayerHealthCpu;
import javaclient3.structures.health.PlayerHealthMemory;
import javaclient3.xdr.OncRpcException;
import javaclient3.xdr.XdrBufferDecodingStream;

/**
 * The health interface allows for a user to get general systems data concerning
 * a specific robot. Allows a user to look at CPU and memory usage of the robot.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class HealthInterface extends PlayerDevice {

    private static final boolean isDebugging = PlayerClient.isDebugging;

    // Logging support
    private Logger logger = Logger.getLogger (HealthInterface.class.getName ());

    private PlayerHealthData phdata;
    private boolean          readyPhdata = false;


    /**
     * Constructor for HealthInterface.
     * @param pc a reference to the PlayerClient object
     */
    public HealthInterface (PlayerClient pc) { super (pc); }

    /**
     * Read health values.
     */
    public synchronized void readData (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype ()) {
                case PLAYER_HEALTH_DATA_STATE: {
                    this.timestamp = header.getTimestamp();

                    phdata = new PlayerHealthData ();
                    PlayerHealthCpu    cpu  = new PlayerHealthCpu    ();
                    PlayerHealthMemory mem  = new PlayerHealthMemory ();
                    PlayerHealthMemory swap = new PlayerHealthMemory ();

                    // Buffer for reading health data (3*4 + 3*8 + 3*8 = 60)
                    byte[] buffer = new byte[60];

                    // Read health data
                    is.readFully (buffer, 0, 60);

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();
                    cpu.setIdle   (xdr.xdrDecodeFloat ());
                    cpu.setSystem (xdr.xdrDecodeFloat ());
                    cpu.setUser   (xdr.xdrDecodeFloat ());
                    mem.setTotal  (xdr.xdrDecodeLong  ());
                    mem.setUsed   (xdr.xdrDecodeLong  ());
                    mem.setFree   (xdr.xdrDecodeLong  ());
                    swap.setTotal (xdr.xdrDecodeLong  ());
                    swap.setUsed  (xdr.xdrDecodeLong  ());
                    swap.setFree  (xdr.xdrDecodeLong  ());
                    xdr.endDecoding   ();
                    xdr.close ();

                    phdata.setCpu  (cpu);
                    phdata.setMem  (mem);
                    phdata.setSwap (swap);

                    readyPhdata = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new PlayerException
                ("[Gripper] : Error reading payload: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Gripper] : Error while XDR-decoding payload: " +
                        e.toString(), e);
        }
    }

    /**
     * Get health data.
     * @return an object of type PlayerHealthData containing the requested data
     */
    public PlayerHealthData getData () { return this.phdata; }

    /**
     * Check if health data is available.
     * @return true if ready, false if not ready
     */
    public boolean isDataReady () {
        if (readyPhdata) {
            readyPhdata = false;
            return true;
        }
        return false;
    }
}

