/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */

package model;

import java.util.Map;

/**
 * This class represents behavior of trucks.
 * @author Andrew Joshua Loria
 * @version 10/23/17
 *
 */
public class Truck extends AbstractVehicle implements Vehicle {
    
    /**Death time of truck is 0.*/
    private static final int DEATH_TIME = 0;
    
    /**
     * Overloaded truck constructor.
     * @param theX x coordinate.
     * @param theY y coordinate.
     * @param theDirec direction of vehicle.
     */
    public Truck(final int theX, final int theY, final Direction theDirec) {
        super(theX, theY, theDirec, DEATH_TIME);
        
    }

    /**
     * Says whether or not truck can pass the terrain.
     * Trucks drive through all traffic lights without stopping.
     * Trucks stop for red cross walk lights, but drive through yellow
     * or green cross walk lights without stopping.
     * Trucks travel only on streets and through lights and crosswalks. 
     * @param theTerrain terrain of Truck.
     * @param theLight color of light.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean truckCanPass = false;
        
        if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
            truckCanPass = false;
        } else if (theTerrain == Terrain.CROSSWALK || theTerrain == Terrain.STREET
                        || theTerrain == Terrain.LIGHT) {
            truckCanPass = true;
        }
        return truckCanPass;
    }

    /**
     * Decides direction of where truck will go.
     * @return Direction of where turck will go.
     * @param theNeighbors map containing direction and terrain
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction truckDirec  = Direction.random();
        
        if (canPass(Terrain.STREET, Light.GREEN)) {

            if (truckCanTurnAround(theNeighbors)) {
                truckDirec = getDirection().reverse();
                
            } else {
                while (theNeighbors.get(truckDirec) != Terrain.STREET
                       && theNeighbors.get(truckDirec) != Terrain.CROSSWALK
                       && theNeighbors.get(truckDirec) != Terrain.LIGHT
                       || truckDirec == getDirection().reverse()) {
                    truckDirec = Direction.random();
                }
            }
        }

        return truckDirec;
    }
    
    /**
     * Helper method for chooseDirection method.
     * Says if the truck will have to turn around by checking its forward, left and
     * right directions.
     * @param theNeighbors neighboring terrain.
     * @return boolean true if truck will turn around.
     */
    public boolean truckCanTurnAround(final Map<Direction, Terrain> theNeighbors) {
        final Direction truckDirec = getDirection();
        boolean turnAround = true;
        if (theNeighbors.get(getDirection()) == Terrain.STREET 
                        || theNeighbors.get(getDirection()) == Terrain.LIGHT
                        || theNeighbors.get(getDirection()) == Terrain.CROSSWALK) {
            turnAround = false;
        } else if (theNeighbors.get(truckDirec.left()) == Terrain.STREET 
                                || theNeighbors.get(truckDirec.left()) == Terrain.LIGHT
                                || theNeighbors.get(truckDirec.left()) == Terrain.CROSSWALK) {
            turnAround = false;                   
        } else if (theNeighbors.get(truckDirec.right()) == Terrain.STREET 
                                || theNeighbors.get(truckDirec.right()) == Terrain.LIGHT
                                || theNeighbors.get(truckDirec.right()) == Terrain.CROSSWALK) {
            turnAround = false;
        }
     
        return turnAround;
    }
}
