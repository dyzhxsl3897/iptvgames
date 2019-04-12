package com.company.iptvgames.peiqidown.boards;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.company.iptvgames.peiqidown.GameConst;

public abstract class Board {

	private Sprite boardSprite;

	private int posX;
	private int posY;

	private boolean isExist;
	private int index;

	protected Board(Image image, int x, int y, int index) {
		this.boardSprite = new Sprite(image, GameConst.Board.WIDTH, GameConst.Board.HEIGHT);
		initBoard(image, x, y, index);
	}

	protected void initBoard(Image image, int x, int y, int index) {
		this.setPosition(x, y);
		this.index = index;
		this.isExist = true;
	}

	public void removeFromScreen(LayerManager layerManager) {
		layerManager.remove(boardSprite);
	}

	public void addToScreen(LayerManager layerManager) {
		layerManager.insert(boardSprite, 0);
	}

	public void move(int dx, int dy) {
		this.setPosition(this.posX + dx, this.posY + dy);
	}

	public void setPosition(int x, int y) {
		this.posX = x;
		this.posY = y;
		this.boardSprite.setPosition(this.posX, this.posY);
	}

	public boolean collidesWith(Sprite s, boolean pixelLevel) {
		if (this.isExist) {
			return this.boardSprite.collidesWith(s, pixelLevel);
		}
		return false;
	}

	public Sprite getBoardSprite() {
		return boardSprite;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public abstract void nextFrame();

}
