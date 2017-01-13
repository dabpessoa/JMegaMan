package me.dabpessoa.sprite;

import me.dabpessoa.game.GameAction;
import me.dabpessoa.game.World;
import me.dabpessoa.manager.ResourceManager;

import java.awt.Graphics2D;
import java.awt.Window;

public abstract class Sprite {

	// Anima��o do Sprite
	private Animation animation;
	
	// posicionamento
    private float x;
    private float y;
    
    // velocidade (pixels por milisegundo)
    private float velocityX;
    private float velocityY;

	private World world;

	public abstract void update(long elapsedTime);
	public abstract void draw(Graphics2D g2d);
	public abstract void init();

	public Sprite(World world) {
		this.world = world;
		this.x = 0;
		this.y = 0;
		this.velocityX = 0;
		this.velocityY = 0;
		init();
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getVelocityX() {
		return velocityX;
	}
	
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}
	
	public float getVelocityY() {
		return velocityY;
	}
	
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}
	
	public Animation getAnimation() {
		return animation;
	}
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	 /**
     * Obt�m a largura da Sprite, baseado no tamanho da imagem atual.
     */
    public int getWidth() {
        return animation.getImage().getWidth( null );
    }
    
    
    /**
     * Obt�m a altura da Sprite, baseado no tamanho da imagem atual.
     */
    public int getHeight() {
        return animation.getImage().getHeight( null );
    }

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
