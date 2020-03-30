package com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.states;

import javax.microedition.lcdui.game.GameCanvas;

import com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.MainGameCanvas;

public class GCDeadState implements GCState {

	private MainGameCanvas gameCanvas;
	private boolean showAlert = false;
	
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
		showAlert = true;
	}

	public void keyAction() {
		if(showAlert){
			int keyState = this.gameCanvas.getKeyStates();
			if (0 != (keyState & GameCanvas.FIRE_PRESSED)) {
				this.gameCanvas.initalizeGame();
				this.gameCanvas.updateStateToPlay();
			}
		}	 
	}

}
