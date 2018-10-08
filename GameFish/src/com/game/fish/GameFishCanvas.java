package com.game.fish;

import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.game.fish.Constants.KeyCode;

/**
 * 
 * @author JoYoNB
 * 
 */
public class GameFishCanvas extends GameCanvas implements Runnable {
	private static final int FISHCOUNT = 9;

	private boolean isPlay;
	private boolean isPause;
	private int step;
	private long delay;
	private int currentX, currentY;
	private int width;
	private int height;
	private int startSel;
	private int endSel;
	private Image startImage;
	private Image endImage;
	private Sprite startSprite;
	private Sprite endSprite;
	private Image continueImage;
	private Sprite continueSprite;
	private Image mainBgImage;
	private Image gameBgImage0;
	private Image gameBgImage1;
	private Sprite backgroundSprite0;
	private Sprite backgroundSprite1;
	private Image[] playerFishImage;
	private Fish[] playerFishSprite;
	private Fish[] fishSprites;
	private Image[] fishImages;
	private int score = 0;
	private int level = 1;
	private int life = 3;
	private Image[] fishIcons;
	private Image numberImage;
	private Sprite[] fishIconSprites;
	private Image levelImage;
	private Sprite levelSprite;
	private Sprite[] scoreSprites;
	private Sprite[] lifeSprites;
	private Image lvUpImage;
	private Sprite lvUpSprites;
	private int lifeScore = 0;
	private Random rd = new Random();
	private Fish playerFish;
	private int lvUpScore = 0;
	private int disappearMove = 0;
	private Image upImage;
	private Sprite upSprites;
	private Image pauseBgImage;
	private Sprite pauseBgSprite;
	private Thread t;

	private LayerManager layerManager = new LayerManager();;

	public GameFishCanvas() throws Exception {
		super(false);
		this.setFullScreenMode(true);
		step = 0;
		width = getWidth();
		height = getHeight();

		currentX = width / 2;
		currentY = height / 2;
		delay = 80;
		startSel = 0;
		endSel = 1;

		startImage = Image.createImage("/start.png");
		endImage = Image.createImage("/end.png");
		continueImage = Image.createImage("/continue.png");
		mainBgImage = Image.createImage("/bg_main.jpg");

		gameBgImage0 = Image.createImage("/bg_play_0.jpg");
		gameBgImage1 = Image.createImage("/bg_play_1.png");

		playerFishImage = new Image[3];
		for (int i = 0; i < playerFishImage.length; i++) {
			playerFishImage[i] = Image.createImage("/fish/player_Lv" + (i + 1) + ".png");
		}
		fishImages = new Image[5];
		for (int i = 0; i < fishImages.length; i++) {
			fishImages[i] = Image.createImage("/fish/fish" + (i + 1) + ".png");
		}
		fishIcons = new Image[3];
		fishIcons[0] = Image.createImage("/fish1_true.png");
		fishIcons[1] = Image.createImage("/fish2_true_false.png");
		fishIcons[2] = Image.createImage("/fish3_true_false.png");

		numberImage = Image.createImage("/num.png");
		lvUpImage = Image.createImage("/LvUP.png");
		upImage = Image.createImage("/+1UP.png");

		levelImage = Image.createImage("/Lv.png");
		pauseBgImage = Image.createImage("/bg_pause.png");
		initMainMenu();
	}

	private void initMainMenu() {
		for (int i = layerManager.getSize() - 1; i >= 0; i--) {
			layerManager.remove(layerManager.getLayerAt(i));
		}

		backgroundSprite0 = new Sprite(mainBgImage);
		startSprite = new Sprite(startImage, 260, 133);
		endSprite = new Sprite(endImage, 260, 133);
//		layerManager.append(startSprite);
//		layerManager.append(endSprite);
		layerManager.append(backgroundSprite0);
		startSel = 0;
		endSel = 1;
		step = 0;
		isPause = false;
		startSprite.setFrame(startSel);
		endSprite.setFrame(endSel);
		startSprite.setPosition(currentX - 130, currentY - 100);
		endSprite.setPosition(currentX - 130, currentY + 33);
		pauseBgSprite = new Sprite(pauseBgImage);
		continueSprite = new Sprite(continueImage, 260, 133);

		layerManager.paint(getGraphics(), 0, 0);
		flushGraphics();
	}

	private void initGame() {
		lifeScore = 0;
		lvUpScore = 0;
		score = 0;
		level = 1;
		life = 3;
		layerManager = new LayerManager();
		backgroundSprite0 = new Sprite(gameBgImage0);
		backgroundSprite1 = new Sprite(gameBgImage1);

		playerFishSprite = new Fish[3];
		for (int i = 0; i < playerFishSprite.length; i++) {
			playerFishSprite[i] = new Fish(playerFishImage[i], playerFishImage[i].getWidth() / 9, playerFishImage[i].getHeight(), true);
			playerFishSprite[i].setMoveArea(0, width, 120, height);
		}
		playerFish = playerFishSprite[0];
		playerFish.setPosition(currentX, currentY);
		layerManager.append(playerFish);
		fishSprites = new Fish[FISHCOUNT];
		fishSprites[0] = new Fish(fishImages[0], fishImages[0].getWidth() / 2, fishImages[0].getHeight(), false);
		fishSprites[0].setMoveArea(0, width, 120, height);
		fishSprites[0].goBirthPosition(rd);
		fishSprites[0].setLevel(1);
		layerManager.append(fishSprites[0]);
		for (int i = 1; i < fishSprites.length; i++) {
			if (i <= 3) {
				fishSprites[i] = createRandomFish(i);
			} else {
				fishSprites[i] = createRandomFish(-1);
			}

			layerManager.append(fishSprites[i]);
		}

		levelSprite = new Sprite(levelImage, 85, 36);
		levelSprite.setPosition(810, 15);
		layerManager.append(levelSprite);

		fishIconSprites = new Sprite[3];
		fishIconSprites[0] = new Sprite(fishIcons[0]);
		fishIconSprites[1] = new Sprite(fishIcons[1], 90, 52);
		fishIconSprites[2] = new Sprite(fishIcons[2], 90, 52);
		fishIconSprites[0].setPosition(950, 10);
		fishIconSprites[1].setPosition(1060, 10);
		fishIconSprites[2].setPosition(1170, 10);
		fishIconSprites[1].setFrame(1);
		fishIconSprites[2].setFrame(1);
		layerManager.append(fishIconSprites[0]);
		layerManager.append(fishIconSprites[1]);
		layerManager.append(fishIconSprites[2]);

		lvUpSprites = new Sprite(lvUpImage, 100, 26);
		lvUpSprites.setFrame(1);
		layerManager.append(lvUpSprites);
		upSprites = new Sprite(upImage, 77, 27);
		upSprites.setFrame(1);
		layerManager.append(upSprites);
		scoreSprites = new Sprite[8];
		for (int j = 0; j < scoreSprites.length; j++) {
			scoreSprites[j] = new Sprite(numberImage, 26, 37);
			scoreSprites[j].setPosition(1040 + j * 30, 70);
			layerManager.append(scoreSprites[j]);
		}

		lifeSprites = new Sprite[2];
		lifeSprites[0] = new Sprite(numberImage, 26, 37);
		lifeSprites[0].setPosition(830, 70);
		layerManager.append(lifeSprites[0]);
		lifeSprites[1] = new Sprite(numberImage, 26, 37);
		lifeSprites[1].setPosition(860, 70);
		lifeSprites[1].setFrame(3);
		layerManager.append(lifeSprites[1]);

		layerManager.append(backgroundSprite1);
		layerManager.append(backgroundSprite0);
	}

	public void start() {
		isPlay = true;
		isPause = false;
		t = new Thread(this);
		t.start();
	}

	public void stop() {
		isPlay = false;
	}

	public void goMenu() {
		step = 0;
		initMainMenu();
	}

	public void pause() {
		isPause = !isPause;
		if (isPause) {
			continueSprite.setPosition(currentX - 130, currentY - 100);
			endSprite.setPosition(currentX - 130, currentY + 33);
			layerManager.insert(continueSprite, 1);
			layerManager.insert(endSprite, 2);
			layerManager.insert(pauseBgSprite, 3);
		} else {
			layerManager.remove(continueSprite);
			layerManager.remove(endSprite);
			layerManager.remove(pauseBgSprite);
		}
	}

	public void run() {
		Graphics g = getGraphics();
		while (isPlay) {
			try {
				if (!isPause && step == 1) {
					if (fishSprites != null && fishSprites.length > 0) {
						for (int i = 0; i < fishSprites.length; i++) {
							if (fishSprites[i] != null)
								fishSprites[i].nextFrame();
							if (playerFish.collidesWith(fishSprites[i], false) && playerFish.getAction() != 2) {
								playerFish.setFrameSequence(new int[] { 6, 7, 8 });
								playerFish.setAction(2);
								int fishLevel = fishSprites[i].getLevel();
								if (level >= fishLevel) {
									layerManager.remove(fishSprites[i]);
									fishSprites[i] = createRandomFish(-1);
									fishSprites[i].goBirthPosition(rd);
									layerManager.insert(fishSprites[i], i + 1);
									layerManager.remove(fishSprites[i]);
									layerManager.insert(fishSprites[i], i + 1);
									score += fishLevel * 10;
									lvUpScore += fishLevel * 10;
									int[] bits = getBits(score, 8);
									for (int j = 0; j < scoreSprites.length; j++) {
										scoreSprites[j].setFrame(bits[j]);
									}
									if (lvUpScore >= 500 && lvUpScore < 1200 && level == 1) {
										levelSprite.setFrame(1);
										fishIconSprites[1].setFrame(0);

										playerFishSprite[1].setPosition(playerFish.getX(), playerFish.getY());
										layerManager.remove(playerFishSprite[0]);
										layerManager.insert(playerFishSprite[1], 0);
										playerFish = playerFishSprite[1];
										level = 2;
										lvUpSprites.setPosition(playerFish.getX(), playerFish.getY() - playerFish.getHeight());
										lvUpSprites.setFrame(0);

									} else if (lvUpScore >= 1200 && level == 2) {
										levelSprite.setFrame(2);
										fishIconSprites[2].setFrame(0);

										playerFishSprite[2].setPosition(playerFish.getX(), playerFish.getY());
										layerManager.remove(playerFishSprite[1]);
										layerManager.insert(playerFishSprite[2], 0);
										playerFish = playerFishSprite[2];
										level = 3;
										lvUpSprites.setPosition(playerFish.getX(), playerFish.getY() - playerFish.getHeight());
										lvUpSprites.setFrame(0);
									}
									lifeScore += fishLevel * 10;
									if (lifeScore >= 3000) {
										life += 1;
										lifeScore -= 3000;
										upSprites.setPosition(playerFish.getX(), playerFish.getY() - playerFish.getHeight());
										upSprites.setFrame(0);
										bits = getBits(life, 2);
										for (int j = 0; j < lifeSprites.length; j++) {
											lifeSprites[j].setFrame(bits[j]);
										}
									}
								} else {
									pause();
									step = 0;
									levelSprite.setFrame(0);
									fishIconSprites[1].setFrame(1);
									fishIconSprites[2].setFrame(1);
									playerFishSprite[0].setPosition(currentX, currentY);
									layerManager.remove(layerManager.getLayerAt(0));
									layerManager.insert(playerFishSprite[0], 0);
									playerFish = playerFishSprite[0];
									level = 1;
									lvUpScore = 0;
									life -= 1;
									int[] bits = getBits(life, 2);
									for (int j = 0; j < lifeSprites.length; j++) {
										lifeSprites[j].setFrame(bits[j]);
									}
									if (life <= 0) {
										stop();
										goMenu();
									}
								}
							}
							if (lvUpSprites.getFrame() == 0) {
								lvUpSprites.move(0, -1);
								disappearMove += 1;
								if (disappearMove >= 40) {
									lvUpSprites.setFrame(1);
									disappearMove = 0;
								}
							}
							if (upSprites.getFrame() == 0) {
								upSprites.move(0, -1);
								disappearMove += 1;
								if (disappearMove >= 40) {
									upSprites.setFrame(1);
									disappearMove = 0;
								}
							}
							if ((fishSprites[i].getX() > width + fishSprites[i].getWidth() || fishSprites[i].getX() < 0 - fishSprites[i].getWidth())
									&& i != 1 & i != 2) {
								fishSprites[i] = createRandomFish(-1);
								fishSprites[i].goBirthPosition(rd);
								layerManager.insert(fishSprites[i], i + 1);
							}
						}
					}
					if (playerFish != null)
						playerFish.nextFrame();
				}
				drawScreen(g);
				Thread.sleep(delay);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * menu input
	 */
	private void menuInput(int keyCode) {
		// Up
		if (keyCode == KeyCode.UP) {
			startSel = 0;
			endSel = 1;
			startSprite.setFrame(startSel);
			continueSprite.setFrame(startSel);
			endSprite.setFrame(endSel);
			layerManager.paint(getGraphics(), 0, 0);
			flushGraphics();
		}
		// Down
		if (keyCode == KeyCode.DOWN) {
			startSel = 1;
			endSel = 0;
			startSprite.setFrame(startSel);
			continueSprite.setFrame(startSel);
			endSprite.setFrame(endSel);
			layerManager.paint(getGraphics(), 0, 0);
			flushGraphics();
		}

		// Enter
		if (keyCode == KeyCode.OK) {
			if (isPause) {
				if (endSprite.getFrame() == 0) {
					GameFishMidlet.instance.exit();
				} else {
					pause();
					layerManager.paint(getGraphics(), 0, 0);
					flushGraphics();
					step = 1;
				}
			} else if (startSprite.getFrame() == 0) {
				step = 1;
				initGame();
				start();
			} else {
				GameFishMidlet.instance.exit();
			}
		}
	}

	/**
	 * Game input
	 */
	private void playInput(int keyCode) {
		// Up
		if (keyCode == KeyCode.UP) {
			playerFish.up();
		}
		// Down
		if (keyCode == KeyCode.DOWN) {
			playerFish.down();
		}
		// Left
		if (keyCode == KeyCode.LEFT) {
			playerFish.left();
		}
		// Right
		if (keyCode == KeyCode.RIGHT) {
			playerFish.right();
		}
		// Enter
		if (keyCode == KeyCode.OK) {
			pause();
			step = 0;
		}
		// Back
		if (keyCode == KeyCode.BACK || keyCode == KeyCode.BACK_1 || keyCode == KeyCode.BACK_2) {
			goMenu();
		}
	}

	private void drawScreen(Graphics g) {
		layerManager.paint(g, 0, 0);
		flushGraphics();
	}

	protected void keyPressed(int keyCode) {
		super.keyPressed(keyCode);
		if (step == 0) {
			menuInput(keyCode);
		} else if (step == 1) {
			playInput(keyCode);
		}
	}

	protected void keyRepeated(int keyCode) {
		super.keyRepeated(keyCode);
		if (step == 0) {
			menuInput(keyCode);
		} else if (step == 1) {
			playInput(keyCode);
		}
	}

	private Fish createRandomFish(int fishIdx) {
		if (fishIdx < 0) {
			fishIdx = rd.nextInt(level + 2);
		}
		if (fishIdx == 0) {
			Fish fishSp = new Fish(fishImages[fishIdx], fishImages[fishIdx].getWidth() / 2, fishImages[fishIdx].getHeight(), false);
			fishSp.setMoveArea(0, width, 120, height - fishImages[fishIdx].getHeight());
			fishSp.setSpeed(fishImages[fishIdx].getWidth() / 50 + rd.nextInt(4) * fishImages[fishIdx].getWidth() / 60);
			fishSp.goBirthPosition(rd);
			fishSp.setLevel(fishIdx + 1);
			return fishSp;
		}
		Fish fishSp = new Fish(fishImages[fishIdx], fishImages[fishIdx].getWidth() / 3, fishImages[fishIdx].getHeight(), false);
		fishSp.setMoveArea(0, width, 120, height - fishImages[fishIdx].getHeight());
		fishSp.setSpeed(fishImages[fishIdx].getWidth() / 50 + rd.nextInt(4) * fishImages[fishIdx].getWidth() / 60);
		fishSp.goBirthPosition(rd);
		fishSp.setLevel(fishIdx + 1);
		return fishSp;
	}

	private int[] getBits(int score, int n) {
		int[] bits = new int[n];
		for (int i = 0; i < bits.length; i++) {
			bits[n - 1 - i] = score % 10;
			score = score / 10;
		}
		return bits;
	}
}