package com.zhongdan.games.tetris;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.midlet.MIDlet;

import com.zhongdan.games.framework.utils.Constants;

public class MyGameCanvas extends GameCanvas {

	MIDlet midlet;
	private Graphics graphics;
	private LayerManager layerManager = new LayerManager();
	private Image backgroundImg;
	private Sprite[][] allBrickSprite = new Sprite[MyGameConstants.Playboard.ROW_NO][MyGameConstants.Playboard.COL_NO];
	private BrickItem movingBrick;
	private BrickItem nextBrick;
	private boolean isPlaying = true;
	private int currentLevel = 0;
	private int score = 0;
	private ButtonSprite btnDown;
	private ButtonSprite btnLeft;
	private ButtonSprite btnRight;

	// private ButtonSprite btnDdown;
	// private ButtonSprite btnPause;
	// private ButtonSprite btnReturns;

	protected MyGameCanvas(MIDlet midlet) {
		super(false);
		this.midlet = midlet;
		graphics = this.getGraphics();
		this.setFullScreenMode(true);
		initCanvas();
	}

	protected void keyRepeated(int keyCode) {
		if (keyCode == Constants.KeyCode.DOWN) {
			keyPressed(keyCode);
		} else if (keyCode == Constants.KeyCode.LEFT) {
			keyPressed(keyCode);
		} else if (keyCode == Constants.KeyCode.RIGHT) {
			keyPressed(keyCode);
		} else if (keyCode == Constants.KeyCode.OK) {
			keyPressed(keyCode);
		}
	}

	protected void keyPressed(int keyCode) {
		if (keyCode == Constants.KeyCode.DOWN) {
			if (isPlaying) {
				btnDown.toggleButton();
				movingBrick.moveDown();
				layerManager.paint(graphics, 0, 0);
				this.flushGraphics();
			}
		} else if (keyCode == Constants.KeyCode.LEFT) {
			if (isPlaying) {
				btnLeft.toggleButton();
				movingBrick.moveLeft();
				layerManager.paint(graphics, 0, 0);
				this.flushGraphics();
			}
		} else if (keyCode == Constants.KeyCode.RIGHT) {
			if (isPlaying) {
				btnRight.toggleButton();
				movingBrick.moveRight();
				layerManager.paint(graphics, 0, 0);
				this.flushGraphics();
			}
		} else if (keyCode == Constants.KeyCode.OK) {
			if (isPlaying) {
				movingBrick.turnBrick();
				layerManager.paint(graphics, 0, 0);
				this.flushGraphics();
			}
		} else if (keyCode == Constants.KeyCode.BACK) {
			midlet.notifyDestroyed();
		}
	}

	private void initCanvas() {
		// Initialize background
		if (backgroundImg == null) {
			try {
				backgroundImg = Image.createImage("/background.png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		TiledLayer backgroundLayer = new TiledLayer(1, 1, backgroundImg, backgroundImg.getWidth(), backgroundImg.getHeight());
		backgroundLayer.setCell(0, 0, 1);
		layerManager.insert(backgroundLayer, 0);

		// Initialize buttons
		new ButtonSprite("ddown", this, graphics, MyGameConstants.ButtonIcon.ddown_X, MyGameConstants.ButtonIcon.ddown_Y);
		btnLeft = new ButtonSprite("left", this, graphics, MyGameConstants.ButtonIcon.left_X, MyGameConstants.ButtonIcon.left_Y);
		btnDown = new ButtonSprite("down", this, graphics, MyGameConstants.ButtonIcon.down_X, MyGameConstants.ButtonIcon.down_Y);
		btnRight = new ButtonSprite("right", this, graphics, MyGameConstants.ButtonIcon.right_X, MyGameConstants.ButtonIcon.right_Y);
		new ButtonSprite("pause", this, graphics, MyGameConstants.ButtonIcon.pause_X, MyGameConstants.ButtonIcon.pause_Y);
		new ButtonSprite("returns", this, graphics, MyGameConstants.ButtonIcon.returns_X, MyGameConstants.ButtonIcon.returns_Y);

		// Initialize moving brick
		Random rnd = new Random();
		int type = (rnd.nextInt() >>> 1) % 7;
		int dir = (rnd.nextInt() >>> 1) % 4;
		nextBrick = BrickItem.createNewBrick(type, dir, this, true);
		type = (rnd.nextInt() >>> 1) % 7;
		dir = (rnd.nextInt() >>> 1) % 4;
		movingBrick = BrickItem.createNewBrick(type, dir, this, false);

		// Paint all
		layerManager.setViewWindow(0, 0, this.getWidth(), this.getHeight());
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();

		// Start drop down
		Timer dropDownTimer = new Timer();
		dropDownTimer.schedule(new DropdownTask(this, graphics), 0, MyGameConstants.GameSettings.DROPDOWN_INTERVAL[currentLevel]);
	}

	public LayerManager getLayerManager() {
		return layerManager;
	}

	public Sprite[][] getAllBrickSprite() {
		return allBrickSprite;
	}

	public BrickItem getMovingBrick() {
		return movingBrick;
	}

	public BrickItem getNextBrick() {
		return nextBrick;
	}

	public void setMovingBrick(BrickItem movingBrick) {
		this.movingBrick = movingBrick;
	}

	public void setNextBrick(BrickItem nextBrick) {
		this.nextBrick = nextBrick;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

}
