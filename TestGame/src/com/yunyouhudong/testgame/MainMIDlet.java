package com.yunyouhudong.testgame;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDletStateChangeException;

import com.yunyouhudong.framework.game.YunyouMIDlet;

public class MainMIDlet extends YunyouMIDlet {

	public MainMIDlet() {
		this.init("TestGame");

		Display dis = Display.getDisplay(this);
		MainGameCanvas gc = new MainGameCanvas(this, false);
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
