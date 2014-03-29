/*
 *  Player Java Client 3 - PlayerLocalizeGetParticles.java
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
 * $Id: PlayerLocalizeGetParticles.java 134 2011-05-04 00:08:23Z corot $
 *
 */

package javaclient3.structures.localize;

import javaclient3.structures.*;

/**
 * Request/reply: Get particles.
 * To get (usually a subset of) the current particle set (assuming
 * the underlying driver uses a particle filter), send a null
 * PLAYER_LOCALIZE_REQ_GET_PARTICLES request.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v3.0 - Player 3.0 supported
 * </ul>
 */
public class PlayerLocalizeGetParticles implements PlayerConstants {

    // The best (?) pose [m, m, rad]
    private PlayerPose2d mean;
    // The variance of the best (?) pose [m^2]
    private double variance;
    // The particles
    private PlayerLocalizeParticle[] particles;


    /**
     * @return  The best (?) pose [m, m, rad]
     */
    public synchronized PlayerPose2d getMean () {
        return this.mean;
    }

    /**
     * @param newMean  The best (?) pose [m, m, rad]
     */
    public synchronized void setMean (PlayerPose2d newMean) {
        this.mean = newMean;
    }

    /**
     * @return  The variance of the best (?) pose [m^2]
     */
    public synchronized double getVariance () {
        return this.variance;
    }

    /**
     * @param newVariance  The variance of the best (?) pose [m^2]
     */
    public synchronized void setVariance (double newVariance) {
        this.variance = newVariance;
    }

    /**
     * @return  The number of particles included
     */
    public synchronized int getParticles_count () {
        return (this.particles == null)?0:particles.length;
    }

    /**
     * @return  The particles
     */
    public synchronized PlayerLocalizeParticle[] getParticles () {
        return this.particles;
    }

    /**
     * @param newParticles  The particles
     */
    public synchronized void setParticles (PlayerLocalizeParticle[] newParticles) {
        this.particles = newParticles;
    }
}