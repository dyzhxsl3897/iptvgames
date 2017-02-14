package com.zhongdan.games.supermario;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class GameFlash {
	GameImage imglei = new GameImage();
	GameMap map = new GameMap();
	Sprite sp_jinbi,sp_shitou;
	final byte jinbi = 0,shitou = 1;
	final byte U = 2,D = 8;
	int DIR = U;
	Image img_Y_tu = imglei.imglong("MaLi");
	Image img_jinbi = Image.createImage(img_Y_tu, 30, 0, 11*6, 11, Sprite.TRANS_NONE);
	Image img_shitou = Image.createImage(img_Y_tu, 0, 105, 40*4, 40*2, Sprite.TRANS_NONE);
	int 地图移动量X;
	int type;
	int jibimove;//金币的移动偏移量
	int shitoumove;//石头的移动偏移量
	boolean sfxiaoshi;
	
	public GameFlash(int type,int x,int y) {
		this.type = type;
		switch (type) {
		case jinbi:
			sp_jinbi = new Sprite(img_jinbi,11,11);
			sp_jinbi.setPosition(x+5, y-11);
			break;

		case shitou:
			sp_shitou = new Sprite(img_shitou,40,40);
			sp_shitou.setPosition(x-10, y-25);
			break;
		}
	}
	
	public void pinat(Graphics g) {
		switch (type) {
		case jinbi:		sp_jinbi.paint(g);		break;
		case shitou:	sp_shitou.paint(g);		break;
		}
	}
	
	public void logic(int 地图移动量X) {
		switch (type) {
		case jinbi:
			sp_jinbi.nextFrame();
			switch (DIR) {
			case U:
				jibimove++;
				if(jibimove>=4)DIR = D;
				break;

			case D:
				jibimove--;
				if(jibimove<=0) {
					sp_jinbi.setVisible(false);
					jibimove = 0;
					sfxiaoshi = true;
				}
				break;
			}
			break;
			
		case shitou:
			switch (DIR) {
			case U:
				if(shitoumove%2==0)sp_shitou.nextFrame();
				shitoumove++;
				if(shitoumove>=4)DIR = D;
				break;

			case D:
				if(shitoumove%2==0)sp_shitou.nextFrame();
				shitoumove--;
				if(sp_shitou.getFrame()==7) {
					sp_shitou.setVisible(false);
					shitoumove = 0;
					sfxiaoshi = true;
				}
				break;
			}
			break;
		}
	}
	
	public void move() {
		switch (type) {
		case jinbi:
			switch (DIR) {
			case U:		sp_jinbi.move(0, -4);		break;
			case D:		sp_jinbi.move(0, 4);		break;
			}
			break;
			
		case shitou:
			switch (DIR) {
			case U:		sp_shitou.move(0, -3);		break;
			case D:		sp_shitou.move(0, 3);		break;
			}
			break;
		}
	}
}
