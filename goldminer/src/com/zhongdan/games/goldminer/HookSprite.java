package com.zhongdan.games.goldminer;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class HookSprite extends Sprite {

	public final static int CIRCUMGYRATEING = 11;
	public final static int STRETCHING = 12;
	public final static int BACKING = 13;
	public final static int CATCHING = 14;
	public int hookSpriteState = CIRCUMGYRATEING;
	private int nowSpeed;
	private int nowValue;
	private int direction;
	private boolean isRight;
	private int lineEndX;
	private int lineEndY;
	private int linelength;
	private int catchOre;
	public static final double[] SIN = { Math.sin(75 * Math.PI / 180), Math.sin(60 * Math.PI / 180), Math.sin(45 * Math.PI / 180),
			Math.sin(30 * Math.PI / 180), Math.sin(15 * Math.PI / 180), Math.sin(0 * Math.PI / 180), Math.sin(-15 * Math.PI / 180),
			Math.sin(-30 * Math.PI / 180), Math.sin(-45 * Math.PI / 180), Math.sin(-60 * Math.PI / 180), Math.sin(-75 * Math.PI / 180) };
	public static final double[] COS = { Math.cos(75 * Math.PI / 180), Math.cos(60 * Math.PI / 180), Math.cos(45 * Math.PI / 180),
			Math.cos(30 * Math.PI / 180), Math.cos(15 * Math.PI / 180), Math.cos(0 * Math.PI / 180), Math.cos(-15 * Math.PI / 180),
			Math.cos(-30 * Math.PI / 180), Math.cos(-45 * Math.PI / 180), Math.cos(-60 * Math.PI / 180), Math.cos(-75 * Math.PI / 180) };

	public HookSprite(Image image, int frameW, int frameH) {
		super(image, frameW, frameH);
	}

	public void upData(OreSprite[] allOre) {
		switch (hookSpriteState) {
		case CIRCUMGYRATEING:
			if (isRight) {
				direction++;
				if (direction >= 10)
					isRight = false;
			} else {
				direction--;
				if (direction <= 0)
					isRight = true;
			}

			break;
		case STRETCHING:
			linelength += nowSpeed;
			checkCollide(allOre);
			break;
		case BACKING:
			if (MainGameCanvas.nowFrame == 0)
				MainGameCanvas.nowFrame = 1;
			else
				MainGameCanvas.nowFrame = 0;
			linelength -= nowSpeed;
			if (linelength <= 16) {
				this.hookSpriteState = CIRCUMGYRATEING;
				MainGameCanvas.fire = false;
				MainGameCanvas.nowFrame = 1;
			}
			break;
		case CATCHING:
			if (MainGameCanvas.nowFrame == 0)
				MainGameCanvas.nowFrame = 1;
			else
				MainGameCanvas.nowFrame = 0;
			linelength -= nowSpeed;
			allOre[catchOre].setPosition(lineEndX - allOre[catchOre].getWidth() / 2, lineEndY);
			if (linelength <= 16) {
				this.hookSpriteState = CIRCUMGYRATEING;
				MainGameCanvas.score += nowValue;
				nowValue = 0;
				MainGameCanvas.fire = false;
				MainGameCanvas.nowFrame = 1;
				allOre[catchOre].setVisible(false);
			}
			break;
		default:
			break;
		}
		lineEndX = (int) (325 - linelength * SIN[direction]);
		lineEndY = (int) (78 + linelength * COS[direction]);
		checkBounds();
		if (direction > 5) {
			this.setFrame(5 - (direction - 5));
			this.setTransform(Sprite.TRANS_MIRROR);
			this.setPosition(lineEndX - 9, lineEndY - 10);
		} else {
			this.setFrame(direction);
			this.setTransform(Sprite.TRANS_NONE);
			this.setPosition(lineEndX - 15, lineEndY - 10);
		}

	}

	private void checkBounds() {
		if (lineEndX <= 0 || lineEndX >= 640 || lineEndY >= 530) {
			this.hookSpriteState = BACKING;
		}

	}

	private void checkCollide(OreSprite[] allOre) {
		for (int i = 0; i < allOre.length; i++) {
			if (this.collidesWith(allOre[i], true)) {
				this.hookSpriteState = CATCHING;
				nowSpeed = allOre[i].getSpeed();
				nowValue = allOre[i].getValue();
				catchOre = i;
			}
		}

	}

	public void init() {
		isRight = true;
		nowSpeed = 0;
		nowValue = 0;
		direction = 5;
		lineEndX = 325;
		lineEndY = 94;
		linelength = 16;
		catchOre = -1;
		setFrame(5);

	}

	public void drawHook(Graphics graphics) {
		graphics.setColor(0);
		graphics.drawLine(325, 78, lineEndX, lineEndY);
		this.paint(graphics);
	}

	public void setNowSpeed(int nowSpeed) {
		this.nowSpeed = nowSpeed;
	}

}
