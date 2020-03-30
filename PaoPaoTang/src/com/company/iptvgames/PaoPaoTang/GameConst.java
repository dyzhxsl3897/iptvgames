package com.company.iptvgames.PaoPaoTang;

public class GameConst {
	public static final int FPS = 200; // This is not real FPS, this is time in milliseconds
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 530;

	public static class Menu {
		public static final int START_BTN_X = 276;
		public static final int START_BTN_Y = 312;
		public static final int EXIT_BTN_X = 580;
		public static final int EXIT_BTN_Y = 10;
		public static final int LAYER_0 = 0;
	}
	
	public static class GameCanvas {
		public static final int TILES_IN_WIDTH = 17;//水平方向tile总个数
		public static final int TILES_IN_HEIGHT = 9;//垂直方向tile总个数
		public static final int TILE_WIDTH = 33;//tile宽
		public static final int TILE_HEIGHT = 33;//tile高
		public static final int PA_X = 41;//第一个tile的边距x
		public static final int PA_Y = 102;//第一个tile的边距y
		public static final int BUILD_TYPES_AMOUT = 9;//builds总类别数
		public static final int TOOL_TYPES_AMOUT = 3;//特效工具总类别数
		public static final int TOOL_AMOUT = 6;//地图中特效工具最多存在数

		public static final int LAYER_0 = 0;
		public static final int LAYER_1 = 1;//角色层
		public static final int LAYER_2 = 8;//炸弹层
	}
	
	public static class Player {
		public static final int WIDTH = 33;
		public static final int HEIGHT = 50;
		public static final int LIFE = 2;
		public static final int SPEED = 4;

		public static final int COLLIDE_X = 0;
		public static final int COLLIDE_Y = 17;
		public static final int COLLIDE_W = 33;
		public static final int COLLIDE_H = 50;

	}
	
	public static class NPC {
		public static final int NUMBER = 4;	
		public static final boolean ALLOW_GET_TOOL = true;	//是否允许NPC获取道具
	}
	
	public static class Bomb {
		public static final int WIDTH = 33;
		public static final int HEIGHT = 50;
		public static final int bombMAX = 1;//炸弹数默认初始值
		public static final int bombPOWER = 1;//炸弹威力默认初始值
		public static final int bombDURATION = 15;//炸弹延时默认初始值
		public static final int fireCenterWidth = 46;//爆炸中心图片宽
		public static final int fireWidth = 33;//爆炸范围距离
		public static final int fireHeight = 32;//爆炸范围
		public static final int fireEdgeWidth = 40;//爆炸边界图片宽
		public static final int fireEdgeHeight = 25;//爆炸边界图片高
	}
	
	public static class Pause {
		public static final int CONTINUE_X = 212;//暂无暂停提示页
		public static final int CONTINUE_Y = 124;//暂无暂停提示页
		public static final int OVER_X = 315;//暂无暂停提示页
		public static final int OVER_Y = 124;//暂无暂停提示页
		public static final int RETURN_X = 247;
		public static final int RETURN_Y = 331;
	}
}
