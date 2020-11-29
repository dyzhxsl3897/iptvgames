package com.game.tank;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Zd extends Sprite{

	/**
	 * 谁的子弹，0主战坦克的，1敌方坦克的
	 */
	private int whose=0;
	private Image bzImage;
	public Zd(Image image) {
		super(image);
	}
	
	
	public Zd(Image image_s,Image image_x,Image image_z,Image image_y){
		super(image_s);
		this.image_s=image_s;
		this.image_x=image_x;
		this.image_z=image_z;
		this.image_y=image_y;
	}
	private Image image_s;
	private Image image_x;
	private Image image_z;
	private Image image_y;
	private int fx=0;
	public int getFx() {
		return fx;
	}
	public void setFx(int fx) {
		this.fx = fx;
		if(fx==1) this.setImage(image_s, 2, 3);
		if(fx==2) this.setImage(image_x, 4, 5);
		if(fx==3) this.setImage(image_z, 5, 4);
		if(fx==4) this.setImage(image_y, 5, 4);
	}

	public void move(int dx, int dy) {
		super.move(dx, dy);
		if(this.getX()<140||this.getY()<99||this.getX()>640-140||this.getY()>431){
			this.fx=5;
		}
	}

	public int getWhose() {
		return whose;
	}

	public void setWhose(int whose) {
		this.whose = whose;
	}
	public void bz(){
		this.fx=5;
		this.setImage(this.bzImage,28,28);
		if(this.getFrame()==0){
			this.setPosition(this.getX()-12, this.getY()-12);
		}
		
		this.nextFrame();
		if(this.getFrame()==0){
			this.fx=0;
			this.setPosition(-10, -10);
		}
	}


	public Image getBzImage() {
		return bzImage;
	}


	public void setBzImage(Image bzImage) {
		this.bzImage = bzImage;
	}
	
}
