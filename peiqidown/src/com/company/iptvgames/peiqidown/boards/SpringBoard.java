package com.company.iptvgames.peiqidown.boards;

import com.company.iptvgames.peiqidown.resources.ImageRes;

public class SpringBoard extends Board {

	protected SpringBoard(int x, int y, int index) {
		super(ImageRes.getInstance().getImage("springBoardImg"), x, y, index);
	}

	public void nextFrame() {
		if (this.getBoardSprite().getFrame() > 0) {
			this.getBoardSprite().nextFrame();
		}
	}

	public void startAnimation() {
		this.getBoardSprite().nextFrame();
	}

}
