package com.game.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class EnemyBomb extends Sprite {

	/** �Ƿ�ɼ� */
	private boolean isDisplay = false;

	/** ͼƬ�߶� */
	private int imageHeight;
	/** ͼƬ��� */
	private int imageWidth;
	/** �ƶ���λ */
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
	 * �Ƿ��Ǳͧ������ײ
	 * 
	 * @param Ship
	 *            Submarine Ǳͧ����
	 * @return boolean true�����ײ��false���δ������ײ
	 */
	public boolean collidesWith(Ship ship) {
		// ը�����ĵ�����
		int bx = this.getX() + imageWidth / 2;
		int by = this.getY() + imageHeight / 2;

		// Ǳͧ���ĵ�����
		int sx = ship.getX() + ship.getImageWidth() / 2;
		int sy = ship.getY() + ship.getImageHeight() / 2;

		// �ж����ĵ�֮��ľ���
		if ((Math.abs(bx - sx) <= ((imageWidth + ship.getImageWidth()) / 2))
				&& (Math.abs(by - sy) <= ((imageHeight + ship.getImageHeight()) / 2))) {
			return true;
		} else {
			return false;
		}
	}
	
	public void handlecollidesWith() {
		this.isDisplay = false;
		this.isFire=false;
		this.setPosition(-100, -100);
	}

	public void fire(int shipHeight) {
		if (this.getY()-46- shipHeight< dy ) {
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
