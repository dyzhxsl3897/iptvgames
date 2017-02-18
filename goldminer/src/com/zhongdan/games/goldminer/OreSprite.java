package com.zhongdan.games.goldminer;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class OreSprite extends Sprite {

	private int speed;

	private int value;

	public OreSprite(Image arg0, int arg1, int arg2, int arg3, int arg4) {
		super(arg0);
		speed = arg1;
		value = arg2;
		super.setPosition(arg3, arg4);
	}

	public int getSpeed() {
		return speed;
	}

	public int getValue() {
		return value;
	}

}
