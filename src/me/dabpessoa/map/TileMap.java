package me.dabpessoa.map;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

import me.dabpessoa.sprite.Sprite;

public class TileMap {

	private Tile[][] tiles;
//	private LinkedList<Sprite> sprites;
//	private Sprite player;
	 
	public TileMap() {
//        this.sprites = new LinkedList<Sprite>();
	}

	 /**
     * Cria um novo TileMap com a largura e altura especificada
     * (em n�mero de peda�os) do mapa.
     */
    public TileMap( int width, int height ) {
//    	this.sprites = new LinkedList<Sprite>();
        tiles = new Tile[ width ][ height ];
    } 
    
    /**
     * Obt�m a largura do TileMap (n�mero de peda�os).
     */
    public int getWidth() {
        return tiles.length;
    }
    
    /**
     * Obt�m a altura do TileMap (n�mero de peda�os).
     */
    public int getHeight() {
        return tiles[ 0 ].length;
    }
    
    /**
     * Obt�m o peda�o de uma localiza��o espef�fica. Retorna null is
     * n�o haver nenhum peda�o na localiza��o espeficada ou ent�o se a
     * localiza��ono for fora dos limites do mapa.
     */
    public Tile getTile( int x, int y ) {
        if ( x < 0 || x >= getWidth() ||
                y < 0 || y >= getHeight() ) {
            return null;
        } else {
            return tiles[ x ][ y ];
        }
    }

    /**
     * Obt�m o tile que a Sprite colide. Somente X ou Y da Sprite deve ser
     * mudado n�o ambos. Retorna null se nenhuma colis�o for detectada.
     */
    public Point getSpriteTileCollision(Sprite sprite,
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
                toX + sprite.getWidth());
        int toTileY = TileMapRenderer.pixelsToTiles(
                toY + sprite.getHeight());

        // checa cada tile para verificar a colis�o
        for ( int x = fromTileX; x <= toTileX; x++ ) {

            for ( int y = fromTileY; y <= toTileY; y++ ) {

                if ( x < 0 || x >= getWidth() || y < 0 || y >= getHeight() ||
                        getTile( x, y ) != null ) {
                    // colis�o achada, retorna o tile
                    pointCache.setLocation( x, y );
                    return pointCache;
                }

            }

        }

        // nenhuma colisão encontrada
        return null;

    }

    public boolean checkExistsTile(float x, float y) {

        Tile t = getTile( TileMapRenderer.pixelsToTiles(x), TileMapRenderer.pixelsToTiles(y) );

        if (t == null) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Configura o peda�o no local especificado.
     */
    public void setTile( int x, int y, Tile tile ) {
        tiles[ x ][ y ] = tile;
    }
    
    /**
     * Configura o peda�o no local especificado.
     */
    public void setTile( int x, int y, Image image) {
        tiles[ x ][ y ] = new Tile(image);
    }

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
//	public void setSprites(LinkedList<Sprite> sprites) {
//		this.sprites = sprites;
//	}

//	public void setPlayer(Sprite player) {
//		this.player = player;
//	}
//
//	public Sprite getPlayer() {
//		return player;
//	}
	
	/**
     * Obt�m o Iterator de todas as Sprites desse mapa, menos a do jogador.
     */
//    public Iterator<Sprite> getSprites() {
//        return sprites.iterator();
//    }

}
