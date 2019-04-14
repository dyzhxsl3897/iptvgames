package com.company.iptvgames.peiqidown.canvas.gamecanvas.states;

import javax.microedition.lcdui.game.GameCanvas;

import com.company.iptvgames.peiqidown.GameConst;
import com.company.iptvgames.peiqidown.canvas.gamecanvas.MainGameCanvas;

public class GCPlayState implements GCState {

	private MainGameCanvas gameCanvas;

	public GCPlayState(MainGameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}

	public void exitState() {
	}

	public void intoState() {
	}

	public void keyAction() {
		int keyState = this.gameCanvas.getKeyStates();
		if (0 != (keyState & GameCanvas.RIGHT_PRESSED)) {
			this.gameCanvas.getPeiqi().faceRight();
			if (!this.gameCanvas.getPeiqi().isInWalkState()) {
				this.gameCanvas.getPeiqi().updateState(this.gameCanvas.getPeiqi().getWalkState());
			}
			this.gameCanvas.getPeiqi().move(GameConst.Peiqi.MOVE_SPEED, 0);
		} else if (0 != (keyState & GameCanvas.LEFT_PRESSED)) {
			this.gameCanvas.getPeiqi().faceLeft();
			if (!this.gameCanvas.getPeiqi().isInWalkState()) {
				this.gameCanvas.getPeiqi().updateState(this.gameCanvas.getPeiqi().getWalkState());
			}
			this.gameCanvas.getPeiqi().move(-GameConst.Peiqi.MOVE_SPEED, 0);
		} else if (0 == keyState) {
			if (!this.gameCanvas.getPeiqi().isInStandState()) {
				this.gameCanvas.getPeiqi().updateState(this.gameCanvas.getPeiqi().getStandState());
			}
		}
	}

}
