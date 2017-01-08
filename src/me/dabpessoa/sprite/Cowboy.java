package me.dabpessoa.sprite;

import java.awt.Graphics2D;
import java.awt.Window;

import me.dabpessoa.core.Run;

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
    
    public Cowboy(Window canvas) {
    	super(canvas);
	}
    
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getAnimation().getImage(), Math.round(getX()), Math.round(getY()), getCanvas());
	}
	
	@Override
	public void update(long elapsedTime) {
		
		float novaPosicaoX = getX() + (getVelocityX() * elapsedTime);
		float novaPosicaoY = getY() + (getVelocityY() * elapsedTime);
		
		boolean temTile = Run.checkExistsTile(novaPosicaoX, novaPosicaoY, this);
		
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
