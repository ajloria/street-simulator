/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */

package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;
import org.junit.Test;

/**
 * Unit tests for class Truck.
 * @author Andrew Joshua Loria
 * @version 10/27/17
 *
 */
public class TruckTest {

    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;
    
    /** Test method for Truck constructor. */
    @Test
    public void testTruckConstructor() {
        final Truck h = new Truck(20, 21, Direction.NORTH);
        
        assertEquals("Truck x coordinate not initialized correctly!", 20, h.getX());
        assertEquals("T y coordinate not initialized correctly!", 21, h.getY());
        assertEquals("Truck direction not initialized correctly!",
                     Direction.NORTH, h.getDirection());
        assertEquals("Truck death time not initialized correctly!", 0, h.getDeathTime());
        assertTrue("Truck isAlive() fails initially!", h.isAlive());
    }
    
    /** Test method for Truck setters. */
    @Test
    public void testTruckSetters() {
        final Truck h = new Truck(20, 21, Direction.NORTH);
       
        h.setX(12);
        assertEquals("Truck setX failed!", 12, h.getX());
        h.setY(13);
        assertEquals("Truck setY failed!", 13, h.getY());
        h.setDirection(Direction.SOUTH);
        assertEquals("Truck setDirection failed!", Direction.SOUTH, h.getDirection());
    }

    /**
     * Test method for {@link Truck#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {
        
        // Trucks can move to STREETS or to CROSSWALKS
        // so we need to test both of those conditions
        
        // Trucks should NOT choose to move to other terrain types
        // so we need to test that Trucks never move to other terrain types
        
        // Trucks should only reverse direction if no other option is available
        // so we need to be sure to test that requirement also
        
        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.LIGHT);
                
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.STREET) {
                
                    // Trucks can pass STREET under any light condition
                    assertTrue("Truck should be able to pass STREET"
                               + ", with light " + currentLightCondition,
                               truck.canPass(destinationTerrain, currentLightCondition));
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                           // Trucks can pass CROSSWALK
                           // if the light is YELLOW or GREEN

                    if (currentLightCondition == Light.RED) {
                        assertFalse("Truck should NOT be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            truck.canPass(destinationTerrain,
                                          currentLightCondition));
                    } else { // light is yellow or green
                        assertTrue("Truck should be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            truck.canPass(destinationTerrain,
                                          currentLightCondition));
                    }
                } else if (destinationTerrain == Terrain.LIGHT) {
                        //Trucks can pass through lights
                        //any colors
                    if (currentLightCondition == Light.RED) {
                        assertTrue("Truck should be able to pass" + destinationTerrain + ".", 
                                       truck.canPass(destinationTerrain, 
                                                     currentLightCondition));
                    } else {
                        assertTrue("Truck should be able to pass" + destinationTerrain + ".", 
                                   truck.canPass(destinationTerrain, currentLightCondition));
                    }
                    
                    
                } 
            }
        } 
    } 
    

    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionSurroundedByStreet() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.STREET);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);
        
        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;
        
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        
        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            final Direction d = truck.chooseDirection(neighbors);
            
            if (d == Direction.WEST) {
                seenWest = true;
            } else if (d == Direction.NORTH) {
                seenNorth = true;
            } else if (d == Direction.EAST) {
                seenEast = true;
            } else if (d == Direction.SOUTH) { // this should NOT be chosen
                seenSouth = true;
            }
        }
 
        assertTrue("Truck chooseDirection() fails to select randomly "
                   + "among all possible valid choices!",
                   seenWest && seenNorth && seenEast);
            
        assertFalse("Truck chooseDirection() reversed direction when not necessary!",
                    seenSouth);
    }
    
    
    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionOnStreetMustReverse() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK) {
                //test scenario where truck's only option is to reverse back to street
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, Terrain.TRAIL);
                neighbors.put(Direction.NORTH, Terrain.STREET);
                neighbors.put(Direction.EAST, Terrain.WALL);
                neighbors.put(Direction.SOUTH, Terrain.GRASS);
                
                final Truck truck = new Truck(0, 0, Direction.SOUTH);
                
                // the Truck must reverse and go NORTH
                assertEquals("Truck chooseDirection() failed "
                                + "when reverse was the only valid choice!",
                             Direction.NORTH, truck.chooseDirection(neighbors));
            }
                
        }
    }
    
}
