package com.zhongdan.games.sokoban;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MyMIDlet extends MIDlet {

	private Display display;
	private MenuCanvas menuCanvas;
	private MyGameCanvas myGameCanvas;

	public MyMIDlet() {
	}

	protected void startApp() throws MIDletStateChangeException {
		menuCanvas = new MenuCanvas(this);
		myGameCanvas = new MyGameCanvas(this);
		display = Display.getDisplay(this);
		display.setCurrent(menuCanvas);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

	public Display getDisplay() {
		return display;
	}

	public MenuCanvas getMenuCanvas() {
		return menuCanvas;
	}

	public MyGameCanvas getMyGameCanvas() {
		return myGameCanvas;
	}

}
