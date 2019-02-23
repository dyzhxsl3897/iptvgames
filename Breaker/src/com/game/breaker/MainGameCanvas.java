package com.game.breaker;

import java.io.IOException;
import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.game.Constants.KeyCode;
import com.game.breaker.common.Position;
import com.game.sprite.Ball;
import com.game.sprite.Enemy;
import com.game.sprite.Hero;

public class MainGameCanvas extends GameCanvas implements Runnable {

	private final static long DELAY = 50;
	private final static int BALL_SPEED = 4;
	public MainMidlet midlet;
	public LayerManager layerManager = new LayerManager();// 图层管理器
	public boolean isRunning = false;
	public boolean pausing = false;
	public int fire = 0;// 小球出发方向 -1静止，0左上，1右上
	public int level = 1;
	public int score = 0;
	public int enemyCount = 0;// 剩余敌人数量
	public Thread mainThread = new Thread(this);
	private Image[] bgGameImages;
	private Sprite[] bgGameSprites;
	private Image[] enemyImages;
	private Enemy[] enemySprites;
	private Image heroImage;
	private Hero hero;
	private Image ballImage;
	private Ball ball;
	private Image pauseImage;
	private Sprite pauseSprite;
	private Image goonImage;
	private Sprite goonSprite;
	private Image exitImage;
	private Sprite exitSprite;

	protected MainGameCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
		initializeGame();
	}

	private void initializeGame() {
		// TODO Auto-generated method stub'
		reloadImages();
		reloadMusics();
		cleanUpBackground();
		initializeLevel(level);

		// new func 1

		// new func 2
	}

	private void initializeLevel(int level) {
		// TODO Auto-generated method stub

		// Last step to start the game
		isRunning = true;
		pausing = false;
		this.level = level;
		goStartPosition();
		int idx = 0;
		if (level > 0 && level < 6) {
			for (int i = 0; i < Position.p_1_5.length; i++) {
				int[][] line = Position.p_1_5[i];
				for (int j = 0; j < line.length; j++) {
					if (Position.getPosition(i, j, level) != 0) {
						Enemy temp = new Enemy(enemyImages[Position.getPosition(i, j, level) - 1]);
						temp.setPosition(Position.p_1_5[i][j][0], Position.p_1_5[i][j][1]);
						if (Position.getPosition(i, j, level) == 5) {
							temp.setEnableBreak(false);
						}
						layerManager.append(temp);
						enemySprites[idx] = temp;
						idx++;
					}
				}
			}
		} else {
			for (int i = 0; i < Position.p_6_10.length; i++) {
				int[][] line = Position.p_6_10[i];
				for (int j = 0; j < line.length; j++) {
					if (Position.getPosition(i, j, level) != 0) {
						Enemy temp = new Enemy(enemyImages[Position.getPosition(i, j, level) - 1]);
						temp.setPosition(Position.p_6_10[i][j][0], Position.p_6_10[i][j][1]);
						if (Position.getPosition(i, j, level) == 5) {
							temp.setEnableBreak(false);
						}
						layerManager.append(temp);
						enemySprites[idx] = temp;
						idx++;
					}
				}
			}
		}
		layerManager.append(hero);
		layerManager.append(ball);
		layerManager.append(bgGameSprites[level - 1]);
		if (!mainThread.isAlive()) {
			mainThread.start();
		}
	}

	private void cleanUpBackground() {
		layerManager.remove(bgGameSprites[level - 1]);
		layerManager.remove(ball);
		layerManager.remove(hero);
	}

	private void reloadMusics() {
		// TODO Auto-generated method stub

	}

	private void reloadImages() {
		// TODO Auto-generated method stub
		try {
			pauseImage = Image.createImage("/pause.png");
			pauseSprite = new Sprite(pauseImage);
			goonImage = Image.createImage("/goon.png");
			goonSprite = new Sprite(goonImage);
			exitImage = Image.createImage("/exit.png");
			exitSprite = new Sprite(exitImage);

			goonSprite.setPosition(252, 143);
			exitSprite.setPosition(352, 143);

			bgGameImages = new Image[10];
			for (int i = 0; i < bgGameImages.length; i++) {
				bgGameImages[i] = Image.createImage("/bg_" + (i + 1) + ".jpg");
			}
			bgGameSprites = new Sprite[10];
			for (int i = 0; i < bgGameSprites.length; i++) {
				bgGameSprites[i] = new Sprite(bgGameImages[i]);
			}

			heroImage = Image.createImage("/hero.png");
			hero = new Hero(heroImage, 75, 39);

			ballImage = Image.createImage("/ball.png");
			ball = new Ball(ballImage, 15, 15);

			enemyImages = new Image[5];
			for (int i = 0; i < enemyImages.length; i++) {
				enemyImages[i] = Image.createImage("/enemy/e_" + (i + 1) + ".png");
			}

			enemySprites = new Enemy[109];

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
		switch (keyCode) {
		case KeyCode.LEFT:
			if (isRunning && !pausing) {
				hero.left();
				fire = 0;
			} else if (pausing) {
				goonSprite.setVisible(true);
				exitSprite.setVisible(false);
			}
			break;
		case KeyCode.RIGHT:
			if (isRunning && !pausing) {
				hero.right();
				fire = 1;
			} else if (pausing) {
				goonSprite.setVisible(false);
				exitSprite.setVisible(true);
			}
			break;
		case KeyCode.OK:
			if (pausing) {
				if (exitSprite.isVisible()) {
					midlet.exit();
				}
			}
			if (isRunning) {
				pause();
			} else {

			}
			break;
		}
	}

	protected void keyRepeated(int keyCode) {
		super.keyRepeated(keyCode);
		switch (keyCode) {
		case KeyCode.LEFT:
			if (isRunning) {
				hero.left();
				fire = 0;
			} else {
			}
			break;
		case KeyCode.RIGHT:
			if (isRunning) {
				hero.right();
				fire = 1;
			} else {
			}
			break;
		}
	}

	protected void keyReleased(int keyCode) {
		hero.setDirection(0);
	}

	public void run() {
		try {
			Graphics g = getGraphics();
			while (isRunning) {
				if (pausing) {
					drawScreen(g);
					Thread.currentThread();
					Thread.sleep(DELAY);
					continue;
				}
				Random rdm = new Random();
				for (int i = 0; i < enemySprites.length; i++) {
					if (enemySprites[i] != null) {
						if (enemySprites[i].isEnableBreak()) {
							if (rdm.nextInt(100) == 0) {
								enemySprites[i].nextFrame();
							} else if (enemySprites[i].getFrame() != 0) {
								enemySprites[i].nextFrame();
							}
						} else {
							if (ball.collidesWith(enemySprites[i], false)) {
								enemySprites[i].nextFrame();
							} else if (enemySprites[i].getFrame() != 0) {
								enemySprites[i].nextFrame();
							}
						}
					}
				}

				// 小球移动
				ball.fire(fire);
				// 接球
				if (ball.collidesWith(hero, false) && System.currentTimeMillis() - hero.getCatchBallTime() > 1000) {
					hero.setCatchBallTime(System.currentTimeMillis());
					ball.setyStep(-ball.getyStep());
					if (hero.getDirection() == 1 && Math.abs(ball.getxStep()) > 1) {
						ball.setxStep(ball.getxStep() - 1);
						ball.setyStep(ball.getxStep() > 0 ? (ball.getyStep() + 1) : (ball.getyStep() - 1));
					} else if (hero.getDirection() == 2 && ball.getyStep() > 1) {
						ball.setxStep(ball.getxStep() + 1);
						ball.setyStep(ball.getxStep() < 0 ? (ball.getyStep() + 1) : (ball.getyStep() - 1));
					}
				}
				// 打砖块
				enemyCount = 0;
				boolean ifTurn = false;
				for (int i = 0; i < enemySprites.length; i++) {
					if (enemySprites[i] != null && ball.collidesWith(enemySprites[i], false)
							&& System.currentTimeMillis() - enemySprites[i].getCatchBallTime() > 500) {
						enemySprites[i].setCatchBallTime(System.currentTimeMillis());
						if (Math.abs(enemySprites[i].getCenterX() - ball.getCenterX()) > Math.abs(enemySprites[i].getCenterY() - ball.getCenterY())) {
							if (!ifTurn) {
								ball.setxStep(-ball.getxStep());
								ifTurn = true;
							}
						} else {
							if (!ifTurn) {
								ball.setyStep(-ball.getyStep());
								ifTurn = true;
							}
						}
						if (enemySprites[i].isEnableBreak()) {
							layerManager.remove(enemySprites[i]);
							enemySprites[i] = null;
						}
					}
					if (enemySprites[i] != null && enemySprites[i].isEnableBreak()) {
						enemyCount++;
					}
				}
				// 全歼敌人
				if (enemyCount < 1) {
					cleanUpBackground();
					if (level == 10) {
						level = 1;
					} else {
						initializeLevel(level + 1);
					}
				}
				// 死亡
				if (ball.getY() > 330) {
					goStartPosition();
				}

				drawScreen(g);
				Thread.currentThread();
				Thread.sleep(DELAY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void drawScreen(Graphics g) {
		// g.setColor(0xffffff);
		// g.fillRect(0, 0, getWidth(), getHeight());
		// g.setColor(0x0000ff);
		layerManager.paint(g, 0, 0);
		flushGraphics();
	}

	private void goStartPosition() {
		fire = -1;
		ball.setMove(false);
		ball.setPosition(302, 305);
		hero.setPosition(272, 320);
		ball.setxStep(-BALL_SPEED);
		ball.setyStep(-BALL_SPEED);
	}

	private void pause() {
		pausing = !pausing;
		if (pausing) {
			layerManager.insert(goonSprite, 0);
			layerManager.insert(exitSprite, 1);
			layerManager.insert(pauseSprite, 2);
			exitSprite.setVisible(false);
		} else {
			layerManager.remove(pauseSprite);
			layerManager.remove(goonSprite);
			layerManager.remove(exitSprite);
		}
	}
}
