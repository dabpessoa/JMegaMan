package me.dabpessoa.map;

import java.awt.Image;

public class Tile {
	
	private Image image;
	private int x;
	private int y;
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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
