/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */

package model;

import java.util.Map;

/**
 * This class represents behavior of cars.
 * @author Andrew Joshua Loria
 * @version 10/25/17
 *
 */
public class Car extends AbstractVehicle implements Vehicle {

    /**Death time of car is 5.*/
    private static final int DEATH_TIME = 5;
    
    /**
     * Overloaded car constructor.
     * @param theX x coordinate.
     * @param theY y coordinate.
     * @param theDirec direction of vehicle.
     */
    public Car(final int theX, final int theY, final Direction theDirec) {
        super(theX, theY, theDirec, DEATH_TIME);
        
    }

    /**
     * Says whether or not car can pass the terrain.
     * Cars stop for red lights; if a traffic light is immediately ahead of
     * the car and the light is red, the car stays still and does not move.
     * It does not turn to avoid the light. When the light turns green, 
     * the car resumes its original direction.
     * Cars stop for red and yellow crosswalk lights, but drive through
     * green crosswalk lights without stopping.
     * @param theTerrain terrain car is on.
     * @param theLight color of light.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean carCanPass = false;
        
        if ((theTerrain == Terrain.LIGHT && theLight == Light.GREEN)
                        || (theTerrain == Terrain.LIGHT && theLight == Light.YELLOW)) {
            carCanPass = true;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN) {
            carCanPass = true;
        } else if (theTerrain == Terrain.STREET) {
            carCanPass = true;
        }
         
        return carCanPass;
    }

    /**
     * A car prefers to drive straight ahead on the street if it can. If it cannot 
     * move straight ahead, it turns left if possible; if it cannot turn left,
     * it turns right if possible; as a last resort, it turns around.
     * @return Direction of where car will go.
     * @param theNeighbors map containing direction and terrain
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction carDirec = Direction.random();
             
        if (canPass(Terrain.STREET, Light.GREEN)) { //if can pass street with green light

            if (carCanTurnAround(theNeighbors)) {
                carDirec = getDirection().reverse();
                
            } else {
                while (theNeighbors.get(carDirec) != Terrain.STREET
                       && theNeighbors.get(carDirec) != Terrain.CROSSWALK
                       && theNeighbors.get(carDirec) != Terrain.LIGHT
                       || carDirec == getDirection().reverse()) {
                    //if not street & crosswalk & light or going in reverse
                    carDirec = Direction.random();
                }
            }
        }

        return carDirec;

    }
    
    /**
     * Helper method for chooseDirection method.
     * Says if the car will have to turn around by checking its forward, left and
     * right directions.
     * @param theNeighbors Map with Direction, Terrain.
     * @return boolean true if car will turn around.
     */
    public boolean carCanTurnAround(final Map<Direction, Terrain> theNeighbors) {
        final Direction carDirec = getDirection();
        boolean turnAround = true;
        if (theNeighbors.get(getDirection()) == Terrain.STREET 
                        || theNeighbors.get(getDirection()) == Terrain.LIGHT
                        || theNeighbors.get(getDirection()) == Terrain.CROSSWALK) {
            turnAround = false;
        } else if (theNeighbors.get(carDirec.left()) == Terrain.STREET 
                                || theNeighbors.get(carDirec.left()) == Terrain.LIGHT
                                || theNeighbors.get(carDirec.left()) == Terrain.CROSSWALK) {
            turnAround = false;                   
        } else if (theNeighbors.get(carDirec.right()) == Terrain.STREET 
                                || theNeighbors.get(carDirec.right()) == Terrain.LIGHT
                                || theNeighbors.get(carDirec.right()) == Terrain.CROSSWALK) {
            turnAround = false;
        }
                
        
            
        return turnAround;
    }
    
    
}
