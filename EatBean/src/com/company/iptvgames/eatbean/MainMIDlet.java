package com.company.iptvgames.eatbean;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMIDlet extends MIDlet {

	public Display dis;
	public MainGameCanvas gameCanvas;
	public MenuCanvas menuCanvas;
	public boolean menuShow = true;
	public boolean isRunning = true;
	public int gamestatus = 0;// 1 means running, 0 means pause, 2 means failed, 3 means pass, 4 means real pause
	public int fps = 100;
	public long NPCbegin;

	public MainMIDlet() {
		dis = Display.getDisplay(this);
		menuCanvas = new MenuCanvas(this);
		gameCanvas = new MainGameCanvas(this);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

	protected void startApp() throws MIDletStateChangeException {
		dis.setCurrent(menuCanvas);
	}

}
