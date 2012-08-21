package com.brackeen.javagamebook.tilegame.sprites;

import java.lang.reflect.Constructor;
import com.brackeen.javagamebook.graphics.*;

/**
   @(#)Creature.java
 
    A <code>Creature</code> is an abstract <code>Sprite</code> that is affected by 
	 gravity and can die. It has four Animations: moving left, moving right,
    dying on the left, and dying on the right.
	 
   @author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21
*/
public abstract class Creature extends Sprite {

    /**
        Amount of time to go from STATE_DYING to STATE_DEAD.
    */
    private static final int DIE_TIME = 1000;
    /**
        State to flag that the creature is normal
    */
    public static final int STATE_NORMAL = 0;
	 /**
        State to flag that the creature is dying
    */
    public static final int STATE_DYING = 1;
	 /**
        State to flag that the creature is dead.
    */
    public static final int STATE_DEAD = 2;
    /**
        Animation when the creature is moving to the left
    */
    private Animation left;
	 /**
        Animation when the creature is moving to the right
    */
    private Animation right;
	 /**
        Animation when the creature is dying to the left
    */
    private Animation deadLeft;
	 /**
        Animation when the creature is dying to the right
    */
    private Animation deadRight;
	 /**
        Integer that tells you in what state is the creature
    */
    private int state;
	 /**
        Tells you the time of the state.
    */
    private long stateTime;

    /**
        Creates a new <code>Creature</code> with the specified Animations.
    */
    public Creature(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
    {
        super(right);
        this.left = left;
        this.right = right;
        this.deadLeft = deadLeft;
        this.deadRight = deadRight;
        state = STATE_NORMAL;
    }

    /**
        Method <I>clone</I> returns a clone of the creature.
		  
		  @return Returns a clone of the creature.
    */
    public Object clone() {
        // use reflection to create the correct subclass
        Constructor constructor = getClass().getConstructors()[0];
        try {
            return constructor.newInstance(new Object[] {
                (Animation)left.clone(),
                (Animation)right.clone(),
                (Animation)deadLeft.clone(),
                (Animation)deadRight.clone()
            });
        }
        catch (Exception ex) {
            // should never happen
            ex.printStackTrace();
            return null;
        }
    }


    /**
        Method <I>getMaxSpeed</I> gets the maximum speed 
		  of this Creature.
		  
		  @return Returns a velocity, depending the type of creature
    */
    public float getMaxSpeed() {
        return 0;
    }


    /**
        Method <I>wakeUp</I> wakes up the creature when the 
		  Creature first appears on screen. 
		  Normally, the creature starts moving left.
    */
    public void wakeUp() {
        if (getState() == STATE_NORMAL && getVelocityX() == 0) {
            setVelocityX(-getMaxSpeed());
        }
    }


    /**
        Method <I>getState</I> gets the state of this Creature. 
		  The state is either STATE_NORMAL, STATE_DYING, or 
		  STATE_DEAD.
		  
		  @return Returns the actual state of the creature.
    */
    public int getState() {
        return state;
    }


    /**
        Method <I>setState</I> sets the state of this Creature 
		  to STATE_NORMAL,STATE_DYING, or STATE_DEAD.
		  
		  @param state is an integer that modifies the actual
		  			state of the creature
    */
    public void setState(int state) {
        if (this.state != state) {
            this.state = state;
            stateTime = 0;
            if (state == STATE_DYING) {
                setVelocityX(0);
                setVelocityY(0);
            }
        }
    }


    /**
        Method <I>isAlive</I> checks if this creature is alive.
		  
		  @return returns a boolean that checks 
		  			 if he creature is alive
    */
    public boolean isAlive() {
        return (state == STATE_NORMAL);
    }


    /**
        Method <I>isFlying</I> Checks if this creature is flying.
		  
		  @return returns a boolean that checks 
		  			 if the creature is flying.
    */
    public boolean isFlying() {
        return false;
    }


    /**
        Method <I>collideHorizontal</I> is called before update() 
		  if the creature collided with a tile horizontally.
    */
    public void collideHorizontal() {
        setVelocityX(-getVelocityX());
    }


    /**
        Method <I>collideVertical</I> is called before update() 
		  if the creature collided with a tile vertically
    */
    public void collideVertical() {
        setVelocityY(0);
    }


    /**
        Method <I>update</I> updates the animaton 
		  for this creature.
		  
		  @param elapsedTime is the time that has
		  			passed in the game.
    */
    public void update(long elapsedTime) {
        // select the correct Animation
        Animation newAnim = anim;
        if (getVelocityX() < 0) {
            newAnim = left;
        }
        else if (getVelocityX() > 0) {
            newAnim = right;
        }
        if (state == STATE_DYING && newAnim == left) {
            newAnim = deadLeft;
        }
        else if (state == STATE_DYING && newAnim == right) {
            newAnim = deadRight;
        }

        // update the Animation
        if (anim != newAnim) {
            anim = newAnim;
            anim.start();
        }
        else {
            anim.update(elapsedTime);
        }

        // update to "dead" state
        stateTime += elapsedTime;
        if (state == STATE_DYING && stateTime >= DIE_TIME) {
            setState(STATE_DEAD);
        }
    }

}
