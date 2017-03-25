package com.zhongdan.games.goldminer;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

import com.zhongdan.games.framework.utils.ImageUtil;

public class MainGameCanvas extends GameCanvas implements Runnable {

	private MainMidlet midlet;
	private Graphics graphics = this.getGraphics();
	public final static int LEVELINFO = 0;
	public final static int GAME = 1;
	public final static int WIN = 2;
	public final static int LOST = 3;
	public final static int GAMEEND = 4;
	public final static int PAUSE = 5;
	public int gameState = LEVELINFO;
	private Image backgroundImg;
	private Image workerUpImg;
	private Image workerDownImg;
	private Image[] workerImg;
	private Image hookImg;
	private Image coinBagImg;
	private Image goldBigImg;
	private Image goldMidImg;
	private Image goldSmallImg;
	private Image stoneBigImg;
	private Image stoneSmallImg;
	private HookSprite hookSprite;
	private OreSprite[] allOre;
	private int levelScore[] = { 0, 650, 1000 };
	private int levelTime[] = { 0, 60, 60 };
	private int level;
	public static int score;
	public static int nowFrame;
	public int nowTime;
	private int count = 0;
	public static boolean fire;

	public MainGameCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
		initCanvas();
	}

	private void initCanvas() {
		// Load images
		backgroundImg = ImageUtil.createImage("/background.png");
		hookImg = ImageUtil.createImage("/hook.png");
		coinBagImg = ImageUtil.createImage("/coinbag.png");
		goldBigImg = ImageUtil.createImage("/gold_big.png");
		goldMidImg = ImageUtil.createImage("/gold_mid.png");
		goldSmallImg = ImageUtil.createImage("/gold_small.png");
		workerUpImg = ImageUtil.createImage("/worker_up.png");
		workerDownImg = ImageUtil.createImage("/worker_down.png");
		workerImg = new Image[2];
		workerImg[0] = workerDownImg;
		workerImg[1] = workerUpImg;
		stoneBigImg = ImageUtil.createImage("/stone_big.png");
		stoneSmallImg = ImageUtil.createImage("/stone_small.png");

		// Initialize level
		level = 1;
		initOre(level);

		// Start game
		Thread t = new Thread(this);
		t.start();
	}

	private void initOre(int newLevel) {
		fire = false;
		level = newLevel;
		nowTime = levelTime[level];
		nowFrame = 1;
		hookSprite = new HookSprite(hookImg, 24, 24);
		hookSprite.init();
		if (level == 1) {
			score = 0;
			allOre = new OreSprite[14];
			allOre[0] = new OreSprite(goldBigImg, 1, 400, 143, 198);
			allOre[1] = new OreSprite(goldBigImg, 1, 400, 89, 312);
			allOre[2] = new OreSprite(goldMidImg, 2, 250, 454, 193);
			allOre[3] = new OreSprite(goldMidImg, 2, 250, 416, 409);
			allOre[4] = new OreSprite(goldSmallImg, 3, 50, 197, 161);
			allOre[5] = new OreSprite(goldSmallImg, 3, 50, 371, 293);
			allOre[6] = new OreSprite(goldSmallImg, 3, 50, 414, 345);
			allOre[7] = new OreSprite(goldSmallImg, 3, 50, 519, 327);
			allOre[8] = new OreSprite(goldSmallImg, 3, 50, 577, 264);
			allOre[9] = new OreSprite(stoneBigImg, 1, 50, 44, 153);
			allOre[10] = new OreSprite(stoneBigImg, 1, 50, 40, 237);
			allOre[11] = new OreSprite(stoneSmallImg, 2, 20, 347, 343);
			allOre[12] = new OreSprite(stoneSmallImg, 2, 20, 177, 455);
			allOre[13] = new OreSprite(coinBagImg, 1, 600, 553, 386);

		}
		if (level == 2) {
			allOre = new OreSprite[14];
			allOre[0] = new OreSprite(stoneSmallImg, 2, 20, 25, 105);
			allOre[1] = new OreSprite(stoneBigImg, 1, 50, 110, 125);
			allOre[2] = new OreSprite(stoneBigImg, 1, 50, 145, 85);
			allOre[3] = new OreSprite(stoneSmallImg, 2, 20, 45, 155);
			allOre[4] = new OreSprite(goldSmallImg, 3, 50, 15, 200);
			allOre[5] = new OreSprite(goldMidImg, 2, 250, 185, 185);
			allOre[6] = new OreSprite(goldMidImg, 2, 250, 110, 215);
			allOre[7] = new OreSprite(goldSmallImg, 3, 50, 35, 255);
			allOre[8] = new OreSprite(goldSmallImg, 3, 50, 180, 255);

		}
	}

	protected void keyPressed(int keyCode) {
	}

	public MainMidlet getMidlet() {
		return midlet;
	}

	public void run() {
		while (true) {
			long currTime = System.currentTimeMillis();
			game(this.gameState);
			long lastTime = System.currentTimeMillis();
			long delay = 80 - (lastTime - currTime);
			try {
				if (delay <= 0)
					delay = 1;
				Thread.sleep(delay);
			} catch (Exception exception) {
			}
		}
	}

	private void game(int gameState) {
		switch (gameState) {
		case LEVELINFO:
		case GAME:
			drawBack();
			drawMan();
			drawHook();
			drawOre();
			count++;
			if (count >= 10) {
				count = 0;
				nowTime--;
				if (nowTime <= 0) {
					if (score >= levelScore[level]) {
						if (level >= 2)
							this.gameState = GAMEEND;
						else
							this.gameState = WIN;
					} else
						this.gameState = LOST;
				}
			}
			key();
			hookSprite.upData(allOre);
			break;
		case WIN:
//			drawWin();
			count++;
			if (count >= 30) {
				count = 0;
				initOre(level + 1);
				this.gameState = LEVELINFO;
			}
			break;
		}
		this.flushGraphics();
	}

	private void key() {
		if (!fire) {
			int key = this.getKeyStates();
			if ((key & GameCanvas.FIRE_PRESSED) != 0) {
				fire = true;
				nowFrame = 0;
				hookSprite.hookSpriteState = HookSprite.STRETCHING;
				hookSprite.setNowSpeed(8);
			}
		}
	}

	private void drawOre() {
		for (int i = 0; i < allOre.length; i++) {
			allOre[i].paint(graphics);
		}
	}

	private void drawHook() {
		hookSprite.drawHook(graphics);
	}

	private void drawMan() {
		graphics.drawImage(workerImg[nowFrame], 310, 48, 0);
	}

	private void drawBack() {
		graphics.drawImage(backgroundImg, 0, 0, 0);
		graphics.setColor(0Xffff00);
		graphics.drawString(String.valueOf(score), 30, -2, 0);
		graphics.drawString(String.valueOf(levelScore[level]), 45, 22, 0);
		graphics.drawString(String.valueOf(nowTime), 220, 0, 0);
		graphics.drawString(String.valueOf(level), 212, 22, 0);
	}

}
