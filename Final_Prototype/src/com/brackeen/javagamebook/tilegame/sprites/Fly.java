package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

/**
   @(#)Fly.java
	 
   A <code>Fly</code> is a class extendig from <code>Creature</code> 
	that can fly slowly in the air.
	 
	@author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21
*/
public class Fly extends Creature {
    /**
      Creates a new flying <code>Creature</code> 
		with the specified Animations.
    */
    public Fly(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
    }

    /**
        Method <I>getMaxSpeed</I> gets the maximum speed 
		  of this flying <code>Creature</code>.
		  
		  @return Returns a small float velocity, because 
		  			 the <code>Fly</code> flies slowly. 
    */
    public float getMaxSpeed() {
        return 0.2f;
    }

    /**
        Method <I>isFlying</I> checks if this creature is flying.
		  
		  @return Returns true only if its alive, because this
		  			 <code>Creature</code> can fly.
    */
    public boolean isFlying() {
        return isAlive();
    }

}
