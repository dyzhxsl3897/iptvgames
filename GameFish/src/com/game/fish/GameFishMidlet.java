package com.game.fish;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * 
 * @author JoYoNB
 *
 */
public class GameFishMidlet extends MIDlet {
	public static GameFishMidlet instance;
	private Display display;
	public GameFishMidlet() {
		instance = this;
	}
	public void startApp() {
		try {
			display = Display.getDisplay(this);
			GameFishCanvas gameCanvas = new GameFishCanvas();
			display.setCurrent(gameCanvas);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Display getDisplay() {
		return display;
	}

	public void pauseApp() {
	}

	public void destroyApp(boolean unconditional) {
	}

	public void exit() {
		System.gc();
		instance.destroyApp(true);
		instance.notifyDestroyed();
		instance = null;
	}
}