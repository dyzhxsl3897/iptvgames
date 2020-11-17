package com.company.iptvgames.poker.pokercards;

import java.util.Vector;

public class AllCombinations {

	boolean firstShow = false;

	private int[] allCards;
	private Vector singleVector = new Vector();// 单张
	private Vector pairVector = new Vector();// 对子
	private Vector tripleVector = new Vector();// 三张
	private Vector tripleWithOneVector = new Vector();// 三带一
	private Vector straightVector = new Vector();// 顺子
	private Vector straightPairVector = new Vector();// 双顺子
	private Vector straightTripleVector = new Vector();// 三顺子
	private Vector planeVector = new Vector();// 飞机
	private Vector fourWithTwoVector = new Vector();// 四带二
	private Vector bompVector = new Vector();// 炸弹
	private Vector rocketVector = new Vector();// 火箭,双王

	private int[] commonPokes = new int[11];// 分别存储3到k的牌个数
	private int count2 = 0;
	private int count1 = 0;
	private int countKing = 0;

	private AllCombinations() {
	}

	public void AIcards(int[] plCards) {
		allCards = plCards;
		// allCards= new int[]{13,14,15,16,17,18,19,21,22,23,25,26,29,30,33,34};
		initAllVector();

		// 牌个数
		for (int i = 0; i < allCards.length; i++) {
			int cardValue = PokerCard.getCardNumber(allCards[i]);
			if (cardValue == 14) {
				count1++;
			} else if (cardValue == 15) {
				count2++;
			} else if (cardValue == 53 || cardValue == 54) {
				countKing++;
			} else {
				commonPokes[cardValue - 3]++;
			}
		}

		// 三顺子：l-顺子中不同牌的个数，
		boolean flag = false;
		for (int l = 2; l < allCards.length / 3; l++) {
			for (int i = 0; i <= commonPokes.length - l; i++) {
				if (commonPokes[i] >= 3) {
					flag = true;
					// 判断i后连续l个数是否为3
					for (int j = 1; j < l; j++) {
						if (commonPokes[i + j] < 3) {
							flag = false;
						}
					}

					if (flag) {
						int[] ss = new int[l * 3];
						int index = 0;

						for (int j = 0; j < allCards.length; j++) {// 依次检查每张牌
							if (PokerCard.getCardNumber(allCards[j]) == i + 3) { // 牌值与i相同，顺子开始取牌
								for (int k = 0; k < l; k++) {// 依次取不同数值的牌各取3张
									ss[index] = allCards[j];
									ss[index + 1] = allCards[j + 1];
									ss[index + 2] = allCards[j + 2];
									index = index + 3;
									j = j + commonPokes[i + k];
								}
								j = allCards.length;// 顺子取牌完成，无需再检查手牌中后面的牌，结束本次顺子
							}
						}
						straightTripleVector.addElement(ss);
						flag = false;
					}
				}
			}
		}

		// 双顺子
		flag = false;
		for (int l = 3; l < allCards.length / 2; l++) {
			for (int i = 0; i < commonPokes.length - l; i++) {
				if (commonPokes[i] == 2) {
					flag = true;
					// 判断i后连续l个数是否为2
					for (int j = 1; j < l; j++) {
						if (commonPokes[i + j] != 2) {
							flag = false;
						}
					}

					if (flag) {
						int[] ss = new int[l * 2];
						int index = 0;
						for (int j = 0; j < allCards.length; j++) {// 依次检查每张牌
							if (PokerCard.getCardNumber(allCards[j]) == i + 3) { // 牌值与i相同，顺子开始取牌
								for (int k = 0; k < l; k++) {// 依次取不同数值的牌各取3张
									ss[index] = allCards[j];
									ss[index + 1] = allCards[j + 1];
									index = index + 2;
									j = j + commonPokes[i + k];
								}
								j = allCards.length;// 顺子取牌完成，无需再检查手牌中后面的牌，结束本次顺子
							}
						}
						straightPairVector.addElement(ss);
						flag = false;
					}
				}
			}
		}

		// 顺子
		flag = false;
		for (int l = 5; l < allCards.length; l++) {
			for (int i = 0; i < commonPokes.length - l; i++) {
				if (commonPokes[i] > 1) {
					flag = true;
					// 判断i后连续l个数是否为1
					for (int j = 1; j < l; j++) {
						if (commonPokes[i + j] < 1) {
							flag = false;
						}
					}

					if (flag) {
						int[] ss = new int[l];
						int index = 0;
						for (int j = 0; j < allCards.length; j++) {
							if (PokerCard.getCardNumber(allCards[j]) == i + 3) {
								for (int k = 0; k < l; k++) {
									ss[index] = allCards[j];
									index++;
									j = j + commonPokes[i + k];
								}
							}
						}
						straightVector.addElement(ss);
						flag = false;
					}
				}
			}
		}

		// 三张
		for (int i = 0; i < commonPokes.length; i++) {
			if (commonPokes[i] == 3) {
				int[] ss = new int[3];
				int index = 0;
				for (int j = 0; j < allCards.length; j++) {
					if (PokerCard.getCardNumber(allCards[j]) == i + 3) {
						ss[index] = allCards[j];
						index++;
					}
				}
				tripleVector.addElement(ss);
			}
		}

		// 对子
		for (int i = 0; i < commonPokes.length; i++) {
			if (commonPokes[i] == 2) {
				int[] ss = new int[2];
				int index = 0;
				for (int j = 0; j < allCards.length; j++) {
					if (PokerCard.getCardNumber(allCards[j]) == i + 3) {
						ss[index] = allCards[j];
						index++;
					}
				}
				pairVector.addElement(ss);
			}
		}

		// 单牌
		for (int i = 0; i < commonPokes.length; i++) {
			if (commonPokes[i] == 1) {
				int[] ss = new int[1];
				for (int j = 0; j < allCards.length; j++) {
					if (PokerCard.getCardNumber(allCards[j]) == i + 3) {
						ss[0] = allCards[j];
					}
				}
				singleVector.addElement(ss);
			}
		}

		if (countKing == 1) {
			for (int i = 0; i < allCards.length; i++) {
				if (PokerCard.getCardNumber(allCards[i]) == 53 || PokerCard.getCardNumber(allCards[i]) == 54) {
					int[] ss = new int[1];
					ss[0] = allCards[i];
					singleVector.addElement(ss);
				}
			}
		}

		// 炸弹
		for (int i = 0; i < commonPokes.length; i++) {
			if (commonPokes[i] == 4) {
				int[] ss = new int[4];
				int index = 0;
				for (int j = 0; j < allCards.length; j++) {
					if (PokerCard.getCardNumber(allCards[j]) == i + 3) {
						ss[index] = allCards[j];
						index++;
					}
				}
				bompVector.addElement(ss);
			}
		}

		// 火箭
		if (countKing == 2) {
			int[] ss = new int[2];
			for (int i = 0; i < allCards.length; i++) {
				if (PokerCard.getCardNumber(allCards[i]) == 53) {
					ss[0] = allCards[i];
				}

				if (PokerCard.getCardNumber(allCards[i]) == 54) {
					ss[1] = allCards[i];
				}

				rocketVector.addElement(ss);
			}
		}

		// A，2分析
		if (count1 > 0) {
			int[] ss = new int[count1];
			int index = 0;
			for (int i = 0; i < allCards.length; i++) {
				if (PokerCard.getCardNumber(allCards[i]) == 14) {
					ss[index] = allCards[i];
					index++;
				}
			}

			if (count1 == 4) {
				bompVector.addElement(ss);
			} else if (count1 == 3) {
				tripleVector.addElement(ss);
			} else if (count1 == 2) {
				pairVector.addElement(ss);
			} else if (count1 == 1) {
				singleVector.addElement(ss);
			}
		}

		if (count2 > 0) {
			int[] ss = new int[count2];
			int index = 0;
			for (int i = 0; i < allCards.length; i++) {
				if (PokerCard.getCardNumber(allCards[i]) == 15) {
					ss[index] = allCards[i];
					index++;
				}
			}

			if (count2 == 4) {
				bompVector.addElement(ss);
			} else if (count2 == 3) {
				tripleVector.addElement(ss);
			} else if (count2 == 2) {
				pairVector.addElement(ss);
			} else if (count2 == 1) {
				singleVector.addElement(ss);
			}
		}

	}

	public static AllCombinations getInstance() {
		return new AllCombinations();
	}

	public void initAllVector() {
		for (int i = 0; i < commonPokes.length; i++) {
			commonPokes[i] = 0;
		}

		clear(straightTripleVector);
		clear(straightPairVector);
		clear(straightVector);
		clear(bompVector);
		clear(tripleVector);
		clear(pairVector);
		clear(singleVector);
		clear(tripleWithOneVector);
		clear(planeVector);
		clear(rocketVector);
		clear(fourWithTwoVector);
	}

	public void clear(Vector temp) {
		if (temp.size() != 0) {
			temp.removeAllElements();
		}
	}

	public Vector getVectorOfType(int index) {
		switch (index) {
		case 1:
			return singleVector;
		case 2:
			return pairVector;
		case 3:
			return tripleVector;
		case 4:
			return tripleWithOneVector;
		case 5:
			return straightVector;
		case 6:
			return straightPairVector;
		case 7:
			return straightTripleVector;
		case 8:
			return planeVector;
		case 9:
			return fourWithTwoVector;
		case 10:
			return bompVector;
		case 11:
			return rocketVector;
		}

		return null;
	}

	public Vector getVectorOfSingle() {
		return singleVector;
	}

	public Vector getVectorOfPair() {
		return pairVector;
	}

	public Vector getVectorOfTriple() {
		return tripleVector;
	}

	public Vector getVectorOfTripleWithOne() {
		return tripleWithOneVector;
	}

	public Vector getVectorOfStraight() {
		return straightVector;
	}

	public Vector getVectorOfStraightPair() {
		return straightPairVector;
	}

	public Vector getVectorOfStraightTriple() {
		return straightTripleVector;
	}

	public Vector getVectorOfPlane() {
		return planeVector;
	}

	public Vector getVectorOfFourWithTwo() {
		return fourWithTwoVector;
	}

	public Vector getVectorOfBomp() {
		return bompVector;
	}

	public Vector getVectorOfRocket() {
		return rocketVector;
	}

}
