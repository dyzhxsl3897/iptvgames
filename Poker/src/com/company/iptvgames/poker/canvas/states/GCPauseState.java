package com.company.iptvgames.poker.canvas.states;

import javax.microedition.lcdui.game.GameCanvas;

import com.company.iptvgames.poker.GameConst;
import com.company.iptvgames.poker.canvas.MainGameCanvas;

public class GCPauseState implements GCState{

	private MainGameCanvas gameCanvas;
	private boolean showAlert = false;
	
	public GCPauseState(MainGameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}

	public void exitState() {
//		this.gameCanvas.getPauseAlertSprite().setVisible(false);
//		this.gameCanvas.getContinueSprite().setVisible(false);
//		this.gameCanvas.getOverSprite().setVisible(false);
		showAlert = false;
	}

	public void intoState() {
		showAlert = false;
//		this.gameCanvas.getContinueSprite().setPosition(GameConst.GameCanvas.CX_PAUSE, GameConst.GameCanvas.CY_PAUSE);
//		this.gameCanvas.getOverSprite().setPosition(GameConst.GameCanvas.OX_PAUSE, GameConst.GameCanvas.OY_PAUSE);
//		this.gameCanvas.getPauseAlertSprite().setVisible(true);
//		this.gameCanvas.getContinueSprite().setVisible(true);
//		this.gameCanvas.getOverSprite().setVisible(false);
	}
	
	public void setAlert(boolean value){
		try{
			Thread.sleep(200);
		 }catch(InterruptedException ie){
		     ie.printStackTrace();
		 }
		 showAlert = value;
	}
	
	public void keyAction() {
//		if(showAlert){
//			int keyState = this.gameCanvas.getKeyStates();
//			if (0 != (keyState & GameCanvas.FIRE_PRESSED)) {
//				if (this.gameCanvas.getContinueSprite().isVisible()) {
//					this.gameCanvas.updateStateToPlay();
//				} else if (this.gameCanvas.getOverSprite().isVisible()) {
//					this.gameCanvas.turnOffGameCanvas();
//					this.gameCanvas.getMidlet().getDisplay().setCurrent(this.gameCanvas.getMidlet().getMenuGameCanvas());
//					this.gameCanvas.getMidlet().getMenuGameCanvas().startMenuCanvas();
//				}
//			} else if (0 != (keyState & GameCanvas.LEFT_PRESSED)) {
//				this.gameCanvas.getOverSprite().setVisible(false);
//				this.gameCanvas.getContinueSprite().setVisible(true);
//			} else if (0 != (keyState & GameCanvas.RIGHT_PRESSED)) {
//				this.gameCanvas.getOverSprite().setVisible(true);
//				this.gameCanvas.getContinueSprite().setVisible(false);
//			}
//		}	
	}
}
