package com.yunyouhudong.games.wuziqi;

import javax.microedition.midlet.MIDletStateChangeException;

import com.yunyouhudong.framework.game.YunyouMIDlet;
import com.yunyouhudong.games.wuziqi.menu.MenuCanvas;

public class MainMIDlet extends YunyouMIDlet {

	public MainMIDlet() {
		this.init("wuziqi");
		this.addCanvas("menuCanvas", new MenuCanvas(this, false));
		this.changeGameCanvas(this.getCanvas("menuCanvas"));
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

	protected void startApp() throws MIDletStateChangeException {
	}

}
