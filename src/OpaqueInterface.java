/*
 *  Player Java Client 3 - OpaqueInterface.java
 *  Copyright (C) 2011 Dustin Webb
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
 * $Id: OpaqueInterface.java 167 2011-11-16 04:31:29Z corot $
 *
 */
package javaclient3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaclient3.structures.PlayerMsgHdr;
import javaclient3.structures.opaque.PlayerOpaqueData;
import javaclient3.xdr.OncRpcException;
import javaclient3.xdr.XdrBufferDecodingStream;
import javaclient3.xdr.XdrBufferEncodingStream;

/**
 * A generic interface for user-defined messages.
 * The opaque interface allows you to send user-specified messages. With
 * this interface a user can send custom commands to their drivers/plugins.
 * @author Dustin Webb
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class OpaqueInterface extends PlayerDevice {

    private static final boolean isDebugging = PlayerClient.isDebugging;

    // Logging support
    private Logger logger = Logger.getLogger (OpaqueInterface.class.getName ());

	private PlayerOpaqueData podata;
	private boolean          readyPodata = false;


    /**
     * Constructor for OpaqueInterface.
     * @param pc A reference to the PlayerClient object.
     */
    public OpaqueInterface (PlayerClient pc) { super (pc); }

    /**
     * Read the data packet.
     */
    public synchronized void readData (PlayerMsgHdr header) {
    	super.readData(header);

    	try {
    		switch (header.getSubtype()) {
    			case PLAYER_OPAQUE_DATA_STATE: {
    				readData ();
    			    break;
    			}
    		}
		} catch (IOException e) {
			throw new PlayerException
            ("[Opaque] : Error reading payload: " +
                    e.toString(), e);
		} catch (OncRpcException e) {
            throw new PlayerException
            ("[Opaque] : Error while XDR-decoding payload: " +
                    e.toString(), e);
		}
    }

    /**
     * Returns the opaque data.
     * @return  A PlayerOpaqueData object containing a byte array.
     */
    public synchronized PlayerOpaqueData getData() { return podata; }

    /**
     * Check if data is available.
     * @return True if ready, false if not ready.
     */
    public boolean isDataReady () {
        if (readyPodata) {
            readyPodata = false;
            return true;
        }
        return false;
    }

    /**
     * Command subtype: generic command.
     * @param data  The data to send.
     */
    public void command (byte[] data) {
        try {
            sendData (data, PLAYER_OPAQUE_CMD_DATA);
        } catch (OncRpcException e) {
            throw new PlayerException
            ("[Opaque] : Couldn't send data command: " +
                    e.toString(), e);
        } catch (IOException e) {
            throw new PlayerException
            ("[Opaque] : Error while XDR-encoding data command: " +
                    e.toString(), e);
        }
    }

    /**
     * Request/reply: generic request.
     * @param data  The data to send.
     */
    public void request (byte[] data) {
        try {
            sendData (data, PLAYER_OPAQUE_REQ_DATA);
        } catch (OncRpcException e) {
            throw new PlayerException
            ("[Opaque] : Couldn't send data request: " +
                    e.toString(), e);
        } catch (IOException e) {
            throw new PlayerException
            ("[Opaque] : Error while XDR-encoding data request: " +
                    e.toString(), e);
        }
    }

    /**
     * Handle acknowledgement response messages.
     * @param header Player message header.
     */
    public void handleResponse (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype ()) {
                case PLAYER_OPAQUE_REQ_DATA: {
                    readData ();
                    break;
                }
                default:{
                    if (isDebugging)
                        logger.log (Level.FINEST, "[Opaque][Debug] : " +
                                "Unexpected response " + header.getSubtype () +
                                " of size = " + header.getSize ());
                    break;
                }
            }
        } catch (IOException e) {
            throw new PlayerException
                ("[Opaque] : Error reading payload: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[Opaque] : Error while XDR-decoding payload: " +
                        e.toString(), e);
        }
    }

    /**
     * Send user-defined data to the server.
     * @param data  The data to send.
     * @param subtype  Command or request.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    private void sendData (byte[] data, int subtype)
                   throws OncRpcException, IOException {
        sendHeader (PLAYER_MSGTYPE_CMD, subtype, 8 + data.length);
        XdrBufferEncodingStream xdr = new XdrBufferEncodingStream (8 + data.length);
        xdr.beginEncoding (null, 0);
        xdr.xdrEncodeInt (data.length);
        xdr.xdrEncodeInt (data.length);
        xdr.xdrEncodeOpaque (data);
        xdr.endEncoding ();
        os.write (xdr.getXdrData(), 0, xdr.getXdrLength());
        xdr.close ();
        os.flush ();
    }

    /**
     * Read user-defined data from the server.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    private void readData () throws OncRpcException, IOException {

        podata = new PlayerOpaqueData ();

        // Buffer for data_count
        byte[] buffer = new byte[8];
        // Read data_count; skip array_count
        is.readFully (buffer, 0, 8);

        // Begin decoding the XDR buffer
        XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
        xdr.beginDecoding ();
        int dataCount = xdr.xdrDecodeInt ();
        xdr.endDecoding ();
        xdr.close ();

        // Buffer for reading data
        buffer = new byte[dataCount];
        // Read generic data
        is.readFully (buffer, 0, dataCount);

        // Begin decoding the XDR buffer
        xdr = new XdrBufferDecodingStream (buffer);
        xdr.beginDecoding ();
        podata.setData (xdr.xdrDecodeOpaque (dataCount));
        xdr.endDecoding ();
        xdr.close ();

        readyPodata = true;
    }
}
