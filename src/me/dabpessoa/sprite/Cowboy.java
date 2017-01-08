package me.dabpessoa.sprite;

import java.awt.Graphics2D;

import me.dabpessoa.game.World;
import me.dabpessoa.manager.ResourceManager;

public class Cowboy extends Sprite {

	public static final int NORMAL_STATE = 0;
    public static final int WALKING_LEFT_STATE = 1;
    public static final int WALKING_RIGHT_STATE = 2;
    public static final int JUMPING_STATE = 3;
    public static final int CELEBRATING_STATE = 4;
	
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;
    private Animation idleAnimation;
    private Animation celebrateAnimation;
    private Animation jumpAnimation;
    private int state;
    private float maxSpeed;

	private World world;
    
    public Cowboy(World world) {
		super(world.getCanvas());
		this.world = world;
		init(world.getResourceManager());
	}
    
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getAnimation().getImage(), Math.round(getX()), Math.round(getY()), getCanvas());
	}

	@Override
	public void init(ResourceManager resource) {

		Animation walkLeftAnimation = new Animation();
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft1.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft2.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft3.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft4.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft5.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft6.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft7.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft8.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft9.png"), 80);
		walkLeftAnimation.addFrame(resource.loadImage("walk/walkLeft10.png"), 80);

		Animation walkRightAnimation = new Animation();
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight1.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight2.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight3.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight4.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight5.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight6.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight7.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight8.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight9.png"), 80);
		walkRightAnimation.addFrame(resource.loadImage("walk/walkRight10.png"), 80);

		Animation idleAnimation = new Animation();
		idleAnimation.addFrame(resource.loadImage("idle.png"), 80);

		Animation jumpAnimation = new Animation();
		jumpAnimation.addFrame(resource.loadImage("jump/jump1.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump2.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump3.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump4.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump5.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump6.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump7.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump8.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump9.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump10.png"), 80);
		jumpAnimation.addFrame(resource.loadImage("jump/jump11.png"), 80);

		Animation celebrateAnimation = new Animation();
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate1.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate2.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate3.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate4.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate5.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate6.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate7.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate8.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate9.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate10.png"), 80);
		celebrateAnimation.addFrame(resource.loadImage("celebrate/celebrate11.png"), 80);

		this.setWalkLeftAnimation(walkLeftAnimation);
		this.setWalkRightAnimation(walkRightAnimation);
		this.setIdleAnimation(idleAnimation);
		this.setCelebrateAnimation(celebrateAnimation);
		this.setJumpAnimation(jumpAnimation);

		this.setX(668);
		this.setY(327);

		this.setState(Cowboy.NORMAL_STATE);

	}

	@Override
	public void update(long elapsedTime) {
		
		float novaPosicaoX = getX() + (getVelocityX() * elapsedTime);
		float novaPosicaoY = getY() + (getVelocityY() * elapsedTime);
		
		boolean temTile = world.getTileMap().checkExistsTile(novaPosicaoX, novaPosicaoY, this);
		
		if ( getVelocityX() == 0 ) {
			this.setAnimation(getIdleAnimation());
		} else if (getVelocityX() > 0) {
			this.setAnimation(getWalkRightAnimation());
		} else if (getVelocityX() < 0) {
			this.setAnimation(getWalkLeftAnimation());
		}
		
		if (temTile) {
			setVelocityX(0);
			setVelocityY(0);
		} else {
			setX(novaPosicaoX);
	        setY(novaPosicaoY);
		}
        
        getAnimation().update( elapsedTime );
		
		
		
//		setX(getX() + getVelocityX() * elapsedTime);
//        setY(getY() + getVelocityY() * elapsedTime);
//        getAnimation().update( elapsedTime );
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		if (state == NORMAL_STATE) {
       	 	setAnimation(idleAnimation);
		} else if (state == JUMPING_STATE) {
			setAnimation(jumpAnimation);
		} else if (state == WALKING_LEFT_STATE) {
			setAnimation(walkLeftAnimation);
		} else if (state == WALKING_RIGHT_STATE) {
			setAnimation(walkRightAnimation);
		} else if (state == CELEBRATING_STATE) {
			setAnimation(celebrateAnimation);
		}
		this.state = state;
	}
	
	public Animation getWalkLeftAnimation() {
		return walkLeftAnimation;
	}
	
	public void setWalkLeftAnimation(Animation walkLeftAnimation) {
		this.walkLeftAnimation = walkLeftAnimation;
	}
	
	public Animation getWalkRightAnimation() {
		return walkRightAnimation;
	}
	
	public void setWalkRightAnimation(Animation walkRightAnimation) {
		this.walkRightAnimation = walkRightAnimation;
	}
	
	public Animation getIdleAnimation() {
		return idleAnimation;
	}
	
	public void setIdleAnimation(Animation idleAnimation) {
		this.idleAnimation = idleAnimation;
	}
	
	public Animation getCelebrateAnimation() {
		return celebrateAnimation;
	}
	
	public void setCelebrateAnimation(Animation celebrateAnimation) {
		this.celebrateAnimation = celebrateAnimation;
	}
	
	public Animation getJumpAnimation() {
		return jumpAnimation;
	}
	
	public void setJumpAnimation(Animation jumpAnimation) {
		this.jumpAnimation = jumpAnimation;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}
	
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
}
