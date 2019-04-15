package com.company.iptvgames.peiqidown.peiqi;

import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.company.iptvgames.peiqidown.GameConst;
import com.company.iptvgames.peiqidown.boards.Board;
import com.company.iptvgames.peiqidown.boards.FlapBoard;
import com.company.iptvgames.peiqidown.boards.LeftBoard;
import com.company.iptvgames.peiqidown.boards.RightBoard;
import com.company.iptvgames.peiqidown.boards.SpringBoard;
import com.company.iptvgames.peiqidown.boards.StabBoard;
import com.company.iptvgames.peiqidown.peiqi.states.PJumpState;
import com.company.iptvgames.peiqidown.peiqi.states.PStandState;
import com.company.iptvgames.peiqidown.peiqi.states.PState;
import com.company.iptvgames.peiqidown.peiqi.states.PWalkState;
import com.company.iptvgames.peiqidown.resources.ImageRes;

public class Peiqi {

	private PState state;
	private PState walkState;
	private PState standState;
	private PState jumpState;

	private Sprite walkSprite;
	private Sprite standSprite;
	private Sprite jumpSprite;

	private int posX;
	private int posY;

	private int dropSpeed;
	private boolean isTouchedTop;

	public Peiqi() {
		this.dropSpeed = 0;
		this.isTouchedTop = false;

		walkSprite = new Sprite(ImageRes.getInstance().getImage("peiqiWalkImg"), GameConst.Peiqi.WIDTH, GameConst.Peiqi.HEIGHT);
		standSprite = new Sprite(ImageRes.getInstance().getImage("peiqiStandImg"), GameConst.Peiqi.WIDTH, GameConst.Peiqi.HEIGHT);
		jumpSprite = new Sprite(ImageRes.getInstance().getImage("peiqiJumpImg"), GameConst.Peiqi.WIDTH, GameConst.Peiqi.HEIGHT);
		walkSprite.defineCollisionRectangle(GameConst.Peiqi.COLLIDE_X, GameConst.Peiqi.COLLIDE_Y, GameConst.Peiqi.COLLIDE_W,
				GameConst.Peiqi.COLLIDE_H);
		standSprite.defineCollisionRectangle(GameConst.Peiqi.COLLIDE_X, GameConst.Peiqi.COLLIDE_Y, GameConst.Peiqi.COLLIDE_W,
				GameConst.Peiqi.COLLIDE_H);
		jumpSprite.defineCollisionRectangle(GameConst.Peiqi.COLLIDE_X, GameConst.Peiqi.COLLIDE_Y, GameConst.Peiqi.COLLIDE_W,
				GameConst.Peiqi.COLLIDE_H);
		walkSprite.setVisible(false);
		standSprite.setVisible(false);
		jumpSprite.setVisible(false);

		setPosition(GameConst.Peiqi.START_X - GameConst.Peiqi.WIDTH / 2, GameConst.Peiqi.START_Y - GameConst.Peiqi.HEIGHT / 2);

		walkState = new PWalkState(this);
		standState = new PStandState(this);
		jumpState = new PJumpState(this);
		updateState(standState);
	}

	public void addToScreen(LayerManager layerManager) {
		layerManager.insert(walkSprite, GameConst.GameCanvas.LAYER_1);
		layerManager.insert(standSprite, GameConst.GameCanvas.LAYER_1);
		layerManager.insert(jumpSprite, GameConst.GameCanvas.LAYER_1);
	}

	public void drop(Board[] boards) {
		int indexOfStandOnBoard = indexOfStandOnBoard(boards);
		if (!isStandOnBoard(boards)) {
			this.move(0, this.dropSpeed);
			this.dropSpeed += GameConst.GameCanvas.GRAVITY;
			indexOfStandOnBoard = indexOfStandOnBoard(boards);
			if (isStandOnBoard(boards)) {
				this.dropSpeed = 0;
				this.setPosition(this.posX, boards[indexOfStandOnBoard % GameConst.Board.NUMBER].getPosY() - GameConst.Peiqi.HEIGHT + 10);
			}
		} else {
			this.dropSpeed = 0;
			this.setPosition(this.posX, boards[indexOfStandOnBoard % GameConst.Board.NUMBER].getPosY() - GameConst.Peiqi.HEIGHT + 10);
		}
	}

	public boolean isOutOfScreen() {
		if (this.standSprite.getY() > GameConst.SCREEN_HEIGHT) {
			return true;
		}
		return false;
	}

	public boolean isTouchTop() {
		return this.posY < GameConst.Peiqi.TOUCH_TOP_Y;
	}

	public boolean isStandOnLeftBoard(Board[] boards) {
		int indexOfStandOnBoard = indexOfStandOnBoard(boards);
		if (indexOfStandOnBoard == -1) {
			return false;
		}
		Board board = boards[indexOfStandOnBoard % GameConst.Board.NUMBER];
		if (board instanceof LeftBoard) {
			return true;
		}
		return false;
	}

	public boolean isStandOnRightBoard(Board[] boards) {
		int indexOfStandOnBoard = indexOfStandOnBoard(boards);
		if (indexOfStandOnBoard == -1) {
			return false;
		}
		Board board = boards[indexOfStandOnBoard % GameConst.Board.NUMBER];
		if (board instanceof RightBoard) {
			return true;
		}
		return false;
	}

	public boolean isStandOnSpringBoard(Board[] boards) {
		int indexOfStandOnBoard = indexOfStandOnBoard(boards);
		if (indexOfStandOnBoard == -1) {
			return false;
		}
		Board board = boards[indexOfStandOnBoard % GameConst.Board.NUMBER];
		if (board instanceof SpringBoard) {
			return true;
		}
		return false;
	}

	public boolean isStandOnFlapBoard(Board[] boards) {
		int indexOfStandOnBoard = indexOfStandOnBoard(boards);
		if (indexOfStandOnBoard == -1) {
			return false;
		}
		Board board = boards[indexOfStandOnBoard % GameConst.Board.NUMBER];
		if (board instanceof FlapBoard) {
			return true;
		}
		return false;
	}

	public boolean isStandOnStabBoard(Board[] boards) {
		int indexOfStandOnBoard = indexOfStandOnBoard(boards);
		if (indexOfStandOnBoard == -1) {
			return false;
		}
		Board board = boards[indexOfStandOnBoard % GameConst.Board.NUMBER];
		if (board instanceof StabBoard) {
			return true;
		}
		return false;
	}

	public boolean isStandOnBoard(Board[] boards) {
		if (-1 == indexOfStandOnBoard(boards)) {
			return false;
		} else {
			return true;
		}
	}

	public int indexOfStandOnBoard(Board[] boards) {
		int indexOfBoard = -1;
		for (int i = 0; i < boards.length; i++) {
			if (boards[i].collidesWith(walkSprite, false) || boards[i].collidesWith(standSprite, false) || boards[i].collidesWith(jumpSprite, false)) {
				indexOfBoard = boards[i].getIndex();
				break;
			}
		}
		return indexOfBoard;
	}

	public void updateState(PState newState) {
		if (this.state != null) {
			this.state.exitState();
		}
		this.state = newState;
		this.state.intoState();
	}

	public void faceLeft() {
		this.walkSprite.setTransform(Sprite.TRANS_MIRROR);
		this.standSprite.setTransform(Sprite.TRANS_MIRROR);
		this.jumpSprite.setTransform(Sprite.TRANS_MIRROR);
	}

	public void faceRight() {
		this.walkSprite.setTransform(Sprite.TRANS_NONE);
		this.standSprite.setTransform(Sprite.TRANS_NONE);
		this.jumpSprite.setTransform(Sprite.TRANS_NONE);
	}

	public void move(int dx, int dy) {
		this.setPosition(this.posX + dx, this.posY + dy);
	}

	public void setPosition(int x, int y) {
		if (x < GameConst.GameCanvas.PLAYAREA_LEFT_X || x > GameConst.GameCanvas.PLAYAREA_RIGHT_X) {
		} else {
			this.posX = x;
		}
		this.posY = y;
		this.walkSprite.setPosition(this.posX, this.posY);
		this.standSprite.setPosition(this.posX, this.posY);
		this.jumpSprite.setPosition(this.posX, this.posY);
	}

	public void nextFrame() {
		this.state.nextFrame();
	}

	public boolean isInWalkState() {
		return this.getState() instanceof PWalkState;
	}

	public boolean isInStandState() {
		return this.getState() instanceof PStandState;
	}

	public boolean isInJumpState() {
		return this.getState() instanceof PJumpState;
	}

	public PState getState() {
		return state;
	}

	public Sprite getWalkSprite() {
		return walkSprite;
	}

	public Sprite getStandSprite() {
		return standSprite;
	}

	public Sprite getJumpSprite() {
		return jumpSprite;
	}

	public PState getWalkState() {
		return walkState;
	}

	public PState getStandState() {
		return standState;
	}

	public PState getJumpState() {
		return jumpState;
	}

	public int getDropSpeed() {
		return dropSpeed;
	}

	public void setDropSpeed(int dropSpeed) {
		this.dropSpeed = dropSpeed;
	}

	public boolean isTouchedTop() {
		return isTouchedTop;
	}

	public void setTouchedTop(boolean isTouchedTop) {
		this.isTouchedTop = isTouchedTop;
	}

}
