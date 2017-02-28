package com.zhongdan.games.sokoban;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;

import com.zhongdan.games.framework.utils.Constants;
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
	private Image boxTargetImg;
	private Image playerUpImg;
	private Image playerDownImg;
	private Image playerLeftImg;
	private Image playerRightImg;
	private Image playerUpTargetImg;
	private Image playerDownTargetImg;
	private Image playerLeftTargetImg;
	private Image playerRightTargetImg;
	private Image targetImg;
	private Image wallImg;
	private boolean isPlaying;
	private MapSprite[][] mapSprites = new MapSprite[GameConstants.GameSettings.ROW_NO][GameConstants.GameSettings.COL_NO];
	private int[][] map = new int[GameConstants.GameSettings.ROW_NO][GameConstants.GameSettings.COL_NO];
	private int level;
	public static int step;

	protected MainGameCanvas(MainMIDlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
		initCanvas();
	}

	private void initCanvas() {
		// Load images
		backgroundImg = ImageUtil.createImage("/background.png");
		wallImg = ImageUtil.createImage("/wall.png");
		boxImg = ImageUtil.createImage("/box.png");
		boxTargetImg = ImageUtil.createImage("/box_target.png");
		targetImg = ImageUtil.createImage("/target.png");
		playerUpImg = ImageUtil.createImage("/player_up.png");
		playerDownImg = ImageUtil.createImage("/player_down.png");
		playerLeftImg = ImageUtil.createImage("/player_left.png");
		playerRightImg = ImageUtil.createImage("/player_right.png");
		playerUpTargetImg = ImageUtil.createImage("/player_up.png");
		playerDownTargetImg = ImageUtil.createImage("/player_down.png");
		playerLeftTargetImg = ImageUtil.createImage("/player_left.png");
		playerRightTargetImg = ImageUtil.createImage("/player_right.png");

		// Initialize level
		level = 2;
		initLevel(level);
		drawBack();
		isPlaying = true;

		// Paint map
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();

		// Start game
		Thread t = new Thread(this);
		t.start();
	}

	private void initLevel(int newLevel) {
		this.level = newLevel;
		for (int i = 0; i < GameConstants.GameSettings.ROW_NO; i++) {
			for (int j = 0; j < GameConstants.GameSettings.COL_NO; j++) {
				switch (GameConstants.START_MAP[level - 1][i][j]) {
				case GameConstants.MapInfo.WALL:
					mapSprites[i][j] = new MapSprite(wallImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.TARGET:
					mapSprites[i][j] = new MapSprite(targetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.BOX:
					mapSprites[i][j] = new MapSprite(boxImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.BOX_TARGET:
					mapSprites[i][j] = new MapSprite(boxTargetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_UP:
					mapSprites[i][j] = new MapSprite(playerUpImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_DOWN:
					mapSprites[i][j] = new MapSprite(playerDownImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_LEFT:
					mapSprites[i][j] = new MapSprite(playerLeftImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_RIGHT:
					mapSprites[i][j] = new MapSprite(playerRightImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_UP_TARGET:
					mapSprites[i][j] = new MapSprite(playerUpTargetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_DOWN_TARGET:
					mapSprites[i][j] = new MapSprite(playerDownTargetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_LEFT_TARGET:
					mapSprites[i][j] = new MapSprite(playerLeftTargetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_RIGHT_TARGET:
					mapSprites[i][j] = new MapSprite(playerRightTargetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				}
				map[i][j] = GameConstants.START_MAP[level - 1][i][j];
			}
		}
	}

	protected void keyPressed(int keyCode) {
		if (keyCode == Constants.KeyCode.LEFT) {
			if (isPlaying) {
				movePlayer(0, -1);
			}
		} else if (keyCode == Constants.KeyCode.RIGHT) {
			if (isPlaying) {
				movePlayer(0, 1);
			}
		} else if (keyCode == Constants.KeyCode.UP) {
			if (isPlaying) {
				movePlayer(-1, 0);
			}
		} else if (keyCode == Constants.KeyCode.DOWN) {
			if (isPlaying) {
				movePlayer(1, 0);
			}
		} else if (keyCode == Constants.KeyCode.BACK) {
			this.midlet.notifyDestroyed();
		}
	}

	private void movePlayer(int rowInc, int colInc) {
		int[] playerPos = findPlayer();
		int row = playerPos[0];
		int col = playerPos[1];
		boolean isPlayerStandOnTarget = false;
		if (map[row][col] == GameConstants.MapInfo.PLAYER_UP || map[row][col] == GameConstants.MapInfo.PLAYER_DOWN
				|| map[row][col] == GameConstants.MapInfo.PLAYER_LEFT || map[row][col] == GameConstants.MapInfo.PLAYER_RIGHT) {
			isPlayerStandOnTarget = false;
		} else if (map[row][col] == GameConstants.MapInfo.PLAYER_UP_TARGET || map[row][col] == GameConstants.MapInfo.PLAYER_DOWN_TARGET
				|| map[row][col] == GameConstants.MapInfo.PLAYER_LEFT_TARGET || map[row][col] == GameConstants.MapInfo.PLAYER_RIGHT_TARGET) {
			isPlayerStandOnTarget = true;
		}
		int playerNewDir = 0;
		int playerOnTargetNewDir = 0;
		if (rowInc == 1) {
			playerNewDir = GameConstants.MapInfo.PLAYER_DOWN;
			playerOnTargetNewDir = GameConstants.MapInfo.PLAYER_DOWN_TARGET;
		} else if (rowInc == -1) {
			playerNewDir = GameConstants.MapInfo.PLAYER_UP;
			playerOnTargetNewDir = GameConstants.MapInfo.PLAYER_UP_TARGET;
		} else if (colInc == 1) {
			playerNewDir = GameConstants.MapInfo.PLAYER_RIGHT;
			playerOnTargetNewDir = GameConstants.MapInfo.PLAYER_RIGHT_TARGET;
		} else if (colInc == -1) {
			playerNewDir = GameConstants.MapInfo.PLAYER_LEFT;
			playerOnTargetNewDir = GameConstants.MapInfo.PLAYER_LEFT_TARGET;
		}

		if (map[row + rowInc][col + colInc] == GameConstants.MapInfo.BOX) {
			if (map[row + 2 * rowInc][col + 2 * colInc] == GameConstants.MapInfo.BLANK) {
				if (isPlayerStandOnTarget) {
					map[row][col] = GameConstants.MapInfo.TARGET;
				} else {
					map[row][col] = GameConstants.MapInfo.BLANK;
				}
				map[row + rowInc][col + colInc] = playerNewDir;
				map[row + 2 * rowInc][col + 2 * colInc] = GameConstants.MapInfo.BOX;
			} else if (map[row + 2 * rowInc][col + 2 * colInc] == GameConstants.MapInfo.TARGET) {
				if (isPlayerStandOnTarget) {
					map[row][col] = GameConstants.MapInfo.TARGET;
				} else {
					map[row][col] = GameConstants.MapInfo.BLANK;
				}
				map[row + rowInc][col + colInc] = playerNewDir;
				map[row + 2 * rowInc][col + 2 * colInc] = GameConstants.MapInfo.BOX_TARGET;
			}
		} else if (map[row + rowInc][col + colInc] == GameConstants.MapInfo.BOX_TARGET) {
			if (map[row + 2 * rowInc][col + 2 * colInc] == GameConstants.MapInfo.BLANK) {
				if (isPlayerStandOnTarget) {
					map[row][col] = GameConstants.MapInfo.TARGET;
				} else {
					map[row][col] = GameConstants.MapInfo.BLANK;
				}
				map[row + rowInc][col + colInc] = playerOnTargetNewDir;
				map[row + 2 * rowInc][col + 2 * colInc] = GameConstants.MapInfo.BOX;
			} else if (map[row + 2 * rowInc][col + 2 * colInc] == GameConstants.MapInfo.TARGET) {
				if (isPlayerStandOnTarget) {
					map[row][col] = GameConstants.MapInfo.TARGET;
				} else {
					map[row][col] = GameConstants.MapInfo.BLANK;
				}
				map[row + rowInc][col + colInc] = playerOnTargetNewDir;
				map[row + 2 * rowInc][col + 2 * colInc] = GameConstants.MapInfo.BOX_TARGET;
			}
		} else if (map[row + rowInc][col + colInc] == GameConstants.MapInfo.BLANK) {
			if (isPlayerStandOnTarget) {
				map[row][col] = GameConstants.MapInfo.TARGET;
			} else {
				map[row][col] = GameConstants.MapInfo.BLANK;
			}
			map[row + rowInc][col + colInc] = playerNewDir;
		} else if (map[row + rowInc][col + colInc] == GameConstants.MapInfo.TARGET) {
			if (isPlayerStandOnTarget) {
				map[row][col] = GameConstants.MapInfo.TARGET;
			} else {
				map[row][col] = GameConstants.MapInfo.BLANK;
			}
			map[row + rowInc][col + colInc] = playerOnTargetNewDir;
		}

		resetLayer();
	}

	private int[] findPlayer() {
		int[] playerPos = new int[2];
		for (int i = 0; i < GameConstants.GameSettings.ROW_NO; i++) {
			for (int j = 0; j < GameConstants.GameSettings.COL_NO; j++) {
				if (map[i][j] > 4) {
					playerPos[0] = i;
					playerPos[1] = j;
					break;
				}
			}
		}
		return playerPos;
	}

	private void resetLayer() {
		for (int i = 0; i < GameConstants.GameSettings.ROW_NO; i++) {
			for (int j = 0; j < GameConstants.GameSettings.COL_NO; j++) {
				if (null != mapSprites[i][j]) {
					layerManager.remove(mapSprites[i][j]);
				}
			}
		}
		for (int i = 0; i < GameConstants.GameSettings.ROW_NO; i++) {
			for (int j = 0; j < GameConstants.GameSettings.COL_NO; j++) {
				switch (map[i][j]) {
				case GameConstants.MapInfo.WALL:
					mapSprites[i][j] = new MapSprite(wallImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.TARGET:
					mapSprites[i][j] = new MapSprite(targetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.BOX:
					mapSprites[i][j] = new MapSprite(boxImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.BOX_TARGET:
					mapSprites[i][j] = new MapSprite(boxTargetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_UP:
					mapSprites[i][j] = new MapSprite(playerUpImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_DOWN:
					mapSprites[i][j] = new MapSprite(playerDownImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_LEFT:
					mapSprites[i][j] = new MapSprite(playerLeftImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_RIGHT:
					mapSprites[i][j] = new MapSprite(playerRightImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_UP_TARGET:
					mapSprites[i][j] = new MapSprite(playerUpTargetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_DOWN_TARGET:
					mapSprites[i][j] = new MapSprite(playerDownTargetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_LEFT_TARGET:
					mapSprites[i][j] = new MapSprite(playerLeftTargetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				case GameConstants.MapInfo.PLAYER_RIGHT_TARGET:
					mapSprites[i][j] = new MapSprite(playerRightTargetImg, i, j);
					layerManager.insert(mapSprites[i][j], 0);
					break;
				}
			}
		}
		drawBack();
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
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
	}

}
