package me.dabpessoa.sprite;

import java.awt.Graphics2D;
import java.awt.Point;

import me.dabpessoa.game.World;
import me.dabpessoa.manager.ResourceManager;
import me.dabpessoa.map.TileMapRenderer;

public class MegaMan extends Sprite {

	private static final float JUMP_SPEED = -.77f;
	public static final float GRAVITY = 0.002f;
	
	private Animation runLeftAnimation;
    private Animation runRightAnimation;
    private Animation idleLeftAnimation;
	private Animation idleRightAnimation;
    private Animation jumpRightAnimation;
    
    private boolean onGround;
	private boolean turnedRight;

	private World world;
	
	public MegaMan(World world) {
		super(world.getCanvas());
		this.world = world;
		this.setAnimation(getIdleRightAnimation());
		turnedRight = true;
		onGround = true;
		init(world.getResourceManager());
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getAnimation().getImage(), Math.round(getX()), Math.round(getY()), getCanvas());
	}

	@Override
	public void init(ResourceManager resourceManager) {

		Animation runLeftAnimation = new Animation();
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run1.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run2.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run3.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run4.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run5.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run6.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run7.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run8.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run9.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/run/left/run10.png"), 80);

		Animation runRightAnimation = new Animation();
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run1.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run2.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run3.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run4.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run5.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run6.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run7.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run8.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run9.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/run/right/run10.png"), 80);

		Animation idleRightAnimation = new Animation();
		idleRightAnimation.addFrame(resourceManager.loadImage("megaman/idle/right/idle1.png"), 2000);
		idleRightAnimation.addFrame(resourceManager.loadImage("megaman/idle/right/idle2.png"), 350);
		idleRightAnimation.addFrame(resourceManager.loadImage("megaman/idle/right/idle3.png"), 200);
		idleRightAnimation.addFrame(resourceManager.loadImage("megaman/idle/right/idle4.png"), 230);
		idleRightAnimation.addFrame(resourceManager.loadImage("megaman/idle/right/idle5.png"), 350);
		idleRightAnimation.addFrame(resourceManager.loadImage("megaman/idle/right/idle6.png"), 1650);

		Animation idleLeftAnimation = new Animation();
		idleLeftAnimation.addFrame(resourceManager.loadImage("megaman/idle/left/idle1.png"), 2000);
		idleLeftAnimation.addFrame(resourceManager.loadImage("megaman/idle/left/idle2.png"), 350);
		idleLeftAnimation.addFrame(resourceManager.loadImage("megaman/idle/left/idle3.png"), 200);
		idleLeftAnimation.addFrame(resourceManager.loadImage("megaman/idle/left/idle4.png"), 230);
		idleLeftAnimation.addFrame(resourceManager.loadImage("megaman/idle/left/idle5.png"), 350);
		idleLeftAnimation.addFrame(resourceManager.loadImage("megaman/idle/left/idle6.png"), 1650);

		Animation jumpRightAnimation = new Animation();
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump1.png"), 200);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump2.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump3.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump4.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump5.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump6.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump7.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump8.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump9.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jump/right/jump10.png"), 80);

		this.setIdleLeftAnimation(idleLeftAnimation);
		this.setIdleRightAnimation(idleRightAnimation);
		this.setRunLeftAnimation(runLeftAnimation);
		this.setRunRightAnimation(runRightAnimation);
		this.setJumpRightAnimation(jumpRightAnimation);

		this.setX(668);
		this.setY(300);

	}

	@Override
	public void update(long elapsedTime) {
		
		if (this.getVelocityY() == 0) {
			onGround = true;
		}
		
		//A gravidade afeta o sprite
		this.setVelocityY(this.getVelocityY() + (GRAVITY * elapsedTime));

		if (!isJumping()) {
			getJumpRightAnimation().start();
		}

		// Seleciona a anima��o correta
//		if (isJumping()) {
//			if (isTurnedRight()) this.setAnimation(getJumpRightAnimation());
//			else this.setAnimation(getJumpRightAnimation());
//		} else
		if ( getVelocityX() == 0 ) {
			if (isTurnedRight()) this.setAnimation(getIdleRightAnimation());
			else this.setAnimation(getIdleLeftAnimation());
		} else if (getVelocityX() > 0) {
			setTurnedRight(true);
			this.setAnimation(getRunRightAnimation());
		} else if (getVelocityX() < 0) {
			setTurnedRight(false);
			this.setAnimation(getRunLeftAnimation());
		}

		// altera x
        float dx = this.getVelocityX();
        float oldX = this.getX();
        float newX = oldX + (dx * elapsedTime);
        Point tile = world.getTileMap().getTileCollision( this, newX, this.getY() );
        
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
        tile = world.getTileMap().getTileCollision( this, this.getX(), newY );
        
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

	public boolean isJumping() {
		return !onGround;
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

	public Animation getIdleLeftAnimation() {
		return idleLeftAnimation;
	}

	public void setIdleLeftAnimation(Animation idleLeftAnimation) {
		this.idleLeftAnimation = idleLeftAnimation;
	}

	public Animation getIdleRightAnimation() {
		return idleRightAnimation;
	}

	public void setIdleRightAnimation(Animation idleRightAnimation) {
		this.idleRightAnimation = idleRightAnimation;
	}

	public Animation getJumpRightAnimation() {
		return jumpRightAnimation;
	}
	
	public void setJumpRightAnimation(Animation jumpRightAnimation) {
		this.jumpRightAnimation = jumpRightAnimation;
	}

	public boolean isTurnedRight() {
		return turnedRight;
	}

	public void setTurnedRight(boolean turnedRight) {
		this.turnedRight = turnedRight;
	}

}
