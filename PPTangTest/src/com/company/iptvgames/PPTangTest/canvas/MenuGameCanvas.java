package com.company.iptvgames.PPTangTest.canvas;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.company.iptvgames.framework.utils.ImageUtil;
import com.company.iptvgames.PPTangTest.GameConst;
import com.company.iptvgames.PPTangTest.MainMIDlet;

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
		
		startBtnSprite = new Sprite(startImg,141, 133);
		startBtnSprite.setPosition(GameConst.Menu.START_BTN_X, GameConst.Menu.START_BTN_Y);
		layerManager.insert(startBtnSprite, GameConst.Menu.LAYER_0);
		
		exitBtnSprite = new Sprite(exitImg,43,42);
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
