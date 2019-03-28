package com.company.iptvgames.peiqidown.boards;

import com.company.iptvgames.peiqidown.resources.ImageRes;

public class LandBoard extends Board {

	protected LandBoard(int x, int y, int index) {
		super(ImageRes.getInstance().getImage("landBoardImg"), x, y, index);
	}

	public void nextFrame() {
	}

}
