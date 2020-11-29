package com.game.tank;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Tank extends Sprite{

	private Image sImage;
	private Image xImage;
	private Image zImage;
	private Image yImage;
	private boolean remove;
	private int cx;
	private Sprite bhSprite;
	private boolean isMe=true;
	private int bhTime=100;
	
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	private int fx=1;
	public Tank(Image image) {
		super(image);
	}
	public Tank(Image sImage,Image xImage,Image zImage,Image yImage,Image tkcx,boolean isMe) {
		super(sImage);
		this.isMe=isMe;
		if(!isMe){
			this.setImage(tkcx, 28, 28);
		}
		this.sImage=sImage;
		this.xImage=xImage;
		this.zImage=zImage;
		this.yImage=yImage;
	}
	
	public void up(int speed){
		this.setImage(sImage, 28, 28);
		this.setPosition(this.getX(), this.getY());
		this.move(0, -speed);
		this.setFx(1);
		this.setRemove(false);
		if(bhSprite!=null)bhSprite.setPosition(this.getX(),this.getY());
	}
	public void down(int speed){
		this.setImage(xImage, 28, 28);
		this.setPosition(this.getX(), this.getY());
		this.move(0, speed);
		this.setRemove(false);
		if(bhSprite!=null)bhSprite.setPosition(this.getX(),this.getY());
		this.setFx(2);
	}
	public void left(int speed){
		this.setImage(zImage, 28, 28);
		this.setPosition(this.getX(), this.getY());
		this.move(-speed, 0);
		this.setRemove(false);
		if(bhSprite!=null)bhSprite.setPosition(this.getX(),this.getY());
		this.setFx(3);
	}
	public void right(int speed){
		this.setImage(yImage, 28, 28);
		this.setPosition(this.getX(), this.getY());
		this.move(speed, 0);
		this.setRemove(false);
		if(bhSprite!=null)bhSprite.setPosition(this.getX(),this.getY());
		this.setFx(4);
	}
	public void reMove(int speed){
		if(remove)return;
		remove=true;
		switch (fx) {
		case 1:
			this.move(0, speed);
			if(bhSprite!=null)bhSprite.setPosition(this.getX(),this.getY());
			break;
		case 2:
			this.move(0, -speed);
			if(bhSprite!=null)bhSprite.setPosition(this.getX(),this.getY());
			break;
		case 3:
			this.move(speed, 0);
			if(bhSprite!=null)bhSprite.setPosition(this.getX(),this.getY());
			break;
		case 4:
			this.move(-speed, 0);
			if(bhSprite!=null)bhSprite.setPosition(this.getX(),this.getY());
			break;
		default:
			break;
		}
	}
	
	public void autoMove(int speed){
		remove=false;
		if(cx!=20){
			this.nextFrame();
			cx++;
		}else{
			this.setImage(xImage, 28, 28);
			switch (fx) {
			case 1:
				this.up(speed);
				break;
			case 2:
				this.down(speed);
				break;
			case 3:
				this.left(speed);
				break;
			case 4:
				this.right(speed);
				break;
			default:
				break;
			}
		}
		
	}
	
	//复活
	public void fh(){
		bhTime=100;
		if(bhSprite!=null){
			bhSprite.setVisible(true);
			bhSprite.setPosition(140+9*14, 99+22*14);
		}
		this.setPosition(138+9*14, 97+22*14);
	}
	
	
	public int getFx() {
		return fx;
	}
	public void setFx(int fx) {
		if(fx>4){
			this.fx=1;
		}else{
			this.fx = fx;
		}
	}
	public Image getsImage() {
		return sImage;
	}
	public void setsImage(Image sImage) {
		this.sImage = sImage;
	}
	public Image getxImage() {
		return xImage;
	}
	public void setxImage(Image xImage) {
		this.xImage = xImage;
	}
	public Image getzImage() {
		return zImage;
	}
	public void setzImage(Image zImage) {
		this.zImage = zImage;
	}
	public Image getyImage() {
		return yImage;
	}
	public void setyImage(Image yImage) {
		this.yImage = yImage;
	}
	public int getCx() {
		return cx;
	}
	public void setCx(int cx) {
		this.cx = cx;
	}
	public Sprite getBhSprite() {
		return bhSprite;
	}
	public void setBhSprite(Sprite bhSprite) {
		this.bhSprite = bhSprite;
	}
	public boolean isMe() {
		return isMe;
	}
	public void setMe(boolean isMe) {
		this.isMe = isMe;
	}
	public int getBhTime() {
		return bhTime;
	}
	public void setBhTime(int bhTime) {
		this.bhTime = bhTime;
	}
}
