package com.zhongdan.games.tetris;

import java.util.Vector;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.zhongdan.games.framework.utils.ImageUtil;

public class MovingBrick {

	private MainGameCanvas canvas;
	private Vector sprites = new Vector();
	private int type;
	private int dir;
	private int initX = MyGameConstants.Brick.START_LEFT;
	private int initY = MyGameConstants.Brick.START_TOP;

	public MovingBrick(int type, int dir, MainGameCanvas canvas) {
		this.canvas = canvas;
		this.type = type;
		this.dir = dir;
		for (int i = 0; i < 4; i++) {
			Image img = ImageUtil.createImage("/bricks/" + type + ".png");
			Sprite sprite = new Sprite(img);
			sprite.defineReferencePixel(sprite.getWidth() / 2, sprite.getHeight() / 2);
			sprite.setPosition(initX + MyGameConstants.Brick.brickStart[type][dir][i][0] * MyGameConstants.Brick.WIDTH, initY
					+ MyGameConstants.Brick.brickStart[type][dir][i][1] * MyGameConstants.Brick.HEIGHT);
			int transRot = 0;
			switch (dir) {
			case 0:
				transRot = Sprite.TRANS_NONE;
				break;
			case 1:
				transRot = Sprite.TRANS_ROT90;
				break;
			case 2:
				transRot = Sprite.TRANS_ROT180;
				break;
			case 3:
				transRot = Sprite.TRANS_ROT270;
				break;
			}
			sprite.setTransform(transRot);
			sprites.addElement(sprite);
			this.canvas.getLayerManager().insert(sprite, 0);
		}
	}

	public void moveDown() {
		if (canMove(0, MyGameConstants.Brick.HEIGHT)) {
			for (int i = 0; i < sprites.size(); i++) {
				Sprite sprite = (Sprite) sprites.elementAt(i);
				int x = sprite.getX();
				int y = sprite.getY();
				int newX = x;
				int newY = y + MyGameConstants.Brick.HEIGHT;
				sprite.setPosition(newX, newY);
			}
		}
	}

	public void moveLeft() {
		if (canMove(-MyGameConstants.Brick.WIDTH, 0)) {
			for (int i = 0; i < sprites.size(); i++) {
				Sprite sprite = (Sprite) sprites.elementAt(i);
				int x = sprite.getX();
				int y = sprite.getY();
				int newX = x - MyGameConstants.Brick.WIDTH;
				int newY = y;
				sprite.setPosition(newX, newY);
			}
		}
	}

	public void moveRight() {
		if (canMove(MyGameConstants.Brick.WIDTH, 0)) {
			for (int i = 0; i < sprites.size(); i++) {
				Sprite sprite = (Sprite) sprites.elementAt(i);
				int x = sprite.getX();
				int y = sprite.getY();
				int newX = x + MyGameConstants.Brick.WIDTH;
				int newY = y;
				sprite.setPosition(newX, newY);
			}
		}
	}

	public boolean canMove(int diffX, int diffY) {
		for (int i = 0; i < sprites.size(); i++) {
			Sprite sprite = (Sprite) sprites.elementAt(i);
			int x = sprite.getX();
			int y = sprite.getY();
			int newX = x + diffX;
			int newY = y + diffY;
			int posX = (newX - MyGameConstants.Brick.START_LEFT) / MyGameConstants.Brick.WIDTH;
			int posY = (newY - MyGameConstants.Brick.START_TOP) / MyGameConstants.Brick.HEIGHT;
			if (posX < 0 || posX > MyGameConstants.Playboard.WIDTH - 1 || posY < 0 || posY > MyGameConstants.Playboard.HEIGHT - 1
					|| canvas.getMap()[posX][posY] == 1) {
				return false;
			}
		}
		return true;
	}

	public void turnBrick() {
		dir++;
		dir %= 4;
		int transRot = 0;
		switch (dir) {
		case 0:
			transRot = Sprite.TRANS_NONE;
			break;
		case 1:
			transRot = Sprite.TRANS_ROT90;
			break;
		case 2:
			transRot = Sprite.TRANS_ROT180;
			break;
		case 3:
			transRot = Sprite.TRANS_ROT270;
			break;
		}
		// If can't turn the brick, then return
		for (int i = 0; i < sprites.size(); i++) {
			Sprite sprite = (Sprite) sprites.elementAt(i);
			int x = sprite.getX();
			int y = sprite.getY();
			int newX = x + MyGameConstants.Brick.brickTransform[type][dir][i][0] * MyGameConstants.Brick.WIDTH;
			int newY = y + MyGameConstants.Brick.brickTransform[type][dir][i][1] * MyGameConstants.Brick.HEIGHT;
			int col = (newX - MyGameConstants.Brick.START_LEFT) / MyGameConstants.Brick.WIDTH;
			int row = (newY - MyGameConstants.Brick.START_TOP) / MyGameConstants.Brick.HEIGHT;
			if (col < 0 || col > MyGameConstants.Playboard.WIDTH - 1 || row < 0 || row > MyGameConstants.Playboard.HEIGHT - 1
					|| canvas.getMap()[col][row] == 1) {
				return;
			}
		}
		// Then turn the brick
		for (int i = 0; i < sprites.size(); i++) {
			Sprite sprite = (Sprite) sprites.elementAt(i);
			int x = sprite.getX();
			int y = sprite.getY();
			int newX = x + MyGameConstants.Brick.brickTransform[type][dir][i][0] * MyGameConstants.Brick.WIDTH;
			int newY = y + MyGameConstants.Brick.brickTransform[type][dir][i][1] * MyGameConstants.Brick.HEIGHT;
			sprite.setPosition(newX, newY);
			sprite.setTransform(transRot);
		}
	}

	public Vector getSprites() {
		return sprites;
	}

	public void setSprites(Vector sprites) {
		this.sprites = sprites;
	}

}
