package com.company.iptvgames.PPTangTest.canvas.gamecanvas.states;

import javax.microedition.lcdui.game.GameCanvas;

import com.company.iptvgames.PPTangTest.canvas.gamecanvas.MainGameCanvas;

public class GCPauseState implements GCState {

	private MainGameCanvas gameCanvas;

	public GCPauseState(MainGameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}

	public void exitState() {
		this.gameCanvas.getAlertSprite().setVisible(false);
		this.gameCanvas.getContinueSprite().setVisible(false);
		this.gameCanvas.getOverSprite().setVisible(false);
	}

	public void intoState() {
		this.gameCanvas.getAlertSprite().setVisible(true);
		this.gameCanvas.getContinueSprite().setVisible(true);
		this.gameCanvas.getOverSprite().setVisible(false);
	}

	public void keyAction() {
		int keyState = this.gameCanvas.getKeyStates();
		if (0 != (keyState & GameCanvas.FIRE_PRESSED)) {
			if (this.gameCanvas.getContinueSprite().isVisible()) {
				this.gameCanvas.updateStateToPlay();
			} else if (this.gameCanvas.getOverSprite().isVisible()) {
				this.gameCanvas.turnOffGameCanvas();
				this.gameCanvas.getMidlet().getDisplay().setCurrent(this.gameCanvas.getMidlet().getMenuGameCanvas());
				this.gameCanvas.getMidlet().getMenuGameCanvas().startMenuCanvas();
			}
		} else if (0 != (keyState & GameCanvas.LEFT_PRESSED)) {
			this.gameCanvas.getOverSprite().setVisible(false);
			this.gameCanvas.getContinueSprite().setVisible(true);
		} else if (0 != (keyState & GameCanvas.RIGHT_PRESSED)) {
			this.gameCanvas.getOverSprite().setVisible(true);
			this.gameCanvas.getContinueSprite().setVisible(false);
		}
	}

}
