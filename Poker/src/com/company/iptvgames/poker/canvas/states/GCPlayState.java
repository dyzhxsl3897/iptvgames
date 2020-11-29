package com.company.iptvgames.poker.canvas.states;

import javax.microedition.lcdui.game.GameCanvas;

import com.company.iptvgames.poker.canvas.MainGameCanvas;

public class GCPlayState implements GCState{
	
	private MainGameCanvas gameCanvas;
	
	public GCPlayState(MainGameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}

	public void exitState() {
	}

	public void intoState() {
		try{
			Thread.sleep(100);
		 }catch(InterruptedException ie){
		     ie.printStackTrace();
		 }
	}
	
	public void setAlert(boolean value){
		
	}
	
	public void finishShow(boolean value){
	}
	
	public void keyAction() {
		int keyState = this.gameCanvas.getKeyStates();
		//ȷ�ϼ�����
//		if (0 != (keyState & GameCanvas.FIRE_PRESSED)) {
//			//���״̬Ϊ��ʱ��������Ч�����Ϊ��Ծ״̬
//			if (this.gameCanvas.getRole().isInWalkState()) {
//				this.gameCanvas.getRole().updateState(this.gameCanvas.getRole().getJumpState());
//			}		
//		}else if(0 != (keyState & GameCanvas.UP_PRESSED)){
//			this.gameCanvas.updateStateToPause();
//		}
	}
}
