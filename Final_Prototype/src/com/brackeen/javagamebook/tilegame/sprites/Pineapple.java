package com.brackeen.javagamebook.tilegame.sprites;

import java.lang.reflect.Constructor;
import com.brackeen.javagamebook.graphics.*;

/**
   @(#)Pineapple.java
	
    A <code>Pineapple</code> class extends from 
	 <code>Sprite</code> that can kill the player.
	 	 
	@author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21	 
*/
public class Pineapple extends Sprite {
    
	 /**
       Float that shows the initial horizontal position.
    */
	 private float initialX;
	 /**
       Float that shows the initial vertical position.
    */
	 private float initialY;
	 /**
        State to flag that the pineapple is normal
    */
	 private boolean STATE_NORMAL;
	 
    /**
      Creates a new <code>Pineapple</code> that moves 
		horizontally, with the specified Animations and
		states.
    */
    public Pineapple(Animation anim) {
        super(anim);
		  STATE_NORMAL = true;
    }
    /**
        Method <I>clone</I> returns a clone of the pineapple.
		  
		  @return Returns a clone of the pineapple.
    */
    public Object clone() {
        // use reflection to create the correct subclass
        Constructor constructor = getClass().getConstructors()[0];
        try {
            return constructor.newInstance(
                new Object[] {(Animation)anim.clone()});
        }
        catch (Exception ex) {
            // should never happen
            ex.printStackTrace();
            return null;
        }
    }
	 /**
        Method <I>getMaxSpeed</I> gets the maximum speed 
		  of this <code>Pineapple</code>.
		  
		  @return Returns a float velocity. 
    */
	 public float getMaxSpeed() {
        return 0.3f;
    }
	 
	  /**
        Method <I>wakeUp</I> wakes up the <code>Pineapple</code>.
		  Normally, the pineapple starts moving left.
    */
    public void wakeUp() {
        if (getVelocityX() == 0) {
            setVelocityX(-getMaxSpeed());
        }
    }
	 /**
        Method <I>getInitialX</I> returns the initial horizontal 
		  position of the <code>Pineapple</code>.
		  
		  @return Returns the initial horizontal position.
    */
	 public float getInitialX(){
	  return initialX;
	 }
	 /**
        Method <I>getInitialX</I> returns the initial vertical 
		  position of the <code>Pineapple</code>.
		  
		  @return Returns the initial vertical position.

    */	 
	 public float getInitialY(){
	  return initialY;
	 }
	 /**
        Method <I>setInitialX</I> sets the initial hotizontal 
		  position of the <code>Pineapple</code>.
		  
		  @param x is the new initial horizontal position.

    */	 
	 public void setInitialX(float x){
	  initialX = x;
	 }
	 /**
        Method <I>setInitialX</I> sets the initial vertical
		  position of the <code>Pineapple</code>.
		  
		  @param y is the new initial vertical position.

    */		 
	 public void setInitialY(float y){
	  initialY = y;
	 }
	 /**
        Method <I>getState</I> returns the state 
		  of the <code>Pineapple</code>.
		  
		  @return Returns a boolean of the state.

    */	
	 public boolean getState() {
	  return STATE_NORMAL; 
	 }
	 /**
        Method <I>setState</I> sets the state 
		  of the <code>Pineapple</code>.
		  
		  @param state is the new state of the
		  			<code>Pineapple</code>.

    */		 
	 public void setState(boolean state) {
	   STATE_NORMAL = state;
	 }
}