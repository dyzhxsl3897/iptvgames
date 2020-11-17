package com.company.iptvgames.poker;

public class GameConst {
	public static final int FPS = 100; // This is not real FPS, this is time in milliseconds
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 530;

	public static class Menu {
		public static final int START_BTN_X = 259;// 开始按钮位置X
		public static final int START_BTN_Y = 353;// 开始按钮位置Y
	}

	public static class GameCanvas {
		public static final int LAYER_alert = 1;// alert起始图层号
		public static final int LAYER_alert_select = 0;// alert select起始图层号
		public static final int LAYER_player = 3;
		public static final int LAYER_button = 2;
		public static final int LAYER_card = 15;
		// public static final int LAYER_button = 9;//按钮起始图层号
		// public static final int LAYER_player = 23;//角色起始图层号
		// public static final int LAYER_card = 26;//纸牌起始图层号
		public static final int DISTANCEV = 14;// NPC纵向牌间距
		public static final int DISTANCEH = 20;// 玩家横向牌间距
		public static final int DISTANCES = 20;// 已选中牌的上移距离
		public static final int FRAMEOFTEXIAO = 8;// 特效动画帧数

		public static final int SCORE_X = 200;
		public static final int SCORE_Y = 16;
		public static final int LIFE_X = 60;
		public static final int LIFE_Y = 16;
		public static final int LIFE_BG_X = 10;
		public static final int LIFE_BG_Y = 10;

		public static final int CX_FINISH = 222;// 失败alert的继续按钮位置X
		public static final int CY_FINISH = 351;// 失败alert的继续按钮位置Y
		public static final int OX_FINISH = 335;// 失败alert的退出按钮位置X
		public static final int OY_FINISH = 351;// 失败alert的退出按钮位置Y

		public static final int CX_PAUSE = 264;// 暂停alert的继续标记按钮位置X
		public static final int CY_PAUSE = 289;// 暂停alert的继续标记按钮位置Y
		public static final int OX_PAUSE = 391;// 暂停alert的退出标记按钮位置X
		public static final int OY_PAUSE = 289;// 暂停alert的退出标记按钮位置Y
	}

	public static class Game {
		public static final int PORKERACCOUTS = 54;// 扑克牌张数
		public static final int PORKEREACHPERSON = 17;// 普通玩家扑克牌张数
		public static final int BOTTOMACCOUNTS = 3;// 底牌张数
		public static final int PLAYERACCOUNTS = 3;// 玩家总数
	}

	public static class Position {
		public static final int BOTTOOM_X1 = 257;// 底牌1的Top位置X
		public static final int BOTTOOM_X2 = 286;// 底牌2的Top位置X
		public static final int BOTTOOM_X3 = 315;// 底牌3的Top位置X
		public static final int BOTTOOM_Y = 4;// 底牌的Top位置Y
		public static final int BOTTOOM_BG_X = 166;// 底牌的背景位置X
		public static final int BOTTOOM_BG_Y = 0;// 底牌的背景位置X

		public static final int BOTTOOM_C_X1 = 222;// 底牌1的中间位置X
		public static final int BOTTOOM_C_X2 = 286;// 底牌2的中间位置X
		public static final int BOTTOOM_C_X3 = 350;// 底牌3的中间位置X
		public static final int BOTTOOM_C_Y = 110;// 底牌的中间位置Y

		public static final int PLAYER_X0 = 0;// 玩家1的位置X，下方
		public static final int PLAYER_Y0 = 312;// 玩家1的位置Y
		public static final int PLAYER_X1 = 593;// npc1的位置X，右方
		public static final int PLAYER_Y1 = 0;// npc1的位置Y
		public static final int PLAYER_X2 = 0;// npc2的位置X，左方
		public static final int PLAYER_Y2 = 0;// npc2的位置Y

		public static final int CARDS_X0 = 24;// 玩家1牌面的位置X，下方
		public static final int CARDS_Y0 = 395;// 玩家1牌面的位置Y
		public static final int CARDS_X1 = 526;// npc1牌面的位置X，右方
		public static final int CARDS_Y1 = 47;// npc1牌面的位置Y
		public static final int CARDS_X2 = 45;// npc2牌面的位置X，左方
		public static final int CARDS_Y2 = 47;// npc2牌面的位置Y
		public static final int ARROWICON_X = 30;// 选中icon的位置X
		public static final int ARROWICON_Y = 350;// 选中icon的位置Y
		public static final int DISTANCE_NEXT_LINE = 65;// npc牌面第二列的位置偏移

		// public static final int CALL_X0 = 24;//玩家1说话的位置X，下方
		// public static final int CALL_Y0 = 375;//玩家1说话的位置Y
		// public static final int CALL_X1 = 526;//npc1说话的位置X，右方
		// public static final int CALL_Y1 = 47;//npc1说话的位置Y
		// public static final int CALL_X2 = 45;//npc2说话的位置X，左方
		// public static final int CALL_Y2 = 47;//npc2说话的位置Y
		public static final int CALL_X0 = 200;// 玩家1说话的位置X，下方
		public static final int CALL_Y0 = 270;// 玩家1说话的位置Y
		public static final int CALL_X1 = 426;// npc1说话的位置X，右方
		public static final int CALL_Y1 = 47;// npc1说话的位置Y
		public static final int CALL_X2 = 120;// npc2说话的位置X，左方
		public static final int CALL_Y2 = 47;// npc2说话的位置Y

		public static final int SHOW_CARDS_X0 = 200;// 玩家1出牌的位置X，下方
		public static final int SHOW_CARDS_Y0 = 250;// 玩家1出牌的位置Y

		public static final int BUCHU_X = 120;// 按钮：不出
		public static final int RESELECT_X = 285;// 按钮：重选
		public static final int TIP_X = 376;// 按钮：提示
		public static final int CHUPAI_X = 461;// 按钮：出牌
		public static final int BUTTON_Y = 318;// 按钮Y坐标

		public static final int START_X = 300;// 按钮：开始新局
		public static final int START_Y = 270;// 按钮Y坐标

		public static final int SCORE1_X = 162;// 按钮：叫1分
		public static final int SCORE2_X = 232;// 按钮：叫2分
		public static final int SCORE3_X = 302;// 按钮：叫3分
		public static final int SCORE4_X = 372;// 按钮：不叫
		public static final int SCORE_Y = 70;// 按钮Y坐

		public static final int WIN_X = 156;// 胜利坐标X
		public static final int WIN_Y = 0;// 胜利Y坐标
		public static final int TEXIAO_X = 257;// 特效文字坐标X
		public static final int TEXIAO_Y = 188;// 特效文字Y坐标
		public static final int GUIZE_X = 242;// 规则坐标X
		public static final int GUIZE_Y = 490;// 规则文字Y坐标
		public static final int FJA_X = 11;// 飞机动画坐标X
		public static final int FJA_Y = 81;// 飞机动画Y坐标
		public static final int ZDA_X = 271;// 炸弹动画坐标X
		public static final int ZDA_Y = 130;// 炸弹动画Y坐标
		public static final int HJA_X = 262;// 火箭动画坐标X
		public static final int HJA_Y = 49;// 火箭动画Y坐标
	}
}
