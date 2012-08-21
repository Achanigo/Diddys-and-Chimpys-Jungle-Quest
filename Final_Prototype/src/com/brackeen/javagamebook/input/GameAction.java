package com.brackeen.javagamebook.input;

/**
	 @(#)GameAction.java
    The <code>GameAction</code> class is an abstract to a user-initiated
    action, like jumping or moving. GameActions can be mapped
    to keys or the mouse with the InputManager.
	 
	@author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21

*/
public class GameAction {

    /**
        Normal behavior. The isPressed() method returns true
        as long as the key is held down.
    */
    public static final int NORMAL = 0;

    /**
        Initial press behavior. The isPressed() method returns
        true only after the key is first pressed, and not again
        until the key is released and pressed again.
    */
    public static final int DETECT_INITAL_PRESS_ONLY = 1;

    private static final int STATE_RELEASED = 0;
    private static final int STATE_PRESSED = 1;
    private static final int STATE_WAITING_FOR_RELEASE = 2;

    private String name;
    private int behavior;
    private int amount;
    private int state;

    /**
	     Create a new GameAction with the NORMAL behavior.
		  @param name is a <code>String</code> value wich is the
		  	name of the GameAction
    */
    public GameAction(String name) {
        this(name, NORMAL);
    }


    /**
        Create a new GameAction with the specified behavior.
		  @param name is a <code>String</code> value wich is the
		  	name of the GameAction
		  @param behavior is an <code>Integer</code> value wich is the
		  	behavior of the GameAction
    */
    public GameAction(String name, int behavior) {
        this.name = name;
        this.behavior = behavior;
        reset();
    }


    /**
	 	  Method <code>getName</code>
        Gets the name of this GameAction.
		  @return a <code>String</code> value wich the name 
		  	of this GameAction
    */
    public String getName() {
        return name;
    }


    /**
	     Method <code>reset</code>
        Resets this GameAction so that it appears like it hasn't
        been pressed.
    */
    public void reset() {
        state = STATE_RELEASED;
        amount = 0;
    }


    /**
	     Method <code>tap</code>
        Taps this GameAction. Same as calling press() followed
        by release().
    */
    public synchronized void tap() {
        press();
        release();
    }


    /**
	     Method <code>press</code>
        Signals that the key was pressed.
    */
    public synchronized void press() {
        press(1);
    }


    /**
	     Method <code>press</code>
        Signals that the key was pressed a specified number of
        times, or that the mouse move a spcified distance.
		  @param amount is an <code>Integer</code> value wich is 
		  	the number of times 
    */
    public synchronized void press(int amount) {
        if (state != STATE_WAITING_FOR_RELEASE) {
            this.amount+=amount;
            state = STATE_PRESSED;
        }

    }


    /**
	     Method <code>release</code>
        Signals that the key was released
    */
    public synchronized void release() {
        state = STATE_RELEASED;
    }


    /**
	     Method <code>isPressed</code>
        Returns whether the key was pressed or not since last
        checked.
		  @return a boolean value that is <code>true</code> if the
		  	key was pressed since the last time checked and is 
			<code>false</code> if not
    */
    public synchronized boolean isPressed() {
        return (getAmount() != 0);
    }


    /**
	     Method <code>getAmount</code>
        For keys, this is the number of times the key was
        pressed since it was last checked.
        For mouse movement, this is the distance moved.
		  @return an <code>Integer</code> value that is the number of 
		  times the key was pressed or the distance the mouse was moved 
		  since it was last checked
    */
    public synchronized int getAmount() {
        int retVal = amount;
        if (retVal != 0) {
            if (state == STATE_RELEASED) {
                amount = 0;
            }
            else if (behavior == DETECT_INITAL_PRESS_ONLY) {
                state = STATE_WAITING_FOR_RELEASE;
                amount = 0;
            }
        }
        return retVal;
    }
}
