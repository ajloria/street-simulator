/*
 * TCSS 305 - Autumn 2017
 * Assignment 3 - Easy Street
 */

package model;


/**
 * This is the AbstractVehicle class which is the 
 * common class of all vehicles.
 * @author Andrew Joshua Loria
 * @version 10/22/17
 *
 */
public abstract class AbstractVehicle implements Vehicle {
    
    /**X coordinate of vehicle.*/
    private int myX;
    
    /**Y coordinate of vehicle.*/
    private int myY;
    
    /**Direction of the vehicle.*/
    private Direction myDirec;
    
    /**Death time of vehicle.*/
    private final int myDeathTime;
    
    
    /**Initial X coordinate of vehicle.*/
    private final int myInitX;
    
    /**Initial Y coordinate of vehicle.*/
    private final int myInitY;
    
    /**Initial direction.*/
    private final Direction myInitDirec;
    
    /**Number of pokes.*/
    private int myNumberOfPokes;
    
    /**Says if vehicle alive.*/
    private boolean myAlive;
    
    /**
     * Overloaded constructor.
     * @param theX Vehicle's X coordinate.
     * @param theY Vehicle's Y coordinate.
     * @param theDirec Vehicle's direction.
     * @param theDeathTime Vehicle's death time.
     */
    protected AbstractVehicle(final int theX, final int theY, 
                              final Direction theDirec, final int theDeathTime) {
        setX(theX);
        myY = theY;
        myDirec = theDirec;
        myInitDirec = theDirec;
        myDeathTime = theDeathTime;
        myInitY = theY;
        myInitX = theX;
        myAlive = true;
        
    }
    
    /**
     * When this Vehicle collides with the specified other Vehicle.
     * @param theOther the other vehicle
     */
    public void collide(final Vehicle theOther) {
        if (theOther.isAlive() && isAlive()) {
            if (theOther.getDeathTime() < myDeathTime) {
                myAlive = false; //vehicle dead because it had greater death time
            } else if (theOther.getDeathTime() > myDeathTime) {
                myAlive = true; //vehicle alive because it had smaller death time
            } else {
                myAlive = true; //vehicle alive because it collided with same vehicle
            }
        }
    }
                        
    
    /**
     * Returns death time of vehicle.
     * @return death time.
     */
    public int getDeathTime() {
        return myDeathTime;
    }
    
    /**
     * Returns the file name of the image for this Vehicle object.
     * 
     * @return the file name.
     */
    @Override
    public String getImageFileName() {
        final StringBuilder stbu = new StringBuilder();
        stbu.append(getClass().getSimpleName().toLowerCase());
        
        if (isAlive()) {
            stbu.append(".gif");
        } else {
            stbu.append("_dead.gif");
        }
        
        return stbu.toString();
        
    }
    
    /**
     * Returns direction.
     * @return myDirec my direction
     */
    public Direction getDirection() {
        return myDirec;
    }
    
    /**
     * Sets the direction.
     * @param theDirec direction want to set it to.
     */
    public void setDirection(final Direction theDirec) {
        myDirec = theDirec;
    }
    
    /**
     * Says if vehicle is alive or not.
     * @return boolean true if alive, false if dead.
     */
    public boolean isAlive() {
        return myAlive;
    }
    
    /**
     * If vehicle is alive, it will go in a random direction.
     * If vehicle is dead, its death time will increase.
     */
    @Override
    public void poke() {
        
        if (isAlive()) {
            myAlive = true;
            myDirec = Direction.random();
        
        } else if (myNumberOfPokes == myDeathTime) {
            myAlive = true;
            setDirection(Direction.random());
            myNumberOfPokes = 0;
        
        } else if (myNumberOfPokes < myDeathTime) {
            myNumberOfPokes++;
        }
    }
    /**
     * Return X.
     * @return the myX
     */
    @Override 
    public int getX() {
        return myX;
    }

    /**
     * Set X to given value.
     * @param theX theX to set
     */
    public void setX(final int theX) {
        myX = theX;
    }
    
    /**
     * Return Y.
     * @return the myY
     */
    public int getY() {
        return myY;
    }
    
    /**
     * Set Y.
     * @param theY the Y to set
     */
    public void setY(final int theY) {
        myY = theY;
    }
    
    /**
     * Resets X,Y, & direction.
     */
    public final void reset() {
        myX = myInitX;
        myY = myInitY;
        myDirec = myInitDirec;
    }
    
    /**
     * Returns string representation of vehicle.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(180);
        builder.append(getClass().getSimpleName());
        builder.append(" Direction: ");
        builder.append(myDirec);
        return builder.toString();
        
    }

}
