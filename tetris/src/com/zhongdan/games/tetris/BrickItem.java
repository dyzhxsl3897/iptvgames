package com.zhongdan.games.tetris;

import java.util.Vector;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.zhongdan.games.framework.utils.ImageUtil;

public class BrickItem {

	private MyGameCanvas canvas;
	private Vector sprites = new Vector();
	private int type;
	private int dir;
	private int startX = MyGameConstants.Brick.START_LEFT;
	private int startY = MyGameConstants.Brick.START_TOP;
	private int nextX = MyGameConstants.Brick.NEXT_LEFT;
	private int nextY = MyGameConstants.Brick.NEXT_TOP;

	private BrickItem() {
	}

	public static BrickItem createNewBrick(int type, int dir, MyGameCanvas canvas, boolean isNext) {
		BrickItem brick = new BrickItem();
		brick.canvas = canvas;
		brick.type = type;
		brick.dir = dir;
		for (int i = 0; i < 4; i++) {
			Image img = ImageUtil.createImage("/bricks/" + type + ".png");
			Sprite sprite = new Sprite(img);
			sprite.defineReferencePixel(sprite.getWidth() / 2, sprite.getHeight() / 2);
			int initX = 0, initY = 0;
			if (isNext) {
				initX = brick.nextX + MyGameConstants.Brick.brickStart[type][dir][i][1] * MyGameConstants.Brick.WIDTH;
				initY = brick.nextY + MyGameConstants.Brick.brickStart[type][dir][i][0] * MyGameConstants.Brick.HEIGHT;
			} else {
				initX = brick.startX + MyGameConstants.Brick.brickStart[type][dir][i][1] * MyGameConstants.Brick.WIDTH;
				initY = brick.startY + MyGameConstants.Brick.brickStart[type][dir][i][0] * MyGameConstants.Brick.HEIGHT;
			}
			sprite.setPosition(initX, initY);
			int transRot = MyGameConstants.Brick.DIRECTION[dir];
			sprite.setTransform(transRot);
			brick.sprites.addElement(sprite);
			brick.canvas.getLayerManager().insert(sprite, 0);
		}
		return brick;
	}

	public boolean moveDown() {
		if (canMove(0, MyGameConstants.Brick.HEIGHT)) {
			for (int i = 0; i < sprites.size(); i++) {
				Sprite sprite = (Sprite) sprites.elementAt(i);
				int x = sprite.getX();
				int y = sprite.getY();
				int newX = x;
				int newY = y + MyGameConstants.Brick.HEIGHT;
				sprite.setPosition(newX, newY);
			}
			return true;
		}
		return false;
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
			int col = (newX - MyGameConstants.Brick.START_LEFT) / MyGameConstants.Brick.WIDTH;
			int row = (newY - MyGameConstants.Brick.START_TOP) / MyGameConstants.Brick.HEIGHT;
			if (col < 0 || col > MyGameConstants.Playboard.COL_NO - 1 || row < 0 || row > MyGameConstants.Playboard.ROW_NO - 1
					|| canvas.getAllBrickSprite()[row][col] != null) {
				return false;
			}
		}
		return true;
	}

	public void turnBrick() {
		int transRot = MyGameConstants.Brick.DIRECTION[dir];
		// If can't turn the brick, then return
		for (int i = 0; i < sprites.size(); i++) {
			Sprite sprite = (Sprite) sprites.elementAt(i);
			int x = sprite.getX();
			int y = sprite.getY();
			int newX = x + MyGameConstants.Brick.brickTransform[type][dir][i][1] * MyGameConstants.Brick.WIDTH;
			int newY = y + MyGameConstants.Brick.brickTransform[type][dir][i][0] * MyGameConstants.Brick.HEIGHT;
			int col = (newX - MyGameConstants.Brick.START_LEFT) / MyGameConstants.Brick.WIDTH;
			int row = (newY - MyGameConstants.Brick.START_TOP) / MyGameConstants.Brick.HEIGHT;
			if (col < 0 || col > MyGameConstants.Playboard.COL_NO - 1 || row < 0 || row > MyGameConstants.Playboard.ROW_NO - 1
					|| canvas.getAllBrickSprite()[row][col] != null) {
				return;
			}
		}
		// Then turn the brick
		for (int i = 0; i < sprites.size(); i++) {
			Sprite sprite = (Sprite) sprites.elementAt(i);
			int x = sprite.getX();
			int y = sprite.getY();
			int newX = x + MyGameConstants.Brick.brickTransform[type][dir][i][1] * MyGameConstants.Brick.WIDTH;
			int newY = y + MyGameConstants.Brick.brickTransform[type][dir][i][0] * MyGameConstants.Brick.HEIGHT;
			sprite.setPosition(newX, newY);
			sprite.setTransform(transRot);
		}
		dir++;
		dir %= 4;
	}

	public Vector getSprites() {
		return sprites;
	}

	public int getType() {
		return type;
	}

	public int getDir() {
		return dir;
	}

}