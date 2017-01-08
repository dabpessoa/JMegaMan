package me.dabpessoa.map;

import java.awt.Image;

public class Tile {
	
	private Image image;
	private boolean throwable;
	
	public Tile() {}
	
	public Tile(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public boolean isThrowable() {
		return throwable;
	}
	
	public void setThrowable(boolean throwable) {
		this.throwable = throwable;
	}
	
}
