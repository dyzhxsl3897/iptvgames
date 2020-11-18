package com.game.deepwar;

import java.util.Random;
import java.util.Vector;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.game.sprite.Bomb;
import com.game.sprite.EnemyBomb;
import com.game.sprite.Ship;
import com.game.sprite.Submarine;

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
		// TODO Auto-generated method stub

	}

	private void initEnebombs() {
		// TODO Auto-generated method stub

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
				topScoreSprite[i] = new Sprite(Image.createImage(shuziImage, 0, 0, 13, 19, Sprite.TRANS_NONE));
				if (i == 0)
					topScoreSprite[i].setPosition(73, 36);
				else {
					topScoreSprite[i].setPosition(-50, -50);
				}

				layerManager.append(topScoreSprite[i]);
			}
			/*-

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
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initSumarine(int i) {
		// TODO Auto-generated method stub

	}

	public void run() {
	}

}
