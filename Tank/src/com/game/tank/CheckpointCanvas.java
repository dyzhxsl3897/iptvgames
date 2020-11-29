package com.game.tank;


import java.io.IOException;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

import com.game.Constants.KeyCode;

/**
 * 选关
 * @author JoYoNB
 *
 */
public class CheckpointCanvas extends GameCanvas implements Runnable{
	private final static long DELAY=300;
	public Thread mainThread = new Thread(this);
	public boolean isRunning = false;
	public MainMidlet midlet;
	private Image bgMainImage;
	private Sprite bgMainSprite;
	private Image selectedImage;
	private Sprite selectedSprite;
	private Sprite[] numSprites;
	private int h=0;
	private int l=0;
	
	
	private LayerManager layerManager=new LayerManager();;

	protected CheckpointCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;

		// 勾勒菜单
		this.setFullScreenMode(true);
		try {
			bgMainImage=Image.createImage("/xg/bg.png");
			selectedImage=Image.createImage("/xg/xz.png");
			numSprites=new Sprite[15];
			for (int i = 0; i < numSprites.length; i++) {
				Image image=Image.createImage("/xg/"+(i+1)+".png");
				numSprites[i]=new Sprite(image);
				int x=153;
				int y=229;
				if(i<5){
					x+=i*66;
				}else if(i>=5&&i<=9){
					x+=(i-5)*66;
					y=295;
				}else{
					x+=(i-10)*66;
					y=362;
				}
				
				numSprites[i].setPosition(x, y);
				layerManager.append(numSprites[i]);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		bgMainSprite=new Sprite(bgMainImage);
		selectedSprite=new Sprite(selectedImage);
		selectedSprite.setPosition(149, 225);
		layerManager.append(selectedSprite);
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
			if(selectedSprite.getY()>225){
				selectedSprite.move(0, -66);
				h-=1;
			}
			break;
		case KeyCode.DOWN:
			if(selectedSprite.getY()<339){
				selectedSprite.move(0, 66);
				h+=1;
			}
			break;
		case KeyCode.LEFT:
			if(selectedSprite.getX()>149){
				selectedSprite.move(-66,0);
				l-=1;
			}
			break;
		case KeyCode.RIGHT:
			if(selectedSprite.getX()<413){
				selectedSprite.move(66,0);
				l+=1;
			}
			break;
		case KeyCode.OK:
			int level=h*5+l+1;
			
			this.isRunning=false;
			midlet.dis.setCurrent(midlet.gameCanvas);
			midlet.gameCanvas.initializeGame(level);
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