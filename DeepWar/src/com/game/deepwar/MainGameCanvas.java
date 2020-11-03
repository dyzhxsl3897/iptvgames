package com.game.deepwar;

import java.util.Random;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.game.Constants.KeyCode;
import com.game.sprite.Bomb;
import com.game.sprite.EnemyBomb;
import com.game.sprite.Ship;
import com.game.sprite.Submarine;
import com.yunyouhudong.framework.utils.NumberImgUtil;

public class MainGameCanvas extends GameCanvas implements Runnable {

	private final static long DELAY = 80;
	public MainMidlet midlet;
	public Thread mainThread = new Thread(this);

	public LayerManager layerManager = new LayerManager();

	public boolean isRunning = false;
	public boolean pausing = false;
	public boolean isWin = false;
	public boolean isleft = true;

	public int level = 1;

	private Image bgImage;
	private Sprite bgGameSprites;

	private Image bgWinImage;
	private Sprite bgWinSprite;

	private Image shipImage;
	Ship ship;

	Submarine[] submarine = new Submarine[6];
	private Image sImage1;
	private Image sImage2;
	private Image sImage3;
	private Image sImage4;

	private Image shuziImage;
	private Sprite[] shuziSprites;

	private Image shuziForWinImage;
	private Vector shuziForWinSprites;

	private Image pauseImage;
	private Sprite pauseSprite;
	private Image startImage;
	private Sprite startSprite;

	private Image bombImage;
	private Bomb[] bombs = new Bomb[5];

	Random rdm = new Random();
	private Image subDdImage;
	private Image subSlImage;

	private EnemyBomb[] eneBombs;

	private int score = 0;
	private int highestScore = 0;

	private int life = 5;

	Sprite scoreSprite;
	Sprite s1Sprite;
	Sprite s2Sprite;
	Sprite s3Sprite;
	Sprite s4Sprite;

	Sprite[] topScoreSprite;

	Sprite levelSprite;

	Sprite lifeSprite;

	protected MainGameCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);

		loadImage();
		initializeGame();
	}

	public void initializeGame() {
		initializeLevel(level);

		mainThread = new Thread(this);
		if (!mainThread.isAlive()) {
			mainThread.start();
		}
	}

	private void initializeLevel(int level) {
		pausing = false;
		isleft = true;
		life = 5;
		score = 0;
		loadwithPosition();
	}

	private void initEnebombs() {
		eneBombs = new EnemyBomb[10];
		for (int i = 0; i < eneBombs.length; i++) {
			if (i % 2 == 0) {
				eneBombs[i] = new EnemyBomb(subDdImage, 11, 34);
			} else {
				eneBombs[i] = new EnemyBomb(subSlImage, 19, 19);
			}
			eneBombs[i].setPosition(-100, -100);
			layerManager.append(eneBombs[i]);
		}
	}

	private void loadImage() {
		try {
			bgImage = Image.createImage("/back.jpg");
			bgWinImage = Image.createImage("/win.jpg");
			shipImage = Image.createImage("/ship.png");
			pauseImage = Image.createImage("/zt.jpg");
			startImage = Image.createImage("/start.png");
			shuziImage = Image.createImage("/sz.png");
			shuziForWinImage = Image.createImage("/sz_win.png");

			bombImage = Image.createImage("/zd.png");

			sImage1 = Image.createImage("/q1.png");
			sImage2 = Image.createImage("/q2.png");
			sImage3 = Image.createImage("/q3.png");
			sImage4 = Image.createImage("/q4.png");

			subDdImage = Image.createImage("/dd.png");
			subSlImage = Image.createImage("/sl.png");

			pauseSprite = new Sprite(pauseImage);
			startSprite = new Sprite(startImage, 69, 74);
			bgGameSprites = new Sprite(bgImage);
			bgWinSprite = new Sprite(bgWinImage);

			ship = new Ship(shipImage, 142, 42);

			shuziSprites = new Sprite[10];
			for (int i = 0; i < 10; i++) {
				shuziSprites[i] = new Sprite(Image.createImage(shuziImage, 13 * i, 0, 13, 19, Sprite.TRANS_NONE));
			}

			initSumarine(0);
			initSumarine(1);
			initSumarine(2);
			initSumarine(3);

			initEnebombs();
			topScoreSprite = new Sprite[5];
			for (int i = 0; i < 5; i++) {
				topScoreSprite[i] = new Sprite(shuziSprites[0]);
				if (i == 0)
					topScoreSprite[i].setPosition(73, 36);
				else {
					topScoreSprite[i].setPosition(-50, -50);
				}

				layerManager.append(topScoreSprite[i]);
			}

			Sprite levelSprite = new Sprite(shuziSprites[1]);
			levelSprite.setPosition(596, 12);
			layerManager.append(levelSprite);

			ship.setPosition(258, 48);

			for (int i = 0; i < submarine.length; i++) {
				submarine[i].setPosition(0 - rdm.nextInt(200), rdm.nextInt(300) + 200);
				layerManager.append(submarine[i]);
			}

			for (int i = 0; i < 5; i++) {
				int x = 261 + 25 * (i + 1);
				int y = 7;
				bombs[i] = new Bomb(bombImage, 20, 21, x, y);
				bombs[i].setPosition(x, y);
				layerManager.append(bombs[i]);
			}

			layerManager.append(ship);
			layerManager.append(bgGameSprites);

			lifeSprite = new Sprite(shuziSprites[life]);
			lifeSprite.setPosition(596, 38);
			layerManager.insert(lifeSprite, 0);

			scoreSprite = new Sprite(shuziSprites[0]);
			s1Sprite = new Sprite(shuziSprites[0]);
			scoreSprite.setPosition(73, 12);
			s1Sprite.setPosition(-100, -120);
			layerManager.insert(scoreSprite, 0);
			layerManager.insert(s1Sprite, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateSore(int score) {
		if (scoreSprite != null)
			layerManager.remove(scoreSprite);
		if (s1Sprite != null)
			layerManager.remove(s1Sprite);
		if (s2Sprite != null)
			layerManager.remove(s2Sprite);
		if (score > 9 && score < 100) {
			String s = String.valueOf(score);
			scoreSprite = new Sprite(shuziSprites[Integer.parseInt(s.substring(0, 1))]);
			s1Sprite = new Sprite(shuziSprites[Integer.parseInt(s.substring(1, 2))]);
			scoreSprite.setPosition(73, 12);
			s1Sprite.setPosition(86, 12);
			layerManager.insert(scoreSprite, 0);
			layerManager.insert(s1Sprite, 0);
		}
		if (score > 99 && score < 1000) {
			String s = String.valueOf(score);
			scoreSprite = new Sprite(shuziSprites[Integer.parseInt(s.substring(0, 1))]);
			s1Sprite = new Sprite(shuziSprites[Integer.parseInt(s.substring(1, 2))]);
			s2Sprite = new Sprite(shuziSprites[Integer.parseInt(s.substring(2, 3))]);
			scoreSprite.setPosition(73, 12);
			s1Sprite.setPosition(86, 12);
			s2Sprite.setPosition(99, 12);
			layerManager.insert(scoreSprite, 0);
			layerManager.insert(s1Sprite, 0);
			layerManager.insert(s2Sprite, 0);
		}

		if (score > highestScore) {
			highestScore = score;
			updateHighScore(highestScore);
		}
	}

	private void updateHighScore(int score) {
		for (int i = 0; i < 5; i++) {
			layerManager.remove(topScoreSprite[i]);
		}

		if (score > 9 && score < 100) {
			String s = String.valueOf(score);
			topScoreSprite[0] = new Sprite(shuziSprites[Integer.parseInt(s.substring(0, 1))]);
			topScoreSprite[1] = new Sprite(shuziSprites[Integer.parseInt(s.substring(1, 2))]);
			topScoreSprite[0].setPosition(73, 36);
			topScoreSprite[1].setPosition(86, 36);
		}
		if (score > 99 && score < 1000) {
			String s = String.valueOf(score);
			scoreSprite = new Sprite(shuziSprites[Integer.parseInt(s.substring(0, 1))]);
			s1Sprite = new Sprite(shuziSprites[Integer.parseInt(s.substring(1, 2))]);
			s2Sprite = new Sprite(shuziSprites[Integer.parseInt(s.substring(2, 3))]);
			scoreSprite.setPosition(73, 12);
			s1Sprite.setPosition(86, 12);
			s2Sprite.setPosition(99, 12);

			topScoreSprite[0] = new Sprite(shuziSprites[Integer.parseInt(s.substring(0, 1))]);
			topScoreSprite[1] = new Sprite(shuziSprites[Integer.parseInt(s.substring(1, 2))]);

			topScoreSprite[2] = new Sprite(shuziSprites[Integer.parseInt(s.substring(2, 3))]);
			topScoreSprite[0].setPosition(73, 36);
			topScoreSprite[1].setPosition(86, 36);
			topScoreSprite[2].setPosition(99, 36);
		}

		for (int i = 0; i < 5; i++) {
			layerManager.insert(topScoreSprite[i], 0);
		}

	}

	private void updateLife(int life) {
		if (life <= 0) {
			showWin();
			return;
		}

		layerManager.remove(lifeSprite);

		lifeSprite = new Sprite(shuziSprites[life]);
		lifeSprite.setPosition(596, 38);
		layerManager.insert(lifeSprite, 0);
	}

	private void showWin() {
		isWin = true;
		layerManager.remove(lifeSprite);
		layerManager.insert(bgWinSprite, 0);
		startSprite.setPosition(379, 395);
		layerManager.insert(startSprite, 0);
		if (shuziForWinSprites != null && !shuziForWinSprites.isEmpty()) {
			for (int i = 0; i < shuziForWinSprites.size(); i++) {
				layerManager.remove((Sprite) shuziForWinSprites.elementAt(i));
			}
		}
		try {
			shuziForWinSprites = NumberImgUtil.updateNumber(score, shuziForWinImage, 320, 235, Graphics.HCENTER | Graphics.TOP);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (shuziForWinSprites != null && !shuziForWinSprites.isEmpty()) {
			for (int i = 0; i < shuziForWinSprites.size(); i++) {
				layerManager.insert((Sprite) shuziForWinSprites.elementAt(i), 0);
			}
		}
		pausing = true;
		isRunning = false;
	}

	private void loadwithPosition() {
		updateLife(life);
		updateSore(score);
		for (int i = 0; i < eneBombs.length; i++) {
			eneBombs[i].setPosition(-100, -100);
		}
		ship.setPosition(258, 48);

		for (int i = 0; i < submarine.length; i++) {
			submarine[i].setPosition(0 - rdm.nextInt(200), rdm.nextInt(300) + 200);
		}

		for (int i = 0; i < 5; i++) {
			int x = 261 + 25 * (i + 1);
			int y = 7;
			bombs[i].setPosition(x, y);
			bombs[i].setFire(false);
		}
	}

	private void initSumarine(int num) {
		if (num == 0) {
			submarine[0] = new Submarine(sImage1, 84, 26);
		}
		if (num == 1) {
			submarine[1] = new Submarine(sImage2, 114, 37);
			submarine[4] = new Submarine(sImage2, 114, 37);
		}

		if (num == 2) {
			submarine[2] = new Submarine(sImage3, 108, 31);
			submarine[5] = new Submarine(sImage3, 108, 31);
		}
		if (num == 3) {
			submarine[3] = new Submarine(sImage4, 152, 32);
		}
	}

	protected void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
		switch (keyCode) {
		case KeyCode.LEFT:
			if (isRunning && !pausing) {
				ship.left();
			} else if (pausing) {
				startSprite.setPosition(264, 289);
				isleft = true;
			}
			break;
		case KeyCode.RIGHT:
			if (isRunning && !pausing) {
				ship.right();
			} else if (pausing) {
				startSprite.setPosition(391, 289);
				isleft = false;
			}
			break;
		case KeyCode.OK:
			if (isWin) {
				try {
					layerManager.remove(bgWinSprite);
					layerManager.remove(startSprite);
					if (shuziForWinSprites != null && !shuziForWinSprites.isEmpty()) {
						for (int i = 0; i < shuziForWinSprites.size(); i++) {
							layerManager.remove((Sprite) shuziForWinSprites.elementAt(i));
						}
					}
					isWin = false;
					backMenu();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (pausing && isleft) {
				pause();
			} else if (pausing && !isleft) {
				pause();
				try {
					backMenu();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (isRunning && !pausing) {
				fireBomb();
			}
			break;

		case KeyCode.MENU:
			break;
		case KeyCode.NUM_0:
			if (pausing)
				return;
			pause();
			break;
		case KeyCode.BACK:
		case KeyCode.BACK_1:
		case KeyCode.BACK_2:
			if (!pausing) {
				try {
					backMenu();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			break;
		}
		drawScreen(getGraphics());
	}

	private void backMenu() throws InterruptedException {
		this.midlet.dis.setCurrent(midlet.menuCanvas);
		midlet.menuCanvas.initMenu();
		this.isRunning = false;
	}

	private void fireBomb() {
		for (int i = 4; i > -1; i--) {
			if (bombs[i].isFire())
				continue;
			bombs[i].setPosition(ship.getX() + 71, ship.getY() + 42);
			bombs[i].setDisplay(true);
			bombs[i].setFire(true);
			break;
		}
	}

	private void pause() {
		pausing = !pausing;
		if (pausing) {
			startSprite.setPosition(264, 289);
			layerManager.insert(startSprite, 0);
			layerManager.insert(pauseSprite, 1);
		} else {
			layerManager.remove(pauseSprite);
			layerManager.remove(startSprite);
		}
	}

	public void run() {
		try {
			Graphics g = getGraphics();
			while (isRunning) {
				long startTime = System.currentTimeMillis();
				if (pausing) {
					drawScreen(g);
					Thread.currentThread();
					Thread.sleep(DELAY);
					continue;
				}

				for (int i = 0; i < eneBombs.length; i++) {
					if (eneBombs[i].isDisplay()) {

						if (eneBombs[i].collidesWith(ship)) {
							eneBombs[i].handlecollidesWith();
							life--;
							updateLife(life);
							Thread.sleep(DELAY * 2);
							ship.setPosition(258, 48);
						}

						eneBombs[i].fire(ship.getHeight());
					}
				}
				int rint = rdm.nextInt(submarine.length);

				for (int i = 0; i < submarine.length; i++) {
					if (!submarine[i].isIsBoom()) {
						submarine[i].move();
					} else {
						submarine[i].boom();
					}

					if (rint == i) {
						if (!eneBombs[i].isDisplay() && !eneBombs[i].isFire())
							eneBombs[i].setPosition(submarine[i].getX() + submarine[i].getImageWidth() / 2, submarine[i].getY()
									+ submarine[i].getImageHeight() / 2);
						eneBombs[i].setDisplay(true);
						eneBombs[i].setFire(true);
					}
				}

				for (int i = 4; i > -1; i--) {
					if (bombs[i].isDisplay() && bombs[i].isFire()) {
						bombs[i].fire();

						for (int j = 0; j < submarine.length; j++) {
							if (bombs[i].collidesWith(submarine[j])) {
								bombs[i].handlecollidesWith(submarine[j]);

								score += 10;
								updateSore(score);

							}
						}
					}
				}

				drawScreen(g);

				long runTime = System.currentTimeMillis() - startTime;

				if (runTime < DELAY) {
					try {
						Thread.sleep(DELAY - runTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void drawScreen(Graphics g) {
		layerManager.paint(g, 0, 0);
		flushGraphics();
	}
}
