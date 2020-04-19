package com.company.iptvgames.RunCool;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.company.iptvgames.RunCool.canvas.MenuGameCanvas;
import com.company.iptvgames.RunCool.canvas.MainGameCanvas;

public class MainMIDlet extends MIDlet {

	private Display display;
	private MenuGameCanvas menuGameCanvas;
	private MainGameCanvas mainGameCanvas;
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
		
	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
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
