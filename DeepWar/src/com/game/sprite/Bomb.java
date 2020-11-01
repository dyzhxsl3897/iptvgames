package com.game.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Constants.Device;

public class Bomb extends Sprite {

	/** 炸弹图片 */
	private Image bombImage;
	/** 是否可见 */
	private boolean isDisplay = false;

	/** 图片高度 */
	private int imageHeight;
	/** 图片宽度 */
	private int imageWidth;
	/** 移动单位 */
	private int dy = 3;

	private int initX;

	private int initY;
	
	/** 是否发射 */
	private boolean isFire =false;

	

	/**
	 * 构造方法
	 * 
	 * @param bombImage
	 *            Image 炸弹图片
	 */
	public Bomb(Image bombImage, int width, int height, int originX, int originY) {
		super(bombImage, width, height);

		this.bombImage = bombImage;
		this.imageHeight = bombImage.getHeight();
		this.imageWidth = bombImage.getWidth();
		this.initX = originX;
		this.initY = originY;
	}

	/**
	 * 是否和潜艇发生碰撞
	 * 
	 * @param submarine
	 *            Submarine 潜艇对象
	 * @return boolean true代表碰撞，false代表未发生碰撞
	 */
	public boolean collidesWith(Submarine submarine) {
		// 炸弹中心点的坐标
		int bx = this.getX() + imageWidth / 2;
		int by = this.getY() + imageHeight / 2;

		// 潜艇中心点的坐标
		int sx = submarine.getX() + submarine.getImageWidth() / 2;
		int sy = submarine.getY() + submarine.getImageHeight() / 2;

		// 判断中心点之间的距离
		if ((Math.abs(bx - sx) < (imageWidth + submarine.getImageWidth()) / 2)
				&& (Math.abs(by - sy) < (imageHeight + submarine
						.getImageHeight()) / 2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 碰撞处理
	 * 
	 * @param submarine
	 *            Submarine 潜艇对象
	 */
	public void handlecollidesWith(Submarine submarine) {
		// 设置炸弹不可见
		this.isDisplay = false;
		this.isFire=false;
		this.setPosition(initX, initY);
		// 潜艇爆炸
		submarine.setIsBoom(true);
	}

	public void fire() {
		if(Device.getHeight()-this.getY()-imageHeight<dy){
			this.setPosition(initX, initY);
			this.isDisplay = false;
			this.isFire = false;
		}

		this.move(0, dy);
	}

	public boolean isDisplay() {
		return isDisplay;
	}

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public int getInitX() {
		return initX;
	}

	public int getInitY() {
		return initY;
	}
	
	public boolean isFire() {
		return isFire;
	}

	public void setFire(boolean isFire) {
		this.isFire = isFire;
	}
}
