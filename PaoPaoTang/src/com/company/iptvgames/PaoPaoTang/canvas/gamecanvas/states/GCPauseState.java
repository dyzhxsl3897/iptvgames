package com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.states;

import com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.MainGameCanvas;
import com.company.iptvgames.framework.utils.Constants;

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

	public void keyAction(int keyCode) {
		if (keyCode == Constants.KeyCode.OK) {
			if (this.gameCanvas.getContinueSprite().isVisible()) {
				this.gameCanvas.updateStateToPlay();
			} else if (this.gameCanvas.getOverSprite().isVisible()) {
				this.gameCanvas.turnOffGameCanvas();
				this.gameCanvas.getMidlet().getDisplay().setCurrent(this.gameCanvas.getMidlet().getMenuGameCanvas());
				this.gameCanvas.getMidlet().getMenuGameCanvas().startMenuCanvas();
			}
		} else if (keyCode == Constants.KeyCode.LEFT) {
			this.gameCanvas.getOverSprite().setVisible(false);
			this.gameCanvas.getContinueSprite().setVisible(true);
		} else if (keyCode == Constants.KeyCode.RIGHT) {
			this.gameCanvas.getOverSprite().setVisible(true);
			this.gameCanvas.getContinueSprite().setVisible(false);
		}
	}

}
