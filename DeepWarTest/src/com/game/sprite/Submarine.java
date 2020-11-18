package com.game.sprite;

import java.util.Random;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Constants.Device;

public class Submarine extends Sprite {

	/** ǱˮͧͼƬ */
	private Image submarineImage;
	/** ͼƬ��� */
	private int imageWidth;

	private int imageHeight;
	/** �ƶ���λ */
	private int dx = 2;
	/** �Ƿ�ը */
	private boolean isBoom = false;
	/** Ǳͧ��� */
	private int num;

	private Random rdm = new Random();

	/**
	 * @param submarineImage
	 *            ǱˮͧͼƬ����
	 * @param num
	 *            Ǳˮͧ���
	 */
	public Submarine(Image submarineImage, int width, int height) {
		super(submarineImage, width, height);

		this.submarineImage = submarineImage;
		this.imageWidth = submarineImage.getWidth();
		this.imageHeight = submarineImage.getHeight();
		dx=rdm.nextInt(6)+1;
	}

	/**
	 * �б��Ƿ�ը
	 * 
	 * @return boolean true����ը��false����û�б�ը
	 */
	public boolean isIsBoom() {
		return isBoom;
	}

	/**
	 * ����Ǳˮͧ����
	 */
	private void generate() {
		this.setPosition(-imageWidth-rdm.nextInt(100), rdm.nextInt(200) + 200);
		dx=rdm.nextInt(5)+1;
	}

	/**
	 * ��ը����
	 */
	public void boom() {

		isBoom = false;
		// ��������Ǳͧ
		generate();
	}

	/**
	 * �ƶ�����
	 */
	public void move() {
		// û�б�ը
		if (!isBoom) {
			if (this.getX() < Device.getWidth() + dx) {
				this.move(dx, 0);
			} else {
				this.setPosition(0, this.getY());
			}
		}
	}

	/**
	 * ���ͼƬ�߶�
	 * 
	 * @return int ͼƬ�߶�
	 */
	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 * ���ͼƬ���
	 * 
	 * @return int ͼƬ���
	 */
	public int getImageWidth() {
		return imageWidth;
	}

	/**
	 * ���ñ�ըЧ��
	 * 
	 * @param isBoom
	 *            true����ը
	 */
	public void setIsBoom(boolean isBoom) {
		this.isBoom = isBoom;
	}
}
