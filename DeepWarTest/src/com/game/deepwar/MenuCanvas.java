/**
 * 
 */
package com.game.deepwar;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.game.Constants.KeyCode;

/**
 * @author Administrator
 * 
 */
public class MenuCanvas extends GameCanvas implements Runnable {

	public MainMidlet midlet;

	private Image bgMainImage;
	private Image startImage;

	private Sprite bgMainSprite;
	private Sprite startSprite;

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
			startImage = Image.createImage("/start.png");
			bgMainImage = Image.createImage("/bj.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}

		bgMainSprite = new Sprite(bgMainImage);
		startSprite = new Sprite(startImage, 69, 74);
		startSprite.setPosition(379, 395);

		layerManager.append(startSprite);
		layerManager.append(bgMainSprite);
		initMenu();
	}

	public void initMenu() {
		startSprite.setPosition(379, 395);

		mainThread = new Thread(this);
		if (!mainThread.isAlive()) {
			mainThread.start();
		}
	}

	private void input(int keyCode) {
		switch (keyCode) {
		case KeyCode.OK:
			this.isRunning = false;
			if(isExit){
				midlet.exit();
			}
			if (!midlet.gameCanvas.isRunning) {
				midlet.gameCanvas.isRunning = true;
				midlet.gameCanvas.initializeGame();
			}
			midlet.dis.setCurrent(midlet.gameCanvas);
			break;
		case KeyCode.BACK:
			midlet.exit();
			break;
		case KeyCode.UP:
			startSprite.setPosition(379, 395);
			isExit = false;
			break;
		case KeyCode.DOWN:
			startSprite.setPosition(349, 458);
			isExit = true;
			break;
		}
		drawScreen(getGraphics());
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
				Thread.currentThread();
				Thread.sleep(DELAY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
