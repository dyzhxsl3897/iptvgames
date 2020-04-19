package com.company.iptvgames.RunCool.canvas;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.company.iptvgames.RunCool.MainMIDlet;
import com.company.iptvgames.RunCool.GameConst;
import com.company.iptvgames.framework.utils.ImageUtil;

public class MenuGameCanvas extends GameCanvas implements Runnable {

	private MainMIDlet midlet;
	private Graphics graphics;
	private Image menuBgImg;
	private Image startImg;
	private Image exitImg;
	private LayerManager layerManager;
	private TiledLayer menuBg;
	private Sprite startBtnSprite;
	private Sprite exitBtnSprite;
	private boolean isPlayMenuCartoon;
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
		// TODO Auto-generated method stub
		layerManager = new LayerManager();
		menuBg = new TiledLayer(1, 1, menuBgImg, GameConst.SCREEN_WIDTH, GameConst.SCREEN_HEIGHT);
		menuBg.setCell(0, 0, 1);
		layerManager.append(menuBg);
		
		startBtnSprite = new Sprite(startImg,GameConst.Menu.START_BTN_WIDTH/GameConst.Menu.START_BTN_FRAME, GameConst.Menu.START_BTN_HEIGHT);
		startBtnSprite.setPosition(GameConst.Menu.START_BTN_X, GameConst.Menu.START_BTN_Y);
		layerManager.insert(startBtnSprite, 0);
		
		exitBtnSprite = new Sprite(exitImg,43,42);
		exitBtnSprite.setPosition(GameConst.Menu.EXIT_BTN_X, GameConst.Menu.EXIT_BTN_Y);		
		exitBtnSprite.setVisible(false);
		layerManager.insert(exitBtnSprite, 0);
		
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();

	}

	private void loadImages() {
		// TODO Auto-generated method stub
		menuBgImg = ImageUtil.createImage("/menu/menu.png");
		startImg = ImageUtil.createImage("/menu/start.png");
		exitImg = ImageUtil.createImage("/menu/exit.png");
	}

	public void startMenuCanvas() {
		// TODO Auto-generated method stub
		isPlayMenuCartoon = true;

		menuThread = new Thread(this);
		menuThread.start();
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
