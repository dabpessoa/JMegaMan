package me.dabpessoa.map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;

import me.dabpessoa.game.World;
import me.dabpessoa.sprite.Sprite;

public class TileMapRenderer {

	private static final int TILE_SIZE = 32;
    private static final int TILE_SIZE_BITS = 5;
    private Image background;

    private World world;
    
    public TileMapRenderer(World world) {
        this.world = world;
    }
    
    /**
     * Converte uma posi��o em pixel para a posi��o de um tile.
     */
    public static int pixelsToTiles( float pixels ) {
        return pixelsToTiles( Math.round( pixels ) );
    }
    
    /**
     * Converte uma posi��o em pixel para a posi��o de um tile.
     */
    public static int pixelsToTiles(int pixels) {
        // usa deslocamento para obter os valores corretos para pixels negativos
        return pixels >> TILE_SIZE_BITS;
        
        // ou, se o tamanho dos tiles n�o forem pot�ncia de dois, usa o m�todo 
        // floor():
        // return ( int ) Math.floor( ( float ) pixels / TILE_SIZE );
    }
    
    
    /**
     * Converte a posi��o de um tile para a posi��o em pixel.
     */
    public static int tilesToPixels(int numTiles) {
        // sem raz�o real para usar deslocamento aqui.
        // o seu uso � um pouco mais r�pido, mas nos processadores modernos isso 
        // quase n�o faz diferen�a
        return numTiles << TILE_SIZE_BITS;
        
        // se o tamanho dos tiles n�o forem pot�ncia de dois,
        // return numTiles * TILE_SIZE;
    }
    
    public void setBackground(Image background) {
		this.background = background;
	}
    
    public Image getBackground() {
		return background;
	}
    
    /**
     * Desenha o Map especificado.
     */
    public void draw( Graphics g, TileMap map,
            int screenWidth, int screenHeight ) {
        
//        Sprite player = map.getPlayer();
        int mapWidth = tilesToPixels( map.getWidth() );
        
        // obt�m a posi��o de scrolling do mapa, baseado na posi��o do jogador
        int offsetX = screenWidth / 2 -
                Math.round( world.getPlayer().getX() ) - TILE_SIZE;
        offsetX = Math.min( offsetX, 0 );
        offsetX = Math.max( offsetX, screenWidth - mapWidth );
//        int offsetX = screenWidth - tilesToPixels(map.getWidth()); 
        
        // obt�m o offset de y para desenhar todas as sprites e tiles
        int offsetY = screenHeight - tilesToPixels( map.getHeight() ) - TILE_SIZE;
        
//        int offsetX = 0;
//        int offsetY = 0;
        
        // desenha um fundo preto se necess�rio
        if ( background == null ||
                screenHeight > background.getHeight( null ) ) {
            g.setColor( Color.BLACK );
            g.fillRect( 0, 0, screenWidth, screenHeight );
        }
        
        // desenha a imagem de fundo usando parallax
        if ( background != null ) {
            int x = offsetX *
                    ( screenWidth - background.getWidth( null ) ) /
                    ( screenWidth - mapWidth );
            int y = screenHeight - background.getHeight( null );
            
            g.drawImage( background, x, y, null );
        }
        
        // desenha os tiles vis�veis
        int firstTileX = pixelsToTiles( -offsetX );
        int lastTileX = firstTileX +
                pixelsToTiles( screenWidth ) + 1;
        for ( int y = 0; y < map.getHeight(); y++ ) {
            for ( int x = firstTileX; x <= lastTileX; x++ ) {
            	Tile tile = map.getTile( x, y );
            	Image image = null;
            	if (tile != null) {
            		image = tile.getImage();
            	}
//                Image image = map.getTile( x, y ).getImage();
                if ( image != null ) {
                    g.drawImage( image,
                            tilesToPixels( x ) + offsetX,
                            tilesToPixels( y ) + offsetY,
                            null );
                }
            }
        }
        
        // desenha o jogador
        g.drawImage( world.getPlayer().getAnimation().getImage(),
                Math.round( world.getPlayer().getX() ) + offsetX,
                Math.round( world.getPlayer().getY() ) + offsetY,
                null );
        
        // desenha as sprites
        Iterator<Sprite> i = world.getSprites();
        while ( i.hasNext() ) {
            Sprite sprite = ( Sprite ) i.next();
            int x = Math.round( sprite.getX() ) + offsetX;
            int y = Math.round( sprite.getY() ) + offsetY;
            g.drawImage( sprite.getAnimation().getImage(), x, y, null );
            
            // acorda a critura quando a mesma estiver na tela
//            if ( sprite instanceof Creature &&
//                    x >= 0 && x < screenWidth ) {
//                ( ( Creature ) sprite ).wakeUp();
//            }
        }
        
    }
	
}
