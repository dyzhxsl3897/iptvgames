package com.zhongdan.games.framework.utils;

import java.util.Vector;

public class Constants {

	public static class KeyCode {
		public final static Vector UP = new Vector();// ��
		public final static Vector DOWN = new Vector();// ��
		public final static Vector LEFT = new Vector();// ��
		public final static Vector RIGHT = new Vector();// ��
		public final static Vector OK = new Vector();// ȷ��/OK
		public final static Vector MENU = new Vector();// �˵�
		public final static Vector BACK = new Vector();// ����
		public final static Vector DEL = new Vector();// ɾ��
		public final static Vector FAST_FORWARD = new Vector();// ���/��ҳ
		public final static Vector REWIND = new Vector();// ����/��ҳ
		public final static Vector INPUT = new Vector();// ���뷨
		public final static Vector RED = new Vector();// ֱ��/��/F1
		public final static Vector GREEN = new Vector();// �ؿ�/��/F2
		public final static Vector YELLOW = new Vector();// �㲥/��/F3
		public final static Vector BLUE = new Vector();// ��ѯ/��/F4
		public final static Vector VOLUMN_DEC = new Vector();// ������
		public final static Vector NUM_0 = new Vector();// ����0
		public final static Vector NUM_1 = new Vector();// ����1
		public final static Vector NUM_2 = new Vector();// ����2
		public final static Vector NUM_3 = new Vector();// ����3
		public final static Vector NUM_4 = new Vector();// ����4
		public final static Vector NUM_5 = new Vector();// ����5
		public final static Vector NUM_6 = new Vector();// ����6
		public final static Vector NUM_7 = new Vector();// ����7
		public final static Vector NUM_8 = new Vector();// ����8
		public final static Vector NUM_9 = new Vector();// ����9
		static {
			UP.addElement(new Integer(-1));// ��
			DOWN.addElement(new Integer(-2));// ��
			LEFT.addElement(new Integer(-3));// ��
			RIGHT.addElement(new Integer(-4));// ��
			OK.addElement(new Integer(-5));// ȷ��/OK
			MENU.addElement(new Integer(-6));// �˵�
			BACK.addElement(new Integer(-7));// ����
			BACK.addElement(new Integer(-31));// HUAWEI_EC6108V9A
			BACK.addElement(new Integer(-9));// HUAWEI_EC6108V9
			DEL.addElement(new Integer(-8));// ɾ��
			FAST_FORWARD.addElement(new Integer(-21));// ���/��ҳ
			REWIND.addElement(new Integer(-20));// ����/��ҳ
			INPUT.addElement(new Integer(-30));// ���뷨
			RED.addElement(new Integer(42));// ֱ��/��/F1
			GREEN.addElement(new Integer(42));// �ؿ�/��/F2
			YELLOW.addElement(new Integer(42));// �㲥/��/F3
			BLUE.addElement(new Integer(42));// ��ѯ/��/F4
			VOLUMN_DEC.addElement(new Integer(35));// ������
			NUM_0.addElement(new Integer(48));// ����0
			NUM_1.addElement(new Integer(49));// ����1
			NUM_2.addElement(new Integer(50));// ����2
			NUM_3.addElement(new Integer(51));// ����3
			NUM_4.addElement(new Integer(52));// ����4
			NUM_5.addElement(new Integer(53));// ����5
			NUM_6.addElement(new Integer(54));// ����6
			NUM_7.addElement(new Integer(55));// ����7
			NUM_8.addElement(new Integer(56));// ����8
			NUM_9.addElement(new Integer(57));// ����9
		}
	}

}
