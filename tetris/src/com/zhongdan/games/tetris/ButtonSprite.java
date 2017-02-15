package com.zhongdan.games.tetris;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.zhongdan.games.framework.utils.ImageUtil;

public class ButtonSprite {

	private MyGameCanvas canvas;
	private Graphics graphics;
	private String buttonName;
	private Sprite sprite;

	public ButtonSprite(String buttonName, MyGameCanvas canvas, Graphics graphics, int x, int y) {
		this.buttonName = buttonName;
		this.canvas = canvas;
		this.graphics = graphics;
		this.sprite = new Sprite(ImageUtil.createImage("/buttons/" + buttonName + ".png"));
		this.sprite.setPosition(x, y);
		this.canvas.getLayerManager().insert(sprite, 0);
		this.canvas.getLayerManager().paint(this.graphics, 0, 0);
		this.canvas.flushGraphics();

	}

	public void toggleButton() {
		new Thread(new Runnable() {
			public void run() {
				LayerManager layerManager = canvas.getLayerManager();
				if (buttonName.endsWith("_down")) {
					buttonName = buttonName.substring(0, buttonName.indexOf("_down"));
				} else {
					buttonName += "_down";
				}
				int x = sprite.getX();
				int y = sprite.getY();
				layerManager.remove(sprite);
				sprite = new Sprite(ImageUtil.createImage("/buttons/" + buttonName + ".png"));
				sprite.setPosition(x, y);
				layerManager.insert(sprite, 0);
				layerManager.paint(graphics, 0, 0);
				canvas.flushGraphics();
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (buttonName.endsWith("_down")) {
					buttonName = buttonName.substring(0, buttonName.indexOf("_down"));
				} else {
					buttonName += "_down";
				}
				x = sprite.getX();
				y = sprite.getY();
				layerManager.remove(sprite);
				sprite = new Sprite(ImageUtil.createImage("/buttons/" + buttonName + ".png"));
				sprite.setPosition(x, y);
				layerManager.insert(sprite, 0);
				layerManager.paint(graphics, 0, 0);
				canvas.flushGraphics();
			}
		}).start();
	}

	public Sprite getSprite() {
		return sprite;
	}

}
