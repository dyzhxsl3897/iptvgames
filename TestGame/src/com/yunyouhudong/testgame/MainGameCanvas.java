package com.yunyouhudong.testgame;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.midlet.MIDlet;

public class MainGameCanvas extends GameCanvas {

	private MIDlet midlet;
	private Graphics graphics;
	private int line = 0;
	private int height = 15;

	protected MainGameCanvas(MIDlet midlet) {
		super(false);
		this.setFullScreenMode(true);
		this.graphics = this.getGraphics();

		drawString("test");
		drawString("testabc");
	}

	private void drawString(String string) {
		this.graphics.drawString(string, 0, nextLine(), 0);
		this.flushGraphics();
	}

	private int nextLine() {
		return line++ * height;
	}

}
