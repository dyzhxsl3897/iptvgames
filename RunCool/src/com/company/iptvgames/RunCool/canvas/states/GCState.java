package com.company.iptvgames.RunCool.canvas.states;

public interface GCState {

	public void intoState();

	public void exitState();

	public void keyAction(int keyCode);
	
	public void setAlert(boolean value);
}
