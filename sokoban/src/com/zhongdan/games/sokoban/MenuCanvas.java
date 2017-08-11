package com.zhongdan.games.sokoban;

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
import com.zhongdan.games.sokoban.GameConstants.Menu;

public class MenuCanvas extends GameCanvas {

	private MyMIDlet midlet;
	private Graphics graphics = this.getGraphics();
	private LayerManager layerManager = new LayerManager();
	private TiledLayer backgroundLayer;
	private Image backgroundImg;
	private Image startGameBtnImg;
	private Image endGameBtnImg;
	// private Image levelSelectBtnImg;
	// private Image rankingBtnImg;
	// private Image helpBtnImg;
	private Image menuSelectedImg;
	private Sprite menuSelectedSprite;
	private Sprite topBtnSprite;
	// private Sprite midBtnSprite;
	private Sprite botBtnSprite;
	private Sprite startGameBtnSprite;
	private Sprite endGameBtnSprite;
	// private Sprite levelSelectBtnSprite;
	// private Sprite rankingBtnSprite;
	// private Sprite helpBtnSprite;
	private int selectedItem = 1;

	protected MenuCanvas(MyMIDlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
		initCanvas();
	}

	private void initCanvas() {
		// Initialize background
		backgroundImg = ImageUtil.createImage("/menu_background.png");
		backgroundLayer = new TiledLayer(1, 1, backgroundImg, backgroundImg.getWidth(), backgroundImg.getHeight());
		backgroundLayer.setCell(0, 0, 1);
		layerManager.append(backgroundLayer);
		menuSelectedImg = ImageUtil.createImage("/menu_selected.png");
		menuSelectedSprite = new Sprite(menuSelectedImg, 30, 50);
		menuSelectedSprite.setFrame(0);
		menuSelectedSprite.setPosition(400, 240);
		new Timer().schedule(new MenuSelectedTimerTask(this, graphics, layerManager, menuSelectedSprite), 0, 200);

		// Initialize buttons
		startGameBtnImg = ImageUtil.createImage("/menu_start_game.png");
		endGameBtnImg = ImageUtil.createImage("/menu_end_game.png");
		// levelSelectBtnImg = ImageUtil.createImage("/menu_level_select.png");
		// rankingBtnImg = ImageUtil.createImage("/menu_ranking.png");
		// helpBtnImg = ImageUtil.createImage("/menu_help.png");
		startGameBtnSprite = new Sprite(startGameBtnImg, 200, 55);
		endGameBtnSprite = new Sprite(endGameBtnImg, 200, 55);
		// levelSelectBtnSprite = new Sprite(levelSelectBtnImg, 200, 55);
		// rankingBtnSprite = new Sprite(rankingBtnImg, 200, 55);
		// helpBtnSprite = new Sprite(helpBtnImg, 200, 55);
		startGameBtnSprite.setFrame(0);
		endGameBtnSprite.setFrame(0);
		// levelSelectBtnSprite.setFrame(0);
		// rankingBtnSprite.setFrame(0);
		// helpBtnSprite.setFrame(0);
		topBtnSprite = startGameBtnSprite;
		// midBtnSprite = levelSelectBtnSprite;
		botBtnSprite = endGameBtnSprite;
		// rankingBtnSprite.setVisible(false);
		// helpBtnSprite.setVisible(false);
		topBtnSprite.setPosition(Menu.BTN_TOP_X, Menu.BTN_TOP_Y);
		// midBtnSprite.setPosition(Menu.BTN_MID_X, Menu.BTN_MID_Y);
		botBtnSprite.setPosition(Menu.BTN_BOT_X, Menu.BTN_BOT_Y);
		topBtnSprite.setFrame(1);
		layerManager.insert(startGameBtnSprite, 0);
		layerManager.insert(endGameBtnSprite, 0);
		// layerManager.insert(levelSelectBtnSprite, 0);
		// layerManager.insert(rankingBtnSprite, 0);
		// layerManager.insert(helpBtnSprite, 0);

		layerManager.insert(menuSelectedSprite, 0);

		// Paint map
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	private void updateSelectedButton() {
		topBtnSprite.setFrame(0);
		// midBtnSprite.setFrame(0);
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
				menuSelectedSprite.setPosition(400, 240);
				updateSelectedButton();
			}
		} else if (keyCode == Constants.KeyCode.DOWN) {
			if (selectedItem < 2) {
				selectedItem++;
				menuSelectedSprite.setPosition(400, 320);
				updateSelectedButton();
			}
		} else if (keyCode == Constants.KeyCode.OK) {
			if (selectedItem == 1) {
				this.midlet.getDisplay().setCurrent(this.midlet.getMyGameCanvas());
			} else if (selectedItem == 2) {
				this.midlet.notifyDestroyed();
			}
		} else if (keyCode == KeyCode.BACK || keyCode == KeyCode.BACK_1) {
			this.midlet.notifyDestroyed();
		}
	}

}
