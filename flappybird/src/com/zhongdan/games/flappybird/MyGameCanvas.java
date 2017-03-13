package com.zhongdan.games.flappybird;

import java.util.Random;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.zhongdan.games.flappybird.GameConstants.Bird;
import com.zhongdan.games.flappybird.GameConstants.GameStatus;
import com.zhongdan.games.flappybird.GameConstants.Pipe;
import com.zhongdan.games.utils.Constants;
import com.zhongdan.games.utils.ImageUtil;

public class MyGameCanvas extends GameCanvas implements Runnable {

	// private MyMIDlet midlet;
	private Graphics graphics;
	private LayerManager layerManager = new LayerManager();
	private Random random;
	private Image backgroundImg;
	private Image pipeImg;
	private Image birdImg;
	TiledLayer backgroundLayer;
	TiledLayer welcomeLayer;
	private Vector pipeSpriteList;
	private Sprite birdSprite;
	private int birdFrameSeqNo;
	private int birdSpeedV = 0;
	private int gravity = 2;
	private int gameStatus;
	private Thread t;

	protected MyGameCanvas(MyMIDlet midlet) {
		super(false);
		// this.midlet = midlet;
		graphics = this.getGraphics();
		this.setFullScreenMode(true);
		random = new Random();
		initCanvas();
	}

	private void initCanvas() {
		// Load images
		backgroundImg = ImageUtil.createImage("/background.png");
		pipeImg = ImageUtil.createImage("/pipe.png");
		birdImg = ImageUtil.createImage("/bird.png");

		// Draw background
		backgroundLayer = new TiledLayer(1, 1, backgroundImg, backgroundImg.getWidth(), backgroundImg.getHeight());
		backgroundLayer.setCell(0, 0, 1);
		layerManager.append(backgroundLayer);

		// Initialize bird
		birdSprite = new Sprite(birdImg, Bird.WIDTH, Bird.HEIGHT);
		birdFrameSeqNo = birdSprite.getFrameSequenceLength();
		layerManager.insert(birdSprite, 0);

		// Paint map
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();

		// Start game
		gameStatus = GameStatus.START;
		initGame();
		t = new Thread(this);
		t.start();
	}

	private void initGame() {
		birdSprite.setFrame(0);
		birdSprite.setPosition(150, 150);
		birdSpeedV = 0;

		// Draw pipe
		if (pipeSpriteList != null && 0 < pipeSpriteList.size()) {
			for (int i = 0; i < pipeSpriteList.size(); i++) {
				Sprite pipe = (Sprite) pipeSpriteList.elementAt(i);
				layerManager.remove(pipe);
			}
		}
		pipeSpriteList = new Vector(10, 10);
		for (int i = 0; i < 10; i++) {
			int height = (random.nextInt() >>> 1) % 5 + 4;
			Sprite pipeBody = null;
			for (int j = 0; j < height; j++) {
				pipeBody = new Sprite(pipeImg, 45, 25);
				pipeBody.setPosition(640 + i * GameConstants.Pipe.DISTANCE, j * 25);
				pipeBody.setFrame(2);
				pipeSpriteList.addElement(pipeBody);
				layerManager.insert(pipeBody, 0);
			}
			pipeBody = new Sprite(pipeImg, 45, 25);
			pipeBody.setPosition(640 + i * GameConstants.Pipe.DISTANCE, height * 25);
			pipeBody.setFrame(3);
			pipeSpriteList.addElement(pipeBody);
			layerManager.insert(pipeBody, 0);
			for (int j = 0; j < GameConstants.Pipe.TOTAL - height; j++) {
				pipeBody = new Sprite(pipeImg, 45, 25);
				pipeBody.setPosition(640 + i * GameConstants.Pipe.DISTANCE, 417 - j * 25);
				pipeBody.setFrame(1);
				pipeSpriteList.addElement(pipeBody);
				layerManager.insert(pipeBody, 0);
			}
			pipeBody = new Sprite(pipeImg, 45, 25);
			pipeBody.setPosition(640 + i * GameConstants.Pipe.DISTANCE, 417 - (GameConstants.Pipe.TOTAL - height) * 25);
			pipeBody.setFrame(0);
			pipeSpriteList.addElement(pipeBody);
			layerManager.insert(pipeBody, 0);
		}
	}

	protected void keyPressed(int keyCode) {
		if (this.gameStatus == GameStatus.PLAYING) {
			if (keyCode == Constants.KeyCode.OK) {
				birdSpeedV = -16;
			}
		} else {
			if (keyCode == Constants.KeyCode.OK) {
				initGame();
				this.gameStatus = GameStatus.PLAYING;
			}
		}
	}

	private boolean birdCollidesWithPipe() {
		int length = pipeSpriteList.size();
		for (int i = 0; i < length; i++) {
			Sprite pipe = (Sprite) pipeSpriteList.elementAt(i);
			if (birdSprite.collidesWith(pipe, true)) {
				return true;
			}
		}
		return false;
	}

	private void game(int gameStatus) {
		switch (gameStatus) {
		case GameStatus.PLAYING:
			long start = System.currentTimeMillis();

			birdSpeedV += gravity;
			birdSprite.move(0, birdSpeedV);
			birdSprite.setFrame((birdSprite.getFrame() + 1) % birdFrameSeqNo);

			int length = pipeSpriteList.size();
			for (int i = 0; i < length; i++) {
				Sprite pipe = (Sprite) pipeSpriteList.elementAt(i);
				if (pipe.getX() + 8 + GameConstants.Pipe.WIDTH < 0) {
					pipe.move(Pipe.DISTANCE * 10, 0);
				}
				pipe.move(-4, 0);
			}

			if (birdSprite.getY() > 420) {
				this.gameStatus = GameStatus.LOST;
				birdSprite.setPosition(birdSprite.getX(), 422);
			} else if (birdSprite.getY() <= 0) {
				this.gameStatus = GameStatus.LOST;
			} else if (birdCollidesWithPipe()) {
				this.gameStatus = GameStatus.LOST;
			}

			layerManager.paint(graphics, 0, 0);
			this.flushGraphics();

			long end = System.currentTimeMillis();
			long usedTime = end - start;
			long sleepTime = GameConstants.GameSettings.TIMER;
			if (usedTime < GameConstants.GameSettings.TIMER) {
				sleepTime = GameConstants.GameSettings.TIMER - usedTime;
			} else {
				sleepTime = 0;
			}
			try {
				Thread.sleep(sleepTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	public void run() {
		while (true) {
			game(this.gameStatus);
			this.flushGraphics(0, 0, 0, 0);
		}
	}
}
