package com.zhongdan.games.tohellwithjohnny;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class Man extends MIDlet {

	private Game game;
	public Display display;

	public Man() {
		game = new Game(this);
		display = Display.getDisplay(this);
	}

	public void startApp() {
		game.startGame();
	}

	public void pauseApp() {
	}

	public void destroyApp(boolean unconditional) {
	}

	public void closeman() {
		destroyApp(false);
		notifyDestroyed();
	}

}
