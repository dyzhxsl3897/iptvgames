package com.game.fish;

import java.util.Random;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * 
 * @author JoYoNB
 *
 */
public class Fish extends Sprite{
	private int level=0;
	private int speed=70;
	private int moveXMin=0;
	private int moveXMax=1280;
	private int moveYMin=120;
	private int moveYMax=720;
	private int direction=0;// 0Left，1right
	private int action=0;
	private boolean isPlayer=false;
	public Fish(Image image) {
		super(image);
	}
	public Fish(Image image,int width,int hight,boolean isPlayer){
		super(image, width, hight);
		this.isPlayer=isPlayer;
		if(isPlayer){
			this.setFrameSequence(new int[]{0,1,2});
		}
		this.defineCollisionRectangle(this.getWidth()/6,this.getHeight()/10,this.getHeight()*4/6,this.getHeight()*8/10);
	}
	public void setMoveArea(int moveXMin,int moveXMax,int moveYMin,int moveYMax){
		this.moveXMin=moveXMin;
		this.moveXMax=moveXMax;
		this.moveYMin=moveYMin;
		this.moveYMax=moveYMax;
	}
	public void up(){
		if(this.getY()>moveYMin){
			this.move(0, -speed);
		}
	}
	public void down(){
		if(this.getY()<moveYMax-this.getHeight()){
			this.move(0, speed);
		}
	}
	public void left(){
		if(direction!=0){
			turn();
		}
		if(this.getX()>moveXMin){
			this.move(-speed, 0);
		}
		direction=0;
	}
	public void right(){
		if(direction!=1){
			turn();
		}
		if(this.getX()<moveXMax-this.getWidth()){
			this.move(speed, 0);
		}
		direction=1;
	}
	public void goBirthPosition(Random random){
		direction=random.nextInt(2);
		if(direction==0){
			this.setTransform(TRANS_NONE);
			int x=moveXMax+this.getWidth();
			int y=moveYMin+random.nextInt(moveYMax-moveYMin-this.getHeight());
			this.setPosition(x, y);
		}else{
			this.setTransform(TRANS_MIRROR);
			int x=moveXMin-this.getWidth();
			int y=moveYMin+random.nextInt(moveYMax-moveYMin-this.getHeight());
			this.setPosition(x, y);
		}
	}
	private void turn(){
		action=1;
		this.setFrameSequence(new int[]{3,4,5});
	}
	public void nextFrame() {
		super.nextFrame();
		
		if(isPlayer){
			if(action==1){
				if(this.getFrame()==2){
					if(direction==0){
						this.setTransform(TRANS_NONE);
						this.setPosition(this.getX()-this.getWidth(), this.getY());
					}else{
						this.setTransform(TRANS_MIRROR);
						this.setPosition(this.getX()+this.getWidth(), this.getY());
					}
					this.setFrameSequence(new int[]{0,1,2});
					action=0;
				}
			}else if(action==2){
				if(direction==0){
					this.setTransform(TRANS_NONE);
				}else{
					this.setTransform(TRANS_MIRROR);
				}
				if(this.getFrame()==2){
					this.setFrameSequence(new int[]{0,1,2});
					action=0;
				}
			}
		}else{
			if(direction==0){
				this.move(-speed, 0);
			}else{
				this.move(speed, 0);
			}
		}
	}
	public int getMoveXMin() {
		return moveXMin;
	}
	public void setMoveXMin(int moveXMin) {
		this.moveXMin = moveXMin;
	}
	public int getMoveXMax() {
		return moveXMax;
	}
	public void setMoveXMax(int moveXMax) {
		this.moveXMax = moveXMax;
	}
	public int getMoveYMin() {
		return moveYMin;
	}
	public void setMoveYMin(int moveYMin) {
		this.moveYMin = moveYMin;
	}
	public int getMoveYMax() {
		return moveYMax;
	}
	public void setMoveYMax(int moveYMax) {
		this.moveYMax = moveYMax;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
