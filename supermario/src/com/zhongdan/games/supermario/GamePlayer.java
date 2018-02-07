package com.zhongdan.games.supermario;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.media.Player;
/********本源码下载自源码爱好者_www.codefans.net*********/

public class GamePlayer {
	GameImage imglei;
	GameMusic miclei;
	GameMap map;
	Player mid_kill,mid_gameover;
	GameEnemy enemy[];//敌人类（怪物）
	GameFlash flash[];//动画类（金币和石头）
	int flashN;
	int enemyN;
	boolean guaichuxian[];
	boolean jinbichuxian[];
	boolean shitouchuxian[];
	int pengkuai[][];
	int mapmoveX;
	int W,H;
	Image img_Y_mali,img_d_mali,img_x_mali,img_gamevoer;	
	Sprite d_mali,x_mali;
	int frame[] = {3,1,2,5,6,7};	
	final byte zhanli = 0,yidong = 1,ACT = 2,tiaoyue = 3,kill = 4,gameover = 5;
	final byte U = 2,D = 8,L = 4,R = 6,UR = 26,UL = 24,DR = 86,DL = 84;
	byte d_mali_state = zhanli,x_mali_state = zhanli,DIR = R,跳跃状态,kill_state;	
	int mali_X,mali_Y;
	byte malisudu;	
	byte jiasudu[] = {23,19,16,14,13,11,9,8,6,4,3,1};
	byte index;//加速度数组下标
	int x_mali_H,x_mali_W,d_mali_H,d_mali_W;
	boolean sfkillstate;//是否进入死亡状态
	boolean sfkill;//是否死亡
	int n1;//玛丽数量
	int n2;//金币数量
	int n3;//总分数
	byte z;//换图片
	byte d_guan;//大关卡数
	byte x_guan;//小关卡数
	int gametime01,gametime02 = 100;
	
	public GamePlayer(GameMap map,GameImage imglei,GameMusic miclei,int w,int h) {
		this.map = map;
		this.imglei = imglei;
		this.miclei = miclei;
		this.W = w;
		this.H = h;
		enemyN = 6;
		enemy = new GameEnemy[enemyN];
		guaichuxian = new boolean[enemyN];
		flashN = 4;
		flash = new GameFlash[flashN];
		jinbichuxian = new boolean[flashN];
		shitouchuxian = new boolean[flashN];
		pengkuai = new int[map.map_peng01_h][map.map_peng01_w];
		mali_X = 16;
		mali_Y = 458;
		mid_kill = miclei.musiclong("kill");
		mid_gameover = miclei.musiclong("gameover");
		img_Y_mali = imglei.imglong("MaLi");
		img_gamevoer = imglei.imglong("gameover");
		img_d_mali = Image.createImage(img_Y_mali, 0, 23, 264, 44, Sprite.TRANS_NONE);		
		img_x_mali = Image.createImage(img_Y_mali, 0, 68, 264, 39, Sprite.TRANS_NONE);	
		d_mali = new Sprite(img_d_mali,33,44);
		x_mali = new Sprite(img_x_mali,33,39);	
		malisudu = 6;
		n1 = 5;
		d_guan = 1;
		x_guan = 1;
		x_mali_H = x_mali.getHeight();
		x_mali_W = x_mali.getWidth();
		d_mali_H = d_mali.getHeight();
		d_mali_W = d_mali.getWidth();
		d_mali.defineReferencePixel(d_mali_W/2, 0);
		x_mali.defineReferencePixel(x_mali_W/2, 0);		
		x_mali.setPosition(mali_X, mali_Y);
		x_mali.setFrameSequence(frame);
	}

	public void piant(Graphics g) {
		g.setColor(0);
		g.fillRect(0, 0, W, 24);
		g.setColor(-1);
		g.drawRegion(img_Y_mali, 0, 0, 24, 23, Sprite.TRANS_NONE, 0, 0, 20);
		g.drawString("X", 33, 0, 20);
		g.drawString(String.valueOf(n1), 49, 0, 20);
		g.drawRegion(img_Y_mali, 49+18*(z%6), 0, 18, 18, Sprite.TRANS_NONE, 79, 3, 20);
		g.drawString("X", 109, 0, 20);
		g.drawString(String.valueOf(n2), 132, 0, 20);
		g.drawString("总分数:", 165, 0, 20);
		g.drawString(String.valueOf(n3), 250, 0, Graphics.TOP|Graphics.RIGHT);
		g.drawRegion(img_Y_mali, 24, 0, 23, 23, Sprite.TRANS_NONE, 338, 0, 20);
		g.drawString(String.valueOf(gametime02), 396, 0, Graphics.TOP|Graphics.RIGHT);
		/******怪物绘制******/
		for(int i=0;i<enemyN;i++) {
			if(enemy[i] == null)continue;
			else enemy[i].pinat(g);
		}
		for(int i=0;i<flashN;i++) {
			if(flash[i] == null)continue;
			else flash[i].pinat(g);
		}
		/******主角绘制******/
		switch (x_mali_state) {
		case zhanli:
			switch (DIR) {
			case R:		
				x_mali.setTransform(Sprite.TRANS_NONE);
				x_mali.paint(g);	
				break;
				
			case L:		
				x_mali.setTransform(Sprite.TRANS_MIRROR);	
				x_mali.paint(g);		
				break;
			}
			break;
			
		case yidong:
			switch (DIR) {
			case R:
				x_mali.setTransform(Sprite.TRANS_NONE);
				x_mali.paint(g);
				break;
				
			case L:
				x_mali.setTransform(Sprite.TRANS_MIRROR);	
				x_mali.paint(g);
				break;
			}
			break;
			
		case tiaoyue:
			switch (跳跃状态) {
			case UL:
			case UR:
			case U:
				switch (DIR) {
				case R:
					x_mali.setTransform(Sprite.TRANS_NONE);
					x_mali.paint(g);
					break;
					
				case L:
					x_mali.setTransform(Sprite.TRANS_MIRROR);
					x_mali.paint(g);
					break;
				}
				break;
				
			case DL:
			case DR:
			case D:
				switch (DIR) {
				case R:
					x_mali.setTransform(Sprite.TRANS_NONE);
					x_mali.paint(g);
					break;
					
				case L:
					x_mali.setTransform(Sprite.TRANS_MIRROR);
					x_mali.paint(g);
					break;
				}
			}
			break;
			
		case kill:
			x_mali.setTransform(Sprite.TRANS_NONE);
			x_mali.paint(g);
			if(sfkillstate&&x_mali.getY()>325) {
				g.setColor(0);
				g.fillRect(0, 0, W, H);
				g.setColor(-1);
				g.drawString(String.valueOf((int)d_guan), 102, H/2-20, Graphics.TOP|Graphics.HCENTER);
				g.drawString("X", W/2, H/2-20, Graphics.TOP|Graphics.HCENTER);
				g.drawString(String.valueOf((int)x_guan), 138, H/2-20, Graphics.TOP|Graphics.HCENTER);
				g.drawRegion(img_Y_mali, 20, 41, 20, 24, Sprite.TRANS_NONE, 100, H/2-6+10, Graphics.TOP|Graphics.HCENTER);
				g.drawString("X", W/2, H/2+10, Graphics.TOP|Graphics.HCENTER);
				g.drawString(String.valueOf(n1), 140, H/2+10, Graphics.TOP|Graphics.HCENTER);
			}
			break;
			
		case gameover:
			g.setColor(0);
			g.fillRect(0, 0, W, H);
			g.drawRegion(img_gamevoer, 104, 0, 36, 52, Sprite.TRANS_NONE, 43, 132, 20);
			g.drawRegion(img_gamevoer, 0, (z%3)*10, 104, 10, Sprite.TRANS_NONE, 95, 153, 20);
			break;
		}
		
	}
	
	public void logic() {
		/*********时间到则主角死亡判断*************/
		if(gametime01%25==0)gametime02--;
		if(gametime01<500000)gametime01++;
		if(!sfkillstate&&gametime02<0){
			index = 0;
			x_mali_state = kill;
			kill_state = U;
			sfkillstate = true;
			z = 0;
			mid_kill = miclei.musiclong("kill");
			miclei.musicstart(mid_kill);
		}
		/**********主角过关***************/
		switch (GameMap.guan) {
		case 0:
			if(x_mali.getX()>=220) {
				GameMap.guan = GameMap.tow;
				x_guan++;
				x_mali.setPosition(mali_X, mali_Y);
				map.til_back02.setPosition(0, 0);
				map.til_peng02.setPosition(0, 0);
				map.til_zhebi02.setPosition(0, 0);
				for(int i=0;i<map.map_peng01_h;i++) {
					for(int j=0;j<map.map_peng01_w;j++) {
						if(pengkuai[i][j]==0)continue;
						else pengkuai[i][j] = 0;
					}
				}
				for(int i=0;i<enemyN;i++) {
					guaichuxian[i] = false;
					enemy[i] = null;
				}
				DIR = R;
				sfkillstate = false;
				sfkill = false;
				x_mali_arrout = false;
				x_mali_state = zhanli;
				mapmoveX = 0;
				gametime01 = 0;
				gametime02 = 100;
			}
			break;
			
		case 1:
			if(x_mali.getX()>=317) {
				
			}
			break;
		}
		/********当主角到地图移动量X的某个值开始刷怪***********/
		switch (GameMap.guan) {
		case 0:
			if(!guaichuxian[0]&&!guaichuxian[1]&&!guaichuxian[2]&&mapmoveX==0) {
				enemy[0] = new GameEnemy(0,330,297);
				enemy[1] = new GameEnemy(0,330,231);
				enemy[2] = new GameEnemy(0,330,363);
				guaichuxian[0] = true;
				guaichuxian[1] = true;
				guaichuxian[2] = true;
			}
			if(!guaichuxian[3]&&!guaichuxian[4]&&!guaichuxian[5]&&mapmoveX==252) {
				enemy[3] = new GameEnemy(1,495,297);
				enemy[4] = new GameEnemy(2,495,363);
				enemy[5] = new GameEnemy(2,594,297);
				guaichuxian[3] = true;
				guaichuxian[4] = true;
				guaichuxian[5] = true;
			}
			if(!guaichuxian[0]&&!guaichuxian[1]&&mapmoveX==834) {
				enemy[0] = new GameEnemy(1,495,297);
				enemy[1] = new GameEnemy(1,495,363);
				guaichuxian[0] = true;
				guaichuxian[1] = true;
			}
			if(!guaichuxian[2]&&!guaichuxian[3]&&!guaichuxian[4]&&!guaichuxian[5]&&mapmoveX==1650) {
				enemy[2] = new GameEnemy(0,495,297);
				enemy[3] = new GameEnemy(0,495,363);
				enemy[4] = new GameEnemy(2,594,231);
				enemy[5] = new GameEnemy(2,660,264);
				guaichuxian[2] = true;
				guaichuxian[3] = true;
				guaichuxian[4] = true;
				guaichuxian[5] = true;
			}
			if(!guaichuxian[0]&&!guaichuxian[1]&&mapmoveX==2310) {
				enemy[0] = new GameEnemy(0,140,113);
				enemy[1] = new GameEnemy(0,206,80);
				guaichuxian[0] = true;
				guaichuxian[1] = true;
			}
			break;

		case 1:
			if(!guaichuxian[0]&&!guaichuxian[1]&&mapmoveX==1074) {
				enemy[0] = new GameEnemy(0,396,165);
				enemy[1] = new GameEnemy(0,495,132);
				guaichuxian[0] = true;
				guaichuxian[1] = true;
			}
			if(!guaichuxian[2]&&!guaichuxian[3]&&!guaichuxian[4]&&mapmoveX==1422) {
				enemy[2] = new GameEnemy(1,231,165);
				enemy[3] = new GameEnemy(0,264,148);
				enemy[4] = new GameEnemy(1,297,132);
				guaichuxian[2] = true;
				guaichuxian[3] = true;
				guaichuxian[4] = true;
			}
			if(!guaichuxian[5]&&!guaichuxian[0]&&!guaichuxian[1]&&mapmoveX==1950) {
				enemy[5] = new GameEnemy(1,561,165);
				enemy[0] = new GameEnemy(2,594,148);
				enemy[1] = new GameEnemy(1,627,132);
				guaichuxian[5] = true;
				guaichuxian[0] = true;
				guaichuxian[1] = true;
			}
			if(!guaichuxian[2]&&!guaichuxian[3]&&!guaichuxian[4]&&mapmoveX==2502) {
				enemy[2] = new GameEnemy(1,231,165);
				enemy[3] = new GameEnemy(2,264,148);
				enemy[4] = new GameEnemy(1,297,132);
				guaichuxian[2] = true;
				guaichuxian[3] = true;
				guaichuxian[4] = true;
			}
			break;
		}
		/******怪物逻辑******/
		for(int i=0;i<enemyN;i++) {
			if(enemy[i] == null)continue;
			else {
				enemy[i].logic(mapmoveX);
				switch (enemy[i].type) {
				case 0:		if(enemy[i].sp_mogu01.getX()<-30)guaichuxian[i] = false;		break;
				case 1:		if(enemy[i].sp_mogu02.getX()<-30)guaichuxian[i] = false;		break;
				case 2:		if(enemy[i].sp_wugui.getX()<-30)guaichuxian[i] = false;			break;
				}
			}
		}
		for(int i=0;i<flashN;i++) {
			if(flash[i] == null)continue;
			else {
				flash[i].logic(mapmoveX);
				switch (flash[i].type) {
				case 0:		if(flash[i].sfxiaoshi)jinbichuxian[i] = false;		break;
				case 1:		if(flash[i].sfxiaoshi)shitouchuxian[i] = false;		break;
				}
			}
		}
		if(z == 120)z = 0;
		/******主角超出屏幕死亡判断******/
		if(x_mali.getY()>530&&!sfkillstate) {
			index = 0;
			x_mali_state = kill;
			kill_state = U;
			sfkillstate = true;
			z = 0;
			mid_kill = miclei.musiclong("kill");
			miclei.musicstart(mid_kill);
		}
		/******主角逻辑******/
		switch (x_mali_state) {
		case zhanli:
			/******主角站立状态碰到怪物死亡判断******/
			for(int i=0;i<enemyN;i++) {
				if(enemy[i] == null||enemy[i].sfkill)continue;
				else {
					switch (enemy[i].type) {
					case 0:
						if(!enemy[i].sfkill&&x_mali.collidesWith(enemy[i].sp_mogu01, true)) {
							index = 0;
							x_mali_state = kill;
							kill_state = U;
							sfkillstate = true;
							z = 0;
							mid_kill = miclei.musiclong("kill");
							miclei.musicstart(mid_kill);
						}
						break;
						
					case 1:
						if(!enemy[i].sfkill&&x_mali.collidesWith(enemy[i].sp_mogu02, true)) {
							index = 0;
							x_mali_state = kill;
							kill_state = U;
							sfkillstate = true;
							z = 0;
							mid_kill = miclei.musiclong("kill");
							miclei.musicstart(mid_kill);
						}
						break;
						
					case 2:
						if(!enemy[i].sfkill&&x_mali.collidesWith(enemy[i].sp_wugui, true)) {
							index = 0;
							x_mali_state = kill;
							kill_state = U;
							sfkillstate = true;
							z = 0;
							mid_kill = miclei.musiclong("kill");
							miclei.musicstart(mid_kill);
						}
						break;
					}
				}
			}
			z++;
			x_mali.setFrame(2);
			break;
			
		case yidong:
			/******主角移动状态碰到怪物死亡判断******/
			for(int i=0;i<enemyN;i++) {
				if(enemy[i] == null||enemy[i].sfkill)continue;
				else {
					switch (enemy[i].type) {
					case 0:
						if(!enemy[i].sfkill&&x_mali.collidesWith(enemy[i].sp_mogu01, true)) {
							index = 0;
							x_mali_state = kill;
							kill_state = U;
							sfkillstate = true;
							z = 0;
							mid_kill = miclei.musiclong("kill");
							miclei.musicstart(mid_kill);
						}
						break;
						
					case 1:
						if(!enemy[i].sfkill&&x_mali.collidesWith(enemy[i].sp_mogu02, true)) {
							index = 0;
							x_mali_state = kill;
							kill_state = U;
							sfkillstate = true;
							z = 0;
							mid_kill = miclei.musiclong("kill");
							miclei.musicstart(mid_kill);
						}
						break;
						
					case 2:
						if(!enemy[i].sfkill&&x_mali.collidesWith(enemy[i].sp_wugui, true)) {
							index = 0;
							x_mali_state = kill;
							kill_state = U;
							sfkillstate = true;
							z = 0;
							mid_kill = miclei.musiclong("kill");
							miclei.musicstart(mid_kill);
						}
						break;
					}
				}
			}
			z++;
			switch (DIR) {
			/******主角右移动逻辑******/
			case R:	
				/******主角换帧******/
				x_mali.nextFrame();
				if(x_mali.getFrame() >= 3)x_mali.setFrame(0);
				/******主角右移动地图移动判断******/
				switch (GameMap.guan) {
				case 0:
					if(map.til_back01.getX()>-2970&&x_mali.getX()>=198) {
						map.til_back01.move(-malisudu, 0);
						map.til_peng01.move(-malisudu, 0);
						map.til_zhebi01.move(-malisudu, 0);
						mapmoveX+=malisudu;
						x_mali.move(-malisudu, 0);
						/******主角右移动怪物移动判断******/
						for(int i=0;i<enemyN;i++) {
							if(enemy[i] == null)continue;
							else {
								switch (enemy[i].type) {
								case 0:		enemy[i].sp_mogu01.move(-malisudu, 0);		break;						
								case 1:		enemy[i].sp_mogu02.move(-malisudu, 0);		break;						
								case 2:		enemy[i].sp_wugui.move(-malisudu, 0);		break;
								}
							}
						}
					}
					break;

				case 1:
					if(map.til_peng02.getX()>-2970&&x_mali.getX()>=198) {
						map.til_back02.move(-malisudu, 0);
						map.til_peng02.move(-malisudu, 0);
						map.til_zhebi02.move(-malisudu, 0);
						mapmoveX+=malisudu;
						x_mali.move(-malisudu, 0);
						/******主角右移动怪物移动判断******/
						for(int i=0;i<enemyN;i++) {
							if(enemy[i] == null)continue;
							else {
								switch (enemy[i].type) {
								case 0:		enemy[i].sp_mogu01.move(-malisudu, 0);		break;						
								case 1:		enemy[i].sp_mogu02.move(-malisudu, 0);		break;						
								case 2:		enemy[i].sp_wugui.move(-malisudu, 0);		break;
								}
							}
						}
					}
					break;
				}
				break;	
				
			/******主角左移动逻辑******/
			case L:
				/******主角换帧******/
				x_mali.nextFrame();
				if(x_mali.getFrame() >= 3)x_mali.setFrame(0);
				break;
			}
			break;
			
		case tiaoyue:
			z++;
			switch (跳跃状态) {
			/******主角上跳跃逻辑******/
			case UR:
			case UL:
			case U:
				x_mali.setFrame(3);
				/******主角上跳跃时碰到地图中那一块的逻辑******/
				switch (GameMap.guan) {
				case 0:
					for(int i=0;i<map.map_peng01_h;i++) {
						for(int j=0;j<map.map_peng01_w;j++) {
							if(map.map_peng01[i][j]==0)continue;
							if(UZR_coll(map.til_peng01,map.map_peng01)||UZL_coll(map.til_peng01,map.map_peng01)) {
								if(map.map_peng01[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x]==56||
										map.map_peng01[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x]==57||
										map.map_peng01[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x]==58||
										map.map_peng01[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x]==59) {
									map.map_peng01[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x] = 60;
									pengkuai[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x] = 56;
									n2++;
									for(int a=0;a<flashN;a++) {
										if(!jinbichuxian[a]) {
											flash[a] = new GameFlash(0,x_mali_kuai_up_coll_x*map.kuaisize-mapmoveX,x_mali_kuai_up_coll_y*map.kuaisize); 
											jinbichuxian[a] = true;
											break;
										}
									}
									n3+=100;
								} 
								if(map.map_peng01[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x]==31) {
									map.map_peng01[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x] = 0;
									pengkuai[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x] = 31;
									跳跃状态 = D;
									for(int a=0;a<flashN;a++) {
										if(!shitouchuxian[a]) {
											flash[a] = new GameFlash(1,x_mali_kuai_up_coll_x*map.kuaisize-mapmoveX,x_mali_kuai_up_coll_y*map.kuaisize); 
											shitouchuxian[a] = true;
											break;
										}
									}
								}
							} 
						}
					}
					break;

				case 1:
					for(int i=0;i<map.map_peng02_h;i++) {
						for(int j=0;j<map.map_peng02_w;j++) {
							if(map.map_peng02[i][j]==0)continue;
							if(UZR_coll(map.til_peng02,map.map_peng02)||UZL_coll(map.til_peng02,map.map_peng02)) {
								if(map.map_peng02[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x]==56||
										map.map_peng02[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x]==57||
										map.map_peng02[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x]==58||
										map.map_peng02[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x]==59) {
									map.map_peng02[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x] = 60;
									pengkuai[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x] = 56;
									n2++;
									for(int a=0;a<flashN;a++) {
										if(!jinbichuxian[a]) {
											flash[a] = new GameFlash(0,x_mali_kuai_up_coll_x*map.kuaisize-mapmoveX,x_mali_kuai_up_coll_y*map.kuaisize); 
											jinbichuxian[a] = true;
											break;
										}
									}
									n3+=100;
								} 
								if(map.map_peng02[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x]==31) {
									map.map_peng02[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x] = 0;
									pengkuai[x_mali_kuai_up_coll_y][x_mali_kuai_up_coll_x] = 31;
									跳跃状态 = D;
									for(int a=0;a<flashN;a++) {
										if(!shitouchuxian[a]) {
											flash[a] = new GameFlash(1,x_mali_kuai_up_coll_x*map.kuaisize-mapmoveX,x_mali_kuai_up_coll_y*map.kuaisize); 
											shitouchuxian[a] = true;
											break;
										}
									}
								}
							} 
						}
					}
					break;
				}
				/******主角上跳跃死亡判断******/
				for(int i=0;i<enemyN;i++) {
					if(enemy[i] == null||enemy[i].sfkill)continue;
					else {
						switch (enemy[i].type) {
						case 0:
							if(!enemy[i].sfkill&&x_mali.collidesWith(enemy[i].sp_mogu01, true)) {
								index = 0;
								x_mali_state = kill;
								kill_state = U;
								sfkillstate = true;
								z = 0;
								mid_kill = miclei.musiclong("kill");
								miclei.musicstart(mid_kill);
							}
							break;
							
						case 1:
							if(!enemy[i].sfkill&&x_mali.collidesWith(enemy[i].sp_mogu02, true)) {
								index = 0;
								x_mali_state = kill;
								kill_state = U;
								sfkillstate = true;
								z = 0;
								mid_kill = miclei.musiclong("kill");
								miclei.musicstart(mid_kill);
							}
							break;
							
						case 2:
							if(!enemy[i].sfkill&&x_mali.collidesWith(enemy[i].sp_wugui, true)) {
								index = 0;
								x_mali_state = kill;
								kill_state = U;
								sfkillstate = true;
								z = 0;
								mid_kill = miclei.musiclong("kill");
								miclei.musicstart(mid_kill);
							}
							break;
						}
					}
				}
				/******主角上跳跃地图移动判断******/
				switch (GameMap.guan) {
				case 0:
					if(map.til_back01.getX()>-2970&&x_mali.getX()>=198) {
						map.til_back01.move(-malisudu, 0);
						map.til_peng01.move(-malisudu, 0);
						map.til_zhebi01.move(-malisudu, 0);
						mapmoveX+=malisudu;
						x_mali.move(-malisudu, 0);
						/******主角上跳跃怪物移动判断******/
						for(int i=0;i<enemyN;i++) {
							if(enemy[i] == null)continue;
							else {
								switch (enemy[i].type) {
								case 0:		enemy[i].sp_mogu01.move(-malisudu, 0);		break;						
								case 1:		enemy[i].sp_mogu02.move(-malisudu, 0);		break;						
								case 2:		enemy[i].sp_wugui.move(-malisudu, 0);		break;
								}
							}
						}
					}
					break;

				case 1:
					if(map.til_peng02.getX()>-2970&&x_mali.getX()>=198) {
						map.til_back02.move(-malisudu, 0);
						map.til_peng02.move(-malisudu, 0);
						map.til_zhebi02.move(-malisudu, 0);
						mapmoveX+=malisudu;
						x_mali.move(-malisudu, 0);
						/******主角上跳跃怪物移动判断******/
						for(int i=0;i<enemyN;i++) {
							if(enemy[i] == null)continue;
							else {
								switch (enemy[i].type) {
								case 0:		enemy[i].sp_mogu01.move(-malisudu, 0);		break;						
								case 1:		enemy[i].sp_mogu02.move(-malisudu, 0);		break;						
								case 2:		enemy[i].sp_wugui.move(-malisudu, 0);		break;
								}
							}
						}
					}
					break;
				}
				/******主角上跳跃到顶点或碰撞到地图状态转换******/
				switch (GameMap.guan) {
				case 0:
					if(index==jiasudu.length-2||(UZR_coll(map.til_peng01,map.map_peng01)||UZL_coll(map.til_peng01,map.map_peng01)))跳跃状态 = D;
					index++;
					break;

				case 1:
					if(index==jiasudu.length-2||(UZR_coll(map.til_peng02,map.map_peng02)||UZL_coll(map.til_peng02,map.map_peng02)))跳跃状态 = D;
					index++;
					break;
				}
				break;
				
			case DR:
			case DL:
			case D:
				x_mali.setFrame(4);
				/******主角下跳跃怪物死亡判断******/
				for(int i=0;i<enemyN;i++) {
					if(enemy[i] == null||enemy[i].sfkill)continue;
					else {
						switch (enemy[i].type) {
						case 0:
							if(!enemy[i].sfkill&&x_mali.collidesWith(enemy[i].sp_mogu01, true)) {
								enemy[i].sfkill =true;
								enemy[i].state = enemy[i].kill;
								x_mali_state = tiaoyue;
								跳跃状态 = U;
								index = 7;
								n3+=300;
							}
							break;
							
						case 1:
							if(!enemy[i].sfkill&&x_mali.collidesWith(enemy[i].sp_mogu02, true)) {
								enemy[i].sfkill =true;
								enemy[i].state = enemy[i].kill;
								x_mali_state = tiaoyue;
								跳跃状态 = U;
								index = 7;
								n3+=500;
							}
							break;
							
						case 2:
							if(!enemy[i].sfkill&&x_mali.collidesWith(enemy[i].sp_wugui, true)) {
								enemy[i].sfkill =true;
								enemy[i].state = enemy[i].kill;
								x_mali_state = tiaoyue;
								跳跃状态 = U;
								index = 7;
								n3+=800;
							}
							break;
						}
					}
				}
				/******主角下跳跃地图移动判断******/
				switch (GameMap.guan) {
				case 0:
					if(map.til_back01.getX()>-2970&&x_mali.getX()>=198) {
						map.til_back01.move(-malisudu, 0);
						map.til_peng01.move(-malisudu, 0);
						map.til_zhebi01.move(-malisudu, 0);
						mapmoveX+=malisudu;
						x_mali.move(-malisudu, 0);
						/******主角上跳跃怪物移动判断******/
						for(int i=0;i<enemyN;i++) {
							if(enemy[i] == null)continue;
							else {
								switch (enemy[i].type) {
								case 0:		enemy[i].sp_mogu01.move(-malisudu, 0);		break;						
								case 1:		enemy[i].sp_mogu02.move(-malisudu, 0);		break;						
								case 2:		enemy[i].sp_wugui.move(-malisudu, 0);		break;
								}
							}
						}
					}
					if(index==0)index = 1;
					/******主角下跳跃碰撞到地图设置主角坐标和状态转换******/
					if(DZR_coll(map.til_peng01,map.map_peng01)||DZL_coll(map.til_peng01,map.map_peng01)) {
						x_mali.setPosition(x_mali.getX(), (x_mali.getY()+x_mali_H)/map.kuaisize*map.kuaisize-x_mali_H+3);
						x_mali_state = zhanli;
						index = 1;
					}
					index--;
					break;

				case 1:
					if(map.til_peng02.getX()>-2970&&x_mali.getX()>=198) {
						map.til_back02.move(-malisudu, 0);
						map.til_peng02.move(-malisudu, 0);
						map.til_zhebi02.move(-malisudu, 0);
						mapmoveX+=malisudu;
						x_mali.move(-malisudu, 0);
						/******主角上跳跃怪物移动判断******/
						for(int i=0;i<enemyN;i++) {
							if(enemy[i] == null)continue;
							else {
								switch (enemy[i].type) {
								case 0:		enemy[i].sp_mogu01.move(-malisudu, 0);		break;						
								case 1:		enemy[i].sp_mogu02.move(-malisudu, 0);		break;						
								case 2:		enemy[i].sp_wugui.move(-malisudu, 0);		break;
								}
							}
						}
					}
					if(index==0)index = 1;
					/******主角下跳跃碰撞到地图设置主角坐标和状态转换******/
					if(DZR_coll(map.til_peng02,map.map_peng02)||DZL_coll(map.til_peng02,map.map_peng02)) {
						x_mali.setPosition(x_mali.getX(), (x_mali.getY()+x_mali_H)/map.kuaisize*map.kuaisize-x_mali_H+3);
						x_mali_state = zhanli;
						index = 1;
					}
					index--;
					break;
				}
				break;
			}
			break;
			
		case kill:
			/******主角复活******/
			if(z==90) {
				for(int i=0;i<enemyN;i++) {
					guaichuxian[i] = false;
					enemy[i] = null;
				}
				switch (GameMap.guan) {
				case 0:
					x_mali.setPosition(mali_X, mali_Y);
					map.til_back01.setPosition(0, 0);
					map.til_peng01.setPosition(0, 0);
					map.til_zhebi01.setPosition(0, 0);
					for(int i=0;i<map.map_peng01_h;i++) {
						for(int j=0;j<map.map_peng01_w;j++) {
							if(pengkuai[i][j]==0)continue;
							if(pengkuai[i][j]==56)map.map_peng01[i][j] = 56;
							if(pengkuai[i][j]==31)map.map_peng01[i][j] = 31;
						}
					}
					enemy[0] = new GameEnemy(0,330,297);
					enemy[1] = new GameEnemy(0,330,231);
					enemy[2] = new GameEnemy(0,330,363);
					guaichuxian[0] = true;
					guaichuxian[1] = true;
					guaichuxian[2] = true;				
					break;

				case 1:
					x_mali.setPosition(mali_X, mali_Y);
					map.til_back02.setPosition(0, 0);
					map.til_peng02.setPosition(0, 0);
					map.til_zhebi02.setPosition(0, 0);
					for(int i=0;i<map.map_peng02_h;i++) {
						for(int j=0;j<map.map_peng02_w;j++) {
							if(pengkuai[i][j]==0)continue;
							if(pengkuai[i][j]==56)map.map_peng02[i][j] = 56;
							if(pengkuai[i][j]==31)map.map_peng02[i][j] = 31;
						}
					}
					break;
				}
				DIR = R;
				miclei.musicstop(mid_kill);
				sfkillstate = false;
				sfkill = false;
				x_mali_arrout = false;
				x_mali_state = zhanli;
				mapmoveX = 0;
				gametime01 = 0;
				gametime02 = 100;
				mid_kill.close();
				mid_kill = null;
				System.gc();
			}
			z++;
			x_mali.setFrame(5);
			/******主角死亡判断******/
			if(sfkillstate&&!sfkill&&x_mali.getY()>325) {
				n1--;
				if(n1<=0) {
					x_mali_state = gameover;
					miclei.musicstart(mid_gameover);
				}
				z = 0;
				sfkill = true;
			}
			/******主角死亡逻辑******/
			switch (kill_state) {
			case U:
				if(index == jiasudu.length-2)kill_state = D;
				index++;
				break;
				
			case D:
				if(index == 0)index = 1;
				index--;
				break;
			}
			break;
			
		case gameover:
			z++;
			break;
		}
	}
	
	public void move() {
		/******怪物移动******/
		for(int i=0;i<enemyN;i++) {
			if(enemy[i] == null||enemy[i].sfkill)continue;
			else enemy[i].move();
		}
		for(int i=0;i<flashN;i++) {
			if(flash[i] == null)continue;
			else flash[i].move();
		}
		switch (x_mali_state) {
		case yidong:
			switch (GameMap.guan) {
			case 0:
				if(!DZ_coll(map.til_peng01,map.map_peng01)) {
					x_mali_state = tiaoyue;
					跳跃状态 = D;
					index = (byte)jiasudu.length;
				}
				switch (DIR) {
				case R:
					if((!RZU_coll(map.til_peng01,map.map_peng01)&&!RZD_coll(map.til_peng01,map.map_peng01))&&x_mali.getX()<220) {
						x_mali.move(malisudu, 0);
					}
					break;
					
				case L:
					if((!LZU_coll(map.til_peng01,map.map_peng01)&&!LZD_coll(map.til_peng01,map.map_peng01))&&x_mali.getX()>0) {
						x_mali.move(-malisudu, 0);
					}
					break;
				}
				break;

			case 1:
				if(!DZ_coll(map.til_peng02,map.map_peng02)) {
					x_mali_state = tiaoyue;
					跳跃状态 = D;
					index = (byte)jiasudu.length;
				}
				switch (DIR) {
				case R:
					if((!RZU_coll(map.til_peng02,map.map_peng02)&&!RZD_coll(map.til_peng02,map.map_peng02))&&x_mali.getX()<220)x_mali.move(malisudu, 0);
					break;
					
				case L:
					if((!LZU_coll(map.til_peng02,map.map_peng02)&&!LZD_coll(map.til_peng02,map.map_peng02))&&x_mali.getX()>0)x_mali.move(-malisudu, 0);
					break;
				}
				break;
			}
			break;
			
		case tiaoyue:
			switch (跳跃状态) {
			case U:		x_mali.move(0, -jiasudu[index]);		break;	
			case D:		x_mali.move(0, jiasudu[index]);		break;
			case UR:	x_mali.move(malisudu, -jiasudu[index]);		break;	
			case UL:	x_mali.move(-malisudu, -jiasudu[index]);		break;	
			case DR:	x_mali.move(malisudu, jiasudu[index]);		break;	
			case DL:	x_mali.move(-malisudu, jiasudu[index]);		break;	
			}
			break;
			
		case kill:
			switch (kill_state) {
			case U:		x_mali.move(0, -jiasudu[index]);		break;				
			case D:		x_mali.move(0, jiasudu[index]);		break;
			}
			break;
		}
	}
	
	boolean x_mali_arrout = false;
	int x_mali_kuai_up_coll_x,x_mali_kuai_up_coll_y;
	
	/*************碰撞检测点***********上中左***/
	public boolean UZL_coll(TiledLayer til,int tilarr[][]) {
		int x_mali_kuai_x = (x_mali.getX()+Math.abs(til.getX())+x_mali_W/2-5)/map.kuaisize;
		int x_mali_kuai_y = (x_mali.getY()+Math.abs(til.getY()))/map.kuaisize;
		int coll_kuai = 0;
		if(!x_mali_arrout) {
			try {
				coll_kuai = tilarr[x_mali_kuai_y][x_mali_kuai_x];
			} catch (RuntimeException e) {
				x_mali_arrout = true;
			}
		}
		if(coll_kuai!=0){
			x_mali_kuai_up_coll_x = x_mali_kuai_x;
			x_mali_kuai_up_coll_y = x_mali_kuai_y;
			return true;
		}
		else return false;
	}
	
	/*************碰撞检测点***********上中右***/
	public boolean UZR_coll(TiledLayer til,int tilarr[][]) {
		int x_mali_kuai_x = (x_mali.getX()+Math.abs(til.getX())+x_mali_W/2+5)/map.kuaisize;
		int x_mali_kuai_y = (x_mali.getY()+Math.abs(til.getY()))/map.kuaisize;
		int coll_kuai = 0;
		if(!x_mali_arrout) {
			try {
				coll_kuai = tilarr[x_mali_kuai_y][x_mali_kuai_x];
			} catch (RuntimeException e) {
				x_mali_arrout = true;
			}
		}
		if(coll_kuai!=0){
			x_mali_kuai_up_coll_x = x_mali_kuai_x;
			x_mali_kuai_up_coll_y = x_mali_kuai_y;
			return true;
		}
		else return false;
	}
	
	/*************碰撞检测点***********右中上***/
	public boolean RZU_coll(TiledLayer til,int tilarr[][]) {
		int x_mali_kuai_x = (x_mali.getX()+Math.abs(til.getX())+x_mali_W)/map.kuaisize;
		int x_mali_kuai_y = (x_mali.getY()+Math.abs(til.getY())+x_mali_H/2-8)/map.kuaisize;
		int coll_kuai = 0;
		if(!x_mali_arrout) {
			try {
				coll_kuai = tilarr[x_mali_kuai_y][x_mali_kuai_x];
			} catch (RuntimeException e) {
				x_mali_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;
		else return false;
	}
	
	/*************碰撞检测点***********左中上***/
	public boolean LZU_coll(TiledLayer til,int tilarr[][]) {
		int x_mali_kuai_x = (x_mali.getX()+Math.abs(til.getX()))/map.kuaisize;
		int x_mali_kuai_y = (x_mali.getY()+Math.abs(til.getY())+x_mali_H/2-8)/map.kuaisize;
		int coll_kuai = 0;
		if(!x_mali_arrout) {
			try {
				coll_kuai = tilarr[x_mali_kuai_y][x_mali_kuai_x];
			} catch (RuntimeException e) {
				x_mali_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;
		else return false;
	}
	
	/*************碰撞检测点***********下中***/
	public boolean DZ_coll(TiledLayer til,int tilarr[][]) {
		int x_mali_kuai_x = (x_mali.getX()+Math.abs(til.getX())+x_mali_W/2)/map.kuaisize;
		int x_mali_kuai_y = (x_mali.getY()+Math.abs(til.getY())+x_mali_H)/map.kuaisize;
		int coll_kuai = 0;
		if(!x_mali_arrout) {
			try {
				coll_kuai = tilarr[x_mali_kuai_y][x_mali_kuai_x];
			} catch (RuntimeException e) {
				x_mali_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;
		else return false;
	}
	
	/*************碰撞检测点***********下中左***/
	public boolean DZL_coll(TiledLayer til,int tilarr[][]) {
		int x_mali_kuai_x = (x_mali.getX()+Math.abs(til.getX())+x_mali_W/2-5)/map.kuaisize;
		int x_mali_kuai_y = (x_mali.getY()+Math.abs(til.getY())+x_mali_H)/map.kuaisize;
		int coll_kuai = 0;
		if(!x_mali_arrout) {
			try {
				coll_kuai = tilarr[x_mali_kuai_y][x_mali_kuai_x];
			} catch (RuntimeException e) {
				x_mali_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;
		else return false;
	}
	
	/*************碰撞检测点***********下中右***/
	public boolean DZR_coll(TiledLayer til,int tilarr[][]) {
		int x_mali_kuai_x = (x_mali.getX()+Math.abs(til.getX())+x_mali_W/2+5)/map.kuaisize;
		int x_mali_kuai_y = (x_mali.getY()+Math.abs(til.getY())+x_mali_H)/map.kuaisize;
		int coll_kuai = 0;
		if(!x_mali_arrout) {
			try {
				coll_kuai = tilarr[x_mali_kuai_y][x_mali_kuai_x];
			} catch (RuntimeException e) {
				x_mali_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;
		else return false;
	}
	
	
	/*************碰撞检测点***********右中下***/
	public boolean RZD_coll(TiledLayer til,int tilarr[][]) {
		int x_mali_kuai_x = (x_mali.getX()+Math.abs(til.getX())+x_mali_W)/map.kuaisize;
		int x_mali_kuai_y = (x_mali.getY()+Math.abs(til.getY())+x_mali_H/2+8)/map.kuaisize;
		int coll_kuai = 0;
		if(!x_mali_arrout) {
			try {
				coll_kuai = tilarr[x_mali_kuai_y][x_mali_kuai_x];
			} catch (RuntimeException e) {
				x_mali_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;
		else return false;
	}
	
	/*************碰撞检测点***********左中下***/
	public boolean LZD_coll(TiledLayer til,int tilarr[][]) {
		int x_mali_kuai_x = (x_mali.getX()+Math.abs(til.getX()))/map.kuaisize;
		int x_mali_kuai_y = (x_mali.getY()+Math.abs(til.getY())+x_mali_H/2+8)/map.kuaisize;
		int coll_kuai = 0;
		if(!x_mali_arrout) {
			try {
				coll_kuai = tilarr[x_mali_kuai_y][x_mali_kuai_x];
			} catch (RuntimeException e) {
				x_mali_arrout = true;
			}
		}
		if(coll_kuai!=0)return true;
		else return false;
	}
	
	public void baiye() {
		
	}
}
