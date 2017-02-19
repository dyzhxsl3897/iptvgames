package com.zhongdan.games.sokoban;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMIDlet extends MIDlet {

	private Display display;
	private MainGameCanvas mainGameCanvas;

	public MainMIDlet() {
	}

	protected void startApp() throws MIDletStateChangeException {
		mainGameCanvas = new MainGameCanvas(this);
		display = Display.getDisplay(this);
		display.setCurrent(mainGameCanvas);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

}
