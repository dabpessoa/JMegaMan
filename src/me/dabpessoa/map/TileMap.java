package me.dabpessoa.map;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

import me.dabpessoa.sprite.Sprite;

public class TileMap {

	private Tile[][] tiles;
	 
	public TileMap() {}

	 /**
     * Cria um novo TileMap com a largura e altura especificada
     * (em n�mero de peda�os) do mapa.
     */
    public TileMap( int width, int height ) {
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

    public boolean checkColision(Sprite sprite, float newPositionX, float newPositionY) {

        float newBottomRightX = newPositionX + sprite.getWidth();
        float newBottomRightY = newPositionY + sprite.getHeight();

        if (isPointColideTile(newPositionX, newPositionY) ||
            isPointColideTile(newBottomRightX, newBottomRightY)) {
            return true;
        }
        return false;

    }

    // x e y em pixels
    public boolean isPointColideTile(float x, float y) {
        for (int linha = 0 ; linha < tiles.length ; linha++) {
            for (int coluna = 0 ; coluna < tiles[linha].length ; coluna++) {
                Tile tile = tiles[linha][coluna];
                if (tile != null) {
                    if ( (x >= tile.getX() && x <= (tile.getX()+tile.getWidth()) ) &&
                         (y >= tile.getY() && y <= (tile.getY()+tile.getHeight())) ) {
                        return true;
                    }
                }
            }
        } return false;
    }

    /**
     * Obtém o tile que a Sprite colide. Somente X ou Y da Sprite deve ser
     * mudado não ambos. Retorna null se nenhuma colisão for detectada.
     */
    public Point getSpriteTileCollisionPoint(Sprite sprite,
                                             float newX, float newY) {

        Point pointCache = new Point();

        float fromX = Math.min( sprite.getX(), newX );
        float fromY = Math.min( sprite.getY(), newY );
        float toX = Math.max( sprite.getX(), newX );
        float toY = Math.max( sprite.getY(), newY );

        // obtem a localização do tile
        int fromTileX = TileMapRenderer.pixelsToTiles( fromX );
        int fromTileY = TileMapRenderer.pixelsToTiles( fromY );
        int toTileX = TileMapRenderer.pixelsToTiles(
                toX + sprite.getWidth());
        int toTileY = TileMapRenderer.pixelsToTiles(
                toY + sprite.getHeight());

        // checa cada tile para verificar a colisão
        for ( int x = fromTileX; x <= toTileX; x++ ) {

            for ( int y = fromTileY; y <= toTileY; y++ ) {

                if ( x < 0 || x >= getWidth() || y < 0 || y >= getHeight() ||
                        getTile( x, y ) != null ) {
                    // colisão achada, retorna o tile
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
     * Configura o pedaço no local especificado.
     */
    public void setTile( int x, int y, Tile tile ) {
        tiles[ x ][ y ] = tile;
    }
    
    /**
     * Configura o pedaço no local especificado.
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

}
