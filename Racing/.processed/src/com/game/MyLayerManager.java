package com.game;

import javax.microedition.lcdui.game.Layer;
import javax.microedition.lcdui.game.LayerManager;

public class MyLayerManager extends LayerManager{
	private int index;
	public void append(Layer l) {
		super.append(l);
		index++;
	}
	public int getIndex(Layer l){
		for (int i = 0; i < index; i++) {
			if(l==this.getLayerAt(i)){
				return i;
			}
		}
		return 0;
	}
}
