package com.company.iptvgames.RunCool.canvas.states;

import javax.microedition.lcdui.game.GameCanvas;

import com.company.iptvgames.RunCool.GameConst;
import com.company.iptvgames.RunCool.canvas.MainGameCanvas;
import com.company.iptvgames.framework.utils.KeyCode;

public class GCPauseState implements GCState{

	private MainGameCanvas gameCanvas;
	private boolean showAlert = false;
	
	public GCPauseState(MainGameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}

	public void exitState() {
		this.gameCanvas.getPauseAlertSprite().setVisible(false);
		this.gameCanvas.getContinueSprite().setVisible(false);
		this.gameCanvas.getOverSprite().setVisible(false);
		showAlert = false;
	}

	public void intoState() {
		showAlert = false;
		this.gameCanvas.getContinueSprite().setPosition(GameConst.GameCanvas.CX_PAUSE, GameConst.GameCanvas.CY_PAUSE);
		this.gameCanvas.getOverSprite().setPosition(GameConst.GameCanvas.OX_PAUSE, GameConst.GameCanvas.OY_PAUSE);
		this.gameCanvas.getPauseAlertSprite().setVisible(true);
		this.gameCanvas.getContinueSprite().setVisible(true);
		this.gameCanvas.getOverSprite().setVisible(false);
	}
	
	public void setAlert(boolean value){
		try{
			Thread.sleep(200);
		 }catch(InterruptedException ie){
		     ie.printStackTrace();
		 }
		 showAlert = value;
	}
	
	public void keyAction(int keyCode) {
		if(showAlert){
			if (KeyCode.OK.contains(new Integer(keyCode))) {
				if (this.gameCanvas.getContinueSprite().isVisible()) {
					this.gameCanvas.updateStateToPlay();
				} else if (this.gameCanvas.getOverSprite().isVisible()) {
					this.gameCanvas.turnOffGameCanvas();
					this.gameCanvas.getMidlet().getDisplay().setCurrent(this.gameCanvas.getMidlet().getMenuGameCanvas());
					this.gameCanvas.getMidlet().getMenuGameCanvas().startMenuCanvas();
				}
			} else if (KeyCode.LEFT.contains(new Integer(keyCode))) {
				this.gameCanvas.getOverSprite().setVisible(false);
				this.gameCanvas.getContinueSprite().setVisible(true);
			} else if (KeyCode.RIGHT.contains(new Integer(keyCode))) {
				this.gameCanvas.getOverSprite().setVisible(true);
				this.gameCanvas.getContinueSprite().setVisible(false);
			}
		}	
	}
}
