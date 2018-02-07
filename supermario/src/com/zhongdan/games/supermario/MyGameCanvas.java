package com.zhongdan.games.supermario;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.Player;
/********本源码下载自源码爱好者_www.codefans.net*********/

public class MyGameCanvas extends GameCanvas implements Runnable {
	final int W,H;
	MIDlet mid;
	boolean RUN = true;
	Player cg,gameback01,gameback02,menu;
	boolean gameback01start,gameback01stop;
	int jishi,hqy,RUNtime = 80;
	int ppx[] = {320,306,293,280,269,259,251,245,241,240,241,245,251,259,269,280,293,306,320,334,347,360,371,381,389,395,399,400,399,395,389,381,371,360,347,334};
	int ppy[] = {110,111,115,121,129,139,150,163,176,190,204,217,230,241,251,259,265,269,270,269,265,259,251,241,230,217,204,190,176,163,150,139,129,121,115,111};
	int pp_Array_index_1,pp_Array_index_2,pp_Array_index_3,pp_Array_index_4,pp_Array_index_5,pp_Array_index_6;
	String zhizuo_Array[] = {"总策划:","阿飞同学。","美术设计:", "史瑞克同学。","程序设计:", "忍者神龟同学。",
			"测试人员:","蜘蛛侠同学。","制作单位：","西安蓝硅通信技术有限公司。"};
	String jieshao_File = "从前在玛雅大陆上有着一个安静而又和平的小国家，在那个国家中，人们都非常的友好。在国家的中心" +
			"有一个名叫比特艾尔的花园城堡，城堡非常的美丽，满地都是鲜花，在这个国家的每个角落都能感受到城堡中鲜花" +			
			"的香味。这坐城堡的主人有一个女儿，拥有着天使般的面容。可能正是因为这位公主的美丽而带来了一次浩劫。在一" +
			"个漆黑的晚上乌云密布，天空中电闪雷鸣，突然从乌云中飞出一条黑色的巨龙抢走了公主，城堡的主人四下打听才得" +
			"知原来自己的女儿给巨龙带到了离城堡不远的龙牙谷中，为了就出他唯一的女儿，在城堡中粘贴告示，并许诺：谁要" +
			"是能救出公主就把公主许配给他。此时一位年轻人走出人群，来到城堡答应救出公主。这位年轻人他的名字就叫“玛丽”" +
			"。等到第二天玛丽就踏上了营救公主的旅程。。。。。。";
	GameImage imglei;
	GameMusic miclei;
	GameMap map;
	GamePlayer pla;
	Image img_logo,img_enemy,img_cai01,img_cai02,img_pp,img_help,img_zi,img_MaLi;
	final byte LOGO = 0, CG1 = 1,CG2 = 2,caidan = 3,HELP = 4,jieshao = 5,zhizuo = 6,kaishi = 7;
	byte gamestate = caidan;
	final byte U = 2,D = 8,R = 4,L = 6;
	byte hqDIR = 8;
	
	public MyGameCanvas(MIDlet mid) {
		super(false);
		this.setFullScreenMode(true);
		W = 640;
		H = 530;
		this.mid = mid; 
		imglei = new GameImage();
		miclei = new GameMusic();
		map = new GameMap();
		pla = new GamePlayer(map,imglei,miclei,W,H);
		cg = miclei.musiclong("CG");
		gameback01 = miclei.musiclong("gameback01");
		gameback02 = miclei.musiclong("gameback02");
		menu = miclei.musiclong("menu");
		gameback01.setLoopCount(-1);
		gameback02.setLoopCount(-1);
		menu.setLoopCount(-1);
//		img_logo = imglei.imglong("");
		img_cai01 = imglei.imglong("cai01");
		img_cai02 = imglei.imglong("cai02");
		img_pp = imglei.imglong("pp");
		img_enemy = imglei.imglong("emy");
		img_help = imglei.imglong("help");
		img_zi = imglei.imglong("zi");
		img_MaLi = imglei.imglong("MaLi");
		new Thread(this).start();
		
	}

	public void paint(Graphics g) {
		switch (gamestate) {
		case LOGO:	
			g.setColor(0);
			g.fillRect(0, 0, W, H);
			if(jishi<30) {
				if(jishi*8<=224) {
					g.setColor(-1);
					g.drawRect(8, 270, 224, 6);
					g.setColor(255,0,0);
					g.fillRect(8, 270, jishi*8, 6);
					g.drawRegion(img_MaLi, 20*(jishi%4), 14, 20, 27, Sprite.TRANS_NONE, jishi*8, 240, 20);
					g.drawString(String.valueOf(jishi*100/28)+"%", 120, 280, Graphics.TOP|Graphics.HCENTER);	
				}
			}
			if(jishi>=30) {
				img_logo = null;
				jishi = 0;
				gamestate = CG1;
			}
			break;
			
		case CG1:
			g.setColor(0);
			g.fillRect(0, 0, W, H);
			if(jishi<40) {
				g.drawRegion(img_MaLi, 20*(jishi%4), 41, 20, 27, Sprite.TRANS_MIRROR, 240-jishi*8, 246, 20);
				g.drawRegion(img_enemy, 71+23*(jishi%2), 96, 23, 18, Sprite.TRANS_NONE, 320-jishi*10, 250, 20);
			} 
			if(jishi>40&&jishi<85) {
				g.drawRegion(img_enemy, 71+23*(jishi%2), 96, 23, 18, Sprite.TRANS_NONE, -240+jishi*6, 250, 20);
				g.drawRegion(img_MaLi, 9*(jishi%2), 94, 9, 7, Sprite.TRANS_NONE, -400+jishi*8, 248+hqy, 20);
				g.drawRegion(img_MaLi, 20*(jishi%4), 14, 20, 27, Sprite.TRANS_NONE, -500+jishi*9, 242, 20);
			}
			if(jishi>=85) {
				System.gc();
				jishi = 0;
				gamestate = CG2;
			}
			break;
			
		case CG2:
			g.setColor(0);
			g.fillRect(0, 0, W, H);
			if(jishi<=30) {
				g.drawRegion(img_cai01, 0, 0, 114, 114, Sprite.TRANS_NONE, W/2, jishi*6-114,Graphics.TOP|Graphics.HCENTER);
			}
			if(jishi>30&&jishi<=66) {
				g.drawRegion(img_cai01, 0, 0, 114, 114, Sprite.TRANS_NONE, W/2, 180-114,Graphics.TOP|Graphics.HCENTER);
				g.drawRegion(img_pp, 55*5, 0, 55, 55, Sprite.TRANS_NONE, ppx[jishi-31], ppy[jishi-31]-25, Graphics.TOP|Graphics.HCENTER);
				if(jishi>36)g.drawRegion(img_pp, 55*2, 0, 55, 55, Sprite.TRANS_NONE, ppx[jishi-37], ppy[jishi-37]-25, Graphics.TOP|Graphics.HCENTER);
				if(jishi>42)g.drawRegion(img_pp, 55*3, 0, 55, 55, Sprite.TRANS_NONE, ppx[jishi-43], ppy[jishi-43]-25, Graphics.TOP|Graphics.HCENTER);
				if(jishi>48)g.drawRegion(img_pp, 55*0, 0, 55, 55, Sprite.TRANS_NONE, ppx[jishi-49], ppy[jishi-49]-25, Graphics.TOP|Graphics.HCENTER);
				if(jishi>54)g.drawRegion(img_pp, 55*4, 0, 55, 55, Sprite.TRANS_NONE, ppx[jishi-55], ppy[jishi-55]-25, Graphics.TOP|Graphics.HCENTER);
				if(jishi>60)g.drawRegion(img_pp, 55*1, 0, 55, 55, Sprite.TRANS_NONE, ppx[jishi-61], ppy[jishi-61]-25, Graphics.TOP|Graphics.HCENTER);
			}
			if(jishi>66) {
				g.drawRegion(img_cai01, 0, 0, 114, 114, Sprite.TRANS_NONE, W/2, 180-114,Graphics.TOP|Graphics.HCENTER);
				g.drawRegion(img_pp, 55*5, 0, 55, 55, Sprite.TRANS_NONE, 120, 40-25, Graphics.TOP|Graphics.HCENTER);
				g.drawRegion(img_pp, 55*1, 0, 55, 55, Sprite.TRANS_NONE, 51, 80-25, Graphics.TOP|Graphics.HCENTER);
				g.drawRegion(img_pp, 55*2, 0, 55, 55, Sprite.TRANS_NONE, 189, 80-25, Graphics.TOP|Graphics.HCENTER);
				g.drawRegion(img_pp, 55*0, 0, 55, 55, Sprite.TRANS_NONE, 120,200-25, Graphics.TOP|Graphics.HCENTER);
				g.drawRegion(img_pp, 55*4, 0, 55, 55, Sprite.TRANS_NONE, 51, 160-25, Graphics.TOP|Graphics.HCENTER);
				g.drawRegion(img_pp, 55*3, 0, 55, 55, Sprite.TRANS_NONE, 189, 160-25, Graphics.TOP|Graphics.HCENTER);
			}
			if(jishi<15) {
				g.drawRegion(img_cai02, 0, 0, 45, 49, Sprite.TRANS_NONE, jishi*4-60, 250, 20);
			}
			if(jishi>=15&&jishi<32) {
				g.drawRegion(img_cai02, 0, 0, 45, 49, Sprite.TRANS_NONE, 0, 250, 20);
				g.drawRegion(img_cai02, 46, 0, 107, 78, Sprite.TRANS_NONE, 35, jishi*20-410, 20);
				g.drawRegion(img_cai02, 0, 49, 45, 40, Sprite.TRANS_NONE, 120, jishi*20-368, 20);
			}
			if(jishi>=32&&jishi<66) {
				g.drawRegion(img_cai02, 0, 0, 45, 49, Sprite.TRANS_NONE, 0, 250, 20);
				g.drawRegion(img_cai02, 46, 0, 107, 78, Sprite.TRANS_NONE, 35, 222, 20);
				g.drawRegion(img_cai02, 0, 49, 45, 40, Sprite.TRANS_NONE, 120, 270, 20);
				g.drawRegion(img_cai02, 159, 0, 81, 74, Sprite.TRANS_NONE, 160, 235, 20);
			}
			if(jishi>=66) {
				jishi = 0;
				gamestate = caidan;
			}
			break;
			
		case caidan:
			g.setColor(0);
			g.fillRect(0, 0, W, H);
			g.drawRegion(img_cai02, 0, 0, 45, 49, Sprite.TRANS_NONE, 0+200, 250+120, 20);
			g.drawRegion(img_cai02, 46, 0, 107, 78, Sprite.TRANS_NONE, 35+200, 222+120, 20);
			g.drawRegion(img_cai02, 0, 49, 45, 40, Sprite.TRANS_NONE, 120+200, 270+120, 20);
			g.drawRegion(img_cai02, 159, 0, 81, 74, Sprite.TRANS_NONE, 160+200, 235+120, 20);
			g.drawRegion(img_cai01, 0, 0, 114, 114, Sprite.TRANS_NONE, W/2, 180-114+70,Graphics.TOP|Graphics.HCENTER);
			if(jishi == 0 && ppy[pp_Array_index_6+18]-25 == 175+70) {
				g.drawRegion(img_pp, 55*0, 55, 55, 55, Sprite.TRANS_NONE, ppx[pp_Array_index_6+18], ppy[pp_Array_index_6+18]-25, Graphics.TOP|Graphics.HCENTER);
			} else {
				if(pp_Array_index_6+18 > 35 ) {
					pp_Array_index_6 = -18;
				}
				if(pp_Array_index_6+18 < 0 ) {
					pp_Array_index_6 = 17;
				}
				g.drawRegion(img_pp, 55*0, 0, 55, 55, Sprite.TRANS_NONE, ppx[pp_Array_index_6+18], ppy[pp_Array_index_6+18]-25, Graphics.TOP|Graphics.HCENTER);
			}
			if(jishi == 4 && ppy[pp_Array_index_4+6]-25 == 175+70) {
				g.drawRegion(img_pp, 55*1, 55, 55, 55, Sprite.TRANS_NONE, ppx[pp_Array_index_4+6], ppy[pp_Array_index_4+6]-25, Graphics.TOP|Graphics.HCENTER);
			} else {
				if(pp_Array_index_4+6 > 35 ) {
					pp_Array_index_4 = -6;
				}
				if(pp_Array_index_4+6 < 0 ) {
					pp_Array_index_4 = 29;
				}
				g.drawRegion(img_pp, 55*1, 0, 55, 55, Sprite.TRANS_NONE, ppx[pp_Array_index_4+6], ppy[pp_Array_index_4+6]-25, Graphics.TOP|Graphics.HCENTER);
			}
			if(jishi == 2 && ppy[pp_Array_index_2+30]-25 == 175+70) {
				g.drawRegion(img_pp, 55*2, 55, 55, 55, Sprite.TRANS_NONE, ppx[pp_Array_index_2+30], ppy[pp_Array_index_2+30]-25, Graphics.TOP|Graphics.HCENTER);
			} else {
				if(pp_Array_index_2+30 > 35 ) {
					pp_Array_index_2 = -30;
				}
				if(pp_Array_index_2+30 < 0 ) {
					pp_Array_index_2 = 5;
				}
				g.drawRegion(img_pp, 55*2, 0, 55, 55, Sprite.TRANS_NONE, ppx[pp_Array_index_2+30], ppy[pp_Array_index_2+30]-25, Graphics.TOP|Graphics.HCENTER);
			}
			if(jishi == 1 && ppy[pp_Array_index_1+24]-25 == 175+70) {
				g.drawRegion(img_pp, 55*3, 55, 55, 55, Sprite.TRANS_NONE, ppx[pp_Array_index_1+24], ppy[pp_Array_index_1+24]-25, Graphics.TOP|Graphics.HCENTER);
			} else {
				if(pp_Array_index_1+24 > 35 ) {
					pp_Array_index_1 = -24;
				}
				if(pp_Array_index_1+24 < 0 ) {
					pp_Array_index_1 = 11;
				}
				g.drawRegion(img_pp, 55*3, 0, 55, 55, Sprite.TRANS_NONE, ppx[pp_Array_index_1+24], ppy[pp_Array_index_1+24]-25, Graphics.TOP|Graphics.HCENTER);
			}
			if(jishi == 5 && ppy[pp_Array_index_5+12]-25 == 175+70) {
				g.drawRegion(img_pp, 55*4, 55, 55, 55, Sprite.TRANS_NONE, ppx[pp_Array_index_5+12], ppy[pp_Array_index_5+12]-25, Graphics.TOP|Graphics.HCENTER);
			} else {
				if(pp_Array_index_5+12 > 35 ) {
					pp_Array_index_5 = -12;
				}
				if(pp_Array_index_5+12 < 0 ) {
					pp_Array_index_5 = 23;
				}
				g.drawRegion(img_pp, 55*4, 0, 55, 55, Sprite.TRANS_NONE, ppx[pp_Array_index_5+12], ppy[pp_Array_index_5+12]-25, Graphics.TOP|Graphics.HCENTER);
			}
			if(jishi == 3 && ppy[pp_Array_index_3]-25 == 175+70) {
				g.drawRegion(img_pp, 55*5, 55, 55, 55, Sprite.TRANS_NONE, ppx[pp_Array_index_3], ppy[pp_Array_index_3]-25, Graphics.TOP|Graphics.HCENTER);
			} else {
				if(pp_Array_index_3 > 35 ) {
					pp_Array_index_3 = 0;
				}
				if(pp_Array_index_3 < 0 ) {
					pp_Array_index_3 = 35;
				}
				g.drawRegion(img_pp, 55*5, 0, 55, 55, Sprite.TRANS_NONE, ppx[pp_Array_index_3], ppy[pp_Array_index_3]-25, Graphics.TOP|Graphics.HCENTER);
			}
			break;
			
		case HELP:
			g.setColor(0);
			g.fillRect(0, 0, W, H);
			g.drawImage(img_help, W/2, 20, Graphics.TOP|Graphics.HCENTER);
			g.drawRegion(img_zi, 50, 42, 50, 43, Sprite.TRANS_NONE, 120, 268, Graphics.TOP|Graphics.HCENTER);
			break;
			
		case jieshao:
			g.setColor(0);
			g.fillRect(0, 0, W, H);
			g.drawRegion(img_zi, 50, 42, 50, 43, Sprite.TRANS_NONE, 500, 450, Graphics.TOP|Graphics.HCENTER);
			g.setColor(-1);
			g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE));
			g.setClip(0, 0, W, H);
			g.drawSubstring(jieshao_File, 0, 17-2, 28+200, 268+17*0-jishi+70, 20);
			for(int i=1;i<=jieshao_File.length()/17;i++) {
				if(i == jieshao_File.length()/17) {
					g.drawSubstring(jieshao_File, 17*i-2, jieshao_File.length()%17+2, 0+200, 268+17*i-jishi+70, 20);
				} else {
					g.drawSubstring(jieshao_File, 17*i-2, 17, 0+200, 268+17*i-jishi+70, 20);
				}
			}
			g.setClip(0, 0, W, H);
			if(jishi>=614)RUNtime = 80;
			break;
			
		case zhizuo:
			g.setColor(0);
			g.fillRect(0, 0, W, H);
			g.setColor(-1);
			g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
			if(jishi<4) {
				g.drawSubstring(zhizuo_Array[0], 0, jishi+1, 40+200, 20+70, 20);
			}
			if(jishi>=4&&jishi<9) {
				g.drawString(zhizuo_Array[0], 40+200, 20+70, 20);
				g.drawSubstring(zhizuo_Array[1], 0, jishi-4+1, 80+200, 40+70, 20);
			}
			if(jishi>=9&&jishi<14) {
				g.drawString(zhizuo_Array[0], 40+200, 20+70, 20);
				g.drawString(zhizuo_Array[1], 80+200, 40+70, 20);
				g.drawSubstring(zhizuo_Array[2], 0, jishi-9+1, 40+200, 60+70, 20);
			}
			if(jishi>=14&&jishi<20) {
				g.drawString(zhizuo_Array[0], 40+200, 20+70, 20);
				g.drawString(zhizuo_Array[1], 80+200, 40+70, 20);
				g.drawString(zhizuo_Array[2], 40+200, 60+70, 20);
				g.drawSubstring(zhizuo_Array[3], 0, jishi-14+1, 80+200, 80+70, 20);
			}
			if(jishi>=20&&jishi<25) {
				g.drawString(zhizuo_Array[0], 40+200, 20+70, 20);
				g.drawString(zhizuo_Array[1], 80+200, 40+70, 20);
				g.drawString(zhizuo_Array[2], 40+200, 60+70, 20);
				g.drawString(zhizuo_Array[3], 80+200, 80+70, 20);
				g.drawSubstring(zhizuo_Array[4], 0, jishi-20+1, 40+200, 100+70, 20);
			}
			if(jishi>=25&&jishi<32) {
				g.drawString(zhizuo_Array[0], 40+200, 20+70, 20);
				g.drawString(zhizuo_Array[1], 80+200, 40+70, 20);
				g.drawString(zhizuo_Array[2], 40+200, 60+70, 20);
				g.drawString(zhizuo_Array[3], 80+200, 80+70, 20);
				g.drawString(zhizuo_Array[4], 40+200, 100+70, 20);
				g.drawSubstring(zhizuo_Array[5], 0, jishi-25+1, 80+200, 120+70, 20);
			}
			if(jishi>=32&&jishi<37) {
				g.drawString(zhizuo_Array[0], 40+200, 20+70, 20);
				g.drawString(zhizuo_Array[1], 80+200, 40+70, 20);
				g.drawString(zhizuo_Array[2], 40+200, 60+70, 20);
				g.drawString(zhizuo_Array[3], 80+200, 80+70, 20);
				g.drawString(zhizuo_Array[4], 40+200, 100+70, 20);
				g.drawString(zhizuo_Array[5], 80+200, 120+70, 20);
				g.drawSubstring(zhizuo_Array[6], 0, jishi-32+1, 40+200, 140+70, 20);
			}
			if(jishi>=37&&jishi<43) {
				g.drawString(zhizuo_Array[0], 40+200, 20+70, 20);
				g.drawString(zhizuo_Array[1], 80+200, 40+70, 20);
				g.drawString(zhizuo_Array[2], 40+200, 60+70, 20);
				g.drawString(zhizuo_Array[3], 80+200, 80+70, 20);
				g.drawString(zhizuo_Array[4], 40+200, 100+70, 20);
				g.drawString(zhizuo_Array[5], 80+200, 120+70, 20);
				g.drawString(zhizuo_Array[6], 40+200, 140+70, 20);
				g.drawSubstring(zhizuo_Array[7], 0, jishi-37+1, 80+200, 160+70, 20);
			}
			if(jishi>=43&&jishi<48) {
				g.drawString(zhizuo_Array[0], 40+200, 20+70, 20);
				g.drawString(zhizuo_Array[1], 80+200, 40+70, 20);
				g.drawString(zhizuo_Array[2], 40+200, 60+70, 20);
				g.drawString(zhizuo_Array[3], 80+200, 80+70, 20);
				g.drawString(zhizuo_Array[4], 40+200, 100+70, 20);
				g.drawString(zhizuo_Array[5], 80+200, 120+70, 20);
				g.drawString(zhizuo_Array[6], 40+200, 140+70, 20);
				g.drawString(zhizuo_Array[7], 80+200, 160+70, 20);
				g.drawSubstring(zhizuo_Array[8], 0, jishi-43+1, 40+200, 180+70, 20);
			}
			if(jishi>=48&&jishi<61) {
				g.drawString(zhizuo_Array[0], 40+200, 20+70, 20);
				g.drawString(zhizuo_Array[1], 80+200, 40+70, 20);
				g.drawString(zhizuo_Array[2], 40+200, 60+70, 20);
				g.drawString(zhizuo_Array[3], 80+200, 80+70, 20);
				g.drawString(zhizuo_Array[4], 40+200, 100+70, 20);
				g.drawString(zhizuo_Array[5], 80+200, 120+70, 20);
				g.drawString(zhizuo_Array[6], 40+200, 140+70, 20);
				g.drawString(zhizuo_Array[7], 80+200, 160+70, 20);
				g.drawString(zhizuo_Array[8], 40+200, 180+70, 20);
				g.drawSubstring(zhizuo_Array[9], 0, jishi-48+1, 80+200, 200+70, 20);
			}
			if(jishi>=53) {
				RUNtime = 80;
				g.drawString(zhizuo_Array[0], 40+200, 20+70, 20);
				g.drawString(zhizuo_Array[1], 80+200, 40+70, 20);
				g.drawString(zhizuo_Array[2], 40+200, 60+70, 20);
				g.drawString(zhizuo_Array[3], 80+200, 80+70, 20);
				g.drawString(zhizuo_Array[4], 40+200, 100+70, 20);
				g.drawString(zhizuo_Array[5], 80+200, 120+70, 20);
				g.drawString(zhizuo_Array[6], 40+200, 140+70, 20);
				g.drawString(zhizuo_Array[7], 80+200, 160+70, 20);
				g.drawString(zhizuo_Array[8], 40+200, 180+70, 20);
				g.drawString(zhizuo_Array[9], 80+200, 200+70, 20);
				g.drawRegion(img_zi, 50, 42, 50, 43, Sprite.TRANS_NONE, 120+200, 268+70, Graphics.TOP|Graphics.HCENTER);
			}
			break;
			
		case kaishi:
			g.setColor(76,213,252);
			g.fillRect(0, 0, W, H);
			map.piant(g);
			pla.piant(g);
			if(pla.x_mali_state!=pla.kill&&pla.x_mali_state!=pla.gameover) {
				switch (GameMap.guan) {
				case 0:		map.til_zhebi01.paint(g);		break;
				case 1:		map.til_zhebi02.paint(g);		break;
				}
				g.drawRegion(img_zi, 0, 85, 40, 27, Sprite.TRANS_NONE, 600, 27, 20);
			}
			break;
		}
	}
	
	public void logic() {
		switch (gamestate) {
		case LOGO:
			if(jishi>140)jishi=0;
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {}
			jishi++; 
			break;
			
		case CG1: 
			if(cg.getState()!=Player.STARTED)miclei.musicstart(cg);
			if(jishi>140)jishi=0;
			if(jishi>40) {
				switch (hqDIR) {
				case U:		hqy-=5;		break;
				case D:		hqy+=5;		break;
				}
				if(hqy==15)hqDIR=U;
				if(hqy==0)hqDIR=D;
			}
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {}
			jishi++; 
			break;
			
		case CG2:
			if(jishi>140)jishi=0;
			jishi++;
			break;
			
		case jieshao:
			if(jishi>615)jishi=0;
			jishi++;
			break;
			
		case caidan:
			if(xiang_L) {
				pp_Array_index_1++;
				pp_Array_index_2++;
				pp_Array_index_3++;
				pp_Array_index_4++;
				pp_Array_index_5++;
				pp_Array_index_6++;
				if(pp_Array_index_1%6==0&&pp_Array_index_2%6==0&&pp_Array_index_3%6==0&&
						pp_Array_index_4%6==0&&pp_Array_index_5%6==0&&pp_Array_index_6%6==0) {
					xiang_L = false;
				}
			}
			if(xiang_R) {
				pp_Array_index_1--;
				pp_Array_index_2--;
				pp_Array_index_3--;
				pp_Array_index_4--;
				pp_Array_index_5--;
				pp_Array_index_6--;
				if(pp_Array_index_1%6==0&&pp_Array_index_2%6==0&&pp_Array_index_3%6==0&&
						pp_Array_index_4%6==0&&pp_Array_index_5%6==0&&pp_Array_index_6%6==0) {
					xiang_R = false;
				}
			}
			miclei.musicstop(gameback01);
			if(cg.getState()==Player.STARTED)miclei.musicstop(cg);
			if(menu.getState()!=Player.STARTED)miclei.musicstart(menu);
			break;
			
		case zhizuo:
			if(jishi>120)jishi=0;
			jishi++;
			break;
			
		case kaishi:
			if(menu.getState()==Player.STARTED)miclei.musicstop(menu);
			if(gameback01.getState()!=Player.STARTED&&!pla.sfkillstate) {
				miclei.musicstart(gameback01);
				gameback01stop = false;
			} else if(pla.sfkillstate&&!gameback01stop) {
				miclei.musicstop(gameback01);
				gameback01stop = true;
			}
			pla.logic();
			map.logic();
			if(pla.x_mali_state==pla.gameover&&pla.z==120) {
				gamestate = caidan;
				GameMap.guan = GameMap.one;
				pla.x_mali.setPosition(pla.mali_X,pla.mali_Y);
				map.til_back01.setPosition(0, 0);
				map.til_peng01.setPosition(0, 0);
				map.til_zhebi01.setPosition(0, 0);
				map.til_back02.setPosition(0, 0);
				map.til_peng02.setPosition(0, 0);
				map.til_zhebi02.setPosition(0, 0);
				for(int i=0;i<map.map_peng01_h;i++) {
					for(int j=0;j<map.map_peng01_w;j++) {
						if(pla.pengkuai[i][j]==0)continue;
						if(pla.pengkuai[i][j]==56)map.map_peng01[i][j] = 56;
						if(pla.pengkuai[i][j]==31)map.map_peng01[i][j] = 31;
					}
				}
				for(int i=0;i<map.map_peng01_h;i++) {
					for(int j=0;j<map.map_peng01_w;j++) {
						if(pla.pengkuai[i][j]==0)continue;
						pla.pengkuai[i][j]=0;
					}
				}
				for(int i=0;i<pla.enemyN;i++) {
					pla.guaichuxian[i] = false;
					pla.enemy[i] = null;
				}
				pla.DIR = pla.R;
				pla.sfkillstate = false;
				pla.sfkill = false;
				pla.x_mali_arrout = false;
				pla.x_mali_state = pla.zhanli;
				pla.mapmoveX = 0;
				pla.gametime01 = 0;
				pla.gametime02 = 100;
				pla.n1 = 5;
				pla.n2 = 0;
				pla.n3 = 0;
				pla.z = 0;
				pla.d_guan = 1;
				pla.x_guan = 1;
			}
			break;
		}
	}

	public void move() {
		switch (gamestate) {
		case kaishi:
			pla.move();
			break;
		}
	}
	
	boolean xiang_L = false;
	boolean xiang_R = false;
	public void keyPressed(int keyCode) {
		int key = this.getGameAction(keyCode);
		switch (gamestate) {
		case caidan:
			switch (key) {
			case Canvas.RIGHT:
				if(!xiang_R&&!xiang_L) {
					xiang_R = true;
					if(jishi>=5)jishi = -1;	
					jishi++;
				}
				break;	
				
			case Canvas.LEFT:
				if(!xiang_L&&!xiang_R) {
					xiang_L = true;
					if(jishi<=0)jishi = 6;	
					jishi--;
				}
				break;
				
			case Canvas.FIRE:
				switch (jishi) {
				/*********开始*************/
				case 0:
					if(!xiang_L&&!xiang_R) {
						jishi = 0;
						RUNtime = 40;
						gamestate = kaishi;			
					}
					break;
				/*********音乐*************/
				case 1:
					if(!xiang_L&&!xiang_R) {
						
					}
					break;
				/*********介绍*************/
				case 2:
					if(!xiang_L&&!xiang_R) {
						jishi = 0;
						gamestate = jieshao;	
					}
					break;
				/*********退出*************/
				case 3:
					if(!xiang_L&&!xiang_R) {
						RUN = false;
					}
					break;
				/*********帮助*************/
				case 4:
					if(!xiang_L&&!xiang_R) {
//						jishi = 0;	
//						gamestate = HELP;	
					}
					break;
				/*********制作组************/
				case 5:
					if(!xiang_L&&!xiang_R) {
						jishi = 0;
						gamestate = zhizuo;	
					}
					break;
				}
			}
			break;
			
		case HELP:
			switch (key) {
			case Canvas.FIRE:		
				jishi = 0;	
				pp_Array_index_1 = 0;
				pp_Array_index_2 = 0;
				pp_Array_index_3 = 0;
				pp_Array_index_4 = 0;
				pp_Array_index_5 = 0;
				pp_Array_index_6 = 0;
				gamestate = caidan;
				break;
			}
			break;
			
		case zhizuo:
			switch (key) {
			case Canvas.FIRE:	
				RUNtime = 40;
				if(jishi>53) {
					jishi = 0;	
					pp_Array_index_1 = 0;
					pp_Array_index_2 = 0;
					pp_Array_index_3 = 0;
					pp_Array_index_4 = 0;
					pp_Array_index_5 = 0;
					pp_Array_index_6 = 0;
					RUNtime = 80;
					gamestate = caidan;
				}
				break;
			}
			break;
			
		case jieshao:
			switch (key) {
			case Canvas.FIRE:	
				RUNtime = 40;
				if(jishi>80) {
					pp_Array_index_1 = 0;
					pp_Array_index_2 = 0;
					pp_Array_index_3 = 0;
					pp_Array_index_4 = 0;
					pp_Array_index_5 = 0;
					pp_Array_index_6 = 0;
					jishi = 0;
					RUNtime = 80;
					gamestate = caidan;	
				}
				break;
			}
		case kaishi:
			if(keyCode == -7) {
				jishi = 0;	
				pp_Array_index_1 = 0;
				pp_Array_index_2 = 0;
				pp_Array_index_3 = 0;
				pp_Array_index_4 = 0;
				pp_Array_index_5 = 0;
				pp_Array_index_6 = 0;
				RUNtime = 80;
				gamestate = caidan;
			}
//			if(keyCode == KEY_NUM1&&pla.x_mali_state!=pla.跳跃) {
//				pla.x_mali_state = pla.跳跃;
////				pla.大玛丽状态 = pla.跳跃;
//				pla.跳跃状态 = pla.U;
////				pla.x_mali.move(pla.玛丽移动速度, 0);
//			}
//			if(keyCode == KEY_NUM3&&pla.x_mali_state!=pla.跳跃) {
//				System.out.println("54");
//			}
			break;
		}
	}

	public void key2() {
		switch (gamestate) {
		case kaishi:
			if(pla.x_mali_state!=pla.gameover&&pla.x_mali_state!=pla.kill) {
				if((this.getKeyStates()&GameCanvas.RIGHT_PRESSED)!=0)//防止线程不同步问题
					if((this.getKeyStates()&GameCanvas.RIGHT_PRESSED)!=0&&pla.x_mali_state!=pla.tiaoyue) {
						pla.x_mali_state = pla.yidong;
//						pla.大玛丽状态 = pla.移动;
						pla.DIR = pla.R;
					}
					if((this.getKeyStates()&GameCanvas.LEFT_PRESSED)!=0&&pla.x_mali_state!=pla.tiaoyue) {
						pla.x_mali_state = pla.yidong;
//						pla.大玛丽状态 = pla.移动;
						pla.DIR = pla.L;
					}	
					if((this.getKeyStates()&GameCanvas.UP_PRESSED)!=0&&pla.x_mali_state!=pla.tiaoyue) {
						pla.x_mali_state = pla.tiaoyue;
//						pla.大玛丽状态 = pla.跳跃;
						pla.跳跃状态 = pla.U;
					}
					switch (pla.x_mali_state) {
					case 3:
						switch (GameMap.guan) {
						case 0:
							if((this.getKeyStates()&GameCanvas.RIGHT_PRESSED)!=0&&pla.x_mali.getX()<220) {
								if(!pla.RZU_coll(map.til_peng01,map.map_peng01)&&!pla.RZD_coll(map.til_peng01,map.map_peng01)) {
									pla.x_mali.move(pla.malisudu, 0);
								}
							}
							if((this.getKeyStates()&GameCanvas.LEFT_PRESSED)!=0&&pla.x_mali.getX() > 0) {
								if(!pla.LZU_coll(map.til_peng01,map.map_peng01)&&!pla.LZD_coll(map.til_peng01,map.map_peng01)) {
									pla.x_mali.move(-pla.malisudu, 0);
								}
							}
							break;

						case 1:
							if((this.getKeyStates()&GameCanvas.RIGHT_PRESSED)!=0&&pla.x_mali.getX()<220) {
								if(!pla.RZU_coll(map.til_peng02,map.map_peng02)&&!pla.RZD_coll(map.til_peng02,map.map_peng02)) {
									pla.x_mali.move(pla.malisudu, 0);
								}
							}
							if((this.getKeyStates()&GameCanvas.LEFT_PRESSED)!=0&&pla.x_mali.getX() > 0) {
								if(!pla.LZU_coll(map.til_peng02,map.map_peng02)&&!pla.LZD_coll(map.til_peng02,map.map_peng02)) {
									pla.x_mali.move(-pla.malisudu, 0);
								}
							}
							break;
						}
					}
					break;
			}
		}
	}
	
	public void keyReleased(int keyCode) {
		switch (gamestate) {
		case kaishi:
			switch (pla.x_mali_state) {
			case 1:
				pla.x_mali_state = pla.zhanli;
				break;
			}
			break;
		}
	}
	
	public void run() {
		while(RUN) {
			logic();
			move();
			key2();
			repaint();
			try {
				Thread.sleep(RUNtime);
			} catch (InterruptedException e) {}
		}
		if(!RUN)mid.notifyDestroyed();
	} 
}
