package com.zhongdan.games.tetris;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

import com.zhongdan.games.framework.utils.ImageUtil;

public class ScoreSprite {

	private int score;
	private Vector sprites;
	private MyGameCanvas canvas;
	private Graphics graphics;

	public ScoreSprite(int score, MyGameCanvas canvas, Graphics graphics) {
		super();
		this.score = score;
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
		int scoreSplit = score;
		int digitNo = 0;
		if (score > 0) {
			while (scoreSplit > 0) {
				Sprite s = new Sprite(ImageUtil.createImage("/number.png"), MyGameConstants.Score.WIDTH, MyGameConstants.Score.HEIGHT);
				s.setFrame(scoreSplit % 10);
				sprites.addElement(s);
				scoreSplit /= 10;
				digitNo++;
			}
		} else {
			digitNo++;
			Sprite s = new Sprite(ImageUtil.createImage("/number.png"), MyGameConstants.Score.WIDTH, MyGameConstants.Score.HEIGHT);
			s.setFrame(0);
			sprites.addElement(s);
		}
		for (int i = 0; i < digitNo; i++) {
			Sprite s = (Sprite) sprites.elementAt(i);
			int posX = MyGameConstants.Score.X - (digitNo / 2 * MyGameConstants.Score.WIDTH) + ((digitNo - i) * MyGameConstants.Score.WIDTH);
			int posY = MyGameConstants.Score.Y;
			s.setPosition(posX, posY);
		}
		for (int i = 0; i < sprites.size(); i++) {
			canvas.getLayerManager().insert((Sprite) sprites.elementAt(i), 0);
			canvas.getLayerManager().paint(graphics, 0, 0);
			canvas.flushGraphics();
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		createSprites();
	}

	public Vector getSprites() {
		return sprites;
	}

	public void setSprites(Vector sprites) {
		this.sprites = sprites;
	}

}
