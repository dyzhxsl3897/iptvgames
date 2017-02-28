package com.zhongdan.games.tohellwithjohnny;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;

import com.zhongdan.games.framework.utils.ImageUtil;

public class MainGameCanvas extends GameCanvas implements Runnable {

	private MainMIDlet midlet;
	private Graphics graphics = this.getGraphics();
	private LayerManager layerManager = new LayerManager();
	private Image backgroundImg;
	private Image playerImg;
	private int level;
	private boolean isPlaying;

	protected MainGameCanvas(MainMIDlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
		initCanvas();
	}

	private void initCanvas() {
		// Load images
		backgroundImg = ImageUtil.createImage("/background.png");
		playerImg = ImageUtil.createImage("/player.png");

		// Draw background
		graphics.drawImage(backgroundImg, 0, 0, 0);

		// Initialize the level
		level = 1;
		initLevel(level);
		isPlaying = true;

		// Paint map
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	private void initLevel(int newLevel) {
		level = newLevel;

	}

	public void run() {
		while (isPlaying) {

		}
	}

}
