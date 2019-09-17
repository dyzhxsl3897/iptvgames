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
	private Image backImage;
	private Sprite bgMainSprite;
	private Sprite startSprite;
	private Sprite backSprite;
	
	private LayerManager layerManager=new LayerManager();;

	protected MenuCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;

		// 勾勒菜单
		this.setFullScreenMode(true);
		try {
			bgMainImage=Image.createImage("/menu/bg_main.png");
			startImage=Image.createImage("/menu/start.png");
			backImage=Image.createImage("/menu/back.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		bgMainSprite=new Sprite(bgMainImage);
		startSprite=new Sprite(startImage, 133, 84);
		startSprite.setPosition(90, 215);
		backSprite=new Sprite(backImage);
		backSprite.setPosition(586, 3);
		backSprite.setVisible(false);
		layerManager.append(startSprite);
		layerManager.append(backSprite);
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
		case KeyCode.LEFT:
			startSprite.setVisible(true);
			backSprite.setVisible(false);
			break;
		case KeyCode.RIGHT:
			backSprite.setVisible(true);
			startSprite.setVisible(false);
			break;
		case KeyCode.OK:
			this.isRunning=false;
			if(startSprite.isVisible()){
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
