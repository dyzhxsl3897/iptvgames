package com.zhongdan.games.paopaolong;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMidlet extends MIDlet {

	private Display display;
	private GameCanvas menuCanvas;
	private MyGameCanvas myGameCanvas;

	public MainMidlet() {
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

	public GameCanvas getMenuCanvas() {
		return menuCanvas;
	}

	public MyGameCanvas getMyGameCanvas() {
		return myGameCanvas;
	}

}
