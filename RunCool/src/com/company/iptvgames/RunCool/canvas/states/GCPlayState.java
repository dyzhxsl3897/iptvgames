package com.company.iptvgames.RunCool.canvas.states;

import javax.microedition.lcdui.game.GameCanvas;

import com.company.iptvgames.RunCool.canvas.MainGameCanvas;
import com.company.iptvgames.framework.utils.KeyCode;

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
	
	public void keyAction(int keyCode) {
		//确认键按下
		if (KeyCode.OK.contains(new Integer(keyCode))) {
			//玩家状态为跑时，按键有效，变更为跳跃状态
			if (this.gameCanvas.getRole().isInWalkState()) {
				this.gameCanvas.getRole().updateState(this.gameCanvas.getRole().getJumpState());
			}		
		}
	}
}
