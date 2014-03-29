/*
 *  Player Java Client 3 - SpeechRecognitionInterface.java
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
 * $Id: SpeechRecognitionInterface.java 125 2011-03-24 02:24:05Z corot $
 *
 */
package javaclient3;

import java.io.IOException;

import javaclient3.structures.PlayerMsgHdr;
import javaclient3.structures.speechrecognition.PlayerSpeechRecognitionData;
import javaclient3.xdr.OncRpcException;
import javaclient3.xdr.XdrBufferDecodingStream;

/**
 * The speech recognition interface provides access to a speech recognition
 * server.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class SpeechRecognitionInterface extends PlayerDevice {

    private PlayerSpeechRecognitionData psrdata;
    private boolean                     readyPsrdata = false;


    /**
     * Constructor for SpeechRecognitionInterface.
     * @param pc a reference to the PlayerClient object
     */
    public SpeechRecognitionInterface (PlayerClient pc) { super(pc); }

    /**
     * Read the speech recognition data packet.
     */
    public synchronized void readData (PlayerMsgHdr header) {
        try {
            switch (header.getSubtype ()) {
                case SPEECH_RECOGNITION_DATA_STRING: {
                    this.timestamp = header.getTimestamp();

                    psrdata = new PlayerSpeechRecognitionData ();

                    // Buffer for reading text_count, array_count
                    byte[] buffer = new byte[8];
                    // Read text_count, array_count
                    is.readFully (buffer, 0, 8);

                    // Begin decoding the XDR buffer
                    XdrBufferDecodingStream xdr = new XdrBufferDecodingStream (buffer);
                    xdr.beginDecoding ();
                    int text_count = xdr.xdrDecodeInt ();
                    xdr.endDecoding   ();
                    xdr.close ();

                    buffer = new byte[text_count];
                    is.readFully (buffer, 0, text_count);
                    psrdata.setText (new String (buffer));

                    // Take care of the residual zero bytes
                    if ((psrdata.getText_count () % 4) != 0)
                        is.readFully (buffer, 0, 4 - (psrdata.getText_count () % 4));

                    readyPsrdata = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new PlayerException
                ("[SpeechRecognition] : Error reading payload: " +
                        e.toString(), e);
        } catch (OncRpcException e) {
            throw new PlayerException
                ("[SpeechRecognition] : Error while XDR-decoding payload: " +
                        e.toString(), e);
        }
    }

    /**
     * Get the data.
     * @return an object of type PlayerSpeechRecognitionData containing the requested data
     */
    public PlayerSpeechRecognitionData getData () { return this.psrdata; }

    /**
     * Check if data is available.
     * @return true if ready, false if not ready
     */
    public boolean isDataReady () {
        if (readyPsrdata) {
            readyPsrdata = false;
            return true;
        }
        return false;
    }
}
