package com.company.iptvgames.RunCool.canvas;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.company.iptvgames.RunCool.GameConst;
import com.company.iptvgames.RunCool.MainMIDlet;
import com.company.iptvgames.RunCool.Role.PlayerObject;
import com.company.iptvgames.RunCool.canvas.states.GCDeadState;
import com.company.iptvgames.RunCool.canvas.states.GCPauseState;
import com.company.iptvgames.RunCool.canvas.states.GCPlayState;
import com.company.iptvgames.RunCool.canvas.states.GCState;
import com.company.iptvgames.RunCool.obstacles.ObstacleObject;
import com.company.iptvgames.RunCool.resources.ImageRes;
import com.company.iptvgames.framework.utils.ImageUtil;
import com.company.iptvgames.framework.utils.NumberImgUtil;
import com.company.iptvgames.framework.utils.RandomUtil;

public class MainGameCanvas extends GameCanvas implements Runnable {

	private Graphics graphics;
	private MainMIDlet midlet;
	private LayerManager layerManager;

	private PlayerObject player;
	private Vector BGVector;
	private Vector ObstacleVector;
	private ObstacleObject Obstacle1;
	private Sprite BGObject;
	private int BgNumber = 0;
	private int ObstacleNumber = 0;
	private int currentLevel = 0;
	private int distance = 0;// 玩家当前移动总距离

	private boolean isStayOnGameCanvas = false;
	private Thread gameCanvasThread;
	private Vector numberSprite;
	private Vector lifeSprite;
	private Sprite lifeBackground;
	private GCState state;

	private Sprite alertFailSprite1;
	private Sprite alertFailSprite2;
	private Sprite alertFailSprite3;
	private Sprite alertPauseSprite;
	private Sprite continueSprite;
	private Sprite overSprite;
	private GCPlayState playState;
	private GCPauseState pauseState;
	private GCDeadState deadState;

	public MainGameCanvas(MainMIDlet mainMIDlet) {
		super(false);
		this.setFullScreenMode(true);
		this.graphics = getGraphics();
		this.midlet = mainMIDlet;

		loadImages();

		playState = new GCPlayState(this);
		pauseState = new GCPauseState(this);
		deadState = new GCDeadState(this);

		updateState(playState);
		;
	}

	private void loadImages() {
		ImageRes.getInstance().loadImage("bgImg0", ImageUtil.createImage("/map/b0.jpg"));
		ImageRes.getInstance().loadImage("bgImg1", ImageUtil.createImage("/map/b1.png"));

		ImageRes.getInstance().loadImage("pbRunImg", ImageUtil.createImage("/role/run.png"));
		ImageRes.getInstance().loadImage("pbJumpImg", ImageUtil.createImage("/role/jumpsmall.png"));

		ImageRes.getInstance().loadImage("ObstacleImg1", ImageUtil.createImage("/obstacle/1-1.png"));
		ImageRes.getInstance().loadImage("ObstacleImg2", ImageUtil.createImage("/obstacle/2-1.png"));
		ImageRes.getInstance().loadImage("ObstacleImg3", ImageUtil.createImage("/obstacle/3-1.png"));
		ImageRes.getInstance().loadImage("ObstacleImg4", ImageUtil.createImage("/obstacle/4-1.png"));
		ImageRes.getInstance().loadImage("ObstacleImg5", ImageUtil.createImage("/obstacle/5-1.png"));
		ImageRes.getInstance().loadImage("ObstacleImg6", ImageUtil.createImage("/obstacle/6-1.png"));
		ImageRes.getInstance().loadImage("ObstacleImg7", ImageUtil.createImage("/obstacle/7-1.png"));
		ImageRes.getInstance().loadImage("ObstacleImg0", ImageUtil.createImage("/obstacle/8-1.png"));

		ImageRes.getInstance().loadImage("numbersImg", ImageUtil.createImage("/other/numbers.png"));
		ImageRes.getInstance().loadImage("lifeImg", ImageUtil.createImage("/other/life.png"));

		ImageRes.getInstance().loadImage("alertPauseImg", ImageUtil.createImage("/other/pause.png"));
		ImageRes.getInstance().loadImage("alertFailImg1", ImageUtil.createImage("/other/fail1.png"));
		ImageRes.getInstance().loadImage("alertFailImg2", ImageUtil.createImage("/other/fail2.png"));
		ImageRes.getInstance().loadImage("alertFailImg3", ImageUtil.createImage("/other/fail3.png"));
		ImageRes.getInstance().loadImage("continueImg", ImageUtil.createImage("/other/continue.png"));
		ImageRes.getInstance().loadImage("overImg", ImageUtil.createImage("/other/over.png"));
	}

	public void initalizeGame() {
		distance = 0;

		layerManager = new LayerManager();

		// 创建提示信息
		alertFailSprite3 = new Sprite(ImageRes.getInstance().getImage("alertFailImg3"));
		alertFailSprite2 = new Sprite(ImageRes.getInstance().getImage("alertFailImg2"));
		alertFailSprite1 = new Sprite(ImageRes.getInstance().getImage("alertFailImg1"));
		alertPauseSprite = new Sprite(ImageRes.getInstance().getImage("alertPauseImg"));
		continueSprite = new Sprite(ImageRes.getInstance().getImage("continueImg"));
		overSprite = new Sprite(ImageRes.getInstance().getImage("overImg"));

		layerManager.append(alertFailSprite1);
		layerManager.append(alertFailSprite2);
		layerManager.append(alertFailSprite3);
		layerManager.append(alertPauseSprite);
		layerManager.insert(continueSprite, GameConst.GameCanvas.LAYER_alert);
		layerManager.insert(overSprite, GameConst.GameCanvas.LAYER_alert);

		alertFailSprite1.setVisible(false);
		alertFailSprite2.setVisible(false);
		alertFailSprite3.setVisible(false);
		alertPauseSprite.setVisible(false);
		continueSprite.setVisible(false);
		overSprite.setVisible(false);

		// 创建玩家角色
		player = new PlayerObject();
		player.addToScreen(layerManager);

		// 创建Obstacle
		ObstacleVector = new Vector();
		for (int i = 0; i < GameConst.Obstacle.NUMBER; i++) {
			currentLevel = RandomUtil.nextInt(2);// 背景随机
			createNew("OBSTACLE");
		}

		// 创建生命值背景
		lifeBackground = new Sprite(ImageRes.getInstance().getImage("lifeImg"));
		lifeBackground.setPosition(GameConst.GameCanvas.LIFE_BG_X, GameConst.GameCanvas.LIFE_BG_Y);
		layerManager.append(lifeBackground);

		// 创建背景
		BGVector = new Vector();
		for (int i = 0; i < GameConst.GameCanvas.BGNUMBER; i++) {
			createNew("BG");
		}

		updateScoreAndLife();

		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();

		isStayOnGameCanvas = true;

	}

	public void updateStateToPlay() {
		// TODO Auto-generated method stub
		this.updateState(this.playState);
	}

	public void updateStateToPause() {
		// TODO Auto-generated method stub
		this.updateState(this.pauseState);
	}

	public void updateStateToDead() {
		// TODO Auto-generated method stub
		this.updateState(this.deadState);
		this.layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
		this.state.setAlert(true);
	}

	private void updateState(GCState newState) {
		if (this.state != null) {
			this.state.exitState();
		}
		this.state = newState;
		this.state.intoState();
	}

	public void startGameCanvas() {
		gameCanvasThread = new Thread(this);
		gameCanvasThread.start();
	}

	public void turnOffGameCanvas() {
		this.state.exitState();
		this.isStayOnGameCanvas = false;
	}

	public void run() {
		// TODO Auto-generated method stub
		while (isStayOnGameCanvas) {
			long startTime = System.currentTimeMillis();
			if (isInPlayState()) {
				animation();
				if (player.getSuperTimeLeft() == 0) {// 角色丢失生命后闪现期间不计算碰撞，0值表示非闪现状态
					checkCollides();
				}
				updateScoreAndLife();

				if (player.getLife() == 0) {
					// 更新玩家状态为死亡，更新游戏状态为结束
					updateStateToDead();
				}
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

	private boolean isInPauseState() {
		// TODO Auto-generated method stub
		return this.getState() instanceof GCPauseState;
	}

	private boolean isInDeadState() {
		// TODO Auto-generated method stub
		return this.getState() instanceof GCDeadState;
	}

	private boolean isInPlayState() {
		// TODO Auto-generated method stub
		return this.getState() instanceof GCPlayState;
	}

	public GCState getState() {
		return state;
	}

	public GCPlayState getPlayState() {
		return playState;
	}

	private void createNew(String type) {
		// TODO Auto-generated method stub
		int tempX = 0;
		int tempY = GameConst.Player.POS_Y;
		int num = 0;
		int rand = 0;

		// 创建背景
		if (type.equalsIgnoreCase("BG")) {
			if (BGVector.size() < 2) {
				BGObject = new Sprite(ImageRes.getInstance().getImage("bgImg" + currentLevel));
				BGVector.addElement(BGObject);
				BgNumber++;
				layerManager.append(BGObject);

				num = BGVector.size();
				if (num > 1) {
					tempX = ((Sprite) BGVector.elementAt(num - 2)).getX();
					((Sprite) BGVector.elementAt(num - 1)).setPosition(tempX + GameConst.GameCanvas.BGWIDTH, 0);
				} else {
					((Sprite) BGVector.elementAt(0)).setPosition(0, 0);
				}
			} else {
				int temp0x = ((Sprite) BGVector.elementAt(0)).getX();
				int temp1x = ((Sprite) BGVector.elementAt(1)).getX();
				if (temp0x < temp1x) {
					((Sprite) BGVector.elementAt(0)).setPosition(tempX + GameConst.GameCanvas.BGWIDTH, 0);
				} else {
					((Sprite) BGVector.elementAt(1)).setPosition(tempX + GameConst.GameCanvas.BGWIDTH, 0);
				}
			}
		} else if (type.equalsIgnoreCase("OBSTACLE")) {// 创建障碍物
			Obstacle1 = new ObstacleObject();
			ObstacleVector.addElement(Obstacle1);
			ObstacleNumber++;
			Obstacle1.addToScreen(layerManager);

			num = ObstacleVector.size();
			rand = GameConst.Obstacle.DISTANCE_MIN + RandomUtil.nextInt(GameConst.Obstacle.DISTANCE_RAND) * GameConst.GameCanvas.RUNSPEED;// 障碍物间距随机
			if (num > 1) {
				tempX = ((ObstacleObject) ObstacleVector.elementAt(num - 2)).getX();
				int previousWidth = ((ObstacleObject) ObstacleVector.elementAt(num - 2)).getWidth();
				((ObstacleObject) ObstacleVector.elementAt(num - 1)).setPosition(tempX + rand + previousWidth, tempY + GameConst.Player.WALKHEIGHT
						- ((ObstacleObject) ObstacleVector.elementAt(num - 1)).getHeight());
			} else {
				((ObstacleObject) ObstacleVector.elementAt(0)).setPosition(rand, tempY + GameConst.Player.WALKHEIGHT
						- ((ObstacleObject) ObstacleVector.elementAt(0)).getHeight());
			}
		}
	}

	private void animation() {
		int tempX;
		int tempY;

		// 移动背景
		for (int i = 0; i < BGVector.size(); i++) {
			tempX = ((Sprite) BGVector.elementAt(i)).getX();
			tempY = ((Sprite) BGVector.elementAt(i)).getY();
			((Sprite) BGVector.elementAt(i)).setPosition(tempX - GameConst.GameCanvas.RUNSPEED, tempY);

			// 移除出界背景，补充更新背景
			if (tempX - GameConst.GameCanvas.RUNSPEED + GameConst.GameCanvas.BGWIDTH <= 0) {
				// layerManager.remove(((Sprite)BGVector.elementAt(i)));
				// BGVector.removeElementAt(i);
				createNew("BG");
			}
		}

		// 移动障碍物
		for (int i = 0; i < ObstacleVector.size(); i++) {
			tempX = ((ObstacleObject) ObstacleVector.elementAt(i)).getX();
			((ObstacleObject) ObstacleVector.elementAt(i)).move(-GameConst.GameCanvas.RUNSPEED, 0);

			// 移除出界障碍物，补充更新障碍物
			if (tempX + ((ObstacleObject) ObstacleVector.elementAt(i)).getWidth() - GameConst.GameCanvas.RUNSPEED <= 0) {
				((ObstacleObject) ObstacleVector.elementAt(i)).removeFromScreen(layerManager);
				ObstacleVector.removeElementAt(i);
				createNew("OBSTACLE");
			}
		}

		player.nextFrame();

		distance++;
	}

	private void checkCollides() {
		// TODO Auto-generated method stub

		for (int i = 0; i < ObstacleVector.size(); i++) {
			if (!((ObstacleObject) ObstacleVector.elementAt(i)).getFinish()) {
				if (player.isInWalkState()) {
					if (((ObstacleObject) ObstacleVector.elementAt(i)).getObstacle().collidesWith(player.getWalkSprite(), true)) {
						((ObstacleObject) ObstacleVector.elementAt(i)).setFinish(true);// 设置障碍物属性，避免多次计算碰撞
						player.setLife(-1);
					}
				} else {
					if (((ObstacleObject) ObstacleVector.elementAt(i)).getObstacle().collidesWith(player.getJumpSprite(), true)) {
						((ObstacleObject) ObstacleVector.elementAt(i)).setFinish(true);// 设置障碍物属性，避免多次计算碰撞
						player.setLife(-1);
					}
				}
			}
		}
	}

	private void updateScoreAndLife() {
		int life = player.getLife();

		// 显示生命值
		if (null != lifeSprite && 0 < lifeSprite.size()) {
			for (int i = 0; i < lifeSprite.size(); i++) {
				layerManager.remove((Sprite) lifeSprite.elementAt(i));
			}
		}

		lifeSprite = NumberImgUtil.updateNumber(life, ImageRes.getInstance().getImage("numbersImg"), GameConst.GameCanvas.LIFE_X,
				GameConst.GameCanvas.LIFE_Y, Graphics.TOP | Graphics.HCENTER);

		for (int i = 0; i < lifeSprite.size(); i++) {
			layerManager.insert((Sprite) lifeSprite.elementAt(i), GameConst.GameCanvas.LAYER_bg - 1);
		}

		// 显示分数
		if (null != numberSprite && 0 < numberSprite.size()) {
			for (int i = 0; i < numberSprite.size(); i++) {
				layerManager.remove((Sprite) numberSprite.elementAt(i));
			}
		}

		numberSprite = NumberImgUtil.updateNumber(distance / 10, ImageRes.getInstance().getImage("numbersImg"), GameConst.GameCanvas.SCORE_X,
				GameConst.GameCanvas.SCORE_Y, Graphics.TOP | Graphics.HCENTER);

		for (int i = 0; i < numberSprite.size(); i++) {
			layerManager.insert((Sprite) numberSprite.elementAt(i), GameConst.GameCanvas.LAYER_bg);
		}
	}

	public Sprite getFailAlertSprite() {
		// TODO Auto-generated method stub
		if (distance >= 50) {
			return alertFailSprite1;
		} else if (distance >= 20) {
			return alertFailSprite2;
		} else
			return alertFailSprite3;
	}

	public Sprite getPauseAlertSprite() {
		// TODO Auto-generated method stub
		return alertPauseSprite;
	}

	public Sprite getContinueSprite() {
		// TODO Auto-generated method stub
		return continueSprite;
	}

	public Sprite getOverSprite() {
		// TODO Auto-generated method stub
		return overSprite;
	}

	public MainMIDlet getMidlet() {
		return midlet;
	}

	public PlayerObject getRole() {
		return player;
	}

	protected void keyPressed(int keyCode) {
		this.state.keyAction(keyCode);
	}

	// 角色生命值为0，开始退出游戏画面
	public void startToDrop() {
		int dropheight = GameConst.SCREEN_HEIGHT - player.getY();

		while (dropheight >= 0) {
			player.drop();
			layerManager.paint(graphics, 0, 0);
			this.flushGraphics();
			dropheight = dropheight - GameConst.Player.DROPSPEED;
		}
	}

}
