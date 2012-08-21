package com.brackeen.javagamebook.graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;

/**
   @(#)ScreenManager.java
 
    The <code>ScreenManager</code> class manages initializing and displaying
    full screen graphics modes.
	 
   @author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21
*/


public class ScreenManager {

    private GraphicsDevice device;

    /**
        Creates a new <code>ScreenManager</code> object.
    */
    public ScreenManager() {
        GraphicsEnvironment environment =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
    }


    /**
	 	  Method <I>getCompatibleDisplayModes</I> 
		  @return a list of compatible <code>DisplayMode</code> for
		  	the default device on the system.
    */
    public DisplayMode[] getCompatibleDisplayModes() {
        return device.getDisplayModes();
    }


    /**
	 	  Method <code>findFirstCompatibleMode</code>
        Returns the first compatible mode in a list of modes.
        Returns null if no modes are compatible.
		  @param modes[] is a list of <code>DisplayMode</code>.
		  @return a <code>DisplayMode</code> wich is the first compatible in the list.
		  @return <code>null</code> if no modes in the list are compatible		  
    */
    public DisplayMode findFirstCompatibleMode(
        DisplayMode modes[])
    {
        DisplayMode goodModes[] = device.getDisplayModes();
        for (int i = 0; i < modes.length; i++) {
            for (int j = 0; j < goodModes.length; j++) {
                if (displayModesMatch(modes[i], goodModes[j])) {
                    return modes[i];
                }
            }

        }

        return null;
    }


    /**
	     Method <code>getCurrentDisplayMode</code>
        Returns the current display mode.
		  @return the current <code>DisplayMode</code>
    */
    public DisplayMode getCurrentDisplayMode() {
        return device.getDisplayMode();
    }


    /**
	 	  Method <code>displayModesMatch</code>
        Determines if two display modes "match". Two display
        modes match if they have the same resolution, bit depth,
        and refresh rate. The bit depth is ignored if one of the
        modes has a bit depth of DisplayMode.BIT_DEPTH_MULTI.
        Likewise, the refresh rate is ignored if one of the
        modes has a refresh rate of
        DisplayMode.REFRESH_RATE_UNKNOWN.
		  @param mode1 is a <code>DisplayMode</code>
		  @param mode2 is a <code>DisplayMode</code>
		  @return a boolean value wich is <code>true</code> if the display
			  modes match, <code>false</code> if not.
    */
    public boolean displayModesMatch(DisplayMode mode1,
        DisplayMode mode2)

    {
        if (mode1.getWidth() != mode2.getWidth() ||
            mode1.getHeight() != mode2.getHeight())
        {
            return false;
        }

        if (mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
            mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
            mode1.getBitDepth() != mode2.getBitDepth())
        {
            return false;
        }

        if (mode1.getRefreshRate() !=
            DisplayMode.REFRESH_RATE_UNKNOWN &&
            mode2.getRefreshRate() !=
            DisplayMode.REFRESH_RATE_UNKNOWN &&
            mode1.getRefreshRate() != mode2.getRefreshRate())
         {
             return false;
         }

         return true;
    }


    /**
	     Method <code>setFullScreen</code>
        Enters full screen mode and changes the display mode.
        If the specified display mode is null or not compatible
        with this device, or if the display mode cannot be
        changed on this system, the current display mode is used.
        <p>
        The display uses a BufferStrategy with 2 buffers.
		  @param displayMode is the <code>DisplayMode</code> that is going 
		  		to be display
    */
    public void setFullScreen(DisplayMode displayMode) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);

        device.setFullScreenWindow(frame);

        if (displayMode != null &&
            device.isDisplayChangeSupported())
        {
            try {
                device.setDisplayMode(displayMode);
            }
            catch (IllegalArgumentException ex) { }
            // fix for mac os x
            frame.setSize(displayMode.getWidth(),
                displayMode.getHeight());
        }
        // avoid potential deadlock in 1.4.1_02
        try {
            EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    frame.createBufferStrategy(2);
                }
            });
        }
        catch (InterruptedException ex) {
            // ignore
        }
        catch (InvocationTargetException  ex) {
            // ignore
        }


    }


    /**
	 	  Method <code>getGraphics</code>
        Gets the graphics context for the display. The
        ScreenManager uses double buffering, so applications must
        call update() to show any graphics drawn.
        <p>
        The application must dispose of the graphics object.
		  @return the <code>Graphics2D</code> context for the display.
    */
    public Graphics2D getGraphics() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            BufferStrategy strategy = window.getBufferStrategy();
            return (Graphics2D)strategy.getDrawGraphics();
        }
        else {
            return null;
        }
    }


    /**
	 	  Method <code>update</code>
        Updates the display.
    */
    public void update() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            BufferStrategy strategy = window.getBufferStrategy();
            if (!strategy.contentsLost()) {
                strategy.show();
            }
        }
        // Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();
    }


    /**
	 	  Method <code>getFullScreenWindow</code>
        Returns the window currently used in full screen mode.
        Returns null if the device is not in full screen mode.
		  @return the <code>JFrame</code> of the window currently used
		   in full screen or <code>null</code> if the device is not in full
			screen.
		  
    */
    public JFrame getFullScreenWindow() {
        return (JFrame)device.getFullScreenWindow();
    }


    /**
	     Method <code>getWidth</code>
        Gets the width of the window currently used in full
        screen mode. Returns 0 if the device is not in full
        screen mode.
		  @return an <code>Integer</code> value wich is the width 
		  	of the window used in full sreen or 0 is the device is 
			not in full screen.
    */
    public int getWidth() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            return window.getWidth();
        }
        else {
            return 0;
        }
    }


    /**
	     Method <code>getHeight</code>
        Gets the height of the window currently used in full
        screen mode. Returns 0 if the device is not in full
        screen mode.
		  @return an <code>Integer</code> value wich is the height 
		  	of the window used in full sreen or 0 is the device is 
			not in full screen.
    */
    public int getHeight() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            return window.getHeight();
        }
        else {
            return 0;
        }
    }


    /**
	 	  Method <code>restoreScreen</code>
        Restores the screen's display mode.
    */
    public void restoreScreen() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            window.dispose();
        }
        device.setFullScreenWindow(null);
    }


    /**
	 	  Method <code>createCompatibleImage</code>
        Creates an image compatible with the current display.
		  @param w is an <code>Integer</code> value
		  @param h is an <code>Integer</code> value
		  @param transparency is an <code>Integer</code> value
		  @return a <code>BufferedImage</code> that is the image 
		   compatible with the current display
    */
    public BufferedImage createCompatibleImage(int w, int h,
        int transparancy)
    {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            GraphicsConfiguration gc =
                window.getGraphicsConfiguration();
            return gc.createCompatibleImage(w, h, transparancy);
        }
        return null;
    }
}
