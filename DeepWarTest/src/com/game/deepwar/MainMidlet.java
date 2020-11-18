package com.game.deepwar;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainMidlet extends MIDlet {

	public Display dis;
	public MenuCanvas menuCanvas;

	public MainMidlet() {
		dis = Display.getDisplay(this);
		menuCanvas = new MenuCanvas(this);
		dis.setCurrent(menuCanvas);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}
	
	public void exit(){
		try {
			this.destroyApp(true);
			this.notifyDestroyed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
