package com.game.breaker;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.game.Constants.KeyCode;

public class MenuCanvas extends GameCanvas implements Runnable {

	private final static long DELAY = 300;
	public Thread mainThread = new Thread(this);
	public boolean isRunning = false;
	public MainMidlet midlet;
	private Image bgMainImage;
	private Image startImage;
	private Image backImage;
	private Sprite bgMainSprite;
	private Sprite startSprite;
	private Sprite backSprite;

	private LayerManager layerManager = new LayerManager();

	protected MenuCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;

		// 勾勒菜单
		this.setFullScreenMode(true);
		try {
			startImage = Image.createImage("/start.png");
			backImage = Image.createImage("/back.png");
			bgMainImage = Image.createImage("/bg_main.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		bgMainSprite = new Sprite(bgMainImage);
		startSprite = new Sprite(startImage, 150, 79);
		startSprite.setPosition(254, 279);
		backSprite = new Sprite(backImage);
		backSprite.setPosition(591, 5);
		layerManager.append(startSprite);
		// layerManager.append(backSprite);
		layerManager.append(bgMainSprite);
		if (!mainThread.isAlive()) {
			mainThread.start();
		}
	}

	private void input(int keyCode) {
		switch (keyCode) {
		case KeyCode.OK:
			this.isRunning = false;
			midlet.dis.setCurrent(midlet.gameCanvas);
			break;
		case KeyCode.BACK:
			midlet.exit();
			break;
		}
	}

	protected void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
		input(keyCode);
	}

	protected void keyRepeated(int keyCode) {
		super.keyRepeated(keyCode);
		input(keyCode);
	}

	private void drawScreen(Graphics g) {
		g.setColor(0xffffff);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(0x0000ff);
		layerManager.paint(g, 0, 0);
		flushGraphics();
	}

	public void run() {
		isRunning = true;
		try {
			while (isRunning) {
				startSprite.nextFrame();
				Graphics g = getGraphics();
				drawScreen(g);
				// System.gc();
				Thread.currentThread();
				Thread.sleep(DELAY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
