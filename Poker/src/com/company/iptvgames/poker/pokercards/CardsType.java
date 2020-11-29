package com.company.iptvgames.poker.pokercards;

//定义支持的牌型
public interface CardsType {
	int single = 1;// 单张
	int pair = 2;// 对子
	int triple = 3;// 三张
	int tripleWithOne = 4;// 三带一
	int straight = 5;// 顺子
	int straightPair = 6;// 双顺子
	int straightTriple = 7;// 三顺子
	int plane = 8;// 飞机
	int fourWithTwo = 9;// 四带二
	int bomp = 10;// 炸弹
	int rocket = 11;// 火箭,双王
	int eror = 12;// 错误牌型
}
