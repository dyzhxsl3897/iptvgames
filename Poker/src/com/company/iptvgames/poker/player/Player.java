package com.company.iptvgames.poker.player;

import java.util.Vector;

import javax.microedition.lcdui.game.LayerManager;

import com.company.iptvgames.poker.GameConst;
import com.company.iptvgames.poker.pokercards.Card;
import com.company.iptvgames.poker.pokercards.CardsType;
import com.company.iptvgames.poker.pokercards.PokerCard;

public class Player {

	private int id;// 牌桌的座位编号，0：玩家；1：下家；2：上家
	private int tagID; // 0：地主；1：非地主
	private String name;

	int[] plCards;// 全部手牌
	private Vector cardsVector;// 全部手牌实例集

	boolean[] isSelected;// 全部手牌选中标记集
	private Vector selectedCardsVector;// 出牌实例集

	int posX, posY;
	int plDir;
	Card newCardObject;

	LayerManager layerManager;

	public Player(int id, LayerManager layerManager) {

		this.id = id;
		this.layerManager = layerManager;
		this.cardsVector = new Vector();
		this.selectedCardsVector = new Vector();
		this.tagID = 1;
	}

	// 获取玩家在牌桌的座位编号
	public int getId() {
		return id;
	}

	// //获取玩家名称
	// public String getName() {
	// return name;
	// }

	// //设置玩家名称
	// public void setName(String name) {
	// this.name = name;
	// }

	// 设置玩家类型
	public void setTagID(int value) {
		tagID = value;
	}

	// 获取玩家类型
	public int getTagID() {
		return tagID;
	}

	// 更新玩家全部手牌
	public void updateCards(int[] newCards) {
		plCards = newCards;
		isSelected = new boolean[newCards.length];

		if (cardsVector != null) {
			for (int i = 0; i < cardsVector.size(); i++) {
				((Card) cardsVector.elementAt(i)).removeFromscreen(layerManager);
			}
			cardsVector.removeAllElements();
		}

		for (int i = 0; i < newCards.length; i++) {
			newCardObject = new Card(newCards[i], false);
			isSelected[i] = false;
			cardsVector.addElement(newCardObject);
		}

	}

	// 绘制玩家全部手牌并显示到界面
	public void paintCards(int[] plCards, boolean show) {
		int distanceInV = GameConst.GameCanvas.DISTANCEV;
		int distanceInH = GameConst.GameCanvas.DISTANCEH;

		for (int i = 0; i < cardsVector.size(); i++) {
			int distance = 0;
			if (id == 0) {
				if (isSelected[i]) {
					distance = GameConst.GameCanvas.DISTANCES;
				}
				((Card) cardsVector.elementAt(i)).setPosition(GameConst.Position.CARDS_X0 + i * distanceInH, GameConst.Position.CARDS_Y0 - distance);

			}
			if (id == 1) {
				((Card) cardsVector.elementAt(i)).setPosition(GameConst.Position.CARDS_X1, GameConst.Position.CARDS_Y1 + i * distanceInV);
			}
			if (id == 2) {
				((Card) cardsVector.elementAt(i)).setPosition(GameConst.Position.CARDS_X2, GameConst.Position.CARDS_Y2 + i * distanceInV);
			}
			((Card) cardsVector.elementAt(i)).setVisible(show);
			((Card) cardsVector.elementAt(i)).addToscreen(layerManager);
		}
	}

	// 绘制玩家所出牌并显示到界面
	public void paintSelectedCards(int[] selectedCards) {
		int distanceInV = GameConst.GameCanvas.DISTANCEH;
		int distanceInH = GameConst.GameCanvas.DISTANCEH;

		if (selectedCardsVector != null) {
			for (int i = 0; i < selectedCardsVector.size(); i++) {
				((Card) selectedCardsVector.elementAt(i)).removeFromscreen(layerManager);
			}
			selectedCardsVector.removeAllElements();
		}

		if (selectedCards != null) {
			for (int i = 0; i < selectedCards.length; i++) {
				newCardObject = new Card(selectedCards[i], false);
				selectedCardsVector.addElement(newCardObject);

				if (id == 0) {
					newCardObject.setPosition(GameConst.Position.SHOW_CARDS_X0 + i * distanceInH, GameConst.Position.SHOW_CARDS_Y0);
				}
				if (id == 1) {
					if (i <= 9) {
						newCardObject.setPosition(GameConst.Position.CARDS_X1 - GameConst.Position.DISTANCE_NEXT_LINE, GameConst.Position.CARDS_Y1
								+ i * distanceInV);
					} else {
						newCardObject.setPosition(GameConst.Position.CARDS_X1 - GameConst.Position.DISTANCE_NEXT_LINE * 2,
								GameConst.Position.CARDS_Y1 + (i - 10) * distanceInV);
					}
				}
				if (id == 2) {
					if (i <= 9) {
						newCardObject.setPosition(GameConst.Position.CARDS_X2 + GameConst.Position.DISTANCE_NEXT_LINE, GameConst.Position.CARDS_Y2
								+ i * distanceInV);
					} else {
						newCardObject.setPosition(GameConst.Position.CARDS_X2 + GameConst.Position.DISTANCE_NEXT_LINE * 2,
								GameConst.Position.CARDS_Y2 + (i - 10) * distanceInV);
					}
				}

				newCardObject.setVisible(true);
				newCardObject.addToscreen(layerManager);
			}
		}

	}

	// 获取玩家全部手牌
	public int[] plCards() {
		return plCards;
	}

	// 设置第index张牌的选中标记项为state
	public void selectCardOrNot(int index, boolean state) {
		isSelected[index] = state;
		paintCards(plCards, id == 0);
		// //test
		// paintCards(plCards, true);
		// //end test
	}

	// 设置列表中牌为选中标记
	public void selectCardsArray(int[] wantSelected) {
		for (int i = 0; i < plCards().length; i++) {
			selectCardOrNot(i, false);
		}

		if (wantSelected != null) {
			for (int i = 0; i < wantSelected.length; i++) {
				for (int j = 0; j < plCards.length; j++) {
					if (wantSelected[i] == plCards[j]) {
						isSelected[j] = true;
					}
				}
			}
		}
	}

	// 反置第index张牌的选中标记项
	public void reserveCardSelectStatus(int index) {
		if (isSelected[index]) {
			isSelected[index] = false;
		} else {
			isSelected[index] = true;
		}
		paintCards(plCards, id == 0);
		// //test
		// paintCards(plCards, true);
		// //end test
	}

	// 获取第index张牌的选中标记项
	public boolean getCardSelectStatus(int index) {
		return isSelected[index];
	}

	// 获取玩家选中的牌集合
	public int[] getCardsOfSelected() {
		int amount = 0;
		int index = 0;
		int[] result = null;

		for (int i = 0; i < isSelected.length; i++) {
			if (isSelected[i]) {
				amount++;
			}
		}

		if (amount != 0) {
			result = new int[amount];
			for (int j = 0; j < plCards.length; j++) {
				if (isSelected[j]) {
					result[index] = plCards[j];
					index++;
				}
			}
		}

		return result;
	}

	// 智能选牌，大于temp
	public int[] selectByAI(int[] temp) {
		int[] result = null;

		if (temp == null) {
			System.out.println("NPC bestShow:");
			result = PokerCard.bestShow(plCards);
		} else {
			System.out.println("NPC betterShow:");
			result = PokerCard.betterShow(temp, plCards);
		}

		if (result != null) {
			System.out.println("NPC selectCardsArray:");
			selectCardsArray(result);
			return result;
		} else {
			return null;
		}
	}

	// 移除牌
	public void removeSelectedCards(int[] temp) {

		// 玩家手牌中移除result中的相同牌
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < plCards.length; j++) {
				if (plCards[j] == temp[i]) {
					plCards[j] = -1;
					break;
				}
			}
		}

		int[] plCardsNew = new int[plCards.length - temp.length];
		int index = 0;
		for (int k = 0; k < plCards.length; k++) {
			if (plCards[k] != -1) {
				plCardsNew[index] = plCards[k];
				index++;
			}
		}

		plCards = plCardsNew;

		updateCards(plCards);

		paintCards(plCards, id == 0);
		// //test
		// paintCards(plCards, true);
		// //end test
	}

	// 玩家出牌,返回结果分别为0:允许出牌，1：牌型不存在，2：所出牌型不一致，3：所出牌小于上家牌
	public int readyForChupai(int[] ready, int[] old) {
		int result = 3;
		System.out.print("ready value :");
		for (int n = 0; n < ready.length; n++) {
			System.out.print(ready[n] + ",");
		}
		System.out.println("end");

		if (PokerCard.getCardsType(ready) != CardsType.eror) {
			if (old != null) {
				if (PokerCard.isFristBiggerThanSecond(ready, old)) {
					result = 0;
				} else {
					if (PokerCard.getCardsType(ready) != PokerCard.getCardsType(old)) {
						result = 2;
						System.out.println("different type :" + PokerCard.getCardsType(old) + "," + PokerCard.getCardsType(ready));
					} else {
						result = 3;
						System.out.println("old type :" + PokerCard.getCardsType(old) + "," + PokerCard.getValueOfCardsType(old));
						System.out.println("new type :" + PokerCard.getCardsType(ready) + "," + PokerCard.getValueOfCardsType(ready));
					}
				}
			} else {
				result = 0;
			}
		} else {
			result = 1;
			System.out.println("eror type :");
		}

		return result;
	}

}
