package com.brackeen.javagamebook.graphics;

import java.awt.Image;

/**
   @(#)Sprite.java
 
    The <code>Sprite</code> class manages an <code>Animation</code> , its
	 position and velocity.
	 
   @author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21
*/
public class Sprite {

    protected Animation anim;
    // position (pixels)
    private float x;
    private float y;
    // velocity (pixels per millisecond)
    private float dx;
    private float dy;

    /** 	
	    Creates a new <code>Sprite</code> object with the specified Animation.
		 @param anim is the <code>Animation</code> that is going to be 
		 		used in this Sprite object
    */
    public Sprite(Animation anim) {
        this.anim = anim;
    }

    /**
	     Method <code>update</code> 
        Updates this Sprite's Animation and its position based
        on the velocity.
		  @param elapsedTime is a <code>long</code> value
    */
    public void update(long elapsedTime) {
        x += dx * elapsedTime;
        y += dy * elapsedTime;
        anim.update(elapsedTime);
    }

    /**
	     Method <code>getX</code>
        Gets this Sprite's current x position.
		  @return x is a <code>float</code> value that 
		  	contains the position
    */
    public float getX() {
        return x;
    }

    /**
	     Method <code>getY</code>
        Gets this Sprite's current y position.
		  @return y is a <code>float</code> value that
		   contains the position
    */
    public float getY() {
        return y;
    }

    /**
	     Method <code>setX</code>
        Sets this Sprite's current x position.
		  @param x is a <code>float</code> value
    */
    public void setX(float x) {
        this.x = x;
    }

    /**
	     Method <code>setY</code>
        Sets this Sprite's current y position.
		  @param y is a <code>float</code> value
    */
    public void setY(float y) {
        this.y = y;
    }

    /**
	     Method <code>getWidth</code>
        Gets this Sprite's width, based on the size of the
        current image.
		  @return an <code>Integer</code> value that is the sprite's width
    */
    public int getWidth() {
        return anim.getImage().getWidth(null);
    }

    /**
	     Method <code>getHeigth</code>
        Gets this Sprite's height, based on the size of the
        current image.
		  @return an <code>Integer</code> value that is the sprite's height
    */
    public int getHeight() {
        return anim.getImage().getHeight(null);
    }

    /**
	     Method <code>getVelocityX</code> 
        Gets the horizontal velocity of this Sprite in pixels
        per millisecond.
		  @return a <code>float</code> value that is the sprite's velocity
    */
    public float getVelocityX() {
        return dx;
    }

    /**
	     Method <code>getVelocityY</code>
        Gets the vertical velocity of this Sprite in pixels
        per millisecond.
		  @return a <code>float</code> value that is the sprite's velocity
    */
    public float getVelocityY() {
        return dy;
    }

    /**
	     Method <code>setVelocityX</code>
        Sets the horizontal velocity of this Sprite in pixels
        per millisecond.
		  @param dx is a <code>float</code> value wich is the new velocity
    */
    public void setVelocityX(float dx) {
        this.dx = dx;
    }

    /**
	 	  Method <code>setVelocityY</code>
        Sets the vertical velocity of this Sprite in pixels
        per millisecond.
		  @param dy is a <code>float</code> value wich is the new velocity
    */
    public void setVelocityY(float dy) {
        this.dy = dy;
    }

    /**
	 	  Method <code>getImage</code>
        Gets this Sprite's current image.
		  @return the Sprite's current <code>Image</code>
    */
    public Image getImage() {
        return anim.getImage();
    }

    /**
	 	  Method <code>clone</code>
        Clones this Sprite. Does not clone position or velocity
        info.
		  @return a new <code>Sprite</code> wich is the clone of this sprite.
    */
    public Object clone() {
        return new Sprite(anim);
    }
}
