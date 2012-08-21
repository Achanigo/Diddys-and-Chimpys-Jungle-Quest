package com.brackeen.javagamebook.tilegame.sprites;

import java.lang.reflect.Constructor;
import com.brackeen.javagamebook.graphics.*;

/**
    A <code>PowerUp</code> class is a <code>Sprite</code> 
	 that the player can pick up.
*/
public abstract class PowerUp extends Sprite {
    /**
        Creates a new <code>PowerUp</code> with the specified Animation.
    */
    public PowerUp(Animation anim) {
        super(anim);
    }

    /**
        Method <I>clone</I> returns a clone of the <code>PowerUp</code>.
		  
		  @return Returns a clone of the power up.
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
        A <code>Star</code> extends from <code>PowerUp</code>. 
		  Gives the player banana points.
    */
    public static class Star extends PowerUp {
	 /**
        Creates a new <code>Star</code> with the specified Animation.
    */
        public Star(Animation anim) {
            super(anim);
        }
    }


    /**
        A <code>Music</code> extends from <code>PowerUp</code>. 
		  Gives the player one life.
    */
    public static class Music extends PowerUp {
	 /**
        Creates a new <code>Music</code> with the specified Animation.
    */
        public Music(Animation anim) {
            super(anim);
        }
    }


    /**
        A <code>Goal</code> extends from <code>PowerUp</code>. 
		  Advances to the next map.
    */
    public static class Goal extends PowerUp {
	 /**
        Creates a new <code>Goal</code> with the specified Animation.
    */
        public Goal(Animation anim) {
            super(anim);
        }
    }
    /**
        A <code>Switch</code> extends from <code>PowerUp</code>. 
		  Erases kongo's bridge.
    */
	 public static class Switch extends PowerUp {
	 /**
        Boolean flag to show if the switch is turned on or off.
    */
	   private boolean on;
	 /**
        Counter to know how many times to update the animation.
    */
		private int update;  
    /**
        Creates a new <code>Switch</code> with the specified Animation,
		  turned on.
    */
	   public Switch(Animation anim) {
		  super(anim);
		  on = true;
		}
	   /**
        Method <I>turnedOff</I> sets the switch turned to off, making
		  the boolean on, false. Increases the counter of the animation
		  update, until it gets to 10.
		  
     */	
	   public void turnedOff() {
		  on = false;
		  if(update < 10) {
		    update++;
		  }
		}
	   /**
        Method <I>getSwitch</I> returns a boolean to know if the switch
		  is turned on or off.
		  
		  @return Returns a boolean showing the state of the switch.
		  
     */	
		public boolean getSwitch() {
		  return on;
		}
	   /**
        Method <I>getUpdate</I> returns an integer showing the counter
		  of the updates.
		  
		  @return Returns the counter of the updates.
		  
     */	
		public int getUpdate() {
		 return update;
		}
	   /**
        Method <I>setUpdate</I> sets an integer of the counter
		  of the updates.
		  
		  @param up is the new number of the counter of the updates.
		  
     */	
		public void setUpdate(int up) {
		 update = up;
		}
	 }
    /**
        A <code>Friend</code> extends from <code>PowerUp</code>. 
		  Finishes the game.
    */
    public static class Friend extends PowerUp {
	 /**
        Creates a new <code>Friend</code> with the specified Animation.
    */
		public Friend(Animation anim) {
		  super(anim);
		}
	 }
}
