package com.yunyouhudong.games.wuziqi.menu;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.yunyouhudong.framework.constants.KeyCode;
import com.yunyouhudong.framework.game.YunyouGameCanvas;
import com.yunyouhudong.framework.game.YunyouMIDlet;
import com.yunyouhudong.games.wuziqi.GameConsts;

public class MenuCanvas extends YunyouGameCanvas implements Runnable {

	private Sprite bg;
	private Sprite start;
	private Sprite end;
	private Sprite cursor;
	private LayerManager layerManager = new LayerManager();

	private Thread thread;
	private boolean isRunning;

	public MenuCanvas(YunyouMIDlet mid, boolean suppressKeyEvents) {
		super(mid, suppressKeyEvents);
	}

	public void exitCanvas() {
	}

	public void intoCanvas() {
		loadImages();
		initialize();
		startMenu();
	}

	public void run() {
		while (this.isRunning) {
			long startTime = System.currentTimeMillis();

			cursor.nextFrame();
			layerManager.paint(this.getGraphics(), 0, 0);
			this.flushGraphics();

			long runTime = System.currentTimeMillis() - startTime;
			if (runTime < GameConsts.fps) {
				try {
					Thread.sleep(GameConsts.fps - runTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void keyPressed(int keyCode) {
		if (KeyCode.UP.contains(new Integer(keyCode))) {
			cursor.setPosition(GameConsts.Menu.cursorUpX, GameConsts.Menu.cursorUpY);
		} else if (KeyCode.DOWN.contains(new Integer(keyCode))) {
			cursor.setPosition(GameConsts.Menu.cursorDownX, GameConsts.Menu.cursorDownY);
		} else if (KeyCode.OK.contains(new Integer(keyCode))) {
			if (isStartSelected()) {
			} else if (isEndSelected()) {
				this.getMidlet().notifyDestroyed();
			}
		}
	}

	private boolean isStartSelected() {
		return cursor.getY() == GameConsts.Menu.cursorUpY;
	}

	private boolean isEndSelected() {
		return cursor.getY() == GameConsts.Menu.cursorDownY;
	}

	private void startMenu() {
		thread = new Thread(this);
		this.isRunning = true;
		thread.start();
	}

	private void loadImages() {
		Image img = this.getResourceManager().loadImageFromLocal("menu_background.png");
		bg = new Sprite(img);
		img = this.getResourceManager().loadImageFromLocal("menu_start_game.png");
		start = new Sprite(img);
		img = this.getResourceManager().loadImageFromLocal("menu_end_game.png");
		end = new Sprite(img);
		img = this.getResourceManager().loadImageFromLocal("menu_selected.png");
		cursor = new Sprite(img, GameConsts.Menu.cursorW, GameConsts.Menu.cursorH);
	}

	private void initialize() {
		start.setPosition(GameConsts.Menu.startX, GameConsts.Menu.startY);
		end.setPosition(GameConsts.Menu.endX, GameConsts.Menu.endY);
		cursor.setPosition(GameConsts.Menu.cursorUpX, GameConsts.Menu.cursorUpY);

		layerManager.append(cursor);
		layerManager.append(start);
		layerManager.append(end);
		layerManager.append(bg);

		layerManager.paint(getGraphics(), 0, 0);
		this.flushGraphics();
	}

}
