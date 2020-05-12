package com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.states;

import com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.MainGameCanvas;
import com.company.iptvgames.framework.utils.Constants;

public class GCPassState implements GCState {

	private MainGameCanvas gameCanvas;
	private boolean showAlert = false;

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
		showAlert = true;
	}

	public void keyAction(int keyCode) {
		if (showAlert) {
			if (keyCode == Constants.KeyCode.OK) {
				this.gameCanvas.initalizeGame();
				this.gameCanvas.updateStateToPlay();
			}
		}
	}

}
