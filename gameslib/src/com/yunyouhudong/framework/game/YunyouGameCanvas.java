package com.yunyouhudong.framework.game;

import javax.microedition.lcdui.game.GameCanvas;

import com.yunyouhudong.framework.resourcemanagement.ResourceManager;

public abstract class YunyouGameCanvas extends GameCanvas {

	private YunyouMIDlet midlet;
	private ResourceManager resourceManager;

	public YunyouGameCanvas(YunyouMIDlet midlet, boolean suppressKeyEvents) {
		super(suppressKeyEvents);
		this.setFullScreenMode(true);
		this.midlet = midlet;
		this.resourceManager = new ResourceManager(this);
	}
	
	public YunyouMIDlet getMidlet() {
		return midlet;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public abstract void intoCanvas();

	public abstract void exitCanvas();

}
