package com.zhongdan.games.huarong;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.zhongdan.games.huarong.GameConstants.RoleName;

public class RoleSprite extends Sprite {

	private int roleName;
	private int row;
	private int col;
	private int roleWidth;
	private int roleHeight;
	private boolean selected;

	public RoleSprite(Image image, int roleName, int frameWidth, int frameHeight, int row, int col, boolean selected) {
		super(image, frameWidth, frameHeight);
		this.roleName = roleName;
		this.row = row;
		this.col = col;
		switch (roleName) {
		case RoleName.CAO_CAO:
			this.roleWidth = 2;
			this.roleHeight = 2;
			break;
		case RoleName.GUAN_YU:
			this.roleWidth = 2;
			this.roleHeight = 1;
			break;
		case RoleName.ZHAO_YUN:
			this.roleWidth = 1;
			this.roleHeight = 2;
			break;
		case RoleName.ZHANG_FEI:
			this.roleWidth = 1;
			this.roleHeight = 2;
			break;
		case RoleName.MA_CHAO:
			this.roleWidth = 1;
			this.roleHeight = 2;
			break;
		case RoleName.HUANG_ZHONG:
			this.roleWidth = 1;
			this.roleHeight = 2;
			break;
		case RoleName.ZU:
			this.roleWidth = 1;
			this.roleHeight = 1;
			break;
		}
		if (selected) {
			this.setFrame(1);
		} else {
			this.setFrame(0);
		}
		int x = GameConstants.GameSettings.TOP_LEFT_CELL_X + col * GameConstants.GameSettings.CELL_W;
		int y = GameConstants.GameSettings.TOP_LEFT_CELL_Y + row * GameConstants.GameSettings.CELL_H;
		super.setPosition(x, y);
	}

	public int getRoleName() {
		return roleName;
	}

	public void setRoleName(int roleName) {
		this.roleName = roleName;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected) {
			this.setFrame(1);
		} else {
			this.setFrame(0);
		}
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
		int x = GameConstants.GameSettings.TOP_LEFT_CELL_X + col * GameConstants.GameSettings.CELL_W;
		int y = GameConstants.GameSettings.TOP_LEFT_CELL_Y + row * GameConstants.GameSettings.CELL_H;
		super.setPosition(x, y);
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
		int x = GameConstants.GameSettings.TOP_LEFT_CELL_X + col * GameConstants.GameSettings.CELL_W;
		int y = GameConstants.GameSettings.TOP_LEFT_CELL_Y + row * GameConstants.GameSettings.CELL_H;
		super.setPosition(x, y);
	}

	public int getRoleWidth() {
		return roleWidth;
	}

	public int getRoleHeight() {
		return roleHeight;
	}

}
