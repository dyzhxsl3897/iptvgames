package com.company.iptvgames.PPTangTest.canvas.gamecanvas.states;

import javax.microedition.lcdui.game.GameCanvas;

import com.company.iptvgames.PPTangTest.canvas.gamecanvas.MainGameCanvas;

public class GCDeadState implements GCState {

	private MainGameCanvas gameCanvas;

	public GCDeadState(MainGameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}

	public void exitState() {
		this.gameCanvas.getFailAlertSprite().setVisible(false);
		this.gameCanvas.getFailReturnSprite().setVisible(false);
	}

	public void intoState() {
		this.gameCanvas.getFailAlertSprite().setVisible(true);
		this.gameCanvas.getFailReturnSprite().setVisible(true);
	}

	public void keyAction() {
		int keyState = this.gameCanvas.getKeyStates();
		if (0 != (keyState & GameCanvas.FIRE_PRESSED)) {
			this.gameCanvas.initalizeGame();
			this.gameCanvas.updateStateToPlay();
		} 
	}

}
