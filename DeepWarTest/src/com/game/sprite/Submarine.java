package com.game.sprite;

import java.util.Random;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Constants.Device;

public class Submarine extends Sprite {

	/** 潜水艇图片 */
	private Image submarineImage;
	/** 图片宽度 */
	private int imageWidth;

	private int imageHeight;
	/** 移动单位 */
	private int dx = 2;
	/** 是否爆炸 */
	private boolean isBoom = false;
	/** 潜艇编号 */
	private int num;

	private Random rdm = new Random();

	/**
	 * @param submarineImage
	 *            潜水艇图片数组
	 * @param num
	 *            潜水艇编号
	 */
	public Submarine(Image submarineImage, int width, int height) {
		super(submarineImage, width, height);

		this.submarineImage = submarineImage;
		this.imageWidth = submarineImage.getWidth();
		this.imageHeight = submarineImage.getHeight();
		dx=rdm.nextInt(6)+1;
	}

	/**
	 * 判别是否爆炸
	 * 
	 * @return boolean true代表爆炸，false代表没有爆炸
	 */
	public boolean isIsBoom() {
		return isBoom;
	}

	/**
	 * 生成潜水艇坐标
	 */
	private void generate() {
		this.setPosition(-imageWidth-rdm.nextInt(100), rdm.nextInt(200) + 200);
		dx=rdm.nextInt(5)+1;
	}

	/**
	 * 爆炸处理
	 */
	public void boom() {

		isBoom = false;
		// 重新生成潜艇
		generate();
	}

	/**
	 * 移动方法
	 */
	public void move() {
		// 没有爆炸
		if (!isBoom) {
			if (this.getX() < Device.getWidth() + dx) {
				this.move(dx, 0);
			} else {
				this.setPosition(0, this.getY());
			}
		}
	}

	/**
	 * 获得图片高度
	 * 
	 * @return int 图片高度
	 */
	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 * 获得图片宽度
	 * 
	 * @return int 图片宽度
	 */
	public int getImageWidth() {
		return imageWidth;
	}

	/**
	 * 设置爆炸效果
	 * 
	 * @param isBoom
	 *            true代表爆炸
	 */
	public void setIsBoom(boolean isBoom) {
		this.isBoom = isBoom;
	}
}
