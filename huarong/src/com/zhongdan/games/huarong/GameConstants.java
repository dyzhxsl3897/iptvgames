package com.zhongdan.games.huarong;

public class GameConstants {

	public static class Menu {
		public static int BTN_TOP_X = 80;
		public static int BTN_TOP_Y = 300;
		public static int BTN_BOT_X = 535;
		public static int BTN_BOT_Y = 15;
	}

	public static class GameSettings {
		public static int MAP_ROW = 5;
		public static int MAP_COL = 4;
		public static int TOP_LEFT_CELL_X = 151;
		public static int TOP_LEFT_CELL_Y = 114;
		public static int CELL_W = 84;
		public static int CELL_H = 62;
		public static int STEP_NUMBER_X = 320;
		public static int STEP_NUMBER_Y = 50;
		public static int GOAL_NUMBER_X = 560;
		public static int GOAL_NUMBER_Y = 50;
	}

	public static class RoleName {
		public final static int CAO_CAO = 0;
		public final static int GUAN_YU = 1;
		public final static int ZHANG_FEI = 2;
		public final static int ZHAO_YUN = 3;
		public final static int HUANG_ZHONG = 4;
		public final static int MA_CHAO = 5;
		public final static int ZU = 6;
	}

	public static int[][][] START_MAP = {
	// µÚÒ»¹Ø
	{ { 4, 0, -1, 3 },//
			{ -1, -1, -1, -1 },//
			{ 2, 1, -1, 5 },//
			{ -1, -1, -1, -1 },//
			{ 6, 6, 6, 6 } } //
	};

}
