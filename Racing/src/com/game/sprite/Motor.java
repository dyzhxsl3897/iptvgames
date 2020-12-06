package com.game.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Motor extends Sprite{
	private int line=0;
	private long times=0;
	private Image[] images;
	boolean isJump=false;
	private int interval;
	private long speedTime=0;
	private int normalInterval;
	private static final int[] motorYs={96+85,126+85,158+85,195+85};
	public Motor(Image image) {
		super(image);
	}
	public Motor(Image[] images){
		super(images[0],135,132);
		this.images=images;
	}
	public void jump(){
		if(!isJump){
			this.setImage(images[1],135,132);
			this.setFrame(0);
			isJump=true;
		}
	}
	public void nextFrame(){
		if(isJump){
			if(this.getFrame()==0)times=0;
			if(this.getFrame()<3){
				this.move(0, -3);
			}else{
				this.move(0, 3);
			}
			if(this.getFrame()==5){
				this.setImage(images[0],135,132);
				isJump=false;
			}
			if(times%4==0){
				super.nextFrame();
			}
			times++;
		}else{
		
			if(interval>normalInterval){
				if(times%3==0){
					super.nextFrame();
				}
			}else if(interval==normalInterval){
				if(times%2==0){
					super.nextFrame();
				}
			}else{
				super.nextFrame();
			}
			
			if(speedTime<=0){
				normal();
			}else{
				speedTime--;
			}
			times++;
		}
	}
	public void speedUp(){
		speedTime=80;
		interval=normalInterval-5;
	}
	public void slowDown(){
		speedTime=50;
		interval=normalInterval+5;
	}
	public void normal(){
		interval=normalInterval;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
		this.normalInterval=interval;
	}
	public boolean isJump() {
		return isJump;
	}
	public void setJump(boolean isJump) {
		this.isJump = isJump;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
		this.setPosition(this.getX(), motorYs[line]);
	}
}
