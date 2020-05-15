package com.yunyouhudong.testgame;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.yunyouhudong.framework.constants.GameProps;

public class MainMIDlet extends MIDlet {

	public MainMIDlet() {
		GameProps.initializeProps(this);

		Display dis = Display.getDisplay(this);
		MainGameCanvas gc = new MainGameCanvas(this);
		dis.setCurrent(gc);
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

}
