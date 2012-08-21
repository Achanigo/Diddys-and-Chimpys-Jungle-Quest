package com.brackeen.javagamebook.test;

import java.awt.*;
import javax.swing.ImageIcon;

import com.brackeen.javagamebook.graphics.ScreenManager;

/**
    @(#)GameCore.java
	 
    Simple abstract class used for testing. Subclasses should
    implement the draw() method.
	 
	@author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21

*/
public abstract class GameCore {

    protected static final int FONT_SIZE = 24;

    private static final DisplayMode POSSIBLE_MODES[] = {
        new DisplayMode(800, 600, 16, 0),
        new DisplayMode(800, 600, 32, 0),
        new DisplayMode(800, 600, 24, 0),
        new DisplayMode(640, 480, 16, 0),
        new DisplayMode(640, 480, 32, 0),
        new DisplayMode(640, 480, 24, 0),
        new DisplayMode(1024, 768, 16, 0),
        new DisplayMode(1024, 768, 32, 0),
        new DisplayMode(1024, 768, 24, 0),
    };

    private boolean isRunning;
    protected ScreenManager screen;


    /**
	 	  Method <code>stop</code>
        Signals the game loop that it's time to quit
    */
    public void stop() {
        isRunning = false;
    }


    /** 
	 	  Method <code>run</code>
        Calls init() and gameLoop()
    */
    public void run() {
        try {
            init();
            gameLoop();
        }
        finally {
            screen.restoreScreen();
            lazilyExit();
        }
    }


    /**
	     Method <code>lazilyExit</code>
        Exits the VM from a daemon thread. The daemon thread waits
        2 seconds then calls System.exit(0). Since the VM should
        exit when only daemon threads are running, this makes sure
        System.exit(0) is only called if neccesary. It's neccesary
        if the Java Sound system is running.
    */
    public void lazilyExit() {
        Thread thread = new Thread() {
            public void run() {
                // first, wait for the VM exit on its own.
                try {
                    Thread.sleep(2000);
                }
                catch (InterruptedException ex) { }
                // system is still running, so force an exit
                System.exit(0);
            }
        };
        thread.setDaemon(true);
        thread.start();
    }


    /**
	     Method <code>init</code>
        Sets full screen mode and initiates and objects.
    */
    public void init() {
        screen = new ScreenManager();
        DisplayMode displayMode =
            screen.findFirstCompatibleMode(POSSIBLE_MODES);
        screen.setFullScreen(displayMode);

        Window window = screen.getFullScreenWindow();
        window.setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
        window.setBackground(Color.blue);
        window.setForeground(Color.white);

        isRunning = true;
    }

	/**
		  Method <code>loadImage</code>
		  Loads an image that is named with the fileName given as a parameter.
		  @param fileName is an <code>String</code> that contains the name
		  	of the file
		  @return the <code>Image</code> with that fileName
        
    */
    public Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }


    /**
	 	  Method <code>gameLoop</code>
        Runs through the game loop until stop() is called.
    */
    public void gameLoop() {
        long startTime = System.currentTimeMillis();
        long currTime = startTime;

        while (isRunning) {
            long elapsedTime =
                System.currentTimeMillis() - currTime;
            currTime += elapsedTime;

            // update
            update(elapsedTime);

            // draw the screen
            Graphics2D g = screen.getGraphics();
            draw(g);
            g.dispose();
            screen.update();

            // don't take a nap! run as fast as possible
            /*try {
                Thread.sleep(20);
            }
            catch (InterruptedException ex) { }*/
        }
    }


    /**
	     Method <code>update</code>
        Updates the state of the game/animation based on the
        amount of elapsed time that has passed.
		  @param elapsedTime is a <code>long</code> value that is the
		  	amount of elapsed time that has passed.
    */
    public void update(long elapsedTime) {
        // do nothing
    }


    /**
	     Method <code>draw</code>.
        Draws to the screen. Subclasses must override this
        method.
    */
    public abstract void draw(Graphics2D g);
}
