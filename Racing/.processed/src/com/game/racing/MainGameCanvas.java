package com.game.racing;

import java.io.IOException;
import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Layer;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.midlet.MIDletStateChangeException;

import com.game.MyLayerManager;
import com.game.sprite.Map;
import com.game.sprite.Motor;
import com.game.sprite.MySprite;

public class MainGameCanvas extends GameCanvas implements Runnable {
	private final static long DELAY=80;
	public MainMidlet midlet;
	
	public Thread mainThread = new Thread(this);
	public MyLayerManager layerManager = new MyLayerManager();// 图层管理
	public boolean isRunning = true;
	public boolean pausing = false;
	public boolean finish=false;
	int lastKey=0;
	
	
	//menu
	private Image pauseImage;
	private Sprite pauseMenu;
	private Image goOnImage;
	private Sprite goOnSprite;
	private Image quitImage;
	private Sprite quitSprite;
	
	private static final int MILEAGE = 50000;
	
	//plyer
	private Image[] playerImgs;
	private Motor player;
	//AI
	private Image[] aiImages0;
	private Image[] aiImages1;
	private Image[] aiImages2;
	private Motor[] ais;
	
	//map
	private Image mapImage;
	Map[] maps;
	int mapStep=6;//地图滚动速度
	//障碍
	private Image obstacleImage;
	private MySprite obstacle;
	private static final int[] obYs={182,214,248,283};
	//汽油
	private Image fuelImage;
	private MySprite fuel;
	private static final int[] fuelYs={158,188,220,256};
	
	//�?
	private Image lightImage;
	private Sprite light;
	private Image[] greenLightImages;
	private Sprite[] greenLights;
	private int time; 
	
	//终点
	private Image filishImage;
	private Sprite filishSprite;
	
	
	private Image winImage;
	private Sprite winSprite;
	private Image winSelBackImage;
	private Sprite winSelBackSprite;
	private Image loseImage;
	private Sprite loseSprite;
	private Image loseSelBackImage;
	private Sprite loseSelBackSprite;
	
	//面板
	private Image mcImage;
	private Sprite mcSprite;
	private Image jdImage;
	private Sprite jdSprite;
	private Image percentImage;
	private Sprite percentSprite;
	private Image slashImage;
	private Sprite slashSprite;
	private Image numberImage;
	private Sprite totleSprite;
	private Sprite rankSprite;
	private Sprite percent1Sprite;
	private Sprite percent2Sprite;
	private Sprite percent3Sprite;
	private int rank=1;
	private Random rdm=new Random();
	
	protected MainGameCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
	}

	public void initializeGame() {
		// TODO Auto-generated method stub'
		isRunning=true;
		reloadImages();
		if(!mainThread.isAlive()){
			mainThread.start();
		}

		// new func 1

		// new func 2
	}

	private void reloadMusics() {
		// TODO Auto-generated method stub

	}

	private void reloadImages() {
		try {
			goOnImage=Image.createImage("/goon.png");
			goOnSprite=new Sprite(goOnImage);
			goOnSprite.setVisible(false);
			goOnSprite.setPosition(212, 124);
			layerManager.append(goOnSprite);
			
			quitImage=Image.createImage("/quit.png");
			quitSprite=new Sprite(quitImage);
			quitSprite.setVisible(false);
			quitSprite.setPosition(315, 124);
			layerManager.append(quitSprite);
			
			pauseImage=Image.createImage("/pause.png");
			pauseMenu=new Sprite(pauseImage);
			pauseMenu.setVisible(false);
			layerManager.append(pauseMenu);
			
			lightImage=Image.createImage("/light.png");
			light=new Sprite(lightImage);
			light.setPosition(243, 162);
			greenLightImages=new Image[]{Image.createImage("/greenlight_0.png"),Image.createImage("/greenlight_1.png"),Image.createImage("/greenlight_2.png")};
			greenLights=new Sprite[3];
			for (int i = 0; i < greenLightImages.length; i++) {
				greenLights[i]=new Sprite(greenLightImages[i]);
				greenLights[i].setPosition(243, 162);
				greenLights[i].setVisible(false);
				layerManager.append(greenLights[i]);
			}
			layerManager.append(light);
			playerImgs=new Image[]{Image.createImage("/run/player.png"),Image.createImage("/jump/player.png")};
			player=new Motor(playerImgs);
			player.setInterval(10);
			player.setPosition(120, 0);
			player.setLine(3);
			player.defineCollisionRectangle(0, 120, 130, 9);
			layerManager.append(player);
			
			
			aiImages0=new Image[]{Image.createImage("/run/ai-0.png"),Image.createImage("/jump/ai-0.png")};
			aiImages1=new Image[]{Image.createImage("/run/ai-1.png"),Image.createImage("/jump/ai-1.png")};
			aiImages2=new Image[]{Image.createImage("/run/ai-2.png"),Image.createImage("/jump/ai-2.png")};
			Motor ai0=new Motor(aiImages0);
			Motor ai1=new Motor(aiImages1);
			Motor ai2=new Motor(aiImages2);
			ais=new Motor[]{ai0,ai1,ai2};

			for (int i = 0; i < ais.length; i++) {
				ais[i].setPosition(120+(3-i)*20, 0);
				ais[i].setLine(i);
				ais[i].setInterval(rdm.nextInt(3)+10);
				ais[i].defineCollisionRectangle(0, 120, 130, 9);
				layerManager.append(ais[ais.length-i-1]);
			}
			
			fuelImage= Image.createImage("/fuel.png");
			fuel=new MySprite(fuelImage);
			fuel.setPositionYs(fuelYs);
			fuel.defineCollisionRectangle(0, 50, 72, 21);
			fuel.setPosition(rdm.nextInt(150)+640, 0);
			fuel.setLine(rdm.nextInt(4));
			layerManager.append(fuel);
			
			obstacleImage=Image.createImage("/obstacle.png");
			obstacle=new MySprite(obstacleImage);
			obstacle.setPositionYs(obYs);
			obstacle.setPosition(rdm.nextInt(200)+640, 0);
			obstacle.setLine(rdm.nextInt(4));
			layerManager.append(obstacle);
			
			filishImage=Image.createImage("/finish.png");
			filishSprite=new Sprite(filishImage);
			filishSprite.setPosition(MILEAGE, 210);
			layerManager.append(filishSprite);
			
			winSelBackImage=Image.createImage("/win-return.png");
			winSelBackSprite=new Sprite(winSelBackImage);
			winSelBackSprite.setPosition(247, 247);
			winSelBackSprite.setVisible(false);
			layerManager.append(winSelBackSprite);
			winImage=Image.createImage("/win.png");
			winSprite=new Sprite(winImage);
			winSprite.setVisible(false);
			layerManager.append(winSprite);
			
			loseSelBackImage=Image.createImage("/lose-return.png");
			loseSelBackSprite=new Sprite(loseSelBackImage);
			loseSelBackSprite.setPosition(247, 247);
			loseSelBackSprite.setVisible(false);
			layerManager.append(loseSelBackSprite);
			loseImage=Image.createImage("/lose.png");
			loseSprite=new Sprite(loseImage);
			loseSprite.setVisible(false);
			layerManager.append(loseSprite);
			
			
			mcImage=Image.createImage("/mc.png");
			mcSprite=new Sprite(mcImage);
			mcSprite.setPosition(10, 11);
			layerManager.append(mcSprite);
//			
			jdImage=Image.createImage("/jd.png");
			jdSprite=new Sprite(jdImage);
			jdSprite.setPosition(9, 34);
			layerManager.append(jdSprite);
			
			percentImage=Image.createImage("/percent.png");
			percentSprite=new Sprite(percentImage);
			percentSprite.setPosition(97, 35);
			layerManager.append(percentSprite);
			
			slashImage=Image.createImage("/slash.png");
			slashSprite=new Sprite(slashImage);
			slashSprite.setPosition(81, 10);
			layerManager.append(slashSprite);
			
			numberImage=Image.createImage("/number.png");
			rankSprite=new Sprite(numberImage, 14, 18);
			rankSprite.setPosition(70, 12);
			rankSprite.setFrame(1);
			layerManager.append(rankSprite);
			totleSprite=new Sprite(numberImage, 14, 18);
			totleSprite.setPosition(91, 12);
			totleSprite.setFrame(4);
			layerManager.append(totleSprite);
			percent1Sprite=new Sprite(numberImage, 14, 18);
			percent1Sprite.setPosition(54, 35);
			percent1Sprite.setVisible(false);
			layerManager.append(percent1Sprite);
			percent2Sprite=new Sprite(numberImage, 14, 18);
			percent2Sprite.setPosition(68, 35);
			percent2Sprite.setVisible(false);
			layerManager.append(percent2Sprite);
			percent3Sprite=new Sprite(numberImage, 14, 18);
			percent3Sprite.setPosition(82, 35);
			percent3Sprite.setFrame(0);
			layerManager.append(percent3Sprite);
			
			maps=new Map[3];
			mapImage=Image.createImage("/bg.jpg");
			maps[0]=new Map(mapImage);
			maps[1]=new Map(mapImage);
			maps[2]=new Map(mapImage);
			maps[0].setPosition(0, 0);
			maps[1].setPosition(640, 0);
			maps[2].setPosition(1280, 0);
			layerManager.append(maps[0]);
			layerManager.append(maps[1]);
			layerManager.append(maps[2]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		try {
			Graphics g=getGraphics();
			while (isRunning) {
				if(pausing||finish){
					input();
					drawScreen(g);
					System.gc();
					Thread.currentThread();
					Thread.sleep(DELAY);
					continue;
				}
				if(light()){
					mapRolling();
					motorRun();
					input();
				}
				drawScreen(g);
				Thread.currentThread();
				Thread.sleep(DELAY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void drawScreen(Graphics g) {
		g.setColor(0xffffff);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(0x0000ff);
		layerManager.setViewWindow(0, 0, 640,360);
		layerManager.paint(g, 0, 0);
//		g.drawString(aaa+"",10,10,0);
		flushGraphics();
	}
	private void pause(){
		pausing=!pausing;
		if(pausing){
			pauseMenu.setVisible(true);
			goOnSprite.setVisible(true);
		}else{
			pauseMenu.setVisible(false);
			goOnSprite.setVisible(false);
			quitSprite.setVisible(false);
		}
	}
	private void mapRolling(){
		switch (player.getInterval()) {
		case 5:
			mapStep=25;
			break;
		case 10:
			mapStep=15;
			break;
		case 15:
			mapStep=10;
			break;
		default:
			break;
		}
		
		for (int i = 0; i < maps.length; i++) {
			maps[i].move(-mapStep, 0);
			filishSprite.move(-mapStep, 0);
			if(maps[i].getX()<-640){
				maps[i].setPosition(maps[i==0?2:i-1].getX()+(i==0?640-mapStep:640), 0);
			}
		}
		fuel.move(-mapStep, 0);
		obstacle.move(-mapStep, 0);
	}
	private void motorRun(){
		//排序
		if(!player.isJump()&&!ais[0].isJump()&&!ais[1].isJump()&&!ais[2].isJump()){
			Layer[] arr={player,ais[0],ais[1],ais[2],fuel,obstacle};
			quickSort(arr, 0, 5);
			for (int i = 0; i < arr.length; i++) {
				layerManager.append(arr[i]);
			}
			layerManager.append(filishSprite);
			layerManager.append(maps[0]);
			layerManager.append(maps[1]);
			layerManager.append(maps[2]);
		}
		player.nextFrame();
		for (int i = 0; i < ais.length; i++) {
			ais[i].nextFrame();
			ais[i].move(player.getInterval()-ais[i].getInterval(), 0);
		}
		//加油
		if(player.collidesWith(fuel, true)){
			player.speedUp();
			fuelSetPosition();
		}
		//障碍刷新
		if(obstacle.getX()<0){
			obstacleSetPosition();
		}
		
		int s=MILEAGE-filishSprite.getX();
		int t=MILEAGE-120;
		int p=s*100/t;
		int p1=p/100;
		int p2=p/10-p1*10;
		int p3=p-p1*100-p2*10;
		if(p1==0){
			percent1Sprite.setVisible(false);
			if(p2==0){
				percent2Sprite.setVisible(false);
			}else{
				percent2Sprite.setVisible(true);
			}
		}else{
			percent1Sprite.setVisible(true);
			percent1Sprite.setFrame(p1);
		}
		percent2Sprite.setFrame(p2);
		percent3Sprite.setFrame(p3);
		
		//到达终点
		if(player.collidesWith(filishSprite, true)){
			winSprite.setVisible(true);
			winSelBackSprite.setVisible(true);
			percent1Sprite.setVisible(true);
			percent1Sprite.setFrame(1);
			percent2Sprite.setFrame(0);
			percent3Sprite.setFrame(0);
			rankSprite.setFrame(1);
			finish=true;
			return;
		}
		//撞墙
		if(player.getLine()==obstacle.getLine()&&player.collidesWith(obstacle, true)){
			player.slowDown();
		}
		rank=1;
		for (int i = 0; i < ais.length; i++) {
			//与AI碰撞
			if(player.collidesWith(ais[i], true)&&!player.isJump()&&!ais[i].isJump()){
				player.slowDown();
				ais[i].slowDown();
			}
			//AI加油
			if(ais[i].collidesWith(fuel, true)){
				ais[i].speedUp();
				fuelSetPosition();
			}
			//AI跳跃
			if((ais[i].getY()+127)>=(obstacle.getY()+24)&&(ais[i].getY()+127)<=(obstacle.getY()+50)){
				if(obstacle.getX()-ais[i].getX()<155&&obstacle.getX()-ais[i].getX()>20){
					if(rdm.nextInt(2)!=0){
						ais[i].jump();
					}
				}
			}
			//AI撞墙
			if(ais[i].getLine()==obstacle.getLine()&&ais[i].collidesWith(obstacle, true)){
				ais[i].slowDown();
			}
			
			if(ais[i].getX()<-200){
				ais[i].speedUp();
			} 
			
			//AI到达终点
			if(ais[i].collidesWith(filishSprite, true)){
				loseSprite.setVisible(true);
				loseSelBackSprite.setVisible(true);
				finish=true;
				return;
			}
			//AI强制减�?
			if(ais[i].getX()>1280){
				ais[i].slowDown();
			}
			
			//计算排名
			if(player.getX()<ais[i].getX()){
				rank++;
			}
		}
		if(rank<1)rank=1;
		if(rank>4)rank=4;
		rankSprite.setFrame(rank);
		if(fuel.getX()<-72){
			fuelSetPosition();
		}
	}
	public void quickSort(Layer array[], int low, int high) {// 传入low=0，high=array.length-1;
		int pivot, p_pos, i;// pivot->位索�?p_pos->轴�?�?
		Layer t;
		if (low < high) {
			p_pos = low;
			pivot = array[p_pos].getY()+array[p_pos].getHeight();
			for (i = low + 1; i <= high; i++)
				if ((array[i].getY()+array[i].getHeight()) > pivot) {
					p_pos++;
					t = array[p_pos];
					array[p_pos] = array[i];
					array[i] = t;
				}
			t = array[low];
			array[low] = array[p_pos];
			array[p_pos] = t;
			// 分�?治之
			quickSort(array, low, p_pos - 1);// 排序左半部分
			quickSort(array, p_pos + 1, high);// 排序右半部分
		}
	}
	private void fuelSetPosition(){
		fuel.setPosition(rdm.nextInt(300)+640, 0);
		int tmp=rdm.nextInt(6);
		fuel.setLine(tmp>3?3:tmp);
	}
	private void obstacleSetPosition(){
		obstacle.setPosition(rdm.nextInt(400)+640, 0);
		int tmp=rdm.nextInt(7);
		obstacle.setLine(tmp>3?tmp-4:tmp);
	}
	private boolean light(){
		if(!light.isVisible()){
			return true;
		}
		time++;
		boolean flag=false;
		if(time==10){
			for (int i = 0; i < greenLights.length; i++) {
				if(!greenLights[i].isVisible()){
					greenLights[i].setVisible(true);
					time=0;
					return false;
				}
			}
			if(greenLights[2].isVisible()){
				flag=true;
				for (int j = 0; j < greenLights.length; j++) {
					greenLights[j].setVisible(false);
				}
				light.setVisible(false);
			}
		}
		return flag;
	}
	private void input() {
		int keyStates=getKeyStates();
		
		if(keyStates==lastKey)return;
		lastKey=keyStates;
		if(pausing){
			if(keyStates==4){//�?
				goOnSprite.setVisible(true);
				quitSprite.setVisible(false);
			}else if(keyStates==32){//�?
				quitSprite.setVisible(true);
				goOnSprite.setVisible(false);
			}else if(keyStates==256){
				if(quitSprite.isVisible()){
					isRunning=false;
					try {
						midlet.startApp();
					} catch (MIDletStateChangeException e) {
						e.printStackTrace();
					}
				}else{
					pause();
				}
			}else if(keyStates==512){
				pause();
			}
		}else if(finish){
			if(keyStates==256){
				isRunning=false;
				try {
					midlet.startApp();
				} catch (MIDletStateChangeException e) {
					e.printStackTrace();
				}
			}
		}else{
			if(keyStates==2){//�?
				player.setLine(player.getLine()>0?player.getLine()-1:0);
			}else if(keyStates==64){//�?
				player.setLine(player.getLine()<3?player.getLine()+1:3);
			}else if(keyStates==256){//�?
				player.jump();
			}else if(keyStates==512){
				pause();
			}
		}
	}
}
