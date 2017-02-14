package com.zhongdan.games.paopaolong;

import java.util.Timer;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.midlet.MIDlet;

public class MyGameCanvas extends GameCanvas {

	private MIDlet midlet;
	private Graphics graphics;
	private LayerManager layerManager = new LayerManager();
	private BallSprite[][] balls = new BallSprite[MyGameConstants.GameSettings.ROW_NO][MyGameConstants.GameSettings.COL_NO];
	private BallSprite waitingBall = null;
	private BallSprite nextBall = null;
	private BallSprite movingBall = null;
	private ArrowSprite arrow = null;
	private boolean isMoving = false;
	private int score = 0;

	protected MyGameCanvas(MIDlet midlet) {
		super(false);
		this.midlet = midlet;
		graphics = this.getGraphics();
		this.setFullScreenMode(true);
		initCanvas();
	}

	protected MyGameCanvas(boolean suppressKeyEvents) {
		super(suppressKeyEvents);
		graphics = this.getGraphics();
		this.setFullScreenMode(true);
		initCanvas();
	}

	public void paint(Graphics g) {
		graphics.drawString(String.valueOf(score), 50, 20, Graphics.TOP | Graphics.HCENTER);
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
			fireBall();
		} else if (keyCode == Constants.KeyCode.BACK) {
			this.midlet.notifyDestroyed();
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
		if (!isMoving) {
			isMoving = true;
			Timer fireBallTimer = new Timer();
			fireBallTimer.schedule(new FireBallTask(this, graphics, layerManager, balls, movingBall, arrow.getAngle()), 0,
					MyGameConstants.GameSettings.SINGLE_MOVE_INTERVAL);
		}
	}

	private void initCanvas() {
		// Initialize background
		Image backgroundImg = Images.getBackground();
		TiledLayer backgroundLayer = new TiledLayer(1, 1, backgroundImg, backgroundImg.getWidth(), backgroundImg.getHeight());
		backgroundLayer.setCell(0, 0, 1);
		layerManager.insert(backgroundLayer, 0);
		// Initialize arrow
		arrow = new ArrowSprite().createArrow();
		arrow.getSprite().setPosition(MyGameConstants.Arrow.LEFT, MyGameConstants.Arrow.TOP);
		layerManager.insert(arrow.getSprite(), 0);

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

		// Paint all
		layerManager.setViewWindow(0, 0, this.getWidth(), this.getHeight());
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
