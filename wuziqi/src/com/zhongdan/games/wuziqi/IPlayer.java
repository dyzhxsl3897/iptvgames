package com.zhongdan.games.wuziqi;

import java.util.Vector;

public interface IPlayer {

	// ��һ�����ӣ���������Ѿ��µ����Ӽ���
	public Point run(Vector enemyPoints, Point point);

	public boolean hasWin();

	public void setChessboard(IChessboard chessboard);

	public Vector getMyPoints();

}
