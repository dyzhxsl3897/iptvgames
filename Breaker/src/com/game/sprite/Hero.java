package com.game.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * 
 * @author JoYoNB
 *
 */
public class Hero extends Sprite{
	private int direction=0;//移动方向，0静止，1左移，2右移
	private int speed=45;
	private int moveXMin=71;
	private int moveXMax=580;
	private long catchBallTime=0;
	public Hero(Image image) {
		super(image);
	}
	public Hero(Image image,int width,int hight){
		super(image, width, hight);
//		this.setFrameSequence(new int[]{0,1,2});
		this.defineCollisionRectangle(0, 0, width, hight);
	}
	public void left(){
		if(this.getX()>moveXMin){
			this.direction=1;
			this.move(-speed, 0);
		}
	}
	public void right(){
		if(this.getX()<moveXMax-this.getWidth()){
			this.direction=2;
			this.move(speed, 0);
		}
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public long getCatchBallTime() {
		return catchBallTime;
	}
	public void setCatchBallTime(long catchBallTime) {
		this.catchBallTime = catchBallTime;
	}
}
