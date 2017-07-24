package com.zhongdan.games.goldminer;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.zhongdan.games.framework.utils.Constants;
import com.zhongdan.games.framework.utils.ImageUtil;
import com.zhongdan.games.goldminer.GameConstants.Menu;

public class MenuCanvas extends GameCanvas {

	private MainMidlet midlet;
	private Graphics graphics = this.getGraphics();
	private LayerManager layerManager = new LayerManager();
	private TiledLayer backgroundLayer;
	private Image menuBackgroundImg;
	private Image startGameBtnImg;
	private Image endGameBtnImg;
	private Image levelSelectBtnImg;
	private Image rankingBtnImg;
	private Image helpBtnImg;
	private Sprite topBtnSprite;
	private Sprite midBtnSprite;
	private Sprite botBtnSprite;
	private Sprite startGameBtnSprite;
	private Sprite endGameBtnSprite;
	private Sprite levelSelectBtnSprite;
	private Sprite rankingBtnSprite;
	private Sprite helpBtnSprite;
	private int selectedItem = 1;

	protected MenuCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
		initCanvas();
	}

	private void initCanvas() {
		// Initialize background
		menuBackgroundImg = ImageUtil.createImage("/menu-background.png");
		backgroundLayer = new TiledLayer(1, 1, menuBackgroundImg, menuBackgroundImg.getWidth(), menuBackgroundImg.getHeight());
		backgroundLayer.setCell(0, 0, 1);
		layerManager.append(backgroundLayer);

		// Initialize buttons
		startGameBtnImg = ImageUtil.createImage("/menu_btn_start_game.png");
		endGameBtnImg = ImageUtil.createImage("/menu_btn_end_game.png");
		levelSelectBtnImg = ImageUtil.createImage("/menu_btn_level_select.png");
		rankingBtnImg = ImageUtil.createImage("/menu_btn_ranking.png");
		helpBtnImg = ImageUtil.createImage("/menu_btn_help.png");
		startGameBtnSprite = new Sprite(startGameBtnImg, 200, 55);
		endGameBtnSprite = new Sprite(endGameBtnImg, 200, 55);
		levelSelectBtnSprite = new Sprite(levelSelectBtnImg, 200, 55);
		rankingBtnSprite = new Sprite(rankingBtnImg, 200, 55);
		helpBtnSprite = new Sprite(helpBtnImg, 200, 55);
		startGameBtnSprite.setFrame(0);
		endGameBtnSprite.setFrame(0);
		levelSelectBtnSprite.setFrame(0);
		rankingBtnSprite.setFrame(0);
		helpBtnSprite.setFrame(0);
		topBtnSprite = startGameBtnSprite;
		midBtnSprite = levelSelectBtnSprite;
		botBtnSprite = endGameBtnSprite;
		levelSelectBtnSprite.setVisible(false);
		rankingBtnSprite.setVisible(false);
		helpBtnSprite.setVisible(false);
		topBtnSprite.setPosition(Menu.BTN_TOP_X, Menu.BTN_TOP_Y);
		midBtnSprite.setPosition(Menu.BTN_MID_X, Menu.BTN_MID_Y);
		botBtnSprite.setPosition(Menu.BTN_BOT_X, Menu.BTN_BOT_Y);
		topBtnSprite.setFrame(1);
		layerManager.insert(startGameBtnSprite, 0);
		layerManager.insert(endGameBtnSprite, 0);
		layerManager.insert(levelSelectBtnSprite, 0);
		layerManager.insert(rankingBtnSprite, 0);
		layerManager.insert(helpBtnSprite, 0);

		// Paint map
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	private void updateSelectedButton() {
		topBtnSprite.setFrame(0);
		midBtnSprite.setFrame(0);
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
				updateSelectedButton();
			}
		} else if (keyCode == Constants.KeyCode.DOWN) {
			if (selectedItem < 2) {
				selectedItem++;
				updateSelectedButton();
			}
		} else if (keyCode == Constants.KeyCode.OK) {
			if (selectedItem == 1) {
				MainGameCanvas mainGameCanvas = new MainGameCanvas(this.midlet);
				this.midlet.setMainGameCanvas(mainGameCanvas);
				this.midlet.getDisplay().setCurrent(this.midlet.getMainGameCanvas());
			} else if (selectedItem == 2) {
				this.midlet.notifyDestroyed();
			}
		}
	}

}
