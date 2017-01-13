
package net.phatcode.rel;
/**
 * @author Richard Eric M. Lope (relminator)
 *
 */


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import net.phatcode.rel.utils.ImageAtlas;
import net.phatcode.rel.utils.Keyboard;


public class Player extends Entity
{

	private boolean canJump = false;
	private float JUMP_HEIGHT = 9.0f;
	
	public Player()
	{
		width = 28;
		height = 62;
		x = Constants.TILE_SIZE * 3;
		y = Constants.TILE_SIZE * 6;
		speed = 2.5f;
		dy = 1;
		canJump = false;
		
	}
	
	
	public void update( LevelMap levelMap )
	{
		
		dx = 0;
		if( Keyboard.getInstance().isKeyPressed(KeyEvent.VK_RIGHT))
		{
			dx = speed;
		}
		
		if( Keyboard.getInstance().isKeyPressed(KeyEvent.VK_LEFT))
		{
			dx = -speed;
		}
		
		if( Keyboard.getInstance().isKeyPressed(KeyEvent.VK_SPACE))
		{
			if( canJump )
			{
				dy = -JUMP_HEIGHT;
				canJump = false;
			}
		}
		
		
		Collision.CollisionType collisionType = Collision.collideOnMap( this, levelMap );
		
		if( collisionType == Collision.CollisionType.COLLIDE_FLOOR )
		{
			if( !Keyboard.getInstance().isKeyPressed(KeyEvent.VK_SPACE))
			{
				canJump = true;
			}
		}
		else
		{
			canJump = false;
		}
	
		tileX = (int)(x / Constants.TILE_SIZE);
		tileY = (int)(y / Constants.TILE_SIZE);
		
	}
	
	public void render( Screen screen, Graphics2D g, ImageAtlas imageAtlas )
	{
		
		g.drawImage( imageAtlas.getSprite(4), 
			 	 	 (int)x-2,
			 	     (int)y,
			 	     screen );
		g.drawImage( imageAtlas.getSprite(4), 
		 	 	     (int)x-2,
		 	         (int)y + 32,
		 	         screen );
	}
	
	
}
