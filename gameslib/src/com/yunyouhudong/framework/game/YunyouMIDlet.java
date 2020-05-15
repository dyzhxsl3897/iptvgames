package com.yunyouhudong.framework.game;

import javax.microedition.midlet.MIDlet;

import com.yunyouhudong.framework.constants.GameProps;

public abstract class YunyouMIDlet extends MIDlet {

	private String gameName;

	public void init(String gameName) {
		this.gameName = gameName;
		GameProps.initializeProps(this);
	}

	public String getGameName() {
		return gameName;
	}

}
