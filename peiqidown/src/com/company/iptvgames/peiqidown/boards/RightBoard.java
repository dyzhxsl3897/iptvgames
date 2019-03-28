package com.company.iptvgames.peiqidown.boards;

import com.company.iptvgames.peiqidown.resources.ImageRes;

public class RightBoard extends Board {

	protected RightBoard(int x, int y, int index) {
		super(ImageRes.getInstance().getImage("rightBoardImg"), x, y, index);
	}

	public void nextFrame() {
		this.getBoardSprite().nextFrame();
	}

}
