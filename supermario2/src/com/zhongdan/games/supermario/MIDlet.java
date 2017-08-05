package com.zhongdan.games.supermario;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDletStateChangeException;


public class MIDlet extends javax.microedition.midlet.MIDlet {

	public MIDlet() {
		
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	

	}

	protected void pauseApp() {
	

	}

	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(new MyGameCanvas(this));

	}

}
