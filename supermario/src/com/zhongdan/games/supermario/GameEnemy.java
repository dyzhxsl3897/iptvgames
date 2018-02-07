package com.zhongdan.games.supermario;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
/********本源码下载自源码爱好者_www.codefans.net*********/

public class GameEnemy {
	GameImage imglei = new GameImage();
	GameMap map = new GameMap();
	Sprite sp_mogu01,sp_mogu02,sp_wugui;
	final byte mogu01 = 0,mogu02 = 1,wugui = 2;
	final byte R = 6,L= 4,D = 8;
	final byte 移动 = 1,kill = 2;
	int type;
	int 地图移动量X;
	int DIR = L,state = D;
	boolean sfkill;
	int killtime;
	int mogu01_H,mogu01_W,mogu02_H,mogu02_W,wugui_H,wugui_W;
	Image img_Y_emy = imglei.imglong("emy");
	Image img_emy_mogu01 = Image.createImage(img_Y_emy, 118, 161, 40*3, 26, Sprite.TRANS_NONE);
	Image img_emy_mogu02 = Image.createImage(img_Y_emy, 0, 166, 40*3, 21, Sprite.TRANS_NONE);
	Image img_emy_wugui = Image.createImage(img_Y_emy, 115, 28, 43*3, 24, Sprite.TRANS_NONE);
	
	public GameEnemy(int type,int x,int y) {
		this.type = type;
		/********根据类型不同创建不同的怪物*********/
		switch (type) {
		case mogu01:
			sp_mogu01 = new Sprite(img_emy_mogu01,40,26);
			sp_mogu01.setPosition(x, y);	
			mogu01_H = sp_mogu01.getHeight();
			mogu01_W = sp_mogu01.getWidth();
			break;

		case mogu02:
			sp_mogu02 = new Sprite(img_emy_mogu02,40,21);
			sp_mogu02.setPosition(x, y);
			mogu02_H = sp_mogu02.getHeight();
			mogu02_W = sp_mogu02.getWidth();
			break;
			
		case wugui:
			sp_wugui = new Sprite(img_emy_wugui,43,24);
			sp_wugui.setPosition(x, y);
			wugui_H = sp_wugui.getHeight();
			wugui_W = sp_wugui.getWidth();
			sp_wugui.defineReferencePixel(wugui_W/2, 0);
			break;
		}
	}
	
	public void pinat(Graphics g) {
		switch (state) {
		case 移动:
			/********在移动状态下根据类型不同绘制不同的怪物*********/
			switch (type) {
			case mogu01:	sp_mogu01.paint(g);		break;				
			case mogu02:	sp_mogu02.paint(g);		break;					
			case wugui:
				switch (DIR) {
				case R:
					sp_wugui.setTransform(Sprite.TRANS_NONE);
					sp_wugui.paint(g);
					break;

				case L:
					sp_wugui.setTransform(Sprite.TRANS_MIRROR);
					sp_wugui.paint(g);
					break;
				}
				break;
			}
			break;
			
		case D:
			/********在下落状态下根据类型不同绘制不同的怪物*********/
			switch (type) {
			case mogu01:	sp_mogu01.paint(g);		break;				
			case mogu02:	sp_mogu02.paint(g);		break;				
			case wugui:
				switch (DIR) {
				case R:
					sp_wugui.setTransform(Sprite.TRANS_NONE);
					sp_wugui.paint(g);
					break;

				case L:
					sp_wugui.setTransform(Sprite.TRANS_MIRROR);
					sp_wugui.paint(g);
					break;
				}
				break;
			}
			break;
			
		case kill:
			/********在死亡状态下根据类型不同绘制不同的怪物*********/
			switch (type) {
			case mogu01:	sp_mogu01.paint(g);		break;					
			case mogu02:	sp_mogu02.paint(g);		break;					
			case wugui:
				switch (DIR) {
				case R:
					sp_wugui.setTransform(Sprite.TRANS_NONE);
					sp_wugui.paint(g);
					break;

				case L:
					sp_wugui.setTransform(Sprite.TRANS_MIRROR);
					sp_wugui.paint(g);
					break;
				}
				break;
			}
			break;
		}
	}

	public void logic(int 地图移动量X) {
		this.地图移动量X = 地图移动量X;
		/********根据不同怪物的类型实现不同的逻辑*********/
		switch (GameMap.guan) {
		case 0:
			switch (type) {
			case mogu01:
				/********第一类怪物的逻辑*********/
				if(!sfkill&&sp_mogu01_DZ_coll(map.map_peng01)) {
					sp_mogu01.setPosition(sp_mogu01.getX(), (sp_mogu01.getY()+mogu01_H)/map.kuaisize*map.kuaisize-mogu01_H);
					state = 移动;
				} else if(!sfkill&&!sp_mogu01_DZ_coll(map.map_peng01))state = D;
				if(!sfkill&&sp_mogu01_LZ_coll(map.map_peng01))DIR = R;
				if(!sfkill&&sp_mogu01_RZ_coll(map.map_peng01))DIR = L;
				break;
				
			case mogu02:
				/********第二类怪物的逻辑*********/
				if(!sfkill&&sp_mogu02_DZ_coll(map.map_peng01)) {
					sp_mogu02.setPosition(sp_mogu02.getX(), (sp_mogu02.getY()+mogu02_H)/map.kuaisize*map.kuaisize-mogu02_H);
					state = 移动;
				} else if(!sfkill&&!sp_mogu02_DZ_coll(map.map_peng01))state = D;
				if(!sfkill&&sp_mogu02_LZ_coll(map.map_peng01))DIR = R;
				if(!sfkill&&sp_mogu02_RZ_coll(map.map_peng01))DIR = L;
				break;
				
			case wugui:
				/********第三类怪物的逻辑*********/
				if(!sfkill&&sp_wugui_DZ_coll(map.map_peng01)) {
					sp_wugui.setPosition(sp_wugui.getX(), (sp_wugui.getY()+wugui_H)/map.kuaisize*map.kuaisize-wugui_H);
					state = 移动;
				} else if(!sfkill&&!sp_wugui_DZ_coll(map.map_peng01))state = D;
				if(!sfkill&&sp_wugui_LZ_coll(map.map_peng01))DIR = R;
				if(!sfkill&&sp_wugui_RZ_coll(map.map_peng01))DIR = L;
				break;
			}
			break;

		case 1:
			switch (type) {
			case mogu01:
				/********第一类怪物的逻辑*********/
				if(!sfkill&&sp_mogu01_DZ_coll(map.map_peng02)) {
					sp_mogu01.setPosition(sp_mogu01.getX(), (sp_mogu01.getY()+mogu01_H)/map.kuaisize*map.kuaisize-mogu01_H);
					state = 移动;
				} else if(!sfkill&&!sp_mogu01_DZ_coll(map.map_peng02))state = D;
				if(!sfkill&&sp_mogu01_LZ_coll(map.map_peng02))DIR = R;
				if(!sfkill&&sp_mogu01_RZ_coll(map.map_peng02))DIR = L;
				break;
				
			case mogu02:
				/********第二类怪物的逻辑*********/
				if(!sfkill&&sp_mogu02_DZ_coll(map.map_peng02)) {
					sp_mogu02.setPosition(sp_mogu02.getX(), (sp_mogu02.getY()+mogu02_H)/map.kuaisize*map.kuaisize-mogu02_H);
					state = 移动;
				} else if(!sfkill&&!sp_mogu02_DZ_coll(map.map_peng02))state = D;
				if(!sfkill&&sp_mogu02_LZ_coll(map.map_peng02))DIR = R;
				if(!sfkill&&sp_mogu02_RZ_coll(map.map_peng02))DIR = L;
				break;
				
			case wugui:
				/********第三类怪物的逻辑*********/
				if(!sfkill&&sp_wugui_DZ_coll(map.map_peng02)) {
					sp_wugui.setPosition(sp_wugui.getX(), (sp_wugui.getY()+wugui_H)/map.kuaisize*map.kuaisize-wugui_H);
					state = 移动;
				} else if(!sfkill&&!sp_wugui_DZ_coll(map.map_peng02))state = D;
				if(!sfkill&&sp_wugui_LZ_coll(map.map_peng02))DIR = R;
				if(!sfkill&&sp_wugui_RZ_coll(map.map_peng02))DIR = L;
				break;
			}
			break;
		}
		switch (state) {
		case 移动:
			switch (type) {
			case mogu01:
				/********第一类怪物的换帧*********/
				sp_mogu01.nextFrame();	
				if(sp_mogu01.getFrame()==2)sp_mogu01.setFrame(0);
				break;
				
			case mogu02:
				/********第二类怪物的换帧*********/
				sp_mogu02.nextFrame();	
				if(sp_mogu02.getFrame()==2)sp_mogu02.setFrame(0);
				break;
				
			case wugui:
				/********第三类怪物的换帧*********/
				sp_wugui.nextFrame();	
				if(sp_wugui.getFrame()==2)sp_wugui.setFrame(0);	
				break;
			}
			break;

		case kill:
			switch (type) {
			case mogu01:	
				killtime++;
				sp_mogu01.setFrame(2);
				if(killtime>=60) {
					sp_mogu01.setVisible(false);
					killtime =0 ;
				}
				break;	
				
			case mogu02:
				killtime++;
				sp_mogu02.setFrame(2);
				if(killtime>=60) {
					sp_mogu02.setVisible(false);
					killtime =0 ;
				}
				break;					
				
			case wugui:		
				sp_wugui.setFrame(2);
				break;
			}
			break;
		}
	}
	
	public void move() {
		/********根据不同怪物的类型实现不同的移动*********/
		switch (state) {
		case 移动:
			switch (type) {
			case mogu01:
				switch (DIR) {
				case R:		sp_mogu01.move(3, 0);		break;
				case L:		sp_mogu01.move(-3, 0);		break;	
				}
				break;

			case mogu02:
				switch (DIR) {
				case R:		sp_mogu02.move(3, 0);		break;
				case L:		sp_mogu02.move(-3, 0);		break;
				}
				break;
				
			case wugui:
				switch (DIR) {
				case R:		sp_wugui.move(3, 0);		break;
				case L:		sp_wugui.move(-3, 0);		break;
				}
				break;
			}
			break;
			
		case D:
			switch (type) {
			case mogu01:	sp_mogu01.move(0, 6);		break;
			case mogu02:	sp_mogu02.move(0, 6);		break;			
			case wugui:		sp_wugui.move(0, 6);		break;
			}
			break;
		}
	}
	
	/**
	 * sp_mogu01 碰撞检测点
	 */
	boolean sp_mogu01_arrout = false;
	
	/*************sp_mogu01 碰撞检测点***********下中***/
	public boolean sp_mogu01_DZ_coll(int tilarrr[][]) {
		int sp_mogu01_kuai_x = (sp_mogu01.getX()+地图移动量X+mogu01_W/2)/map.kuaisize;
		int	sp_mogu01_kuai_y = (sp_mogu01.getY()+mogu01_H)/map.kuaisize;
		int coll_kuai = 1;
		if(!sp_mogu01_arrout) {
			try {
				coll_kuai = tilarrr[sp_mogu01_kuai_y][sp_mogu01_kuai_x];
			} catch (RuntimeException e) {
				sp_mogu01_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;
		else return false;
	}
	
	/*************sp_mogu01 碰撞检测点***********右中***/
	public boolean sp_mogu01_RZ_coll(int tilarrr[][]) {
		int sp_mogu01_kuai_x = (sp_mogu01.getX()+地图移动量X+mogu01_W)/map.kuaisize;
		int	sp_mogu01_kuai_y = (sp_mogu01.getY()+mogu01_H/2)/map.kuaisize;
		int coll_kuai = 1;
		if(!sp_mogu01_arrout) {
			try {
				coll_kuai = tilarrr[sp_mogu01_kuai_y][sp_mogu01_kuai_x];
			} catch (RuntimeException e) {
				sp_mogu01_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;		
		else return false;
	}
	
	/*************sp_mogu01 碰撞检测点***********左中***/
	public boolean sp_mogu01_LZ_coll(int tilarrr[][]) {
		int sp_mogu01_kuai_x = (sp_mogu01.getX()+地图移动量X)/map.kuaisize;
		int sp_mogu01_kuai_y = (sp_mogu01.getY()+mogu01_H/2)/map.kuaisize;
		int coll_kuai = 1;
		if(!sp_mogu01_arrout) {
			try {
				coll_kuai = tilarrr[sp_mogu01_kuai_y][sp_mogu01_kuai_x];
			} catch (RuntimeException e) {
				sp_mogu01_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;			
		else return false;
	}
	
	/**
	 * sp_mogu02 碰撞检测点
	 */
	boolean sp_mogu02_arrout = false;
	
	/*************sp_mogu02 碰撞检测点***********下中***/
	public boolean sp_mogu02_DZ_coll(int tilarrr[][]) {
		int sp_mogu02_kuai_x = (sp_mogu02.getX()+地图移动量X+mogu02_W/2)/map.kuaisize;
		int	sp_mogu02_kuai_y = (sp_mogu02.getY()+mogu02_H)/map.kuaisize;
		int coll_kuai = 1;
		if(!sp_mogu02_arrout) {
			try {
				coll_kuai = tilarrr[sp_mogu02_kuai_y][sp_mogu02_kuai_x];
			} catch (RuntimeException e) {
				sp_mogu02_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;
		else return false;
	}
	
	/*************sp_mogu02 碰撞检测点***********右中***/
	public boolean sp_mogu02_RZ_coll(int tilarrr[][]) {
		int sp_mogu02_kuai_x = (sp_mogu02.getX()+地图移动量X+mogu02_W)/map.kuaisize;
		int	sp_mogu02_kuai_y = (sp_mogu02.getY()+mogu02_H/2)/map.kuaisize;
		int coll_kuai = 1;
		if(!sp_mogu02_arrout) {
			try {
				coll_kuai = tilarrr[sp_mogu02_kuai_y][sp_mogu02_kuai_x];
			} catch (RuntimeException e) {
				sp_mogu02_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;		
		else return false;
	}
	
	/*************sp_mogu02 碰撞检测点***********左中***/
	public boolean sp_mogu02_LZ_coll(int tilarrr[][]) {
		int sp_mogu02_kuai_x = (sp_mogu02.getX()+地图移动量X)/map.kuaisize;
		int sp_mogu02_kuai_y = (sp_mogu02.getY()+mogu02_H/2)/map.kuaisize;
		int coll_kuai = 1;
		if(!sp_mogu02_arrout) {
			try {
				coll_kuai = tilarrr[sp_mogu02_kuai_y][sp_mogu02_kuai_x];
			} catch (RuntimeException e) {
				sp_mogu02_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;			
		else return false;
	}
	
	/**
	 * sp_wugui 碰撞检测点
	 */
	boolean sp_wugui_arrout = false;
	
	/*************sp_wugui 碰撞检测点***********下中***/
	public boolean sp_wugui_DZ_coll(int tilarrr[][]) {
		int sp_wugui_kuai_x = (sp_wugui.getX()+地图移动量X+wugui_W/2)/map.kuaisize;
		int	sp_wugui_kuai_y = (sp_wugui.getY()+wugui_H)/map.kuaisize;
		int coll_kuai = 1;
		if(!sp_wugui_arrout) {
			try {
				coll_kuai = tilarrr[sp_wugui_kuai_y][sp_wugui_kuai_x];
			} catch (RuntimeException e) {
				sp_wugui_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;
		else return false;
	}
	
	/*************sp_wugui 碰撞检测点***********右中***/
	public boolean sp_wugui_RZ_coll(int tilarrr[][]) {
		int sp_wugui_kuai_x = (sp_wugui.getX()+地图移动量X+wugui_W)/map.kuaisize;
		int	sp_wugui_kuai_y = (sp_wugui.getY()+wugui_H/2)/map.kuaisize;
		int coll_kuai = 1;
		if(!sp_wugui_arrout) {
			try {
				coll_kuai = tilarrr[sp_wugui_kuai_y][sp_wugui_kuai_x];
			} catch (RuntimeException e) {
				sp_wugui_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;		
		else return false;
	}
	
	/*************sp_wugui 碰撞检测点***********左中***/
	public boolean sp_wugui_LZ_coll(int tilarrr[][]) {
		int sp_wugui_kuai_x = (sp_wugui.getX()+地图移动量X)/map.kuaisize;
		int sp_wugui_kuai_y = (sp_wugui.getY()+wugui_H/2)/map.kuaisize;
		int coll_kuai = 1;
		if(!sp_wugui_arrout) {
			try {
				coll_kuai = tilarrr[sp_wugui_kuai_y][sp_wugui_kuai_x];
			} catch (RuntimeException e) {
				sp_wugui_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;			
		else return false;
	}
}
