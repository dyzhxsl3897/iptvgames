package com.game.tank;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * 地图元素
 * @author JoYoNB
 *
 */
public class Obstacle extends Sprite{

	/** 障碍物分类，1墙,2石头，3水，4草 */
	private int type=1;
	public Obstacle(Image image) {
		super(image);
		// TODO Auto-generated constructor stub
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
