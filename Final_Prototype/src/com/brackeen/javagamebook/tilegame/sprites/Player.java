package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

/**
   @(#)Player.java

   <code>Player</code> is a class extending from
	<code>Creture</code>. Its the player of the game.
	 
	@author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21
*/
public class Player extends Creature {

    /**
        Float number that shows the speed of the player's jump.
    */
    private static final float JUMP_SPEED = -.95f;
	 
    /**
        Boolean flag to know if the player is on the ground.
    */
    private boolean onGround;
    /**
      Creates a new <code>Player</code> that moves 
		on the ground and jumps, with the specified 
		Animations. It's the player of the game.
    */
    public Player(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
    }

    /**
        Method <I>collideHorizontal</I> is called before update() 
		  if the player collided with a tile horizontally.
    */
    public void collideHorizontal() {
        setVelocityX(0);
    }

    /**
        Method <I>collideVertical</I> is called before update() 
		  if the player collided with a tile vertically
    */
    public void collideVertical() {
        // check if collided with ground
        if (getVelocityY() > 0) {
            onGround = true;
        }
        setVelocityY(0);
    }

    /**
        Method <I>setY</I>, overwritten from class <code>Sprite</code>
		  sets this Sprite's current y position.
		  
		  @param y is the vertical position you which to change the player´s
		  		   position.
    */
    public void setY(float y) {
        // check if falling
        if (Math.round(y) > Math.round(getY())) {
            onGround = false;
        }
        super.setY(y);
    }
    /**
        Method <I>wakeUp</I> overwritten from class
		  <code>Creature</code>. It doesnt do anything,
		  the player is always awake.
    */
    public void wakeUp() {
        // do nothing
    }


    /**
        Method <I>jump</I> makes the player jump if the 
		  player is on the ground or if forceJump is true.
    */
    public void jump(boolean forceJump) {
        if (onGround || forceJump) {
            onGround = false;
            setVelocityY(JUMP_SPEED);
        }
    }

    /**
        Method <I>getMaxSpeed</I> overwritten from class 
		  <code>Creature</code>. Gets the maximum speed of 
		  the <code>Player</code>.
		  
		  @return Returns a medium float velocity.
    */
    public float getMaxSpeed() {
        return 0.5f;
    }

}
