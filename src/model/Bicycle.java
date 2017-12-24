/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */

package model;

import java.util.Map;

/**
 * This class represents behavior of bicycles.
 * @author Andrew Joshua Loria
 * @version 10/23/17
 *
 */
public class Bicycle extends AbstractVehicle implements Vehicle {

    /**Death time of bicycle is 15.*/
    private static final int DEATH_TIME = 15;
    
    /**
     * Overloaded bicycle constructor.
     * @param theX x coordinate.
     * @param theY y coordinate.
     * @param theDirec direction of vehicle.
     */
    public Bicycle(final int theX, final int theY, final Direction theDirec) {
        super(theX, theY, theDirec, DEATH_TIME);
        
    }
    
    
    /**
     * Whether or not bicycle can pass terrain.
     * Bicycles can travel on streets and through lights and cross walk lights,
     * but they prefer to travel on trails. Bicycles ignore green lights.
     * Bicycles stop for yellow and red lights; if a traffic light or 
     * cross walk light is immediately ahead of the bicycle and the light is not green,
     * the bicycle stays still and does not move unless a trail is to the left or right.
     * @param theTerrain terrain for Bicycle.
     * @param theLight color of light.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean bicycleCanPass = false;
        if (theTerrain == Terrain.CROSSWALK && theLight != Light.GREEN) {
            bicycleCanPass = false;
        } else if (theTerrain == Terrain.LIGHT && theLight != Light.GREEN) {
            bicycleCanPass = false;
        } else if (theTerrain == Terrain.TRAIL || theTerrain == Terrain.STREET
                        || theTerrain == Terrain.LIGHT || theTerrain == Terrain.CROSSWALK) {
            bicycleCanPass = true;
        }
        return bicycleCanPass;
    }
    
    /**
     * Choose direction of bike.
     * If there is no trail straight ahead, to the left, or to the right, 
     * the bicycle prefers to move straight ahead on a street if it can.
     * @param theNeighbors neighboring terrain of bike.
     * @return Direction bike will go
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction bikeDirec = getDirection(); //turns around if no place to go
        
        while (theNeighbors.get(bikeDirec) != Terrain.TRAIL
                   && theNeighbors.get(bikeDirec) != Terrain.STREET
                   && theNeighbors.get(bikeDirec) != Terrain.CROSSWALK
                   && theNeighbors.get(bikeDirec) != Terrain.LIGHT) {
            bikeDirec = Direction.random();
        } 
               
        return bikeDirec;
    }
               

}
