/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */

package model;

import java.util.Map;

/**
 * This class represents behavior of Atv's.
 * @author Andrew Joshua Loria
 * @version 10/25/17
 *
 */
public class Atv extends AbstractVehicle implements Vehicle {

    /**Death time of ATV is 10.*/
    private static final int DEATH_TIME = 10;
    
    /**
     * Overloaded ATV constructor.
     * @param theX x coordinate.
     * @param theY y coordinate.
     * @param theDirection direction of vehicle.
     */
    public Atv(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME);
        
    }

    /**
     * Says whether or not ATV can pass the terrain.
     * ATVs can travel on any terrain except walls. They randomly select to go
     * straight, turn left, or turn right. ATV’s never reverse direction.  ATV’s
     * drive through all traffic lights and cross walk lights without stopping. 
     * @param theTerrain terrain of ATV.
     * @param theLight color of light.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean atvCanPass = true;
        
        //ATV's can't travel on walls
        if (theTerrain == Terrain.WALL) { 
            atvCanPass = false;
        }
        return atvCanPass;
    }

    /**
     * Choose direction for ATV.
     * ATVs can travel on any terrain except walls. They randomly select to go
     * straight, turn left, or turn right. ATV’s never reverse direction.  ATV’s
     * drive through all traffic lights and crosswalk lights without stopping! 
     * @return Direction of where car will go.
     * @param theNeighbors map of neighboring terrain of ATV.
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        return Direction.random();
    }
}
