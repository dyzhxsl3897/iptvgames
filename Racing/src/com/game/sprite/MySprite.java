package com.game.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class MySprite extends Sprite{
	public MySprite(Image image) {
		super(image);
	}
	public MySprite(Image image, int frameWidth, int frameHeight) {
		super(image, frameWidth, frameHeight);
	}
	private int[] positionYs;
	private int line;

	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
		this.setPosition(this.getX(), positionYs[line]);
	}
	public void setPositionYs(int[] positionYs) {
		this.positionYs = positionYs;
	}
}
