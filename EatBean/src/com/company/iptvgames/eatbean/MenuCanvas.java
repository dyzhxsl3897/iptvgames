package com.company.iptvgames.eatbean;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

import com.company.iptvgames.framework.utils.Constants.KeyCode;

public class MenuCanvas extends GameCanvas {

	public MainMIDlet midlet;
	private Image homeImage;
	private Image startImage;
	private Image exitImage;
	private int startmenu = 1;
	private Graphics gra;

	protected MenuCanvas(MainMIDlet midlet) {
		super(false);// 传入false值，一旦按键按 下，就会调用传统的键盘事件处理方法
		this.midlet = midlet;
		gra = getGraphics();

		// load image
		try {
			homeImage = Image.createImage("/homepage.png");
			startImage = Image.createImage("/start.png");
			exitImage = Image.createImage("/exit.png");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		paint();
		this.flushGraphics();// 将后备屏幕缓冲区内容输出到显示屏上
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
					System.out.println("exit game:");
					midlet.notifyDestroyed();
				}
			}
		}
	}

	public void paint() {
		gra.drawImage(homeImage, 0, 0, 0);
		if (startmenu == 1) {
			gra.drawImage(startImage, 184, 281, 0);
		}
		if (startmenu == 0) {
			gra.drawImage(exitImage, 184, 392, 0);
		}
	}

}
