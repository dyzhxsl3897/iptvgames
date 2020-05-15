package com.yunyouhudong.testgame;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.midlet.MIDlet;

import com.yunyouhudong.framework.constants.GameProps;
import com.yunyouhudong.framework.http.ApiFacade;

public class MainGameCanvas extends GameCanvas {

	private MIDlet midlet;
	private Graphics graphics;
	private int line = 0;
	private int start = 20;
	private int height = 15;

	protected MainGameCanvas(MIDlet midlet) {
		super(false);
		this.setFullScreenMode(true);
		this.graphics = this.getGraphics();

		String userId = GameProps.getProperty("userid");
		drawString(userId);

		int loginTimes = ApiFacade.getUserLoginTimes(userId);
		drawString(String.valueOf(loginTimes));
	}

	private void drawString(String string) {
		this.graphics.drawString(string, start, nextLine(), 0);
		this.flushGraphics();
	}

	private int nextLine() {
		return line++ * height;
	}

}
