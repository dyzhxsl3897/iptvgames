/**
 * 
 */
package com.game;

/**
 * @author Administrator
 *
 */
public class Constants {
	public static class KeyCode {
		public final static int UP = -1;
		public final static int DOWN = -2;// ��
		public final static int LEFT = -3;// ��
		public final static int RIGHT = -4;// ��
		
		public final static int OK = -5;// ȷ��/OK
		public final static int MENU = -6;// �˵�
		public final static int BACK = -7;// ����
		public final static int BACK_1 = -31;// HUAWEI_EC6108V9A
		public final static int BACK_2 = -9;// HUAWEI_EC6108V9
		
//		public final static int DEL = -8;// ɾ��
//		public final static int FAST_FORWARD = -21;// ���/��ҳ
//		public final static int REWIND = -20;// ����/��ҳ
//		public final static int INPUT = -30;// ���뷨
//		public final static int RED = 42;// ֱ��/��/F1
//		public final static int GREEN = 42;// �ؿ�/��/F2
//		public final static int YELLOW = 42;// �㲥/��/F3
//		public final static int BLUE = 42;// ��ѯ/��/F4
//		public final static int VOLUMN_DEC = 35;// ������
		public final static int NUM_0 = 48;// ����0
		public final static int NUM_1 = 49;// ����1
		public final static int NUM_2 = 50;// ����2
		public final static int NUM_3 = 51;// ����3
		public final static int NUM_4 = 52;// ����4
		public final static int NUM_5 = 53;// ����5
		public final static int NUM_6 = 54;// ����6
		public final static int NUM_7 = 55;// ����7
		public final static int NUM_8 = 56;// ����8
		public final static int NUM_9 = 57;// ����9
	}
	
	public static class Device{
		public static int width= 640;

		public static int height= 530;

		public static int getHeight() {
			return height;
		}

		public static int getWidth() {
			return width;
		}
	}
}
