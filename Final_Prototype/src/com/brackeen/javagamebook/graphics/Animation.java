   package com.brackeen.javagamebook.graphics;

   import java.awt.Image;
   import java.util.ArrayList;


/**
   @(#)Animation.java
 
    The <code>Animation</code> class manages a series of <code>Image</code> (frames) and
    the amount of time to display each frame.
	 
   @author Alejandro Federico Gastelum Callahan - A00806309
   @author Joel Ernesto García Verástica - A00806027
   @author Hilda Elisa Saldaña - A01088359
   @author Norma Susej Escobedo Pérez - A00805387
	
	@version 3.00 2010/11/21
*/

   public class Animation {
   
      private ArrayList frames;
      private int currFrameIndex;
      private long animTime;
      private long totalDuration;
   
   
    /**
        Creates a new, empty <code>Animation</code>.
    */
      public Animation() {
         this(new ArrayList(), 0);
      }
   
   
   /**
        Creates a new <code>Animation</code> with the specified <code>ArrayList</code>
   	  of frames and the <code>long</code> totalDuration of the animation.
    */
      private Animation(ArrayList frames, long totalDuration) {
         this.frames = frames;
         this.totalDuration = totalDuration;
         start();
      }
   
   /**
        Method <I>clone</I> returns a duplicate of this animation.
   	  The list of frames are shared between the two Animations, 
   	  but each Animation can be animated independently.
   	  
   	  @return a new <code>Animation</code> that is the duplicate.
    */
   
    
      public Object clone() {
         return new Animation(frames, totalDuration);
      }
   
   
   	/**
        Method <I>addFrame</I> Adds an image to the animation 
   	  with the specified duration (time to display the image).
   
   	  
   	  @param image is the <code>Image</code> that is going to be add
   	  @param duration is a <code>long</code> that indicates the time that the image 
   	  		is going to be display
    */
   
      public synchronized void addFrame(Image image,
        long duration)
      {
         totalDuration += duration;
         frames.add(new AnimFrame(image, totalDuration));
      }
   
   
    /**
    		Method <I>start</I> starts this animation over
   		from the beginning.
    */
      public synchronized void start() {
         animTime = 0;
         currFrameIndex = 0;
      }
   
   
    /**
        Method <I>update</I> updates this animation's current
		  image (frame), if neccesary.
		  @param elapsedTime is a <code>long</code> variable that
		  		indicates the elapsed time.
    */
      public synchronized void update(long elapsedTime) {
         if (frames.size() > 1) {
            animTime += elapsedTime;
         
            if (animTime >= totalDuration) {
               animTime = animTime % totalDuration;
               currFrameIndex = 0;
            }
         
            while (animTime > getFrame(currFrameIndex).endTime) {
               currFrameIndex++;
            }
         }
      }
   
   
    /**
	 	  Method <I>getImage</I> gets this Animation's current image.
		  @return an <code>Image</code> if this animation has images.
		  @return null if this animation has no images.
		    
    */
      public synchronized Image getImage() {
         if (frames.size() == 0) {
            return null;
         }
         else {
            return getFrame(currFrameIndex).image;
         }
      }
   
   
      private AnimFrame getFrame(int i) {
         return (AnimFrame)frames.get(i);
      }
   
   
      private class AnimFrame {
      
         Image image;
         long endTime;
      
         public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
         }
      }
   }
