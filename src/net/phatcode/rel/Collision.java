package net.phatcode.rel;

/**
 * @author Richard Eric M. Lope (relminator)
 *
 */


public class Collision
{

	public enum CollisionType
	{
		COLLIDE_NONE,
		COLLIDE_FLOOR,
		COLLIDE_WALL,
		COLLIDE_CEILING,
		
	}
	
	private static int tileX;
	private static int tileY;
	
	public static boolean collideWalls( float offset, Entity e, LevelMap levelMap )
	{
		
		int ix = (int)(e.getX() + offset);
		int iy = (int)e.getY();
		
		int hei = (int)e.getHeight();
		
		int tileYpixels = iy - ( iy % Constants.TILE_SIZE );
		int testEnd = iy + hei;	
		
		tileX = ix / Constants.TILE_SIZE;
		tileY = tileYpixels / Constants.TILE_SIZE;
		
		
		int[][] map = levelMap.getMap();
		while( tileYpixels <= testEnd )
		{		
			if( map[tileX][tileY] > 0 )
			{
				return true;
			}
			tileY++;
			tileYpixels += Constants.TILE_SIZE;
		}
		
		return false;
	}
	
	public static boolean collideFloors( float offset, Entity e, LevelMap levelMap )
	{
		
		int ix = (int)e.getX();
		int iy = (int)(e.getY() + offset);
		
		int wid = (int)e.getWidth();
		
		int tileXpixels = ix - ( ix % Constants.TILE_SIZE );
		int testEnd = ix + wid;	
		
		tileX = tileXpixels / Constants.TILE_SIZE;
		tileY = iy / Constants.TILE_SIZE;
		
		
		int[][] map = levelMap.getMap();
		while( tileXpixels <= testEnd )
		{		
			if( map[tileX][tileY] > 0 )
			{
				return true;
			}
			tileX++;
			tileXpixels += Constants.TILE_SIZE;
		}
		
		return false;
	}
	
	public static CollisionType collideOnMap(  Entity e, LevelMap levelMap )
	{
		
		tileX = 0;
		tileY = 0;
		CollisionType collision = CollisionType.COLLIDE_NONE;
		
		if( e.getDx() > 0 )
		{
		
			if( collideWalls( e.getDx() + e.getWidth(), e, levelMap ) )
			{
				e.setX( (tileX  * Constants.TILE_SIZE) - e.getWidth() - 1 );
				collision = CollisionType.COLLIDE_WALL;
			}
			else
			{
				e.addX( e.getDx() );
			}
	
		}
		
		else if( e.getDx() < 0 )
		{

			if( collideWalls( e.getDx(), e, levelMap ) )
			{
				e.setX( ( tileX + 1 ) * Constants.TILE_SIZE + 1 );
				collision = CollisionType.COLLIDE_WALL;
			}
			else
			{
				e.addX( e.getDx() );
			}
			
		}
		
		
		if( e.getDy() < 0 )
		{
			
			if( collideFloors( e.getDy(), e, levelMap ) )
			{
				e.setY( ( tileY + 1 ) * Constants.TILE_SIZE + 1 );
				e.setDy( 0 );
				collision = CollisionType.COLLIDE_CEILING;
			}
			else
			{
				e.addY( e.getDy() );
				e.addDy( Constants.GRAVITY );
			}
			
		}
		else
		{
			
			if( collideFloors( e.getDy() + e.getHeight(), e, levelMap ) )
			{
				e.setY( (tileY  * Constants.TILE_SIZE) - e.getHeight() - 1 );
				e.setDy( 1 );
				collision = CollisionType.COLLIDE_FLOOR;
			}
			else
			{
				e.addY( e.getDy() );
				e.addDy( Constants.GRAVITY );
			}
		}
		
		
		return collision;
		
	}
	
	
}
