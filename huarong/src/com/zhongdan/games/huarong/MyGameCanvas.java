package com.zhongdan.games.huarong;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.TiledLayer;

import com.zhongdan.games.framework.utils.Constants;
import com.zhongdan.games.framework.utils.ImageUtil;
import com.zhongdan.games.huarong.GameConstants.GameSettings;
import com.zhongdan.games.huarong.GameConstants.RoleName;

public class MyGameCanvas extends GameCanvas {

	private MyMIDlet midlet;
	private Graphics graphics = this.getGraphics();
	private LayerManager layerManager = new LayerManager();
	private Image backgroundImg;
	private Image zhangfeiImg;
	private Image zhaoyunImg;
	private Image huangzhongImg;
	private Image machaoImg;
	private Image guanyuImg;
	private Image caocaoImg;
	private Image zuImg;
	private TiledLayer backgroundLayer;
	private RoleSprite zhangfeiSprite;
	private RoleSprite guanyuSprite;
	private RoleSprite caocaoSprite;
	private RoleSprite machaoSprite;
	private RoleSprite zhaoyunSprite;
	private RoleSprite huangzhongSprite;
	private RoleSprite zuSprite;
	private RoleSprite selectedRoleSprite;
	private RoleSprite maps[][];
	private boolean isPlaying;
	private boolean isMoving;
	private int level = 1;
	public int step;

	protected MyGameCanvas(MyMIDlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
		loadImage();
		initCanvas(1);
	}

	private void loadImage() {
		backgroundImg = ImageUtil.createImage("/background.jpg");
		zhangfeiImg = ImageUtil.createImage("/zhangfei.png");
		zhaoyunImg = ImageUtil.createImage("/zhaoyun.png");
		huangzhongImg = ImageUtil.createImage("/huangzhong.png");
		machaoImg = ImageUtil.createImage("/machao.png");
		guanyuImg = ImageUtil.createImage("/guanyu.png");
		caocaoImg = ImageUtil.createImage("/caocao.png");
		zuImg = ImageUtil.createImage("/zu.png");
	}

	public void initCanvas(int newLevel) {
		// Initialize layerManager
		for (int i = layerManager.getSize() - 1; i >= 0; i--) {
			layerManager.remove(layerManager.getLayerAt(i));
		}

		// Draw background
		backgroundLayer = new TiledLayer(1, 1, backgroundImg, backgroundImg.getWidth(), backgroundImg.getHeight());
		backgroundLayer.setCell(0, 0, 1);
		layerManager.append(backgroundLayer);

		// Initialize level
		step = 0;
		level = newLevel;
		initLevel(level);
		isPlaying = true;
		isMoving = false;

		// Paint map
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	private void initLevel(int newLevel) {
		maps = new RoleSprite[GameSettings.MAP_ROW][GameSettings.MAP_COL];
		for (int i = 0; i < GameSettings.MAP_ROW; i++) {
			for (int j = 0; j < GameSettings.MAP_COL; j++) {
				switch (GameConstants.START_MAP[level - 1][i][j]) {
				case RoleName.CAO_CAO:
					caocaoSprite = new RoleSprite(caocaoImg, RoleName.CAO_CAO, 166, 124, i, j, false);
					maps[i][j] = caocaoSprite;
					layerManager.insert(caocaoSprite, 0);
					break;
				case RoleName.GUAN_YU:
					guanyuSprite = new RoleSprite(guanyuImg, RoleName.GUAN_YU, 166, 62, i, j, false);
					maps[i][j] = guanyuSprite;
					layerManager.insert(guanyuSprite, 0);
					break;
				case RoleName.ZHAO_YUN:
					zhaoyunSprite = new RoleSprite(zhaoyunImg, RoleName.ZHAO_YUN, 83, 123, i, j, false);
					maps[i][j] = zhaoyunSprite;
					layerManager.insert(zhaoyunSprite, 0);
					break;
				case RoleName.MA_CHAO:
					machaoSprite = new RoleSprite(machaoImg, RoleName.MA_CHAO, 83, 123, i, j, false);
					maps[i][j] = machaoSprite;
					layerManager.insert(machaoSprite, 0);
					break;
				case RoleName.HUANG_ZHONG:
					huangzhongSprite = new RoleSprite(huangzhongImg, RoleName.HUANG_ZHONG, 83, 123, i, j, false);
					maps[i][j] = huangzhongSprite;
					layerManager.insert(huangzhongSprite, 0);
					break;
				case RoleName.ZHANG_FEI:
					zhangfeiSprite = new RoleSprite(zhangfeiImg, RoleName.ZHANG_FEI, 83, 123, i, j, false);
					maps[i][j] = zhangfeiSprite;
					layerManager.insert(zhangfeiSprite, 0);
					break;
				case RoleName.ZU:
					zuSprite = new RoleSprite(zuImg, RoleName.ZU, 83, 62, i, j, false);
					maps[i][j] = zuSprite;
					layerManager.insert(zuSprite, 0);
					break;
				}
			}
		}
		huangzhongSprite.setSelected(true);
		selectedRoleSprite = huangzhongSprite;
	}

	protected void keyPressed(int keyCode) {
		if (keyCode == Constants.KeyCode.LEFT) {
			if (isPlaying) {
				if (isMoving) {
				} else {
					moveCurser(Constants.KeyCode.LEFT);
				}
			}
		} else if (keyCode == Constants.KeyCode.RIGHT) {
			if (isPlaying) {
				if (isMoving) {
				} else {
					moveCurser(Constants.KeyCode.RIGHT);
				}
			}
		} else if (keyCode == Constants.KeyCode.UP) {
			if (isPlaying) {
				if (isMoving) {
				} else {
					moveCurser(Constants.KeyCode.UP);
				}
			}
		} else if (keyCode == Constants.KeyCode.DOWN) {
			if (isPlaying) {
				if (isMoving) {
				} else {
					moveCurser(Constants.KeyCode.DOWN);
				}
			}
		} else if (keyCode == Constants.KeyCode.OK) {
			if (isPlaying) {
				if (isMoving) {
					isMoving = false;
				} else {
					isMoving = true;
				}
			}
		} else if (keyCode == Constants.KeyCode.BACK) {
			this.midlet.getDisplay().setCurrent(this.midlet.getMenuCanvas());
		}
	}

	private void moveCurser(int dir) {
		int currentRow = selectedRoleSprite.getRow();
		int currentCol = selectedRoleSprite.getCol();
		RoleSprite nextRoleSprite = null;
		switch (dir) {
		case Constants.KeyCode.LEFT:
			if (currentCol > 0) {
				// Left
				nextRoleSprite = maps[currentRow][currentCol - 1];
				// Lower left
				if (null == nextRoleSprite && currentRow + 1 < GameSettings.MAP_ROW) {
					nextRoleSprite = maps[currentRow + 1][currentCol - 1];
				}
				// Double left
				if (null == nextRoleSprite && currentCol > 1) {
					nextRoleSprite = maps[currentRow][currentCol - 2];
				}
				// Lower double left
				if (null == nextRoleSprite && currentRow + 1 < GameSettings.MAP_ROW && currentCol > 1) {
					nextRoleSprite = maps[currentRow + 1][currentCol - 2];
				}
			} else {
				nextRoleSprite = selectedRoleSprite;
			}
			break;
		case Constants.KeyCode.RIGHT:
			if (currentCol + selectedRoleSprite.getRoleWidth() < GameSettings.MAP_COL) {
				// Right
				nextRoleSprite = maps[currentRow][currentCol + selectedRoleSprite.getRoleWidth()];
				// Lower right
				if (null == nextRoleSprite && currentRow + 1 < GameSettings.MAP_ROW) {
					nextRoleSprite = maps[currentRow + 1][currentCol + selectedRoleSprite.getRoleWidth()];
				}
				// Double right
				if (null == nextRoleSprite && currentCol + selectedRoleSprite.getRoleWidth() + 1 < GameSettings.MAP_COL) {
					nextRoleSprite = maps[currentRow][currentCol + selectedRoleSprite.getRoleWidth() + 1];
				}
				// Lower double right
				if (null == nextRoleSprite && currentRow + 1 < GameSettings.MAP_ROW
						&& currentCol + selectedRoleSprite.getRoleWidth() + 1 < GameSettings.MAP_COL) {
					nextRoleSprite = maps[currentRow + 1][currentCol + selectedRoleSprite.getRoleWidth() + 1];
				}
			} else {
				nextRoleSprite = selectedRoleSprite;
			}
			break;
		case Constants.KeyCode.UP:
			if (currentRow > 0) {
				// Upper
				nextRoleSprite = maps[currentRow - 1][currentCol];
				// Upper right
				if (null == nextRoleSprite && currentCol + 1 < GameSettings.MAP_COL) {
					nextRoleSprite = maps[currentRow - 1][currentCol + 1];
				}
				// Double upper
				if (null == nextRoleSprite && currentRow > 1) {
					nextRoleSprite = maps[currentRow - 2][currentCol];
				}
				// Double upper right
				if (null == nextRoleSprite && currentRow > 1 && currentCol + 1 < GameSettings.MAP_COL) {
					nextRoleSprite = maps[currentRow - 2][currentCol + 1];
				}
				// Triple upper
				if (null == nextRoleSprite && currentRow > 2) {
					nextRoleSprite = maps[currentRow - 3][currentCol];
				}
				// Triple upper right
				if (null == nextRoleSprite && currentRow > 2 && currentCol + 1 < GameSettings.MAP_COL) {
					nextRoleSprite = maps[currentRow - 3][currentCol + 1];
				}
			} else {
				nextRoleSprite = selectedRoleSprite;
			}
			break;
		case Constants.KeyCode.DOWN:
			if (currentRow + selectedRoleSprite.getRoleHeight() < GameSettings.MAP_ROW) {
				// Down
				nextRoleSprite = maps[currentRow + selectedRoleSprite.getRoleHeight()][currentCol];
				// Down right
				if (null == nextRoleSprite && currentCol + 1 < GameSettings.MAP_COL) {
					nextRoleSprite = maps[currentRow + selectedRoleSprite.getRoleHeight()][currentCol + 1];
				}
				// Double down
				if (null == nextRoleSprite && currentRow + selectedRoleSprite.getRoleHeight() + 1 < GameSettings.MAP_ROW) {
					nextRoleSprite = maps[currentRow + selectedRoleSprite.getRoleHeight() + 1][currentCol];
				}
				// Double down right
				if (null == nextRoleSprite && currentRow + selectedRoleSprite.getRoleHeight() + 1 < GameSettings.MAP_ROW
						&& currentCol + 1 < GameSettings.MAP_COL) {
					nextRoleSprite = maps[currentRow + selectedRoleSprite.getRoleHeight() + 1][currentCol + 1];
				}
			} else {
				nextRoleSprite = selectedRoleSprite;
			}
			break;
		}
		selectedRoleSprite.setSelected(false);
		selectedRoleSprite = nextRoleSprite;
		selectedRoleSprite.setSelected(true);
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

}
