package com.company.iptvgames.peiqidown;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.company.iptvgames.peiqidown.canvas.MenuGameCanvas;
import com.company.iptvgames.peiqidown.canvas.gamecanvas.MainGameCanvas;

public class MainMIDlet extends MIDlet {

	private Display display;
	private MainGameCanvas mainGameCanvas;
	private MenuGameCanvas menuGameCanvas;

	public MainMIDlet() {
		display = Display.getDisplay(this);
		display.setCurrent(getMenuGameCanvas());
		getMenuGameCanvas().startMenuCanvas();
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

	public MainGameCanvas getMainGameCanvas() {
		if (this.mainGameCanvas == null) {
			this.mainGameCanvas = new MainGameCanvas(this);
		}
		return mainGameCanvas;
	}

	public MenuGameCanvas getMenuGameCanvas() {
		if (this.menuGameCanvas == null) {
			this.menuGameCanvas = new MenuGameCanvas(this);
		}
		return menuGameCanvas;
	}

}
