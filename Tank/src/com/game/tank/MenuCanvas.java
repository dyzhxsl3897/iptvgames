package com.game.tank;


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
	private int selected=0;
//	private Image bgMainImage;
//	private Image selectedImage;
	private Sprite bgMainSprite;
	private Sprite selectedSprite;
	
	private LayerManager layerManager=new LayerManager();;

	protected MenuCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;

		// 勾勒菜单
		this.setFullScreenMode(true);
		try {
			Image bgMainImage=Image.createImage("/menu/bg_main.png");
			Image selectedImage=Image.createImage("/menu/selected.png");
			bgMainSprite=new Sprite(bgMainImage);
			selectedSprite=new Sprite(selectedImage, 147, 48);
			selectedSprite.setPosition(248, 424);
			layerManager.append(selectedSprite);
			layerManager.append(bgMainSprite);
			if(!mainThread.isAlive()){
				mainThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * menu input
	 */
	private void input(int keyCode){
		switch (keyCode) {
		case KeyCode.OK:
			if(selected==0){
				this.isRunning=false;
				midlet.dis.setCurrent(midlet.checkpointCanvas);
			}else{
				midlet.exit();
			}
			break;
		case KeyCode.UP:
			if(selected==0){
				selected=1;
			}else{
				selected=0;
			}
			break;
		case KeyCode.DOWN:
			if(selected==0){
				selected=1;
			}else{
				selected=0;
			}
			break;
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
				if(selected==0){
					selectedSprite.setPosition(248, 424);
				}else{
					selectedSprite.setPosition(248, 473);
				}
				selectedSprite.nextFrame();
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