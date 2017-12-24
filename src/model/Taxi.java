/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */

package model;

import java.util.Map;

/**
 * This class represents behavior of taxis.
 * @author Andrew Joshua Loria
 * @version 10/25/17
 *
 */
public class Taxi extends AbstractVehicle implements Vehicle {

    /**Death time of taxi is 5.*/
    private static final int DEATH_TIME = 5;
    
    
    /**
     * Overloaded taxi constructor.
     * @param theX x coordinate.
     * @param theY y coordinate.
     * @param theDir direction of vehicle.
     */
    public Taxi(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
        
    }

    /**
     * Says whether or not taxi can pass the terrain.
     * Taxis stop for red lights; if a traffic light is immediately ahead
     *  of the taxi and the light is red, the Taxi stays still and does not move
     *  until the light turns green. It does not turn to avoid the light. When the light
     *  turns green the taxi resumes its original direction. Taxis ignore yellow 
     *  and green lights. Taxis stop for (temporarily) red crosswalk lights. If a
     *  crosswalk light is immediately ahead of the taxi and the crosswalk light is red,
     *  the Taxi stays still and does not move for 3 clock cycles or until the crosswalk
     *  light turns green, whichever occurs first. It does not turn to avoid the crosswalk
     *  light. When the crosswalk light turns green, or after 3 clock cycles, whichever
     *  happens first, the taxi resumes its original direction. A Taxi will drive 
     *  through yellow or green crosswalk lights without stopping.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean taxiCanPass = false;
        
        if (theTerrain == Terrain.LIGHT && theLight == Light.RED) {
            taxiCanPass = false;
        } else if (theTerrain == Terrain.CROSSWALK && (theLight == Light.RED)) {
            redCrossLight(theTerrain, theLight);
        } else if (theTerrain == Terrain.CROSSWALK || theTerrain == Terrain.STREET
                        || theTerrain == Terrain.LIGHT) {
            taxiCanPass = true;
        }
        return taxiCanPass;
    }
    

    /**
     * Returns the red crosswalk light scenario helper method.
     * @param theTerrain terrain taxi will/is driving on.
     * @param theLight color of light.
     * @return true or false.
     */
    public boolean redCrossLight(final Terrain theTerrain, final Light theLight) {
        boolean taxiCanPass = false;
        
        int wait = 0;
        final int clockCycles = 3;
        wait++;
        if (wait % clockCycles == 0) {
            taxiCanPass = true;
        }
        
        if (theLight == Light.GREEN) {
            taxiCanPass = true;
        }
      
        return taxiCanPass;
    }

    /**
     * A taxi prefers to drive straight ahead on the street if it can. If it cannot 
     * move straight ahead, it turns left if possible; if it cannot turn left,
     * it turns right if possible; as a last resort, it turns around.
     * @return Direction of where car will go.
     * @param theNeighbors map containing direction and terrain
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction taxiDirec = Direction.random();
       
        if (canPass(Terrain.STREET, Light.GREEN)) {

            if (taxiCanTurnAround(theNeighbors)) {
                taxiDirec = getDirection().reverse();
                
            } else {
                while (theNeighbors.get(taxiDirec) != Terrain.STREET
                       && theNeighbors.get(taxiDirec) != Terrain.CROSSWALK
                       && theNeighbors.get(taxiDirec) != Terrain.LIGHT
                       || taxiDirec == getDirection().reverse()) {
                    taxiDirec = Direction.random();
                }
            }
        }
        
        return taxiDirec;
    }
    
    /**
     * Says if taxi can turn around (reverse).
     * @param theNeighbors map of direction and terrain.
     * @return boolean true if can turn around; false if not.
     */
    public boolean taxiCanTurnAround(final Map<Direction, Terrain> theNeighbors) {
        final Direction taxiDirec = getDirection();
        boolean turnAround = true;
        if (theNeighbors.get(getDirection()) == Terrain.STREET 
                        || theNeighbors.get(getDirection()) == Terrain.LIGHT
                        || theNeighbors.get(getDirection()) == Terrain.CROSSWALK) {
            turnAround = false;
        } else if (theNeighbors.get(taxiDirec.left()) == Terrain.STREET 
                                || theNeighbors.get(taxiDirec.left()) == Terrain.LIGHT
                                || theNeighbors.get(taxiDirec.left()) == Terrain.CROSSWALK) {
            turnAround = false;                   
        } else if (theNeighbors.get(taxiDirec.right()) == Terrain.STREET 
                                || theNeighbors.get(taxiDirec.right()) == Terrain.LIGHT
                                || theNeighbors.get(taxiDirec.right()) == Terrain.CROSSWALK) {
            turnAround = false;
        }
                
        
            
        return turnAround;
    }
}
