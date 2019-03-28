package com.company.iptvgames.peiqidown.boards;

import com.company.iptvgames.framework.utils.RandomUtil;

public class BoardFactory {

	private BoardFactory() {
	}

	private static class SingletonHolder {
		private static final BoardFactory instance = new BoardFactory();
	}

	public static BoardFactory getInstance() {
		return SingletonHolder.instance;
	}

	public Board createRandomBoard(int x, int y, int index) {
		int randomInt = RandomUtil.nextInt(6);
		Board board = null;
		switch (randomInt) {
		case 0:
			board = this.createFlapBoard(x, y, index);
			break;
		case 1:
			board = this.createLandBoard(x, y, index);
			break;
		case 2:
			board = this.createLeftBoard(x, y, index);
			break;
		case 3:
			board = this.createRightBoard(x, y, index);
			break;
		case 4:
			board = this.createSpringBoard(x, y, index);
			break;
		case 5:
			board = this.createStabBoard(x, y, index);
			break;
		}
		return board;
	}

	private FlapBoard createFlapBoard(int x, int y, int index) {
		return new FlapBoard(x, y, index);
	}

	private LandBoard createLandBoard(int x, int y, int index) {
		return new LandBoard(x, y, index);
	}

	private LeftBoard createLeftBoard(int x, int y, int index) {
		return new LeftBoard(x, y, index);
	}

	private RightBoard createRightBoard(int x, int y, int index) {
		return new RightBoard(x, y, index);
	}

	private SpringBoard createSpringBoard(int x, int y, int index) {
		return new SpringBoard(x, y, index);
	}

	private StabBoard createStabBoard(int x, int y, int index) {
		return new StabBoard(x, y, index);
	}

}
