package com.yunyouhudong.games.deepwar;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

public class MenuCanvas extends GameCanvas implements Runnable {

	public MainMidlet midlet;

	private Image bgMainImage;

	private Sprite bgMainSprite;

	private final static long DELAY = 300;
	public Thread mainThread = new Thread(this);
	public boolean isRunning = false;
	public boolean isExit = false;

	private LayerManager layerManager = new LayerManager();

	protected MenuCanvas(MainMidlet midlet) {
		super(false);

		this.midlet = midlet;
		this.setFullScreenMode(true);

		try {
			bgMainImage = Image.createImage("/bj.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}

		bgMainSprite = new Sprite(bgMainImage);

		layerManager.append(bgMainSprite);
		initMenu();
	}

	public void initMenu() {
		mainThread = new Thread(this);
		if (!mainThread.isAlive()) {
			mainThread.start();
		}
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
				Graphics g = getGraphics();
				drawScreen(g);
				Thread.currentThread();
				Thread.sleep(DELAY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
