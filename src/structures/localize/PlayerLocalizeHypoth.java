/*
 *  Player Java Client 3 - PlayerLocalizeHypoth.java
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
 * $Id: PlayerLocalizeHypoth.java 125 2011-03-24 02:24:05Z corot $
 *
 */

package javaclient3.structures.localize;

import javaclient3.structures.*;

/**
 * Hypothesis format.
 * Since the robot pose may be ambiguous (i.e., the robot may at any
 * of a number of widely spaced locations), the localize interface is
 * capable of returning more that one hypothesis.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerLocalizeHypoth implements PlayerConstants {

    // The mean value of the pose estimate [m, m, rad].
    private PlayerPose2d mean;
    // The covariance matrix pose estimate (m$^2$, rad$^2$).
    private double[] cov = new double[6];
    // The weight coefficient for linear combination (alpha)
    private double alpha;


    /**
     * @return  The mean value of the pose estimate [m, m, rad].
     */
    public synchronized PlayerPose2d getMean () {
        return this.mean;
    }

    /**
     * @param newMean  The mean value of the pose estimate [m, m, rad].
     */
    public synchronized void setMean (PlayerPose2d newMean) {
        this.mean = newMean;
    }

    /**
     * @return  The covariance matrix pose estimate (m$^2$, rad$^2$).
     */
    public synchronized double[] getCov () {
        return this.cov;
    }

    /**
     * @param newCov  The covariance matrix pose estimate (m$^2$, rad$^2$).
     */
    public synchronized void setCov (double[] newCov) {
        this.cov = newCov;
    }

    /**
     * @return  The weight coefficient for linear combination (alpha)
     */
    public synchronized double getAlpha () {
        return this.alpha;
    }

    /**
     * @param newAlpha  The weight coefficient for linear combination (alpha)
     */
    public synchronized void setAlpha (double newAlpha) {
        this.alpha = newAlpha;
    }
}