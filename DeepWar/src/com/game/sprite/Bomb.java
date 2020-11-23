package com.game.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Constants.Device;

public class Bomb extends Sprite {

	/** ը��ͼƬ */
	/** �Ƿ�ɼ� */
	private boolean isDisplay = false;

	/** ͼƬ�߶� */
	private int imageHeight;
	/** ͼƬ��� */
	private int imageWidth;
	/** �ƶ���λ */
	private int dy = 3;

	private int initX;

	private int initY;
	
	/** �Ƿ��� */
	private boolean isFire =false;

	

	/**
	 * ���췽��
	 * 
	 * @param bombImage
	 *            Image ը��ͼƬ
	 */
	public Bomb(Image bombImage, int width, int height, int originX, int originY) {
		super(bombImage, width, height);

		this.imageHeight = bombImage.getHeight();
		this.imageWidth = bombImage.getWidth();
		this.initX = originX;
		this.initY = originY;
	}

	/**
	 * �Ƿ��Ǳͧ������ײ
	 * 
	 * @param submarine
	 *            Submarine Ǳͧ����
	 * @return boolean true�����ײ��false���δ������ײ
	 */
	public boolean collidesWith(Submarine submarine) {
		// ը�����ĵ�����
		int bx = this.getX() + imageWidth / 2;
		int by = this.getY() + imageHeight / 2;

		// Ǳͧ���ĵ�����
		int sx = submarine.getX() + submarine.getImageWidth() / 2;
		int sy = submarine.getY() + submarine.getImageHeight() / 2;

		// �ж����ĵ�֮��ľ���
		if ((Math.abs(bx - sx) < (imageWidth + submarine.getImageWidth()) / 2)
				&& (Math.abs(by - sy) < (imageHeight + submarine
						.getImageHeight()) / 2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��ײ����
	 * 
	 * @param submarine
	 *            Submarine Ǳͧ����
	 */
	public void handlecollidesWith(Submarine submarine) {
		this.isDisplay = false;
		this.isFire=false;
		this.setPosition(initX, initY);
		submarine.setIsBoom(true);
	}

	public void fire() {
		if(Device.getHeight()-this.getY()-imageHeight<dy){
			this.setPosition(initX, initY);
			this.isDisplay = false;
			this.isFire = false;
			return;
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
