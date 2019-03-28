package com.company.iptvgames.peiqidown.canvas;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.company.iptvgames.framework.utils.ImageUtil;
import com.company.iptvgames.peiqidown.GameConst;
import com.company.iptvgames.peiqidown.MainMIDlet;

public class MenuGameCanvas extends GameCanvas implements Runnable {

	private MainMIDlet midlet;
	private Graphics graphics;

	private Image menuImg;
	private Image startImg;
	private Image exitImg;
	private Image cartoonImg;

	private LayerManager layerManager = new LayerManager();
	private TiledLayer backgroundLayer;
	private Sprite cartoonSprite;
	private Sprite startBtnSprite;
	private Sprite exitBtnSprite;

	private Thread menuThread;

	private boolean isPlayMenuCartoon = false;

	public MenuGameCanvas(MainMIDlet midlet) {
		super(false);
		this.setFullScreenMode(true);
		this.graphics = getGraphics();
		this.midlet = midlet;

		loadImages();
		initalizeMenu();
	}

	private void initalizeMenu() {
		backgroundLayer = new TiledLayer(1, 1, menuImg, GameConst.SCREEN_WIDTH, GameConst.SCREEN_HEIGHT);
		backgroundLayer.setCell(0, 0, 1);
		layerManager.append(backgroundLayer);

		cartoonSprite = new Sprite(cartoonImg, GameConst.Menu.CARTOON_W, GameConst.Menu.CARTOON_H);
		cartoonSprite.setPosition(GameConst.Menu.CARTOON_X, GameConst.Menu.CARTOON_Y);
		layerManager.insert(cartoonSprite, 0);

		startBtnSprite = new Sprite(startImg);
		startBtnSprite.setPosition(GameConst.Menu.START_BTN_X, GameConst.Menu.START_BTN_Y);
		layerManager.insert(startBtnSprite, 0);

		exitBtnSprite = new Sprite(exitImg);
		exitBtnSprite.setPosition(GameConst.Menu.EXIT_BTN_X, GameConst.Menu.EXIT_BTN_Y);
		exitBtnSprite.setVisible(false);
		layerManager.insert(exitBtnSprite, 0);
	}

	public void startMenuCanvas() {
		isPlayMenuCartoon = true;

		menuThread = new Thread(this);
		menuThread.start();
	}

	private void loadImages() {
		menuImg = ImageUtil.createImage("/menu/menu.png");
		cartoonImg = ImageUtil.createImage("/menu/cartoon.png");
		startImg = ImageUtil.createImage("/menu/start.png");
		exitImg = ImageUtil.createImage("/menu/exit.png");
	}

	public void run() {
		while (isPlayMenuCartoon) {
			long startTime = System.currentTimeMillis();

			animateCartoon();
			keyAction();

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

	private void keyAction() {
		if (0 != (getKeyStates() & GameCanvas.LEFT_PRESSED) || 0 != (getKeyStates() & GameCanvas.DOWN_PRESSED)) {
			startBtnSprite.setVisible(true);
			exitBtnSprite.setVisible(false);
		} else if (0 != (getKeyStates() & GameCanvas.RIGHT_PRESSED) || 0 != (getKeyStates() & GameCanvas.UP_PRESSED)) {
			startBtnSprite.setVisible(false);
			exitBtnSprite.setVisible(true);
		} else if (0 != (getKeyStates() & GameCanvas.FIRE_PRESSED)) {
			if (startBtnSprite.isVisible()) {
				turnOffMenuCanvas();
				this.midlet.getDisplay().setCurrent(this.midlet.getMainGameCanvas());
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
		cartoonSprite.nextFrame();
	}
}
