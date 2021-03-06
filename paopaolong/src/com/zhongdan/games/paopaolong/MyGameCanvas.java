package com.zhongdan.games.paopaolong;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.TiledLayer;

import com.zhongdan.games.paopaolong.Constants.KeyCode;
import com.zhongdan.games.paopaolong.MyGameConstants.GameSettings;

public class MyGameCanvas extends GameCanvas {

	private MainMidlet midlet;
	private Graphics graphics;
	private LayerManager layerManager = new LayerManager();
	private BallSprite[][] balls = new BallSprite[MyGameConstants.GameSettings.ROW_NO][MyGameConstants.GameSettings.COL_NO];
	private BallSprite waitingBall = null;
	private BallSprite nextBall = null;
	private BallSprite movingBall = null;
	private ArrowSprite arrow = null;
	private Image gameOverBackgroundImage = null;
	private Image gameOverFocusImage = null;
	private boolean isMoving = false;
	private boolean isGameOver = false;
	private boolean canPressOKBackToMenu = false;// 游戏失败后可以按OK返回菜单
	private ScoreSprite score = null;
	public Date systemDate = new Date();

	protected MyGameCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;
		graphics = this.getGraphics();
		this.setFullScreenMode(true);
		initCanvas();
	}

	public void initCanvas() {
		// Initialize layerManager
		for (int i = layerManager.getSize() - 1; i >= 0; i--) {
			layerManager.remove(layerManager.getLayerAt(i));
		}

		// Initialize background
		Image backgroundImg = Images.getBackground();
		TiledLayer backgroundLayer = new TiledLayer(1, 1, backgroundImg, backgroundImg.getWidth(), backgroundImg.getHeight());
		backgroundLayer.setCell(0, 0, 1);
		layerManager.insert(backgroundLayer, 0);

		// Initialize arrow
		arrow = new ArrowSprite().createArrow();
		arrow.getSprite().setPosition(MyGameConstants.Arrow.LEFT, MyGameConstants.Arrow.TOP);
		layerManager.insert(arrow.getSprite(), 0);

		// Initialize score
		score = new ScoreSprite(0, this, graphics);
		systemDate = new Date();
		balls = new BallSprite[MyGameConstants.GameSettings.ROW_NO][MyGameConstants.GameSettings.COL_NO];
		movingBall = null;
		isMoving = false;
		isGameOver = false;
		canPressOKBackToMenu = false;

		// Initialize game over screen
		gameOverBackgroundImage = ImageUtil.createImage("/game_over.png");
		gameOverFocusImage = ImageUtil.createImage("/game_over_focus.png");

		initializeBalls();

		// Paint all
		layerManager.setViewWindow(0, 0, this.getWidth(), this.getHeight());
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	public void dropDownTwoLines() {
		if (!hasBallInBottomTwoRows()) {
			for (int row = GameSettings.ROW_NO - 1; row > 1; row--) {
				for (int col = 0; col < GameSettings.COL_NO; col++) {
					balls[row][col] = balls[row - 2][col];
					if (balls[row][col] != null) {
						int posX = balls[row][col].getSprite().getX();
						int posY = balls[row][col].getSprite().getY();
						balls[row][col].getSprite().setPosition(posX, posY + 2 * GameSettings.ROW_HEIGHT);
					}
				}
			}
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < (i % 2 == 0 ? MyGameConstants.GameSettings.COL_NO : MyGameConstants.GameSettings.COL_NO - 1); j++) {
					int left = (i % 2 == 0 ? MyGameConstants.GameSettings.TOP_LEFT_BALL_X : MyGameConstants.GameSettings.SECOND_ROW_LEFT_BALL_X)
							+ MyGameConstants.Ball.WIDTH * j;
					int top = MyGameConstants.GameSettings.TOP_LEFT_BALL_Y + i * MyGameConstants.GameSettings.ROW_HEIGHT;
					BallSprite ball = new BallSprite().createBigRandomBall();
					balls[i][j] = ball;
					ball.getSprite().setPosition(left, top);
					layerManager.insert(ball.getSprite(), 0);
				}
			}
		}
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	public boolean hasBallInBottomTwoRows() {
		boolean hasBallInBottomRow = false;
		for (int i = 0; i < GameSettings.COL_NO; i++) {
			if (balls[GameSettings.ROW_NO - 1][i] != null) {
				hasBallInBottomRow = true;
			}
			if (balls[GameSettings.ROW_NO - 2][i] != null) {
				hasBallInBottomRow = true;
			}
		}
		return hasBallInBottomRow;
	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	protected void keyRepeated(int keyCode) {
		if (keyCode == Constants.KeyCode.LEFT) {
			turnLeftArrow();
		} else if (keyCode == Constants.KeyCode.RIGHT) {
			turnRightArrow();
		}
	}

	protected void keyPressed(int keyCode) {
		if (keyCode == Constants.KeyCode.LEFT) {
			turnLeftArrow();
		} else if (keyCode == Constants.KeyCode.RIGHT) {
			turnRightArrow();
		} else if (keyCode == Constants.KeyCode.OK) {
			if (!isGameOver) {
				fireBall();
			} else if (canPressOKBackToMenu) {
				this.midlet.getDisplay().setCurrent(this.midlet.getMenuCanvas());
			}
		} else if (keyCode == Constants.KeyCode.BACK || keyCode == KeyCode.BACK_1 || keyCode == KeyCode.BACK_2) {
			this.midlet.getDisplay().setCurrent(this.midlet.getMenuCanvas());
		}
	}

	private void turnRightArrow() {
		if (arrow.getAngle() < MyGameConstants.GameSettings.ANGLE_MAX) {
			arrow.setAngle(arrow.getAngle() + MyGameConstants.GameSettings.ANGLE_SINGLE_MOVE);
			layerManager.paint(graphics, 0, 0);
			this.flushGraphics();
		}
	}

	private void turnLeftArrow() {
		if (arrow.getAngle() > -MyGameConstants.GameSettings.ANGLE_MAX) {
			arrow.setAngle(arrow.getAngle() - MyGameConstants.GameSettings.ANGLE_SINGLE_MOVE);
			layerManager.paint(graphics, 0, 0);
			this.flushGraphics();
		}
	}

	private void fireBall() {
		if (!isMoving) {
			// Switch next ball and waiting ball, create new next ball
			movingBall = waitingBall;
			int nextBallColor = nextBall.getColor();
			if (nextBallColor == MyGameConstants.BallColor.BLUE) {
				waitingBall = new BallSprite().createBigBlueBall();
			} else if (nextBallColor == MyGameConstants.BallColor.PURPLE) {
				waitingBall = new BallSprite().createBigPurpleBall();
			} else if (nextBallColor == MyGameConstants.BallColor.RED) {
				waitingBall = new BallSprite().createBigRedBall();
			} else if (nextBallColor == MyGameConstants.BallColor.YELLOW) {
				waitingBall = new BallSprite().createBigYellowBall();
			}
			waitingBall.getSprite().setPosition(MyGameConstants.WaitingBall.LEFT, MyGameConstants.WaitingBall.TOP);
			layerManager.insert(waitingBall.getSprite(), 0);
			layerManager.remove(nextBall.getSprite());
			nextBall = new BallSprite().createSmallRandomBall();
			layerManager.insert(nextBall.getSprite(), 0);
			nextBall.getSprite().setPosition(MyGameConstants.NextBall.LEFT, MyGameConstants.NextBall.TOP);

			// Move the moving ball
			isMoving = true;
			Timer fireBallTimer = new Timer();
			fireBallTimer.schedule(new FireBallTask(this, graphics, layerManager, balls, movingBall, arrow.getAngle()), 0,
					MyGameConstants.GameSettings.SINGLE_MOVE_INTERVAL);
		}
	}

	private void initializeBalls() {
		// Initialize waiting ball
		waitingBall = new BallSprite().createBigRandomBall();
		nextBall = new BallSprite().createSmallRandomBall();
		waitingBall.getSprite().setPosition(MyGameConstants.WaitingBall.LEFT, MyGameConstants.WaitingBall.TOP);
		nextBall.getSprite().setPosition(MyGameConstants.NextBall.LEFT, MyGameConstants.NextBall.TOP);
		layerManager.insert(waitingBall.getSprite(), 0);
		layerManager.insert(nextBall.getSprite(), 0);

		// Initialize balls
		/*-
		int initMap[][] = new int[][] { { 0, 1, 1, 1, 0, 3, 2, 1, 1, 0, 2, 3, 1 }, { 3, 2, 2, 2, 0, 0, 0, 2, 0, 2, 3, 1, 0 },
				{ 3, 1, 1, 3, 2, 0, 0, 3, 1, 2, 1, 3, 1 }, { 3, 0, 2, 0, 2, 0, 3, 1, 1, 3, 1, 3, 0 } };
		for (int i = 0; i < MyGameConstants.GameSettings.INITIAL_ROW_NO; i++) {
			for (int j = 0; j < (i % 2 == 0 ? MyGameConstants.GameSettings.COL_NO : MyGameConstants.GameSettings.COL_NO - 1); j++) {
				int left = (i % 2 == 0 ? MyGameConstants.GameSettings.TOP_LEFT_BALL_X : MyGameConstants.GameSettings.SECOND_ROW_LEFT_BALL_X)
						+ MyGameConstants.Ball.WIDTH * j;
				int top = MyGameConstants.GameSettings.TOP_LEFT_BALL_Y + i * MyGameConstants.GameSettings.ROW_HEIGHT;
				BallSprite ball = null;
				if (initMap[i][j] == 0) {
					ball = new BallSprite().createBigBlueBall();
				} else if (initMap[i][j] == 1) {
					ball = new BallSprite().createBigPurpleBall();
				} else if (initMap[i][j] == 2) {
					ball = new BallSprite().createBigRedBall();
				} else if (initMap[i][j] == 3) {
					ball = new BallSprite().createBigYellowBall();
				}
				balls[i][j] = ball;
				ball.getSprite().setPosition(left, top);
				layerManager.insert(ball.getSprite(), 0);
			}
		}
		 */
		for (int i = 0; i < MyGameConstants.GameSettings.INITIAL_ROW_NO; i++) {
			for (int j = 0; j < (i % 2 == 0 ? MyGameConstants.GameSettings.COL_NO : MyGameConstants.GameSettings.COL_NO - 1); j++) {
				int left = (i % 2 == 0 ? MyGameConstants.GameSettings.TOP_LEFT_BALL_X : MyGameConstants.GameSettings.SECOND_ROW_LEFT_BALL_X)
						+ MyGameConstants.Ball.WIDTH * j;
				int top = MyGameConstants.GameSettings.TOP_LEFT_BALL_Y + i * MyGameConstants.GameSettings.ROW_HEIGHT;
				BallSprite ball = new BallSprite().createBigRandomBall();
				balls[i][j] = ball;
				ball.getSprite().setPosition(left, top);
				layerManager.insert(ball.getSprite(), 0);
			}
		}
	}

	public void failed() {
		Timer failedScreenTimer = new Timer();
		failedScreenTimer.schedule(new TimerTask() {
			public void run() {
				graphics.drawImage(gameOverBackgroundImage, 0, 0, Graphics.TOP | Graphics.LEFT);
				graphics.drawImage(gameOverFocusImage, GameSettings.GAME_OVER_FOCUS_X, GameSettings.GAME_OVER_FOCUS_Y, Graphics.TOP | Graphics.LEFT);
				flushGraphics();
				canPressOKBackToMenu = true;
			}
		}, GameSettings.GAME_OVER_SCREEN_DELAY);
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	public BallSprite[][] getBalls() {
		return balls;
	}

	public BallSprite getMovingBall() {
		return movingBall;
	}

	public void setMovingBall(BallSprite movingBall) {
		this.movingBall = movingBall;
	}

	public LayerManager getLayerManager() {
		return layerManager;
	}

	public void setLayerManager(LayerManager layerManager) {
		this.layerManager = layerManager;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public int getScore() {
		return score.getScore();
	}

	public void setScore(int score) {
		this.score.setScore(score);
	}

}
