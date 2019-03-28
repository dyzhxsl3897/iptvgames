package com.company.iptvgames.peiqidown.peiqi.states;

import com.company.iptvgames.peiqidown.peiqi.Peiqi;

public class PJumpState implements PState {

	private Peiqi peiqi;

	public PJumpState(Peiqi peiqi) {
		this.peiqi = peiqi;
	}

	public void exitState() {
		this.peiqi.getJumpSprite().setVisible(false);
	}

	public void intoState() {
		this.peiqi.getJumpSprite().setVisible(true);
		this.peiqi.getJumpSprite().setFrame(0);
	}

	public void nextFrame() {
		this.peiqi.getJumpSprite().nextFrame();
	}

}
