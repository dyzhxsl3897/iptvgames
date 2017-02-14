package com.zhongdan.games.tetris;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class MainMidlet extends MIDlet {

	private MainGameCanvas canvas = new MainGameCanvas(false);
	private Display dis = null;

	public MainMidlet() {
	}

	protected void startApp() throws MIDletStateChangeException {
		dis = Display.getDisplay(this);
		dis.setCurrent(canvas);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

}
