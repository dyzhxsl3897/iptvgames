package com.zhongdan.games.tohellwithjohnny;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MyMIDlet extends MIDlet {

	private Display display;
	private MyGameCanvas mainGameCanvas;

	public MyMIDlet() {
	}

	protected void startApp() throws MIDletStateChangeException {
		mainGameCanvas = new MyGameCanvas(this);
		display = Display.getDisplay(this);
		display.setCurrent(mainGameCanvas);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

}
