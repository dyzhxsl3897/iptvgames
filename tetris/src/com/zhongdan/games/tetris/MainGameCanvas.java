package com.zhongdan.games.tetris;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.zhongdan.games.framework.utils.Constants;

public class MainGameCanvas extends GameCanvas {

	private Graphics graphics;
	private LayerManager layerManager = new LayerManager();
	private Image backgroundImg;
	private int[][] map = new int[MyGameConstants.Playboard.WIDTH][MyGameConstants.Playboard.HEIGHT];
	private Sprite[][] allBrickSprite = new Sprite[MyGameConstants.Playboard.WIDTH][MyGameConstants.Playboard.HEIGHT];
	private MovingBrick movingBrick;
	private boolean isPlaying = true;

	public MainGameCanvas(boolean suppressKeyEvents) {
		super(suppressKeyEvents);
		graphics = this.getGraphics();
		this.setFullScreenMode(true);
		initCanvas();
	}

	protected void keyPressed(int keyCode) {
		if (keyCode == Constants.KeyCode.DOWN) {
			if (isPlaying) {
				movingBrick.moveDown();
				layerManager.paint(graphics, 0, 0);
				this.flushGraphics();
			}
		} else if (keyCode == Constants.KeyCode.LEFT) {
			if (isPlaying) {
				movingBrick.moveLeft();
				layerManager.paint(graphics, 0, 0);
				this.flushGraphics();
			}
		} else if (keyCode == Constants.KeyCode.RIGHT) {
			if (isPlaying) {
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

		// Initialize moving brick
		movingBrick = new MovingBrick(0, 0, this);

		// Paint all
		layerManager.setViewWindow(0, 0, this.getWidth(), this.getHeight());
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	public LayerManager getLayerManager() {
		return layerManager;
	}

	public int[][] getMap() {
		return map;
	}

}
