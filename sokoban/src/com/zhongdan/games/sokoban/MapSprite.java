package com.zhongdan.games.sokoban;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class MapSprite extends Sprite {

	public MapSprite(Image image, int row, int col) {
		super(image);
		int x = GameConstants.GameSettings.TOP_LEFT_CELL_X + (col - row) * GameConstants.GameSettings.CELL_W / 2;
		int y = GameConstants.GameSettings.TOP_LEFT_CELL_Y + (col + row) * GameConstants.GameSettings.CELL_H / 2;
		super.setPosition(x, y);
	}

}
