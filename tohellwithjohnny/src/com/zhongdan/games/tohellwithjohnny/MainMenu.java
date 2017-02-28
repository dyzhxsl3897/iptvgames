package com.zhongdan.games.tohellwithjohnny;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

public class MainMenu extends Canvas {

	private int SCREEN_WIDTH = this.getWidth();
	private int SCREEN_HEIGHT = this.getHeight();
	public int menuindex = 0;
	private int setindex = 0;
	private Game game;
	private String menustr[] = new String[4];
	private String setstr[] = new String[4];

	public MainMenu(Game game) {
		this.game = game;
		try {
			setFullScreenMode(true);
			menustr[0] = "开始";
			menustr[1] = "设置";
			menustr[2] = "退出";
			menustr[3] = "关于";
			setstr[0] = "简单";
			setstr[1] = "普通";
			setstr[2] = "困难";
			setstr[3] = "返回";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void keyPressed(int key) {
		if (menuindex < 5) {
			switch (getGameAction(key)) {
			case Canvas.UP:
				menuindex--;
				if (menuindex < 0)
					menuindex = 3;
				break;
			case Canvas.DOWN:
				menuindex++;
				if (menuindex > 3)
					menuindex = 0;
				break;
			case Canvas.FIRE:
				switch (menuindex) {
				case 0:
					// 开始游戏
					this.game.startmandown();
					break;
				case 1:
					// 设置难度
					menuindex = 5;
					break;
				case 2:
					// 退出
					this.game.exitGame();
					break;
				case 3:
					// 关于
					menuindex = 4;
					break;
				case 4:
					// 从关于中退出
					menuindex = 3;
					break;
				}
				break;
			}
		} else {
			switch (getGameAction(key)) {
			case Canvas.UP:
				setindex--;
				if (setindex < 0)
					setindex = 3;
				break;
			case Canvas.DOWN:
				setindex++;
				if (setindex > 3)
					setindex = 0;
				break;
			case Canvas.FIRE:
				switch (setindex) {
				case 0:
					// 简单
					this.game.man_mov_x = 3;
					this.game.man_mov_y = 3;
					this.game.obj_mov = 2;
					this.game.startmandown();
					break;
				case 1:
					// 一般
					this.game.man_mov_x = 3;
					this.game.man_mov_y = 3;
					this.game.obj_mov = 4;
					this.game.startmandown();
					break;
				case 2:
					// 困难
					this.game.man_mov_x = 4;
					this.game.man_mov_y = 4;
					this.game.obj_mov = 5;
					this.game.startmandown();
					break;
				case 3:
					// 返回
					setindex = 0;
					menuindex = 1;
					break;

				}
				break;
			}
		}
		this.repaint();
	}

	protected void sizeChanged(int w, int h) {
		SCREEN_WIDTH = w;
		SCREEN_HEIGHT = h;
	}

	public void paint(Graphics graphics) {
		if (menuindex < 4) {
			graphics.setColor(96, 57, 19);
			graphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			graphics.clipRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			for (int i = 0; i < 4; i++) {
				if (i == menuindex) {
					graphics.setColor(218, 218, 218);
					graphics.drawString(menustr[i], this.getWidth() / 2, this.getHeight() / 6 * (1 + i), Graphics.HCENTER | Graphics.TOP);
				} else {
					graphics.setColor(198, 156, 109);
					graphics.drawString(menustr[i], this.getWidth() / 2, this.getHeight() / 6 * (1 + i), Graphics.HCENTER | Graphics.TOP);
				}
			}
		} else if (menuindex == 4) {
			graphics.setColor(96, 57, 19);
			graphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			graphics.clipRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			graphics.setColor(198, 156, 109);
			graphics.drawString("雨石工作室", this.getWidth() / 2, this.getHeight() / 5 * 1, Graphics.HCENTER | Graphics.TOP);
			graphics.drawString("设计开发", this.getWidth() / 2, this.getHeight() / 5 * 2, Graphics.HCENTER | Graphics.TOP);
		} else if (menuindex == 5) {
			graphics.setColor(96, 57, 19);
			// graphics.fillRect(0,0,this.getWidth(),this.getHeight());
			// graphics.clipRect(0,0,this.getWidth(),this.getHeight());
			graphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			graphics.clipRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			for (int i = 0; i < 4; i++) {
				if (i == setindex) {
					graphics.setColor(218, 218, 218);
					graphics.drawString(setstr[i], this.getWidth() / 2, this.getHeight() / 6 * (1 + i), Graphics.HCENTER | Graphics.TOP);
				} else {
					graphics.setColor(198, 156, 109);
					graphics.drawString(setstr[i], this.getWidth() / 2, this.getHeight() / 6 * (1 + i), Graphics.HCENTER | Graphics.TOP);
				}
			}
		}

	}

}
