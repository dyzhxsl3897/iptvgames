package com.zhongdan.games.tohellwithjohnny;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.zhongdan.games.framework.utils.ImageUtil;

public class MyGameCanvas extends GameCanvas implements Runnable {

	private MyMIDlet midlet;
	private Graphics graphics = this.getGraphics();
	private LayerManager layerManager = new LayerManager();
	private Image backgroundImg;
	private Image playerImg;
	private Sprite playerSprite;
	private Sprite playerSprite2;
	private int level;
	private boolean isPlaying;

	protected MyGameCanvas(MyMIDlet midlet) {
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
		TiledLayer backgroundLayer = new TiledLayer(1, 1, backgroundImg, backgroundImg.getWidth(), backgroundImg.getHeight());
		backgroundLayer.setCell(0, 0, 1);
		layerManager.append(backgroundLayer);

		// test
		playerSprite = new Sprite(playerImg, 112, 125);
		playerSprite.setPosition(100, 100);
		int[] seq = { 6, 5, 4, 3, 2, 1 };
		playerSprite.setFrameSequence(seq);
		playerSprite2 = new Sprite(playerImg, 112, 125);
		playerSprite2.setPosition(400, 100);
		int[] seq2 = { 7, 6, 5, 4, 3, 2, 1 };
		playerSprite2.setFrameSequence(seq2);
		playerSprite2.setTransform(Sprite.TRANS_MIRROR);
		layerManager.insert(playerSprite, 0);
		layerManager.insert(playerSprite2, 0);

		// Initialize the level
		level = 1;
		initLevel(level);
		isPlaying = true;

		// Paint map
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();

		Thread t = new Thread(this);
		t.start();
	}

	private void initLevel(int newLevel) {
		level = newLevel;

	}

	public void run() {
		while (isPlaying) {
			playerSprite.nextFrame();
			playerSprite2.nextFrame();
			layerManager.paint(graphics, 0, 0);
			this.flushGraphics();
			try {
				Thread.sleep(150);
			} catch (Exception e) {

			}
		}
	}

}
