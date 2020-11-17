package com.company.iptvgames.poker.canvas.states;

import javax.microedition.lcdui.game.GameCanvas;

import com.company.iptvgames.poker.GameConst;
import com.company.iptvgames.poker.canvas.MainGameCanvas;

public class GCDeadState implements GCState{

	private MainGameCanvas gameCanvas;
	private boolean showAlert = false;

	public GCDeadState(MainGameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}

	public void exitState() {
//		this.gameCanvas.getFailAlertSprite().setVisible(false);
//		this.gameCanvas.getContinueSprite().setVisible(false);
//		this.gameCanvas.getOverSprite().setVisible(false);
		showAlert = false;
	}

	public void intoState() {
		showAlert = false;
		//��ɫ�˳���Ϸ����
//		this.gameCanvas.startToDrop();
//		
//		//��ʾ����alert
//		this.gameCanvas.getContinueSprite().setPosition(GameConst.GameCanvas.CX_FINISH, GameConst.GameCanvas.CY_FINISH);
//		this.gameCanvas.getOverSprite().setPosition(GameConst.GameCanvas.OX_FINISH, GameConst.GameCanvas.OY_FINISH);
//		this.gameCanvas.getFailAlertSprite().setVisible(true);
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
	
	public void keyAction(){
		if(showAlert){	
//			int keyState = this.gameCanvas.getKeyStates();
//			if (0 != (keyState & GameCanvas.FIRE_PRESSED)) {
//				if (this.gameCanvas.getContinueSprite().isVisible()) {
//					this.gameCanvas.initalizeGame(); 
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
		}
	}
}
