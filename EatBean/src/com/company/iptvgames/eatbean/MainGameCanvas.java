package com.company.iptvgames.eatbean;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.company.iptvgames.framework.utils.NumberImgUtil;
import com.company.iptvgames.framework.utils.Constants.KeyCode;

public class MainGameCanvas extends GameCanvas implements Runnable {

	public MainMIDlet midlet;
	private LayerManager layerManager = new LayerManager();
	public Thread mainThread;
	private Graphics gra = getGraphics();
	private int maxlevel = 4;
	private int score = 0;
	private int level = 1;
	private int lives = 2;
	private int speed = 5;
	private long playduration;
	private boolean fail_restart_selected = true;

	// for map size
	public final static int Tile_WIDTH = 25;
	public final static int Tile_HEIGHT = 25;

	public final static int Tiles_IN_WIDTH = 17;
	public final static int Tiles_IN_HEIGHT = 19;

	public final static int mapPX = 35;
	public final static int mapPY = 26;

	private TiledLayer mapLayer;
	private TiledLayer bglayer;
	private TiledLayer roadlayer;
	private TiledLayer dialoglayer;
	protected Sprite player;
	private Sprite NPC;
	private Vector NPCs = new Vector();;
	private Vector scorenumbersVector = new Vector();
	private Vector levelnumbersVector = new Vector();
	private Vector livesnumbersVector = new Vector();

	private Image bgImage;
	private Image map;
	private Image bean;
	private Image plimage;
	private Image result;
	private Image NPCorange;
	private Image NPCred;
	private Image NPCgreen;
	private Image NPCblue;
	private Image numbimage;

	private int direction = Sprite.TRANS_NONE;
	private int NPCdir = Sprite.TRANS_NONE;

	// position of player
	private int PX = 235;
	private int PY = 251;
	private int tmpPX, tmpPY;

	protected MainGameCanvas(MainMIDlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);

		initializeGame();

		// paint canvas
		layerManager.paint(gra, 0, 0);
		this.flushGraphics();

		if (mainThread == null) {
			mainThread = new Thread(this);
			mainThread.start();
		}

	}

	private void initializeGame() {
		cleanUpBackground();

		reloadImages();

		// create map and player
		CreateMap();
		CreateRoad();
		CreateSprite();
		CreateNPC();

		// add layer
		player.setPosition(PX, PY);
		player.setTransform(direction);

		layerManager.append(player);
		layerManager.append(roadlayer);
		layerManager.append(mapLayer);
		layerManager.append(bglayer);
		showscore(score, "Score");
		showscore(level, "Level");
		showscore(lives, "Lives");

		layerManager.paint(gra, 0, 0);
		this.flushGraphics();
	}

	private void cleanUpBackground() {
		for (int i = layerManager.getSize() - 1; i > 0; i--) {
			layerManager.remove(layerManager.getLayerAt(i));
		}

	}

	private void reloadImages() {
		try {
			bgImage = Image.createImage("/bg.png");
			map = Image.createImage("/wall/map.png");
			bean = Image.createImage("/bean.png");
			plimage = Image.createImage("/player.png");
			NPCorange = Image.createImage("/NPC_O.png");
			NPCred = Image.createImage("/NPC_R.png");
			NPCgreen = Image.createImage("/NPC_G.png");
			NPCblue = Image.createImage("/NPC_B.png");
			numbimage = Image.createImage("/num240x34.png");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	protected void keyPressed(int keyCode) {
		switch (midlet.gamestatus) {
		case 0:
			if (keyCode == KeyCode.OK) {
				// ok is pressed
				layerManager.remove(dialoglayer);
				PX = 235;
				PY = 251;
				player.setPosition(PX, PY);
				layerManager.paint(gra, 0, 0);
				this.flushGraphics();
				midlet.gamestatus = 1;
				midlet.NPCbegin = System.currentTimeMillis();
			}
			break;
		case 2:
			if (keyCode == KeyCode.LEFT) {
				if (fail_restart_selected) {
					fail_restart_selected = false;
					try {
						result = Image.createImage("/fail_exit.png");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					fail_restart_selected = true;
					try {
						result = Image.createImage("/fail_restart.png");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (keyCode == KeyCode.RIGHT) {
				if (fail_restart_selected) {
					fail_restart_selected = false;
					try {
						result = Image.createImage("/fail_exit.png");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					fail_restart_selected = true;
					try {
						result = Image.createImage("/fail_restart.png");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			layerManager.remove(dialoglayer);
			dialoglayer = new TiledLayer(1, 1, result, result.getWidth(), result.getHeight());
			dialoglayer.setCell(0, 0, 1);
			layerManager.insert(dialoglayer, 0);
			layerManager.paint(gra, 0, 0);
			this.flushGraphics();
			if ((keyCode == KeyCode.OK)) {
				layerManager.remove(dialoglayer);
				if (fail_restart_selected) {
					newlevel(1);// continue playing level 1
					layerManager.paint(gra, 0, 0);
					this.flushGraphics();
					midlet.gamestatus = 1;
					midlet.NPCbegin = System.currentTimeMillis();
				} else {
					newlevel(1);
					midlet.gamestatus = 0;
					midlet.menuShow = true;
					midlet.dis.setCurrent(midlet.menuCanvas);// return to menu page
					this.flushGraphics();
				}
			}
			break;
		case 3:
			if (keyCode == KeyCode.OK) {
				layerManager.remove(dialoglayer);
				level = level + 1;
				newlevel(level);// continue playing next level
				layerManager.paint(gra, 0, 0);
				this.flushGraphics();
				midlet.gamestatus = 1;
				midlet.NPCbegin = System.currentTimeMillis();
			}
			break;
		case 4:
			if (keyCode == KeyCode.OK) {
				midlet.gamestatus = 1;
				layerManager.remove(dialoglayer);
				layerManager.paint(gra, 0, 0);
				this.flushGraphics();
			}
			break;
		case 1:
			tmpPX = PX;
			tmpPY = PY;
			if (keyCode == KeyCode.OK) {
				midlet.gamestatus = 4;
				showalert("pause");
			}
			if (keyCode == KeyCode.UP) {
				direction = Sprite.TRANS_ROT270;
				PY = PY - 25;
			}
			if (keyCode == KeyCode.DOWN) {
				direction = Sprite.TRANS_ROT90;
				PY = PY + 25;
			}
			if (keyCode == KeyCode.LEFT) {
				direction = Sprite.TRANS_ROT180;
				PX = PX - 25;
			}
			if (keyCode == KeyCode.RIGHT) {
				direction = Sprite.TRANS_NONE;
				PX = PX + 25;
			}
			if (keyCode == KeyCode.BACK || keyCode == KeyCode.BACK_1 || keyCode == KeyCode.BACK_2) {
				newlevel(1);
				midlet.gamestatus = 0;
				midlet.menuShow = true;
				midlet.dis.setCurrent(midlet.menuCanvas);// return to menu page
				this.flushGraphics();
				break;
			}

			player.setTransform(direction);
			player.setPosition(PX, PY);

			// 与地图碰撞，则返回原位置
			if (player.collidesWith(mapLayer, true)) {
				PX = tmpPX;
				PY = tmpPY;
				player.setPosition(PX, PY);
			} else {// 与豆子碰撞，则获得分数
				int j = (PX - mapPX) / Tile_WIDTH;
				int i = (PY - mapPY) / Tile_HEIGHT;

				if (roadlayer.getCell(j, i) == 1 || roadlayer.getCell(j, i) == 2) {
					score = score + 10 * roadlayer.getCell(j, i);
					roadlayer.setCell(j, i, 0);
					showscore(score, "Score");
					// check if pass
					boolean passflag = true;
					for (int x = 0; x < Tiles_IN_HEIGHT; x++) {
						for (int y = 0; y < Tiles_IN_WIDTH; y++) {
							if (roadlayer.getCell(y, x) == 1) {
								passflag = false;
							}
							if (passflag == false) {
								break;
							}
						}
					}
					if (passflag) {
						showalert("Pass");
						break;
					}
				}
			}

			layerManager.paint(gra, 0, 0);
			this.flushGraphics();
		}
	}

	public void run() {
		int times = 0;
		while (true) {
			long begin = System.currentTimeMillis();
			player.nextFrame();

			playduration = System.currentTimeMillis() - midlet.NPCbegin;
			// if(midlet.gamestatus ==1){
			if (midlet.NPCbegin > 0 && playduration > 2000 && midlet.gamestatus == 1) {
				// NPC move speed is slower than player 5 times
				if (times % speed == 0) {
					for (int i = 0; i < NPCs.size(); i++) {
						moving((Sprite) NPCs.elementAt(i));
					}
				}
				// 检查与npc碰撞，则生命减1
				for (int j = 0; j < NPCs.size(); j++) {
					// check collides with player
					if (player.collidesWith((Sprite) NPCs.elementAt(j), true)) {
						showalert("Caught");
						break;
					}
				}
			}
			// }

			layerManager.paint(gra, 0, 0);
			this.flushGraphics();// 将后备屏幕缓冲区内容输出到显示屏上

			long runtime = System.currentTimeMillis() - begin;

			if (runtime < midlet.fps) {
				try {
					Thread.sleep(midlet.fps - runtime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			times++;
		}
	}

	public void CreateMap() {

		try {
			mapLayer = new TiledLayer(Tiles_IN_WIDTH, Tiles_IN_HEIGHT, map, Tile_WIDTH, Tile_HEIGHT);
			bglayer = new TiledLayer(1, 1, bgImage, bgImage.getWidth(), bgImage.getHeight());

			// 填充bgcells
			bglayer.setCell(0, 0, 1);

			// 填充map cells
			int[][] cells = new int[Tile_WIDTH][Tile_HEIGHT];

			for (int i = 0; i < Tiles_IN_HEIGHT; i++) {
				for (int j = 0; j < Tiles_IN_WIDTH; j++) {
					cells[i][j] = MapForEachLevel.getmapcells(level, i, j);
					mapLayer.setCell(j, i, cells[i][j]);
				}
			}
			mapLayer.setPosition(mapPX, mapPY);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void CreateRoad() {
		try {
			roadlayer = new TiledLayer(Tiles_IN_WIDTH, Tiles_IN_HEIGHT, bean, Tile_WIDTH, Tile_HEIGHT);

			// 填充豆子
			int[][] beancells = new int[Tile_WIDTH][Tile_HEIGHT];

			for (int i = 0; i < Tiles_IN_HEIGHT; i++) {
				for (int j = 0; j < Tiles_IN_WIDTH; j++) {
					beancells[i][j] = MapForEachLevel.getbeancells(level, i, j);
					roadlayer.setCell(j, i, beancells[i][j]);
				}
			}
			roadlayer.setPosition(mapPX, mapPY);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void CreateSprite() {
		try {
			player = new Sprite(plimage, 25, 25);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		player.defineReferencePixel(13, 13);// 参考像素点
	}

	public void CreateNPC() {
		for (int i = NPCs.size(); i > 0; i--) {
			layerManager.remove((Sprite) NPCs.elementAt(i - 1));
			NPCs.removeElementAt(i - 1);
		}
		for (int i = 0; i < level; i++) {
			if (i % 4 == 0) {
				try {
					NPC = new Sprite(NPCorange, 25, 25);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (i % 4 == 1) {
				try {
					NPC = new Sprite(NPCred, 25, 25);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (i % 4 == 2) {
				try {
					NPC = new Sprite(NPCgreen, 25, 25);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (i % 4 == 3) {
				try {
					NPC = new Sprite(NPCblue, 25, 25);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			NPC.setPosition(PX - 25 + (i % 3) * 25, PY);
			layerManager.append(NPC);
			NPCs.addElement(NPC);
		}
	}

	private void moving(Sprite npc) {
		Sprite s = npc;
		// NPC is moving
		boolean moveR = false;
		int NPCPX = s.getX();
		int NPCPY = s.getY();
		// int NPCdir = Sprite.TRANS_NONE;

		if (NPCPX > PX) {
			if (NPCPY > PY) {
				NPCdir = Sprite.TRANS_ROT90;
			}// go up
			if (NPCPY == PY) {
				NPCdir = Sprite.TRANS_ROT180;
			}// go left
			if (NPCPY < PY) {
				NPCdir = Sprite.TRANS_ROT270;
			}// go down
		}
		if (NPCPX == PX) {
			if (NPCPY > PY) {
				NPCdir = Sprite.TRANS_ROT90;
			}
			if (NPCPY < PY) {
				NPCdir = Sprite.TRANS_ROT270;
			}
		}
		if (NPCPX < PX) {
			if (NPCPY > PY) {
				NPCdir = Sprite.TRANS_ROT90;
			}
			if (NPCPY == PY) {
				NPCdir = Sprite.TRANS_NONE;
			}// go right
			if (NPCPY < PY) {
				NPCdir = Sprite.TRANS_ROT270;
			}
		}

		while (!moveR) {
			if (NPCdir == Sprite.TRANS_ROT180) {
				s.move(-25, 0);
				moveR = true;
				if (s.collidesWith(mapLayer, true)) {
					s.move(25, 0);
					NPCdir = Sprite.TRANS_ROT90;
					moveR = false;
				}
			}
			if (NPCdir == Sprite.TRANS_ROT270) {
				s.move(0, 25);
				moveR = true;
				if (s.collidesWith(mapLayer, true)) {
					s.move(0, -25);
					NPCdir = Sprite.TRANS_ROT180;
					moveR = false;
				}
			}
			if (NPCdir == Sprite.TRANS_NONE) {
				s.move(25, 0);
				moveR = true;
				if (s.collidesWith(mapLayer, true)) {
					s.move(-25, 0);
					NPCdir = Sprite.TRANS_ROT270;
					moveR = false;
				}
			}
			if (NPCdir == Sprite.TRANS_ROT90) {
				s.move(0, -25);
				moveR = true;
				if (s.collidesWith(mapLayer, true)) {
					s.move(0, 25);
					NPCdir = Sprite.TRANS_NONE;
					moveR = false;
				}
			}
			// NPC.setTransform(NPCdir);
		}
	}

	private void showscore(int value, String str) {
		int position = 0;
		String st = str;

		if (st.equals("Score")) {
			position = 170;
			// clear old records
			if (null != scorenumbersVector && 0 < scorenumbersVector.size()) {
				for (int i = 0; i < scorenumbersVector.size(); i++) {
					layerManager.remove((Sprite) scorenumbersVector.elementAt(i));
				}
			}

			// set new records
			try {
				scorenumbersVector = NumberImgUtil.updateNumber(value, numbimage, 600, position, Graphics.RIGHT | Graphics.TOP);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// add new records to layermanager
			if (null != scorenumbersVector && 0 < scorenumbersVector.size()) {
				for (int i = 0; i < scorenumbersVector.size(); i++) {
					layerManager.insert((Sprite) scorenumbersVector.elementAt(i), 0);
				}
			}
		}

		if (st.equals("Level")) {
			position = 260;
			position = 260;
			// clear old records
			if (null != levelnumbersVector && 0 < levelnumbersVector.size()) {
				for (int i = 0; i < levelnumbersVector.size(); i++) {
					layerManager.remove((Sprite) levelnumbersVector.elementAt(i));
				}
			}

			// set new records
			try {
				levelnumbersVector = NumberImgUtil.updateNumber(value, numbimage, 600, position, Graphics.RIGHT | Graphics.TOP);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// add new records to layermanager
			if (null != levelnumbersVector && 0 < levelnumbersVector.size()) {
				for (int i = 0; i < levelnumbersVector.size(); i++) {
					layerManager.insert((Sprite) levelnumbersVector.elementAt(i), 0);
				}
			}
		}

		if (st.equals("Lives")) {
			position = 440;
			position = 440;
			// clear old records
			if (null != livesnumbersVector && 0 < livesnumbersVector.size()) {
				for (int i = 0; i < livesnumbersVector.size(); i++) {
					layerManager.remove((Sprite) livesnumbersVector.elementAt(i));
				}
			}

			// set new records
			try {
				livesnumbersVector = NumberImgUtil.updateNumber(value, numbimage, 600, position, Graphics.RIGHT | Graphics.TOP);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// add new records to layermanager
			if (null != livesnumbersVector && 0 < livesnumbersVector.size()) {
				for (int i = 0; i < livesnumbersVector.size(); i++) {
					layerManager.insert((Sprite) livesnumbersVector.elementAt(i), 0);
				}
			}
		}

		layerManager.paint(gra, 0, 0);
		this.flushGraphics();
	}

	private void showalert(String st) {
		midlet.gamestatus = 0;
		if (st.equals("Caught")) {
			lives = lives - 1;
			showscore(lives, "Lives");
			if (lives == 0) {
				try {
					result = Image.createImage("/fail_restart.png");
					fail_restart_selected = true;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				midlet.gamestatus = 2;// 1 means keep running, 0 means pause, 2 means failed, 3 means pass
			} else {
				try {
					result = Image.createImage("/caught.png");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				midlet.gamestatus = 0;// 1 means keep running, 0 means pause, 2 means failed, 3 means pass
			}
		} else if ("pause".equalsIgnoreCase(st)) {
			try {
				result = Image.createImage("/pause.png");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			midlet.gamestatus = 4;
		} else {
			try {
				result = Image.createImage("/pass.png");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			midlet.gamestatus = 3;// 1 means keep running, 0 means pause, 2 means failed, 3 means pass
		}

		dialoglayer = new TiledLayer(1, 1, result, result.getWidth(), result.getHeight());
		dialoglayer.setCell(0, 0, 1);
		layerManager.insert(dialoglayer, 0);
		layerManager.paint(gra, 0, 0);
		this.flushGraphics();
	}

	private void newlevel(int value) {
		// cleanUpBackground();
		if (value == 1) {
			score = 0;
			level = 1;
			lives = 2;
		} else {
			level = value;
		}

		if (level > maxlevel) {
			level = 1;
		}
		PX = 235;
		PY = 251;

		initializeGame();
	}

}
