package com.zhongdan.games.sokoban;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;

import com.zhongdan.games.framework.utils.ImageUtil;

public class MainGameCanvas extends GameCanvas implements Runnable {

	private MainMIDlet midlet;
	private Graphics graphics = this.getGraphics();
	private LayerManager layerManager = new LayerManager();
	public final static int LEVELINFO = 0;
	public final static int GAME = 1;
	public final static int WIN = 2;
	public final static int LOST = 3;
	public final static int GAMEEND = 4;
	public final static int PAUSE = 5;
	public int gameState = LEVELINFO;
	private Image backgroundImg;
	private Image boxImg;
	private Image playerUpImg;
	private Image playerDownImg;
	private Image playerLeftImg;
	private Image playerRightImg;
	private Image targetImg;
	private Image wallImg;
	private MapSprite[][] mapSprites = new MapSprite[GameConstants.GameSettings.ROW_NO][GameConstants.GameSettings.COL_NO];
	private int level;
	public static int step;
	public int nowTime;
	private int count = 0;

	protected MainGameCanvas(MainMIDlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
		initCanvas();
	}

	private void initCanvas() {
		// Load images
		backgroundImg = ImageUtil.createImage("/background.png");
		boxImg = ImageUtil.createImage("/box.png");
		playerUpImg = ImageUtil.createImage("/player_up.png");
		playerDownImg = ImageUtil.createImage("/player_down.png");
		playerLeftImg = ImageUtil.createImage("/player_left.png");
		playerRightImg = ImageUtil.createImage("/player_right.png");
		targetImg = ImageUtil.createImage("/target.png");
		wallImg = ImageUtil.createImage("/wall.png");

		// Initialize level
		level = 9;
		initLevel(level);
		drawBack();

		// Paint map
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();

		// Start game
		Thread t = new Thread(this);
		t.start();
	}

	private void initLevel(int newLevel) {
		nowTime = 0;
		this.level = newLevel;
		for (int i = 0; i < GameConstants.GameSettings.ROW_NO; i++) {
			for (int j = 0; j < GameConstants.GameSettings.COL_NO; j++) {
				switch (GameConstants.START_MAP[level - 1][i][j]) {
				case 1:
					mapSprites[i][j] = new MapSprite(wallImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case 2:
					mapSprites[i][j] = new MapSprite(targetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case 3:
					mapSprites[i][j] = new MapSprite(boxImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case 4:
					mapSprites[i][j] = new MapSprite(boxImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case 5:
					mapSprites[i][j] = new MapSprite(playerUpImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case 6:
					mapSprites[i][j] = new MapSprite(playerDownImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case 7:
					mapSprites[i][j] = new MapSprite(playerLeftImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case 8:
					mapSprites[i][j] = new MapSprite(playerRightImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case 9:
					mapSprites[i][j] = new MapSprite(playerRightImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				}
			}
		}
	}

	protected void keyPressed(int keyCode) {
		// TODO Auto-generated method stub
		super.keyPressed(keyCode);
	}

	public void run() {
		while (true) {
			long currTime = System.currentTimeMillis();
			// game(this.gameState);
			long lastTime = currTime;
			long delay = 100 - (currTime - lastTime);
			try {
				if (delay <= 0)
					delay = 1;
				Thread.sleep(delay);
			} catch (Exception exception) {
			}
		}
	}

	private void drawBack() {
		graphics.drawImage(backgroundImg, 0, 0, 0);
		graphics.setColor(0Xffff00);
		// TODO
		// graphics.drawString(String.valueOf(nowTime), 220, 0, 0);
		// graphics.drawString(String.valueOf(level), 212, 22, 0);
	}

}
