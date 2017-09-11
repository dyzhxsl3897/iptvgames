package com.zhongdan.games.wuziqi;

import java.util.Vector;

public class HumanPlayer extends BasePlayer {

	public Point run(Vector enemyPoints, Point p) {
		getMyPoints().addElement(p);
		allFreePoints.removeElement(p);
		return p;
	}

}
