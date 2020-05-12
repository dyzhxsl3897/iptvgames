package com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.states;

import com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.MainGameCanvas;
import com.company.iptvgames.framework.utils.Constants;

public class GCPlayState implements GCState {

	private MainGameCanvas gameCanvas;

	public GCPlayState(MainGameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}

	public void exitState() {
	}

	public void intoState() {
	}

	public void keyAction(int keyCode) {
		if (keyCode == Constants.KeyCode.NUM_0) {
			this.gameCanvas.updateStateToPause();
		} else if (keyCode == Constants.KeyCode.OK) {
			this.gameCanvas.setBomb(this.gameCanvas.getRole());
		} else if (keyCode == Constants.KeyCode.RIGHT) {
			this.gameCanvas.getRole().faceRight();
			this.gameCanvas.moveIfNotCollides(this.gameCanvas.getRole(), 1, 0);
		} else if (keyCode == Constants.KeyCode.LEFT) {
			this.gameCanvas.getRole().faceLeft();
			this.gameCanvas.moveIfNotCollides(this.gameCanvas.getRole(), -1, 0);
		} else if (keyCode == Constants.KeyCode.UP) {
			this.gameCanvas.getRole().faceUp();
			this.gameCanvas.moveIfNotCollides(this.gameCanvas.getRole(), 0, -1);
		} else if (keyCode == Constants.KeyCode.DOWN) {
			this.gameCanvas.getRole().faceDown();
			this.gameCanvas.moveIfNotCollides(this.gameCanvas.getRole(), 0, 1);
		}
	}
}
