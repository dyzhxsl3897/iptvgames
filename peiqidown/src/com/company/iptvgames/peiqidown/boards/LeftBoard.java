package com.company.iptvgames.peiqidown.boards;

import com.company.iptvgames.peiqidown.resources.ImageRes;

public class LeftBoard extends Board {

	protected LeftBoard(int x, int y, int index) {
		super(ImageRes.getInstance().getImage("leftBoardImg"), x, y, index);
	}

	public void nextFrame() {
		this.getBoardSprite().nextFrame();
	}

}
