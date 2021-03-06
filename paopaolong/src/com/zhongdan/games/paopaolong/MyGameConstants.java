package com.zhongdan.games.paopaolong;

public class MyGameConstants extends Constants {

	public static class GameSettings {
		public static int GAMEBOARD_LEFT = 7;// 游戏版左侧坐标
		public static int GAMEBOARD_RIGHT = 506;// 游戏版右侧坐标
		public static int SINGLE_MOVE_STEP = 10;// 小球每动一次的位移量
		public static int SINGLE_MOVE_INTERVAL = 20;// 小球每次移动的间隔时间，毫秒
		public static int TOP_LEFT_BALL_X = 7;// 左上角小球的X坐标
		public static int SECOND_ROW_LEFT_BALL_X = 26;// 第二行左边小球的X坐标
		public static int TOP_LEFT_BALL_Y = 80;// 左上角小球的Y坐标
		public static int INITIAL_ROW_NO = 4;// 游戏初始化小球的行数
		public static int ROW_NO = 10;// 行数
		public static int COL_NO = 13;// 列数
		public static int ROW_HEIGHT = 33;// 行高
		public static int COL_WIDTH = 38;// 列宽
		public static int ANGLE_MAX = 72;// 箭头旋转最大角度
		public static int ANGLE_SINGLE_MOVE = 6;// 箭头每次旋转的角度
		public static int DROP_DOWN_TIME = 20000;// 每过多少毫秒下降两层
		public static int GAME_OVER_FOCUS_X = 254;// 游戏失败按钮选中对焦坐标
		public static int GAME_OVER_FOCUS_Y = 431;// 游戏失败按钮选中对焦坐标
		public static int GAME_OVER_SCREEN_DELAY = 1000; // Game Over之后等一段时间显示Game Over画面
	}

	public static class Menu {
		public static int BTN_TOP_X = 180;
		public static int BTN_TOP_Y = 200;
		public static int BTN_BOT_X = 180;
		public static int BTN_BOT_Y = 300;
	}

	public static class Score {
		public static int X = 580;// 分数中心点横坐标
		public static int Y = 313;// 分数中心点纵坐标
		public static int WIDTH = 13;// 分数每个数字宽度
		public static int HEIGHT = 20;// 分数每个数字高度
	}

	public static class Ball {
		public static int WIDTH = 38;// 小球宽度
		public static int HEIGHT = 38;// 小球高度
	}

	public static class BallColor {
		public static int BLUE = 0;// 蓝色
		public static int PURPLE = 1;// 紫色
		public static int RED = 2;// 红色
		public static int YELLOW = 3;// 黄色
	}

	public static class WaitingBall {
		public static int TOP = 440;// 等待发射小球的X坐标
		public static int LEFT = 187;// 等待发射小球的Y坐标
		public static int WIDTH = 38;// 等待发射小球的宽度
		public static int HEIGHT = 38;// 等待发射小球的高度
	}

	public static class NextBall {
		public static int TOP = 489;// 下一个小球的X坐标
		public static int LEFT = 192;// 下一个小球的Y坐标
		public static int WIDTH = 26;// 下一个小球的宽度
		public static int HEIGHT = 26;// 下一个小球的高度
	}

	public static class Arrow {
		public static int TOP = 398;// 箭头的X坐标
		public static int LEFT = 136;// 箭头的Y坐标
	}

}
