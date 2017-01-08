package me.dabpessoa.sprite;

import java.awt.Graphics2D;
import java.awt.Point;

import me.dabpessoa.core.World;
import me.dabpessoa.game.ResourceManager;
import me.dabpessoa.map.TileMapRenderer;

public class MegaMan extends Sprite {

	private static final float JUMP_SPEED = -.77f;
	public static final float GRAVITY = 0.002f;
	
	private Animation runLeftAnimation;
    private Animation runRightAnimation;
    private Animation idleAnimation;
    private Animation jumpRightAnimation;
    
    private boolean onGround = true;
	private World world;
	
	public MegaMan(World world) {
		super(world.getCanvas());
		this.world = world;
		this.setAnimation(getIdleAnimation());
		init(world.getResourceManager());
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getAnimation().getImage(), Math.round(getX()), Math.round(getY()), getCanvas());
	}

	@Override
	public void init(ResourceManager resourceManager) {

		Animation runLeftAnimation = new Animation();
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/runleft/run1.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/runleft/run2.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/runleft/run3.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/runleft/run4.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/runleft/run5.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/runleft/run6.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/runleft/run7.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/runleft/run8.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/runleft/run9.png"), 80);
		runLeftAnimation.addFrame(resourceManager.loadImage("megaman/runleft/run10.png"), 80);



		Animation runRightAnimation = new Animation();
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/runright/run1.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/runright/run2.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/runright/run3.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/runright/run4.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/runright/run5.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/runright/run6.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/runright/run7.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/runright/run8.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/runright/run9.png"), 80);
		runRightAnimation.addFrame(resourceManager.loadImage("megaman/runright/run10.png"), 80);

		Animation idleAnimation = new Animation();
		idleAnimation.addFrame(resourceManager.loadImage("megaman/idleright/idle1.png"), 8000);
		idleAnimation.addFrame(resourceManager.loadImage("megaman/idleright/idle2.png"), 350);
		idleAnimation.addFrame(resourceManager.loadImage("megaman/idleright/idle3.png"), 200);
		idleAnimation.addFrame(resourceManager.loadImage("megaman/idleright/idle4.png"), 230);
		idleAnimation.addFrame(resourceManager.loadImage("megaman/idleright/idle5.png"), 350);
		idleAnimation.addFrame(resourceManager.loadImage("megaman/idleright/idle6.png"), 1650);

		Animation jumpRightAnimation = new Animation();
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jumpright/jump1.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jumpright/jump2.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jumpright/jump3.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jumpright/jump4.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jumpright/jump5.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jumpright/jump6.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jumpright/jump7.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jumpright/jump8.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jumpright/jump9.png"), 80);
		jumpRightAnimation.addFrame(resourceManager.loadImage("megaman/jumpright/jump10.png"), 80);

		this.setIdleAnimation(idleAnimation);
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
