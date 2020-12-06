package com.game.racing;

import java.io.IOException;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

import com.game.Constants.KeyCode;

/**
 * 
 * @author JoYoNB
 *
 */
public class MenuCanvas extends GameCanvas implements Runnable{
	private final static long DELAY=300;
	public Thread mainThread = new Thread(this);
	public boolean isRunning = false;
	public MainMidlet midlet;
	private Image bgMainImage;
	private Image startImage;
	private Sprite bgMainSprite;
	private Sprite startSprite;
	
	private LayerManager layerManager=new LayerManager();;

	protected MenuCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;

		// 勾勒菜单
		this.setFullScreenMode(true);
		try {
			bgMainImage=Image.createImage("/menu/bg_main.png");
			startImage=Image.createImage("/menu/start.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		bgMainSprite=new Sprite(bgMainImage);
		startSprite=new Sprite(startImage, 133, 84);
		startSprite.setPosition(90, 300);
		layerManager.append(startSprite);
		layerManager.append(bgMainSprite);
		if(!mainThread.isAlive()){
			mainThread.start();
		}
	}

	/**
	 * menu input
	 */
	private void input(int keyCode){
		switch (keyCode) {
		case KeyCode.UP:
			startSprite.setPosition(90, 300);
			break;
		case KeyCode.DOWN:
			startSprite.setPosition(90, 387);
			break;
		case KeyCode.OK:
			this.isRunning=false;
			if(startSprite.getY()==300){
				midlet.dis.setCurrent(midlet.gameCanvas);
				midlet.gameCanvas.initializeGame();
			}else{
				midlet.exit();
			}
		}
	}
	
	protected void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
		input(keyCode);
	}
	protected void keyRepeated(int keyCode) {
		super.keyRepeated(keyCode);
		input(keyCode);
	}
	private void drawScreen(Graphics g) {
		g.setColor(0xffffff);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(0x0000ff);
		layerManager.paint(g, 0, 0);
		flushGraphics();
	}
	public void run() {
		isRunning=true;
		try {
			while (isRunning) {
				startSprite.nextFrame();
				Graphics g=getGraphics();
				drawScreen(g);
				System.gc();
				Thread.currentThread();
				Thread.sleep(DELAY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
