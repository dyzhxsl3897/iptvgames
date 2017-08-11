package com.zhongdan.games.tetris;

import java.util.Timer;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.zhongdan.games.framework.utils.Constants;
import com.zhongdan.games.framework.utils.ImageUtil;
import com.zhongdan.games.framework.utils.Constants.KeyCode;
import com.zhongdan.games.tetris.MyGameConstants.Menu;

public class MenuCanvas extends GameCanvas {
	private MainMidlet midlet;
	private Graphics graphics = this.getGraphics();
	private LayerManager layerManager = new LayerManager();
	private TiledLayer backgroundLayer;
	private Image menuBackgroundImg;
	private Image startGameBtnImg;
	private Image endGameBtnImg;
	private Image menuSelectedImg;
	private Sprite menuSelectedSprite;
	private Sprite topBtnSprite;
	private Sprite botBtnSprite;
	private Sprite startGameBtnSprite;
	private Sprite endGameBtnSprite;
	private int selectedItem = 1;

	protected MenuCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
		initCanvas();
	}

	private void initCanvas() {
		// Initialize background
		menuBackgroundImg = ImageUtil.createImage("/menu_background.png");
		backgroundLayer = new TiledLayer(1, 1, menuBackgroundImg, menuBackgroundImg.getWidth(), menuBackgroundImg.getHeight());
		backgroundLayer.setCell(0, 0, 1);
		layerManager.append(backgroundLayer);
		menuSelectedImg = ImageUtil.createImage("/menu_selected.png");
		menuSelectedSprite = new Sprite(menuSelectedImg, 30, 50);
		menuSelectedSprite.setFrame(0);
		menuSelectedSprite.setPosition(350, 290);
		new Timer().schedule(new MenuSelectedTimerTask(this, graphics, layerManager, menuSelectedSprite), 0, 200);

		// Initialize buttons
		startGameBtnImg = ImageUtil.createImage("/menu_btn_start.png");
		endGameBtnImg = ImageUtil.createImage("/menu_btn_quit.png");
		startGameBtnSprite = new Sprite(startGameBtnImg, 112, 61);
		endGameBtnSprite = new Sprite(endGameBtnImg, 112, 61);
		startGameBtnSprite.setFrame(0);
		endGameBtnSprite.setFrame(0);
		topBtnSprite = startGameBtnSprite;
		botBtnSprite = endGameBtnSprite;
		topBtnSprite.setPosition(Menu.BTN_TOP_X, Menu.BTN_TOP_Y);
		botBtnSprite.setPosition(Menu.BTN_BOT_X, Menu.BTN_BOT_Y);
		topBtnSprite.setFrame(1);
		layerManager.insert(startGameBtnSprite, 0);
		layerManager.insert(endGameBtnSprite, 0);

		layerManager.insert(menuSelectedSprite, 0);

		// Paint map
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	private void updateSelectedButton() {
		topBtnSprite.setFrame(0);
		botBtnSprite.setFrame(0);
		if (selectedItem == 1) {
			topBtnSprite.setFrame(1);
		} else if (selectedItem == 2) {
			botBtnSprite.setFrame(1);
		}
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	protected void keyPressed(int keyCode) {
		if (keyCode == Constants.KeyCode.UP) {
			if (selectedItem > 1) {
				selectedItem--;
				menuSelectedSprite.setPosition(350, 290);
				updateSelectedButton();
			}
		} else if (keyCode == Constants.KeyCode.DOWN) {
			if (selectedItem < 2) {
				selectedItem++;
				menuSelectedSprite.setPosition(350, 390);
				updateSelectedButton();
			}
		} else if (keyCode == Constants.KeyCode.OK) {
			if (selectedItem == 1) {
				this.midlet.getMyGameCanvas().initCanvas(1);
				this.midlet.getMyGameCanvas().startDropDown();
				this.midlet.getDisplay().setCurrent(this.midlet.getMyGameCanvas());
			} else if (selectedItem == 2) {
				this.midlet.notifyDestroyed();
			}
		} else if (keyCode == KeyCode.BACK || keyCode == KeyCode.BACK_1 || keyCode == KeyCode.BACK_2) {
			this.midlet.notifyDestroyed();
		}
	}
}
