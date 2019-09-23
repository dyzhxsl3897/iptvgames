package com.company.iptvgames.PPTangTest.canvas.gamecanvas.states;

import javax.microedition.lcdui.game.GameCanvas;

import com.company.iptvgames.PPTangTest.canvas.gamecanvas.MainGameCanvas;

public class GCPassState implements GCState {

	private MainGameCanvas gameCanvas;

	public GCPassState(MainGameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}

	public void exitState() {
		this.gameCanvas.getPassAlertSprite().setVisible(false);
		this.gameCanvas.getPassReturnSprite().setVisible(false);
	}

	public void intoState() {
		this.gameCanvas.getPassAlertSprite().setVisible(true);
		this.gameCanvas.getPassReturnSprite().setVisible(true);
	}

	public void keyAction() {
		int keyState = this.gameCanvas.getKeyStates();
		if (0 != (keyState & GameCanvas.FIRE_PRESSED)) {
			this.gameCanvas.initalizeGame();
			this.gameCanvas.updateStateToPlay();
		} 
	}

}
