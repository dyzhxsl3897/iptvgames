package com.zhongdan.games.paopaolong;

public class MyGameConstants extends Constants {

	public static class GameSettings {
		public static int GAMEBOARD_LEFT = 7;//��Ϸ���������
		public static int GAMEBOARD_RIGHT = 506;//��Ϸ���Ҳ�����
		public static int SINGLE_MOVE_STEP = 10;// С��ÿ��һ�ε�λ����
		public static int SINGLE_MOVE_INTERVAL = 20;// С��ÿ���ƶ��ļ��ʱ�䣬����
		public static int TOP_LEFT_BALL_X = 7;// ���Ͻ�С���X����
		public static int SECOND_ROW_LEFT_BALL_X = 26;// �ڶ������С���X����
		public static int TOP_LEFT_BALL_Y = 80;// ���Ͻ�С���Y����
		public static int INITIAL_ROW_NO = 4;// ��Ϸ��ʼ��С�������
		public static int ROW_NO = 8;// ����
		public static int COL_NO = 13;// ����
		public static int ROW_HEIGHT = 33;// �и�
		public static int COL_WIDTH = 38;// �п�
		public static int ANGLE_MAX = 75;// ��ͷ��ת���Ƕ�
		public static int ANGLE_SINGLE_MOVE = 3;// ��ͷÿ����ת�ĽǶ�
	}

	public static class Ball {
		public static int WIDTH = 38;// С����
		public static int HEIGHT = 38;// С��߶�
	}

	public static class BallColor {
		public static int BLUE = 0;// ��ɫ
		public static int PURPLE = 1;// ��ɫ
		public static int RED = 2;// ��ɫ
		public static int YELLOW = 3;// ��ɫ
	}

	public static class WaitingBall {
		public static int TOP = 440;// �ȴ�����С���X����
		public static int LEFT = 187;// �ȴ�����С���Y����
		public static int WIDTH = 38;// �ȴ�����С��Ŀ��
		public static int HEIGHT = 38;// �ȴ�����С��ĸ߶�
	}

	public static class NextBall {
		public static int TOP = 489;// ��һ��С���X����
		public static int LEFT = 192;// ��һ��С���Y����
		public static int WIDTH = 26;// ��һ��С��Ŀ��
		public static int HEIGHT = 26;// ��һ��С��ĸ߶�
	}

	public static class Arrow {
		public static int TOP = 398;// ��ͷ��X����
		public static int LEFT = 136;// ��ͷ��Y����
	}

}
