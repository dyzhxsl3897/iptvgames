package com.zhongdan.games.tetris;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

import com.zhongdan.games.framework.utils.ImageUtil;

public class LineSprite {

	private int line;
	private Vector sprites;
	private MyGameCanvas canvas;
	private Graphics graphics;

	public LineSprite(int line, MyGameCanvas canvas, Graphics graphics) {
		super();
		this.line = line;
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
		int lineSplit = line;
		int digitNo = 0;
		if (line > 0) {
			while (lineSplit > 0) {
				Sprite s = new Sprite(ImageUtil.createImage("/number.png"), MyGameConstants.Line.WIDTH, MyGameConstants.Line.HEIGHT);
				s.setFrame(lineSplit % 10);
				sprites.addElement(s);
				lineSplit /= 10;
				digitNo++;
			}
		} else {
			digitNo++;
			Sprite s = new Sprite(ImageUtil.createImage("/number.png"), MyGameConstants.Line.WIDTH, MyGameConstants.Line.HEIGHT);
			s.setFrame(0);
			sprites.addElement(s);
		}
		for (int i = 0; i < digitNo; i++) {
			Sprite s = (Sprite) sprites.elementAt(i);
			int posX = MyGameConstants.Line.X - (digitNo / 2 * MyGameConstants.Line.WIDTH) + ((digitNo - i) * MyGameConstants.Line.WIDTH);
			int posY = MyGameConstants.Line.Y;
			s.setPosition(posX, posY);
		}
		for (int i = 0; i < sprites.size(); i++) {
			canvas.getLayerManager().insert((Sprite) sprites.elementAt(i), 0);
			canvas.getLayerManager().paint(graphics, 0, 0);
			canvas.flushGraphics();
		}
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
		createSprites();
	}

	public Vector getSprites() {
		return sprites;
	}

	public void setSprites(Vector sprites) {
		this.sprites = sprites;
	}

}
