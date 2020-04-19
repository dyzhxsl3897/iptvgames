package com.company.iptvgames.RunCool.obstacles;

import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.company.iptvgames.RunCool.GameConst;
import com.company.iptvgames.RunCool.resources.ImageRes;
import com.company.iptvgames.framework.utils.RandomUtil;

public class ObstacleObject {

	private Sprite obstactle;
	
	private int posX;
	private int posY;
	private int width;
	private int height;
	
	private boolean isFinished;//是否已计算过碰撞
	
	public ObstacleObject() {
		int id = RandomUtil.nextInt(GameConst.Obstacle.TYPENUMBER);
		this.obstactle = new Sprite(ImageRes.getInstance().getImage("ObstacleImg"+ id));
		isFinished = false;
		width = obstactle.getWidth();
		height = obstactle.getHeight();
	}
	
	public void addToScreen(LayerManager layerManager) {
		layerManager.insert(obstactle, GameConst.Obstacle.LAYER_obstacle);
	}

	public void removeFromScreen(LayerManager layerManager) {
		layerManager.remove(obstactle);
	}
	
	public Sprite getObstacle() {
		return obstactle;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
	public void setPosition(int x, int y) {
		this.posX = x;
		this.posY = y;
		this.obstactle.setPosition(x, y);
	}
	
	public boolean getFinish() {
		return isFinished;
	}
	
	public void setFinish(boolean value) {
		isFinished = value;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void move(int x, int y) {
		setPosition(this.posX + x, this.posY + y);
	}
}
