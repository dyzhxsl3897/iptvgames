package com.company.iptvgames.peiqidown.peiqi.states;

import com.company.iptvgames.peiqidown.peiqi.Peiqi;

public class PStandState implements PState {

	private Peiqi peiqi;

	public PStandState(Peiqi peiqi) {
		this.peiqi = peiqi;
	}

	public void exitState() {
		this.peiqi.getStandSprite().setVisible(false);
	}

	public void intoState() {
		this.peiqi.getStandSprite().setVisible(true);
		this.peiqi.getStandSprite().setFrame(0);
	}

	public void nextFrame() {
		this.peiqi.getStandSprite().nextFrame();
	}

}
