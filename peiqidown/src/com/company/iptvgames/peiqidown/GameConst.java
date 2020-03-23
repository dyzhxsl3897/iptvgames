package com.company.iptvgames.peiqidown;

public class GameConst {

	public static final int FPS = 80; // This is not real FPS, this is time in milliseconds
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 530;

	public static class Menu {
		public static final int CARTOON_W = 177;
		public static final int CARTOON_H = 322;
		public static final int CARTOON_X = 364;
		public static final int CARTOON_Y = 100;

		public static final int START_BTN_X = 142;
		public static final int START_BTN_Y = 328;
		public static final int EXIT_BTN_X = 550;
		public static final int EXIT_BTN_Y = 13;

		public static final int LAYER_0 = 0;
	}

	public static class GameCanvas {
		public static final int BG2_X = 66;
		public static final int BG2_Y = 0;
		public static final int GRAVITY = 1;
		public static final int MOVE_UP_SPEED = 1;
		public static final int PLAYAREA_LEFT_X = 100;
		public static final int PLAYAREA_RIGHT_X = 540;
		public static final int LAYER_0 = 0;
		public static final int LAYER_1 = 3;
		public static final int NUMBER_X = 32;
		public static final int NUMBER_Y = 63;
		public static final int LIFE_X = 16;
		public static final int LIFE_Y = 190;
		public static final int LIFE_H = 10;
	}

	public static class Peiqi {
		public static final int WIDTH = 53;
		public static final int HEIGHT = 68;
		public static final int START_X = 353;
		public static final int START_Y = 80;

		public static final int TOUCH_TOP_Y = 10;
		
		public static final int COLLIDE_X = 15;
		public static final int COLLIDE_Y = 64;
		public static final int COLLIDE_W = 21;
		public static final int COLLIDE_H = 1;

		public static final int JUMP_SPEED = -8;
		public static final int MOVE_SPEED = 15;

		public static final int MAX_LIFE = 11;
		public static final int COST_LIFE_TOUCH_TOP = 1;
		public static final int COST_LIFE_TOUCH_STAB = 3;
		public static final int ADD_LIFE_TOUCH_BOARD = 1;
	}

	public static class Board {
		public static final int NUMBER = 10;
		public static final int WIDTH = 131;
		public static final int HEIGHT = 33;
		
		public static final int COLLIDE_X = 4;
		public static final int COLLIDE_Y = 26;
		public static final int COLLIDE_W = 122;
		public static final int COLLIDE_H = 1;

		public static final int LEFT_BOARD_MOVE_SPEED = 2;
		public static final int RIGHT_BOARD_MOVE_SPEED = 2;

		public static final int START_X = 282;
		public static final int LEFT_X = 120;
		public static final int RIGHT_X = 444;
		public static final int TOP_Y = 148;
		public static final int LAYER_HEIGHT = 120;
	}

	public static class Pause {
		public static final int CONTINUE_X = 212;
		public static final int CONTINUE_Y = 209;
		public static final int OVER_X = 315;
		public static final int OVER_Y = 209;
	}
}
