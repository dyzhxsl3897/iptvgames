package com.company.iptvgames.poker.pokercards;

import java.util.Vector;

public class PokerCard {

	// 获取编号所代表花色
	public static int getFlower(int value) {
		return value % 4;
	}

	// 获取编号所代表牌面值
	public static int getCardNumber(int value) {
		int result = value;

		if (value <= 52) {
			result = (value + 3) / 4;
		}

		// 1为A，牌面值为14
		if (result == 1) {// 1代表A
			result = 14;
		}

		// 2牌面值为15
		if (result == 2) {
			result = 15;
		}

		return result;
	}

	// 获取牌面值value的个数
	public static int getSameCardCount(int[] cards, int value) {
		int count = 0;
		for (int i = 0; i < cards.length; i++) {
			if (getCardNumber(cards[i]) == value) {
				count++;
			}
		}
		return count;
	}

	// 检查顺子
	public static boolean isStraight(int[] cards) {
		boolean isStraight = true;
		int temp1 = getCardNumber(cards[0]);

		for (int i = 1; i < cards.length; i++) {
			int temp2 = getCardNumber(cards[i]);
			if (temp1 < 3) {// 顺子不包含3以下值
				isStraight = false;
			} else if (temp2 - temp1 != 1) {
				isStraight = false;
			}
			temp1 = temp2;
		}

		return isStraight;
	}

	// 纸牌从小到大排序
	public static void sort(int[] cards) {
		int length = cards.length;
		for (int i = 0; i < length; i++) {
			for (int j = i + 1; j < length; j++) {
				if (cards[j] < cards[i]) {
					int temp = cards[j];
					cards[j] = cards[i];
					cards[i] = temp;
				}
			}
		}
	}

	// 判断牌型
	public static int getCardsType(int[] cards) {
		int length = cards.length;

		// 单张
		if (length == 1) {
			return CardsType.single;
		}

		// 对子，火箭
		if (length == 2) {
			if (getCardNumber(cards[0]) == getCardNumber(cards[1])) {
				return CardsType.pair;
			} else if (cards[0] > 52 && cards[1] > 52) {
				return CardsType.rocket;
			}
		}

		// 三张
		if (length == 3) {
			if (getSameCardCount(cards, getCardNumber(cards[0])) == 3) {
				return CardsType.triple;
			}
		}

		// 三带一，炸弹
		if (length == 4) {
			int first = getSameCardCount(cards, getCardNumber(cards[0]));
			int second = getSameCardCount(cards, getCardNumber(cards[1]));
			if (first == 4) {
				return CardsType.bomp;
			} else if (first == 3 || second == 3) {
				return CardsType.tripleWithOne;
			}
		}

		// 四带二
		if (length == 6 || length == 8) {
			boolean temp1 = false;
			boolean temp2 = false;
			for (int i = 0; i < length; i++) {
				int count = getSameCardCount(cards, getCardNumber(cards[i]));
				if (count == 4) {
					temp1 = true;
				} else {
					if (length == 8 && count == 2) {
						temp2 = true;
					} else if (length == 6 && count == 1) {
						temp2 = true;
					}
				}

			}

			if (temp1 && temp2) {
				return CardsType.fourWithTwo;
			}
		}

		// 顺子
		if (length >= 5) {
			if (isStraight(cards)) {
				return CardsType.straight;
			}
		}

		// 三顺、双顺
		if (length >= 6) {
			boolean straightPair = true;
			boolean straightTriple = true;

			for (int i = 0; i < length; i++) {
				int count = getSameCardCount(cards, getCardNumber(cards[i]));
				if (count != 2) {
					straightPair = false;
				}
				if (count != 3) {
					straightTriple = false;
				}
			}

			if (straightPair) {
				int[] tempCards = new int[length / 2];
				for (int i = 0; i < tempCards.length; i++) {
					tempCards[i] = cards[i * 2];
				}

				if (isStraight(tempCards)) {
					return CardsType.straightPair;
				}
			}

			if (straightTriple) {
				int[] tempCards = new int[length / 3];
				for (int i = 0; i < tempCards.length; i++) {
					tempCards[i] = cards[i * 3];
				}

				if (isStraight(tempCards)) {
					return CardsType.straightTriple;
				}
			}
		}

		// 飞机
		if (length == 8 || length == 10) {
			int size = 2;// 3张同牌的个数
			int[] value = new int[size];
			boolean isPlane = true;

			for (int i = 0; i < length; i++) {
				int count = getSameCardCount(cards, getCardNumber(cards[i]));
				int index = 0;

				if (length == 10 && count < 2) {
					isPlane = false;
				} else if (count == 3) {
					value[index] = getCardNumber(cards[i]);
					i = i + 4;
					index++;
				}
			}

			if (value[1] - value[0] != 1 | value[1] - value[0] != -1) {
				isPlane = true;
			}

			if (isPlane) {
				return CardsType.plane;
			}
		}

		// 错误牌
		return CardsType.eror;
	}

	// 计算一组牌所代表牌值大小，便于两组牌比较大小
	public static int getValueOfCardsType(int[] cards) {
		int typeTemp = getCardsType(cards);
		int value = 0;// 默认值，或非正确牌型时值为0

		if (typeTemp == CardsType.single || typeTemp == CardsType.pair || typeTemp == CardsType.triple || typeTemp == CardsType.bomp
				|| typeTemp == CardsType.straight || typeTemp == CardsType.straightPair || typeTemp == CardsType.straightTriple
				|| typeTemp == CardsType.straight) {
			value = cards[0];
		}

		if (typeTemp == CardsType.tripleWithOne || typeTemp == CardsType.plane) {
			for (int i = 0; i < cards.length; i++) {
				if (getSameCardCount(cards, cards[i]) == 3) {
					value = cards[i];
				}
			}
		}

		if (typeTemp == CardsType.fourWithTwo) {
			for (int i = 0; i < cards.length; i++) {
				if (getSameCardCount(cards, cards[i]) == 4) {
					value = cards[i];
				}
			}
		}

		return getCardNumber(value);
	}

	// 判断第二组牌是否大于第一组牌，默认false
	public static boolean isFristBiggerThanSecond(int[] temp1, int[] temp2) {
		// 默认temp2小
		boolean firstWin = false;

		int type1 = getCardsType(temp1);
		int type2 = getCardsType(temp2);

		// 牌型相同，牌个数相同
		if (type1 == type2 && temp1.length == temp2.length) {
			int value1 = getValueOfCardsType(temp1);
			int value2 = getValueOfCardsType(temp2);

			if (value1 <= value2) {
				firstWin = false;
			} else {
				firstWin = true;
			}
		}

		// 牌型不同
		if (type1 != type2) {
			if (type1 == CardsType.rocket) {
				firstWin = true;
			} else if (type1 == CardsType.bomp) {
				firstWin = true;
			}
		}

		return firstWin;
	}

	// npc自动出牌--最多出牌
	public static int[] bestShow(int[] allCardsOfPlayer) {
		int[] typeSeq = new int[] { 8, 7, 6, 5, 9, 4, 3, 2, 1, 10, 11 };
		int[] result = new int[] { allCardsOfPlayer[0] };// 确保至少出一张牌
		int tempLength = -1;
		int resultLength = -1;

		AllCombinations all = AllCombinations.getInstance();
		all.AIcards(allCardsOfPlayer);

		for (int i = 0; i < typeSeq.length; i++) {
			// System.out.println("in bestShow(), check type:"+typeSeq[i]);
			Vector cardsVector = all.getVectorOfType(typeSeq[i]);
			for (int j = 0; j < cardsVector.size(); j++) {
				int[] temp = (int[]) cardsVector.elementAt(j);
				tempLength = temp.length;
				System.out.println("in bestShow(), check tempLength:" + tempLength + ",resultLength:" + resultLength);
				if (tempLength > resultLength) {
					result = temp;
					resultLength = result.length;
				}
			}

			// 当查完顺子类型后，查询结果非空，则不需要继续查找其他类型了
			if (resultLength >= 5 && i >= 3) {
				i = typeSeq.length;
			}

			// 当查完单张类型后，查询结果非空，则不需要继续查找其他类型了
			if (resultLength >= 1 && i >= 7) {
				i = typeSeq.length;
			}
		}

		return result;
	}

	// npc自动出牌--大于上家出牌
	public static int[] betterShow(int[] cardsOfPrePlayer, int[] allCardsOfPlayer) {
		int[] typeSeq = null;
		int preTpye = getCardsType(cardsOfPrePlayer);
		int preValue = getValueOfCardsType(cardsOfPrePlayer);
		int preLength = cardsOfPrePlayer.length;
		int[] result = null;
		int tempvalue = -1;
		Vector resultOfSameTypeVector = new Vector();
		Vector resultOfMoreTypeVector = new Vector();
		boolean must = false;
		int tempIndex = -1;
		int resultIndex = -1;
		int resultvalue = -1;

		AllCombinations all = AllCombinations.getInstance();
		all.AIcards(allCardsOfPlayer);
		/*- New Logic 
		System.out.println("wym0");
		Vector cardsVector = all.getVectorOfType(preTpye);
		for (int i = 0; i < cardsVector.size(); i++) {
			System.out.println("wym00");
			int[] temp = (int[]) cardsVector.elementAt(i);
			tempvalue = getValueOfCardsType(temp);
			if (tempvalue > preValue) {
				resultOfSameTypeVector.addElement(temp);
				resultOfMoreTypeVector.addElement(temp);
			}
			System.out.print("temp :");
			for (int k = 0; k < temp.length; k++) {
				System.out.print(temp[k] + ",");
			}
			System.out.println("finished");

			System.out.print("resultOfSameTypeVector :");
			for (int k = 0; k < temp.length; k++) {
				System.out.print(((int[]) cardsVector.elementAt(0))[k] + ",");
			}
			System.out.println("finished");
		}

		System.out.println("wym1");

		if (must) {
			System.out.println("wym2");
			if (preTpye == CardsType.single) {
				typeSeq = new int[] { 10, 11, 2, 3 };
			} else if (preTpye == CardsType.pair) {
				typeSeq = new int[] { 10, 11, 3 };
			} else if (preTpye == CardsType.straight) {
				typeSeq = new int[] { 10, 11, 6, 7 };
			} else if (preTpye == CardsType.straightPair) {
				typeSeq = new int[] { 10, 11, 7 };
			} else if (preTpye == CardsType.bomp) {
				typeSeq = new int[] { 11 };
			} else {
				typeSeq = new int[] { 10, 11 };
			}

			for (int j = 0; j < typeSeq.length; j++) {
				cardsVector = all.getVectorOfType(typeSeq[j]);
				if (cardsVector != null) {
					for (int i = 0; i < cardsVector.size(); i++) {
						int[] temp = (int[]) cardsVector.elementAt(i);
						if (typeSeq[j] == 10 || typeSeq[j] == 11) {
							resultOfMoreTypeVector.addElement(temp);
						} else {
							System.out.println("wym3");
							if (typeSeq[j] == 6 || typeSeq[j] == 2) {
								if (temp.length / 2 >= preLength) {
									for (int k = 0; k < temp.length / 2 - preLength; k = k + 2) {
										if (temp[k] > preValue) {
											resultOfMoreTypeVector.addElement(temp);
										}
									}
								}
							}
							if (typeSeq[j] == 7 || typeSeq[j] == 3) {
								if (temp.length / 3 >= preLength) {
									for (int k = 0; k < temp.length / 3 - preLength; k = k + 3) {
										if (temp[k] > preValue) {
											resultOfMoreTypeVector.addElement(temp);
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (must) {
			if (resultOfMoreTypeVector != null) {
				result = (int[]) (resultOfMoreTypeVector.elementAt(0));
				return result;
			} else {
				return null;
			}
		} else {
			System.out.println("wym5");
			if (resultOfSameTypeVector != null) {
				System.out.println("wym6");
				result = (int[]) resultOfSameTypeVector.elementAt(0);
				return result;
			} else {
				return null;
			}
		}
		 */

		Vector cardsVector = all.getVectorOfType(preTpye);
		for (int i = 0; i < cardsVector.size(); i++) {
			int[] temp = (int[]) cardsVector.elementAt(i);
			tempvalue = getValueOfCardsType(temp);
			if (preLength == temp.length && tempvalue > preValue) {
				tempIndex = i;
				if (tempvalue < resultvalue || resultIndex == -1) {
					resultIndex = tempIndex;
					resultvalue = tempvalue;
					result = temp;
				}
			}
		}

		if (result != null) {
			return result;
		} else {
			return null;
		}
		// end old
	}
}
