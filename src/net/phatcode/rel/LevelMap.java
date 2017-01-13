package net.phatcode.rel;

/**
 * @author Richard Eric M. Lope (relminator)
 *
 */


import java.awt.Graphics2D;

import net.phatcode.rel.utils.ImageAtlas;

public class LevelMap
{
	
	private ImageAtlas tileAtlas;
	
	private int width = 0;
	private int height = 0;
	
	private int tileSize = 32;
		
	
	int map[][];
	
	public LevelMap( int width, int height, int tileSize, ImageAtlas tileAtlas )
	{
		this.tileAtlas = tileAtlas;
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
		
		map = new int[width][height];
		
		for( int y = 0; y < height; y++ )	
		{
			for( int x = 0; x < width; x++ )
			{
				map[x][y] = 0;
			}
		}
		
		String[] textMap = 
		{
				//012345678901234567890123456789012345678901234567890
				"++++++++++++++++++++",		// 0
				"+                  +",		// 1	
				"+                  +",		// 2	
				"+               ++++",		// 3	
				"+       ##         +",		// 4	
				"++++++         +   +",		// 5	
				"+                  +",		// 6	
				"+                  +",		// 7	
				"+   ##### # #      +",		// 8	
				"+                  +",		// 9	
				"+              +++++",		// 0	
				"+##                +",		// 1	
				"+    ###  ++++     +",		// 2	
				"+                  +",		// 3	
				"+##################+",		// 4	
				
		};

		for( int y = 0; y < height; y++ )	
		{
		
			int len = textMap[y].length(); 
			for( int x = 0; x < len; x++ )
			{
				int index = textMap[y].charAt(x);
				if( index > 0 )
				{
					switch( index )
					{
						case '#':
							map[x][y] = 1;
						break;
						case '+':
							map[x][y] = 2;
						break;
					}
				}
			}
		}
		
	}
	
	public void fill( int value )
	{
		
		for( int y = 0; y < height; y++ )	
		{
			for( int x = 0; x < width; x++ )
			{
				map[x][y] = value;
			}
		}
	}
	
	public void randomize( int max )
	{
		
		for( int y = 0; y < height; y++ )	
		{
			for( int x = 0; x < width; x++ )
			{
				float i = (float)Math.random();
				if( i < 0.2f )
				{
					map[x][y] = (int)(Math.random() * max) + 1;
				}
				else
				{
					map[x][y] = 0;
				}
			}
		}
	}
	
	public void update( float speed )
	{
		updateMap( speed );
	}
	
	public void updateMap( float speed )
	{
			
	}

	public void renderMap( Screen screen, Graphics2D g )
	{
		
		for( int y = 0; y < height; y++ )
		{
			for( int x = 0; x < width; x++ ) 
			{
				int i = map[x][y];		// get map index
				if( i > 0 )
				{

					int sx = (x * tileSize);      //Calculate where to put a
					int sy = (y * tileSize);      //particular tile
					g.drawImage( tileAtlas.getSprite(i-1), 
							 	 sx,
							 	 sy,
							 	 screen );
			
				}
			}
		}
		
		
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getTileSize()
	{
		return tileSize;
	}


	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public void setTileSize(int tileSize)
	{
		this.tileSize = tileSize;
	}

	public int[][] getMap()
	{
		return map;
	}

		
}
