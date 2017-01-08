package me.dabpessoa.map;

import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;

import me.dabpessoa.sprite.Sprite;

public class TileMap {

	private Tile[][] tiles;
	private LinkedList<Sprite> sprites;
	private Sprite player;
	 
	public TileMap() {
		this.sprites = new LinkedList<Sprite>();
	}
	 
	 /**
     * Cria um novo TileMap com a largura e altura especificada
     * (em n�mero de peda�os) do mapa.
     */
    public TileMap( int width, int height ) {
    	this.sprites = new LinkedList<Sprite>();
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
	
	public void setSprites(LinkedList<Sprite> sprites) {
		this.sprites = sprites;
	}

	public void setPlayer(Sprite player) {
		this.player = player;
	}

	public Sprite getPlayer() {
		return player;
	}
	
	/**
     * Obt�m o Iterator de todas as Sprites desse mapa, menos a do jogador.
     */
    public Iterator<Sprite> getSprites() {
        return sprites.iterator();
    }
	
}
