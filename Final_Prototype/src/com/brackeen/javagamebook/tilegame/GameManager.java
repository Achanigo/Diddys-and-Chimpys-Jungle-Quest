/**
 * @(#)GameManager.java
 *
 * Juego JFrame application
 *
 * Prototipo Final
 *
 * @author Alejandro Federico Gastelum Callahan - A00806309
 * @author Joel Ernesto García Verástica - A00806027
 * @author Hilda Elisa Saldaña - A01088359
 * @author Norma Susej Escobedo Pérez - A00805387
 */
   package com.brackeen.javagamebook.tilegame;

   import java.awt.*;
   import java.awt.event.KeyEvent;
   import java.util.Iterator;
   import java.util.*;

   import javax.sound.midi.Sequence;
   import javax.sound.midi.Sequencer;
   import javax.sound.sampled.AudioFormat;

   import com.brackeen.javagamebook.graphics.*;
   import com.brackeen.javagamebook.sound.*;
   import com.brackeen.javagamebook.input.*;
   import com.brackeen.javagamebook.test.GameCore;
   import com.brackeen.javagamebook.tilegame.sprites.*;

/**
 * GameManager manages all parts of the game.
 */
    public class GameManager extends GameCore {
   
   //Main method of the game, this will run the entire game
       public static void main(String[] args) {
         new GameManager().run();
      }
   
    // uncompressed, 44100Hz, 16-bit, mono, signed, little-endian
      private static final AudioFormat PLAYBACK_FORMAT =
        new AudioFormat(44100, 16, 1, true, false);
   
      private static final int DRUM_TRACK = 1;
   
      public static final float GRAVITY = 0.002f;
   
      private Point pointCache = new Point(); 
      //Represents a point x,y, in the space
   	
      private TileMap map;  
      //The level map
   	
      private int fondo;
   	//Variable that saves the background number
   	
      private int soundtrack;
   	//Variable that saves the soundtrack number
   	
      private MidiPlayer midiPlayer;  
      //Reproduces midi sounds
   	
      private SoundManager soundManager; 
      //Manages the sound of the game
   	
      private ResourceManager resourceManager;  
      //This provide us the necessary resources
   	
      private Sound prizeSound;  
      //Sound emitted when grabbing a power up
   	
      private Sound boopSound;  
      //Sound emitted when stomping an enemy
   	
      private int totalBananas;  
      //Number of bananas that the player has
   	
      private int vidas;    
      //Lives given in the game
   	
      private int resultado;  
      //Variable that gives the result of the operation
   	
      private int contUpdate;
   	//Variable to check a count of the swith update
   	
      private InputManager inputManager;  
      //Manages the inputs of the game
   	
      private TileMapRenderer renderer;  
      //Draws the map
   	
      private Stack oldBanana;  
      //Stack that saves the erased bananas
   	
      private int numMenu; 
      // Says the number of menu slide
   	
      private int dificultad;  
   	//Says game level 
   	
      private boolean mision;  
   	//Shows the mission of the game
   	
      private boolean menu; 
      //Says if the menu is active
   	
      private boolean juego; 
      //Indicate if the game started
   	
      private boolean men1; 
      //Iddicate if the start option is active
   	
      private boolean men2;
   	//Indicate if the instructions option is active
   	
      private boolean men3;
   	//Indicate if the exit is active
   	
      private boolean instruc1;
   	//Indicate if the first instruction slide is acive
   	
      private boolean instruc2;
   	//Indicate if the second instruction slide is acive
   	
      private boolean instruc3;
   	//Indicate if the third instruction slide is active
   	
      private boolean instruc4;
   	//Indicate if the fourth instruction slide is active
   	
      private boolean instruc5;
   	//Indicate if the fifth instruction slide is active
   	
      private boolean sure;
   	//Says if the player wants to close the game
   	
      private boolean dificultadJuego;
   	//Says the dificulty of the game
   	
      private boolean atrasDificultad;
   	//Says if the player wants to return to the menu
   	
      private boolean isSure;
   	//Says if the player really wants to close the game
   	
      private boolean isNotSure;
   	//Says if the player does not wants to close the game
   	
      private boolean principiante;
   	//Says if the player selected the easy mode
   	
      private boolean intermedio;
   	//Says if the player selected the middle mode
   	
      private boolean experto;
   	//Says if the player selected the difficult mode
   	
      private int numSlideInstruc;
   	//Indicate the instruct slide showing to the player
   	
      private int numTypeInstruc;
   	//Indicate if is back or previows the next slide instruct
   	
      private int numTypeDificulty;
   	//Inticate the dificulty that the player select
   	
      private boolean instructions; 
      //Indicate if the instructions are active
   	
      private boolean pausar;  
      //Boolean that lets you know  when its paused   
   	
      private boolean sonido;  
      //Boolean that lets you know  when its sound
   	
      private boolean incorrect;
   	//Boolean flag to know if the player arrived to the door 
   	//		with an incorrect number of bananas
   	
      private boolean finJuego;
   	//Boolean flag to know when the player has cleared 
   	//		the game
   	
      private boolean kongoFall;
   	//Boolean flag to know if Kongo fell of the bridge.
   	
      private int numA; 
   	//Recieves a random number
   	
      private int numB; 
   	//Recieves a random number
   	
      private String signo; 
   	//Saves the kind of operation
   	     
      private GameAction instruc; 
   	//GameAction for showing up the screen where shows the mission
   			
      private GameAction moveLeft;   
      //GameAction that moves the player to the left
   	
      private GameAction moveRight;  
      //GameAction that moves the player to the right
   	
      private GameAction jump;   
      //GameAction that makes the player jump.
   	
      private GameAction exit;   
      //GameAction for exiting the game
   	
      private GameAction pause;  
      //GameAction for pausing the game
   	
      private GameAction tirar;  
      //GameAction for dropping bananas
   	
      private GameAction pressEnter; 
      //Says if press enter 
   	
      private GameAction moveMenuUp; 
      // Says if press up
   	
      private GameAction moveMenuDown; 
      //Says if press down   
   	
      private GameAction mute;
   	//Enables or disables sounds 
   	
     /*
   	* Initialize all the variables that are going to be used in 
   	* the game
   	*/
       public void init() {
         super.init();
      
         instruc1 = false;
         instruc2 = false;
         instruc3 = false;
         instruc4 = false;
         instruc5 = false;
         dificultadJuego = false;
         atrasDificultad = false;
         isSure = false;
         isNotSure = false;
         principiante = false;
         intermedio = false;
         experto = false;
         menu = false;
         sure = false;
         juego = false;
         instructions = false;
         mision = true;
         men1 = false;
         men2 = false;
         men3 = false;
         incorrect = false;
         finJuego = false;
         kongoFall = false;
         sonido = true;
         
         fondo = 1;
         soundtrack = 0;
         numMenu = 1;   
         dificultad = 0;  
         numSlideInstruc = 1;
         numTypeInstruc = 1;
         numTypeDificulty = 1;
        
        //set up input manager
         initInput();
      
        //start resource manager
         resourceManager = new ResourceManager(
            screen.getFullScreenWindow().getGraphicsConfiguration());
      
        //load resources
         renderer = new TileMapRenderer(); 
         renderer.setBackground(
               resourceManager.loadImage("background_1.jpg"));
             
        //load first map
         map = resourceManager.loadNextMap();
        
        //Initializes the bananas and lives
         totalBananas = 0;
         vidas = 5;
        
         oldBanana = new Stack();
         
        //load sounds
         soundManager = new SoundManager(PLAYBACK_FORMAT);
         prizeSound = soundManager.getSound("sounds/bup.wav");
         boopSound = soundManager.getSound("sounds/prize.wav");
        
        //start music
         midiPlayer = new MidiPlayer();
         Sequence sequence =
            midiPlayer.getSequence("sounds/Soundtrack_"+soundtrack+".mid");
         midiPlayer.play(sequence, true);
         toggleDrumPlayback();
      }
   
    /**
     *stop closes any resurces used by the GameManager.
     */
       public void stop() {
         super.stop();
         midiPlayer.close();
         soundManager.close();
      }
   
   /*
    *initInput Initiallizes the inputs of the game
    */
       private void initInput() {
      //Defines the game actions
         moveLeft = new GameAction("moveLeft");
         moveRight = new GameAction("moveRight");
         jump = new GameAction("jump",
            GameAction.DETECT_INITAL_PRESS_ONLY);
         exit = new GameAction("exit",
            GameAction.DETECT_INITAL_PRESS_ONLY);
         pause = new GameAction("pause",
            GameAction.DETECT_INITAL_PRESS_ONLY);
         tirar = new GameAction("tirar",
            GameAction.DETECT_INITAL_PRESS_ONLY);
         moveMenuUp = new GameAction("menuUp",
            GameAction.DETECT_INITAL_PRESS_ONLY);   
         moveMenuDown = new GameAction("menuDown",
            GameAction.DETECT_INITAL_PRESS_ONLY);      
         pressEnter = new GameAction("pressEnter",
            GameAction.DETECT_INITAL_PRESS_ONLY);   
         instruc = new GameAction("instrucc", 
            GameAction.DETECT_INITAL_PRESS_ONLY);
         mute = new GameAction("mute", 
            GameAction.DETECT_INITAL_PRESS_ONLY);
               
         inputManager = new InputManager(
            screen.getFullScreenWindow());
         inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
       
       //Sets the buttons of the game actions
         inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
         inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
         inputManager.mapToKey(jump, KeyEvent.VK_SPACE);
         inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
         inputManager.mapToKey(pause, KeyEvent.VK_P);
         inputManager.mapToKey(tirar, KeyEvent.VK_T);
         inputManager.mapToKey(pressEnter, KeyEvent.VK_ENTER);
         inputManager.mapToKey(moveMenuDown, KeyEvent.VK_DOWN);
         inputManager.mapToKey(moveMenuUp, KeyEvent.VK_UP);
         inputManager.mapToKey(instruc, KeyEvent.VK_M);
         inputManager.mapToKey(mute, KeyEvent.VK_S);
         	
         signo = ""; 
      }
   
    /*
     * Checks what button is pressed, and checks the correct input
     *
     */
       private void checkInput(long elapsedTime) {
      
      //If the player presses the exit button, the game finishes
         if (exit.isPressed()) {
            stop();
         }
         
      //If the player presses the mute button, the game sound is turned off
         if(mute.isPressed()) {
            sonido = !sonido;
            if(sonido) {
               Sequence sequence =
                  midiPlayer.getSequence("sounds/Soundtrack_"+soundtrack+".mid");
               midiPlayer.play(sequence, true);
            }
            else {
               midiPlayer.stop();
            }
         }
         
      //If the left key is pressed 	
         if(moveLeft.isPressed()) {
         //Moving the instructions 
            if(instructions && !(instruc1)) {
               numTypeInstruc = 2;
            }
         //Moving the exit options   
            if(sure){
               numTypeInstruc = 2;
            }
         }
         
         //If the right key is pressed
         if(moveRight.isPressed()) {
         //Moving the instructions
            if(instructions) {
               numTypeInstruc = 1;
            }
            if(sure){
            //Moving the exit options
               numTypeInstruc = 1;
            }
         }
         
      //If the player presses the ENTER
         if ( pressEnter.isPressed()) {
         //Shows the image presentation
            if(!menu && !juego) {        
               menu = true;
            }
            
         //If the player arrives to the door whit the correct alert image   
            if(incorrect) {
               incorrect = false;
            }   
         
         //If the player press the start option
            if(men1) {
               dificultadJuego = true;
               menu = false;
            }
         
         //If the player press the instructions option
            if(men2) {
               instructions = true;
               menu = false;
            }
         
         //If the player press the exit option
            if(men3) {
               sure = true;   
            }
         
         //If the player wants to play the easy mode   
            if(principiante) {
               signo = "+";
               numA = (int)(Math.random() * 50 + 1);
               numB = (int)(Math.random() * 50 + 1);
               while ( numA + numB > 100){
                  numA = (int)(Math.random() * 50 + 1);
                  numB = (int)(Math.random() * 50 + 1);
               }
               resultado = numA + numB;
               juego = true;
               dificultad = 1;
               dificultadJuego = false;
            }
         
         //If the player wants to play the middle mode   
            if (intermedio) {
               signo = "-";
               numA = (int)(Math.random() * 50 + 1);
               numB = (int)(Math.random() * 50 + 1);
               while ( ! (numA >= numB)){
                  numA = (int)(Math.random() * 50 + 1);
                  numB = (int)(Math.random() * 50 + 1);
               }
               resultado = numA - numB;
               juego = true;
               dificultad = 2;
               dificultadJuego = false;
            }
         
         //If the player wants to play the expert mode   
            if(experto) {
               signo = "*";
               numA = (int)(Math.random() * 10);
               numB = (int)(Math.random() * 10);
               resultado = numA * numB;
               juego = true;
               dificultad = 3;
               dificultadJuego = false;
            }
         
         //If the player press the back option   
            if(atrasDificultad) {
               numTypeDificulty = 1;
               numMenu = 1;
               atrasDificultad = false;
               dificultadJuego = false;
               menu = true;
            }
         
         //If the player wants to go to the second instruction slide      
            if(instruc1) {
               numSlideInstruc = 2;
            }
            
         //If the player wants to go to the first or third instruction slide      
            if(instruc2) {
               switch (numTypeInstruc) {
                  case 1: 
                     numSlideInstruc = 3;
                     break;
                  case 2: numSlideInstruc = 1;
                     break; 
               }
            }
         
         //If the player wants to go to the second or fourth instruction slide   
            if(instruc3) {
               switch (numTypeInstruc) {
                  case 1: numSlideInstruc = 4;
                     break;
                  case 2: 
                     numSlideInstruc = 2;
                     break; 
               }
            }
         
         //If the player wants to go to the third or fifth instruction slide   
            if(instruc4) {
               switch (numTypeInstruc) {
                  case 1: numSlideInstruc = 5;
                     break;
                  case 2: numSlideInstruc = 3;
                     break; 
               }
            }
         
         //If the player wants to go to the fourth instruction slide or to the menu   
            if(instruc5) {
               switch (numTypeInstruc) {
                  case 1: 
                     {
                        numSlideInstruc = 1;
                        instruc5 = false;
                        instructions = false;
                        menu = true;
                     }
                     break;
                  case 2: numSlideInstruc = 4;
                     break;
               }   
            }
         
         //If the player really wants to close the game   
            if(isSure) {
               stop();
            }
         
         //If the player does not want to close the game and is in the exit option   
            if(isNotSure) {
               menu = true;
               sure = false;
               isNotSure = false;
            }
         }
            
      //If the player presses the UP key
         if (moveMenuUp.isPressed()) {
         //The menu goes to the next menu option
            if(numMenu >1) {
               numMenu --;
            }
         //The player goes to the next dificulty option   
            if (dificultadJuego) {
               switch (numTypeDificulty) {
                  case 2: numTypeDificulty = 1;
                     break;
                  case 3: numTypeDificulty = 2; 
                     break;
                  case 4: numTypeDificulty = 3;
                     break;
               }
            }
         }
      
      //If the player presses the DOWN key
         if ( moveMenuDown.isPressed()) {
         //The player goes to the previous menu option
            if(numMenu < 3) {
               numMenu ++;
            }
         //The player goes to the previous dificulty option   
            if(dificultadJuego) {
               switch (numTypeDificulty) {
                  case 1: numTypeDificulty = 2;
                     break;
                  case 2: numTypeDificulty = 3;
                     break;
                  case 3: numTypeDificulty = 4;
                     break;    
               }
            }
         }
         
      //If pause is pressed, the game continues or freezes
         if (pause.isPressed()) {
          if(!mision) {        
				pausar = !pausar;
			 }
         }
      
         if(juego) {   
         //If instruc is pressed, it shows or disappears the mision	
            if(instruc.isPressed()) {
             if(!pausar) {        
				   mision = !(mision);
					}
            }
         
         //If player has lives and isn't paused or isn't reading the mission problem
            if(!(pausar ||mision) && vidas > 0) {
               Player player = (Player)map.getPlayer();
            
            //If player wants to drop bananas
               if (tirar.isPressed()) {
               //If you have bananas, counter drops by one      
                  if(totalBananas > 0) {        
                     totalBananas--;
                  //We pop a banana of the erased banana stacks and draws it.
                     if(!oldBanana.empty()) {
                        Sprite temp = (Sprite)oldBanana.pop();
                        resourceManager.reloadSprite(map, temp);
                     }
                  }
               }
            
            //If the player is alive
               if (player.isAlive()) {
                  float velocityX = 0;
               //The player moves in the wanted direction
                  if (moveLeft.isPressed()) {
                     velocityX-=player.getMaxSpeed();
                  } 
                  if (moveRight.isPressed()) {
                     velocityX+=player.getMaxSpeed();
                  }
               
               //The player jumps
                  if (jump.isPressed()) {
                     player.jump(false);
                  }
                  player.setVelocityX(velocityX);
               }
            }
         }
      }
      
   /*
    * Draws the menu in the screen
    * @param Graphics2D
    * @see resourceManager.loadImage
    */	
       public void menuInicial(Graphics2D g) {
         g.drawImage(resourceManager.loadImage("menu.png"), 0, 0, screen.getWidth(),
                         	screen.getHeight(), null);
         //Changes the banana's position giving a feedback to the player   					
         switch (numMenu) {
            case 1:
               {
                  g.drawImage(resourceManager.loadImage("banana_menu.png"), 30, 380, null); 
                  men1 = true;
                  men2 = false;
                  men3 = false;
               }
               break;
            case 2: 
               {
                  men1 = false;
                  men2 = true;
                  men3 = false;
                  g.drawImage(resourceManager.loadImage("banana_menu.png"), 30, 470, null); 
               }
               break;
            case 3: 
               {
                  men1 = false; 
                  men2 = false;
                  men3 = true;
                  g.drawImage(resourceManager.loadImage("banana_menu.png"), 455, 510, null); 
               }
               break;
         }
      } 
      
   /*
    * Draws the instructions in the screen
    * @param Graphics2D
    * @see resourceManager.loadImage
    */	
       public void instructionsMenu(Graphics2D g) {
         switch (numSlideInstruc){
         //Moving the banana giving a feedback to the player
            case 1:
               {
                  instruc1 = true;
                  instruc2 = false;
                  instruc3 = false;
                  instruc4 = false;
                  instruc5 = false;
                  //Draws the slide instruction
                  g.drawImage(resourceManager.loadImage("instrucciones_1.png"), 0, 0, screen.getWidth(),
                        	screen.getHeight(), null); 
                  g.drawImage(resourceManager.loadImage("banana_instrucciones.png"), 525, 540, null);   	
               }
               break;
            case 2: 
               {
                  instruc1 = false;
                  instruc2 = true;
                  instruc3 = false;
                  instruc4 = false;
                  instruc5 = false;
                  //Draws the slide instruction
                  g.drawImage(resourceManager.loadImage("instrucciones_2.png"), 0, 0, screen.getWidth(),
                        	screen.getHeight(), null);
                  switch(numTypeInstruc){
                  //Indicate if the player press back or next
                     case 1: 
                        g.drawImage(resourceManager.loadImage("banana_instrucciones.png"), 525, 540, null); 
                        break;
                     case 2: 
                        g.drawImage(resourceManager.loadImage("banana_instrucciones.png"), 310, 540, null); 
                        break;
                  }
               }
               break;
            case 3: 
               {
               //Draws the slide instruction
                  instruc1 = false;
                  instruc2 = false;
                  instruc3 = true;
                  instruc4 = false;
                  instruc5 = false;
                  g.drawImage(resourceManager.loadImage("instrucciones_3.png"), 0, 0, screen.getWidth(),
                        	screen.getHeight(), null);
                  switch(numTypeInstruc){
                  //Indicate if the player press back or next
                     case 1: 
                        g.drawImage(resourceManager.loadImage("banana_instrucciones.png"), 525, 540, null); 
                        break;
                     case 2: 
                        g.drawImage(resourceManager.loadImage("banana_instrucciones.png"), 310, 540, null); 
                        break;
                  }
               }
               break;
            case 4: 
               {
                  instruc1 = false;
                  instruc2 = false;
                  instruc3 = false;
                  instruc4 = true;
                  instruc5 = false;
               //Draws the slide instruction   
                  g.drawImage(resourceManager.loadImage("instrucciones_4.png"), 0, 0, screen.getWidth(),
                        	screen.getHeight(), null);
                  switch(numTypeInstruc){
                  //Indicate if the player press back or next
                     case 1: 
                        g.drawImage(resourceManager.loadImage("banana_instrucciones.png"), 525, 540, null); 
                        break;
                     case 2: 
                        g.drawImage(resourceManager.loadImage("banana_instrucciones.png"), 310, 540, null); 
                        break;
                  }
               }
               break;
            case 5: 
               {
                  instruc1 = false;
                  instruc2 = false;
                  instruc3 = false;
                  instruc4 = false;
                  instruc5 = true;
               //Draw the slide instruction   
                  g.drawImage(resourceManager.loadImage("instrucciones_5.png"), 0, 0, screen.getWidth(),
                        	screen.getHeight(), null);
                  switch(numTypeInstruc){
                  //Indicate if the player press back or next
                     case 1: 
                        g.drawImage(resourceManager.loadImage("banana_instrucciones.png"), 525, 540, null); 
                        break;
                     case 2: 
                        g.drawImage(resourceManager.loadImage("banana_instrucciones.png"), 310, 540, null); 
                        break;
                  }
               }
               break;
         }
      }
      
   /*
    * Draws the exit slide in the screen
    * @param Graphics2D
    * @see resourceManager.loadImage
    */	
       public void sureExit(Graphics2D g) {
         g.drawImage(resourceManager.loadImage("seguro_salir.png"), 0, 0,screen.getWidth(),
               	screen.getHeight(), null);
         switch(numTypeInstruc) {
            case 1: 
               {
                  isSure = false;
                  isNotSure = true;
                  g.drawImage(resourceManager.loadImage("banana_instrucciones.png"), 500, 335, null); 
               }
               break;
            case 2: 
               {
                  isSure = true;
                  isNotSure = false;
                  g.drawImage(resourceManager.loadImage("banana_instrucciones.png"), 110, 335, null); 
               }
               break;	 
         }  		
      }
      
    /*
    * Draws the dificulty in the screen
    * @param Graphics2D
    * @see resourceManager.loadImage
    */
       public void dificultadMenu(Graphics2D g) {
         g.drawImage(resourceManager.loadImage("dificultad.png"), 0, 0,screen.getWidth(),
               	screen.getHeight(), null);
         switch(numTypeDificulty) {
            case 1: 
               {
                  principiante = true;
                  intermedio = false;
                  experto = false;
                  atrasDificultad = false;
                  g.drawImage(resourceManager.loadImage("banana_menu.png"), 145, 170, null); 
               }
               break;
            case 2: 
               {
                  principiante = false;
                  intermedio = true;
                  experto = false;
                  atrasDificultad = false;
                  g.drawImage(resourceManager.loadImage("banana_menu.png"), 145, 265, null); 
               }
               break;
            case 3: 
               {
                  principiante = false;
                  intermedio = false;
                  experto = true;
                  atrasDificultad = false;
                  g.drawImage(resourceManager.loadImage("banana_menu.png"), 145, 355, null); 
               }
               break;   
            case 4:
               {
                  principiante = false;
                  intermedio = false;
                  experto = false;
                  atrasDificultad = true;
                  g.drawImage(resourceManager.loadImage("banana_menu.png"), 75, 510, null); 
               }   
         }		
      }
      
   /*
    * Draws the game in the screen
    * @param Graphics2D
    */ 
       public void draw(Graphics2D g) {    
      //Show the menu
         if (menu) {
            menuInicial(g);
         }
         
      //Show the instructions   
         if (instructions) {
            instructionsMenu(g);
         }
         
      //Shows the dificulty
         if(dificultadJuego) {
            dificultadMenu(g);
         }	  
         
      //Ask the player if really want to quit the game   
         if(sure) {
            sureExit(g);   
         }
         
      //Draw the presentation of the team
         if(!(menu) && !(instructions) && !(dificultadJuego)) {
            g.drawImage(resourceManager.loadImage("presentation.png"), 0, 0,screen.getWidth(),
               	screen.getHeight(), null);
         }    
         
         if(juego) {
         
            principiante = false;  
            intermedio = false;
            experto = false;
         
            //If player is alive
            if( vidas > 0) {
            //Draws the level mao
               renderer.draw(g, map,
                  screen.getWidth(), screen.getHeight());
            
            //Draws the count of lives and bananas
               g.setColor(Color.black);
               g.drawString("x " + totalBananas, 725, 48);
               g.drawString("x " + vidas, 55, 48);
               g.drawImage(resourceManager.loadImage("star1.png"), 670, 15, null);
               g.drawImage(resourceManager.loadImage("heart1.png"), 0, 5, null);
            
            //If the game is paused, draw the pause menu
               if(pausar) {
                  g.drawImage(resourceManager.loadImage("pausa1.png"), 280, 200, null);
               }
               
            //In the player arrived to the door with an incorrect number of bananas, draw a warning
               if(incorrect) {
                  g.drawImage(resourceManager.loadImage("incorrect.png"), 230, 200, null);
               }
               
            //The problems in the easy mode		
               if(mision && dificultad == 1 && fondo != 10) {
                  g.setColor(Color.orange);
                  g.drawRect(65, 126, 665, 239);
                  g.fillRect(65, 126, 665, 239);
                  g.setColor(Color.BLACK);
                  g.drawString("Diddy tenía "+numA+" plátanos y por su cumpleaños Chimpy,", 68 , 150); 
                  g.drawString("le regaló "+numB+". Entonces guardaron los plátanos y se,", 68 , 180);
                  g.drawString("fueron a jugar.", 68 , 210);
                  g.drawString("El malvado Kongo encontró los plátanos y los regó por toda", 68 , 240);
                  g.drawString("la selva, ahora los plátanos de Diddy están revueltos con los", 68, 270);
                  g.drawString("demás.",68, 300);
                  g.drawString("Ayuda a Diddy a reunir únicamente sus plátanos. Recuerda", 68 , 330);
                  g.drawString("que él tenía "+numA+" y Chimpy le regaló "+numB+".", 68 , 360);	
               }
               
            //The problems in the midle mode	
               if(mision && dificultad == 2 && fondo != 10) {
                  g.setColor(Color.orange);
                  g.drawRect(65, 126, 665, 239);
                  g.fillRect(65, 126, 665, 239);
                  g.setColor(Color.BLACK);
                  g.drawString("Diddy tenía "+numA+" plátanos y como Chimpy cumplió años él", 68 , 150); 
                  g.drawString("le regaló "+numB+". Entonces guardó los plátanos y se,", 68 , 180);
                  g.drawString("fueron a jugar.", 68 , 210);
                  g.drawString("El malvado Kongo encontró los plátanos y los regó por toda", 68 , 240);
                  g.drawString("la selva, ahora los plátanos de Diddy están revueltos con los", 68, 270);
                  g.drawString("demás.",68, 300);
                  g.drawString("Ayuda a Diddy a reunir únicamente sus plátanos. Recuerda", 68 , 330);
                  g.drawString("que él tenía "+numA+" pero ya le regaló a Chimpy "+numB+".", 68 , 360);	
               }
               
            //The problems in the dificult mode   
               if(mision && dificultad == 3 && fondo != 10) {
                  g.setColor(Color.orange);
                  g.drawRect(65, 126, 665, 239);
                  g.fillRect(65, 126, 665, 239);
                  g.setColor(Color.BLACK);
                  g.drawString("Diddy tenía "+numA+" plátanos y por su cumpleaños Chimpy le", 68 , 150); 
                  g.drawString("regaló muchos, de modo que ahora Diddy tiene "+numB+" veces lo", 68 , 180);
                  g.drawString("que tenía. Entonces guardaron los plátanos y se,fueron a ", 68 , 210);
                  g.drawString("jugar. El malvado Kongo encontró los plátanos y los regó por ", 68 , 240);
                  g.drawString("toda la selva, ahora los plátanos de Diddy están revueltos ", 68, 270);
                  g.drawString("con los demás. Ayuda a Diddy a reunir únicamentes sus  ",68, 300);
                  g.drawString("plátanos. Recuerda que él tenía "+numA+", pero ahora tiene "+numB+"", 68 , 330);
                  g.drawString("veces la cantidad que tenía.", 68 , 360);	
               }
               
            //The Kongo game dificulty   
               if(mision && fondo == 10) {
                  g.setColor(Color.orange);
                  g.drawRect(65, 126, 665, 239);
                  g.fillRect(65, 126, 665, 239);
                  g.setColor(Color.BLACK);
                  g.drawString("Ha llegado la hora de la verdad, en esta misón tienes que ", 68 , 150); 
                  g.drawString("derrotar a Kongo. Es tu oportunidad de darle su merecido", 68 , 180);
                  g.drawString("por ser tan tramposo. Solo ten mucho cuidado, pueden", 68 , 210);
                  g.drawString("aparecer cosas que no esperas", 68 , 240);
               }	
            }
            
            //If player is death, draws the credits screen
            else {
               g.drawImage(resourceManager.loadImage("creditos.png"), 0, 0,screen.getWidth(),
                  									               screen.getHeight(), null);
            }
         }
        //Draws a mute image
         if(!sonido) {
            g.drawImage(resourceManager.loadImage("mute.png"), 640, 20, null);
         }
         if(finJuego) {
            g.drawImage(resourceManager.loadImage("creditos.png"), 0, 0,screen.getWidth(),
                  									                   screen.getHeight(), null);
         }
      }
   
    /**
     * Gets the current map
     * @return TileMap
     */
       public TileMap getMap() {
         return map;
      }
   
    /**
      *Turns on/off drum playback in the midi music  
   	*/
       public void toggleDrumPlayback() {
         Sequencer sequencer = midiPlayer.getSequencer();
         if (sequencer != null) {
            sequencer.setTrackMute(DRUM_TRACK,
                !sequencer.getTrackMute(DRUM_TRACK));
         }
      }
   
    /**
     * Gets the tile that a Sprites collides with. Only the
     * Sprite's X or Y should be changed, not both. Returns null
     * if no collision is detected.
     * @param sprite
     * @param newX
     * @param newY
     * @return Point
     * @see TileMapRenderer.pixelsToTiles
    */
       public Point getTileCollision(Sprite sprite,
        float newX, float newY)
      {
         float fromX = Math.min(sprite.getX(), newX);
         float fromY = Math.min(sprite.getY(), newY);
         float toX = Math.max(sprite.getX(), newX);
         float toY = Math.max(sprite.getY(), newY);
      
        // get the tile locations
         int fromTileX = TileMapRenderer.pixelsToTiles(fromX);
         int fromTileY = TileMapRenderer.pixelsToTiles(fromY);
         int toTileX = TileMapRenderer.pixelsToTiles(
            toX + sprite.getWidth() - 1);
         int toTileY = TileMapRenderer.pixelsToTiles(
            toY + sprite.getHeight() - 1);
      
        // check each tile for a collision
         for (int x=fromTileX; x<=toTileX; x++) {
            for (int y=fromTileY; y<=toTileY; y++) {
               if (x < 0 || x >= map.getWidth() ||
                    map.getTile(x, y) != null)
               {
                    // collision found, return the tile
                  pointCache.setLocation(x, y);
                  return pointCache;
               }
            }
         }
      
        // no collision found
         return null;
      }
   
    /**
        Checks if two Sprites collide with one another. Returns
        false if the two Sprites are the same. Returns false if
        one of the Sprites is a Creature that is not alive.
   	  @param Sprite
   	  @return boolean
    */
       public boolean isCollision(Sprite s1, Sprite s2) {
        // if the Sprites are the same, return false
         if (s1 == s2) {
            return false;
         }
      
        // if one of the Sprites is a dead Creature, return false
         if (s1 instanceof Creature && !((Creature)s1).isAlive()) {
            return false;
         }
         if (s2 instanceof Creature && !((Creature)s2).isAlive()) {
            return false;
         }
      
        // get the pixel location of the Sprites
         int s1x = Math.round(s1.getX());
         int s1y = Math.round(s1.getY());
         int s2x = Math.round(s2.getX());
         int s2y = Math.round(s2.getY());
      
        // check if the two sprites' boundaries intersect
         return (s1x < s2x + s2.getWidth() &&
            s2x < s1x + s1.getWidth() &&
            s1y < s2y + s2.getHeight() &&
            s2y < s1y + s1.getHeight());
      }
   
    /**
        Gets the Sprite that collides with the specified Sprite,
        or null if no Sprite collides with the specified Sprite.
   	  @ param Sprite
   	  @return Sprite
    */
       public Sprite getSpriteCollision(Sprite sprite) {
      
        // run through the list of Sprites
         Iterator i = map.getSprites();
         while (i.hasNext()) {
            Sprite otherSprite = (Sprite)i.next();
            if (isCollision(sprite, otherSprite)) {
                // collision found, return the Sprite
               return otherSprite;
            }
         }
      
        // no collision found
         return null;
      }
   
   
    /**
        Updates Animation, position, and velocity of all Sprites
        in the current map.
   	  @param long
    */
       public void update(long elapsedTime) {
      // get keyboard input
         checkInput(elapsedTime);
         if(!(pausar || mision || finJuego)){
            Creature player = (Creature)map.getPlayer();
         
         
         // player is dead! start map over
            if (player.getState() == Creature.STATE_DEAD) {
               map = resourceManager.reloadMap();
               return;
            }
         
         // update player
            updateCreature(player, elapsedTime);
            player.update(elapsedTime);
         
         // update other sprites
            Iterator i = map.getSprites();
            while (i.hasNext()) {
               Sprite sprite = (Sprite)i.next();
               if (sprite instanceof Creature) {
                  Creature creature = (Creature)sprite;
                  if (creature.getState() == Creature.STATE_DEAD) {
                     i.remove();
                  }
                  else {
                     updateCreature(creature, elapsedTime);
                  }
               }
               if(sprite instanceof Pineapple){
                  Pineapple pine = (Pineapple)sprite;
                  if(pine.getState()) {
                     pine.setInitialX(pine.getX());
                     pine.setState(false);
                     updatePineapple(pine, elapsedTime);
                  }
                  else {
                     updatePineapple(pine, elapsedTime);
                  }
               }
            // normal update
               if(!(sprite instanceof PowerUp.Switch)) {
                  sprite.update(elapsedTime);
               }
               else {
                  PowerUp.Switch on =(PowerUp.Switch)sprite;
                  if(on.getUpdate() > 0 && on.getUpdate() < 10) {
                     sprite.update(elapsedTime);
                  }
               }
            }
         }
      }
   
   
    /**
        Updates the creature, applying gravity for creatures that
        aren't flying, and checks collisions.
   	  @param creature
   	  @param elapsedTime
    */
       private void updateCreature(Creature creature,
        long elapsedTime)
      {
      
        // apply gravity
         if (!creature.isFlying()) {
            creature.setVelocityY(creature.getVelocityY() +
                GRAVITY * elapsedTime);
         }
      
        // change x
         float dx = creature.getVelocityX();
         float oldX = creature.getX();
         float newX = oldX + dx * elapsedTime;
         Point tile =
            getTileCollision(creature, newX, creature.getY());
         if (tile == null) {
            creature.setX(newX);
         }
         else {
            // line up with the tile boundary
            if (dx > 0) {
               creature.setX(
                    TileMapRenderer.tilesToPixels(tile.x) -
                    creature.getWidth());
            }
            else if (dx < 0) {
               creature.setX(
                    TileMapRenderer.tilesToPixels(tile.x + 1));
            }
            creature.collideHorizontal();
         }
         if (creature instanceof Player) {
            checkPlayerCollision((Player)creature, false);
         }
      
        // change y
         float dy = creature.getVelocityY();
         float oldY = creature.getY();
         float newY = oldY + dy * elapsedTime;
         tile = getTileCollision(creature, creature.getX(), newY);
         if (tile == null) {
            creature.setY(newY);
         }
         else {
            // line up with the tile boundary
            if (dy > 0) {
               creature.setY(
                    TileMapRenderer.tilesToPixels(tile.y) -
                    creature.getHeight());
            }
            else if (dy < 0) {
               creature.setY(
                    TileMapRenderer.tilesToPixels(tile.y + 1));
            }
            creature.collideVertical();
         }
         if (creature instanceof Player) {
            boolean canKill = (oldY < creature.getY());
            checkPlayerCollision((Player)creature, canKill);
         }
      }
     /**
        Updates the creature, applying gravity for creatures that
        aren't flying, and checks collisions.
    */
       private void updatePineapple(Pineapple pineapple,
        long elapsedTime)
      {
       // change x
         float dx = pineapple.getVelocityX();
         float oldX = pineapple.getX();
         float newX = oldX + dx * elapsedTime;
         Point tile =
            getTileCollision(pineapple, newX, pineapple.getY());
         if (tile == null) {
            pineapple.setX(newX);
         }
         else {
            pineapple.setX(pineapple.getInitialX());
         		     //map.removeSprite(pineapple);
                    //resourceManager.reloadPineapple(map, pine);
         }
      
      }
   
    /**
        Checks for Player collision with other Sprites. If
        canKill is true, collisions with Creatures will kill
        them.
    */
       public void checkPlayerCollision(Player player,
        boolean canKill)
      {
         if (!player.isAlive()) {
            return;
         }
      
        // check for player collision with other sprites
         Sprite collisionSprite = getSpriteCollision(player);
         if (collisionSprite instanceof PowerUp) {
            acquirePowerUp((PowerUp)collisionSprite);
         }
      	
         if (collisionSprite instanceof Kongo) {
            Creature badguy = (Creature)collisionSprite;
            if (canKill) {
                // kill the badguy and make player bounce
               if(sonido) {          
                  soundManager.play(boopSound);
               }
               player.setY(badguy.getY() - player.getHeight());
               player.jump(true);
            }
            else {
                // player dies!
               player.setState(Creature.STATE_DYING);
               totalBananas = 0;
               vidas--;
            }
         }
         else if (collisionSprite instanceof Creature) {
            Creature badguy = (Creature)collisionSprite;
            if (canKill) {
                // kill the badguy and make player bounce
               if(sonido) {          
                  soundManager.play(boopSound);
               }
               badguy.setState(Creature.STATE_DYING);
               player.setY(badguy.getY() - player.getHeight());
               player.jump(true);
            }
            else {
                // player dies!
               player.setState(Creature.STATE_DYING);
               totalBananas = 0;
               vidas--;
            }
         }
         else if (collisionSprite instanceof Pineapple) {
           // player dies!
            player.setState(Creature.STATE_DYING);
            totalBananas = 0;
            vidas--;
         }
      }
   
   
    /**
        Gives the player the speicifed power up and removes it
        from the map.
   	  @param PoweruP
    */
       public void acquirePowerUp(PowerUp powerUp) {
         if(powerUp instanceof PowerUp.Star) {    
         // Push it on to the stack of the removed bananas
            oldBanana.push(powerUp);
         }    
         if(!(powerUp instanceof PowerUp.Goal || powerUp instanceof PowerUp.Switch ||
         		powerUp instanceof PowerUp.Friend)) {    
         // remove it from the map
            map.removeSprite(powerUp);
         }
      
         if (powerUp instanceof PowerUp.Star) {
            // do something here, like give the player points
            if(sonido) {        
               soundManager.play(prizeSound);
            }
            totalBananas++;
         }
         else if (powerUp instanceof PowerUp.Music) {
            // Gets a life for the player
            if(sonido) {        
               soundManager.play(prizeSound);
            }
            vidas++;
         }
         else if (powerUp instanceof PowerUp.Switch) {
            PowerUp.Switch turn = (PowerUp.Switch)powerUp;      
            //Breaks Kongo's Bridge
            resourceManager.removeTiles(map);
            turn.turnedOff();
            kongoFall = true;
            if(sonido && turn.getUpdate() < 5) {        
               soundManager.play(prizeSound);
            }
         }
         else if(powerUp instanceof PowerUp.Friend) {
          //The player has successfully finished
          // the game
            if(kongoFall) {
               finJuego = true;
               juego = false;
            }
         }
         else if (powerUp instanceof PowerUp.Goal) {
            if(totalBananas == resultado) {   
            //bring up the new background
               incorrect = false;          
					fondo++;
               renderer.setBackground(
                  resourceManager.loadImage("background_"+fondo+".jpg"));
            //make sound the new sountrack that corresponds to the level
               soundtrack++;
               Sequence sequence =
                  midiPlayer.getSequence("sounds/Soundtrack_"+soundtrack+".mid");
               if(sonido) {          
                  midiPlayer.play(sequence, true);
               }
               toggleDrumPlayback();
               
            // advance to next map
               if(sonido) {           
                  soundManager.play(prizeSound,
                     new EchoFilter(2000, .7f), false);
               }		
            	//Turns on the boolean that prints the mission on screen
               mision = true;
            		//Redefines what kind of operation is going to be made	 	
               if (dificultad == 1){
                  signo = "+";
                  numA = (int)(Math.random() * 50 + 1);
                  numB = (int)(Math.random() * 50 + 1);
                  while ( numA + numB > 100){
                     numA = (int)(Math.random() * 50 + 1);
                     numB = (int)(Math.random() * 50 + 1);
                  }
                  resultado = numA + numB;
               }
            
            //Redefines what kind of operation is going to be made
               if (dificultad == 2){
                  signo = "-";
                  numA = (int)(Math.random() * 50 + 1);
                  numB = (int)(Math.random() * 50 + 1);
                  while ( ! (numA >= numB)){
                     numA = (int)(Math.random() * 50 + 1);
                     numB = (int)(Math.random() * 50 + 1);
                  }
                  resultado = numA - numB;
               }
            
            //Redefines what kind of operation is going to be made
               if (dificultad == 3){
                  signo = "*";
                  numA = (int)(Math.random() * 10);
                  numB = (int)(Math.random() * 10);
                  resultado = numA * numB;
               }
               map = resourceManager.loadNextMap();					
               totalBananas = 0;
            }
            else
               incorrect = true; 
         }   
      }
   }
