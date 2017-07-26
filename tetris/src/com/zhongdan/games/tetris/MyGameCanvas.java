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

import com.zhongdan.games.framework.utils.Constants;

public class MyGameCanvas extends GameCanvas {

	MainMidlet midlet;
	private Graphics graphics;
	private LayerManager layerManager = new LayerManager();
	private Image backgroundImg;
	private Sprite[][] allBrickSprite = new Sprite[MyGameConstants.Playboard.ROW_NO][MyGameConstants.Playboard.COL_NO];
	private BrickItem movingBrick;
	private BrickItem nextBrick;
	private boolean isPlaying = true;
	private ButtonSprite btnDown;
	private ButtonSprite btnLeft;
	private ButtonSprite btnRight;
	private ScoreSprite score = null;
	private LevelSprite level = null;
	private LineSprite line = null;

	protected MyGameCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;
		graphics = this.getGraphics();
		this.setFullScreenMode(true);
		initCanvas(1);
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
			this.midlet.getDisplay().setCurrent(this.midlet.getMenuCanvas());
		}
	}

	public void initCanvas(int level) {
		// Initialize layerManager
		for (int i = layerManager.getSize() - 1; i >= 0; i--) {
			layerManager.remove(layerManager.getLayerAt(i));
		}

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

		// Initialize score/level
		score = new ScoreSprite(0, this, graphics);
		line = new LineSprite(0, this, graphics);
		this.level = new LevelSprite(level, this, graphics);

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
	}

	public void startDropDown() {
		Timer dropDownTimer = new Timer();
		dropDownTimer.schedule(new DropdownTask(this, graphics), 0, MyGameConstants.GameSettings.DROPDOWN_INTERVAL[this.level.getLevel()]);
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

	public int getCurrentLevel() {
		return this.level.getLevel();
	}

	public void setCurrentLevel(int currentLevel) {
		this.level.setLevel(currentLevel);
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public int getScore() {
		return score.getScore();
	}

	public void setScore(int score) {
		this.score.setScore(score);
	}

	public int getLine() {
		return line.getLine();
	}

	public void setLine(int line) {
		this.line.setLine(line);
	}
}
