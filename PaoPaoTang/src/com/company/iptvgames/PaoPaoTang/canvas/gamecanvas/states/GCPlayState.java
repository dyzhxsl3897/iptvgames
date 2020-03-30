package com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.states;

import javax.microedition.lcdui.game.GameCanvas;

import com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.MainGameCanvas;


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
		if (0 != (keyState & GameCanvas.FIRE_PRESSED)) {
			this.gameCanvas.setBomb(this.gameCanvas.getRole());
		} else if (0 != (keyState & GameCanvas.RIGHT_PRESSED)) {
			this.gameCanvas.getRole().faceRight();
			this.gameCanvas.moveIfNotCollides(this.gameCanvas.getRole(),1,0);
		} else if (0 != (keyState & GameCanvas.LEFT_PRESSED)) {
			this.gameCanvas.getRole().faceLeft();
			this.gameCanvas.moveIfNotCollides(this.gameCanvas.getRole(),-1,0);
		}else if (0 != (keyState & GameCanvas.UP_PRESSED)) {
			this.gameCanvas.getRole().faceUp();
			this.gameCanvas.moveIfNotCollides(this.gameCanvas.getRole(),0,-1);
		}else if (0 != (keyState & GameCanvas.DOWN_PRESSED)) {
			this.gameCanvas.getRole().faceDown();
			this.gameCanvas.moveIfNotCollides(this.gameCanvas.getRole(),0,1);
		}
	}

}
