package com.company.iptvgames.RunCool.Role.states;

import com.company.iptvgames.RunCool.Role.PlayerObject;

public class PWalkState implements PState {
	
	private PlayerObject role;

	public PWalkState(PlayerObject role) {
		this.role = role;
	}

	public void exitState() {
		this.role.getWalkSprite().setVisible(false);
	}

	public void intoState() {
		this.role.getWalkSprite().setVisible(true);
		this.role.getWalkSprite().setFrame(0);
	}

	public void nextFrame() {
		this.role.getWalkSprite().nextFrame();
	}
}
