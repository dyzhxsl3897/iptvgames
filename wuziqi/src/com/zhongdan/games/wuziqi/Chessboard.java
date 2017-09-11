package com.zhongdan.games.wuziqi;

import java.util.Vector;

public class Chessboard implements IChessboard {

	Vector freePoints = new Vector();

	public Chessboard() {
		for (int i = 0; i < getMaxX(); i++) {
			for (int j = 0; j < getMaxY(); j++) {
				freePoints.addElement(new Point(i, j));
			}
		}
	}

	public Vector getFreePoints() {
		return freePoints;
	}

	public int getMaxX() {
		return 15;
	}

	public int getMaxY() {
		return 15;
	}

}
