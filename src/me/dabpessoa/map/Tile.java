package me.dabpessoa.map;

import java.awt.Image;

public class Tile {
	
	private Image image;
	private int pixelX;
	private int pixelY;
	private int width;
	private int height;
	
	public Tile(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public Tile(Image image, int tileWidth, int tileHeight) {
		this(tileWidth, tileHeight);
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getPixelX() {
		return pixelX;
	}

	public void setPixelX(int pixelX) {
		this.pixelX = pixelX;
	}

	public int getPixelY() {
		return pixelY;
	}

	public void setPixelY(int pixelY) {
		this.pixelY = pixelY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
