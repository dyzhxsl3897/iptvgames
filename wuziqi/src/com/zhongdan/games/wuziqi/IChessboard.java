package com.zhongdan.games.wuziqi;

import java.util.Vector;

public interface IChessboard {

	// ȡ��������������
	public int getMaxX();

	// ���������
	public int getMaxY();

	// ȡ�õ�ǰ���пհ׵㣬��Щ��ſ�������
	public Vector getFreePoints();

}
