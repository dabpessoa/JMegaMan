package net.phatcode.rel;

/**
 * @author Richard Eric M. Lope (relminator)
 *
 */


import java.awt.Graphics2D;

import net.phatcode.rel.utils.ImageAtlas;

public class Entity
{

	protected float width = 32;
	protected float height = 32;
	
	protected float x;
	protected float y;
	protected float dx;
	protected float dy;
	
	protected float speed = 2.5f;
	
	protected int frame = 0;
	protected int baseFrame = 0;
	protected int numFrames =  1;
	
	protected int tileX;
	protected int tileY;
	
	public Entity()
	{
	}
	
	
	public void update()
	{
	}
	
	public void render( Screen screen, Graphics2D g, ImageAtlas imageAtlas )
	{
		
		g.drawImage( imageAtlas.getSprite( baseFrame + frame ), 
			 	 	 (int)x,
			 	     (int)y,
			 	     screen );
	}


	public float getWidth()
	{
		return width;
	}


	public float getHeight()
	{
		return height;
	}


	public float getX()
	{
		return x;
	}


	public float getY()
	{
		return y;
	}


	public float getSpeed()
	{
		return speed;
	}


	public int getBaseFrame()
	{
		return baseFrame;
	}


	public int getNumFrames()
	{
		return numFrames;
	}


	public void setWidth(float width)
	{
		this.width = width;
	}


	public void setHeight(float height)
	{
		this.height = height;
	}


	public void setX(float x)
	{
		this.x = x;
	}

	public void addX(float x)
	{
		this.x += x;
	}


	public void setY(float y)
	{
		this.y = y;
	}

	public void addY(float y)
	{
		this.y += y;
	}

	public float getDx()
	{
		return dx;
	}


	public float getDy()
	{
		return dy;
	}


	public void setDx(float dx)
	{
		this.dx = dx;
	}


	public void setDy(float dy)
	{
		this.dy = dy;
	}

	public void addDy(float y)
	{
		this.dy += y;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}


	public void setBaseFrame(int baseFrame)
	{
		this.baseFrame = baseFrame;
	}


	public void setNumFrames(int numFrames)
	{
		this.numFrames = numFrames;
	}


	public int getTileX()
	{
		return tileX;
	}


	public int getTileY()
	{
		return tileY;
	}


	public void setTileX( int tileX )
	{
		this.tileX = tileX;
	}


	public void setTileY( int tileY )
	{
		this.tileY = tileY;
	}
	
	

}
