package me.dabpessoa.map;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

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

	public Rectangle createRectangle() {
		return new Rectangle(pixelX, pixelY, width, height);
	}

	public static List<Rectangle> createRectangles(List<Tile> tiles) {
		List<Rectangle> rectangles = new ArrayList<Rectangle>();
		if (tiles != null) {
			for (Tile tile : tiles) {
				rectangles.add(tile.createRectangle());
			}
		}
		return rectangles;
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
