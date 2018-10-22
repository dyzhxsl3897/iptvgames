package com.company.iptvgames.eatbean;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMIDlet extends MIDlet {

	public Display dis;
	public MainGameCanvas gameCanvas;
	public MenuGameCanvas menuCanvas;
	public boolean menuShow = true;
	public boolean isRunning = true;
	public int gamestatus = 0;// 1 means running, 0 means pause, 2 means failed, 3 means pass, 4 means real pause
	public int fps = 100;
	public long NPCbegin;

	public MainMIDlet() {
		// TODO Auto-generated constructor stub
		dis = Display.getDisplay(this);
		gameCanvas = new MainGameCanvas(this);
		menuCanvas = new MenuGameCanvas(this);
		dis.setCurrent(menuCanvas);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
	}

}
