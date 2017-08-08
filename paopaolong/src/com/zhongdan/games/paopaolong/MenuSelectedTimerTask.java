package com.zhongdan.games.paopaolong;

import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

public class MenuSelectedTimerTask extends TimerTask {
	private MenuCanvas menuCanvas;
	private Graphics graphics;
	private LayerManager layerManager;
	private Sprite menuSelectedSprite;

	public MenuSelectedTimerTask(MenuCanvas menuCanvas, Graphics graphics, LayerManager layerManager, Sprite menuSelectedSprite) {
		this.menuCanvas = menuCanvas;
		this.layerManager = layerManager;
		this.graphics = graphics;
		this.menuSelectedSprite = menuSelectedSprite;
	}

	public void run() {
		int frameIndex = menuSelectedSprite.getFrame();
		frameIndex++;
		frameIndex %= 3;
		menuSelectedSprite.setFrame(frameIndex);
		layerManager.paint(graphics, 0, 0);
		menuCanvas.flushGraphics();
	}

}
