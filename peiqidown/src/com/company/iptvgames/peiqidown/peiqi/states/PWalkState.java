package com.company.iptvgames.peiqidown.peiqi.states;

import com.company.iptvgames.peiqidown.peiqi.Peiqi;

public class PWalkState implements PState {

	private Peiqi peiqi;

	public PWalkState(Peiqi peiqi) {
		this.peiqi = peiqi;
	}

	public void exitState() {
		this.peiqi.getWalkSprite().setVisible(false);
	}

	public void intoState() {
		this.peiqi.getWalkSprite().setVisible(true);
		this.peiqi.getWalkSprite().setFrame(0);
	}

	public void nextFrame() {
		this.peiqi.getWalkSprite().nextFrame();
	}
}
