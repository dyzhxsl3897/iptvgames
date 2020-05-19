package com.zhongdan.games.wuziqi;

import java.util.Vector;

public interface IPlayer {

	// 下一步棋子，传入对手已经下的棋子集合
	public Point run(Vector enemyPoints, Point point);

	public boolean hasWin();

	public void setChessboard(IChessboard chessboard);

	public Vector getMyPoints();

}
