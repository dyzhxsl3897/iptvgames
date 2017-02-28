package com.zhongdan.games.tohellwithjohnny;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Game {
	public MainMenu mainmenu;
	public ManDown mandown;
	private Man G_man;
	private Image imgEmpty;
	public int man_mov_x = 13;
	public int man_mov_y = 13;
	public int obj_mov = 4;

	public Game(Man man) {
		this.G_man = man;
		this.mainmenu = new MainMenu(this);
	}

	public void startGame() {
		G_man.display.setCurrent(this.mainmenu);
	}

	public void startmandown() {
		this.mandown = new ManDown(this, G_man);
		setgame(man_mov_x, man_mov_y, obj_mov);
		G_man.display.setCurrent(this.mandown);
	}

	public void exitGame() {
		G_man.destroyApp(false);
		G_man.notifyDestroyed();
	}

	public void setgame(int manmovx, int manmovy, int objmov) {
		this.mandown.MAN_MOV_X = manmovx;
		this.mandown.MAN_MOV_Y = manmovy;
		this.mandown.OBJ_MOV = objmov;
	}

	public void myDrawRegion(Graphics graphics, Image imgSrc, int SrcX, int SrcY, int TargetX, int TargetY, int TargetWidth, int TargetHeight) {
		graphics.setClip(TargetX, TargetY, TargetWidth, TargetHeight);
		graphics.drawImage(imgSrc, TargetX - SrcX, TargetY - SrcY, Graphics.LEFT | Graphics.TOP);
	}
}
