package com.brackeen.javagamebook.input;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
    @(#)InputManager.java

    The <code>InputManager</code> manages input of key and mouse events.
    Events are mapped to GameActions.
	 
	@author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21
*/
public class InputManager implements KeyListener, MouseListener,
    MouseMotionListener, MouseWheelListener
{
    /**
        An invisible cursor.
    */
    public static final Cursor INVISIBLE_CURSOR =
        Toolkit.getDefaultToolkit().createCustomCursor(
            Toolkit.getDefaultToolkit().getImage(""),
            new Point(0,0),
            "invisible");

    // mouse codes
    public static final int MOUSE_MOVE_LEFT = 0;
    public static final int MOUSE_MOVE_RIGHT = 1;
    public static final int MOUSE_MOVE_UP = 2;
    public static final int MOUSE_MOVE_DOWN = 3;
    public static final int MOUSE_WHEEL_UP = 4;
    public static final int MOUSE_WHEEL_DOWN = 5;
    public static final int MOUSE_BUTTON_1 = 6;
    public static final int MOUSE_BUTTON_2 = 7;
    public static final int MOUSE_BUTTON_3 = 8;

    private static final int NUM_MOUSE_CODES = 9;

    // key codes are defined in java.awt.KeyEvent.
    // most of the codes (except for some rare ones like
    // "alt graph") are less than 600.
    private static final int NUM_KEY_CODES = 600;

    private GameAction[] keyActions =
        new GameAction[NUM_KEY_CODES];
    private GameAction[] mouseActions =
        new GameAction[NUM_MOUSE_CODES];

    private Point mouseLocation;
    private Point centerLocation;
    private Component comp;
    private Robot robot;
    private boolean isRecentering;

    /**
        Creates a new <code>InputManager</code> that listens to input from the
        specified component.
		  @param comp is a <code>Component</code>
    */
    public InputManager(Component comp) {
        this.comp = comp;
        mouseLocation = new Point();
        centerLocation = new Point();

        // register key and mouse listeners
        comp.addKeyListener(this);
        comp.addMouseListener(this);
        comp.addMouseMotionListener(this);
        comp.addMouseWheelListener(this);

        // allow input of the TAB key and other keys normally
        // used for focus traversal
        comp.setFocusTraversalKeysEnabled(false);
    }


    /**
	 	  Method <code>setCursor</code>
        Sets the cursor on this InputManager's input component.
		  @param cursor is a <code>Cursor</code>
    */
    public void setCursor(Cursor cursor) {
        comp.setCursor(cursor);
    }


    /**
	 	  Method <code>setRelativeMouseMode</code>
        Sets whether realtive mouse mode is on or not. For
        relative mouse mode, the mouse is "locked" in the center
        of the screen, and only the changed in mouse movement
        is measured. In normal mode, the mouse is free to move
        about the screen.
		  @param mode is a <code>boolean</code> value
    */
    public void setRelativeMouseMode(boolean mode) {
        if (mode == isRelativeMouseMode()) {
            return;
        }

        if (mode) {
            try {
                robot = new Robot();
                recenterMouse();
            }
            catch (AWTException ex) {
                // couldn't create robot!
                robot = null;
            }
        }
        else {
            robot = null;
        }
    }


    /**
	     Method <code>isRelativeMouseMode</code>
        Returns whether or not relative mouse mode is on.
		  @return a <code>boolean</code> that is <code>true</code> if
		   the robot is not null, and <code>false</code> if it is
    */
    public boolean isRelativeMouseMode() {
        return (robot != null);
    }


    /**
	 	  Method <code>mapToKey</code>
        Maps a GameAction to a specific key. The key codes are
        defined in java.awt.KeyEvent. If the key already has
        a GameAction mapped to it, the new GameAction overwrites
        it.
		  @param gameAction is the <code>GameAction</code> object 
		  @param keyCode is an <code>Integer</code> value 
		  @see java KeyEvent
    */
    public void mapToKey(GameAction gameAction, int keyCode) {
        keyActions[keyCode] = gameAction;
    }


    /**
	     Method <code>mapToMouse</code>
        Maps a GameAction to a specific mouse action. The mouse
        codes are defined herer in InputManager (MOUSE_MOVE_LEFT,
        MOUSE_BUTTON_1, etc). If the mouse action already has
        a GameAction mapped to it, the new GameAction overwrites
        it.
		  @param gameAction is the <code>GameAction</code> object 
		  @param mouseCode is an <code>Integer</code> value 
    */
    public void mapToMouse(GameAction gameAction,
        int mouseCode)
    {
        mouseActions[mouseCode] = gameAction;
    }


    /**
	     Method <code>clearMap</code>
        Clears all mapped keys and mouse actions to this
        GameAction.
		  @param gameAction is the <code>GameAction</code> that is going
		  	to be cleared from the map
    */
    public void clearMap(GameAction gameAction) {
        for (int i=0; i<keyActions.length; i++) {
            if (keyActions[i] == gameAction) {
                keyActions[i] = null;
            }
        }

        for (int i=0; i<mouseActions.length; i++) {
            if (mouseActions[i] == gameAction) {
                mouseActions[i] = null;
            }
        }

        gameAction.reset();
    }


    /**
	 	  Method <code>getMaps</code>
        Gets a List of names of the keys and mouse actions mapped
        to this GameAction. Each entry in the List is a String.
		  @param gameCode is the <code>GameAction</code> 
		  @return a <code>List</code> of the names of the keys and mouse
		   actions mapped to the GameAction		  
    */
    public List getMaps(GameAction gameCode) {
        ArrayList list = new ArrayList();

        for (int i=0; i<keyActions.length; i++) {
            if (keyActions[i] == gameCode) {
                list.add(getKeyName(i));
            }
        }

        for (int i=0; i<mouseActions.length; i++) {
            if (mouseActions[i] == gameCode) {
                list.add(getMouseName(i));
            }
        }
        return list;
    }


    /**
	     Method <code>resetAllGameActions</code>
        Resets all GameActions so they appear like they haven't
        been pressed.
    */
    public void resetAllGameActions() {
        for (int i=0; i<keyActions.length; i++) {
            if (keyActions[i] != null) {
                keyActions[i].reset();
            }
        }

        for (int i=0; i<mouseActions.length; i++) {
            if (mouseActions[i] != null) {
                mouseActions[i].reset();
            }
        }
    }


    /** 
	     Method <code>getKeyName</code>
        Gets the name of a key code.
		  @param keyCode is an <code>Integer</code> value
		  @return a <code>String</code> that is the name
    */
    public static String getKeyName(int keyCode) {
        return KeyEvent.getKeyText(keyCode);
    }


    /**
	     Method <code>getMouseName</code>
        Gets the name of a mouse code.
		  @param mouseCode is an <code>Integer</code> value.
		  @return a <code>String</code> that is the name
    */
    public static String getMouseName(int mouseCode) {
        switch (mouseCode) {
            case MOUSE_MOVE_LEFT: return "Mouse Left";
            case MOUSE_MOVE_RIGHT: return "Mouse Right";
            case MOUSE_MOVE_UP: return "Mouse Up";
            case MOUSE_MOVE_DOWN: return "Mouse Down";
            case MOUSE_WHEEL_UP: return "Mouse Wheel Up";
            case MOUSE_WHEEL_DOWN: return "Mouse Wheel Down";
            case MOUSE_BUTTON_1: return "Mouse Button 1";
            case MOUSE_BUTTON_2: return "Mouse Button 2";
            case MOUSE_BUTTON_3: return "Mouse Button 3";
            default: return "Unknown mouse code " + mouseCode;
        }
    }


    /**
	     Method <code>getMouseX</code>
        Gets the x position of the mouse.
		  @return an <code>Integer</code> value with the x position
		   of the mouse
    */
    public int getMouseX() {
        return mouseLocation.x;
    }


    /**
	     Method <code>getMouseY</code>
        Gets the y position of the mouse.
		  @return an <code>Integer</code> value with the y position
		   of the mouse
    */
    public int getMouseY() {
        return mouseLocation.y;
    }


    /**
	     Method <code>recenterMouse</code>
        Uses the Robot class to try to postion the mouse in the
        center of the screen.
        <p>Note that use of the Robot class may not be available
        on all platforms.
	  */
    private synchronized void recenterMouse() {
        if (robot != null && comp.isShowing()) {
            centerLocation.x = comp.getWidth() / 2;
            centerLocation.y = comp.getHeight() / 2;
            SwingUtilities.convertPointToScreen(centerLocation,
                comp);
            isRecentering = true;
            robot.mouseMove(centerLocation.x, centerLocation.y);
        }
    }

	/**
	     Method <code>getKeyAction</code>
		  Gets the KeyAction of a Key Event
        @param e is a <code>KeyEvent</code>
		  @return a <code>GameAction</code> object
	  */
    private GameAction getKeyAction(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode < keyActions.length) {
            return keyActions[keyCode];
        }
        else {
            return null;
        }
    }

    /**
	     Method <code>getMouseButtonCode</code<
        Gets the mouse code for the button specified in this
        MouseEvent.
		  @param e is the <code>MouseEvent</code>
		  @return an <code>Integer</code> value wich is the code.
		  
    */
    public static int getMouseButtonCode(MouseEvent e) {
         switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                return MOUSE_BUTTON_1;
            case MouseEvent.BUTTON2:
                return MOUSE_BUTTON_2;
            case MouseEvent.BUTTON3:
                return MOUSE_BUTTON_3;
            default:
                return -1;
        }
    }

     /**
	     Method <code>getMouseButtonAction</code<
        Gets the mouse action for the button specified in this
        MouseEvent.
		  @param e is the <code>MouseEvent</code>
		  @return a <code>GameAction</code>.
		  
    */
    private GameAction getMouseButtonAction(MouseEvent e) {
        int mouseCode = getMouseButtonCode(e);
        if (mouseCode != -1) {
             return mouseActions[mouseCode];
        }
        else {
             return null;
        }
    }

    /**
	     Method <code>keyPressed</code>
		  Invoked when a key has been pressed.
       @see Java KeyListener interface		  
    */
    
    public void keyPressed(KeyEvent e) {
        GameAction gameAction = getKeyAction(e);
        if (gameAction != null) {
            gameAction.press();
        }
        // make sure the key isn't processed for anything else
        e.consume();
    }


    /**
	     Method <code>keyReleased</code>
		  Invoked when a key has been released.
       @see Java KeyListener interface		  
    */
    public void keyReleased(KeyEvent e) {
        GameAction gameAction = getKeyAction(e);
        if (gameAction != null) {
            gameAction.release();
        }
        // make sure the key isn't processed for anything else
        e.consume();
    }


     /**
	     Method <code>keyTyped</code>
		  Invoked when a key has been typed.
       @see Java KeyListener interface		  
    */
    public void keyTyped(KeyEvent e) {
        // make sure the key isn't processed for anything else
        e.consume();
    }


    /**
	    Method <code>mousePressed</code>
		 Invoked when a mouse button has been pressed on a component.
       @see Java MouseListener interface		  
    */
    public void mousePressed(MouseEvent e) {
        GameAction gameAction = getMouseButtonAction(e);
        if (gameAction != null) {
            gameAction.press();
        }
    }


     /**
	    Method <code>mouseReleased</code>
		 Invoked when a mouse button has been released on a component
       @see Java MouseListener interface		  
    */
    public void mouseReleased(MouseEvent e) {
        GameAction gameAction = getMouseButtonAction(e);
        if (gameAction != null) {
            gameAction.release();
        }
    }


    /**
	    Method <code>mouseClicked</code>
		 Invoked when the mouse button has been clicked (pressed and released) on a component.
       @see Java MouseListener interface		  
    */
    public void mouseClicked(MouseEvent e) {
        // do nothing
    }


    /**
	    Method <code>mouseEntered</code>
		 Invoked when the mouse enters a component.
       @see Java MouseListener interface		  
    */
    public void mouseEntered(MouseEvent e) {
        mouseMoved(e);
    }


    /**
	    Method <code>mouseExited</code>
		 Invoked when the mouse exits a component.
       @see Java MouseListener interface		  
    */
    public void mouseExited(MouseEvent e) {
        mouseMoved(e);
    }


    /**
	    Method <code>mouseDragged</code>
		 Invoked when a mouse button is pressed on a component and then dragged.
       @see Java MouseMotionListener interface		  
    */
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }


    /**
	    Method <code>mouseMoved</code>
		 Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
       @see Java MouseMotionListener interface		  
    */
    public synchronized void mouseMoved(MouseEvent e) {
        // this event is from re-centering the mouse - ignore it
        if (isRecentering &&
            centerLocation.x == e.getX() &&
            centerLocation.y == e.getY())
        {
            isRecentering = false;
        }
        else {
            int dx = e.getX() - mouseLocation.x;
            int dy = e.getY() - mouseLocation.y;
            mouseHelper(MOUSE_MOVE_LEFT, MOUSE_MOVE_RIGHT, dx);
            mouseHelper(MOUSE_MOVE_UP, MOUSE_MOVE_DOWN, dy);

            if (isRelativeMouseMode()) {
                recenterMouse();
            }
        }

        mouseLocation.x = e.getX();
        mouseLocation.y = e.getY();

    }

   /**
	    Method <code>mouseWheelMoved</code>
		 Invoked when the mouse wheel is rotated.
       @see Java MouseWheelListener interface		  
    */
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseHelper(MOUSE_WHEEL_UP, MOUSE_WHEEL_DOWN,
            e.getWheelRotation());
    }

    /**
	    Method <code>mouseHelper</code>
		 @param codeNeg is an <code>Integer</code> value
		 @param codePos is an <code>Integer</code> value	
		 @param amount is an <code>Integer</code> value			  
    */
    private void mouseHelper(int codeNeg, int codePos,
        int amount)
    {
        GameAction gameAction;
        if (amount < 0) {
            gameAction = mouseActions[codeNeg];
        }
        else {
            gameAction = mouseActions[codePos];
        }
        if (gameAction != null) {
            gameAction.press(Math.abs(amount));
            gameAction.release();
        }
    }

}
