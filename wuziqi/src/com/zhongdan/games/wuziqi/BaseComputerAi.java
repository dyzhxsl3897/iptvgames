package com.zhongdan.games.wuziqi;

import java.util.Vector;

//�㷨�����࣬�㷨������˼����������裬  
//��һ��������˫���ĵ�ǰ������ѭ���ؼ����Եķֱ���Լ��ͶԷ���һ�ӣ���ĳ����Χ�����ӣ������жϴ������ܴ����������ϵı仯�����ܲ��ܳ�4���ܲ����γ��ҷ���з�˫3�ȣ�  
//�ڶ�����������һ����������ÿһ�����������������н������ĳһ�����ӿ����γ��ҷ�1����3��1����4���ҽ������4���ȣ��������з����ҷ��ġ�  
//�������������û����Ĺ������һ������������򣬲�ѡ�ӣ��н����Ρ������ι���
public class BaseComputerAi extends BasePlayer {

	// �����ӣ�����ӿ�
	public Point run(Vector humans, Point p) {
		// // �������µ����һ������ȥ��
		// allFreePoints.removeElement((Point) humans.elementAt(humans.size() - 1));
		// // ���Կ����µ�һ������
		// Point result = doAnalysis(myPoints, humans);
		// // ȥ�������µ�����
		// allFreePoints.removeElement(result);
		// // ���뵽���������У�������
		// myPoints.addElement(result);

		getMyPoints().addElement(p);
		allFreePoints.removeElement(p);
		return p;
	}

}
