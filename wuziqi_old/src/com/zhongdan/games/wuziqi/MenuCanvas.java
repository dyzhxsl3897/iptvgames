package com.zhongdan.games.wuziqi;

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
import com.zhongdan.games.wuziqi.GameConstants.Menu;

public class MenuCanvas extends GameCanvas {

	private MainMIDlet midlet;
	private Graphics graphics = this.getGraphics();
	private LayerManager layerManager = new LayerManager();
	private TiledLayer backgroundLayer;
	private Image backgroundImg;
	private Image startGameBtnImg;
	private Image endGameBtnImg;
	private Image menuSelectedImg;
	private Sprite menuSelectedSprite;
	private Sprite topBtnSprite;
	private Sprite botBtnSprite;
	private Sprite startGameBtnSprite;
	private Sprite endGameBtnSprite;
	private int selectedItem = 1;

	protected MenuCanvas(MainMIDlet midlet) {
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
		menuSelectedSprite.setPosition(370, 240);
		new Timer().schedule(new MenuSelectedTimerTask(this, graphics, layerManager, menuSelectedSprite), 0, 200);

		// Initialize buttons
		startGameBtnImg = ImageUtil.createImage("/menu_start_game.png");
		endGameBtnImg = ImageUtil.createImage("/menu_end_game.png");
		startGameBtnSprite = new Sprite(startGameBtnImg, 136, 75);
		endGameBtnSprite = new Sprite(endGameBtnImg, 136, 75);
		startGameBtnSprite.setFrame(0);
		endGameBtnSprite.setFrame(0);
		topBtnSprite = startGameBtnSprite;
		botBtnSprite = endGameBtnSprite;
		topBtnSprite.setPosition(Menu.BTN_TOP_X, Menu.BTN_TOP_Y);
		botBtnSprite.setPosition(Menu.BTN_BOT_X, Menu.BTN_BOT_Y);
		topBtnSprite.setFrame(0);
		layerManager.insert(startGameBtnSprite, 0);
		layerManager.insert(endGameBtnSprite, 0);
		layerManager.insert(menuSelectedSprite, 0);

		// Paint map
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	protected void keyPressed(int keyCode) {
		if (keyCode == Constants.KeyCode.UP) {
			if (selectedItem > 1) {
				selectedItem--;
				menuSelectedSprite.setPosition(370, 240);
			}
		} else if (keyCode == Constants.KeyCode.DOWN) {
			if (selectedItem < 2) {
				selectedItem++;
				menuSelectedSprite.setPosition(370, 320);
			}
		} else if (keyCode == Constants.KeyCode.OK) {
			if (selectedItem == 1) {
				this.midlet.getMainGameCanvas().initCanvas();
				this.midlet.getDisplay().setCurrent(this.midlet.getMainGameCanvas());
			} else if (selectedItem == 2) {
				this.midlet.notifyDestroyed();
			}
		} else if (keyCode == KeyCode.BACK || keyCode == KeyCode.BACK_1 || keyCode == KeyCode.BACK_2) {
			this.midlet.notifyDestroyed();
		}
	}

}
