package com.zhongdan.games.paopaolong;

import java.util.TimerTask;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.LayerManager;

public class FireBallTask extends TimerTask {

	private MyGameCanvas canvas;
	private Graphics graphics;
	private LayerManager layerManager;
	private BallSprite[][] balls;
	private BallSprite movingBall;
	private int angle;

	public FireBallTask(MyGameCanvas canvas, Graphics graphics, LayerManager layerManager, BallSprite[][] balls, BallSprite movingBall, int angle) {
		this.canvas = canvas;
		this.graphics = graphics;
		this.layerManager = layerManager;
		this.balls = balls;
		this.movingBall = movingBall;
		this.angle = angle;
	}

	public void run() {
		if (null != movingBall) {
			if (touchLeftBoundry() || touchRightBoundry()) {
				this.angle = -this.angle;
				int xStep = new Double(MyGameConstants.GameSettings.SINGLE_MOVE_STEP * Math.sin((new Integer(angle).doubleValue()) * Math.PI / 180))
						.intValue();
				int yStep = -(new Double(MyGameConstants.GameSettings.SINGLE_MOVE_STEP * Math.cos((new Integer(angle).doubleValue()) * Math.PI / 180))
						.intValue());
				movingBall.getSprite().move(xStep, yStep);
				layerManager.paint(graphics, 0, 0);
				this.canvas.flushGraphics();
			} else if (!hasCollision() && !touchTopBoundry()) {// If not collides with other ball
				int xStep = new Double(MyGameConstants.GameSettings.SINGLE_MOVE_STEP * Math.sin((new Integer(angle).doubleValue()) * Math.PI / 180))
						.intValue();
				int yStep = -(new Double(MyGameConstants.GameSettings.SINGLE_MOVE_STEP * Math.cos((new Integer(angle).doubleValue()) * Math.PI / 180))
						.intValue());
				movingBall.getSprite().move(xStep, yStep);
				layerManager.paint(graphics, 0, 0);
				this.canvas.flushGraphics();
			} else {
				int x = movingBall.getSprite().getX();
				int y = movingBall.getSprite().getY();
				int row = (y - MyGameConstants.GameSettings.TOP_LEFT_BALL_Y)
						/ MyGameConstants.GameSettings.ROW_HEIGHT
						+ ((y - MyGameConstants.GameSettings.TOP_LEFT_BALL_Y - (y - MyGameConstants.GameSettings.TOP_LEFT_BALL_Y)
								/ MyGameConstants.GameSettings.ROW_HEIGHT * MyGameConstants.GameSettings.ROW_HEIGHT) > (MyGameConstants.Ball.HEIGHT / 2 + 1) ? 1
								: 0);
				int columnFix = row % 2 == 0 ? MyGameConstants.GameSettings.TOP_LEFT_BALL_X : MyGameConstants.GameSettings.SECOND_ROW_LEFT_BALL_X;
				int column = (x - columnFix)
						/ MyGameConstants.GameSettings.COL_WIDTH
						+ ((x - columnFix - (x - columnFix) / MyGameConstants.GameSettings.COL_WIDTH * MyGameConstants.GameSettings.COL_WIDTH) > MyGameConstants.GameSettings.SECOND_ROW_LEFT_BALL_X ? 1
								: 0);
				int newX = (row % 2 == 0 ? MyGameConstants.GameSettings.TOP_LEFT_BALL_X : MyGameConstants.GameSettings.SECOND_ROW_LEFT_BALL_X)
						+ column * MyGameConstants.GameSettings.COL_WIDTH;
				int newY = MyGameConstants.GameSettings.TOP_LEFT_BALL_Y + row * MyGameConstants.GameSettings.ROW_HEIGHT;
				movingBall.getSprite().setPosition(newX, newY);
				int clearedBallNo = clearBall();
				if (0 == clearedBallNo) {
					balls[row][column] = movingBall;
				} else {
					int incScore = clearedBallNo * clearedBallNo;
					this.canvas.setScore(this.canvas.getScore() + incScore);
					movingBall = null;
				}
				layerManager.paint(graphics, 0, 0);
				this.canvas.flushGraphics();
				this.canvas.setMoving(false);
				this.cancel();
			}
		}
	}

	private int clearBall() {
		int color = movingBall.getColor();
		int removedBallNo = 0;
		int[][] partOfSet = new int[MyGameConstants.GameSettings.ROW_NO][MyGameConstants.GameSettings.COL_NO];
		Vector queueForCheck = new Vector();
		queueForCheck.addElement(movingBall);
		while (!queueForCheck.isEmpty()) {
			BallSprite queueTopBall = (BallSprite) queueForCheck.elementAt(0);
			int queueTopBallX = queueTopBall.getSprite().getX();
			int queueTopBallY = queueTopBall.getSprite().getY();
			int queueTopRow = (queueTopBallY - MyGameConstants.GameSettings.TOP_LEFT_BALL_Y) / MyGameConstants.GameSettings.ROW_HEIGHT;
			int queueTopCol = ((queueTopBallX - (queueTopRow % 2 == 0 ? MyGameConstants.GameSettings.TOP_LEFT_BALL_X
					: MyGameConstants.GameSettings.SECOND_ROW_LEFT_BALL_X)) / MyGameConstants.GameSettings.COL_WIDTH);
			// Check upper left
			int checkBallRow = queueTopRow - 1;
			int checkBallCol = queueTopCol - (queueTopRow % 2 == 0 ? 1 : 0);
			if (checkBallRow >= 0 && checkBallRow < MyGameConstants.GameSettings.ROW_NO && checkBallCol >= 0
					&& checkBallCol < MyGameConstants.GameSettings.COL_NO) {
				BallSprite checkBall = balls[checkBallRow][checkBallCol];
				if (null != checkBall && checkBall.getColor() == color && partOfSet[checkBallRow][checkBallCol] != 1) {
					queueForCheck.addElement(checkBall);
					partOfSet[checkBallRow][checkBallCol] = 1;
					removedBallNo++;
				}
			}
			// Check upper right
			checkBallRow = queueTopRow - 1;
			checkBallCol = queueTopCol + (queueTopRow % 2 == 0 ? 0 : 1);
			if (checkBallRow >= 0 && checkBallRow < MyGameConstants.GameSettings.ROW_NO && checkBallCol >= 0
					&& checkBallCol < MyGameConstants.GameSettings.COL_NO) {
				BallSprite checkBall = balls[checkBallRow][checkBallCol];
				if (null != checkBall && checkBall.getColor() == color && partOfSet[checkBallRow][checkBallCol] != 1) {
					queueForCheck.addElement(checkBall);
					partOfSet[checkBallRow][checkBallCol] = 1;
					removedBallNo++;
				}
			}
			// Check left
			checkBallRow = queueTopRow;
			checkBallCol = queueTopCol - 1;
			if (checkBallRow >= 0 && checkBallRow < MyGameConstants.GameSettings.ROW_NO && checkBallCol >= 0
					&& checkBallCol < MyGameConstants.GameSettings.COL_NO) {
				BallSprite checkBall = balls[checkBallRow][checkBallCol];
				if (null != checkBall && checkBall.getColor() == color && partOfSet[checkBallRow][checkBallCol] != 1) {
					queueForCheck.addElement(checkBall);
					partOfSet[checkBallRow][checkBallCol] = 1;
					removedBallNo++;
				}
			}
			// Check right
			checkBallRow = queueTopRow;
			checkBallCol = queueTopCol + 1;
			if (checkBallRow >= 0 && checkBallRow < MyGameConstants.GameSettings.ROW_NO && checkBallCol >= 0
					&& checkBallCol < MyGameConstants.GameSettings.COL_NO) {
				BallSprite checkBall = balls[checkBallRow][checkBallCol];
				if (null != checkBall && checkBall.getColor() == color && partOfSet[checkBallRow][checkBallCol] != 1) {
					queueForCheck.addElement(checkBall);
					partOfSet[checkBallRow][checkBallCol] = 1;
					removedBallNo++;
				}
			}
			// Check lower left
			checkBallRow = queueTopRow + 1;
			checkBallCol = queueTopCol - (queueTopRow % 2 == 0 ? 1 : 0);
			if (checkBallRow >= 0 && checkBallRow < MyGameConstants.GameSettings.ROW_NO && checkBallCol >= 0
					&& checkBallCol < MyGameConstants.GameSettings.COL_NO) {
				BallSprite checkBall = balls[checkBallRow][checkBallCol];
				if (null != checkBall && checkBall.getColor() == color && partOfSet[checkBallRow][checkBallCol] != 1) {
					queueForCheck.addElement(checkBall);
					partOfSet[checkBallRow][checkBallCol] = 1;
					removedBallNo++;
				}
			}
			// Check lower right
			checkBallRow = queueTopRow + 1;
			checkBallCol = queueTopCol + (queueTopRow % 2 == 0 ? 0 : 1);
			if (checkBallRow >= 0 && checkBallRow < MyGameConstants.GameSettings.ROW_NO && checkBallCol >= 0
					&& checkBallCol < MyGameConstants.GameSettings.COL_NO) {
				BallSprite checkBall = balls[checkBallRow][checkBallCol];
				if (null != checkBall && checkBall.getColor() == color && partOfSet[checkBallRow][checkBallCol] != 1) {
					queueForCheck.addElement(checkBall);
					partOfSet[checkBallRow][checkBallCol] = 1;
					removedBallNo++;
				}
			}
			queueForCheck.removeElementAt(0);
		}

		// To remove the balls that hanging isolated
		int[][] linkedToTop = new int[MyGameConstants.GameSettings.ROW_NO][MyGameConstants.GameSettings.COL_NO];
		if (removedBallNo >= 2) {
			for (int i = 0; i < MyGameConstants.GameSettings.ROW_NO - 1; i++) {
				for (int j = 0; j < MyGameConstants.GameSettings.COL_NO; j++) {
					if (i == 0 && partOfSet[i][j] != 1 && balls[i][j] != null) {
						linkedToTop[i][j] = 1;
					}
					if (balls[i][j] != null && partOfSet[i][j] != 1 && linkedToTop[i][j] == 1) {
						// Check upper left
						int row = i - 1;
						int col = j - (row % 2 == 0 ? 0 : 1);
						if (row >= 0 && col >= 0 && balls[row][col] != null && partOfSet[row][col] != 1) {
							linkedToTop[row][col] = 1;
						}
						// Check upper right
						row = i - 1;
						col = j + (row % 2 == 0 ? 1 : 0);
						if (row >= 0 && col < MyGameConstants.GameSettings.COL_NO && balls[row][col] != null && partOfSet[row][col] != 1) {
							linkedToTop[row][col] = 1;
						}
						// Check left
						row = i;
						col = j - 1;
						if (col >= 0 && balls[row][col] != null && partOfSet[row][col] != 1) {
							linkedToTop[row][col] = 1;
						}
						// Check right
						row = i;
						col = j + 1;
						if (col < MyGameConstants.GameSettings.COL_NO && balls[row][col] != null && partOfSet[row][col] != 1) {
							linkedToTop[row][col] = 1;
						}
						// Check lower left
						row = i + 1;
						col = j - (row % 2 == 0 ? 0 : 1);
						if (col >= 0 && balls[row][col] != null && partOfSet[row][col] != 1) {
							linkedToTop[row][col] = 1;
						}
						// Check lower right
						row = i + 1;
						col = j + (row % 2 == 0 ? 1 : 0);
						if (col < MyGameConstants.GameSettings.COL_NO && balls[row][col] != null && partOfSet[row][col] != 1) {
							linkedToTop[row][col] = 1;
						}
					}
				}
			}
		}

		if (removedBallNo >= 2) {
			Vector freefallBalls = new Vector();
			for (int i = 0; i < MyGameConstants.GameSettings.ROW_NO; i++) {
				for (int j = 0; j < MyGameConstants.GameSettings.COL_NO; j++) {
					if (partOfSet[i][j] == 1) {
						BallSprite ball = balls[i][j];
						freefallBalls.addElement(ball);
					}
				}
			}
			for (int i = 0; i < MyGameConstants.GameSettings.ROW_NO; i++) {
				for (int j = 0; j < MyGameConstants.GameSettings.COL_NO; j++) {
					if (partOfSet[i][j] != 1 && linkedToTop[i][j] != 1 && balls[i][j] != null) {
						BallSprite ball = balls[i][j];
						freefallBalls.addElement(ball);
						removedBallNo++;
					}
				}
			}
			freefallBalls.addElement(movingBall);
			for (int i = 0; i < 30; i++) {
				for (int j = 0; j < freefallBalls.size(); j++) {
					BallSprite ball = (BallSprite) freefallBalls.elementAt(j);
					ball.getSprite().setPosition(ball.getSprite().getX(), ball.getSprite().getY() + 10);
				}
				try {
					Thread.sleep(20);
				} catch (Exception e) {
					e.printStackTrace();
				}
				layerManager.paint(graphics, 0, 0);
				canvas.flushGraphics();
			}
			for (int i = 0; i < MyGameConstants.GameSettings.ROW_NO; i++) {
				for (int j = 0; j < MyGameConstants.GameSettings.COL_NO; j++) {
					if (partOfSet[i][j] == 1) {
						BallSprite ballForRemove = balls[i][j];
						layerManager.remove(ballForRemove.getSprite());
						balls[i][j] = null;
					}
				}
			}
			for (int i = 0; i < MyGameConstants.GameSettings.ROW_NO; i++) {
				for (int j = 0; j < MyGameConstants.GameSettings.COL_NO; j++) {
					if (partOfSet[i][j] != 1 && linkedToTop[i][j] != 1 && balls[i][j] != null) {
						BallSprite ballForRemove = balls[i][j];
						layerManager.remove(ballForRemove.getSprite());
						balls[i][j] = null;
					}
				}
			}
			layerManager.remove(movingBall.getSprite());
			return removedBallNo;
		} else {
			return 0;
		}

	}

	private boolean touchLeftBoundry() {
		int x = this.movingBall.getSprite().getX();
		if (x <= MyGameConstants.GameSettings.GAMEBOARD_LEFT) {
			return true;
		}
		return false;
	}

	private boolean touchRightBoundry() {
		int x = this.movingBall.getSprite().getX();
		if (x >= MyGameConstants.GameSettings.GAMEBOARD_RIGHT - MyGameConstants.Ball.WIDTH) {
			return true;
		}
		return false;
	}

	private boolean touchTopBoundry() {
		int y = this.movingBall.getSprite().getY();
		if (y <= MyGameConstants.GameSettings.TOP_LEFT_BALL_Y) {
			return true;
		}
		return false;
	}

	private boolean hasCollision() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 13; j++) {
				BallSprite ball = balls[i][j];
				if (ball != null) {
					if (this.movingBall.getSprite().collidesWith(ball.getSprite(), true)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
