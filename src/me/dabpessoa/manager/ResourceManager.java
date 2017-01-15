package me.dabpessoa.manager;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import me.dabpessoa.map.Tile;
import me.dabpessoa.map.TileMap;
import me.dabpessoa.map.TileMapRenderer;

public class ResourceManager {
	
	private ArrayList<Tile> tiles;
	
	public ResourceManager() {
		loadTileImages( 'F' );
	}
	
	/**
     * Obtem uma imagem do diret�rio /recursos/imagens/
     */
    public Image loadImage( String name ) {
        String filename = "/resource/" + name;
        return new ImageIcon( getClass().getResource( filename ) ).getImage();
    }
	
	/**
     * Carrega as imagens dos tiles usando um caracter m�ximo.
     */
    public void loadTileImages( char max ) {
        
        tiles = new ArrayList< Tile >();
        
        char ch = 'A';
        
        while ( ch <= max ) {
            
            String name = "tile_" + ch + ".png";
            tiles.add( new Tile(loadImage( name ), TileMapRenderer.TILE_SIZE, TileMapRenderer.TILE_SIZE) );
            ch++;
            
        }
        
    }
	
	/**
     * Carrega um mapa do diret�rio /resource/
     */
    public TileMap loadMap(String name) throws IOException {
        
        String filename = "/resource/" + name;
        
        ArrayList< String > lines = new ArrayList< String >();
        int width = 0;
        int height = 0;
        BufferedReader reader;
        
        try {
            
            reader = new BufferedReader( new InputStreamReader( 
                    getClass().getResourceAsStream( filename ) ) );
            
            while ( true ) {
                String line = reader.readLine();
                // sem mais linhas para ler
                if ( line == null ) {
                    reader.close();
                    break;
                }
                
                // adiciona toda linha menos os coment�rios
                if ( !line.startsWith( "#" ) ) {
                    lines.add( line );
                    width = Math.max( width, line.length() );
                }
            }
            
            // parseia as linhas para criar uma TileEngine
            height = lines.size();
            TileMap newMap = new TileMap( width, height );
            
            for ( int y = 0; y < height; y++ ) {
                
                String line = ( String ) lines.get( y );
                for ( int x = 0; x < line.length(); x++ ) {
                    char ch = line.charAt( x );
                    
                    // verifica o tile que o caracter atual representa
                    int tile = ch - 'A';
                    if ( tile >= 0 && tile < tiles.size() ) {
                        newMap.setTile( x, y, ( Image ) tiles.get(tile).getImage() );
                    }
                    
                    // checa se o caracter representa umaa sprite
//                    else if ( ch == 'o' ) {
//                        addSprite( newMap, coinSprite, x, y );
//                    } else if ( ch == '!' ) {
//                        addSprite( newMap, mushroomSprite, x, y );
//                    } else if ( ch == 'f' ) {
//                        addSprite( newMap, fireFlowerSprite, x, y );
//                    } else if ( ch == 'u' ) {
//                        addSprite( newMap, oneUpSprite, x, y );
//                    } else if ( ch == '*' ) {
//                        addSprite( newMap, goalSprite, x, y );
//                    } else if ( ch == '1' ) {
//                        addSprite( newMap, goombaSprite, x, y );
//                    } else if ( ch == '2' ) {
//                        addSprite( newMap, flyGoombaSprite, x, y );
//                    } else if ( ch == '3' ) {
//                        addSprite( newMap, greenKoopaSprite, x, y );
//                    } else if ( ch == '4' ) {
//                        addSprite( newMap, redKoopaSprite, x, y );
//                    } else if ( ch == '5' ) {
//                        addSprite( newMap, blueKoopaSprite, x, y );
//                    } else if ( ch == '6' ) {
//                        addSprite( newMap, yellowKoopaSprite, x, y );
//                    } else if ( ch == '7' ) {
//                        addSprite( newMap, bombSprite, x, y );
//                    } else if ( ch == '8' ) {
//                        addSprite( newMap, bulletSprite, x, y );
//                    } else if ( ch == '9' ) {
//                        addSprite( newMap, batSprite, x, y );
//                    } else if ( ch == '@' ) {
//                        addSprite( newMap, turtleSprite, x, y );
//                    } else if ( ch == '$' ) {
//                        addSprite( newMap, mummyTurtleSprite, x, y );
//                    } else if ( ch == '%' ) {
//                        addSprite( newMap, blueDragonSprite, x, y );
//                    }
                    
                    
                }
            }
            
            // adiciona o jogador no mapa
//            Sprite player = ( Sprite ) playerSprite.clone();
//            player.setX( TileMapRenderer.tilesToPixels( 3 ) );
//            player.setY( 0 );
//            newMap.setPlayer( player );
            return newMap;
            
        } catch ( FileNotFoundException ex ) {
            ex.printStackTrace();
        }
        
        return null;
    }
	
}
