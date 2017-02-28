package com.zhongdan.games.sokoban;

public class GameConstants {

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
	 * ����������<br/>
	 * -1�������ͼ���<br/>
	 * 0������յ�<br/>
	 * 1������ǽ<br/>
	 * 2������Ŀ��ص�<br/>
	 * 3����������<br/>
	 * 4������ѹסĿ��ص������<br/>
	 * 5������С����<br/>
	 * 6������С����<br/>
	 * 7������С����<br/>
	 * 8������С����<br/>
	 * 9������ѹסĿ��ص��С��
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

	public static int[][][] START_MAP = {
	// ��һ��(һ)
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
			// �ڶ���(��)
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
			// ������(��)
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
			// ���Ĺ�(ʮ)
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
			// �����(ʮ��)
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
			// ������(ʮ��)
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
			// ���߹�(ʮ��)
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
			// �ڰ˹�(ʮ��)
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
			// �ھŹ�(��ʮ)
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
