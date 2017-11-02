package com.zhongdan.games.framework.utils;

import java.util.Vector;

public class Constants {

	public static class KeyCode {
		public final static Vector UP = new Vector();// 上
		public final static Vector DOWN = new Vector();// 下
		public final static Vector LEFT = new Vector();// 左
		public final static Vector RIGHT = new Vector();// 右
		public final static Vector OK = new Vector();// 确定/OK
		public final static Vector MENU = new Vector();// 菜单
		public final static Vector BACK = new Vector();// 返回
		public final static Vector DEL = new Vector();// 删除
		public final static Vector FAST_FORWARD = new Vector();// 快进/下页
		public final static Vector REWIND = new Vector();// 快退/上页
		public final static Vector INPUT = new Vector();// 输入法
		public final static Vector RED = new Vector();// 直播/红/F1
		public final static Vector GREEN = new Vector();// 回看/绿/F2
		public final static Vector YELLOW = new Vector();// 点播/黄/F3
		public final static Vector BLUE = new Vector();// 咨询/蓝/F4
		public final static Vector VOLUMN_DEC = new Vector();// 音量减
		public final static Vector NUM_0 = new Vector();// 数字0
		public final static Vector NUM_1 = new Vector();// 数字1
		public final static Vector NUM_2 = new Vector();// 数字2
		public final static Vector NUM_3 = new Vector();// 数字3
		public final static Vector NUM_4 = new Vector();// 数字4
		public final static Vector NUM_5 = new Vector();// 数字5
		public final static Vector NUM_6 = new Vector();// 数字6
		public final static Vector NUM_7 = new Vector();// 数字7
		public final static Vector NUM_8 = new Vector();// 数字8
		public final static Vector NUM_9 = new Vector();// 数字9
		static {
			UP.addElement(new Integer(-1));// 上
			DOWN.addElement(new Integer(-2));// 下
			LEFT.addElement(new Integer(-3));// 左
			RIGHT.addElement(new Integer(-4));// 右
			OK.addElement(new Integer(-5));// 确定/OK
			MENU.addElement(new Integer(-6));// 菜单
			BACK.addElement(new Integer(-7));// 返回
			BACK.addElement(new Integer(-31));// HUAWEI_EC6108V9A
			BACK.addElement(new Integer(-9));// HUAWEI_EC6108V9
			DEL.addElement(new Integer(-8));// 删除
			FAST_FORWARD.addElement(new Integer(-21));// 快进/下页
			REWIND.addElement(new Integer(-20));// 快退/上页
			INPUT.addElement(new Integer(-30));// 输入法
			RED.addElement(new Integer(42));// 直播/红/F1
			GREEN.addElement(new Integer(42));// 回看/绿/F2
			YELLOW.addElement(new Integer(42));// 点播/黄/F3
			BLUE.addElement(new Integer(42));// 咨询/蓝/F4
			VOLUMN_DEC.addElement(new Integer(35));// 音量减
			NUM_0.addElement(new Integer(48));// 数字0
			NUM_1.addElement(new Integer(49));// 数字1
			NUM_2.addElement(new Integer(50));// 数字2
			NUM_3.addElement(new Integer(51));// 数字3
			NUM_4.addElement(new Integer(52));// 数字4
			NUM_5.addElement(new Integer(53));// 数字5
			NUM_6.addElement(new Integer(54));// 数字6
			NUM_7.addElement(new Integer(55));// 数字7
			NUM_8.addElement(new Integer(56));// 数字8
			NUM_9.addElement(new Integer(57));// 数字9
		}
	}

}
