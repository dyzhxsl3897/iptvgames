package com.zhongdan.games.wuziqi;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.zhongdan.games.framework.utils.Constants;
import com.zhongdan.games.framework.utils.ImageUtil;
import com.zhongdan.games.framework.utils.NumberImgUtil;
import com.zhongdan.games.framework.utils.Constants.KeyCode;
import com.zhongdan.games.wuziqi.GameConstants.GameSettings;

public class MainGameCanvas extends GameCanvas {

	private MainMIDlet midlet;
	private Graphics graphics;
	private LayerManager layerManager = new LayerManager();
	private Image bgImg;
	private Image lostImg;
	private Image winImg;
	private Image blackImg;
	private Image whiteImg;
	private Image blackWinImg;
	private Image whiteWinImg;
	private Image numbersImg;
	private IChessboard chessboard;
	private IPlayer humanPlayer;
	private IPlayer aiPlayer;
	private boolean isHumanPlaying;
	private boolean isPlaying;
	private Sprite lostSprite;
	private Sprite winSprite;
	private Sprite blackSprite;
	private Sprite whiteSprite;
	private Vector blacks;
	private Vector whites;
	private Point movePoint;
	private int step;
	private Vector stepNumberSprite;

	protected MainGameCanvas(MainMIDlet midlet) {
		super(false);
		this.midlet = midlet;
		graphics = this.getGraphics();
		this.setFullScreenMode(true);
		loadImages();
		initCanvas();
	}

	private void loadImages() {
		bgImg = ImageUtil.createImage("/bg.png");
		lostImg = ImageUtil.createImage("/lost.png");
		winImg = ImageUtil.createImage("/win.png");
		blackImg = ImageUtil.createImage("/black.png");
		whiteImg = ImageUtil.createImage("/white.png");
		blackWinImg = ImageUtil.createImage("/black_win.png");
		whiteWinImg = ImageUtil.createImage("/white_win.png");
		numbersImg = ImageUtil.createImage("/numbers.png");
	}

	public void initCanvas() {
		// Initialize layerManager
		for (int i = layerManager.getSize() - 1; i >= 0; i--) {
			layerManager.remove(layerManager.getLayerAt(i));
		}

		// Initialize background
		TiledLayer backgroundLayer = new TiledLayer(1, 1, bgImg, bgImg.getWidth(), bgImg.getHeight());
		backgroundLayer.setCell(0, 0, 1);
		layerManager.insert(backgroundLayer, 0);

		// Initialize lost and win
		winSprite = new Sprite(winImg, 338, 202);
		lostSprite = new Sprite(lostImg, 338, 202);
		winSprite.setPosition(118, 144);
		lostSprite.setPosition(118, 144);

		// Start game
		step = 0;
		updateStep();
		chessboard = new Chessboard();
		humanPlayer = new HumanPlayer();
		aiPlayer = new BaseComputerAi();
		humanPlayer.setChessboard(chessboard);
		aiPlayer.setChessboard(chessboard);
		isHumanPlaying = true;
		isPlaying = true;
		blacks = new Vector();
		whites = new Vector();
		movePoint = null;
		blackSprite = new Sprite(blackWinImg, GameSettings.CELL_W, GameSettings.CELL_H);
		blackSprite.setPosition(GameSettings.CELL_START_X + GameSettings.CELL_W * (GameSettings.ROW_NO / 2), GameSettings.CELL_START_Y
				+ GameSettings.CELL_H * (GameSettings.COL_NO / 2));
		layerManager.insert(blackSprite, 0);

		// Paint all
		layerManager.setViewWindow(0, 0, this.getWidth(), this.getHeight());
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	protected void keyPressed(int keyCode) {
		if (isPlaying) {
			if (isHumanPlaying) {
				if (keyCode == Constants.KeyCode.LEFT) {
					blackSprite.move(-GameSettings.CELL_W, 0);
				} else if (keyCode == Constants.KeyCode.RIGHT) {
					blackSprite.move(GameSettings.CELL_W, 0);
				} else if (keyCode == Constants.KeyCode.UP) {
					blackSprite.move(0, -GameSettings.CELL_H);
				} else if (keyCode == Constants.KeyCode.DOWN) {
					blackSprite.move(0, GameSettings.CELL_H);
				} else if (keyCode == Constants.KeyCode.OK) {
					int x = (blackSprite.getX() - GameSettings.CELL_START_X) / GameSettings.CELL_W;
					int y = (blackSprite.getY() - GameSettings.CELL_START_Y) / GameSettings.CELL_H;
					movePoint = new Point(x, y);
					if (chessboard.getFreePoints().contains(movePoint)) {
						step++;
						updateStep();
						blackSprite.setImage(blackImg, GameSettings.CELL_W, GameSettings.CELL_H);
						blacks.addElement(blackSprite);
						layerManager.insert(blackSprite, 0);
						layerManager.paint(graphics, 0, 0);
						this.flushGraphics();
						humanPlayer.run(null, movePoint);
						isHumanPlaying = false;

						// If human wins
						if (humanPlayer.hasWin()) {
							layerManager.insert(winSprite, 0);
							isPlaying = false;
						} else {
							Point result = aiPlayer.run(humanPlayer.getMyPoints(), null);
							if (whiteSprite != null) {
								whiteSprite.setImage(whiteImg, GameSettings.CELL_W, GameSettings.CELL_H);
							}
							whiteSprite = new Sprite(whiteWinImg, GameSettings.CELL_W, GameSettings.CELL_H);
							whiteSprite.setPosition(GameSettings.CELL_START_X + GameSettings.CELL_W * result.getX(), GameSettings.CELL_START_Y
									+ GameSettings.CELL_H * result.getY());
							whites.addElement(whiteSprite);
							layerManager.insert(whiteSprite, 0);
							layerManager.paint(graphics, 0, 0);
							this.flushGraphics();
							if (aiPlayer.hasWin()) {
								layerManager.insert(lostSprite, 0);
								isPlaying = false;
							} else {
								blackSprite = new Sprite(blackWinImg, GameSettings.CELL_W, GameSettings.CELL_H);
								blackSprite.setPosition(GameSettings.CELL_START_X + GameSettings.CELL_W * (GameSettings.ROW_NO / 2),
										GameSettings.CELL_START_Y + GameSettings.CELL_H * (GameSettings.COL_NO / 2));
								layerManager.insert(blackSprite, 0);
								isHumanPlaying = true;
							}
						}
					}
				} else if (keyCode == Constants.KeyCode.NUM_0) {
					initCanvas();
				} else if (keyCode == Constants.KeyCode.BACK || keyCode == KeyCode.BACK_1 || keyCode == KeyCode.BACK_2) {
					this.midlet.getDisplay().setCurrent(this.midlet.getMenuCanvas());
				}
				layerManager.paint(graphics, 0, 0);
				this.flushGraphics();
			}
		} else {
			if (keyCode == Constants.KeyCode.OK) {
				this.midlet.getDisplay().setCurrent(this.midlet.getMenuCanvas());
			}
		}
	}

	private void updateStep() {
		if (null != stepNumberSprite && 0 < stepNumberSprite.size()) {
			for (int i = 0; i < stepNumberSprite.size(); i++) {
				layerManager.remove((Sprite) stepNumberSprite.elementAt(i));
			}
		}
		stepNumberSprite = NumberImgUtil.updateNumber(step, numbersImg, GameConstants.GameSettings.STEP_NUMBER_X,
				GameConstants.GameSettings.STEP_NUMBER_Y, Graphics.TOP | Graphics.HCENTER);
		if (null != stepNumberSprite && 0 < stepNumberSprite.size()) {
			for (int i = 0; i < stepNumberSprite.size(); i++) {
				layerManager.insert((Sprite) stepNumberSprite.elementAt(i), 0);
			}
		}
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

}
