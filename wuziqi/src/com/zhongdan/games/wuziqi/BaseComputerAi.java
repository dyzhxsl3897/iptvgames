package com.zhongdan.games.wuziqi;

import java.util.Vector;

//算法核心类，算法的主体思想分三个步骤，  
//第一步：根据双方的当前的形势循环地假设性的分别给自己和对方下一子（在某个范围内下子），并判断此棋子能带来的形势上的变化，如能不能冲4，能不能形成我方或敌方双3等，  
//第二步：根据上一步结果，组合每一步棋子所带来的所有结果（如某一步棋子可能形成我方1个活3，1个冲4（我叫它半活4）等），包括敌方和我方的。  
//第三步：根据用户给的规则对上一步结果进行排序，并选子（有进攻形、防守形规则）
public class BaseComputerAi extends BasePlayer {

	// 下棋子，对外接口
	public Point run(Vector humans, Point p) {
		// // 把人类下的最后一步棋子去除
		// allFreePoints.removeElement((Point) humans.elementAt(humans.size() - 1));
		// // 电脑可以下的一步棋子
		// Point result = doAnalysis(myPoints, humans);
		// // 去除电脑下的棋子
		// allFreePoints.removeElement(result);
		// // 加入到电脑棋子中，下棋了
		// myPoints.addElement(result);

		getMyPoints().addElement(p);
		allFreePoints.removeElement(p);
		return p;
	}

}
