package com.company.iptvgames.PPTangTest;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.company.iptvgames.PPTangTest.canvas.MenuGameCanvas;
import com.company.iptvgames.PPTangTest.canvas.gamecanvas.MainGameCanvas;

public class MainMIDlet extends MIDlet {

	private Display display;
	private MenuGameCanvas menuGameCanvas;
	private MainGameCanvas mainGameCanvas;
	
	public MainMIDlet() {
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

	protected void startApp() throws MIDletStateChangeException {
		display = Display.getDisplay(this);
		display.setCurrent(getMenuGameCanvas());
		getMenuGameCanvas().startMenuCanvas();
	}

	public MenuGameCanvas getMenuGameCanvas() {
		if (this.menuGameCanvas == null) {
			this.menuGameCanvas = new MenuGameCanvas(this);
		}
		return menuGameCanvas;
	}
	
	public MainGameCanvas getMainGameCanvas() {
		if (this.mainGameCanvas == null) {
			this.mainGameCanvas = new MainGameCanvas(this);
		}
		return mainGameCanvas;
	}
	
	public Display getDisplay() {
		return display;
	}

}
