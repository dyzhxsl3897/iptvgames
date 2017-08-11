package com.zhongdan.games.goldminer;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

import com.zhongdan.games.framework.utils.Constants;
import com.zhongdan.games.framework.utils.ImageUtil;
import com.zhongdan.games.framework.utils.NumberImgUtil;
import com.zhongdan.games.framework.utils.Constants.KeyCode;

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
	private Image numbersGreenImg;
	private Image numbersOrangeImg;
	private Image numbersWhiteImg;
	private Image backgroundImg;
	private Image timeRunsOutImg;
	private Image nextLevelImg;
	private Image levelClearImg;
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
	private int levelTime[] = { 0, 60, 5 };
	public int level;
	public static int score;
	public static int nowFrame;
	public int nowTime;
	private int count = 0;
	private boolean isLostScreenDrawn = false;
	private boolean isWinScreenDrawn = false;
	private boolean isGameEndScreenDrawn = false;
	public static boolean fire;

	public MainGameCanvas(MainMidlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
		initCanvas();
	}

	private void initCanvas() {
		// Load images
		numbersGreenImg = ImageUtil.createImage("/numbers_green.png");
		numbersWhiteImg = ImageUtil.createImage("/numbers_white.png");
		numbersOrangeImg = ImageUtil.createImage("/numbers_orange.png");
		backgroundImg = ImageUtil.createImage("/background.png");
		timeRunsOutImg = ImageUtil.createImage("/time_runs_out.png");
		nextLevelImg = ImageUtil.createImage("/next_level.png");
		levelClearImg = ImageUtil.createImage("/level_clear.png");
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

	public void initOre(int newLevel) {
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
			allOre[0] = new OreSprite(goldBigImg, 1, 400, 528, 202);
			allOre[1] = new OreSprite(goldBigImg, 1, 400, 83, 296);
			allOre[2] = new OreSprite(goldMidImg, 2, 250, 76, 157);
			allOre[3] = new OreSprite(goldMidImg, 2, 250, 397, 199);
			allOre[4] = new OreSprite(goldSmallImg, 3, 50, 15, 235);
			allOre[5] = new OreSprite(goldSmallImg, 3, 50, 52, 260);
			allOre[6] = new OreSprite(goldSmallImg, 3, 50, 367, 438);
			allOre[7] = new OreSprite(goldSmallImg, 3, 50, 362, 400);
			allOre[8] = new OreSprite(goldSmallImg, 3, 50, 588, 485);
			allOre[9] = new OreSprite(stoneBigImg, 1, 50, 192, 331);
			allOre[10] = new OreSprite(stoneBigImg, 1, 50, 23, 407);
			allOre[11] = new OreSprite(stoneSmallImg, 2, 20, 16, 290);
			allOre[12] = new OreSprite(stoneSmallImg, 2, 20, 497, 409);
			allOre[13] = new OreSprite(coinBagImg, 1, 600, 455, 298);
		}
	}

	protected void keyPressed(int keyCode) {
		if (keyCode == Constants.KeyCode.BACK || keyCode == KeyCode.BACK_1 || keyCode == KeyCode.BACK_2) {
			this.midlet.getDisplay().setCurrent(this.midlet.getMenuCanvas());
		}
	}

	public MainMidlet getMidlet() {
		return midlet;
	}

	public void run() {
		while (true) {
			long currTime = System.currentTimeMillis();
			try {
				game(this.gameState);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
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

	private void game(int gameState) throws Exception {
		switch (gameState) {
		case LEVELINFO:
			break;
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
			if (!isWinScreenDrawn) {
				graphics.drawImage(nextLevelImg, 0, 0, Graphics.TOP | Graphics.LEFT);
				isWinScreenDrawn = true;
			}
			count++;
			System.out.println(count);
			if (count >= 30) {
				count = 0;
				initOre(level + 1);
				this.gameState = GAME;
				isWinScreenDrawn = false;
			}
			break;
		case LOST:
			if (!isLostScreenDrawn) {
				graphics.drawImage(timeRunsOutImg, 0, 0, Graphics.TOP | Graphics.LEFT);
				isLostScreenDrawn = true;
			}
			count++;
			if (count >= 30) {
				count = 0;
				isLostScreenDrawn = false;
				this.gameState = LEVELINFO;
				this.midlet.getDisplay().setCurrent(this.midlet.getMenuCanvas());
			}
			break;
		case GAMEEND:
			if (!isGameEndScreenDrawn) {
				graphics.drawImage(levelClearImg, 0, 0, Graphics.TOP | Graphics.LEFT);
				isGameEndScreenDrawn = true;
			}
			count++;
			if (count >= 30) {
				count = 0;
				isGameEndScreenDrawn = false;
				this.gameState = LEVELINFO;
				this.midlet.getDisplay().setCurrent(this.midlet.getMenuCanvas());
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

	private void drawBack() throws Exception {
		graphics.drawImage(backgroundImg, 0, 0, 0);
		graphics.setColor(0Xffff00);
		NumberImgUtil.updateNumber(graphics, score, numbersOrangeImg, 10, 10, Graphics.TOP | Graphics.LEFT);
		NumberImgUtil.updateNumber(graphics, levelScore[level], numbersOrangeImg, 10, 50, Graphics.TOP | Graphics.LEFT);
		NumberImgUtil.updateNumber(graphics, nowTime, numbersWhiteImg, 620, 10, Graphics.TOP | Graphics.RIGHT);
		NumberImgUtil.updateNumber(graphics, level, numbersGreenImg, 620, 50, Graphics.TOP | Graphics.RIGHT);
		// graphics.drawString(String.valueOf(score), 30, -2, 0);
		// graphics.drawString(String.valueOf(levelScore[level]), 45, 22, 0);
		// graphics.drawString(String.valueOf(nowTime), 220, 0, 0);
		// graphics.drawString(String.valueOf(level), 212, 22, 0);
	}

}
