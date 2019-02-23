package com.game.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * 
 * @author JoYoNB
 *
 */
public class Enemy extends Sprite{
	private boolean enableBreak=true;
	private long catchBallTime=0;
	public Enemy(Image image) {
		super(image,image.getWidth()/5,image.getHeight());
	}
	public int getCenterX(){
		return this.getX()+this.getWidth()/2;
	}
	public int getCenterY(){
		return this.getY()+this.getHeight()/2;
	}
	public boolean isEnableBreak() {
		return enableBreak;
	}
	public void setEnableBreak(boolean enableBreak) {
		this.enableBreak = enableBreak;
	}
	public long getCatchBallTime() {
		return catchBallTime;
	}
	public void setCatchBallTime(long catchBallTime) {
		this.catchBallTime = catchBallTime;
	}
}
