package com.zhongdan.games.flappybird;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MyMIDlet extends MIDlet {

	private Display display;
	private MyGameCanvas myGameCanvas;

	public MyMIDlet() {
	}

	protected void startApp() throws MIDletStateChangeException {
		myGameCanvas = new MyGameCanvas(this);
		display = Display.getDisplay(this);
		display.setCurrent(myGameCanvas);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

}
