package com.game.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * 
 * @author JoYoNB
 *
 */
public class Ball extends Sprite{
	private boolean move=false;
	private int xStep;
	private int yStep;
	public Ball(Image image) {
		super(image);
	}
	public Ball(Image image,int width,int hight){
		super(image, width, hight);
		this.defineCollisionRectangle(0, 0, width, hight);
	}
	
	public void fire(int fire){
		if(this.getX()<71||this.getX()>560){
			this.xStep=-this.xStep;
		}else if(this.getY()<0){
			this.yStep=-this.yStep;
		}
		if(!move&&fire==1){
			System.out.println("000000000000000000");
			this.xStep=-this.xStep;
		}
		if(!(fire<0)){
			this.move(xStep, yStep);
			move=true;
		}
	}
	public int getCenterX(){
		return this.getX()+this.getWidth()/2;
	}
	public int getCenterY(){
		return this.getY()+this.getHeight()/2;
	}
	public int getxStep() {
		return xStep;
	}
	public void setxStep(int xStep) {
		this.xStep = xStep;
	}
	public int getyStep() {
		return yStep;
	}
	public void setyStep(int yStep) {
		this.yStep = yStep;
	}
	public boolean isMove() {
		return move;
	}
	public void setMove(boolean move) {
		this.move = move;
	}
}
