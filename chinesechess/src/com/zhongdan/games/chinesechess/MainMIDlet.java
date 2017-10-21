package com.zhongdan.games.chinesechess;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMIDlet extends MIDlet {

	private Display display;
	private MenuCanvas menuCanvas;
	private MainGameCanvas mainGameCanvas;

	public MainMIDlet() {
		menuCanvas = new MenuCanvas(this);
		mainGameCanvas = new MainGameCanvas(this);
		display = Display.getDisplay(this);
		display.setCurrent(menuCanvas);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

	protected void startApp() throws MIDletStateChangeException {
	}

	public Display getDisplay() {
		return display;
	}

	public MenuCanvas getMenuCanvas() {
		return menuCanvas;
	}

	public MainGameCanvas getMainGameCanvas() {
		return mainGameCanvas;
	}
}
