package com.game.tank;

import java.io.IOException;
import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Layer;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.midlet.MIDletStateChangeException;

import com.game.Constants.KeyCode;


public class MainGameCanvas extends GameCanvas implements Runnable {
	private final static long DELAY=50;
	public MainMidlet midlet;
	
	public Thread mainThread = new Thread(this);
	public LayerManager layerManager;// 图层管理
	public boolean isRunning = true;
	public boolean pausing = false;
	
	int step=0;
	int cx=0; 
	int level;
	//panel
	private Image bgImage;
	private Sprite bgSprite;
	
	//敌人数量
	private Image countImage;
	private Sprite countSprite;
	//我方坦克数
	private int myCount=5;
	
	//地图元素
	private Obstacle sprite[][]=new Obstacle[24][26];
	private int[][] level_map;
	private Image stImage;
	private Image qiangImage;
	private Image shuiImage;
	private Image cdImage;
	private Image lyImage;
	private Sprite lySprite;
	private Image slyImage;
	
	//tank
	private Image tankImage_s;
	private Image tankImage_x;
	private Image tankImage_z;
	private Image tankImage_y;
	private Tank tankSprite;
	private Image bhImage;
	private Sprite bhSprite;
	
	//敌方坦克
	private int once;
	private int count;
	private Image tankImage0_s;
	private Image tankImage0_x;
	private Image tankImage0_z;
	private Image tankImage0_y;
	
	private Image tankImage1_s;
	private Image tankImage1_x;
	private Image tankImage1_z;
	private Image tankImage1_y;
	
	private Image tankImage2_s;
	private Image tankImage2_x;
	private Image tankImage2_z;
	private Image tankImage2_y;
	
	private Tank[] tankSprites;
	//出现
	private Image cxImage;
	
	
	
	
	//子弹
	private Image zd_s;
	private Image zd_x;
	private Image zd_z;
	private Image zd_y;
	private Image zdbz;
	private Zd[] zdSprites;
	
	Random rdm=new Random();
	
	protected MainGameCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
	}

	/**
	 * 初始化游戏
	 * @param level 关卡
	 */
	public void initializeGame(int level) {
		this.level=level;
		// TODO Auto-generated method stub'
		isRunning=true;
		cleanUpBackground();
		reloadImages(level);
		if(!mainThread.isAlive()){
			mainThread.start();
		}
		// new func 1

		// new func 2
	}

	private void reloadMusics() {
		// TODO Auto-generated method stub

	}

	private void reloadImages(int level) {
		once=5;
		count=8;
		level_map=Position.LEVELS[level-1];
		try {
			zd_s=Image.createImage("/zd/zd_s.png");
			zd_x=Image.createImage("/zd/zd_x.png");
			zd_z=Image.createImage("/zd/zd_z.png");
			zd_y=Image.createImage("/zd/zd_y.png");
			zdbz=Image.createImage("/zd/zdbz.png");
			zdSprites=new Zd[150];
			for (int i = 0; i < zdSprites.length; i++) {
				zdSprites[i]=new Zd(zd_s,zd_x,zd_z,zd_y);
				zdSprites[i].setBzImage(zdbz);
				zdSprites[i].setPosition(183, 12);
				layerManager.append(zdSprites[i]);
			}
			//老鹰
			lyImage=Image.createImage("/game/ly.png");
			slyImage=Image.createImage("/game/sly.png");
			lySprite=new Sprite(lyImage);
			lySprite.setPosition(138+12*14, 12+22*14);
			layerManager.append(lySprite);
			//地图元素
			qiangImage=Image.createImage("/game/qiang.png");
			stImage=Image.createImage("/game/st.png");
			shuiImage=Image.createImage("/game/shui.png");
			cdImage=Image.createImage("/game/cd.png");
			for (int i = 0; i < sprite.length; i++) {
				for (int j = 0; j < sprite[0].length; j++) {
					switch (level_map[i][j]) {
					case 1:
						sprite[i][j]=new Obstacle(qiangImage);
						sprite[i][j].setType(1);
						sprite[i][j].setPosition(138+j*14, 12+i*14);
						break;
					case 2:
						sprite[i][j]=new Obstacle(stImage);
						sprite[i][j].setType(2);
						sprite[i][j].setPosition(138+j*14, 12+i*14);
						break;
					case 3:
						sprite[i][j]=new Obstacle(shuiImage);
						sprite[i][j].setType(3);
						sprite[i][j].setPosition(138+j*14, 12+i*14);
						break;
					case 4:
						sprite[i][j]=new Obstacle(cdImage);
						sprite[i][j].setType(4);
						sprite[i][j].setPosition(138+j*14, 12+i*14);
						break;
					default:
						break;
					}
					if(sprite[i][j]!=null){
						layerManager.append(sprite[i][j]);
					}
				}
			}
			
			//主战坦克
			tankImage_s=Image.createImage("/tk/s.png");
			tankImage_x=Image.createImage("/tk/x.png");
			tankImage_z=Image.createImage("/tk/z.png");
			tankImage_y=Image.createImage("/tk/y.png");
			tankSprite=new Tank(tankImage_s,tankImage_x,tankImage_z,tankImage_y,null,true);
			layerManager.append(tankSprite);
			
			//保护
			bhImage=Image.createImage("/game/bh.png");
			bhSprite=new Sprite(bhImage, 28, 28);
			tankSprite.setBhSprite(bhSprite);
			tankSprite.fh();
			layerManager.append(bhSprite);
			
			//敌方坦克
			cxImage=Image.createImage("/game/tkcx.png");
			tankImage0_s=Image.createImage("/tk1/tk0_s.png");
			tankImage0_x=Image.createImage("/tk1/tk0_x.png");
			tankImage0_z=Image.createImage("/tk1/tk0_z.png");
			tankImage0_y=Image.createImage("/tk1/tk0_y.png");
			
			tankImage1_s=Image.createImage("/tk1/tk1_s.png");
			tankImage1_x=Image.createImage("/tk1/tk1_x.png");
			tankImage1_z=Image.createImage("/tk1/tk1_z.png");
			tankImage1_y=Image.createImage("/tk1/tk1_y.png");
			
			tankImage2_s=Image.createImage("/tk1/tk2_s.png");
			tankImage2_x=Image.createImage("/tk1/tk2_x.png");
			tankImage2_z=Image.createImage("/tk1/tk2_z.png");
			tankImage2_y=Image.createImage("/tk1/tk2_y.png");
			tankSprites=new Tank[once];
			for (int i = 0; i < tankSprites.length; i++) {
				if(i<3){
					int k=i%3;
					switch (k) {
					case 0:
						tankSprites[i]=new Tank(tankImage0_s,tankImage0_x,tankImage0_z,tankImage0_y,cxImage,false);
						tankSprites[i].setFx(2);
						tankSprites[i].setPosition(138, 12);
						break;
					case 1:
						tankSprites[i]=new Tank(tankImage1_s,tankImage1_x,tankImage1_z,tankImage1_y,cxImage,false);
						tankSprites[i].setFx(2);
						tankSprites[i].setPosition(138+12*14, 12);
						break;
					case 2:
						tankSprites[i]=new Tank(tankImage2_s,tankImage2_x,tankImage2_z,tankImage2_y,cxImage,false);
						tankSprites[i].setFx(2);
						tankSprites[i].setPosition(138+24*14, 12);
						break;
					default:
						break;
					}
				}else{
					tankSprites[i]=getRandomTk();
					tankSprites[i].setVisible(false);
				}
				
				
				layerManager.append(tankSprites[i]);
			}
			
			
			//敌人数量
			countImage=Image.createImage("/game/tk.png");
			countSprite=new Sprite(countImage);
			countSprite.setPosition(550, 70);
			layerManager.append(countSprite);
			
			
			//背景
			bgImage=Image.createImage("/game/bg.png");
			bgSprite=new Sprite(bgImage);
			layerManager.append(bgSprite);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		
		try {
			Graphics g=getGraphics();
			while (isRunning) {
				if(pausing){
					drawScreen(g);
					System.gc();
					Thread.currentThread();
					Thread.sleep(DELAY);
					continue;
				}
				
				if(myCount<1){
					midlet.startApp();
				}
				step++;
				cx++;
				//子弹运动
				for (int i = 0; i < zdSprites.length; i++) {
					if(zdSprites[i].getFx()==1){
						zdSprites[i].move(0, -5);
					}else if(zdSprites[i].getFx()==2){
						zdSprites[i].move(0, 5);
					}else if(zdSprites[i].getFx()==3){
						zdSprites[i].move(-5, 0);
					}else if(zdSprites[i].getFx()==4){
						zdSprites[i].move(5, 0);
					}else if(zdSprites[i].getFx()==5){
						zdSprites[i].bz();
					}
					
					//子弹与坦克碰撞
					//未发射的子弹和爆炸的子弹不处理
					if(zdSprites[i].getFx()==0||zdSprites[i].getFx()==5){
						continue;
					}
					if(zdSprites[i].getWhose()==0){//我的子弹与地方碰撞
						for (int h = 0; h < tankSprites.length; h++) {
							if(tankSprites[h].isVisible()&&zdSprites[i].collidesWith(tankSprites[h], true)){
								zdSprites[i].bz();
								tankSprites[h].setPosition(138+rdm.nextInt(3)*12*14, 12);
								tankSprites[h].setCx(0);
								tankSprites[h].setFx(2);
								tankSprites[h].setImage(cxImage, 28, 28);
								tankSprites[h].autoMove();
								count--;
							}
						}
					}else{//敌方子弹与我碰撞
						if(tankSprite.isVisible()&&!tankSprite.getBhSprite().isVisible()&&zdSprites[i].collidesWith(tankSprite, true)){
							tankSprite.fh();
							myCount--;
						}

					}
					
					//子弹打老鹰
					if(zdSprites[i].collidesWith(lySprite, true)){
						lySprite.setImage(slyImage, 28, 28);
						isRunning=false;
					}
				}
				if(bhSprite.isVisible())bhSprite.nextFrame();
				//与障碍物的碰撞
				for (int i = 0; i < sprite.length; i++) {
					for (int j = 0; j < sprite[i].length; j++) {
						if(sprite[i][j]==null){continue;}
						//子弹与障碍物碰撞
						for (int k = 0; k < zdSprites.length; k++) {
							//未发射的子弹和爆炸的子弹不处理
							if(zdSprites[k].getFx()==0||zdSprites[k].getFx()==5){
								continue;
							}
							//河流和草地不处理
							if(sprite[i][j].getType()==3||sprite[i][j].getType()==4){
								continue;
							}
							
							if(sprite[i][j].collidesWith(zdSprites[k], true)){
								zdSprites[k].bz();
								if(sprite[i][j].getType()==1){//墙消失
									sprite[i][j].setVisible(false);
								}
							}
						}
						//主站坦克与障碍物碰撞
						
						while(tankSprite.collidesWith(sprite[i][j], true)||bj(tankSprite)){
							if(sprite[i][j].getType()!=4){
								tankSprite.reMove();//除了草地以外，其他障碍均不允许穿过
							}
						}
						
						//敌方坦克与障碍物碰撞
						for (int h = 0; h < tankSprites.length; h++) {
							if(tankSprites[h].isVisible()&&(tankSprites[h].collidesWith(sprite[i][j], true)||bj(tankSprites[h]))){
								if(sprite[i][j].getType()!=4){
									tankSprites[h].reMove();//除了草地以外，其他障碍均不允许穿过
								}
								tankSprites[h].setFx(tankSprites[h].getFx()+1);
							}
						}
						
					}
					
				}
				
				//保护
				System.out.println(tankSprite.getBhTime());
				if(tankSprite.getBhTime()<1){
					bhSprite.setVisible(false);
				}else{
					System.out.println("--");
					tankSprite.setBhTime(tankSprite.getBhTime()-1);
				}
				
				for (int i = 0; i < tankSprites.length; i++) {
					if(tankSprites[i].isVisible()){
						if(rdm.nextInt(15)==1&&tankSprites[i].getCx()==24)fire(tankSprites[i]);
						if(step==4){
							tankSprites[i].autoMove();
						}
					}else{
						if(cx%100==0&&count>once){
							tankSprites[i].setVisible(true);
							cx=0;
						}
					}
				}
				if(step==4){ 
					
					step=0;
				}
				
				if(count==1){
					initializeGame(++level);
				}
				drawScreen(g,level,myCount,count);
				Thread.currentThread();
				Thread.sleep(DELAY);
			}
			drawScreen(g,level,myCount,count);
			Thread.currentThread();
			Thread.sleep(3000);
			midlet.startApp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void drawScreen(Graphics g,int level,int woCount,int drCount) {
		g.setColor(0xffffff);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(0x0000ff);
		layerManager.setViewWindow(0, 0, 640,360);
		layerManager.paint(g, 0, 0);
		g.drawString(drCount+"",580,70,20);
		g.drawString(woCount+"",75,70,20);
		g.drawString(level+"",75,109,20);
//		g.drawString(aaa+"",10,10,0);
		flushGraphics();
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
		}else{
		}
	}
	protected void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
		switch (keyCode) {
			case KeyCode.UP:
				if (isRunning&&!pausing) {
					tankSprite.up();
				} else if(pausing){
				}
				break;
			case KeyCode.DOWN:
				if (isRunning&&!pausing) {
					tankSprite.down();
				} else if(pausing){
				}
				break;
			case KeyCode.LEFT:
				if (isRunning&&!pausing) {
					tankSprite.left();
				} else if(pausing){
				}
				break;
			case KeyCode.RIGHT:
				if (isRunning&&!pausing) {
					tankSprite.right();
				} else if(pausing){
				}
				break;
			case KeyCode.OK:
				if (isRunning&&!pausing) {
					fire(tankSprite);
				} else if(pausing){
				}
				break;
		}
	}
	protected void keyRepeated(int keyCode) {
		super.keyRepeated(keyCode);
		switch (keyCode) {
		case KeyCode.UP:
			if (isRunning&&!pausing) {
				tankSprite.up();
			} else if(pausing){
			}
			break;
		case KeyCode.DOWN:
			if (isRunning&&!pausing) {
				tankSprite.down();
			} else if(pausing){
			}
			break;
		case KeyCode.LEFT:
			if (isRunning&&!pausing) {
				tankSprite.left();
			} else if(pausing){
			}
			break;
		case KeyCode.RIGHT:
			if (isRunning&&!pausing) {
				tankSprite.right();
			} else if(pausing){
			}
			break;
		case KeyCode.OK:
			if(pausing){
			}
			if (isRunning) {
				pause();
			}else{
				
			}
			break;
		}
	}
	protected void keyReleased(int keyCode) {
		
	}
	
	private void fire(Tank tank){
		for (int i = 0; i < zdSprites.length; i++) {
			if(zdSprites[i].getFx()!=0)continue;
			if(tank.isMe()){
				zdSprites[i].setWhose(0);
			}else{
				zdSprites[i].setWhose(1);
			}
			int fx=tank.getFx();
			int tk_x=tank.getX();
			int tk_y=tank.getY();
			if(fx==1){
				zdSprites[i].setFx(1);
				zdSprites[i].setPosition(tk_x+13, tk_y);
			}
			if(fx==2){
				zdSprites[i].setFx(2);
				zdSprites[i].setPosition(tk_x+13, tk_y+28);
			}
			if(fx==3){
				zdSprites[i].setFx(3);
				zdSprites[i].setPosition(tk_x, tk_y+12);
			}
			if(fx==4){
				zdSprites[i].setFx(4);
				zdSprites[i].setPosition(tk_x+28, tk_y+12);
			}
			break;
		}
	}
	//边界
	private boolean bj(Tank tank){
		if(tank.getX()<138||tank.getX()>138+24*14||tank.getY()<12||tank.getY()>12+22*14)
			return true;
		return false;
	}
	private Tank getRandomTk(){
		Tank tk=null;
		int a=rdm.nextInt(3);
		if(a==0){
			tk=new Tank(tankImage0_s,tankImage0_x,tankImage0_z,tankImage0_y,cxImage,false);
		}else if(a==1){
			tk=new Tank(tankImage1_s,tankImage1_x,tankImage1_z,tankImage1_y,cxImage,false);
		}else if(a==2){
			tk=new Tank(tankImage2_s,tankImage2_x,tankImage2_z,tankImage2_y,cxImage,false);
		}
		tk.setFx(2);
		tk.setPosition(138+a*12*14, 12);
		return tk;
	}
	
	private void cleanUpBackground() {
		layerManager = new LayerManager();
	}
}
