package me.dabpessoa.core;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.LinkedList;

import javax.swing.JFrame;

import me.dabpessoa.exception.GameException;
import me.dabpessoa.game.ResourceManager;
import me.dabpessoa.map.Tile;
import me.dabpessoa.map.TileMap;
import me.dabpessoa.map.TileMapRenderer;
import me.dabpessoa.sprite.Animation;
import me.dabpessoa.sprite.Cowboy;
import me.dabpessoa.sprite.MegaMan;
import me.dabpessoa.sprite.Sprite;

public class Run {

	private static TileMap map;
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				try {
					
//					mapRenderer.setBackground( resource.loadImage( "background0.png") );
					ResourceManager resource = new ResourceManager();
					Run run = new Run();
					
					JFrame canvas = run.getFrame();
					
//					final Cowboy sprite = (Cowboy) run.getPlayer(resource, canvas);
					
					final MegaMan sprite = (MegaMan) run.getMegaMan(resource, canvas);
					
					
					
					LinkedList<Sprite> sprites = new LinkedList<Sprite>();
					Cowboy c2 = (Cowboy) run.getPlayer(resource, canvas);
					c2.setState(Cowboy.CELEBRATING_STATE);
					c2.setX(80);
//					sprites.add(c2);
					
					map = new TileMap(50, 25);
					map = resource.loadMap("map.txt");
					map.setPlayer(sprite);
					map.setSprites(sprites);
					
					TileMapRenderer renderer = new TileMapRenderer();
					renderer.setBackground(resource.loadImage("background.png"));
					
					Cursor invisibleCursor =
				            Toolkit.getDefaultToolkit().createCustomCursor(
				            Toolkit.getDefaultToolkit().getImage( "" ),
				            new Point( 0, 0 ),
				            "invisible" );
					canvas.setCursor(invisibleCursor);
					
					GameCore core = new GameCore(renderer, map, canvas);
					core.run();
					
					
					
					
				} catch (GameException e) {
					System.out.println("GameException: "+e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				
			}
		});
	}
	
	public Sprite getMegaMan(ResourceManager resource, Window canvas) {
		
		MegaMan megaMan = new MegaMan(canvas);
		
		Animation runLeftAnimation = new Animation();
		runLeftAnimation.addFrame(resource.loadImage("megaman/runleft/run1.png"), 80);
		runLeftAnimation.addFrame(resource.loadImage("megaman/runleft/run2.png"), 80);
		runLeftAnimation.addFrame(resource.loadImage("megaman/runleft/run3.png"), 80);
		runLeftAnimation.addFrame(resource.loadImage("megaman/runleft/run4.png"), 80);
		runLeftAnimation.addFrame(resource.loadImage("megaman/runleft/run5.png"), 80);
		runLeftAnimation.addFrame(resource.loadImage("megaman/runleft/run6.png"), 80);
		runLeftAnimation.addFrame(resource.loadImage("megaman/runleft/run7.png"), 80);
		runLeftAnimation.addFrame(resource.loadImage("megaman/runleft/run8.png"), 80);
		runLeftAnimation.addFrame(resource.loadImage("megaman/runleft/run9.png"), 80);
		runLeftAnimation.addFrame(resource.loadImage("megaman/runleft/run10.png"), 80);
		
		
		
		Animation runRightAnimation = new Animation();
		runRightAnimation.addFrame(resource.loadImage("megaman/runright/run1.png"), 80);
		runRightAnimation.addFrame(resource.loadImage("megaman/runright/run2.png"), 80);
		runRightAnimation.addFrame(resource.loadImage("megaman/runright/run3.png"), 80);
		runRightAnimation.addFrame(resource.loadImage("megaman/runright/run4.png"), 80);
		runRightAnimation.addFrame(resource.loadImage("megaman/runright/run5.png"), 80);
		runRightAnimation.addFrame(resource.loadImage("megaman/runright/run6.png"), 80);
		runRightAnimation.addFrame(resource.loadImage("megaman/runright/run7.png"), 80);
		runRightAnimation.addFrame(resource.loadImage("megaman/runright/run8.png"), 80);
		runRightAnimation.addFrame(resource.loadImage("megaman/runright/run9.png"), 80);
		runRightAnimation.addFrame(resource.loadImage("megaman/runright/run10.png"), 80);
		
		Animation idleAnimation = new Animation();
		idleAnimation.addFrame(resource.loadImage("megaman/idleright/idle1.png"), 8000);
		idleAnimation.addFrame(resource.loadImage("megaman/idleright/idle2.png"), 350);
		idleAnimation.addFrame(resource.loadImage("megaman/idleright/idle3.png"), 200);
		idleAnimation.addFrame(resource.loadImage("megaman/idleright/idle4.png"), 230);
		idleAnimation.addFrame(resource.loadImage("megaman/idleright/idle5.png"), 350);
		idleAnimation.addFrame(resource.loadImage("megaman/idleright/idle6.png"), 1650);
		
		Animation jumpRightAnimation = new Animation();
		jumpRightAnimation.addFrame(resource.loadImage("megaman/jumpright/jump1.png"), 80);
		jumpRightAnimation.addFrame(resource.loadImage("megaman/jumpright/jump2.png"), 80);
		jumpRightAnimation.addFrame(resource.loadImage("megaman/jumpright/jump3.png"), 80);
		jumpRightAnimation.addFrame(resource.loadImage("megaman/jumpright/jump4.png"), 80);
		jumpRightAnimation.addFrame(resource.loadImage("megaman/jumpright/jump5.png"), 80);
		jumpRightAnimation.addFrame(resource.loadImage("megaman/jumpright/jump6.png"), 80);
		jumpRightAnimation.addFrame(resource.loadImage("megaman/jumpright/jump7.png"), 80);
		jumpRightAnimation.addFrame(resource.loadImage("megaman/jumpright/jump8.png"), 80);
		jumpRightAnimation.addFrame(resource.loadImage("megaman/jumpright/jump9.png"), 80);
		jumpRightAnimation.addFrame(resource.loadImage("megaman/jumpright/jump10.png"), 80);
		
		megaMan.setIdleAnimation(idleAnimation);
		megaMan.setRunLeftAnimation(runLeftAnimation);
		megaMan.setRunRightAnimation(runRightAnimation);
		megaMan.setJumpRightAnimation(jumpRightAnimation);
		
		megaMan.setX(668);
		megaMan.setY(300);
		
		return megaMan;
		
	}
	
	public Sprite getPlayer(ResourceManager resource, Window canvas) {
		
		Cowboy c = new Cowboy(canvas);
		
		
		Animation walkLeftAnimation = new Animation();
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft1.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft2.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft3.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft4.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft5.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft6.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft7.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft8.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft9.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft10.png"), 80);
		
		Animation walkRightAnimation = new Animation();
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight1.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight2.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight3.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight4.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight5.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight6.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight7.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight8.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight9.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight10.png"), 80);
		
		Animation idleAnimation = new Animation();
		idleAnimation.addFrame(resource.loadImage("idle.png"), 80);
		
		Animation jumpAnimation = new Animation();
		jumpAnimation.addFrame(resource.loadImage("jump/jump1.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump2.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump3.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump4.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump5.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump6.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump7.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump8.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump9.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump10.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump11.png"), 80);
		
		Animation celebrateAnimation = new Animation();
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate1.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate2.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate3.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate4.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate5.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate6.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate7.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate8.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate9.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate10.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate11.png"), 80);
		
		c.setWalkLeftAnimation(walkLeftAnimation);
		c.setWalkRightAnimation(walkRightAnimation);
		c.setIdleAnimation(idleAnimation);
		c.setCelebrateAnimation(celebrateAnimation);
		c.setJumpAnimation(jumpAnimation);
		
		c.setX(668);
		c.setY(327);
		
		
		c.setState(Cowboy.NORMAL_STATE);
		
		return c;
		
	}
	
	 /**
     * Obt�m o tile que a Sprite colide. Somente X ou Y da Sprite deve ser
     * mudado n�o ambos. Retorna null se nenhuma colis�o for detectada.
     */
    public static Point getTileCollision( Sprite sprite,
            float newX, float newY) {
    	
    	Point pointCache = new Point();
        
        float fromX = Math.min( sprite.getX(), newX );
        float fromY = Math.min( sprite.getY(), newY );
        float toX = Math.max( sprite.getX(), newX );
        float toY = Math.max( sprite.getY(), newY );
        
        // obtem a localiza��o do tile
        int fromTileX = TileMapRenderer.pixelsToTiles( fromX );
        int fromTileY = TileMapRenderer.pixelsToTiles( fromY );
        int toTileX = TileMapRenderer.pixelsToTiles(
                toX + sprite.getWidth() - 1 );
        int toTileY = TileMapRenderer.pixelsToTiles(
                toY + sprite.getHeight() - 1 );
        
        // checa cada tile para verificar a colis�o
        for ( int x = fromTileX; x <= toTileX; x++ ) {
            
            for ( int y = fromTileY; y <= toTileY; y++ ) {
                
                if ( x < 0 || x >= map.getWidth() ||
                        map.getTile( x, y ) != null ) {
                    // colis�o achada, retorna o tile
                    pointCache.setLocation( x, y );
                    return pointCache;
                }
                
            }
            
        }
        
        // nenhuma colisão encontrada
        return null;
        
    }
    
    public static boolean checkExistsTile(float x, float y, Sprite sprite) {
    	
		Tile t = map.getTile( TileMapRenderer.pixelsToTiles(x), TileMapRenderer.pixelsToTiles(y) );
    	
    	if (t == null) {
    		return false;
    	} else {
    		return true;
    	}
    	
    }
	
	public JFrame getFrame() {
		JFrame canvas = new JFrame();
		canvas.setSize(500, 500);
		canvas.setIgnoreRepaint(true);
		canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.setLocationRelativeTo(null);
		canvas.setBackground(Color.BLUE);
		canvas.setVisible(true);
		return canvas;
	}
	
}
