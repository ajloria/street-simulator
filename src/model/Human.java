/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */

package model;

import java.util.Map;

/**
 * This class represents behavior of humans.
 * @author Andrew Joshua Loria
 * @version 10/25/17
 *
 */
public class Human extends AbstractVehicle implements Vehicle {

    /**Death time of human is 20.*/
    private static final int DEATH_TIME = 20;
    
    /**
     * Overloaded human constructor.
     * @param theX x coordinate.
     * @param theY y coordinate.
     * @param theDirection direction of vehicle.
     */
    public Human(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME);
        
    }

    /**
     * Says whether or not human can pass the terrain.
     * Humans do not travel through crosswalks when the crosswalk light is green. 
     * If a human is facing a green crosswalk, it will wait until the light
     * changes to yellow and then cross through the crosswalk. The human will 
     * not turn to avoid the crosswalk.Humans travel through crosswalks
     * when the crosswalk light is yellow or red. Humans ignore the color of traffic lights.
     * @param theTerrain Human is on.
     * @param theLight color of light.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean humanCanPass = false;
        
        if ((theTerrain == Terrain.CROSSWALK) && ((theLight == Light.YELLOW)
                        || (theLight == Light.RED))) {
            humanCanPass = true;
        } else if (theTerrain == Terrain.GRASS) {
            humanCanPass = true;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN) {
            /*This is for the scenario when a human is facing a green crosswalk and is waiting 
            until the crosswalk light turns yellow. */
            waitUntilNotGreen(theLight);
        }
        
        return humanCanPass;
    }

    /**
     * Helper method for canPass. When a human is facing a green crosswalk, it will wait until
     * the light turns yellow.
     * @param theLight light color.
     * @return boolean whether or not human can cross crosswalk.
     */
    public boolean waitUntilNotGreen(final Light theLight) {
        boolean humanCanPass = false;
        while (theLight != Light.GREEN) {
            humanCanPass = true; //cross crosswalk when light not green
        }
        return humanCanPass;
    }

    /**
     * Choose direction for human.
     * Humans move in a random direction (straight, left, right, or reverse), 
     * always on grass or crosswalks. A human never reverses direction unless
     * there is no other option. If a human is next to a crosswalk it will 
     * always choose to turn to face in the direction of the crosswalk.
     * @return Direction of where car will go.
     * @param theNeighbors neighboring terrain.
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction humanDirec = Direction.random();     
              
        if (canPass(Terrain.CROSSWALK, Light.YELLOW)) {

            if (humanCanTurnAround(theNeighbors)) {
                humanDirec = getDirection().reverse();
                
            } else {
                while (theNeighbors.get(humanDirec) != Terrain.CROSSWALK
                       && theNeighbors.get(humanDirec) != Terrain.GRASS
                       || humanDirec == getDirection().reverse()) {
                    humanDirec = Direction.random();
                }
                
                if (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK
                                && canPass(Terrain.CROSSWALK, Light.YELLOW)) {
                    humanDirec = getDirection().left();
                } else if (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK
                                && canPass(Terrain.CROSSWALK, Light.YELLOW)) {
                    humanDirec = getDirection().right();
                }
            }
        }
        
        return humanDirec;
    }
    

    /**
     * Helper method for chooseDirection method.
     * Says if the human will have to turn around by checking its forward, left and
     * right directions.
     * @param theNeighbors neighboring terrain of Human.
     * @return boolean true if car will turn around.
     */
    public boolean humanCanTurnAround(final Map<Direction, Terrain> theNeighbors) {
        final Direction carDirec = getDirection();
        boolean turnAround = true;
        
        if (theNeighbors.get(getDirection()) == Terrain.CROSSWALK 
                        || theNeighbors.get(getDirection()) == Terrain.GRASS) {
            turnAround = false;
        } else if (theNeighbors.get(carDirec.left()) == Terrain.CROSSWALK 
                                || theNeighbors.get(carDirec.left()) == Terrain.GRASS) {
            turnAround = false;                   
        } else if (theNeighbors.get(carDirec.right()) == Terrain.CROSSWALK 
                                || theNeighbors.get(carDirec.right()) == Terrain.GRASS) {
            turnAround = false;
        }
                                           
        return turnAround;
    }
    
    
}
               
     
