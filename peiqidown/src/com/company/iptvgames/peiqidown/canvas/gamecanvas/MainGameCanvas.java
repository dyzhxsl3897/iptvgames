package com.company.iptvgames.peiqidown.canvas.gamecanvas;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.company.iptvgames.framework.utils.ImageUtil;
import com.company.iptvgames.framework.utils.RandomUtil;
import com.company.iptvgames.peiqidown.GameConst;
import com.company.iptvgames.peiqidown.MainMIDlet;
import com.company.iptvgames.peiqidown.boards.Board;
import com.company.iptvgames.peiqidown.boards.BoardFactory;
import com.company.iptvgames.peiqidown.canvas.gamecanvas.states.GCDeadState;
import com.company.iptvgames.peiqidown.canvas.gamecanvas.states.GCPauseState;
import com.company.iptvgames.peiqidown.canvas.gamecanvas.states.GCPlayState;
import com.company.iptvgames.peiqidown.canvas.gamecanvas.states.GCState;
import com.company.iptvgames.peiqidown.peiqi.Peiqi;
import com.company.iptvgames.peiqidown.resources.ImageRes;

public class MainGameCanvas extends GameCanvas implements Runnable {

	private MainMIDlet midlet;
	private Graphics graphics;

	private GCState state;
	private GCPlayState playState;
	private GCPauseState pauseState;
	private GCDeadState deadState;

	private ImageRes images;

	private LayerManager layerManager;
	private TiledLayer bg1Layer;
	private TiledLayer bg2Layer;
	private Sprite alertSprite;
	private Sprite continueSprite;
	private Sprite overSprite;

	private Thread gameCanvasThread;

	private Peiqi peiqi;
	private Board[] boards;

	private int boardIndex;
	private boolean isStayOnGameCanvas;

	public MainGameCanvas(MainMIDlet midlet) {
		super(false);
		this.setFullScreenMode(true);
		this.graphics = getGraphics();
		this.midlet = midlet;

		loadImages();

		playState = new GCPlayState(this);
		pauseState = new GCPauseState(this);
		deadState = new GCDeadState(this);

		updateState(playState);
	}

	public void startGameCanvas() {
		gameCanvasThread = new Thread(this);
		gameCanvasThread.start();
	}

	public void turnOffGameCanvas() {
		this.state.exitState();
		this.isStayOnGameCanvas = false;
	}

	public void initalizeGame() {
		this.boardIndex = 0;
		layerManager = new LayerManager();
		Image bg1Img = ImageRes.getInstance().getImage("bg1Img");
		Image bg2Img = ImageRes.getInstance().getImage("bg2Img");
		bg1Layer = new TiledLayer(1, 1, bg1Img, bg1Img.getWidth(), bg1Img.getHeight());
		bg2Layer = new TiledLayer(1, 1, bg2Img, bg2Img.getWidth(), bg2Img.getHeight());
		bg1Layer.setCell(0, 0, 1);
		bg2Layer.setCell(0, 0, 1);
		bg2Layer.setPosition(GameConst.GameCanvas.BG2_X, GameConst.GameCanvas.BG2_Y);
		layerManager.append(bg1Layer);
		layerManager.append(bg2Layer);

		Image alertImg = ImageRes.getInstance().getImage("alertImg");
		Image continueImg = ImageRes.getInstance().getImage("continueImg");
		Image overImg = ImageRes.getInstance().getImage("overImg");
		alertSprite = new Sprite(alertImg);
		continueSprite = new Sprite(continueImg);
		overSprite = new Sprite(overImg);
		layerManager.insert(alertSprite, GameConst.GameCanvas.LAYER_0);
		layerManager.insert(continueSprite, GameConst.GameCanvas.LAYER_0);
		layerManager.insert(overSprite, GameConst.GameCanvas.LAYER_0);
		continueSprite.setPosition(GameConst.Pause.CONTINUE_X, GameConst.Pause.CONTINUE_Y);
		overSprite.setPosition(GameConst.Pause.OVER_X, GameConst.Pause.OVER_Y);
		alertSprite.setVisible(false);
		continueSprite.setVisible(false);
		overSprite.setVisible(false);

		peiqi = new Peiqi();
		peiqi.addToScreen(layerManager);

		boards = new Board[GameConst.Board.NUMBER];
		Board board = BoardFactory.getInstance().createRandomBoard(GameConst.Board.START_X, GameConst.Board.TOP_Y, boardIndex++);
		board.addToScreen(layerManager);
		boards[0] = board;
		for (int i = 1; i < boards.length; i++) {
			board = BoardFactory.getInstance().createRandomBoard(RandomUtil.nextInt(GameConst.Board.LEFT_X, GameConst.Board.RIGHT_X),
					GameConst.Board.TOP_Y + GameConst.Board.LAYER_HEIGHT * i, boardIndex++);
			board.addToScreen(layerManager);
			boards[i] = board;
		}
		isStayOnGameCanvas = true;
	}

	private void loadImages() {
		ImageRes.getInstance().loadImage("bg1Img", ImageUtil.createImage("/bj.png"));
		ImageRes.getInstance().loadImage("bg2Img", ImageUtil.createImage("/bj_02.png"));
		ImageRes.getInstance().loadImage("alertImg", ImageUtil.createImage("/alert.png"));
		ImageRes.getInstance().loadImage("overImg", ImageUtil.createImage("/over.png"));
		ImageRes.getInstance().loadImage("continueImg", ImageUtil.createImage("/continue.png"));
		ImageRes.getInstance().loadImage("peiqiStandImg", ImageUtil.createImage("/peiqi/stand.png"));
		ImageRes.getInstance().loadImage("peiqiWalkImg", ImageUtil.createImage("/peiqi/walk.png"));
		ImageRes.getInstance().loadImage("peiqiJumpImg", ImageUtil.createImage("/peiqi/jump.png"));
		ImageRes.getInstance().loadImage("flapBoardImg", ImageUtil.createImage("/board/flap_board.png"));
		ImageRes.getInstance().loadImage("landBoardImg", ImageUtil.createImage("/board/land_board.png"));
		ImageRes.getInstance().loadImage("leftBoardImg", ImageUtil.createImage("/board/left_board.png"));
		ImageRes.getInstance().loadImage("rightBoardImg", ImageUtil.createImage("/board/right_board.png"));
		ImageRes.getInstance().loadImage("springBoardImg", ImageUtil.createImage("/board/spring_board.png"));
		ImageRes.getInstance().loadImage("stabBoardImg", ImageUtil.createImage("/board/stab_board.png"));
	}

	public void run() {
		while (isStayOnGameCanvas) {
			long startTime = System.currentTimeMillis();

			if (isInPlayState()) {
				screenMoveUp();
				boardScroll();
				keyAction();
				peiqi.drop(boards);
				if (peiqi.isOutOfScreen()) {
					updateState(deadState);
				}
				if (peiqi.isStandOnLeftBoard(boards)) {
					peiqi.move(-GameConst.Board.LEFT_BOARD_MOVE_SPEED, 0);
				} else if (peiqi.isStandOnRightBoard(boards)) {
					peiqi.move(GameConst.Board.LEFT_BOARD_MOVE_SPEED, 0);
				}
				animation();
			} else if (isInDeadState()) {
				keyAction();
			}

			this.layerManager.paint(graphics, 0, 0);
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

	private void animation() {
		peiqi.nextFrame();
		for (int i = 0; i < boards.length; i++) {
			boards[i].nextFrame();
		}
	}

	private void boardScroll() {
		for (int i = 0; i < boards.length; i++) {
			Board board = boards[i];
			if (board.getPosY() <= 0) {
				int index = boards[(i + boards.length - 1) % boards.length].getIndex() + 1;
				Board newBoard = BoardFactory.getInstance().createRandomBoard(RandomUtil.nextInt(GameConst.Board.LEFT_X, GameConst.Board.RIGHT_X),
						GameConst.Board.LAYER_HEIGHT * GameConst.Board.NUMBER, index);
				newBoard.addToScreen(layerManager);
				board.removeFromScreen(layerManager);
				boards[i] = newBoard;
			}
		}
	}

	private void screenMoveUp() {
		if (peiqi.isStandOnBoard(boards)) {
			peiqiMoveUp();
		}
		boardsMoveUp();
	}

	private void boardsMoveUp() {
		for (int i = 0; i < boards.length; i++) {
			boards[i].move(0, -GameConst.GameCanvas.MOVE_UP_SPEED);
		}
	}

	private void peiqiMoveUp() {
		peiqi.move(0, -GameConst.GameCanvas.MOVE_UP_SPEED);
	}

	private void keyAction() {
		this.state.keyAction();
	}

	public MainMIDlet getMidlet() {
		return midlet;
	}

	public boolean isInPlayState() {
		return this.getState() instanceof GCPlayState;
	}

	public boolean isInPauseState() {
		return this.getState() instanceof GCPauseState;
	}

	public boolean isInDeadState() {
		return this.getState() instanceof GCDeadState;
	}

	public void updateStateToPlay() {
		if (this.state != null) {
			this.state.exitState();
		}
		this.state = this.playState;
		this.state.intoState();
	}

	public void updateState(GCState newState) {
		if (this.state != null) {
			this.state.exitState();
		}
		this.state = newState;
		this.state.intoState();
	}

	public GCState getState() {
		return state;
	}

	public GCPlayState getPlayState() {
		return playState;
	}

	public GCPauseState getPauseState() {
		return pauseState;
	}

	public ImageRes getImages() {
		return images;
	}

	public LayerManager getLayerManager() {
		return layerManager;
	}

	public Peiqi getPeiqi() {
		return peiqi;
	}

	public Sprite getAlertSprite() {
		return alertSprite;
	}

	public Sprite getContinueSprite() {
		return continueSprite;
	}

	public Sprite getOverSprite() {
		return overSprite;
	}

}
