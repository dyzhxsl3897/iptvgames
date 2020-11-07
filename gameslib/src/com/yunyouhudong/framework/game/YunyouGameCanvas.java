package com.yunyouhudong.framework.game;

import javax.microedition.lcdui.game.GameCanvas;

import com.yunyouhudong.framework.resourcemanagement.ResourceManager;

public abstract class YunyouGameCanvas extends GameCanvas {

	private YunyouMIDlet midlet;
	private ResourceManager resourceManager;
	private YunyouLayerManager layerManager;

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

	public void initalizeLayerManager(int initalLayerLenses) {
		layerManager = new YunyouLayerManager(initalLayerLenses);
	}

	public YunyouLayerManager getLayerManager() {
		if (layerManager == null) {
			throw new NullPointerException("LayerManager is not initialized yet!");
		}
		return layerManager;
	}

	public abstract void intoCanvas();

	public abstract void exitCanvas();

}
