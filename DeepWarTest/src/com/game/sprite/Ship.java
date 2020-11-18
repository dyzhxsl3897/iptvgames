/**
 * 
 */
package com.game.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Constants.Device;

/**
 * @author Administrator
 * 
 */
public class Ship extends Sprite {

	private int imageWidth;

	private int imageHeight;

	/** �ƶ���λ */
	private int dx = 20;
	/** ��ʼը������ */
	private int bombNum = 5;

	public int getBombNum() {
		return bombNum;
	}

	public void setBombNum(int bombNum) {
		this.bombNum = bombNum;
	}

	/**
	 * @param boatImage
	 *            ����ͼƬ
	 * @param width
	 */
	public Ship(Image boatImage, int width, int height) {
		super(boatImage, width, height);

		imageWidth = boatImage.getWidth();
		imageHeight = boatImage.getHeight();
	}

	public void left() {
		if(this.getX()<dx){
			return;
		}
		this.move(-dx, 0);
	}

	public void right() {
		if(Device.getWidth()-this.getX()-imageWidth<dx){
			return;
		}
		this.move(dx, 0);
	}

	public void addBomb() {
		int bn = this.getBombNum();
		if (bn >= 0 && bn < 5) {
			bn++;
			this.setBombNum(bn);
		}
		if (bn < 0) {
			bn = 0;
			this.setBombNum(bn);
		}
		if (bn >= 5) {
			bn = 5;
			this.setBombNum(bn);
		}
	}
	
	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}
}
