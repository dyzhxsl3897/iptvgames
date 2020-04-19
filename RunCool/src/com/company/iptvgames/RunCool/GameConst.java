package com.company.iptvgames.RunCool;

public class GameConst {
	public static final int FPS = 100; // This is not real FPS, this is time in milliseconds
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 530;
	
	public static class Menu {
		public static final int START_BTN_X = 530;//��ʼ��ťλ��X
		public static final int START_BTN_Y = 256;//��ʼ��ťλ��Y
		public static final int START_BTN_WIDTH = 138;//��ʼ��ťͼƬ�ߴ�-��
		public static final int START_BTN_HEIGHT = 74;//��ʼ��ťͼƬ�ߴ�-��
		public static final int START_BTN_FRAME = 2;//��ʼ��ť����֡��
		
		public static final int EXIT_BTN_X = 597;//�˳���ťλ��X
		public static final int EXIT_BTN_Y = 0;//�˳���ťλ��Y
	}

	public static class GameCanvas {
		
		public static final int LAYER_alert = 0;//alert��ʼͼ���
		public static final int LAYER_bg = 18;//����ͼƬ��ʼͼ���
		public static final int RUNSPEED = 20;//��Ϸ�ٶ�
		public static final int BGNUMBER = 2;//����ͼƬ����������
		public static final int BGWIDTH = 640;//����ͼƬÿ֡���
		public static final int BG_TILEDNUMER = 1;//����ͼƬ��֡����
		public static final int SCORE_X = 200;
		public static final int SCORE_Y = 16;	
		public static final int LIFE_X = 60;
		public static final int LIFE_Y = 16;
		public static final int LIFE_BG_X = 10;
		public static final int LIFE_BG_Y = 10;
		
		public static final int CX_FINISH = 222;//ʧ��alert�ļ�����ťλ��X
		public static final int CY_FINISH = 351;//ʧ��alert�ļ�����ťλ��Y
		public static final int OX_FINISH = 335;//ʧ��alert���˳���ťλ��X
		public static final int OY_FINISH = 351;//ʧ��alert���˳���ťλ��Y
		
		public static final int CX_PAUSE = 222;//��ͣalert�ļ�����ťλ��X
		public static final int CY_PAUSE = 251;//��ͣalert�ļ�����ťλ��Y
		public static final int OX_PAUSE = 335;//��ͣalert���˳���ťλ��X
		public static final int OY_PAUSE = 251;//��ͣalert���˳���ťλ��Y
	}
	
	public static class Obstacle {
		public static final int NUMBER = 10;//�ϰ��ﴴ��������
		public static final int TYPENUMBER = 8;//�ϰ�����������
		public static final int LAYER_obstacle = 8;//�ϰ�����ʼͼ���
		public static final int DISTANCE_MIN = 350;//�ϰ������С��ֵࣨԽС���Ѷ�Խ��
		public static final int DISTANCE_RAND = 10;//�ϰ���������ֵࣨԽС���Ѷ�Խ��
	}

	public static class Player {
		public static final int WALKWIDTH = 504;//�����ܲ�ͼƬ�ߴ�-��
		public static final int WALKHEIGHT = 63;//�����ܲ�ͼƬ�ߴ�-��
		public static final int JUMPWIDTH = 504;//��������ͼƬ�ߴ�-��
		public static final int JUMPHEIGHT = 63;//��������ͼƬ�ߴ�-��
		public static final int ROLE_FRAME = 8;//���ﶯ��֡��
		public static final int ADD_JUMP_HEIGHT = 20;//��Ծʱÿ֡�߶�����ֵ
		public static final int JUMP_FRAME = 16;//��Ծ��������ʾ֡��
		public static final int[] JUMP_FRAME_SEQUENCE = {0,1,1,1,2,2,2,3,3,4,4,5,5,6,6,7};//��Ծ����֡˳��ȷ���м�ֵΪ��ߵ�֡ͼ��
		
		public static final int COLLIDE_X = 0;
		public static final int COLLIDE_Y = 0;
		public static final int COLLIDE_W = 63;
		public static final int COLLIDE_H = 63;
		
		public static final int POS_X = 20;
		public static final int POS_Y = 337;
		public static final int LAYER_player = 3;//��������ͼ���
		
		public static final int LIFE = 3;
		public static final int DROPSPEED = 3;//�˳�����������ٶ�
	}
}
