package com.company.iptvgames.peiqidown.boards;

import com.company.iptvgames.peiqidown.resources.ImageRes;

public class FlapBoard extends Board {

	protected FlapBoard(int x, int y, int index) {
		super(ImageRes.getInstance().getImage("flapBoardImg"), x, y, index);
	}

	public void nextFrame() {
		if (this.getBoardSprite().getFrame() > 0) {
			this.getBoardSprite().nextFrame();
			if (this.getBoardSprite().getFrame() == 0) {
				this.setExist(false);
				this.getBoardSprite().setFrame(this.getBoardSprite().getFrameSequenceLength() - 1);
			}
		}
	}

	public void startAnimation() {
		this.getBoardSprite().nextFrame();
	}

}
