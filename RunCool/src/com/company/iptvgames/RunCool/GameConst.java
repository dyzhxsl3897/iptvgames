package com.company.iptvgames.RunCool;

public class GameConst {
	public static final int FPS = 100; // This is not real FPS, this is time in milliseconds
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 530;
	
	public static class Menu {
		public static final int START_BTN_X = 530;//开始按钮位置X
		public static final int START_BTN_Y = 256;//开始按钮位置Y
		public static final int START_BTN_WIDTH = 138;//开始按钮图片尺寸-宽
		public static final int START_BTN_HEIGHT = 74;//开始按钮图片尺寸-高
		public static final int START_BTN_FRAME = 2;//开始按钮动画帧数
		
		public static final int EXIT_BTN_X = 597;//退出按钮位置X
		public static final int EXIT_BTN_Y = 0;//退出按钮位置Y
	}

	public static class GameCanvas {
		
		public static final int LAYER_alert = 0;//alert起始图层号
		public static final int LAYER_bg = 18;//背景图片起始图层号
		public static final int RUNSPEED = 20;//游戏速度
		public static final int BGNUMBER = 2;//背景图片创建容量数
		public static final int BGWIDTH = 640;//背景图片每帧宽度
		public static final int BG_TILEDNUMER = 1;//背景图片切帧个数
		public static final int SCORE_X = 200;
		public static final int SCORE_Y = 16;	
		public static final int LIFE_X = 60;
		public static final int LIFE_Y = 16;
		public static final int LIFE_BG_X = 10;
		public static final int LIFE_BG_Y = 10;
		
		public static final int CX_FINISH = 222;//失败alert的继续按钮位置X
		public static final int CY_FINISH = 351;//失败alert的继续按钮位置Y
		public static final int OX_FINISH = 335;//失败alert的退出按钮位置X
		public static final int OY_FINISH = 351;//失败alert的退出按钮位置Y
		
		public static final int CX_PAUSE = 222;//暂停alert的继续按钮位置X
		public static final int CY_PAUSE = 251;//暂停alert的继续按钮位置Y
		public static final int OX_PAUSE = 335;//暂停alert的退出按钮位置X
		public static final int OY_PAUSE = 251;//暂停alert的退出按钮位置Y
	}
	
	public static class Obstacle {
		public static final int NUMBER = 10;//障碍物创建容量数
		public static final int TYPENUMBER = 8;//障碍物类型总数
		public static final int LAYER_obstacle = 8;//障碍物起始图层号
		public static final int DISTANCE_MIN = 350;//障碍物间最小间距（值越小，难度越大）
		public static final int DISTANCE_RAND = 10;//障碍物间随机间距（值越小，难度越大）
	}

	public static class Player {
		public static final int WALKWIDTH = 504;//人物跑步图片尺寸-宽
		public static final int WALKHEIGHT = 63;//人物跑步图片尺寸-高
		public static final int JUMPWIDTH = 504;//人物跳起图片尺寸-宽
		public static final int JUMPHEIGHT = 63;//人物跳起图片尺寸-高
		public static final int ROLE_FRAME = 8;//人物动画帧数
		public static final int ADD_JUMP_HEIGHT = 20;//跳跃时每帧高度增加值
		public static final int JUMP_FRAME = 16;//跳跃过程总显示帧数
		public static final int[] JUMP_FRAME_SEQUENCE = {0,1,1,1,2,2,2,3,3,4,4,5,5,6,6,7};//跳跃过程帧顺序（确保中间值为最高点帧图）
		
		public static final int COLLIDE_X = 0;
		public static final int COLLIDE_Y = 0;
		public static final int COLLIDE_W = 63;
		public static final int COLLIDE_H = 63;
		
		public static final int POS_X = 20;
		public static final int POS_Y = 337;
		public static final int LAYER_player = 3;//人物所在图层号
		
		public static final int LIFE = 3;
		public static final int DROPSPEED = 3;//退出画面的下落速度
	}
}
