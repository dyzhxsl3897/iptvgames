package com.zhongdan.games.tetris;

import javax.microedition.lcdui.game.Sprite;

public class MyGameConstants {

	public static class Playboard {
		public static int ROW_NO = 15;
		public static int COL_NO = 10;
	}

	public static class Menu {
		public static int BTN_TOP_X = 265;
		public static int BTN_TOP_Y = 280;
		public static int BTN_BOT_X = 265;
		public static int BTN_BOT_Y = 380;
	}

	public static class Score {
		public static int WIDTH = 25;
		public static int HEIGHT = 27;
		public static int X = 60;
		public static int Y = 72;
	}

	public static class ButtonIcon {
		public static int ddown_X = 50;
		public static int ddown_Y = 360;
		public static int down_X = 210;
		public static int down_Y = 455;
		public static int left_X = 50;
		public static int left_Y = 455;
		public static int right_X = 370;
		public static int right_Y = 455;
		public static int pause_X = 530;
		public static int pause_Y = 455;
		public static int returns_X = 360;
		public static int returns_Y = 530;
	}

	public static class GameSettings {
		public static int[] DROPDOWN_INTERVAL = { 1000, 800, 500, 300, 200, 100, 50 };
		public static int[] SCORE = { 1, 3, 6, 10 };
	}

	public static class Brick {
		public static int[] DIRECTION = { Sprite.TRANS_NONE, Sprite.TRANS_ROT90, Sprite.TRANS_ROT180, Sprite.TRANS_ROT270 };
		public static int WIDTH = 29;
		public static int HEIGHT = 29;
		public static int START_LEFT = 175;
		public static int START_TOP = 5;
		public static int NEXT_LEFT = 430;
		public static int NEXT_TOP = 200;

		public static int[][][][] brickStart = {//
		{// type 1 : 两个折，反Z
				{ { 0, 4 }, { 0, 5 }, { 1, 3 }, { 1, 4 } },// direct 1
						{ { 0, 4 }, { 1, 4 }, { 1, 5 }, { 2, 5 } },// direct 2
						{ { 0, 4 }, { 0, 5 }, { 1, 3 }, { 1, 4 } },// direct 3
						{ { 0, 4 }, { 1, 4 }, { 1, 5 }, { 2, 5 } } // direct 4
				}, {// type 2 : 方块
				{ { 0, 4 }, { 0, 5 }, { 1, 4 }, { 1, 5 } },// direct 1
						{ { 0, 4 }, { 0, 5 }, { 1, 4 }, { 1, 5 } },// direct 2
						{ { 0, 4 }, { 0, 5 }, { 1, 4 }, { 1, 5 } },// direct 3
						{ { 0, 4 }, { 0, 5 }, { 1, 4 }, { 1, 5 } } // direct 4
				}, {// type 3 : 一个折，反7
				{ { 0, 3 }, { 0, 4 }, { 0, 5 }, { 1, 5 } },// direct 1
						{ { 0, 5 }, { 1, 5 }, { 2, 5 }, { 2, 4 } },// direct 2
						{ { 0, 3 }, { 1, 3 }, { 1, 4 }, { 1, 5 } },// direct 3
						{ { 0, 4 }, { 0, 5 }, { 1, 4 }, { 2, 4 } } // direct 4
				}, {// type 4 : T型
				{ { 0, 4 }, { 1, 3 }, { 1, 4 }, { 1, 5 } },// direct 1
						{ { 0, 4 }, { 1, 4 }, { 2, 4 }, { 1, 5 } },// direct 2
						{ { 0, 3 }, { 0, 4 }, { 0, 5 }, { 1, 4 } },// direct 4
						{ { 0, 5 }, { 1, 4 }, { 1, 5 }, { 2, 5 } } // direct 3
				}, {// type 5 : 长条
				{ { 0, 3 }, { 0, 4 }, { 0, 5 }, { 0, 6 } },// direct 1
						{ { 0, 4 }, { 1, 4 }, { 2, 4 }, { 3, 4 } },// direct 2
						{ { 0, 3 }, { 0, 4 }, { 0, 5 }, { 0, 6 } },// direct 3
						{ { 0, 4 }, { 1, 4 }, { 2, 4 }, { 3, 4 } } // direct 4
				}, {// type 6 : 两个折，Z
				{ { 0, 3 }, { 0, 4 }, { 1, 4 }, { 1, 5 } },// direct 1
						{ { 0, 5 }, { 1, 4 }, { 1, 5 }, { 2, 4 } },// direct 2
						{ { 0, 3 }, { 0, 4 }, { 1, 4 }, { 1, 5 } },// direct 3
						{ { 0, 5 }, { 1, 4 }, { 1, 5 }, { 2, 4 } } // direct 4
				}, {// type 7 : 一个折，7
				{ { 0, 3 }, { 0, 4 }, { 0, 5 }, { 1, 3 } },// direct 1
						{ { 0, 5 }, { 1, 5 }, { 2, 5 }, { 0, 4 } },// direct 2
						{ { 0, 5 }, { 1, 3 }, { 1, 4 }, { 1, 5 } },// direct 3
						{ { 0, 4 }, { 2, 5 }, { 1, 4 }, { 2, 4 } } // direct 4
				} };
		public static int[][][][] brickTransform = {//
		{// type 1 : 两个折
				{ { 0, 0 }, { 1, -1 }, { 0, 2 }, { 1, 1 } },// direct 1 -> 2
						{ { 0, 0 }, { -1, 1 }, { 0, -2 }, { -1, -1 } },// direct 2 -> 3
						{ { 0, 0 }, { 1, -1 }, { 0, 2 }, { 1, 1 } }, // direct 4 -> 1
						{ { 0, 0 }, { -1, 1 }, { 0, -2 }, { -1, -1 } } // direct 3 -> 4
				}, {// type 2 : 方块
				{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },// direct 1 -> 2
						{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },// direct 2 -> 3
						{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },// direct 3 -> 4
						{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } } // direct 4 -> 1
				}, {// type 3 : 一个折
				{ { 0, 2 }, { 1, 1 }, { 2, 0 }, { 1, -1 } },// direct 1 -> 2
						{ { 0, -2 }, { 0, -2 }, { -1, -1 }, { -1, 1 } },// direct 2 -> 3
						{ { 0, 1 }, { -1, 2 }, { 0, 0 }, { 1, -1 } },// direct 3 -> 4
						{ { 0, -1 }, { 0, -1 }, { -1, 1 }, { -1, 1 } } // direct 4 -> 1
				}, {// type 4 : T型
				{ { 0, 0 }, { 0, 1 }, { 1, 0 }, { 0, 0 } },// direct 1 -> 2
						{ { 0, -1 }, { -1, 0 }, { -2, 1 }, { 0, -1 } },// direct 2 -> 3
						{ { 0, 2 }, { 1, 0 }, { 1, 0 }, { 1, 1 } },// direct 3 -> 4
						{ { 0, -1 }, { 0, -1 }, { 0, -1 }, { -1, 0 } } // direct 4 -> 1
				}, {// type 5 : 长条
				{ { 0, 1 }, { 1, 0 }, { 2, -1 }, { 3, -2 } },// direct 1 -> 2
						{ { 0, -1 }, { -1, 0 }, { -2, 1 }, { -3, 2 } },// direct 2 -> 3
						{ { 0, 1 }, { 1, 0 }, { 2, -1 }, { 3, -2 } },// direct 3 -> 4
						{ { 0, -1 }, { -1, 0 }, { -2, 1 }, { -3, 2 } } // direct 4 -> 1
				}, {// type 6 : 两个折
				{ { 0, 2 }, { 1, 0 }, { 0, 1 }, { 1, -1 } },// direct 1 -> 2
						{ { 0, -2 }, { -1, 0 }, { 0, -1 }, { -1, 1 } },// direct 2 -> 3
						{ { 0, 2 }, { 1, 0 }, { 0, 1 }, { 1, -1 } },// direct 3 -> 4
						{ { 0, -2 }, { -1, 0 }, { 0, -1 }, { -1, 1 } } // direct 4 -> 1
				}, {// type 7 : 一个折
				{ { 0, 2 }, { 1, 1 }, { 2, 0 }, { -1, 1 } },// direct 1 -> 2
						{ { 0, 0 }, { 0, -2 }, { -1, -1 }, { 1, 1 } },// direct 2 -> 3
						{ { 0, -1 }, { 1, 2 }, { 0, 0 }, { 1, -1 } },// direct 3 -> 4
						{ { 0, -1 }, { -2, -1 }, { -1, 1 }, { -1, -1 } } // direct 4 -> 1
				} };
	}

}
