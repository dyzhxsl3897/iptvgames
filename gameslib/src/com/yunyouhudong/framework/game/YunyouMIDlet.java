package com.yunyouhudong.framework.game;

import java.util.Hashtable;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

import com.yunyouhudong.framework.constants.GameProps;

public abstract class YunyouMIDlet extends MIDlet {

	private String gameName;
	private Display display;
	private Hashtable allCanvas = new Hashtable();

	public void init(String gameName) {
		this.gameName = gameName;
		GameProps.initializeProps(this);
		this.display = Display.getDisplay(this);
	}

	public void changeGameCanvas(YunyouGameCanvas newCanvas) {
		Displayable currentCanvas = this.display.getCurrent();
		if (newCanvas != null) {
			if (currentCanvas != null && currentCanvas instanceof YunyouGameCanvas) {
				YunyouGameCanvas canvas = (YunyouGameCanvas) currentCanvas;
				canvas.exitCanvas();
			}
			this.display.setCurrent(newCanvas);
			newCanvas.intoCanvas();
		}
	}

	public void addCanvas(String canvasName, YunyouGameCanvas canvas) {
		allCanvas.put(canvasName, canvas);
	}

	public YunyouGameCanvas getCanvas(String canvasName) {
		if (this.allCanvas.containsKey(canvasName)) {
			return (YunyouGameCanvas) this.allCanvas.get(canvasName);
		}
		return null;
	}

	public Display getDisplay() {
		return display;
	}

	public String getGameName() {
		return gameName;
	}

}
