package me.dabpessoa.map;

import java.awt.Image;

public class Tile {
	
	private Image image;
	private float x;
	private float y;
	private float width;
	private float height;
	private boolean throwable;
	
	public Tile() {
		this.width = TileMapRenderer.TILE_SIZE;
		this.height = TileMapRenderer.TILE_SIZE;
	}
	
	public Tile(Image image) {
		this();
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
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

	public boolean isThrowable() {
		return throwable;
	}
	
	public void setThrowable(boolean throwable) {
		this.throwable = throwable;
	}
	
}
