package com.company.iptvgames.poker.canvas.states;

public interface GCState {

	public void intoState();

	public void exitState();

	public void keyAction();
	
	public void setAlert(boolean value);
}
