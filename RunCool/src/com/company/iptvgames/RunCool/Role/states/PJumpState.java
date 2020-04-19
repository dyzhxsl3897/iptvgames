package com.company.iptvgames.RunCool.Role.states;

import com.company.iptvgames.RunCool.Role.PlayerObject;

public class PJumpState implements PState {
	
	private PlayerObject role;

	public PJumpState(PlayerObject role) {
		this.role = role;
	}

	public void exitState() {
		this.role.getJumpSprite().setVisible(false);
	}

	public void intoState() {
		this.role.getJumpSprite().setVisible(true);
		this.role.getJumpSprite().setFrame(0);
		this.role.jump();
	}

	public void nextFrame() {
		this.role.getJumpSprite().nextFrame();
	}
}
