package com.company.iptvgames.eatbean;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.company.iptvgames.framework.utils.Constants.KeyCode;

public class MenuCanvas extends GameCanvas {

	public MainMIDlet midlet;
	private LayerManager layerManager = new LayerManager();
	private Image homeImage;
	private Image startImage;
	private Image exitImage;
	private int startmenu = 1;
	private Sprite bkgLayer;
	private Sprite startSprite;
	private Sprite exitSprite;
	private Graphics gra;

	protected MenuCanvas(MainMIDlet midlet) {
		super(false);// 传入false值，一旦按键按 下，就会调用传统的键盘事件处理方法
		this.midlet = midlet;
		gra = getGraphics();

		try {
			// load image
			homeImage = Image.createImage("/homepage.png");
			startImage = Image.createImage("/start.png");
			exitImage = Image.createImage("/exit.png");
			bkgLayer = new Sprite(homeImage);
			startSprite = new Sprite(startImage);
			exitSprite = new Sprite(exitImage);
			exitSprite.setVisible(false);
			startSprite.setPosition(184, 281);
			exitSprite.setPosition(184, 392);
			layerManager.append(bkgLayer);
			layerManager.insert(startSprite, 0);
			layerManager.insert(exitSprite, 0);
			layerManager.paint(gra, 0, 0);
			this.flushGraphics();// 将后备屏幕缓冲区内容输出到显示屏上
		} catch (Exception ex) {
			gra.drawString(ex.getMessage(), 0, 0, 0);
		}

	}

	protected void keyPressed(int keyCode) {
		if (midlet.menuShow) {
			if (keyCode == KeyCode.UP) {
				startmenu = 1;
				paint();
			}
			if (keyCode == KeyCode.DOWN) {
				startmenu = 0;
				paint();
			}
			this.flushGraphics();

			if (keyCode == KeyCode.OK) {
				if (startmenu == 1) {
					midlet.menuShow = false;
					midlet.dis.setCurrent(midlet.gameCanvas);
					midlet.gamestatus = 1;
					midlet.NPCbegin = System.currentTimeMillis();
					this.flushGraphics();
				} else {
					midlet.notifyDestroyed();
				}
			}
		}
	}

	public void paint() {
		if (startmenu == 1) {
			startSprite.setVisible(true);
			exitSprite.setVisible(false);
		}
		if (startmenu == 0) {
			startSprite.setVisible(false);
			exitSprite.setVisible(true);
		}
		layerManager.paint(gra, 0, 0);
		this.flushGraphics();
	}

}
