package com.zhongdan.games.tetris;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

import com.zhongdan.games.framework.utils.ImageUtil;

public class LevelSprite {

	private int level;
	private Vector sprites;
	private MyGameCanvas canvas;
	private Graphics graphics;

	public LevelSprite(int level, MyGameCanvas canvas, Graphics graphics) {
		super();
		this.level = level;
		this.canvas = canvas;
		this.graphics = graphics;
		createSprites();
	}

	private void createSprites() {
		if (sprites != null && sprites.size() > 0) {
			for (int i = 0; i < sprites.size(); i++) {
				canvas.getLayerManager().remove(((Sprite) sprites.elementAt(i)));
			}
			sprites = new Vector();
		} else {
			sprites = new Vector();
		}
		int levelSplit = level;
		int digitNo = 0;
		if (level > 0) {
			while (levelSplit > 0) {
				Sprite s = new Sprite(ImageUtil.createImage("/number.png"), MyGameConstants.Level.WIDTH, MyGameConstants.Level.HEIGHT);
				s.setFrame(levelSplit % 10);
				sprites.addElement(s);
				levelSplit /= 10;
				digitNo++;
			}
		} else {
			digitNo++;
			Sprite s = new Sprite(ImageUtil.createImage("/number.png"), MyGameConstants.Level.WIDTH, MyGameConstants.Level.HEIGHT);
			s.setFrame(0);
			sprites.addElement(s);
		}
		for (int i = 0; i < digitNo; i++) {
			Sprite s = (Sprite) sprites.elementAt(i);
			int posX = MyGameConstants.Level.X - (digitNo / 2 * MyGameConstants.Level.WIDTH) + ((digitNo - i) * MyGameConstants.Level.WIDTH);
			int posY = MyGameConstants.Level.Y;
			s.setPosition(posX, posY);
		}
		for (int i = 0; i < sprites.size(); i++) {
			canvas.getLayerManager().insert((Sprite) sprites.elementAt(i), 0);
			canvas.getLayerManager().paint(graphics, 0, 0);
			canvas.flushGraphics();
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		createSprites();
	}

	public Vector getSprites() {
		return sprites;
	}

	public void setSprites(Vector sprites) {
		this.sprites = sprites;
	}

}
