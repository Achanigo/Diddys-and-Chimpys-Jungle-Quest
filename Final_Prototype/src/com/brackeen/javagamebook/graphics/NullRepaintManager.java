package com.brackeen.javagamebook.graphics;

import javax.swing.RepaintManager;
import javax.swing.JComponent;

/**
   @(#)NullRepaintManager.java
 
    The <code>NullRepaintManager</code> is a RepaintManager that doesn't
    do any repainting. Useful when all the rendering is done
    manually by the application.

	 
   @author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21
*/

public class NullRepaintManager extends RepaintManager {

    /**
	 	  Method <I>install</I> installs the NullRepaintManager.
    */
    public static void install() {
        RepaintManager repaintManager = new NullRepaintManager();
        repaintManager.setDoubleBufferingEnabled(false);
        RepaintManager.setCurrentManager(repaintManager);
    }

	/**
	 	  Method <I>addInvalidComponent</I>
		  Mark the component as in need of layout and queue a runnable
		  for the event dispatching thread that will validate the components
		  first isValidateRoot() ancestor.
		  @param c its a <code>JComponent</code>
    */
    public void addInvalidComponent(JComponent c) {
        // do nothing
    }
	 
	/**
	 	  Method <I>addDirtyRegion</I>  adds a component in the 
		  list of components that should be refreshed.
		  @param c its a <code>JComponent</code>
		  @param x its an <code>Integer</code>
		  @param y its an <code>Integer</code>
		  @param w its an <code>Integer</code>
		  @param h its an <code>Integer</code>
    */
    public void addDirtyRegion(JComponent c, int x, int y,
        int w, int h)
    {
        // do nothing
    }

	/**
	 	  Method <I>markCompletelyDirty</I>  marks a component 
		  completely dirty.
		  @param c its a <code>JComponent</code>
	 */
    public void markCompletelyDirty(JComponent c) {
        // do nothing
    }
	 
    /**
	 	  Method <I>paintDirtyRegions</I>  
		  Paint all of the components that have been marked dirty.
	  */
    public void paintDirtyRegions() {
        // do nothing
    }

}
