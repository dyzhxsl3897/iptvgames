package com.zhongdan.games.tetris;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMidlet extends MIDlet {

	public MainMidlet() {
		Display.getDisplay(this).setCurrent(new MyGameCanvas(this));
	}

	protected void startApp() throws MIDletStateChangeException {
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

}
