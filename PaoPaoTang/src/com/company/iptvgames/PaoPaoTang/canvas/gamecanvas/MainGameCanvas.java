package com.company.iptvgames.PaoPaoTang.canvas.gamecanvas;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.company.iptvgames.PaoPaoTang.GameConst;
import com.company.iptvgames.PaoPaoTang.MainMIDlet;
import com.company.iptvgames.PaoPaoTang.Role.PlayerObject;
import com.company.iptvgames.PaoPaoTang.bomb.BombObject;
import com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.states.GCDeadState;
import com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.states.GCPassState;
import com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.states.GCPauseState;
import com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.states.GCPlayState;
import com.company.iptvgames.PaoPaoTang.canvas.gamecanvas.states.GCState;
import com.company.iptvgames.PaoPaoTang.resources.ImageRes;
import com.company.iptvgames.framework.utils.ImageUtil;
import com.company.iptvgames.framework.utils.RandomUtil;

public class MainGameCanvas extends GameCanvas implements Runnable {
	private MainMIDlet midlet;
	private Graphics graphics;

	private GCState state;
	private GCPlayState playState;
	private GCPauseState pauseState;
	private GCDeadState deadState;
	private GCPassState passState;

	private int tiledForBuilds[][];// 存储地图tile的建筑ID值
	private int tiledForTools[][];// 存储地图tile的特效工具ID值
	private int tiledForBombs[][];// 存储地图tile的炸弹ID值

	private LayerManager layerManager;
	private TiledLayer bg1Layer;
	private TiledLayer[] mapLayer;

	private Sprite alertSprite;
	private Sprite continueSprite;
	private Sprite overSprite;

	private Sprite passAlertSprite;
	private Sprite passReturnSprite;

	private Sprite failAlertSprite;
	private Sprite failReturnSprite;

	private PlayerObject player, NPC;
	private Vector NPCsVector;
	private BombObject newBomb;
	private Vector BombVector;

	private boolean isStayOnGameCanvas;
	private Thread gameCanvasThread;

	public MainGameCanvas(MainMIDlet midlet) {
		super(false);
		this.setFullScreenMode(true);
		this.graphics = getGraphics();
		this.midlet = midlet;

		loadImages();

		playState = new GCPlayState(this);
		pauseState = new GCPauseState(this);
		deadState = new GCDeadState(this);
		passState = new GCPassState(this);
		updateState(playState);
	}

	public void startGameCanvas() {
		gameCanvasThread = new Thread(this);
		gameCanvasThread.start();
	}

	public void turnOffGameCanvas() {
		this.state.exitState();
		this.isStayOnGameCanvas = false;
	}

	public void initalizeGame() {
		layerManager = new LayerManager();
		tiledForBuilds = new int[GameConst.GameCanvas.TILES_IN_HEIGHT][GameConst.GameCanvas.TILES_IN_WIDTH];
		tiledForTools = new int[GameConst.GameCanvas.TILES_IN_HEIGHT][GameConst.GameCanvas.TILES_IN_WIDTH];
		tiledForBombs = new int[GameConst.GameCanvas.TILES_IN_HEIGHT][GameConst.GameCanvas.TILES_IN_WIDTH];
		BombVector = new Vector();
		// 创建玩家角色
		player = new PlayerObject("pb");
		player.setSpeed(2);
		player.setSuperTimeLeft();

		// 创建NPC角色
		NPCsVector = new Vector();
		for (int i = 0; i < GameConst.NPC.NUMBER; i++) {
			NPC = new PlayerObject("NPC" + i % 3);
			NPCsVector.addElement(NPC);
		}

		drawMap();

		Image alertImg = ImageRes.getInstance().getImage("alertImg");
		Image continueImg = ImageRes.getInstance().getImage("continueImg");
		Image overImg = ImageRes.getInstance().getImage("overImg");

		alertSprite = new Sprite(alertImg);
		continueSprite = new Sprite(continueImg);
		overSprite = new Sprite(overImg);

		layerManager.insert(alertSprite, GameConst.GameCanvas.LAYER_0);
		layerManager.insert(continueSprite, GameConst.GameCanvas.LAYER_0);
		layerManager.insert(overSprite, GameConst.GameCanvas.LAYER_0);
		continueSprite.setPosition(GameConst.Pause.CONTINUE_X, GameConst.Pause.CONTINUE_Y);
		overSprite.setPosition(GameConst.Pause.OVER_X, GameConst.Pause.OVER_Y);

		alertSprite.setVisible(false);
		continueSprite.setVisible(false);
		overSprite.setVisible(false);

		Image passAlertImg = ImageRes.getInstance().getImage("passAlertImg");
		Image failAlertImg = ImageRes.getInstance().getImage("failAlertImg");
		Image passReturnImg = ImageRes.getInstance().getImage("passReturnImg");
		Image failReturnImg = ImageRes.getInstance().getImage("failReturnImg");

		passAlertSprite = new Sprite(passAlertImg);
		failAlertSprite = new Sprite(failAlertImg);
		passReturnSprite = new Sprite(passReturnImg);
		failReturnSprite = new Sprite(failReturnImg);
		layerManager.insert(passAlertSprite, GameConst.GameCanvas.LAYER_0);
		layerManager.insert(passReturnSprite, GameConst.GameCanvas.LAYER_0);
		passReturnSprite.setPosition(GameConst.Pause.RETURN_X, GameConst.Pause.RETURN_Y);
		layerManager.insert(failAlertSprite, GameConst.GameCanvas.LAYER_0);
		layerManager.insert(failReturnSprite, GameConst.GameCanvas.LAYER_0);
		failReturnSprite.setPosition(GameConst.Pause.RETURN_X, GameConst.Pause.RETURN_Y);

		passAlertSprite.setVisible(false);
		failAlertSprite.setVisible(false);
		passReturnSprite.setVisible(false);
		failReturnSprite.setVisible(false);

		layerManager.paint(graphics, 0, 0);
		isStayOnGameCanvas = true;

	}

	private void loadImages() {
		ImageRes.getInstance().loadImage("bg1Img", ImageUtil.createImage("/bj1.jpg"));
		ImageRes.getInstance().loadImage("mapImg", ImageUtil.createImage("/map.png"));

		ImageRes.getInstance().loadImage("pbUpImg", ImageUtil.createImage("/role/bu.png"));
		ImageRes.getInstance().loadImage("pbDownImg", ImageUtil.createImage("/role/bd.png"));
		ImageRes.getInstance().loadImage("pbLeftImg", ImageUtil.createImage("/role/bl.png"));
		ImageRes.getInstance().loadImage("pbRightImg", ImageUtil.createImage("/role/br.png"));

		ImageRes.getInstance().loadImage("prUpImg", ImageUtil.createImage("/role/ru.png"));
		ImageRes.getInstance().loadImage("prDownImg", ImageUtil.createImage("/role/rd.png"));
		ImageRes.getInstance().loadImage("prLeftImg", ImageUtil.createImage("/role/rl.png"));
		ImageRes.getInstance().loadImage("prRightImg", ImageUtil.createImage("/role/rr.png"));

		ImageRes.getInstance().loadImage("NPC0UpImg", ImageUtil.createImage("/NPC/1u.png"));
		ImageRes.getInstance().loadImage("NPC0DownImg", ImageUtil.createImage("/NPC/1d.png"));
		ImageRes.getInstance().loadImage("NPC0LeftImg", ImageUtil.createImage("/NPC/1l.png"));
		ImageRes.getInstance().loadImage("NPC0RightImg", ImageUtil.createImage("/NPC/1r.png"));

		ImageRes.getInstance().loadImage("NPC1UpImg", ImageUtil.createImage("/NPC/2u.png"));
		ImageRes.getInstance().loadImage("NPC1DownImg", ImageUtil.createImage("/NPC/2d.png"));
		ImageRes.getInstance().loadImage("NPC1LeftImg", ImageUtil.createImage("/NPC/2l.png"));
		ImageRes.getInstance().loadImage("NPC1RightImg", ImageUtil.createImage("/NPC/2r.png"));

		ImageRes.getInstance().loadImage("NPC2UpImg", ImageUtil.createImage("/NPC/3u.png"));
		ImageRes.getInstance().loadImage("NPC2DownImg", ImageUtil.createImage("/NPC/3d.png"));
		ImageRes.getInstance().loadImage("NPC2LeftImg", ImageUtil.createImage("/NPC/3l.png"));
		ImageRes.getInstance().loadImage("NPC2RightImg", ImageUtil.createImage("/NPC/3r.png"));

		ImageRes.getInstance().loadImage("bombImg", ImageUtil.createImage("/bomb/bomb.png"));
		ImageRes.getInstance().loadImage("bombingImg", ImageUtil.createImage("/bomb/bombing.png"));
		ImageRes.getInstance().loadImage("bombUpImg", ImageUtil.createImage("/bomb/bombup.png"));
		ImageRes.getInstance().loadImage("bombDownImg", ImageUtil.createImage("/bomb/bombdown.png"));
		ImageRes.getInstance().loadImage("bombLeftImg", ImageUtil.createImage("/bomb/bombleft.png"));
		ImageRes.getInstance().loadImage("bombRightImg", ImageUtil.createImage("/bomb/bombright.png"));
		ImageRes.getInstance().loadImage("edgeUpImg", ImageUtil.createImage("/bomb/edgeup.png"));
		ImageRes.getInstance().loadImage("edgeDownImg", ImageUtil.createImage("/bomb/edgedown.png"));
		ImageRes.getInstance().loadImage("edgeLeftImg", ImageUtil.createImage("/bomb/edgeleft.png"));
		ImageRes.getInstance().loadImage("edgeRightImg", ImageUtil.createImage("/bomb/edgeright.png"));

		ImageRes.getInstance().loadImage("alertImg", ImageUtil.createImage("/alert.png"));
		ImageRes.getInstance().loadImage("overImg", ImageUtil.createImage("/over.png"));
		ImageRes.getInstance().loadImage("continueImg", ImageUtil.createImage("/continue.png"));

		ImageRes.getInstance().loadImage("passAlertImg", ImageUtil.createImage("/menu/passalert.png"));
		ImageRes.getInstance().loadImage("passReturnImg", ImageUtil.createImage("/menu/passreturn.png"));

		ImageRes.getInstance().loadImage("failAlertImg", ImageUtil.createImage("/menu/failalert.png"));
		ImageRes.getInstance().loadImage("failReturnImg", ImageUtil.createImage("/menu/failreturn.png"));
	}

	protected void keyPressed(int keyCode) {
		this.state.keyAction(keyCode);
	}

	public void run() {
		int times = 0;

		while (isStayOnGameCanvas) {
			long startTime = System.currentTimeMillis();

			if (isInPlayState()) {
				// 炸弹
				animation();

				// 玩家
				if ((times % player.getSpeed()) == 0) {
					// keyAction();
					checkCollidesWithTools(player);
				}

				if ((times % GameConst.Player.SPEED) == 0) {
					// NPC
					for (int i = 0; i < NPCsVector.size(); i++) {
						if (GameConst.NPC.ALLOW_GET_TOOL) {
							checkCollidesWithTools(((PlayerObject) NPCsVector.elementAt(i)));
						}
						nextAction(((PlayerObject) NPCsVector.elementAt(i)));
					}
				}

				// } else if (isInDeadState()) {
				// keyAction();
				// } else if (isInPauseState()) {
				// keyAction();
				// } else if (isInPassState()) {
				// keyAction();
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

			times++;
		}
	}

	private void animation() {
		if (BombVector != null) {
			for (int i = BombVector.size() - 1; i >= 0; i--) {
				int state = ((BombObject) BombVector.elementAt(i)).checkStates();
				if (state == 2) {
					checkCollidesWithBombArea((BombObject) BombVector.elementAt(i));
					((BombObject) BombVector.elementAt(i)).addExploredEffect(layerManager);
				}

				if (state == 0) {
					((BombObject) BombVector.elementAt(i)).removeFromScreen(layerManager);
					((BombObject) BombVector.elementAt(i)).removeExploredEffect(layerManager);
					BombVector.removeElementAt(i);
				}
			}
		}

		player.nextFrame();

		for (int i = 0; i < NPCsVector.size(); i++) {
			((PlayerObject) NPCsVector.elementAt(i)).nextFrame();
		}
	}

	public MainMIDlet getMidlet() {
		return midlet;
	}

	public boolean isInPlayState() {
		return this.getState() instanceof GCPlayState;
	}

	public boolean isInPauseState() {
		return this.getState() instanceof GCPauseState;
	}

	public boolean isInDeadState() {
		return this.getState() instanceof GCDeadState;
	}

	public boolean isInPassState() {
		return this.getState() instanceof GCPassState;
	}

	public void updateStateToPlay() {
		this.updateState(this.playState);
	}

	public void updateStateToDead() {
		this.updateState(this.deadState);
	}

	public void updateStateToPass() {
		this.updateState(this.passState);
	}

	public void updateStateToPause() {
		this.updateState(this.pauseState);
	}

	private void updateState(GCState newState) {
		if (this.state != null) {
			this.state.exitState();
		}
		this.state = newState;
		this.state.intoState();
	}

	public GCState getState() {
		return state;
	}

	public GCPlayState getPlayState() {
		return playState;
	}

	public GCPauseState getPauseState() {
		return pauseState;
	}

	public PlayerObject getRole() {
		return player;
	}

	public Sprite getAlertSprite() {
		return alertSprite;
	}

	public Sprite getContinueSprite() {
		return continueSprite;
	}

	public Sprite getOverSprite() {
		return overSprite;
	}

	public Sprite getPassAlertSprite() {
		return passAlertSprite;
	}

	public Sprite getFailAlertSprite() {
		return failAlertSprite;
	}

	public Sprite getPassReturnSprite() {
		return passReturnSprite;
	}

	public Sprite getFailReturnSprite() {
		return failReturnSprite;
	}

	private void drawMap() {
		int toolnum = 0;
		int x = 0;
		int y = 0;

		Image bg1Img = ImageRes.getInstance().getImage("bg1Img");
		Image mapImg = ImageRes.getInstance().getImage("mapImg");
		bg1Layer = new TiledLayer(1, 1, bg1Img, bg1Img.getWidth(), bg1Img.getHeight());
		bg1Layer.setCell(0, 0, 1);

		// 设置一个tile数组，数组每个值代表一横向的tiledlayer
		mapLayer = new TiledLayer[GameConst.GameCanvas.TILES_IN_HEIGHT];
		for (int line = GameConst.GameCanvas.TILES_IN_HEIGHT - 1; line >= 0; line--) {
			mapLayer[line] = new TiledLayer(GameConst.GameCanvas.TILES_IN_WIDTH, 1, mapImg, 33, 50);
			for (int col = 0; col < GameConst.GameCanvas.TILES_IN_WIDTH; col++) {
				// 随机生成地图的builds值并保存，工具、炸弹均默认为空
				tiledForBuilds[line][col] = RandomUtil.nextInt(GameConst.GameCanvas.BUILD_TYPES_AMOUT + 1 + 2);// 加2为了增加无build的概率
				if (tiledForBuilds[line][col] > GameConst.GameCanvas.BUILD_TYPES_AMOUT) {
					tiledForBuilds[line][col] = 0;
				}
				tiledForTools[line][col] = 0;
				tiledForBombs[line][col] = 0;
				// tile填充builds图片
				mapLayer[line].setCell(col, 0, tiledForBuilds[line][col]);
			}

			// 逐行设置tile层位置，并添加到界面
			mapLayer[line].setPosition(GameConst.GameCanvas.PA_X, GameConst.GameCanvas.PA_Y + line * GameConst.GameCanvas.TILE_HEIGHT);
			layerManager.append(mapLayer[line]);
		}

		// 设置人物位置
		player.setPosition(8, 4);
		// 避免角色开局被困，清理角色周边阻碍
		clearBuildsNear(player.getX(), player.getY());

		for (int i = 0; i < NPCsVector.size(); i++) {
			((PlayerObject) NPCsVector.elementAt(i)).setPosition(RandomUtil.nextInt(GameConst.GameCanvas.TILES_IN_WIDTH), RandomUtil
					.nextInt(GameConst.GameCanvas.TILES_IN_HEIGHT));
			clearBuildsNear(((PlayerObject) NPCsVector.elementAt(i)).getX(), ((PlayerObject) NPCsVector.elementAt(i)).getY());
		}

		layerManager.append(bg1Layer);

		// 随机隐藏特效工具
		while (toolnum < GameConst.GameCanvas.TOOL_AMOUT) {
			y = RandomUtil.nextInt(GameConst.GameCanvas.TILES_IN_WIDTH);
			x = RandomUtil.nextInt(GameConst.GameCanvas.TILES_IN_HEIGHT);

			if (tiledForBuilds[x][y] > 1) {
				if (tiledForTools[x][y] == 0) {
					// 随机生成地图的特效工具ID值并保存
					tiledForTools[x][y] = RandomUtil.nextInt(1, GameConst.GameCanvas.TOOL_TYPES_AMOUT + 1);
					toolnum++;
				}
			}
		}

		for (int i = 0; i < NPCsVector.size(); i++) {
			((PlayerObject) NPCsVector.elementAt(i)).addToScreen(layerManager);
		}

		player.addToScreen(layerManager);
	}

	public int checkCollidesWithBuilds(PlayerObject spr) {
		int col = spr.getX();
		int line = spr.getY();

		return tiledForBuilds[line][col];
	}

	public int getTiledForBuilds(int line, int col) {
		return tiledForBuilds[line][col];
	}

	public int getTiledForTools(int line, int col) {
		return tiledForTools[line][col];
	}

	public int getTiledForBombs(int line, int col) {
		return tiledForBombs[line][col];
	}

	public void setTiledForBuilds(int line, int col, int value) {
		tiledForBuilds[line][col] = value;
	}

	public void setTiledForTools(int line, int col, int value) {
		tiledForTools[line][col] = value;
	}

	public void setTiledForBombs(int line, int col, int value) {
		tiledForBombs[line][col] = value;
	}

	public void nextAction(PlayerObject spr) {
		boolean finish = false;// 判断是否完成下一步操作
		int times = 0;// 下步移动方向的已分析次数，等于4时则已分析完4个方向，结束分析
		int seachTarget = RandomUtil.nextInt(4);// 随机判断是否追击玩家，追击概率为25%（值为0时追击）
		int dir = RandomUtil.nextInt(1, 5);
		;// 下一步move方向
		boolean inAttackArea = false;// 假设当前位置投掷炸弹，玩家是否在有效爆炸范围内
		int selfX = spr.getX();// NPC位置
		int selfY = spr.getY();// NPC位置

		// 先判断是否安全,不安全时优先远离炸弹区
		if (numOfInBombArea(spr, BombVector) != 0) {
			int bombX = ((BombObject) BombVector.elementAt(numOfInBombArea(spr, BombVector) - 1)).getX();// 炸弹位置
			int bombY = ((BombObject) BombVector.elementAt(numOfInBombArea(spr, BombVector) - 1)).getY();// 炸弹位置

			if (selfY - bombY >= 0) {
				dir = 2;// move down best;
			} else if (selfY - bombY < 0) {
				dir = 1;// move up best;
			} else if (selfX - bombX < 0) {
				dir = 3;// move left best;
			} else if (selfX - bombX >= 0) {
				dir = 4;// move up best;
			}
		} else if (seachTarget == 0) {// an追击概率为25%（值为0时追击）
			int tarX = player.getX();// 玩家位置
			int tarY = player.getY();// 玩家位置

			if (selfX == tarX && selfY == tarY) {
				// DIR.C;
				inAttackArea = true;
			} else if (selfX == tarX && selfY > tarY) {
				dir = 1;// DIR.U;
				if (Math.abs(selfY - tarY) <= spr.getBombPower()) {
					inAttackArea = true;
				}
			} else if (selfX == tarX && selfY < tarY) {
				dir = 2;// DIR.D;
				if (Math.abs(selfY - tarY) <= spr.getBombPower()) {
					inAttackArea = true;
				}
			} else if (selfX > tarX && selfY == tarY) {
				dir = 3;// DIR.L;
				if (Math.abs(selfX - tarX) <= spr.getBombPower()) {
					inAttackArea = true;
				}
			} else if (selfX < tarX && selfY == tarY) {
				dir = 4;// DIR.R;
				if (Math.abs(selfX - tarX) <= spr.getBombPower()) {
					inAttackArea = true;
				}
			} else if (selfX > tarX && selfY > tarY) {
				// DIR.LU，随机向左或向上;
				if (RandomUtil.nextInt(2) == 0) {
					dir = 1;
				} else {
					dir = 3;
				}
			} else if (selfX > tarX && selfY < tarY) {
				// DIR.LD，随机向左或向下;
				if (RandomUtil.nextInt(2) == 0) {
					dir = 3;
				} else {
					dir = 2;
				}
			} else if (selfX < tarX && selfY > tarY) {
				// DIR.RU，随机向右或向上;
				if (RandomUtil.nextInt(2) == 0) {
					dir = 1;
				} else {
					dir = 4;
				}
			} else if (selfX < tarX && selfY < tarY) {
				// DIR.RD，随机向右或向下;
				if (RandomUtil.nextInt(2) == 0) {
					dir = 2;
				} else {
					dir = 4;
				}
			}
		} else {
			dir = RandomUtil.nextInt(1, 5);// 随机选择下步移动方向
		}

		while (!finish) {

			if (inAttackArea) {
				setBomb(spr);
			}

			// 任一方向移动
			switch (dir) {
			case 1:// up
				if (moveIfNotCollides(spr, 0, -1) == 0) {// 无碰撞物
					spr.faceUp();
					finish = true;
				} else if (moveIfNotCollides(spr, 0, -1) == 1) {// 碰撞到可炸物
					setBomb(spr);
				}
				break;
			case 2: // down
				if (moveIfNotCollides(spr, 0, 1) == 0) {
					spr.faceDown();
					finish = true;
				} else if (moveIfNotCollides(spr, 0, -1) == 1) {// 碰撞到可炸物
					setBomb(spr);
				}
				break;
			case 3: // left
				if (moveIfNotCollides(spr, -1, 0) == 0) {
					spr.faceLeft();
					finish = true;
				} else if (moveIfNotCollides(spr, 0, -1) == 1) {// 碰撞到可炸物
					setBomb(spr);
				}
				break;
			case 4:// right
				if (moveIfNotCollides(spr, 1, 0) == 0) {
					spr.faceRight();
					finish = true;
				} else if (moveIfNotCollides(spr, 0, -1) == 1) {// 碰撞到可炸物
					setBomb(spr);
				}
				break;
			}
			// 结束一个方向判断
			times++;

			if (!finish) {
				setBomb(spr);
				dir++;
				if (dir == 5) {
					dir = dir - 4;
				}
			}

			// 四个方向均已判断
			if (times == 5) {
				finish = true;
			}
		}
	}

	public int moveIfNotCollides(PlayerObject spr, int x, int y) {
		if (spr.move(x, y)) {
			if (checkCollidesWithBuilds(spr) == 1) {
				spr.move(-x, -y);
				return 2;
			} else if (checkCollidesWithBuilds(spr) > 1) {
				spr.move(-x, -y);
				return 1;
			} else if (checkCollidesWithBomb(spr)) {
				spr.move(-x, -y);
				return 2;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	private void clearBuildsNear(int x, int y) {
		tiledForBuilds[y][x] = 0;
		mapLayer[y].setCell(x, 0, 0);

		if (x - 1 >= 0) {
			tiledForBuilds[y][x - 1] = 0;
			mapLayer[y].setCell(x - 1, 0, 0);
		}
		if (x + 1 < GameConst.GameCanvas.TILES_IN_WIDTH) {
			tiledForBuilds[y][x + 1] = 0;
			mapLayer[y].setCell(x + 1, 0, 0);
		}
		if (y - 1 >= 0) {
			tiledForBuilds[y - 1][x] = 0;
			mapLayer[y - 1].setCell(x, 0, 0);
		}
		if (y + 1 < GameConst.GameCanvas.TILES_IN_HEIGHT) {
			tiledForBuilds[y + 1][x] = 0;
			mapLayer[y + 1].setCell(x, 0, 0);
		}
	}

	public void setBomb(PlayerObject spr) {
		int tempX = spr.getX();
		int tempY = spr.getY();
		boolean bombExistedInTiled = false;// 当前位置是否已有炸弹

		for (int i = 0; i < BombVector.size(); i++) {
			int bombX = ((BombObject) BombVector.elementAt(i)).getX();
			int bombY = ((BombObject) BombVector.elementAt(i)).getY();
			if (tempX == bombX && tempY == bombY) {
				bombExistedInTiled = true;
			}
		}

		if (spr.getBombNum() > 0 && !bombExistedInTiled) {// 同一tile不允许放置多个炸弹
			newBomb = new BombObject(spr);
			newBomb.addToScreen(layerManager);
			BombVector.addElement(newBomb);
		}
	}

	public void checkCollidesWithBombArea(BombObject bb) {
		int tempX = 0;
		int tempY = 0;
		boolean ifcollides[] = new boolean[4];// 分别对应上下左右四个方向是否已炸到实物
		for (int i = 0; i < 4; i++) {
			ifcollides[i] = false;// 初始值均为未炸到实物，设置为false
		}

		int[] attackArea = bb.getAttackArea();

		// 依次检查炸弹区域内每个tiled上的碰撞情况
		for (int i = 0; i < attackArea.length; i++) {
			if ((i == attackArea.length - 1) || (ifcollides[i % 4] == false)) {// 分别对应上下左右四个方向是否已炸到实物，未炸到实物则该方向继续炸
				// 检查当前tiled是否炸到任意实物
				if (attackArea[i] > 0) {
					// 检查与玩家碰撞
					if (!checkIsSafeInTiled(player, attackArea[i]) && player.getSuperTimeLeft() == 0) {// 角色丢失生命后闪现期间不计算碰撞，0值表示非闪现状态
						player.setLife(-1);
						if (player.getLife() == 0) {
							player.removeToScreen(layerManager);
							updateStateToDead();
						}
					}

					// 检查与NPC碰撞
					if (NPCsVector != null) {
						for (int j = NPCsVector.size() - 1; j >= 0; j--) {
							if (!checkIsSafeInTiled(((PlayerObject) NPCsVector.elementAt(j)), attackArea[i])
									&& ((PlayerObject) NPCsVector.elementAt(j)).getSuperTimeLeft() == 0) {// 角色丢失生命后闪现期间不计算碰撞，0值表示非闪现状态
								((PlayerObject) NPCsVector.elementAt(j)).setLife(-1);
								if (((PlayerObject) NPCsVector.elementAt(j)).getLife() == 0) {
									((PlayerObject) NPCsVector.elementAt(j)).removeToScreen(layerManager);
									NPCsVector.removeElementAt(j);
									if (NPCsVector.size() == 0) {
										updateStateToPass();
									}
								}
							}
						}
					}

					// 检查与builds碰撞
					if (attackArea[i] > GameConst.GameCanvas.TILES_IN_WIDTH) {
						tempY = (attackArea[i] - 1) / GameConst.GameCanvas.TILES_IN_WIDTH;
						tempX = attackArea[i] - tempY * GameConst.GameCanvas.TILES_IN_WIDTH - 1;
					} else {
						tempX = attackArea[i] - 1;
						tempY = 0;
					}

					if (tiledForBuilds[tempY][tempX] > 1) {
						tiledForBuilds[tempY][tempX] = 0;
						if (tiledForTools[tempY][tempX] == 0) {
							mapLayer[tempY].setCell(tempX, 0, 0);
						} else {
							mapLayer[tempY].setCell(tempX, 0, GameConst.GameCanvas.BUILD_TYPES_AMOUT + tiledForTools[tempY][tempX]);
						}
						// 遇到障碍物则不再继续炸
						ifcollides[i % 4] = true;
					} else if (tiledForBuilds[tempY][tempX] == 1) {
						// 遇到障碍物则不再继续炸
						ifcollides[i % 4] = true;
					}
					// 结束（检查当前tiled是否炸到任意实物）
				}

				// 结束（当前四个方向是否炸到任意实物的判断）,已炸到则该方向不再继续炸,设置为0
			} else {
				attackArea[i] = 0;
				bb.setAttackArea(i, 0);
			}

		}// 结束全部tiled判断
	}

	public void checkCollidesWithTools(PlayerObject spr) {
		int tempX = spr.getX();
		int tempY = spr.getY();

		if (tiledForTools[tempY][tempX] == 1) {
			// 加速
			if (spr.getSpeed() >= 2) {
				spr.setSpeed(2);
			}
		} else if (tiledForTools[tempY][tempX] == 2) {
			// 加可投掷炸弹数
			spr.setBombNum(1);
		} else if (tiledForTools[tempY][tempX] == 3) {
			// 加炸弹威力
			spr.setBombPower(1);
		}

		tiledForTools[tempY][tempX] = 0;
		mapLayer[tempY].setCell(tempX, 0, 0);
	}

	public boolean checkCollidesWithBomb(PlayerObject spr) {
		int col = spr.getX();
		int line = spr.getY();
		boolean meetBomb = false;

		if (BombVector != null) {
			for (int i = 0; i < BombVector.size(); i++) {
				if (!meetBomb) {
					int tempX = ((BombObject) BombVector.elementAt(i)).getX();
					int tempY = ((BombObject) BombVector.elementAt(i)).getY();
					if (tempX == col && tempY == line) {
						meetBomb = true;
					}
				}
			}
		}

		return meetBomb;
	}

	public boolean checkIsSafeInTiled(PlayerObject spr, int tiledID) {
		int posX = spr.getX();
		int posY = spr.getY();
		int tiledIDForSprite = posY * GameConst.GameCanvas.TILES_IN_WIDTH + posX + 1;

		if (tiledIDForSprite == tiledID) {
			return false;
		} else {
			return true;
		}
	}

	public int numOfInBombArea(PlayerObject spr, Vector bb) {
		int bombNum = 0;// 在第n个炸弹的炸区，默认值0表示为不在炸区
		int posX = spr.getX();
		int posY = spr.getY();

		if (BombVector != null) {
			for (int i = BombVector.size() - 1; i > 0; i--) {
				int bbX = ((BombObject) BombVector.elementAt(i)).getX();
				int bbY = ((BombObject) BombVector.elementAt(i)).getY();
				int power = ((BombObject) BombVector.elementAt(i)).getPower();

				if (Math.abs(posX - bbX) <= power && Math.abs(posY - bbY) <= power) {
					bombNum = i + 1;
				}
			}
		}
		return bombNum;
	}
}
