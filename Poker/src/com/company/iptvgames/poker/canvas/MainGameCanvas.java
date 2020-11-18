package com.company.iptvgames.poker.canvas;

import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.company.iptvgames.framework.utils.Constants;
import com.company.iptvgames.framework.utils.ImageUtil;
import com.company.iptvgames.poker.GameConst;
import com.company.iptvgames.poker.MainMIDlet;
import com.company.iptvgames.poker.player.Player;
import com.company.iptvgames.poker.pokercards.AllCombinations;
import com.company.iptvgames.poker.pokercards.Card;
import com.company.iptvgames.poker.pokercards.CardsType;
import com.company.iptvgames.poker.pokercards.PokerCard;
import com.company.iptvgames.poker.resources.ImageRes;

public class MainGameCanvas extends GameCanvas implements Runnable {

	private Graphics graphics;
	private MainMIDlet midlet;
	private LayerManager layerManager;

	private Sprite alertPauseSprite;
	private Sprite alertPauseSelectImgSprite;
	private Sprite continueSprite;
	private Sprite overSprite;
	private Sprite DZWinSprite;
	private Sprite NMWinSprite;
	private Sprite startSprite;
	private Sprite selectedSprite;
	private Sprite arrowIconSprite;
	private Sprite texiaoSprite;// 新出牌的牌型文字特效
	private Sprite txSprite;// 新出牌的牌型动画特效
	private Sprite[] txOfBompSpriteSets = new Sprite[4];// 炸弹动画特效

	private int currentPlayer = 0;// 当前令牌人ID,0玩家，1、2：npc
	private int currentCardsID = 0;// 当前牌面所属的玩家,0玩家，1、2：npc
	private int[] currentCards = null;// 最新牌面出牌
	private int[] readyCards = null;// 准备出牌
	private int basicScore = 0;// 当局底分
	private int highScorePlayer = 0;// 当前叫分最高者
	private int option = 0;// 选中的出牌按钮的index ID
	private int ScoreOption = 3;// 选中的叫分按钮的index ID
	private int[] jiaofenByPlayers = new int[] { -1, -1, -1 };// 依次为玩家，npc叫分值
	private boolean finishChupai = false;// 角色完成出牌

	private int[] newCardsAll = new int[GameConst.Game.PORKERACCOUTS];// 总牌数
	private int[][] pl = new int[GameConst.Game.PLAYERACCOUNTS][GameConst.Game.PORKEREACHPERSON];
	private int[] dizhuPl = new int[GameConst.Game.PORKEREACHPERSON + GameConst.Game.BOTTOMACCOUNTS];

	private int[] bottomCards = new int[GameConst.Game.BOTTOMACCOUNTS];
	private Card[] allBottomCards = new Card[GameConst.Game.BOTTOMACCOUNTS * 2];
	private int[][] bottomPos = new int[][] { { GameConst.Position.BOTTOOM_X1, GameConst.Position.BOTTOOM_Y },
			{ GameConst.Position.BOTTOOM_X2, GameConst.Position.BOTTOOM_Y }, { GameConst.Position.BOTTOOM_X3, GameConst.Position.BOTTOOM_Y },
			{ GameConst.Position.BOTTOOM_C_X1, GameConst.Position.BOTTOOM_C_Y }, { GameConst.Position.BOTTOOM_C_X2, GameConst.Position.BOTTOOM_C_Y },
			{ GameConst.Position.BOTTOOM_C_X3, GameConst.Position.BOTTOOM_C_Y } };

	private Player[] players = new Player[GameConst.Game.PLAYERACCOUNTS];// 依次为玩家，下家，上家的牌集合
	private int[][] playersPos = new int[][] { { GameConst.Position.PLAYER_X0, GameConst.Position.PLAYER_Y0 },
			{ GameConst.Position.PLAYER_X1, GameConst.Position.PLAYER_Y1 }, { GameConst.Position.PLAYER_X2, GameConst.Position.PLAYER_Y2 } };

	private Sprite[] scoreOptions = new Sprite[4];// 依次为不叫，1分，2分，3分
	private int[][] scoreOptionsPos = new int[][] { { GameConst.Position.SCORE1_X, GameConst.Position.SCORE_Y },
			{ GameConst.Position.SCORE2_X, GameConst.Position.SCORE_Y }, { GameConst.Position.SCORE3_X, GameConst.Position.SCORE_Y },
			{ GameConst.Position.SCORE4_X, GameConst.Position.SCORE_Y } };

	private Sprite[] options = new Sprite[4];
	private int[][] optionsPos = new int[][] { { GameConst.Position.BUCHU_X, GameConst.Position.BUTTON_Y },
			{ GameConst.Position.RESELECT_X, GameConst.Position.BUTTON_Y }, { GameConst.Position.TIP_X, GameConst.Position.BUTTON_Y },
			{ GameConst.Position.CHUPAI_X, GameConst.Position.BUTTON_Y } };

	private Sprite[] callOptions = new Sprite[3];
	private int[][] callPos = new int[][] { { GameConst.Position.CALL_X0, GameConst.Position.CALL_Y0 },
			{ GameConst.Position.CALL_X1, GameConst.Position.CALL_Y1 }, { GameConst.Position.CALL_X2, GameConst.Position.CALL_Y2 } };

	private TiledLayer bg1Layer;
	private TiledLayer bg2Layer;
	private TiledLayer[] playersLayer = new TiledLayer[GameConst.Game.PLAYERACCOUNTS];
	private TiledLayer dizhuLayer;

	private static Random random = new Random();
	private int gameStatus = 0;// 0：进入游戏界面；1：叫分；2：新开局；3：新局结束；4：暂停
	private int lastGameStatus = 0;

	private Thread gameCanvasThread;

	public MainGameCanvas(MainMIDlet mainMIDlet) {
		super(false);
		this.setFullScreenMode(true);
		this.graphics = getGraphics();
		this.midlet = mainMIDlet;

		loadImages();

		// playState = new GCPlayState(this);
		// pauseState = new GCPauseState(this);
		// deadState = new GCDeadState(this);

		// updateState(playState);;
	}

	private void loadImages() {
		ImageRes.getInstance().loadImage("bgImg0", ImageUtil.createImage("/BG/backgroud.png"));
		ImageRes.getInstance().loadImage("topbj", ImageUtil.createImage("/BG/topbj.png"));

		ImageRes.getInstance().loadImage("dizhuImg", ImageUtil.createImage("/player/dz.png"));
		ImageRes.getInstance().loadImage("npc1Img", ImageUtil.createImage("/player/nm1.png"));
		ImageRes.getInstance().loadImage("npc2Img", ImageUtil.createImage("/player/nm2.png"));
		ImageRes.getInstance().loadImage("npc3Img", ImageUtil.createImage("/player/nm3.png"));

		ImageRes.getInstance().loadImage("alertPauseImg", ImageUtil.createImage("/BG/pause.png"));
		ImageRes.getInstance().loadImage("alertPauseSelectImg", ImageUtil.createImage("/BG/dj.png"));
		ImageRes.getInstance().loadImage("continueImg", ImageUtil.createImage("/BG/continue.png"));
		ImageRes.getInstance().loadImage("overImg", ImageUtil.createImage("/BG/over.png"));

		ImageRes.getInstance().loadImage("DZWinImg", ImageUtil.createImage("/texiao/dzs.png"));
		ImageRes.getInstance().loadImage("NMWinImg", ImageUtil.createImage("/texiao/nms.png"));

		ImageRes.getInstance().loadImage("score1Img", ImageUtil.createImage("/other/1f.png"));
		ImageRes.getInstance().loadImage("score2Img", ImageUtil.createImage("/other/2f.png"));
		ImageRes.getInstance().loadImage("score3Img", ImageUtil.createImage("/other/3f.png"));
		ImageRes.getInstance().loadImage("score0Img", ImageUtil.createImage("/other/bj.png"));

		ImageRes.getInstance().loadImage("option0Img", ImageUtil.createImage("/other/bc.png"));// 不出牌
		ImageRes.getInstance().loadImage("option1Img", ImageUtil.createImage("/other/cx.png"));// 重选
		ImageRes.getInstance().loadImage("option2Img", ImageUtil.createImage("/other/ts.png"));// 提示
		ImageRes.getInstance().loadImage("option3Img", ImageUtil.createImage("/other/cp.png"));// 出牌
		ImageRes.getInstance().loadImage("arrowIconImg", ImageUtil.createImage("/other/arrow.png"));// 牌选中标记

		ImageRes.getInstance().loadImage("startImg", ImageUtil.createImage("/other/ks.png"));
		ImageRes.getInstance().loadImage("selectedImg", ImageUtil.createImage("/other/xz.png"));

		ImageRes.getInstance().loadImage("callBuJiaoImg", ImageUtil.createImage("/BG/bj-1.png"));
		ImageRes.getInstance().loadImage("callBuChuImg", ImageUtil.createImage("/BG/bc-1.png"));
		ImageRes.getInstance().loadImage("callScore1Img", ImageUtil.createImage("/BG/1f1.png"));
		ImageRes.getInstance().loadImage("callScore2Img", ImageUtil.createImage("/BG/2f1.png"));
		ImageRes.getInstance().loadImage("callScore3Img", ImageUtil.createImage("/BG/3f1.png"));

		ImageRes.getInstance().loadImage("texiaoImg101", ImageUtil.createImage("/texiao/no.png"));
		ImageRes.getInstance().loadImage("texiaoImg100", ImageUtil.createImage("/texiao/gz.png"));
		ImageRes.getInstance().loadImage("texiaoImg2", ImageUtil.createImage("/texiao/ld.png"));// 连对
		ImageRes.getInstance().loadImage("texiaoImg5", ImageUtil.createImage("/texiao/sz.png"));// 顺子
		ImageRes.getInstance().loadImage("texiaoImg6", ImageUtil.createImage("/texiao/sz.png"));// 顺子
		ImageRes.getInstance().loadImage("texiaoImg7", ImageUtil.createImage("/texiao/sz.png"));// 顺子
		ImageRes.getInstance().loadImage("texiaoImg8", ImageUtil.createImage("/texiao/fj.png"));// 飞机
		ImageRes.getInstance().loadImage("texiaoImg10", ImageUtil.createImage("/texiao/zd.png"));// 炸弹
		ImageRes.getInstance().loadImage("texiaoImg11", ImageUtil.createImage("/texiao/hj.png"));// 火箭
		ImageRes.getInstance().loadImage("txAniImg8", ImageUtil.createImage("/texiao/fj-1.png"));
		ImageRes.getInstance().loadImage("txAniImg10", ImageUtil.createImage("/texiao/zd1.png"));
		ImageRes.getInstance().loadImage("txAniImg11", ImageUtil.createImage("/texiao/hj-1.png"));
	}

	public void initalizeGame() {
		layerManager = new LayerManager();
		Image plImg = ImageRes.getInstance().getImage("npc3Img");
		Image npc1Img = ImageRes.getInstance().getImage("npc1Img");
		Image npc2Img = ImageRes.getInstance().getImage("npc2Img");

		// 创建背景
		Image bg1Img = ImageRes.getInstance().getImage("bgImg0");
		Image bg2Img = ImageRes.getInstance().getImage("topbj");
		bg1Layer = new TiledLayer(1, 1, bg1Img, bg1Img.getWidth(), bg1Img.getHeight());
		bg2Layer = new TiledLayer(1, 1, bg2Img, bg2Img.getWidth(), bg2Img.getHeight());
		bg1Layer.setCell(0, 0, 1);
		bg2Layer.setCell(0, 0, 1);
		bg2Layer.setPosition(GameConst.Position.BOTTOOM_BG_X, GameConst.Position.BOTTOOM_BG_Y);
		layerManager.append(bg2Layer);
		layerManager.append(bg1Layer);

		// 创建提示信息
		alertPauseSprite = new Sprite(ImageRes.getInstance().getImage("alertPauseImg"));
		alertPauseSelectImgSprite = new Sprite(ImageRes.getInstance().getImage("alertPauseSelectImg"));
		continueSprite = new Sprite(ImageRes.getInstance().getImage("continueImg"));
		overSprite = new Sprite(ImageRes.getInstance().getImage("overImg"));
		DZWinSprite = new Sprite(ImageRes.getInstance().getImage("DZWinImg"));
		NMWinSprite = new Sprite(ImageRes.getInstance().getImage("NMWinImg"));

		texiaoSprite = new Sprite(ImageRes.getInstance().getImage("texiaoImg100"));// 牌型名称特效
		txSprite = new Sprite(ImageRes.getInstance().getImage("txAniImg8"));// 牌型特效动画
		texiaoSprite.setVisible(false);
		txSprite.setVisible(false);
		layerManager.insert(texiaoSprite, GameConst.GameCanvas.LAYER_alert);
		layerManager.insert(txSprite, GameConst.GameCanvas.LAYER_alert);

		layerManager.insert(alertPauseSprite, GameConst.GameCanvas.LAYER_alert);
		layerManager.insert(alertPauseSelectImgSprite, GameConst.GameCanvas.LAYER_alert_select);
		layerManager.insert(continueSprite, GameConst.GameCanvas.LAYER_alert);
		layerManager.insert(overSprite, GameConst.GameCanvas.LAYER_alert);
		layerManager.insert(DZWinSprite, GameConst.GameCanvas.LAYER_alert);
		layerManager.insert(NMWinSprite, GameConst.GameCanvas.LAYER_alert);

		Image bompImg = ImageRes.getInstance().getImage("txAniImg10");
		for (int i = 0; i < txOfBompSpriteSets.length; i++) {
			txOfBompSpriteSets[i] = new Sprite(bompImg, bompImg.getWidth() / GameConst.GameCanvas.FRAMEOFTEXIAO, bompImg.getHeight());
			txOfBompSpriteSets[i].setVisible(false);
			layerManager.insert(txOfBompSpriteSets[i], GameConst.GameCanvas.LAYER_alert);
		}
		txOfBompSpriteSets[1].setTransform(Sprite.TRANS_ROT270);// 左上角
		txOfBompSpriteSets[2].setTransform(Sprite.TRANS_ROT180);// 左下角
		txOfBompSpriteSets[3].setTransform(Sprite.TRANS_ROT90);// 右下角

		txOfBompSpriteSets[0].setPosition(GameConst.Position.ZDA_X, GameConst.Position.ZDA_Y);
		txOfBompSpriteSets[1].setPosition(GameConst.Position.ZDA_X - txOfBompSpriteSets[1].getWidth() / 2, GameConst.Position.ZDA_Y
				- txOfBompSpriteSets[1].getHeight() / 2);
		txOfBompSpriteSets[2].setPosition(GameConst.Position.ZDA_X - txOfBompSpriteSets[2].getWidth() / 2, GameConst.Position.ZDA_Y
				+ txOfBompSpriteSets[2].getHeight() / 2);
		txOfBompSpriteSets[3].setPosition(GameConst.Position.ZDA_X + txOfBompSpriteSets[3].getWidth() / 2, GameConst.Position.ZDA_Y
				+ txOfBompSpriteSets[3].getHeight() / 2);

		for (int i = 0; i < callOptions.length; i++) {
			callOptions[i] = new Sprite(ImageRes.getInstance().getImage("callScore2Img"));
			callOptions[i].setPosition(callPos[i][0], callPos[i][1]);
			layerManager.insert(callOptions[i], GameConst.GameCanvas.LAYER_button);
			callOptions[i].setVisible(false);
		}

		for (int i = 0; i < scoreOptions.length; i++) {
			scoreOptions[i] = new Sprite(ImageRes.getInstance().getImage("score" + i + "Img"));
			scoreOptions[i].setPosition(scoreOptionsPos[i][0], scoreOptionsPos[i][1]);
			layerManager.insert(scoreOptions[i], GameConst.GameCanvas.LAYER_button);
			scoreOptions[i].setVisible(false);
		}

		for (int i = 0; i < options.length; i++) {
			options[i] = new Sprite(ImageRes.getInstance().getImage("option" + i + "Img"));
			options[i].setPosition(optionsPos[i][0], optionsPos[i][1]);
			layerManager.insert(options[i], GameConst.GameCanvas.LAYER_button);
			options[i].setVisible(false);
		}

		startSprite = new Sprite(ImageRes.getInstance().getImage("startImg"));
		selectedSprite = new Sprite(ImageRes.getInstance().getImage("selectedImg"));
		arrowIconSprite = new Sprite(ImageRes.getInstance().getImage("arrowIconImg"), 18, 30);

		DZWinSprite.setPosition(GameConst.Position.WIN_X, GameConst.Position.WIN_Y);
		NMWinSprite.setPosition(GameConst.Position.WIN_X, GameConst.Position.WIN_Y);
		startSprite.setPosition(GameConst.Position.START_X, GameConst.Position.START_Y);
		arrowIconSprite.setPosition(GameConst.Position.ARROWICON_X, GameConst.Position.ARROWICON_Y);

		layerManager.insert(startSprite, GameConst.GameCanvas.LAYER_button);
		layerManager.insert(selectedSprite, GameConst.GameCanvas.LAYER_button);
		layerManager.insert(arrowIconSprite, GameConst.GameCanvas.LAYER_button);

		alertPauseSprite.setVisible(false);
		alertPauseSelectImgSprite.setVisible(false);
		continueSprite.setVisible(false);
		overSprite.setVisible(false);
		DZWinSprite.setVisible(false);
		NMWinSprite.setVisible(false);
		startSprite.setVisible(true);
		selectedSprite.setVisible(true);
		arrowIconSprite.setVisible(false);

		// 生成玩家
		playersLayer[0] = new TiledLayer(1, 1, plImg, plImg.getWidth(), plImg.getHeight());
		playersLayer[1] = new TiledLayer(1, 1, npc1Img, npc1Img.getWidth(), npc1Img.getHeight());
		playersLayer[2] = new TiledLayer(1, 1, npc2Img, npc2Img.getWidth(), npc2Img.getHeight());

		for (int i = 0; i < playersLayer.length; i++) {
			players[i] = new Player(i, layerManager);// 依次生成玩家，玩家的下家，玩家的上家
			playersLayer[i].setCell(0, 0, 1);
			playersLayer[i].setPosition(playersPos[i][0], playersPos[i][1]);
			layerManager.insert(playersLayer[i], GameConst.GameCanvas.LAYER_player);
		}

		// 创建地主图标
		Image dizhuImg = ImageRes.getInstance().getImage("dizhuImg");
		dizhuLayer = new TiledLayer(1, 1, dizhuImg, dizhuImg.getWidth(), dizhuImg.getHeight());
		dizhuLayer.setCell(0, 0, 1);

		// 生成牌
		for (int i = 0; i < newCardsAll.length; i++) {
			newCardsAll[i] = i + 1;
		}

		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();

		selectButton(startSprite, 0);
		gameStatus = 0;
		lastGameStatus = 0;
	}

	public void run() {
		while (gameStatus >= 0) {
			long startTime = System.currentTimeMillis();

			if (gameStatus == 0) {
			} else if (gameStatus == 1) {
				showJiaofenUI();
				if (currentPlayer == 0) {
					/*-
					if(jiaofenByPlayers[currentPlayer] == -1){
						showJiaofenUI();
						selectButton(scoreOptions[ScoreOption], ScoreOption);
					}
					 */
				} else {
					qiangDiZhu();
				}
			} else if (gameStatus == 2) {
				if (currentPlayer == 0) {
					showOptionUI(true);
				} else {
					showOptionUI(false);

					if (players[currentPlayer].selectByAI(currentCards) != null) {
						chupai();
					} else {
						showBuyao();
					}
				}
			}

			layerManager.paint(graphics, 0, 0);
			this.flushGraphics();

			long runTime = System.currentTimeMillis() - startTime;
			if (runTime < GameConst.FPS) {
				try {
					Thread.sleep(GameConst.FPS - runTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void startNewGame() {
		clearUI();

		// 触发洗牌、发牌
		resetNewCards();
		sendCard();

		// 排序
		PokerCard.sort(pl[0]);
		PokerCard.sort(pl[1]);
		PokerCard.sort(pl[2]);

		// 绘制玩家手牌并不亮底牌
		players[0].updateCards(pl[0]);
		players[1].updateCards(pl[1]);
		players[2].updateCards(pl[2]);
		paintAllCards(false);
	}

	// 洗牌
	public void resetNewCards() {
		for (int i = 0; i < newCardsAll.length; i++) {
			int ran = random.nextInt(GameConst.Game.PORKERACCOUTS);
			int temp = newCardsAll[i];
			newCardsAll[i] = newCardsAll[ran];
			newCardsAll[ran] = temp;
		}
	}

	// 发牌
	public void sendCard() {
		int startNo = 0;
		// 分牌
		for (int i = 0; i < GameConst.Game.PORKEREACHPERSON; i++) {
			pl[0][i] = newCardsAll[startNo];
			pl[1][i] = newCardsAll[startNo + 1];
			pl[2][i] = newCardsAll[startNo + 2];
			startNo = startNo + 3;
		}
		// //test
		// pl[0] = new int[]{13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,40};
		// pl[1] = new int[]{29,30,31,32,33,34,35,36,37,38,39,41,42,43,45,46,47};
		// pl[2] = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,50,51,52,48,49};
		// //finish test

		for (int i = 0; i < GameConst.Game.BOTTOMACCOUNTS; i++) {
			bottomCards[i] = newCardsAll[startNo];
			allBottomCards[i] = new Card(bottomCards[i], true);
			allBottomCards[i + GameConst.Game.BOTTOMACCOUNTS] = new Card(bottomCards[i], false);
			startNo++;
		}

		for (int i = 0; i < GameConst.Game.PLAYERACCOUNTS; i++) {
			players[i].updateCards(pl[i]);
		}
	}

	// 绘制桌面所有牌(showBottomCards:是否亮底牌)
	public void paintAllCards(boolean showBottomCards) {

		// 绘制玩家手牌
		players[0].paintCards(pl[0], true);
		players[1].paintCards(pl[1], false);
		players[2].paintCards(pl[2], false);
		// //test
		// players[1].paintCards(pl[1], true);
		// players[2].paintCards(pl[2], true);
		// //finish test

		// 绘制底牌(顶部）
		for (int i = 0; i < GameConst.Game.BOTTOMACCOUNTS; i++) {
			allBottomCards[i].setPosition(bottomPos[i][0], bottomPos[i][1]);
			allBottomCards[i].setVisible(showBottomCards);
			allBottomCards[i].addToscreen(layerManager);
		}

		// 绘制中心位置底牌（桌面中央）
		for (int i = GameConst.Game.BOTTOMACCOUNTS; i < allBottomCards.length; i++) {
			allBottomCards[i].setPosition(bottomPos[i][0], bottomPos[i][1]);
			allBottomCards[i].setVisible(showBottomCards);
			if (!showBottomCards) {
				allBottomCards[i].addToscreen(layerManager);
			} else {
				allBottomCards[i].removeFromscreen(layerManager);
			}
		}
	}

	// 抢地主
	public void qiangDiZhu() {
		int temp = random.nextInt(4);// 随机叫分
		boolean finished = true;

		// 当前角色未叫过分
		if (jiaofenByPlayers[currentPlayer] == -1) {
			// showJiaofenUI();
			showCallMessage(currentPlayer, temp + "fen");
			jiaofenByPlayers[currentPlayer] = temp;
		}

		// 叫分值最大者为地主
		if (jiaofenByPlayers[currentPlayer] > basicScore) {
			basicScore = jiaofenByPlayers[currentPlayer];
			highScorePlayer = currentPlayer;
		}

		for (int i = 0; i < 3; i++) {
			if (jiaofenByPlayers[i] == -1) {
				finished = false;
			}
		}

		if (jiaofenByPlayers[currentPlayer] == 3) {
			finished = true;
			setDiZhu(currentPlayer, 3);
		} else {
			if (finished) {
				// 无人叫分时，当前玩家默认叫1分为地主
				if (basicScore == 0) {
					turnToNext();
					basicScore = 1;
					highScorePlayer = currentPlayer;
					showCallMessage(currentPlayer, basicScore + "fen");
				}

				setDiZhu(highScorePlayer, basicScore);
			} else {
				turnToNext();
			}
		}
	}

	// 角色设置地主属性
	public void setDiZhu(int playerID, int jiaofenValue) {
		clearCallMessage();

		// showJiaofenUI();

		// 抢地主
		basicScore = jiaofenValue;
		players[playerID].setTagID(0);
		currentPlayer = playerID;
		currentCardsID = playerID;

		layerManager.insert(dizhuLayer, GameConst.GameCanvas.LAYER_player);
		dizhuLayer.setPosition(playersPos[playerID][0], playersPos[playerID][1]);

		// 获取第一手牌
		for (int i = 0; i < GameConst.Game.PORKEREACHPERSON; i++) {
			dizhuPl[i] = pl[playerID][i];
		}

		// 获取底牌
		for (int i = 0; i < bottomCards.length; i++) {
			dizhuPl[i + GameConst.Game.PORKEREACHPERSON] = bottomCards[i];
			;
		}

		// 重新排序
		PokerCard.sort(dizhuPl);
		players[playerID].updateCards(dizhuPl);

		// 重新绘制玩家手牌与亮底牌
		paintAllCards(true);
		gameStatus = 2;
		lastGameStatus = 2;
		option = selectButton(options[3], 3);
	}

	// 显示叫分选项
	public void showJiaofenUI() {
		boolean show = false;

		if (jiaofenByPlayers[0] == -1 && currentPlayer == 0) {
			show = true;
		}
		for (int i = 0; i < scoreOptions.length; i++) {
			scoreOptions[i].setVisible(show);
		}

		selectedSprite.setVisible(show);

		if (show) {
			selectButton(scoreOptions[ScoreOption], ScoreOption);
		}
	}

	// 显示出牌选项
	public void showOptionUI(boolean show) {
		for (int i = 0; i < options.length; i++) {
			options[i].setVisible(show && !finishChupai);
		}

		if (currentCards == null) {
			options[0].setVisible(false);
		}

		selectedSprite.setVisible(show && !finishChupai);

		if (selectedSprite.isVisible()) {
			selectButton(options[option], option);
		}
	}

	public void checkResult() {
		if (players[currentPlayer].getTagID() == 0) {
			DZWinSprite.setVisible(true);
		} else {
			NMWinSprite.setVisible(true);
		}

		// 清理玩家callmessage
		clearCallMessage();
		clearShowedCards();

		// 亮牌
		showCardsForAllPlayer();

		gameStatus = 0;
		lastGameStatus = 0;

		startSprite.setVisible(true);
		selectedSprite.setVisible(true);
		selectButton(startSprite, 0);
	}

	// 下一个玩家获取出牌token
	public void turnToNext() {
		currentPlayer++;
		finishChupai = false;

		if (currentPlayer > 2) {
			currentPlayer = 0;
		}

		callOptions[currentPlayer].setVisible(false);
		players[currentPlayer].paintSelectedCards(null);

		// 当前桌面最大牌为当前玩家时，下家可任意出牌，所以设置当前桌面最大牌为null
		if (gameStatus == 2) {
			if (currentPlayer == currentCardsID) {
				currentCards = null;
				for (int i = 0; i < players.length; i++) {
					callOptions[i].setVisible(false);
				}
			} else {
				callOptions[currentPlayer].setVisible(false);
			}
		}
	}

	// 不出牌
	public void showBuyao() {
		// 手牌恢复为未勾选状态
		reSelect();

		// 显示"不出"信息
		finishChupai = true;
		showCallMessage(currentPlayer, "buchu");

		// 下家获取出牌token
		turnToNext();
	}

	// 重选牌
	public void reSelect() {
		for (int i = 0; i < players[currentPlayer].plCards().length; i++) {
			players[currentPlayer].selectCardOrNot(i, false);
		}
	}

	// 提示牌
	public void showAdvice() {

		AllCombinations all = AllCombinations.getInstance();
		all.AIcards(players[currentPlayer].plCards());

		if (players[currentPlayer].selectByAI(currentCards) != null) {
			readyCards = players[currentPlayer].getCardsOfSelected();
			players[currentPlayer].paintCards(pl[currentPlayer], currentPlayer == 0);
		} else {
			showTexiao(101);// 无建议牌型
		}
	}

	// 出牌
	public void chupai() {
		int result;

		readyCards = players[currentPlayer].getCardsOfSelected();

		if (readyCards != null) {
			// 0:出牌成功，1：牌型不存在，2：所出牌型不一致，3：所出牌小于上家牌
			result = players[currentPlayer].readyForChupai(readyCards, currentCards);
			if (result == 0) {
				// 允许出牌时，玩家更新手牌
				if (result == 0) {
					players[currentPlayer].paintSelectedCards(readyCards);
					players[currentPlayer].removeSelectedCards(readyCards);
				}

				finishChupai = true;

				int type = PokerCard.getCardsType(readyCards);
				currentCards = readyCards;
				currentCardsID = currentPlayer;
				readyCards = null;
				showTexiao(type);

				// 检查玩家手牌是否全部出完
				if (players[currentPlayer].plCards().length == 0) {
					players[currentPlayer].paintSelectedCards(null);
					showOptionUI(false);
					checkResult();
				} else {
					turnToNext();
					if (type == CardsType.rocket) {
						showBuyao();
						showBuyao();
					}
				}
			} else {// 出牌失败
				showTexiao(100);
			}
		} else {// 未选中牌
			showTexiao(100);
		}
	}

	// 胜负已分，玩家全部亮牌
	public void showCardsForAllPlayer() {
		for (int i = 0; i < players.length; i++) {
			players[i].paintCards(pl[i], true);
		}
	}

	private void keyAction(int keyCode) {
		switch (gameStatus) {
		case 0:
			if (keyCode == Constants.KeyCode.OK) {
				startSprite.setVisible(false);
				selectedSprite.setVisible(false);
				// 开始新一局游戏
				startNewGame();
				gameStatus = 1;
				lastGameStatus = 1;
			}
			break;

		case 1:// 叫分
			if (currentPlayer == 0) {
				if (keyCode == Constants.KeyCode.LEFT) {
					if (ScoreOption == 0) {
						ScoreOption = selectButton(scoreOptions[3], 3);
					} else {
						ScoreOption = selectButton(scoreOptions[ScoreOption - 1], ScoreOption - 1);
					}
				}

				if (keyCode == Constants.KeyCode.RIGHT) {
					if (ScoreOption == scoreOptions.length - 1) {
						ScoreOption = selectButton(scoreOptions[0], 0);
					} else {
						ScoreOption = selectButton(scoreOptions[ScoreOption + 1], ScoreOption + 1);
					}
				}

				if (keyCode == Constants.KeyCode.OK) {
					showCallMessage(currentPlayer, ScoreOption + "fen");
					jiaofenByPlayers[currentPlayer] = ScoreOption;
					showJiaofenUI();
					qiangDiZhu();
				}
			}
			break;

		case 2:// 菜单选牌
			if (currentPlayer == 0) {
				if (keyCode == Constants.KeyCode.RIGHT) {
					if (option == options.length - 1) {
						if (options[0].isVisible()) {
							option = 0;
						} else {
							option = 1;
						}
					} else {
						option++;
					}

					selectButton(options[option], option);
				}
				if (keyCode == Constants.KeyCode.LEFT) {
					option--;
					if (options[0].isVisible() && option < 0) {
						option = options.length - 1;
					} else if (!options[0].isVisible() && option == 0) {
						option = options.length - 1;
					}

					selectButton(options[option], option);
				}
				if (keyCode == Constants.KeyCode.OK) {
					performOptionAction(option);
				}
				if (keyCode == Constants.KeyCode.DOWN) {
					gameStatus = 3;
					lastGameStatus = 3;
					option = 0;
					selectedSprite.setVisible(false);
					arrowIconSprite.setVisible(true);
				}
			}
			break;

		case 3:// 手工选牌
			if (currentPlayer == 0) {
				if (keyCode == Constants.KeyCode.RIGHT) {
					if (option == players[currentPlayer].plCards().length - 1) {
						option = 0;
					} else {
						option++;
					}
					arrowIconSprite.setPosition(GameConst.Position.ARROWICON_X + option * GameConst.GameCanvas.DISTANCEH,
							GameConst.Position.ARROWICON_Y);
				}
				if (keyCode == Constants.KeyCode.LEFT) {
					if (option == 0) {
						option = players[currentPlayer].plCards().length - 1;
					} else {
						option--;
					}
					arrowIconSprite.setPosition(GameConst.Position.ARROWICON_X + option * GameConst.GameCanvas.DISTANCEH,
							GameConst.Position.ARROWICON_Y);
				}
				if (keyCode == Constants.KeyCode.OK) {
					players[currentPlayer].reserveCardSelectStatus(option);// 更改第option张扑克的选中标志
				}
				if (keyCode == Constants.KeyCode.UP) {
					arrowIconSprite.setPosition(GameConst.Position.ARROWICON_X, GameConst.Position.ARROWICON_Y);
					arrowIconSprite.setVisible(false);
					selectedSprite.setVisible(true);
					gameStatus = 2;
					lastGameStatus = 2;
					option = selectButton(options[3], 3);
				}
			}
			break;
		case 4:
			if (keyCode == Constants.KeyCode.OK) {
				if (alertPauseSelectImgSprite.getX() == 264) {
					alertPauseSprite.setVisible(false);
					alertPauseSelectImgSprite.setVisible(false);
					gameStatus = lastGameStatus;
				} else {
					this.midlet.notifyDestroyed();
				}
			} else if (keyCode == Constants.KeyCode.LEFT) {
				alertPauseSelectImgSprite.setPosition(GameConst.GameCanvas.CX_PAUSE, GameConst.GameCanvas.CY_PAUSE);
			} else if (keyCode == Constants.KeyCode.RIGHT) {
				alertPauseSelectImgSprite.setPosition(GameConst.GameCanvas.OX_PAUSE, GameConst.GameCanvas.OY_PAUSE);
			}
			break;
		}
	}

	public int selectButton(Sprite sp, int returnValue) {
		int tempX = sp.getX() - 3;
		int tempY = sp.getY() - 7;

		if (sp.isVisible()) {
			selectedSprite.setPosition(tempX, tempY);
			selectedSprite.setVisible(true);
		}

		return returnValue;
	}

	public void performOptionAction(int index) {
		if (currentPlayer == 0 && !finishChupai) {
			switch (index) {
			case 0:
				showBuyao();
				break;

			case 1:
				reSelect();
				break;

			case 2:
				showAdvice();
				break;

			case 3:
				chupai();
				break;
			}
		}
	}

	public void clearUI() {
		DZWinSprite.setVisible(false);
		NMWinSprite.setVisible(false);

		// 移除地主标记
		for (int i = 0; i < players.length; i++) {
			players[i].setTagID(1);
		}
		layerManager.remove(dizhuLayer);

		// 恢复默认设置
		currentCardsID = currentPlayer;// 当前牌面所属的玩家,0玩家，1、2：npc
		currentCards = null;// 最新牌面出牌
		readyCards = null;// 准备出牌
		basicScore = 0;// 当局底分
		option = 0;// 选中的出牌按钮的index ID
		ScoreOption = 3;// 选中的叫分按钮的index ID
		jiaofenByPlayers = new int[] { -1, -1, -1 };// 依次为玩家，npc叫分值
		highScorePlayer = currentPlayer;
	}

	public void clearCallMessage() {
		for (int i = 0; i < players.length; i++) {
			callOptions[i].setVisible(false);
		}
	}

	public void clearShowedCards() {
		for (int i = 0; i < players.length; i++) {
			players[i].paintSelectedCards(null);
		}
	}

	public void showCallMessage(int playerID, String str) {
		showOptionUI(false);
		// showJiaofenUI();

		long startTime = System.currentTimeMillis();

		if (str.equals("0fen")) {
			Image temp = ImageRes.getInstance().getImage("callBuJiaoImg");
			callOptions[currentPlayer].setImage(temp, temp.getWidth(), temp.getHeight());
		}

		if (str.equals("1fen")) {
			Image temp = ImageRes.getInstance().getImage("callScore1Img");
			callOptions[currentPlayer].setImage(temp, temp.getWidth(), temp.getHeight());
		}

		if (str.equals("2fen")) {
			Image temp = ImageRes.getInstance().getImage("callScore2Img");
			callOptions[currentPlayer].setImage(temp, temp.getWidth(), temp.getHeight());
		}

		if (str.equals("3fen")) {
			Image temp = ImageRes.getInstance().getImage("callScore3Img");
			callOptions[currentPlayer].setImage(temp, temp.getWidth(), temp.getHeight());
		}

		if (str.equals("buchu")) {
			Image temp = ImageRes.getInstance().getImage("callBuChuImg");
			callOptions[currentPlayer].setImage(temp, temp.getWidth(), temp.getHeight());
		}

		// 清理上次出牌的显示
		players[currentPlayer].paintSelectedCards(null);

		layerManager.insert(callOptions[currentPlayer], GameConst.GameCanvas.LAYER_card);
		callOptions[currentPlayer].setVisible(true);

		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();

		long runTime = System.currentTimeMillis() - startTime;
		if (runTime < 1000) {
			try {
				Thread.sleep(1000 - runTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void showTexiao(int texiaoID) {
		boolean Cartoon = false;
		long startTime = System.currentTimeMillis();

		if (texiaoID == CardsType.rocket || texiaoID == CardsType.bomp || texiaoID == CardsType.plane || texiaoID == CardsType.plane
				|| texiaoID == CardsType.straight || texiaoID == CardsType.straightPair || texiaoID == CardsType.straightTriple) {
			Image temp = ImageRes.getInstance().getImage("texiaoImg" + texiaoID);
			texiaoSprite.setImage(temp, temp.getWidth(), temp.getHeight());
			texiaoSprite.setPosition(GameConst.Position.TEXIAO_X, GameConst.Position.TEXIAO_Y);
			texiaoSprite.setVisible(true);
		} else if (texiaoID == 100 || texiaoID == 101) {
			Image temp = ImageRes.getInstance().getImage("texiaoImg" + texiaoID);
			texiaoSprite.setImage(temp, temp.getWidth(), temp.getHeight());
			texiaoSprite.setPosition(GameConst.Position.GUIZE_X, GameConst.Position.GUIZE_Y);
			texiaoSprite.setVisible(true);
		}

		if (texiaoID == CardsType.rocket || texiaoID == CardsType.plane || texiaoID == CardsType.bomp) {
			Image tempA = ImageRes.getInstance().getImage("txAniImg" + texiaoID);
			if (texiaoID == CardsType.bomp) {// 炸弹
				txOfBompSpriteSets[0].setVisible(true);
				txOfBompSpriteSets[0].setPosition(GameConst.Position.ZDA_X, GameConst.Position.ZDA_Y);
			} else {
				txSprite.setImage(tempA, tempA.getWidth(), tempA.getHeight());
				txSprite.setVisible(true);
			}
			Cartoon = true;
		}

		if (Cartoon) {
			for (int i = 0; i < GameConst.GameCanvas.FRAMEOFTEXIAO; i++) {
				long cartoonTime = System.currentTimeMillis();
				if (texiaoID == CardsType.plane) {// 飞机
					txSprite.setPosition(GameConst.Position.FJA_X + i * 80, GameConst.Position.FJA_Y);
				} else if (texiaoID == CardsType.bomp) {// 炸弹
					if (i < 3) {
						txOfBompSpriteSets[0].setFrame(i);
					} else {// 炸弹爆炸
						txOfBompSpriteSets[0].setPosition(GameConst.Position.ZDA_X + txOfBompSpriteSets[0].getWidth() / 2, GameConst.Position.ZDA_Y
								- txOfBompSpriteSets[0].getHeight() / 2);
						txOfBompSpriteSets[0].setFrame(i);
						for (int j = 1; j < txOfBompSpriteSets.length; j++) {// 显示四个方向爆炸效果
							txOfBompSpriteSets[j].setVisible(true);
							txOfBompSpriteSets[j].setFrame(i);
						}
					}
				} else if (texiaoID == CardsType.rocket) {// 火箭
					txSprite.setPosition(GameConst.Position.HJA_X, GameConst.Position.HJA_Y - i * 6);
				}

				layerManager.paint(graphics, 0, 0);
				this.flushGraphics();

				long cartoonRunTime = System.currentTimeMillis() - cartoonTime;
				if (cartoonRunTime < 125) {
					try {
						Thread.sleep(125 - cartoonRunTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		long runTime = System.currentTimeMillis() - startTime;
		if (runTime < 1000) {
			try {
				Thread.sleep(1000 - runTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// 结束特效
		if (texiaoSprite.isVisible()) {
			texiaoSprite.setVisible(false);
		}

		// 结束特效动画
		if (Cartoon) {
			for (int i = 0; i < txOfBompSpriteSets.length; i++) {
				if (txOfBompSpriteSets[i].isVisible()) {
					txOfBompSpriteSets[i].setVisible(false);
				}
			}

			if (txSprite.isVisible()) {
				txSprite.setVisible(false);
			}
		}
	}

	public Sprite getContinueSprite() {
		return continueSprite;
	}

	public Sprite getOverSprite() {
		return overSprite;
	}

	public void startGameCanvas() {
		gameCanvasThread = new Thread(this);
		gameCanvasThread.start();
	}

	public MainMIDlet getMidlet() {
		return midlet;
	}

	public void updateStateToPlay() {
	}

	protected void keyPressed(int keyCode) {
		if (keyCode == Constants.KeyCode.NUM_0) {
			layerManager.insert(alertPauseSprite, 0);
			layerManager.insert(alertPauseSelectImgSprite, 0);
			alertPauseSprite.setVisible(true);
			alertPauseSelectImgSprite.setVisible(true);
			alertPauseSelectImgSprite.setPosition(GameConst.GameCanvas.CX_PAUSE, GameConst.GameCanvas.CY_PAUSE);
			gameStatus = 4;
			return;
		}

		keyAction(keyCode);
	}
}