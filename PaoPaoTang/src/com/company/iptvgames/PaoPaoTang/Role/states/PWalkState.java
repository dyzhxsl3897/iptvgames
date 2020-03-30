package com.company.iptvgames.PaoPaoTang.Role.states;

import com.company.iptvgames.PaoPaoTang.Role.PlayerObject;

public class PWalkState implements PState {

	private PlayerObject player;

	public PWalkState(PlayerObject player) {
		this.player = player;
	}

	public void exitState() {
		this.player.getWalkSprite().setVisible(false);
	}

	public void intoState() {
		this.player.getWalkSprite().setVisible(true);
		this.player.getWalkSprite().setFrame(0);
	}

	public void nextFrame() {
		this.player.getWalkSprite().nextFrame();
	}
}
