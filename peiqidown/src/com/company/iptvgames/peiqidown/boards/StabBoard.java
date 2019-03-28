package com.company.iptvgames.peiqidown.boards;

import com.company.iptvgames.peiqidown.resources.ImageRes;

public class StabBoard extends Board {

	protected StabBoard(int x, int y, int index) {
		super(ImageRes.getInstance().getImage("stabBoardImg"), x, y, index);
	}

	public void nextFrame() {
	}

}
