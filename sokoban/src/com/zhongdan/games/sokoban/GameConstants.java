package com.zhongdan.games.sokoban;

public class GameConstants {

	public static class GameStatus {
		public final static int START = 0;
		public final static int PLAYING = 1;
	}

	public static class Menu {
		public static int BTN_TOP_X = 220;
		// public static int BTN_TOP_Y = 220;
		public static int BTN_TOP_Y = 240;
		public static int BTN_MID_X = 220;
		public static int BTN_MID_Y = 280;
		public static int BTN_BOT_X = 220;
		public static int BTN_BOT_Y = 320;
		// public static int BTN_BOT_Y = 340;
	}

	public static class GameSettings {
		public static int ROW_NO = 8;
		public static int COL_NO = 8;
		public static int CELL_W = 76;
		public static int CELL_H = 36;
		public static int TOP_LEFT_CELL_X = 282;
		public static int TOP_LEFT_CELL_Y = 170;
		public static int STEP_NUMBER_X = 110;
		public static int STEP_NUMBER_Y = 15;
		public static int LEVEL_NUMBER_X = 320;
		public static int LEVEL_NUMBER_Y = 35;
	}

	/**
	 * 推箱子设置<br/>
	 * -1：代表地图外界<br/>
	 * 0：代表空地<br/>
	 * 1：代表墙<br/>
	 * 2：代表目标地点<br/>
	 * 3：代表箱子<br/>
	 * 4：代表压住目标地点的箱子<br/>
	 * 5：代表小人上<br/>
	 * 6：代表小人下<br/>
	 * 7：代表小人左<br/>
	 * 8：代表小人右<br/>
	 * 9：代表压住目标地点的小人
	 */
	public static class MapInfo {
		public final static int OUT_BOARD = -1;
		public final static int BLANK = 0;
		public final static int WALL = 1;
		public final static int TARGET = 2;
		public final static int BOX = 3;
		public final static int BOX_TARGET = 4;
		public final static int PLAYER_UP = 5;
		public final static int PLAYER_DOWN = 6;
		public final static int PLAYER_LEFT = 7;
		public final static int PLAYER_RIGHT = 8;
		public final static int PLAYER_UP_TARGET = 9;
		public final static int PLAYER_DOWN_TARGET = 10;
		public final static int PLAYER_LEFT_TARGET = 11;
		public final static int PLAYER_RIGHT_TARGET = 12;
	}

	public static int[] MAP_SUCC_STEP = { 4, 4, 3, 4, 3, 4, 5, 6, 7 };

	public static int[][][] START_MAP = {
	// 第一关(一)
			{
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD,
							MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.TARGET, MapInfo.WALL, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD,
							MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.BLANK, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL }, //
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.BOX, MapInfo.BLANK, MapInfo.BOX, MapInfo.TARGET, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.TARGET, MapInfo.BLANK, MapInfo.BOX, MapInfo.PLAYER_DOWN, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.BOX, MapInfo.WALL, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.TARGET, MapInfo.WALL, MapInfo.OUT_BOARD,
							MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD,
							MapInfo.OUT_BOARD } },
			// 第二关(四)
			{
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD,
							MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BOX, MapInfo.WALL, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.BOX, MapInfo.PLAYER_DOWN, MapInfo.BLANK, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.TARGET, MapInfo.BOX, MapInfo.BOX, MapInfo.BLANK, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.TARGET, MapInfo.TARGET, MapInfo.BOX_TARGET, MapInfo.TARGET, MapInfo.WALL,
							MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD } }, //
			// 第三关(五)
			{
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.BLANK, MapInfo.PLAYER_DOWN, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL,
							MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.BLANK, MapInfo.BOX, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.BLANK, MapInfo.WALL, MapInfo.BLANK, MapInfo.WALL, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.TARGET, MapInfo.WALL, MapInfo.BLANK, MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.TARGET, MapInfo.BOX, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.TARGET, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BOX, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL } }, //
			// 第四关(十)
			{
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL },//
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.BOX, MapInfo.BOX, MapInfo.BOX, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.PLAYER_DOWN, MapInfo.BLANK, MapInfo.BOX, MapInfo.TARGET, MapInfo.TARGET, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BOX, MapInfo.TARGET, MapInfo.TARGET, MapInfo.TARGET, MapInfo.WALL, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL,
							MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD,
							MapInfo.OUT_BOARD, MapInfo.OUT_BOARD } }, //
			// 第五关(十二)
			{
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.PLAYER_DOWN, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BOX, MapInfo.TARGET, MapInfo.BLANK, MapInfo.WALL, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.TARGET, MapInfo.BOX, MapInfo.TARGET, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.BLANK, MapInfo.BOX_TARGET, MapInfo.BOX, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.WALL },//
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD,
							MapInfo.OUT_BOARD, MapInfo.OUT_BOARD } }, //
			// 第六关(十三)
			{
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD,
							MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.TARGET, MapInfo.TARGET, MapInfo.WALL, MapInfo.OUT_BOARD,
							MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.BLANK, MapInfo.TARGET, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BOX, MapInfo.TARGET, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.BLANK, MapInfo.BOX, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.BOX, MapInfo.BOX, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.PLAYER_DOWN, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL } }, //
			// 第七关(十四)
			{
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BOX, MapInfo.TARGET, MapInfo.TARGET, MapInfo.BOX, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.PLAYER_DOWN, MapInfo.BOX, MapInfo.TARGET, MapInfo.BOX_TARGET, MapInfo.BLANK, MapInfo.WALL, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BOX, MapInfo.TARGET, MapInfo.TARGET, MapInfo.BOX, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL }, //
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD,
							MapInfo.OUT_BOARD, MapInfo.OUT_BOARD } }, //
			// 第八关(十五)
			{
					{ MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BOX, MapInfo.BLANK, MapInfo.BOX, MapInfo.BOX, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.TARGET, MapInfo.TARGET, MapInfo.TARGET, MapInfo.TARGET, MapInfo.TARGET, MapInfo.TARGET, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BOX, MapInfo.BOX, MapInfo.BLANK, MapInfo.BOX, MapInfo.BLANK, MapInfo.WALL },//
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.BLANK, MapInfo.PLAYER_DOWN, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL },//
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD,
							MapInfo.OUT_BOARD }, //
					{ MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD, MapInfo.OUT_BOARD,
							MapInfo.OUT_BOARD, MapInfo.OUT_BOARD } }, //
			// 第九关(二十)
			{
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.WALL, MapInfo.TARGET, MapInfo.TARGET, MapInfo.BOX, MapInfo.TARGET, MapInfo.TARGET, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.WALL, MapInfo.TARGET, MapInfo.TARGET, MapInfo.WALL, MapInfo.TARGET, 2, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BOX, MapInfo.BOX, MapInfo.BOX, MapInfo.BLANK, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.BOX, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BOX, MapInfo.BOX, MapInfo.BOX, MapInfo.BLANK, MapInfo.WALL, MapInfo.OUT_BOARD },//
					{ MapInfo.WALL, MapInfo.BLANK, MapInfo.BLANK, MapInfo.WALL, MapInfo.PLAYER_DOWN, MapInfo.BLANK, MapInfo.WALL, MapInfo.OUT_BOARD }, //
					{ MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.WALL, MapInfo.OUT_BOARD } } //
	};

}
