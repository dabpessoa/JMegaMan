package me.dabpessoa.sprite;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Window;

import me.dabpessoa.core.Run;
import me.dabpessoa.map.TileMapRenderer;

public class MegaMan extends Sprite {

	private static final float JUMP_SPEED = -.77f;
	public static final float GRAVITY = 0.002f;
	
	private Animation runLeftAnimation;
    private Animation runRightAnimation;
    private Animation idleAnimation;
    private Animation jumpRightAnimation;
    
    private boolean onGround = true;
	
	public MegaMan(Window canvas) {
		super(canvas);
		this.setAnimation(getIdleAnimation());
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getAnimation().getImage(), Math.round(getX()), Math.round(getY()), getCanvas());
	}

	@Override
	public void update(long elapsedTime) {
		
		if (this.getVelocityY() == 0) {
			onGround = true;
		}
		
		//A gravidade afeta o sprite
		this.setVelocityY(this.getVelocityY() + (GRAVITY * elapsedTime));
		
		// Seleciona a anima��o correta
//		 if (isJump()) {
//			this.setAnimation(getJumpRightAnimation());
		if ( getVelocityX() == 0 ) {
			this.setAnimation(getIdleAnimation());
		} else if (getVelocityX() > 0) {
			this.setAnimation(getRunRightAnimation());
		} else if (getVelocityX() < 0) {
			this.setAnimation(getRunLeftAnimation());
		}
		 
		// altera x
        float dx = this.getVelocityX();
        float oldX = this.getX();
        float newX = oldX + dx * elapsedTime;
        Point tile = Run.getTileCollision( this, newX, this.getY() );
        
        if ( tile == null ) {
        	this.setX( newX );
        } else {
            
            // alinha com a borda do tile
            if ( dx > 0 ) {
            	this.setX(
                        TileMapRenderer.tilesToPixels( tile.x ) -
                        this.getWidth() );
            } else if ( dx < 0 ) {
            	this.setX(
                        TileMapRenderer.tilesToPixels( tile.x + 1 ) );
            }
            this.collideHorizontal();
        }
        
        // troca y
        float dy = this.getVelocityY();
        float oldY = this.getY();
        float newY = oldY + dy * elapsedTime;
        tile = Run.getTileCollision( this, this.getX(), newY );
        
        if ( tile == null ) {
        	this.setY( newY );
        } else {
            // alinha com a borda do tile
            if ( dy > 0 ) {
            	this.setY(
                        TileMapRenderer.tilesToPixels( tile.y ) -
                        this.getHeight() );
            } else if ( dy < 0 ) {
            	this.setY(
                        TileMapRenderer.tilesToPixels( tile.y + 1 ) );
            }
            this.collideVertical();
        }
        
        getAnimation().update( elapsedTime );
        
	}
	
	/**
     * Chamado antes de update() se a criatura colidiu com um tile 
     * horizontalmente.
     */
    public void collideHorizontal() {
        setVelocityX(0);
    }
    
    
    /**
     * Chamado antes de update() se a criatura colidiu com um tile 
     * verticalmente.
     */
    public void collideVertical() {
        setVelocityY(0);
    }
	
	/**
     * Faz o jogador pular se o mesmo estiver no ch�o ou ent�o se forceJump 
     * � true.
     */
    public void jump() {
        if ( onGround  ) {
            onGround = false;
            setVelocityY( JUMP_SPEED );
        }
    }
	
	public Animation getRunLeftAnimation() {
		return runLeftAnimation;
	}
	
	public void setRunLeftAnimation(Animation runLeftAnimation) {
		this.runLeftAnimation = runLeftAnimation;
	}
	
	public Animation getRunRightAnimation() {
		return runRightAnimation;
	}
	
	public void setRunRightAnimation(Animation runRightAnimation) {
		this.runRightAnimation = runRightAnimation;
	}
	
	public Animation getIdleAnimation() {
		return idleAnimation;
	}
	
	public void setIdleAnimation(Animation idleAnimation) {
		this.idleAnimation = idleAnimation;
	}
	
	public Animation getJumpRightAnimation() {
		return jumpRightAnimation;
	}
	
	public void setJumpRightAnimation(Animation jumpRightAnimation) {
		this.jumpRightAnimation = jumpRightAnimation;
	}

}
