package com.game.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class EnemyBomb extends Sprite {

	/** 是否可见 */
	private boolean isDisplay = false;

	/** 图片高度 */
	private int imageHeight;
	/** 图片宽度 */
	private int imageWidth;
	/** 移动单位 */
	private int dy = 3;
	
	private boolean isFire = false;

	public boolean isFire() {
		return isFire;
	}

	public void setFire(boolean isFire) {
		this.isFire = isFire;
	}

	public EnemyBomb(Image image, int frameWidth, int frameHeight) {
		super(image, frameWidth, frameHeight);

		imageHeight = image.getHeight();
		imageWidth = image.getWidth();
	}

	/**
	 * 是否和潜艇发生碰撞
	 * 
	 * @param Ship
	 *            Submarine 潜艇对象
	 * @return boolean true代表碰撞，false代表未发生碰撞
	 */
	public boolean collidesWith(Ship ship) {
		// 炸弹中心点的坐标
		int bx = this.getX() + imageWidth / 2;
		int by = this.getY() + imageHeight / 2;

		// 潜艇中心点的坐标
		int sx = ship.getX() + ship.getImageWidth() / 2;
		int sy = ship.getY() + ship.getImageHeight() / 2;

		// 判断中心点之间的距离
		if ((Math.abs(bx - sx) < (imageWidth + ship.getImageWidth()) / 2)
				&& (Math.abs(by - sy) < (imageHeight + ship.getImageHeight()) / 2)) {
			return true;
		} else {
			return false;
		}
	}

	public void fire() {
		if (this.getY() - imageHeight -55 < dy ) {
			this.setPosition(-100, -100);
			this.isDisplay = false;
			this.isFire = false;
		}

		this.move(0, -dy);
	}

	public boolean isDisplay() {
		return isDisplay;
	}

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}
	
}
