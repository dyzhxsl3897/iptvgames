package com.company.iptvgames.poker.canvas;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.company.iptvgames.framework.utils.ImageUtil;
import com.company.iptvgames.poker.GameConst;
import com.company.iptvgames.poker.MainMIDlet;

public class MenuGameCanvas extends GameCanvas implements Runnable {
	
	private MainMIDlet midlet;
	private Graphics graphics;
	private Image menuBgImg;
	private Image startImg;
	private LayerManager layerManager;
	private TiledLayer menuBg;
	private Sprite startBtnSprite;
	private Thread menuThread;
	private boolean isPlayMenu = false;

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
		
		startBtnSprite = new Sprite(startImg);
		startBtnSprite.setPosition(GameConst.Menu.START_BTN_X, GameConst.Menu.START_BTN_Y);
		layerManager.insert(startBtnSprite, 0);
		
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();

	}

	private void loadImages() {
		// TODO Auto-generated method stub
		menuBgImg = ImageUtil.createImage("/menu/menu.png");
		startImg = ImageUtil.createImage("/menu/start.png");
	}

	public void startMenuCanvas() {
		// TODO Auto-generated method stub
		isPlayMenu = true;
		
		menuThread = new Thread(this);
		menuThread.start();
	}

	public void run() {
		while (isPlayMenu){
			keyAction();
		}
	}

	private void keyAction() {
		if (0 != (getKeyStates() & GameCanvas.FIRE_PRESSED)) {
			turnOffMenuCanvas();
			this.midlet.getDisplay().setCurrent(this.midlet.getMainGameCanvas());
			this.midlet.getMainGameCanvas().initalizeGame();
			this.midlet.getMainGameCanvas().updateStateToPlay();
			this.midlet.getMainGameCanvas().startGameCanvas();
		}
	}

	private void turnOffMenuCanvas() {
		this.isPlayMenu = false;
	}
}
