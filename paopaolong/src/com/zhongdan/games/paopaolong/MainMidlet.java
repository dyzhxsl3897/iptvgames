package com.zhongdan.games.paopaolong;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMidlet extends MIDlet {

	public MainMidlet() {
	}

	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(new MyGameCanvas(this));
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

}
