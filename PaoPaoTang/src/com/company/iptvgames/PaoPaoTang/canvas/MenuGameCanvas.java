package com.company.iptvgames.PaoPaoTang.canvas;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.company.iptvgames.PaoPaoTang.GameConst;
import com.company.iptvgames.PaoPaoTang.MainMIDlet;
import com.company.iptvgames.framework.utils.Constants;
import com.company.iptvgames.framework.utils.ImageUtil;

public class MenuGameCanvas extends GameCanvas implements Runnable {

	private MainMIDlet midlet;
	private Graphics graphics;
	private LayerManager layerManager;

	private TiledLayer menuBg;

	private Image menuBgImg;
	private Image startImg;
	private Image exitImg;

	private Sprite startBtnSprite;
	private Sprite exitBtnSprite;
	private boolean isPlayMenuCartoon = false;

	private Thread menuThread;

	public MenuGameCanvas(MainMIDlet mainMIDlet) {
		super(false);
		this.setFullScreenMode(true);
		this.midlet = mainMIDlet;
		this.graphics = getGraphics();

		loadImages();
		initalizeMenu();
	}

	private void initalizeMenu() {
		layerManager = new LayerManager();
		menuBg = new TiledLayer(1, 1, menuBgImg, GameConst.SCREEN_WIDTH, GameConst.SCREEN_HEIGHT);
		menuBg.setCell(0, 0, 1);
		layerManager.append(menuBg);

		startBtnSprite = new Sprite(startImg, 141, 133);
		startBtnSprite.setPosition(GameConst.Menu.START_BTN_X, GameConst.Menu.START_BTN_Y);
		layerManager.insert(startBtnSprite, GameConst.Menu.LAYER_0);

		exitBtnSprite = new Sprite(exitImg, 43, 42);
		exitBtnSprite.setPosition(GameConst.Menu.EXIT_BTN_X, GameConst.Menu.EXIT_BTN_Y);
		exitBtnSprite.setVisible(false);
		layerManager.insert(exitBtnSprite, GameConst.Menu.LAYER_0);

		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();

	}

	public void startMenuCanvas() {
		isPlayMenuCartoon = true;

		menuThread = new Thread(this);
		menuThread.start();
	}

	private void loadImages() {
		menuBgImg = ImageUtil.createImage("/menu/menu.png");
		startImg = ImageUtil.createImage("/menu/start.png");
		exitImg = ImageUtil.createImage("/menu/exit.png");
	}

	protected void keyPressed(int keyCode) {
		keyAction(keyCode);
	}

	public void run() {
		while (isPlayMenuCartoon) {
			long startTime = System.currentTimeMillis();

			animateCartoon();

			layerManager.paint(graphics, 0, 0);
			this.flushGraphics();

			long runTime = System.currentTimeMillis() - startTime;
			if (runTime < GameConst.FPS) {
				try {
					Thread.sleep(GameConst.FPS - runTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void keyAction(int keyCode) {
		if (keyCode == Constants.KeyCode.LEFT || keyCode == Constants.KeyCode.DOWN) {
			startBtnSprite.setVisible(true);
			exitBtnSprite.setVisible(false);
		} else if (keyCode == Constants.KeyCode.RIGHT || keyCode == Constants.KeyCode.UP) {
			startBtnSprite.setVisible(false);
			exitBtnSprite.setVisible(true);
		} else if (keyCode == Constants.KeyCode.OK) {
			if (startBtnSprite.isVisible()) {
				turnOffMenuCanvas();
				this.midlet.getDisplay().setCurrent(this.midlet.getMainGameCanvas());
				this.midlet.getMainGameCanvas().initalizeGame();
				this.midlet.getMainGameCanvas().updateStateToPlay();
				this.midlet.getMainGameCanvas().startGameCanvas();
			} else {
				this.midlet.notifyDestroyed();
			}
		}
	}

	private void turnOffMenuCanvas() {
		this.isPlayMenuCartoon = false;
	}

	private void animateCartoon() {
		startBtnSprite.nextFrame();
	}
}
