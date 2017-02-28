package com.zhongdan.games.tohellwithjohnny;

import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public class ManDown extends GameCanvas implements Runnable {
	private static int MAN_WIDTH = 16;
	private static int MAN_HEIGHT = 16;
	private static int OBJ_WIDTH = 48;
	private static int OBJ_HEIGHT = 8;
	private static int SIDE_WIDTH = 8;
	private static int SIDE_HEIGHT = 16;
	private static int MAX_LIFE = 15;
	// private int SCREEN_WIDTH = this.getWidth();
	// private int SCREEN_HEIGHT = this.getHeight();
	private int SCREEN_WIDTH;
	private int SCREEN_HEIGHT;
	public int MAN_MOV_X;
	public int MAN_MOV_Y;
	public int OBJ_MOV;
	private Random rnd;
	private Game game;
	private Man man;
	private Image I_man, I_obj1, I_obj2, I_side, I_top;
	private Image image_temp;
	private Thread thread;
	private int man_picIndex = 0;
	public int position_obj_x[] = new int[16];
	public int position_obj_y[] = new int[16];
	public int position_man_x;
	public int position_man_y;
	public int position_side_y = 0;
	public int direction = 0;
	public int directionup = 0;
	public int directionhit = 0;
	public int time_num = 0;
	public int life;
	public int level = 0;
	public int life_change = 0;
	public int level_change = 0;
	private Image I_tmp[] = new Image[5];
	private int Int_tmp[] = new int[16];
	public boolean isgameover = false;
	public boolean running = true;

	public ManDown(Game game, Man man) {
		super(true);
		thread = new Thread(this);
		rnd = new Random();
		this.game = game;
		this.man = man;
		setFullScreenMode(true);
		SCREEN_WIDTH = this.getWidth();
		SCREEN_HEIGHT = this.getHeight();
		try {
			life = MAX_LIFE;
			I_man = Image.createImage("/man.png");
			I_obj1 = Image.createImage("/obj1.png");
			I_side = Image.createImage("/side.png");
			I_top = Image.createImage("/top.png");
			initobj(16);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		thread.start();
	}

	public void run() {
		try {
			while (running)
			// while (true)
			{
				man_picIndex++;
				man_picIndex = man_picIndex % 4;
				thread.sleep(100);
				inputs();
				changelevel(SCREEN_HEIGHT / 3);
				scrollside(OBJ_MOV, 14);
				objup(OBJ_MOV);
				this.repaint();
				// flushGraphics();
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	protected void sizeChanged(int w, int h) {
		SCREEN_WIDTH = w;
		SCREEN_HEIGHT = h;
	}

	// 产生物体的坐标以及物体
	public void initobj(int obj_num) {
		for (int i = 0; i < obj_num; i++) {
			if (i == 0) {
				position_obj_x[0] = (SCREEN_WIDTH - OBJ_WIDTH) / 2;
				position_obj_y[0] = SCREEN_HEIGHT;
				position_man_x = (SCREEN_WIDTH - MAN_WIDTH) / 2;
				position_man_y = SCREEN_HEIGHT - MAN_HEIGHT;
				Int_tmp[0] = 0;
			} else {
				position_obj_x[i] = SIDE_WIDTH + (rnd.nextInt() >>> 1) % (SCREEN_WIDTH / 12 - 1) * 12;
				if (position_obj_x[i] > SCREEN_WIDTH - OBJ_WIDTH - SIDE_WIDTH) {
					position_obj_x[i] = position_obj_x[i] - OBJ_WIDTH - SIDE_WIDTH;
				}
				position_obj_y[i] = position_obj_y[i - 1] + 30;
				Int_tmp[i] = (rnd.nextInt() >>> 1) % 6;
			}
		}
	}

	public void gameover() {
		/*
		 * if(isgameover == true) running = false;
		 */
	}

	public void changelevel(int levelHeight) {
		level_change++;
		if (level_change / levelHeight == 1) {
			level_change = 0;
			level++;
		}
	}

	public void scrollside(int chg_side, int max_chg) {
		position_side_y = position_side_y - chg_side;
		if (position_side_y < -max_chg)
			position_side_y = 0;
	}

	public void objup(int chg_obj_y) {
		for (int i = 0; i < 16; i++) {
			// 坐标减小,物体上移
			position_obj_y[i] = position_obj_y[i] - chg_obj_y;
			// 使物体坐标循环出现
			if (position_obj_y[i] < 15) {
				Int_tmp[i] = (rnd.nextInt() >>> 1) % 6;
				if (i == 0) {
					position_obj_y[0] = position_obj_y[15] + 30;
					Int_tmp[0] = (rnd.nextInt() >>> 1) % 6;
				} else {
					position_obj_y[i] = position_obj_y[i - 1] + 30;
					Int_tmp[i] = (rnd.nextInt() >>> 1) % 6;
				}
				position_obj_x[i] = SIDE_WIDTH + (rnd.nextInt() >>> 1) % (SCREEN_WIDTH / 12 - 1) * 12;
				if (position_obj_x[i] > SCREEN_WIDTH - OBJ_WIDTH - SIDE_WIDTH) {
					position_obj_x[i] = position_obj_x[i] - OBJ_WIDTH - SIDE_WIDTH;
				}
			}
		}
	}

	// 画屏幕
	public void paint(Graphics graphics) {
		graphics.setColor(0, 0, 0);
		graphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		graphics.clipRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		graphics.setColor(255, 0, 0);
		graphics.drawString("生命: " + life, 10, 0, Graphics.LEFT | Graphics.TOP);
		graphics.drawString("层数: " + level, 70, 0, Graphics.LEFT | Graphics.TOP);
		if (isgameover) {
			level_change = level;
			graphics.drawString("GameOver", SCREEN_WIDTH / 2, SCREEN_HEIGHT * 1 / 3, Graphics.TOP | Graphics.HCENTER);
			graphics.drawString("层数:" + level_change, SCREEN_WIDTH / 2, SCREEN_HEIGHT * 1 / 3 + 20, Graphics.TOP | Graphics.HCENTER);

		} else {
			// 显示物体
			for (int i = 0; i < 16; i++) {
				draw_obj(graphics, I_obj1, Int_tmp[i], position_obj_x[i], position_obj_y[i]);
			}
		}
		// 顶端 左侧 右侧
		for (int i = 0; i < SCREEN_WIDTH / 8 + 1; i++) {
			game.myDrawRegion(graphics, I_top, 0, 0, 8 * i, 14, 8, 8);
		}
		for (int i = 0; i < SCREEN_HEIGHT / 16 + 1; i++) {
			game.myDrawRegion(graphics, I_side, 0, 0, 0, position_side_y + SIDE_HEIGHT * i, SIDE_WIDTH, SIDE_HEIGHT);
			game.myDrawRegion(graphics, I_side, SIDE_WIDTH, 0, SCREEN_WIDTH - SIDE_WIDTH, position_side_y + SIDE_HEIGHT * i, SIDE_WIDTH, SIDE_HEIGHT);
		}
		gameover();
		checkman(graphics);
		checkposition(graphics, 2, (MAN_MOV_Y + OBJ_MOV) / 2, MAN_MOV_Y);
		checktop(16, 3, MAN_MOV_Y);
		checkbottom(SCREEN_HEIGHT, MAN_MOV_Y);
		checkside();
		serviceRepaints();
	}

	// 检查状态以显示小人状态
	public void checkman(Graphics graphics) {
		if (directionup == 0) {
			switch (direction) {
			case 0:
				game.myDrawRegion(graphics, I_man, 8 * MAN_WIDTH, directionhit * 16, position_man_x, position_man_y, MAN_WIDTH, MAN_HEIGHT);
				break;
			case 1:
				game.myDrawRegion(graphics, I_man, directionhit * 4 * MAN_WIDTH + man_picIndex * MAN_WIDTH, 0, position_man_x, position_man_y,
						MAN_WIDTH, MAN_HEIGHT);
				break;
			case 2:
				game.myDrawRegion(graphics, I_man, directionhit * 4 * MAN_WIDTH + man_picIndex * MAN_WIDTH, 16, position_man_x, position_man_y,
						MAN_WIDTH, MAN_HEIGHT);
				break;
			}
		} else if (directionup == 1) {
			switch (direction) {
			case 0:
				game.myDrawRegion(graphics, I_man, directionhit * 4 * MAN_WIDTH + man_picIndex * MAN_WIDTH, 48, position_man_x, position_man_y,
						MAN_WIDTH, MAN_HEIGHT);
				break;
			case 1:
				game.myDrawRegion(graphics, I_man, directionhit * 4 * MAN_WIDTH + man_picIndex * MAN_WIDTH, 32, position_man_x, position_man_y,
						MAN_WIDTH, MAN_HEIGHT);
				break;
			case 2:
				game.myDrawRegion(graphics, I_man, directionhit * 4 * MAN_WIDTH + man_picIndex * MAN_WIDTH, 64, position_man_x, position_man_y,
						MAN_WIDTH, MAN_HEIGHT);
				break;
			}
		}

	}

	// 检查人物是否到达顶部
	public void checktop(int topposition, int lifevalue, int chg_man_y) {
		if (position_man_y <= topposition) {
			life = life - lifevalue;
			position_man_y = topposition + chg_man_y;
			directionhit = 1;
		}
		if (life < 1) {
			isgameover = true;
			position_man_y = SCREEN_HEIGHT + position_man_y;
		}
	}

	// 检查人物是否到达底部
	public void checkbottom(int bottomposition, int chg_man_y) {
		if (position_man_y > bottomposition - 16) {
			life = 0;
			isgameover = true;
			position_man_y = bottomposition + MAN_HEIGHT + chg_man_y;
		}

	}

	public void checkside() {
		if (position_man_x < 9) {
			position_man_x = 8;
		}

		if (position_man_x > SCREEN_WIDTH - 16 - 9) {
			position_man_x = SCREEN_WIDTH - 16 - 8;
		}
	}

	public void checkposition(Graphics graphics, int nearly_x, int nearly_y, int chg_man_y) {
		int check_num = 1;
		for (int i = 0; i < 16; i++) {
			if (position_man_x + MAN_WIDTH - nearly_x >= position_obj_x[i] && position_man_x <= position_obj_x[i] + OBJ_WIDTH - nearly_x) {
				switch (Int_tmp[i]) {
				case 0:// 平板
					if (position_obj_y[i] - position_man_y - MAN_HEIGHT >= -nearly_y && position_obj_y[i] - position_man_y - MAN_HEIGHT <= nearly_y) {
						life_change++;
						if (life_change == 1 && life < 15)
							life++;
						position_man_y = position_obj_y[i] - 16;
						position_man_y = position_man_y - chg_man_y;
						check_num = 0;
						directionup = 0;
						directionhit = 0;
					}
					break;
				case 1:// 右滚板
					if (position_obj_y[i] - position_man_y - MAN_HEIGHT >= -nearly_y && position_obj_y[i] - position_man_y - MAN_HEIGHT <= nearly_y) {
						life_change++;
						if (life_change == 1 && life < MAX_LIFE)
							life++;
						position_man_y = position_obj_y[i] - 16;
						position_man_y = position_man_y - chg_man_y;
						check_num = 0;
						position_man_x++;
						directionup = 0;
						directionhit = 0;
					}
					break;
				case 2:// 左滚板
					if (position_obj_y[i] - position_man_y - MAN_HEIGHT >= -nearly_y && position_obj_y[i] - position_man_y - MAN_HEIGHT <= nearly_y) {
						life_change++;
						if (life_change == 1 && life < MAX_LIFE)
							life++;
						position_man_y = position_obj_y[i] - 16;
						position_man_y = position_man_y - chg_man_y;
						check_num = 0;
						position_man_x--;
						directionup = 0;
						directionhit = 0;
					}
					break;
				case 3:// 翻板
					if (position_obj_y[i] - position_man_y - MAN_HEIGHT >= -nearly_y && position_obj_y[i] - position_man_y - MAN_HEIGHT <= nearly_y) {
						life_change++;
						if (life_change == 1 && life < MAX_LIFE)
							life++;
						position_man_y = position_obj_y[i] - MAN_HEIGHT;
						position_man_y = position_man_y - chg_man_y;
						check_num = 0;
						time_num++;
						directionup = 0;
						directionhit = 0;
						if (time_num == 1) {
							game.myDrawRegion(graphics, I_obj1, 0, 96, position_obj_x[i], position_obj_y[i] - 3, 48, 14);
						} else if (time_num == 2) {
							game.myDrawRegion(graphics, I_obj1, 0, 110, position_obj_x[i], position_obj_y[i] - 4, 48, 16);
						} else if (time_num == 3) {
							game.myDrawRegion(graphics, I_obj1, 0, 126, position_obj_x[i], position_obj_y[i] - 3, 48, 15);
						} else if (time_num == 4) {
							game.myDrawRegion(graphics, I_obj1, 0, 88, position_obj_x[i], position_obj_y[i], 48, 8);
							position_man_y = position_man_y + chg_man_y + 2;
							time_num = 0;
						}
					}
					break;
				case 4:// 弹板
					// if(position_obj_y[i] - position_man_y - MAN_HEIGHT >=- nearly_y && position_obj_y[i] - position_man_y - MAN_HEIGHT <= nearly_y)
				{
					time_num++;
					if (time_num == 1 && position_obj_y[i] - position_man_y - 16 >= -3 && position_obj_y[i] - position_man_y - 16 <= 3) {
						life_change++;
						if (life_change == 1 && life < MAX_LIFE)
							life++;
						position_man_y = position_obj_y[i] - 16;
						// position_man_y--;
						check_num = 0;
						game.myDrawRegion(graphics, I_obj1, 0, 147, position_obj_x[i], position_obj_y[i] - 2, 48, 8);
						position_man_y = position_man_y - OBJ_MOV * 2;
						directionup = 1;
						directionhit = 0;
					} else if (time_num == 2 && position_obj_y[i] - position_man_y - 16 >= 2 && position_obj_y[i] - position_man_y - 16 <= 7) {
						life_change++;
						if (life_change == 1 && life < MAX_LIFE)
							life++;
						position_man_y = position_obj_y[i] - 16 - 5;
						check_num = 0;
						game.myDrawRegion(graphics, I_obj1, 0, 155, position_obj_x[i], position_obj_y[i] - 3, 48, 9);
						position_man_y = position_man_y - OBJ_MOV * 2;
						directionup = 1;
						directionhit = 0;
					} else if (time_num == 3 && position_obj_y[i] - position_man_y - 16 >= 7 && position_obj_y[i] - position_man_y - 16 <= 12) {
						life_change++;
						if (life_change == 1 && life < MAX_LIFE)
							life++;
						position_man_y = position_obj_y[i] - 16 - 10;
						check_num = 0;
						game.myDrawRegion(graphics, I_obj1, 0, 164, position_obj_x[i], position_obj_y[i] - 4, 48, 10);
						position_man_y = position_man_y - OBJ_MOV * 2;
						time_num = 0;
						directionup = 1;
						directionhit = 0;
					} else {
						time_num--;
						directionup = 0;
						directionhit = 0;
					}
				}
					break;
				case 5:// 带刺的板
					if (position_obj_y[i] - position_man_y - MAN_HEIGHT / 2 >= -nearly_y
							&& position_obj_y[i] - position_man_y - MAN_HEIGHT / 2 <= nearly_y) {
						life_change++;
						if (life_change == 1) {
							life = life - 3;
							if (life < 1) {
								position_man_y = SCREEN_HEIGHT + position_man_y;
								isgameover = true;
							}
						}
						position_man_y = position_obj_y[i] - 8;
						position_man_y--;
						check_num = 0;
						directionup = 0;
						directionhit = 1;
					}
					break;
				}
			}
		}
		if (check_num != 0) {
			position_man_y = position_man_y + MAN_MOV_Y;
			life_change = 0;
			directionup = 1;
			directionhit = 0;
		}

	}

	// 检测按键信息
	public void inputs() {
		int keyState = getKeyStates();
		if ((keyState & LEFT_PRESSED) != 0) {
			if (position_man_x < SIDE_WIDTH) {
				position_man_x = SIDE_WIDTH;
			} else {
				position_man_x = position_man_x - MAN_MOV_X;
			}
			direction = 1;
		} else if ((keyState & RIGHT_PRESSED) != 0) {
			if (position_man_x > SCREEN_WIDTH - MAN_WIDTH - SIDE_WIDTH - 1) {
				position_man_x = SCREEN_WIDTH - MAN_WIDTH - SIDE_WIDTH;
			} else {
				position_man_x = position_man_x + MAN_MOV_X;
			}
			direction = 2;
		} else if (((keyState & GAME_A_PRESSED) != 0 || (keyState & GAME_B_PRESSED) != 0 || (keyState & GAME_C_PRESSED) != 0
				|| (keyState & GAME_D_PRESSED) != 0 || (keyState & FIRE_PRESSED) != 0 || (keyState & UP_PRESSED) != 0 || (keyState & DOWN_PRESSED) != 0)
				&& isgameover)// (keyState & FIRE_PRESSED)!=0 &&
		{
			/*
			 * this.game.mainmenu.menuindex = 1; game.startGame();
			 */
			running = false;
			man.closeman();
		} else
			direction = 0;

	}

	// 显示物体
	public void draw_obj(Graphics graphics, Image image_show, int obj_id, int pos_x, int pos_y) {
		switch (obj_id) {
		case 0:// 平板
			game.myDrawRegion(graphics, image_show, 0, 16, pos_x, pos_y, 48, 8);
			break;
		case 1:// 右滚板
			game.myDrawRegion(graphics, image_show, 0, 24 + man_picIndex * 8, pos_x, pos_y, 48, 8);
			break;
		case 2:// 左滚板
			game.myDrawRegion(graphics, image_show, 0, 56 + man_picIndex * 8, pos_x, pos_y, 48, 8);
			break;
		case 3:// 翻板
			game.myDrawRegion(graphics, image_show, 0, 88, pos_x, pos_y, 48, 8);
			break;
		case 4:// 弹板
			game.myDrawRegion(graphics, image_show, 0, 141, pos_x, pos_y, 48, 6);
			break;
		case 5:// 带刺的板
			game.myDrawRegion(graphics, image_show, 0, 0, pos_x, pos_y, 48, 15);
			break;
		}
	}
}
