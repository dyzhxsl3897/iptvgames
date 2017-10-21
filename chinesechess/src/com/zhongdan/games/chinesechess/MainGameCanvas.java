package com.zhongdan.games.chinesechess;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.zhongdan.games.chinesechess.GameConstants.GameSettings;
import com.zhongdan.games.framework.utils.Constants;
import com.zhongdan.games.framework.utils.ImageUtil;
import com.zhongdan.games.framework.utils.NumberImgUtil;
import com.zhongdan.games.framework.utils.Constants.KeyCode;

public class MainGameCanvas extends GameCanvas {

	private MainMIDlet midlet;
	private Graphics graphics;
	private LayerManager layerManager = new LayerManager();
	private Image bgImg;
	private Image lostImg;
	private Image winImg;
	private Image numbersImg;
	private Image pieceAllImg;
	private Image cursorTargetImg;
	private Image cursorLastSrcImg;
	private Image cursorLastDesImg;
	private Sprite lostSprite;
	private Sprite winSprite;
	private Sprite cursorTargetSprite;
	private Sprite cursorLastSrcSprite;
	private Sprite cursorLastDesSprite;
	private Sprite[][] piecesSprite;
	private Vector stepNumberSprite;
	private char[][] pieces;
	private PosNode currentPos;
	private char currentPiece;
	private int step;
	private boolean isHumanPlaying;
	private boolean isPlaying;
	private boolean isPieceSelected;

	protected MainGameCanvas(MainMIDlet midlet) {
		super(false);
		this.midlet = midlet;
		graphics = this.getGraphics();
		this.setFullScreenMode(true);
		loadImages();
		initCanvas();
	}

	private void loadImages() {
		bgImg = ImageUtil.createImage("/bg.png");
		lostImg = ImageUtil.createImage("/lost.png");
		winImg = ImageUtil.createImage("/win.png");
		pieceAllImg = ImageUtil.createImage("/piece_all.png");
		cursorTargetImg = ImageUtil.createImage("/cur_target.png");
		cursorLastSrcImg = ImageUtil.createImage("/cur_last_src.png");
		cursorLastDesImg = ImageUtil.createImage("/cur_last_des.png");
		numbersImg = ImageUtil.createImage("/numbers.png");
	}

	public void initCanvas() {
		// Initialize layerManager
		for (int i = layerManager.getSize() - 1; i >= 0; i--) {
			layerManager.remove(layerManager.getLayerAt(i));
		}

		// Initialize background
		TiledLayer backgroundLayer = new TiledLayer(1, 1, bgImg, bgImg.getWidth(), bgImg.getHeight());
		backgroundLayer.setCell(0, 0, 1);
		layerManager.insert(backgroundLayer, 0);

		// Initialize lost and win
		winSprite = new Sprite(winImg, 338, 202);
		lostSprite = new Sprite(lostImg, 338, 202);
		winSprite.setPosition(118, 144);
		lostSprite.setPosition(118, 144);

		// Create new chess board with all pieces
		String startChessBoard = "RNBAKABNR/9/1C5C1/P1P1P1P1P/9/9/p1p1p1p1p/1c5c1/9/rnbakabnr";
		readChessBoard(startChessBoard);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				if (null != piecesSprite[i][j]) {
					layerManager.insert(piecesSprite[i][j], 0);
				}
			}
		}

		// Initialize cursors
		cursorTargetSprite = new Sprite(cursorTargetImg, 49, 49);
		cursorTargetSprite.setFrame(1);
		cursorLastSrcSprite = new Sprite(cursorLastSrcImg);
		cursorLastDesSprite = new Sprite(cursorLastDesImg);
		cursorTargetSprite.setPosition(GameSettings.CUR_START_X + (new PosNode("e0")).getCol() * GameSettings.CELL_WIDTH, GameSettings.CUR_START_Y
				+ (9 - (new PosNode("e0")).getRow()) * GameSettings.CELL_HEIGHT);
		cursorLastSrcSprite.setPosition(GameSettings.CUR_START_X + (new PosNode("e9")).getCol() * GameSettings.CELL_WIDTH, GameSettings.CUR_START_Y
				+ (9 - (new PosNode("e9")).getRow()) * GameSettings.CELL_HEIGHT);
		cursorLastDesSprite.setPosition(GameSettings.CUR_START_X + (new PosNode("e9")).getCol() * GameSettings.CELL_WIDTH, GameSettings.CUR_START_Y
				+ (9 - (new PosNode("e9")).getRow()) * GameSettings.CELL_HEIGHT);
		layerManager.insert(cursorLastSrcSprite, 0);
		layerManager.insert(cursorLastDesSprite, 0);
		layerManager.insert(cursorTargetSprite, 0);

		// Start game
		step = 0;
		updateStep();
		isHumanPlaying = true;
		isPlaying = true;
		isPieceSelected = false;
		currentPos = null;
		currentPiece = '\0';
		cursorLastSrcSprite.setVisible(false);
		cursorLastDesSprite.setVisible(false);

		// Paint all
		layerManager.setViewWindow(0, 0, this.getWidth(), this.getHeight());
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	protected void keyPressed(int keyCode) {
		if (isPlaying) {
			if (isHumanPlaying) {
				if (keyCode == Constants.KeyCode.LEFT) {
					if (cursorTargetSprite.getX() - GameSettings.CELL_WIDTH >= GameSettings.CUR_START_X) {
						cursorTargetSprite.move(-GameSettings.CELL_WIDTH, 0);
					}
				} else if (keyCode == Constants.KeyCode.RIGHT) {
					if (cursorTargetSprite.getX() + GameSettings.CELL_WIDTH <= GameSettings.CUR_START_X + 8 * GameSettings.CELL_WIDTH) {
						cursorTargetSprite.move(GameSettings.CELL_WIDTH, 0);
					}
				} else if (keyCode == Constants.KeyCode.UP) {
					if (cursorTargetSprite.getY() - GameSettings.CELL_HEIGHT >= GameSettings.CUR_START_Y) {
						cursorTargetSprite.move(0, -GameSettings.CELL_HEIGHT);
					}
				} else if (keyCode == Constants.KeyCode.DOWN) {
					if (cursorTargetSprite.getY() + GameSettings.CELL_HEIGHT <= GameSettings.CUR_START_Y + 9 * GameSettings.CELL_HEIGHT) {
						cursorTargetSprite.move(0, GameSettings.CELL_HEIGHT);
					}
				} else if (keyCode == Constants.KeyCode.OK) {
					if (isPieceSelected) {
						int row = 9 - (cursorTargetSprite.getY() - GameSettings.CUR_START_Y) / GameSettings.CELL_HEIGHT;
						int col = (cursorTargetSprite.getX() - GameSettings.CUR_START_X) / GameSettings.CELL_WIDTH;
						PosNode to = new PosNode(row, col);
						if (isObeyRule(currentPiece, currentPos, to)) {
							// if destination is black, then kill it
							if (isBlackPiece(to)) {
								piecesSprite[row][col].setVisible(false);
							}
							// update the board
							step++;
							updateStep();
							pieces[currentPos.getRow()][currentPos.getCol()] = '\0';
							pieces[row][col] = currentPiece;
							piecesSprite[row][col] = piecesSprite[currentPos.getRow()][currentPos.getCol()];
							PosNode spritePos = new PosNode(row, col);
							piecesSprite[row][col].setPosition(spritePos.getPieceX(), spritePos.getPieceY());
							piecesSprite[currentPos.getRow()][currentPos.getCol()] = null;
							layerManager.paint(graphics, 0, 0);
							this.flushGraphics();
							// AI's turn
							isHumanPlaying = false;
							if (isHumanWin(pieces)) {// If human wins
								layerManager.insert(winSprite, 0);
								isPlaying = false;
							} else {
								JSONObject json = null;
								StringBuffer currentChessBoard = new StringBuffer();
								currentChessBoard.append(createCurrentChessBoard(pieces));
								json = new JSONObject();
								String moveStep = null;
								try {
									json.put("chessboard", currentChessBoard.toString());
									JSONObject resultJson = AiService.calNextStep("http://localhost:8180/lobby/rest/ai/chinesechess/nextstep", json);
									moveStep = resultJson.getString("moveStep");
								} catch (JSONException e) {
									e.printStackTrace();
								}
								// update the board
								cursorLastSrcSprite.setVisible(true);
								cursorLastDesSprite.setVisible(true);
								String srcMove = moveStep.substring(0, 2);
								String desMove = moveStep.substring(2);
								PosNode srcNode = new PosNode(srcMove);
								PosNode desNode = new PosNode(desMove);
								pieces[desNode.getRow()][desNode.getCol()] = pieces[srcNode.getRow()][srcNode.getCol()];
								pieces[srcNode.getRow()][srcNode.getCol()] = '\0';
								if (piecesSprite[desNode.getRow()][desNode.getCol()] != null) {
									piecesSprite[desNode.getRow()][desNode.getCol()].setVisible(false);
								}
								piecesSprite[desNode.getRow()][desNode.getCol()] = piecesSprite[srcNode.getRow()][srcNode.getCol()];
								piecesSprite[srcNode.getRow()][srcNode.getCol()] = null;
								spritePos.setRow(desNode.getRow());
								spritePos.setCol(desNode.getCol());
								piecesSprite[desNode.getRow()][desNode.getCol()].setPosition(spritePos.getPieceX(), spritePos.getPieceY());
								cursorLastSrcSprite.setPosition(srcNode.getCursorX(), srcNode.getCursorY());
								cursorLastDesSprite.setPosition(desNode.getCursorX(), desNode.getCursorY());
							}
							// AI finishes
							isHumanPlaying = true;
							if (isAiWin(pieces)) {// If AI wins
								layerManager.insert(lostSprite, 0);
								isPlaying = false;
							}
						}
						currentPos = null;
						currentPiece = '\0';
						cursorTargetSprite.nextFrame();
						isPieceSelected = false;
					} else {
						int row = 9 - (cursorTargetSprite.getY() - GameSettings.CUR_START_Y) / GameSettings.CELL_HEIGHT;
						int col = (cursorTargetSprite.getX() - GameSettings.CUR_START_X) / GameSettings.CELL_WIDTH;
						if (pieces[row][col] >= 'A' && pieces[row][col] <= 'Z') {
							isPieceSelected = true;
							cursorTargetSprite.nextFrame();
							currentPos = new PosNode(row, col);
							currentPiece = pieces[row][col];
						}
					}
				} else if (keyCode == Constants.KeyCode.NUM_0) {
					initCanvas();
				} else if (keyCode == Constants.KeyCode.BACK || keyCode == KeyCode.BACK_1 || keyCode == KeyCode.BACK_2) {
					this.midlet.getDisplay().setCurrent(this.midlet.getMenuCanvas());
				}
				layerManager.paint(graphics, 0, 0);
				this.flushGraphics();
			}
		} else {
			if (keyCode == Constants.KeyCode.OK) {
				this.midlet.getDisplay().setCurrent(this.midlet.getMenuCanvas());
			}
		}
	}

	private boolean isObeyRule(char selectedPiece, PosNode from, PosNode to) {
		boolean isObeyRule = false;
		int rowIncrease = 0;
		int colIncrease = 0;
		PosNode checkPos = null;
		switch (selectedPiece) {
		case 'K':// 将走法
			if (Math.abs(from.getRow() - to.getRow()) + Math.abs(from.getCol() - to.getCol()) == 1 && to.getRow() < 3 && to.getCol() > 2
					&& to.getCol() < 6 && !isRedPiece(to)) {
				isObeyRule = true;
			}
			break;
		case 'A':// 士走法
			if (Math.abs(from.getRow() - to.getRow()) == 1 && Math.abs(from.getCol() - to.getCol()) == 1 && to.getRow() < 3 && to.getCol() > 2
					&& to.getCol() < 6 && !isRedPiece(to)) {
				isObeyRule = true;
			}
			break;
		case 'B':// 象走法
			isObeyRule = true;
			if (isRedPiece(to)) {
				isObeyRule = false;
				break;
			}
			if (Math.abs(from.getRow() - to.getRow()) != 2 || Math.abs(from.getCol() - to.getCol()) != 2) {
				isObeyRule = false;
				break;
			}
			if (to.getRow() > from.getRow()
					&& to.getCol() > from.getCol()
					&& (isRedPiece(new PosNode(from.getRow() + 1, from.getCol() + 1)) || isBlackPiece(new PosNode(from.getRow() + 1,
							from.getCol() + 1)))) {
				isObeyRule = false;
				break;
			}
			if (to.getRow() > from.getRow()
					&& to.getCol() < from.getCol()
					&& (isRedPiece(new PosNode(from.getRow() + 1, from.getCol() - 1)) || isBlackPiece(new PosNode(from.getRow() + 1,
							from.getCol() - 1)))) {
				isObeyRule = false;
				break;
			}
			if (to.getRow() < from.getRow()
					&& to.getCol() > from.getCol()
					&& (isRedPiece(new PosNode(from.getRow() - 1, from.getCol() + 1)) || isBlackPiece(new PosNode(from.getRow() - 1,
							from.getCol() + 1)))) {
				isObeyRule = false;
				break;
			}
			if (to.getRow() < from.getRow()
					&& to.getCol() < from.getCol()
					&& (isRedPiece(new PosNode(from.getRow() - 1, from.getCol() - 1)) || isBlackPiece(new PosNode(from.getRow() - 1,
							from.getCol() - 1)))) {
				isObeyRule = false;
				break;
			}
			break;
		case 'N':// 马走法
			isObeyRule = true;
			if (isRedPiece(to)) {
				isObeyRule = false;
				break;
			}
			if (!(Math.abs(from.getRow() - to.getRow()) == 2 && Math.abs(from.getCol() - to.getCol()) == 1 || Math.abs(from.getRow() - to.getRow()) == 1
					&& Math.abs(from.getCol() - to.getCol()) == 2)) {
				isObeyRule = false;
				break;
			}
			if ((to.getRow() - from.getRow() == 2 && to.getCol() - from.getCol() == 1)
					&& (isRedPiece(new PosNode(from.getRow() + 1, from.getCol())) || isBlackPiece(new PosNode(from.getRow() + 1, from.getCol())))) {
				isObeyRule = false;
				break;
			}
			if ((to.getRow() - from.getRow() == 1 && to.getCol() - from.getCol() == 2)
					&& (isRedPiece(new PosNode(from.getRow(), from.getCol() + 1)) || isBlackPiece(new PosNode(from.getRow(), from.getCol() + 1)))) {
				isObeyRule = false;
				break;
			}
			if ((to.getRow() - from.getRow() == -1 && to.getCol() - from.getCol() == 2)
					&& (isRedPiece(new PosNode(from.getRow(), from.getCol() + 1)) || isBlackPiece(new PosNode(from.getRow(), from.getCol() + 1)))) {
				isObeyRule = false;
				break;
			}
			if ((to.getRow() - from.getRow() == -2 && to.getCol() - from.getCol() == 1)
					&& (isRedPiece(new PosNode(from.getRow() - 1, from.getCol())) || isBlackPiece(new PosNode(from.getRow() - 1, from.getCol())))) {
				isObeyRule = false;
				break;
			}
			if ((to.getRow() - from.getRow() == -2 && to.getCol() - from.getCol() == -1)
					&& (isRedPiece(new PosNode(from.getRow() - 1, from.getCol())) || isBlackPiece(new PosNode(from.getRow() - 1, from.getCol())))) {
				isObeyRule = false;
				break;
			}
			if ((to.getRow() - from.getRow() == -1 && to.getCol() - from.getCol() == -2)
					&& (isRedPiece(new PosNode(from.getRow(), from.getCol() - 1)) || isBlackPiece(new PosNode(from.getRow(), from.getCol() - 1)))) {
				isObeyRule = false;
				break;
			}
			if ((to.getRow() - from.getRow() == 1 && to.getCol() - from.getCol() == -2)
					&& (isRedPiece(new PosNode(from.getRow(), from.getCol() - 1)) || isBlackPiece(new PosNode(from.getRow(), from.getCol() - 1)))) {
				isObeyRule = false;
				break;
			}
			if ((to.getRow() - from.getRow() == 2 && to.getCol() - from.getCol() == -1)
					&& (isRedPiece(new PosNode(from.getRow() + 1, from.getCol())) || isBlackPiece(new PosNode(from.getRow() + 1, from.getCol())))) {
				isObeyRule = false;
				break;
			}
			break;
		case 'R':// 车走法
			isObeyRule = true;
			if (isRedPiece(to)) {
				isObeyRule = false;
				break;
			}
			// Check if it goes directly, and no turns
			if (!((to.getRow() == from.getRow() && to.getCol() != from.getCol()) || (to.getRow() != from.getRow() && to.getCol() == from.getCol()))) {
				isObeyRule = false;
				break;
			}
			// Loop through the direct way
			rowIncrease = 0;
			colIncrease = 0;
			if (to.getRow() == from.getRow()) {
				if (to.getCol() > from.getCol()) {
					colIncrease = 1;
				} else {
					colIncrease = -1;
				}
			} else {
				if (to.getRow() > from.getRow()) {
					rowIncrease = 1;
				} else {
					rowIncrease = -1;
				}
			}
			checkPos = new PosNode(from);
			checkPos.setRow(checkPos.getRow() + rowIncrease);
			checkPos.setCol(checkPos.getCol() + colIncrease);
			while (!checkPos.equals(to)) {
				if (isRedPiece(checkPos) || isBlackPiece(checkPos)) {
					isObeyRule = false;
					break;
				}
				checkPos.setRow(checkPos.getRow() + rowIncrease);
				checkPos.setCol(checkPos.getCol() + colIncrease);
			}
			break;
		case 'C':// 炮走法
			isObeyRule = true;
			if (isRedPiece(to)) {
				isObeyRule = false;
				break;
			}
			// Check if it goes directly, and no turns
			if (!((to.getRow() == from.getRow() && to.getCol() != from.getCol()) || (to.getRow() != from.getRow() && to.getCol() == from.getCol()))) {
				isObeyRule = false;
				break;
			}
			// Loop through the direct way
			rowIncrease = 0;
			colIncrease = 0;
			if (to.getRow() == from.getRow()) {
				if (to.getCol() > from.getCol()) {
					colIncrease = 1;
				} else {
					colIncrease = -1;
				}
			} else {
				if (to.getRow() > from.getRow()) {
					rowIncrease = 1;
				} else {
					rowIncrease = -1;
				}
			}
			checkPos = new PosNode(from);
			checkPos.setRow(checkPos.getRow() + rowIncrease);
			checkPos.setCol(checkPos.getCol() + colIncrease);
			int numOfHill = 0;// 炮隔了几座山
			while (!checkPos.equals(to)) {
				if (isRedPiece(checkPos) || isBlackPiece(checkPos)) {
					numOfHill++;
				}
				if (numOfHill > 1) {
					isObeyRule = false;
					break;
				}
				checkPos.setRow(checkPos.getRow() + rowIncrease);
				checkPos.setCol(checkPos.getCol() + colIncrease);
			}
			if (isBlackPiece(to) && numOfHill == 0) {
				isObeyRule = false;
				break;
			}
			if (!isBlackPiece(to) && numOfHill > 0) {
				isObeyRule = false;
				break;
			}
			break;
		case 'P':// 卒走法
			isObeyRule = true;
			if (isRedPiece(to)) {
				isObeyRule = false;
				break;
			}
			if (to.getRow() < from.getRow()) {
				isObeyRule = false;
				break;
			}
			if ((to.getRow() - from.getRow() + Math.abs(to.getCol() - from.getCol())) > 1) {
				isObeyRule = false;
				break;
			}
			if (from.getRow() < 5 && to.getRow() == from.getRow()) {// 没有过河就横着走
				isObeyRule = false;
				break;
			}
			break;
		}
		return isObeyRule;
	}

	private boolean isRedPiece(PosNode pos) {
		if (pieces[pos.getRow()][pos.getCol()] >= 'A' && pieces[pos.getRow()][pos.getCol()] <= 'Z') {
			return true;
		} else {
			return false;
		}
	}

	private boolean isBlackPiece(PosNode pos) {
		if (pieces[pos.getRow()][pos.getCol()] >= 'a' && pieces[pos.getRow()][pos.getCol()] <= 'z') {
			return true;
		} else {
			return false;
		}
	}

	public void readChessBoard(String chessBoard) {
		piecesSprite = new Sprite[10][9];
		pieces = new char[10][9];

		char[] chessBoardCharArray = chessBoard.toCharArray();
		int i = 0, j = 0, index;
		for (index = 0; index < chessBoardCharArray.length; index++) {
			char chessBoardChar = chessBoardCharArray[index];
			Sprite s = null;
			if ('0' < chessBoardChar && chessBoardChar < '9') {
				j += chessBoardChar - '0';
			} else {
				switch (chessBoardChar) {
				case 'K':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(0);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'K';
					j++;
					break;
				case 'k':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(1);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'k';
					j++;
					break;
				case 'R':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(2);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'R';
					j++;
					break;
				case 'r':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(3);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'r';
					j++;
					break;
				case 'N':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(4);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'N';
					j++;
					break;
				case 'n':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(5);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'n';
					j++;
					break;
				case 'B':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(6);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'B';
					j++;
					break;
				case 'b':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(7);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'b';
					j++;
					break;
				case 'A':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(8);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'A';
					j++;
					break;
				case 'a':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(9);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'a';
					j++;
					break;
				case 'C':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(10);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'C';
					j++;
					break;
				case 'c':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(11);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'c';
					j++;
					break;
				case 'P':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(12);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'P';
					j++;
					break;
				case 'p':
					s = new Sprite(pieceAllImg, 44, 44);
					s.setFrame(13);
					s.setPosition(GameSettings.PIECE_START_X + GameSettings.CELL_WIDTH * j, GameSettings.PIECE_START_Y + GameSettings.CELL_HEIGHT
							* (9 - i));
					piecesSprite[i][j] = s;
					pieces[i][j] = 'p';
					j++;
					break;
				case '/':
					i++;
					j = 0;
					break;
				}
			}
		}
	}

	private boolean isHumanWin(char[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 'k') {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isAiWin(char[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 'K') {
					return false;
				}
			}
		}
		return true;
	}

	private String createCurrentChessBoard(char[][] board) {
		StringBuffer sb = new StringBuffer();
		for (int i = board.length - 1; i >= 0; i--) {
			int num = 0;
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] >= 'a' && board[i][j] <= 'z' || board[i][j] >= 'A' && board[i][j] <= 'Z') {
					if (num > 0) {
						sb.append(num);
					}
					sb.append(board[i][j]);
					num = 0;
				} else {
					num++;
				}
			}
			if (num == 9) {
				sb.append(9);
			}
			if (i > 0) {
				sb.append("/");
			}
		}
		sb.append(" b - - 0 " + step);
		return sb.toString();
	}

	private void updateStep() {
		if (null != stepNumberSprite && 0 < stepNumberSprite.size()) {
			for (int i = 0; i < stepNumberSprite.size(); i++) {
				layerManager.remove((Sprite) stepNumberSprite.elementAt(i));
			}
		}
		stepNumberSprite = NumberImgUtil.updateNumber(step, numbersImg, GameConstants.GameSettings.STEP_NUMBER_X,
				GameConstants.GameSettings.STEP_NUMBER_Y, Graphics.TOP | Graphics.HCENTER);
		if (null != stepNumberSprite && 0 < stepNumberSprite.size()) {
			for (int i = 0; i < stepNumberSprite.size(); i++) {
				layerManager.insert((Sprite) stepNumberSprite.elementAt(i), 0);
			}
		}
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

}
