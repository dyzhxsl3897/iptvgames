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
		public static final int TILES_IN_WIDTH = 17;//ˮƽ����tile�ܸ���
		public static final int TILES_IN_HEIGHT = 9;//��ֱ����tile�ܸ���
		public static final int TILE_WIDTH = 33;//tile��
		public static final int TILE_HEIGHT = 33;//tile��
		public static final int PA_X = 41;//��һ��tile�ı߾�x
		public static final int PA_Y = 102;//��һ��tile�ı߾�y
		public static final int BUILD_TYPES_AMOUT = 9;//builds�������
		public static final int TOOL_TYPES_AMOUT = 3;//��Ч�����������
		public static final int TOOL_AMOUT = 6;//��ͼ����Ч������������

		public static final int LAYER_0 = 0;
		public static final int LAYER_1 = 1;//��ɫ��
		public static final int LAYER_2 = 8;//ը����
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
		public static final boolean ALLOW_GET_TOOL = true;	//�Ƿ�����NPC��ȡ����
	}
	
	public static class Bomb {
		public static final int WIDTH = 33;
		public static final int HEIGHT = 50;
		public static final int bombMAX = 1;//ը����Ĭ�ϳ�ʼֵ
		public static final int bombPOWER = 1;//ը������Ĭ�ϳ�ʼֵ
		public static final int bombDURATION = 15;//ը����ʱĬ�ϳ�ʼֵ
		public static final int fireCenterWidth = 46;//��ը����ͼƬ��
		public static final int fireWidth = 33;//��ը��Χ����
		public static final int fireHeight = 32;//��ը��Χ
		public static final int fireEdgeWidth = 40;//��ը�߽�ͼƬ��
		public static final int fireEdgeHeight = 25;//��ը�߽�ͼƬ��
	}
	
	public static class Pause {
		public static final int CONTINUE_X = 212;//������ͣ��ʾҳ
		public static final int CONTINUE_Y = 124;//������ͣ��ʾҳ
		public static final int OVER_X = 315;//������ͣ��ʾҳ
		public static final int OVER_Y = 124;//������ͣ��ʾҳ
		public static final int RETURN_X = 247;
		public static final int RETURN_Y = 331;
	}
}
