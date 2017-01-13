package net.phatcode.rel;

/**
 * @author Richard Eric M. Lope (relminator)
 * Basic Axis - Tile Collision in a platformer
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import net.phatcode.rel.assets.ImageTextureDataDigiFont;
import net.phatcode.rel.assets.ImageTextureDataTiles;
import net.phatcode.rel.utils.ImageAtlas;
import net.phatcode.rel.utils.Keyboard;
import net.phatcode.rel.utils.SpriteFont;

public class Game
{

	private double accumulator = 0;

	private int fps = 0;
	private int framesPerSecond = 0;
	private double previousTime = 0;
	private double oldTime = 0;
	

	private SpriteFont fontDigi = new SpriteFont( new ImageAtlas( new ImageTextureDataDigiFont(), 16, 16 ) );
	
	private LevelMap levelMap = new LevelMap( Constants.SCREEN_WIDTH / Constants.TILE_SIZE,
							    			  Constants.SCREEN_HEIGHT / Constants.TILE_SIZE,
							    			  Constants.TILE_SIZE,
							    			  new ImageAtlas( new ImageTextureDataTiles(), 32, 32 ) );
	private ImageAtlas playerImages = new ImageAtlas( new ImageTextureDataTiles(), 32, 32 );
	private Player player = new Player();
	
	public Game()
	{

	}

	public void init( BufferStrategy strategy, Screen screen )
	{
		 
		Graphics2D g2D = (Graphics2D) strategy.getDrawGraphics();
		g2D.setColor(Color.BLACK);
		g2D.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

		Toolkit.getDefaultToolkit().sync();

		

		g2D.dispose();
		strategy.show();
		

	}
	
	private void update() 
	{

		player.update( levelMap );
		
		handleCollisions();
				
		Keyboard.getInstance().resetKeys();
		
		
	}
	
	private void handleCollisions()
	{
		
	}
	
	private void render( Graphics2D g2D, Screen screen ) 
	{

		levelMap.renderMap( screen, g2D );
		fontDigi.printCenter( 0,  0, 
							  Constants.SCREEN_WIDTH,
							  "  REL'S PLATFORM TEST  ",
							  screen, g2D );

		fontDigi.print( 0, 20, "TILEX = " + player.getTileX(), screen, g2D );
		fontDigi.print( 0, 40, "TILEY = " + player.getTileY(), screen, g2D );
		fontDigi.print( 0, 60, "FPS = " + fps, screen, g2D );
		
		player.render( screen, g2D, playerImages );
	
	}


	public void run( BufferStrategy strategy, Screen screen ) 
	{

		double dt = getDeltaTime(getSystemTime());
		
		init( strategy, screen  );
		
		while (true) 
		{

			dt = getDeltaTime(getSystemTime());
			if( dt > Constants.FIXED_TIME_STEP ) dt = Constants.FIXED_TIME_STEP;
			
			accumulator += dt;
			
			while( accumulator >= Constants.FIXED_TIME_STEP ) 
			{

				accumulator -= Constants.FIXED_TIME_STEP;
				update();
								
			}

			
			Graphics2D g2D = (Graphics2D) strategy.getDrawGraphics();
			g2D.setColor(Color.BLACK);
			g2D.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

			render( g2D, screen );
			g2D.dispose();
			strategy.show();
			Toolkit.getDefaultToolkit().sync();
			

			if( Keyboard.getInstance().isKeyPressed( KeyEvent.VK_ESCAPE ) )
			{
				System.exit(0);
			}
			
			try 
			{
				Thread.sleep(1);
			} 
			catch (InterruptedException e)
			{
				System.out.println("interrupted");
			}
			
		}

	}

	public Keyboard getKeyHandler()
	{
		return Keyboard.getInstance();
	}
	
	
	public double getSystemTime() 
	{
		return System.nanoTime() / 1000000000.0;
	}

	public double getDeltaTime( double timerInSeconds ) 
	{

		double currentTime = timerInSeconds;
		double elapsedTime = currentTime - oldTime;
		oldTime = currentTime;

		framesPerSecond++;

		if ((currentTime - previousTime) > 1.0) 
		{
			previousTime = currentTime;
			fps = framesPerSecond;
			framesPerSecond = 0;
		}

		return elapsedTime;
	}

		
}  // end class

