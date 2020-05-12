package com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.states;

import com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.MainGameCanvas;
import com.company.iptvgames.framework.utils.Constants;

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

	public void keyAction(int keyCode) {
		if (showAlert) {
			if (keyCode == Constants.KeyCode.OK) {
				this.gameCanvas.initalizeGame();
				this.gameCanvas.updateStateToPlay();
			}
		}
	}

}
