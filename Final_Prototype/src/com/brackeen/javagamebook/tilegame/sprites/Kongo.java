package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

/**
    @(#)Kongo.java
	   
	 <code>Kongo</code> is the last boss of Diddys & Chimpys: 
	 Jungle Quest. This class extends from <code>Creature</code>
	 
	@author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21
*/
public class Kongo extends Creature {

    /**
        Creates a new <code>Kongo</code> with the specified Animations.
		  It will throw pineapples to the <code>Player</code>.
    */
    public Kongo(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
    }

    /**
        Method <I>getMaxSpeed</I> gets the maximum speed 
		  of this <code>Kongo</code>.
		  
		  @return Returns a 0, because it will throw pineapples
		  			 without moving. 
    */
    public float getMaxSpeed() {
        return 0f;
    }

}
