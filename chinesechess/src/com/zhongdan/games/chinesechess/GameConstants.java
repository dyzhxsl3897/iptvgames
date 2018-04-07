package com.zhongdan.games.chinesechess;

public class GameConstants {

	public static class GameStatus {
		public final static int START = 0;
		public final static int PLAYING = 1;
	}

	public static class Menu {
		public final static int BTN_TOP_X = 255;
		public final static int BTN_TOP_Y = 240;
		public final static int BTN_BOT_X = 255;
		public final static int BTN_BOT_Y = 320;
	}

	public static class GameSettings {
		public final static int CELL_START_X = 61;// 棋盘左上角坐标
		public final static int CELL_START_Y = 43;// 棋盘左上角坐标
		public final static int CELL_WIDTH = 49;// 每一个格子大小
		public final static int CELL_HEIGHT = 49;// 每一个格子大小
		public final static int PIECE_WIDTH = 44;// 棋子大小
		public final static int PIECE_HEIGHT = 44;// 棋子大小
		public final static int PIECE_START_X = CELL_START_X - PIECE_WIDTH / 2;// 左上角棋子坐标
		public final static int PIECE_START_Y = CELL_START_Y - PIECE_HEIGHT / 2;// 左上角棋子坐标
		public final static int CUR_START_X = CELL_START_X - CELL_WIDTH / 2;// 左上角光标坐标
		public final static int CUR_START_Y = CELL_START_Y - CELL_HEIGHT / 2;// 左上角光标坐标
		public final static int STEP_NUMBER_X = 562;// 步数坐标
		public final static int STEP_NUMBER_Y = 420;// 步数坐标
	}

}
