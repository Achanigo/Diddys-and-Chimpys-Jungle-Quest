package com.brackeen.javagamebook.tilegame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.ImageIcon;

import com.brackeen.javagamebook.graphics.*;
import com.brackeen.javagamebook.tilegame.sprites.*;


/**
    The <code>ResourceManager</code> class loads and manages tile Images 
	 and "host" Sprites used in the game. Game Sprites are cloned from
    "host" Sprites.
*/
public class ResourceManager {
	 /**
        The list of the game's tiles.
    */
    private ArrayList tiles;
	 /**
        A linked list of the removed tiles points.
    */
	 private LinkedList puntos;
	 /**
        integer showing you the current map.
    */
    private int currentMap;
	 /**
        The graphics configuration of the map.
    */
    private GraphicsConfiguration gc;

    // host sprites used for cloning
	 
	 /**
        The sprite of the player.
    */
    private Sprite playerSprite;
	 /**
        The sprite of the lives.
    */
    private Sprite musicSprite;
	 /**
        The sprite of the bananas.
    */
    private Sprite coinSprite;
	 /**
        The sprite of the goal.
    */
    private Sprite goalSprite;
	 /**
        The sprite of the cocodrile.
    */
    private Sprite grubSprite;
	 /**
        The sprite of the snake.
    */
	 private Sprite grub2Sprite;
	 /**
        The sprite of the bird.
    */
    private Sprite flySprite;
	 /**
        The sprite of kongo.
    */
	 private Sprite kongoSprite;
	 /**
        The sprite of the pineapple.
    */
	 private Pineapple pinepSprite;
	 /**
        The sprite of the player's friend.
    */
	 private Sprite friendSprite;
	 /**
        The sprite of the switch.
    */
	 private Sprite switchSprite;
	 

    /**
        Creates a new <code>ResourceManager</code> with the specified
        GraphicsConfiguration.
    */
    public ResourceManager(GraphicsConfiguration gc) {
        this.gc = gc;
        loadTileImages();
        loadCreatureSprites();
        loadPowerUpSprites();
		  loadPineappleSprites();
		  puntos =  new LinkedList();
    }


    /**
        Method <I>loadImage</I> gets an image from the images/ directory.
		  
		  @param name is the name of the image.
    */
    public Image loadImage(String name) {
        String filename = "images/" + name;
		  URL urlImagen = this.getClass().getResource(filename);
		  return new ImageIcon(this.getClass().getResource(filename)).getImage();
        return Toolkit.getDefaultToolkit().getImage(urlImagen);
		  return new ImageIcon(filename).getImage();
    }

    /**
       Method <I>getMirroredImage</I> gets a mirrored image based on the image given.
		 
		 @param image is the image you want to mirror.
    */
    public Image getMirrorImage(Image image) {
        return getScaledImage(image, -1, 1);
    }
    /**
      Method <I>getFlippedImage</I> gets a flipped image based on the image given.
		
		@param image is the image you want to flip.
    */
    public Image getFlippedImage(Image image) {
        return getScaledImage(image, 1, -1);
    }
    /**
      Method <I>getScaledImage</I> gets a scaled image based on the image, width and 
		height given.
		
		@param image is the image you want to scale.
		@param x is the width of the image.
		@param y is the height of the image.
    */
    private Image getScaledImage(Image image, float x, float y) {

        // set up the transform
        AffineTransform transform = new AffineTransform();
        transform.scale(x, y);
        transform.translate(
            (x-1) * image.getWidth(null) / 2,
            (y-1) * image.getHeight(null) / 2);

        // create a transparent (not translucent) image
        Image newImage = gc.createCompatibleImage(
            image.getWidth(null),
            image.getHeight(null),
            Transparency.BITMASK);

        // draw the transformed image
        Graphics2D g = (Graphics2D)newImage.getGraphics();
        g.drawImage(image, transform, null);
        g.dispose();

        return newImage;
    }

    /**
      Method <I>loadNextMap</I> loads the next map of the lsit.
    */
    public TileMap loadNextMap() {
        TileMap map = null;
        while (map == null) {
            currentMap++;
            try {
                map = loadMap(
                    "maps/map" + currentMap + ".txt");
            }
            catch (IOException ex) {
                if (currentMap == 1) {
                    // no maps to load!
                    return null;
                }
                currentMap = 0;
                map = null;
            }
        }

        return map;
    }
    /**
      Method <I>reloadMap</I> reloads the current map.
    */

    public TileMap reloadMap() {
        try {
            return loadMap(
                "maps/map" + currentMap + ".txt");
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /**
      Method <I>loadMap</I> loads the map of the game, given the name of the map.
		
		@param filename is the name of the map file.
		
		@throws IOExcpetion This method will throw th IO Exception.
    */

    private TileMap loadMap(String filename)
        throws IOException
    {
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        // read every line in the text file into the list
        BufferedReader reader = new BufferedReader(
            new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            // add every line except for comments
            if (!line.startsWith("#")) {
                lines.add(line);
                width = Math.max(width, line.length());
            }
        }

        // parse the lines to create a TileEngine
        height = lines.size();
        TileMap newMap = new TileMap(width, height);
        for (int y=0; y<height; y++) {
            String line = (String)lines.get(y);
            for (int x=0; x<line.length(); x++) {
                char ch = line.charAt(x);

                // check if the char represents tile A, B, C etc.
                int tile = ch - 'A';
					 Point punto = new Point(x,y);
					 
					 // check if the char represents tile A, B, C etc.
                if (ch == 'J') {
                    puntos.add(punto);
                }
					 
                if (tile >= 0 && tile < tiles.size()) {
                    newMap.setTile(x, y, (Image)tiles.get(tile));
                }
					 
                // check if the char represents a sprite
                else if (ch == 'o') {
                    addSprite(newMap, coinSprite, x, y);
                }
                else if (ch == '!') {
                    addSprite(newMap, musicSprite, x, y);
                }
                else if (ch == '*') {
                    addSprite(newMap, goalSprite, x, y);
                }
                else if (ch == '1') {
                    addSprite(newMap, grubSprite, x, y);
                }
                else if (ch == '2') {
                    addSprite(newMap, flySprite, x, y);
                }
					 else if (ch == '3') {
                    addSprite(newMap, grub2Sprite, x, y);
                }
					 else if (ch == '4') {
                    addSprite(newMap, kongoSprite, x, y);
                }
					 else if (ch == 'p') {
                    addSprite(newMap, pinepSprite, x, y);
                }
					 else if(ch == 'f') {
					    addSprite(newMap, friendSprite, x, y);
					 }
					 else if(ch == 's') {
					    addSprite(newMap, switchSprite, x, y);
					 }
            }
        }

        // add the player to the map
        Sprite player = (Sprite)playerSprite.clone();
        player.setX(TileMapRenderer.tilesToPixels(3));
        player.setY(0);
        newMap.setPlayer(player);

        return newMap;
    }

    /**
      Method <I>addSprite</I> adds a sprite to a given tile map.
		
		@param map is the tile map of the game.
		@param hostSprite is the sprite you want to add.
		@param tileX is the tile position in x.
		@param tileY is the tile position in y.
    */
    private void addSprite(TileMap map,
        Sprite hostSprite, int tileX, int tileY)
    {
        if (hostSprite != null) {
            // clone the sprite from the "host"
            Sprite sprite = (Sprite)hostSprite.clone();

            // center the sprite
            sprite.setX(
                TileMapRenderer.tilesToPixels(tileX) +
                (TileMapRenderer.tilesToPixels(1) -
                sprite.getWidth()) / 2);

            // bottom-justify the sprite
            sprite.setY(
                TileMapRenderer.tilesToPixels(tileY + 1) -
                sprite.getHeight());

            // add it to the map
            map.addSprite(sprite);
        }
    }


    // -----------------------------------------------------------
    // code for loading sprites and images
    // -----------------------------------------------------------

    /**
      Method <I>loadTileImages</I> loads the images of the tiles.
    */
    public void loadTileImages() {
        // keep looking for tile A,B,C, etc. this makes it
        // easy to drop new tiles in the images/ directory
        tiles = new ArrayList();
        char ch = 'A';
        while (true) {
            String name = "tile_" + ch + ".png";
            File file = new File("images/" + name);
            if (!file.exists()) {
                break;
            }
            tiles.add(loadImage(name));
            ch++;
        }
    }

    /**
      Method <I>loadCreatureSprites</I> loads the creature's sprites of the game.
    */
    public void loadCreatureSprites() {

        Image[][] images = new Image[4][];

        // load left-facing images
        images[0] = new Image[] {
            loadImage("player1.png"),
            loadImage("player2.png"),
            loadImage("player3.png"),
            loadImage("fly1.png"),
            loadImage("fly2.png"),
            loadImage("fly3.png"),
            loadImage("grub1.png"),
            loadImage("grub2.png"),
				loadImage("snake1.png"),
            loadImage("snake2.png"),
				loadImage("boss1.png"),
            loadImage("boss2.png"),
        };

        images[1] = new Image[images[0].length];
        images[2] = new Image[images[0].length];
        images[3] = new Image[images[0].length];
        for (int i=0; i<images[0].length; i++) {
            // right-facing images
            images[1][i] = getMirrorImage(images[0][i]);
            // left-facing "dead" images
            images[2][i] = getFlippedImage(images[0][i]);
            // right-facing "dead" images
            images[3][i] = getFlippedImage(images[1][i]);
        }

        // create creature animations
        Animation[] playerAnim = new Animation[4];
        Animation[] flyAnim = new Animation[4];
        Animation[] grubAnim = new Animation[4];
		  Animation[] grub2Anim = new Animation[4];
		  Animation[] kongo = new Animation[4];
        for (int i=0; i<4; i++) {
            playerAnim[i] = createPlayerAnim(
                images[i][0], images[i][1], images[i][2]);
            flyAnim[i] = createFlyAnim(
                images[i][3], images[i][4], images[i][5]);
            grubAnim[i] = createGrubAnim(
                images[i][6], images[i][7]);
				grub2Anim[i] = createGrubAnim(
                images[i][8], images[i][9]);
				kongo[i] = createKongoAnim(
                images[i][10], images[i][11]);
        }

        // create creature sprites
        playerSprite = new Player(playerAnim[0], playerAnim[1],
            playerAnim[2], playerAnim[3]);
        flySprite = new Fly(flyAnim[0], flyAnim[1],
            flyAnim[2], flyAnim[3]);
        grubSprite = new Grub(grubAnim[0], grubAnim[1],
            grubAnim[2], grubAnim[3]);
		  grub2Sprite = new Grub(grub2Anim[0], grub2Anim[1],
            grub2Anim[2], grub2Anim[3]);
		  kongoSprite = new Kongo(kongo[0], kongo[1],
            kongo[2], kongo[3]);
    }

    /**
      Method <I>createPlayerAnim</I> creates the player's animation based
		on the given images.
		
		@param player1 is the first player image of the animation.
		@param player2 is the second player image of the animation.
		@param player3 is the third player image of the animation.
		
		@return Returns the animation of the given images.
    */
    private Animation createPlayerAnim(Image player1,
        Image player2, Image player3)
    {
        Animation anim = new Animation();
        anim.addFrame(player1, 250);
        anim.addFrame(player2, 150);
        anim.addFrame(player1, 150);
        anim.addFrame(player2, 150);
        anim.addFrame(player3, 200);
        anim.addFrame(player2, 150);
        return anim;
    }
    /**
      Method <I>createFlyAnim</I> creates the fly's animation based
		on the given images.
		
		@param img1 is the first fly image of the animation.
		@param img2 is the second fly image of the animation.
		@param img3 is the third fly image of the animation.
		
		@return Returns the animation of the given images.
    */
    private Animation createFlyAnim(Image img1, Image img2,
        Image img3)
    {
        Animation anim = new Animation();
        anim.addFrame(img1, 200);
        anim.addFrame(img2, 200);
        anim.addFrame(img3, 200);
        anim.addFrame(img2, 200);
        return anim;
    }

    /**
      Method <I>createGrubAnim</I> creates the grub's animation based
		on the given images.
		
		@param img1 is the first grub image of the animation.
		@param img2 is the second grub image of the animation.
		
		@return Returns the animation of the given images.
    */
    private Animation createGrubAnim(Image img1, Image img2) {
        Animation anim = new Animation();
        anim.addFrame(img1, 250);
        anim.addFrame(img2, 250);
        return anim;
    }
    /**
      Method <I>createKongoAnim</I> creates kongo's animation based
		on the given images.
		
		@param img1 is the first kongo image of the animation.
		@param img2 is the second kongo image of the animation.
		
		@return Returns the animation of the given images.
    */	 
	 private Animation createKongoAnim(Image img1, Image img2) {
        Animation anim = new Animation();
        anim.addFrame(img1, 250);
        anim.addFrame(img2, 250);
        return anim;
    }
    /**
      Method <I>loadPineappleSprites</I> loads the pineapple sprites
		of the map.
    */
    private void loadPineappleSprites() {
	   Animation anim = new Animation();
		anim.addFrame(loadImage("pine1.png"), 150);
		anim.addFrame(loadImage("pine2.png"), 150);
		anim.addFrame(loadImage("pine3.png"), 150);
		anim.addFrame(loadImage("pine4.png"), 150);
		
		pinepSprite = new Pineapple(anim);
	 }
	 /**
      Method <I>loadPowerUpSprites</I> loads the power up sprites
		of the map.
    */
    private void loadPowerUpSprites() {
        // create "goal" sprite
        Animation anim = new Animation();
        anim.addFrame(loadImage("music1.png"), 150);
        anim.addFrame(loadImage("music2.png"), 150);
        anim.addFrame(loadImage("music3.png"), 150);
        anim.addFrame(loadImage("music2.png"), 150);
        goalSprite = new PowerUp.Goal(anim);

        // create "star" sprite
        anim = new Animation();
        anim.addFrame(loadImage("star1.png"), 200);
        anim.addFrame(loadImage("star2.png"), 200);
        anim.addFrame(loadImage("star3.png"), 200);
        anim.addFrame(loadImage("star4.png"), 200);
        coinSprite = new PowerUp.Star(anim);

        // create "music" sprite
        anim = new Animation();
        anim.addFrame(loadImage("heart1.png"), 150);
        anim.addFrame(loadImage("heart2.png"), 150);
        anim.addFrame(loadImage("heart3.png"), 150);
        anim.addFrame(loadImage("heart2.png"), 150);
        musicSprite = new PowerUp.Music(anim);
		  
		  //create chimpy sprite
		  anim = new Animation();
        anim.addFrame(loadImage("chimpy1.png"), 150);
        anim.addFrame(loadImage("chimpy2.png"), 150);
        friendSprite = new PowerUp.Friend(anim);
		  
		  //create a "switch" sprite
		  anim = new Animation();
        anim.addFrame(loadImage("switch1.png"), 5);
        anim.addFrame(loadImage("switch2.png"), 150);
        switchSprite = new PowerUp.Switch(anim);
    }
	 /**
	    Method <I>reloadSprite</I>will reload erased bananas, so the 
	    player can grab them again.
		 
		 @param map is the tile map of the game.
		 @param powerUp is te sprite you wanto to reload.
	  */
    public void reloadSprite(TileMap map, Sprite powerUp){
	   map.addSprite(powerUp);
	 }
	 		 
	 /**
	    Method <I>removeTiles</I> will remove the previously 
		 especified tiles.
		 
		 @param map is the tile map of the game
	  */
    public void removeTiles(TileMap map){
	   for(int i = 0; i < puntos.size(); i++){
		  Point punto = (Point)puntos.get(i);
		  map.removeTile((int)punto.getX(),(int)punto.getY());
		}
	 }
}
