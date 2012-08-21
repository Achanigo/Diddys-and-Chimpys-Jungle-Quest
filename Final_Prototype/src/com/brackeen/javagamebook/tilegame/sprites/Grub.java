package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

/**
   @(#)Grub.java
	
   A <code>Grub</code> is a class extendig from 
	<code>Creature</code> that moves slowly on the ground.
	 
	@author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21
*/
public class Grub extends Creature {
    /**
      Creates a new <code>Creature</code> that moves 
		on the ground, with the specified Animations.
    */

    public Grub(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
    }
    /**
        Method <I>getMaxSpeed</I> gets the maximum speed 
		  of this ground <code>Creature</code>.
		  
		  @return Returns a small float velocity, because 
		  			 the <code>Grub</code> moves slowly. 
    */

    public float getMaxSpeed() {
        return 0.05f;
    }

}
