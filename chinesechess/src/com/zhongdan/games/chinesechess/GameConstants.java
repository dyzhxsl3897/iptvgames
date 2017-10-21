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
		public final static int CELL_START_X = 61;// �������Ͻ�����
		public final static int CELL_START_Y = 43;// �������Ͻ�����
		public final static int CELL_WIDTH = 49;// ÿһ�����Ӵ�С
		public final static int CELL_HEIGHT = 49;// ÿһ�����Ӵ�С
		public final static int PIECE_WIDTH = 44;// ���Ӵ�С
		public final static int PIECE_HEIGHT = 44;// ���Ӵ�С
		public final static int PIECE_START_X = CELL_START_X - PIECE_WIDTH / 2;// ���Ͻ���������
		public final static int PIECE_START_Y = CELL_START_Y - PIECE_HEIGHT / 2;// ���Ͻ���������
		public final static int CUR_START_X = CELL_START_X - CELL_WIDTH / 2;// ���Ͻǹ������
		public final static int CUR_START_Y = CELL_START_Y - CELL_HEIGHT / 2;// ���Ͻǹ������
		public final static int STEP_NUMBER_X = 562;// ��������
		public final static int STEP_NUMBER_Y = 420;// ��������
	}

}
