package me.dabpessoa.sprite;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe Animation gerencia uma s�rie de imagens (quadros) e a quantidade
 * de tempo para exibir cada imagem.
 *
 * @author David Buzatto
 */
public class Animation {
    
    private List< SpriteFrame > frames;
    private int currentFrameIndex;
    private long animationTime;
    private long totalDuration;
    
    
    /**
     * Cria uma nova Animation vazia.
     */
    public Animation() {
        this( new ArrayList< SpriteFrame >(), 0 );
    }
    
    /**
     * Cria uma anima��o com os frames e a dura��o total da exibi��o 
     * da anima��o.
     */
    private Animation( List< SpriteFrame > frames, long totalDuration ) {
        this.frames = frames;
        this.totalDuration = totalDuration;
        initConfig();
    }
    
    
    /**
     * Cria uma duplicata da anima��o. A lista de frames � compartilhada 
     * entre duas anima��es, mas cada anima��o pode ser animada 
     * independentemente.
     */
    public Object clone() {
        return new Animation( frames, totalDuration );
    }
    
    
    /**
     * Adiciona uma imagem � anima��o com uma dura��o especificada.
     * (tempo para exibir a imagem).
     */
    public synchronized void addFrame( Image image, long duration ) {
        
        totalDuration += duration;
        frames.add( new SpriteFrame( image, totalDuration ) );
        
    }
    
    
    /**
     * Inicia a anima��o desde o in�cio.
     */
    public synchronized void initConfig() {
        animationTime = 0;
        currentFrameIndex = 0;
    }
    
    
    /**
     * Atualiza o quadro atual desta anima��o, se necess�rio.
     */
    public synchronized void update( long elapsedTime ) {
        
        if ( frames.size() > 1 ) {
            
            animationTime += elapsedTime;
            
            if ( animationTime >= totalDuration ) {
                animationTime = 0;
                currentFrameIndex = 0;
            }
            
            while ( animationTime > getFrame( currentFrameIndex ).endTime ) {
                
                currentFrameIndex++;
                
            }
            
        }
        
    }
    
    
    /**
     * Obt�m a imagem atual da anima��o. Retorna null se a anima��o
     * n�o tiver nenhuma imagem.
     */
    public synchronized Image getImage() {
        
        if ( frames.size() == 0 ) {
            
            return null;
            
        } else {
            
            return getFrame( currentFrameIndex ).image;
            
        }
        
    }
    
    /**
     * Obt�m um frame da anima��o.
     */
    private SpriteFrame getFrame( int i ) {
        
        return ( SpriteFrame ) frames.get( i );
        
    }
    
    
    /**
     * Class interna privada que modela um frame (quadro) da anima��o.
     */
    private class SpriteFrame {
        
        Image image;
        long endTime;
        
        /**
         * Cria uma novo AnimFrame com uma imagem e o tempo de dura��o.
         */
        public SpriteFrame( Image image, long endTime ) {
            
            this.image = image;
            this.endTime = endTime;
            
        }
        
    }
    
}