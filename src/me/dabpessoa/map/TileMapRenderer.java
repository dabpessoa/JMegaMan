package me.dabpessoa.map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;

import me.dabpessoa.game.World;
import me.dabpessoa.sprite.Sprite;

public class TileMapRenderer {

    private static final int TILE_SHIFT_SIZE_BITS = 5;
    public static final int TILE_SIZE = 1<< TILE_SHIFT_SIZE_BITS;
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
        return pixels >> TILE_SHIFT_SIZE_BITS;
        
        // ou, se o tamanho dos tiles n�o forem pot�ncia de dois, usa o m�todo 
        // floor():
        // return ( int ) Math.floor( ( float ) pixels / TILE_SIZE );
    }
    
    
    /**
     * Converte a posi��o de um tile para a posi��o em pixel.
     */
    public static int tilesToPixels(int numTiles) {
        // sem razão real para usar deslocamento aqui.
        // o seu uso é um pouco mais rápido, mas nos processadores modernos isso
        // quase não faz diferença
        return numTiles << TILE_SHIFT_SIZE_BITS;
        
        // se o tamanho dos tiles não forem potência de dois,
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
    public void draw( Graphics g, TileMap tileMap,
            int visibleScreenWidth, int visibleScreenHeight ) {

        // Recupera a largura do mapa de tiles.
        int totalTilesMapWidth = tilesToPixels( tileMap.getWidth() );
        
        // obtêm a posição de scrolling do mapa, baseado na posição do jogador
        int offsetX = (visibleScreenWidth / 2) - Math.round( world.getPlayer().getX() ) - TILE_SIZE;
        offsetX = Math.min( offsetX, 0 );
        offsetX = Math.max( offsetX, visibleScreenWidth - totalTilesMapWidth );
        
        // obtêm o offset de y para desenhar todas as sprites e tiles
        int offsetY = visibleScreenHeight - tilesToPixels( tileMap.getHeight() ) - TILE_SIZE;

        // desenha um fundo preto se necessário
        if ( background == null ||
                visibleScreenHeight > background.getHeight( null ) ) {
            g.setColor( Color.BLACK );
            g.fillRect( 0, 0, visibleScreenWidth, visibleScreenHeight );
        }
        
        // desenha a imagem de fundo usando parallax
        if ( background != null ) {
            int x = offsetX *
                    ( visibleScreenWidth - background.getWidth( null ) ) /
                    ( visibleScreenWidth - totalTilesMapWidth );
            int y = visibleScreenHeight - background.getHeight( null );
            
            g.drawImage( background, x, y, null );
        }
        
        // desenha os tiles visíveis
        int firstTileX = pixelsToTiles( -offsetX );
        int lastTileX = firstTileX +
                pixelsToTiles( visibleScreenWidth ) + 1;
        for ( int y = 0; y < tileMap.getHeight(); y++ ) {
            for ( int x = firstTileX; x <= lastTileX; x++ ) {
            	Tile tile = tileMap.getTile( x, y );
            	Image image = null;
            	if (tile != null) {
            		image = tile.getImage();
            	}
                if ( image != null ) {
                    int tileX = tilesToPixels( x ) + offsetX;
                    int tileY = tilesToPixels( y ) + offsetY;
                    tile.setX(tileX);
                    tile.setY(tileY);
                    g.drawImage( image,
                            (int)tile.getX(),
                            (int)tile.getY(),
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
