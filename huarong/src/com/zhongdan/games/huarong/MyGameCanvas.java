package com.zhongdan.games.huarong;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import com.zhongdan.games.framework.utils.Constants;
import com.zhongdan.games.framework.utils.ImageUtil;
import com.zhongdan.games.framework.utils.NumberImgUtil;
import com.zhongdan.games.framework.utils.Constants.KeyCode;
import com.zhongdan.games.huarong.GameConstants.GameSettings;
import com.zhongdan.games.huarong.GameConstants.RoleName;

public class MyGameCanvas extends GameCanvas {

	private MyMIDlet midlet;
	private Graphics graphics = this.getGraphics();
	private LayerManager layerManager = new LayerManager();
	private Image backgroundImg;
	private Image numberStepImg;
	private Image numberGoalImg;
	private Image zhangfeiImg;
	private Image zhaoyunImg;
	private Image huangzhongImg;
	private Image machaoImg;
	private Image guanyuImg;
	private Image caocaoImg;
	private Image zuImg;
	private TiledLayer backgroundLayer;
	private Vector stepSprite;
	private Vector goalSprite;
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
	public int goal;

	protected MyGameCanvas(MyMIDlet midlet) {
		super(false);
		this.midlet = midlet;
		this.setFullScreenMode(true);
		loadImage();
		initCanvas(1);
	}

	private void loadImage() {
		backgroundImg = ImageUtil.createImage("/background.png");
		numberStepImg = ImageUtil.createImage("/number_step.png");
		numberGoalImg = ImageUtil.createImage("/number_goal.png");
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
		goal = 81;
		level = newLevel;
		initLevel(level);
		updateStep();
		updateGoal();
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
					moveRole(KeyCode.LEFT);
				} else {
					moveCurser(KeyCode.LEFT);
				}
			}
		} else if (keyCode == KeyCode.RIGHT) {
			if (isPlaying) {
				if (isMoving) {
					moveRole(KeyCode.RIGHT);
				} else {
					moveCurser(KeyCode.RIGHT);
				}
			}
		} else if (keyCode == KeyCode.UP) {
			if (isPlaying) {
				if (isMoving) {
					moveRole(KeyCode.UP);
				} else {
					moveCurser(KeyCode.UP);
				}
			}
		} else if (keyCode == KeyCode.DOWN) {
			if (isPlaying) {
				if (isMoving) {
					moveRole(KeyCode.DOWN);
				} else {
					moveCurser(KeyCode.DOWN);
				}
			}
		} else if (keyCode == KeyCode.OK) {
			if (isPlaying) {
				if (isMoving) {
					isMoving = false;
				} else {
					isMoving = true;
				}
			}
		} else if (keyCode == KeyCode.BACK || keyCode == KeyCode.BACK_1 || keyCode == KeyCode.BACK_2) {
			this.midlet.getDisplay().setCurrent(this.midlet.getMenuCanvas());
		}
	}

	private void moveRole(int dir) {
		int currentRow = selectedRoleSprite.getRow();
		int currentCol = selectedRoleSprite.getCol();
		RoleSprite checkRoleSprite = null;
		boolean canMove = true;
		switch (dir) {
		case KeyCode.LEFT:
			if (currentCol > 0) {
				// 1. Check upper
				if (currentRow > 0) {
					// 1.1. Check upper double left
					if (currentCol > 1) {
						checkRoleSprite = maps[currentRow - 1][currentCol - 2];
						if (null != checkRoleSprite && checkRoleSprite.getRoleHeight() == 2 && checkRoleSprite.getRoleWidth() == 2) {
							canMove = false;
						}
					}
					// 1.2. Check upper left
					checkRoleSprite = maps[currentRow - 1][currentCol - 1];
					if (null != checkRoleSprite && checkRoleSprite.getRoleHeight() == 2) {
						canMove = false;
					}
				}
				// 2.1. Check double left
				if (currentCol > 1) {
					checkRoleSprite = maps[currentRow][currentCol - 2];
					if (null != checkRoleSprite && checkRoleSprite.getRoleWidth() == 2) {
						canMove = false;
					}
				}
				// 2.2. Check left
				checkRoleSprite = maps[currentRow][currentCol - 1];
				if (null != checkRoleSprite) {
					canMove = false;
				}
				// 3. Check lower
				if (selectedRoleSprite.getRoleHeight() == 2) {
					// 3.1. Check lower double left
					if (currentCol > 1) {
						checkRoleSprite = maps[currentRow + 1][currentCol - 2];
						if (null != checkRoleSprite && checkRoleSprite.getRoleWidth() == 2) {
							canMove = false;
						}
					}
					// 3.2. Check lower left
					checkRoleSprite = maps[currentRow][currentCol - 1];
					if (null != checkRoleSprite) {
						canMove = false;
					}
				}
			} else {
				canMove = false;
			}
			if (canMove) {
				maps[currentRow][currentCol] = null;
				maps[currentRow][currentCol - 1] = selectedRoleSprite;
				selectedRoleSprite.setCol(currentCol - 1);
				step++;
			}
			break;
		case KeyCode.RIGHT:
			if (currentCol + 1 < GameSettings.MAP_COL) {
				// Check upper right
				if (currentRow > 0) {
					checkRoleSprite = maps[currentRow - 1][currentCol + 1];
					if (null != checkRoleSprite && checkRoleSprite.getRoleHeight() == 2) {
						canMove = false;
					}
				}
				// Check right
				checkRoleSprite = maps[currentRow][currentCol + 1];
				if (null != checkRoleSprite) {
					canMove = false;
				}
				// Check lower right
				if (selectedRoleSprite.getRoleHeight() == 2) {
					checkRoleSprite = maps[currentRow + 1][currentCol + 1];
					if (null != checkRoleSprite) {
						canMove = false;
					}
				}
				if (selectedRoleSprite.getRoleWidth() == 2) {
					if (currentCol + 2 < GameSettings.MAP_COL) {
						// Check upper double right
						if (currentRow > 0) {
							checkRoleSprite = maps[currentRow - 1][currentCol + 2];
							if (null != checkRoleSprite && checkRoleSprite.getRoleHeight() == 2) {
								canMove = false;
							}
						}
						// Check double right
						checkRoleSprite = maps[currentRow][currentCol + 2];
						if (null != checkRoleSprite) {
							canMove = false;
						}
						// Check lower double right
						if (selectedRoleSprite.getRoleHeight() == 2) {
							checkRoleSprite = maps[currentRow + 1][currentCol + 2];
							if (null != checkRoleSprite) {
								canMove = false;
							}
						}
					} else {
						canMove = false;
					}
				}
			} else {
				canMove = false;
			}
			if (canMove) {
				maps[currentRow][currentCol] = null;
				maps[currentRow][currentCol + 1] = selectedRoleSprite;
				selectedRoleSprite.setCol(currentCol + 1);
				step++;
			}
			break;
		case KeyCode.UP:
			if (currentRow > 0) {
				if (currentRow > 1) {
					// 1. Check double upper left
					if (currentCol > 0) {
						checkRoleSprite = maps[currentRow - 2][currentCol - 1];
						if (null != checkRoleSprite && checkRoleSprite.getRoleHeight() == 2 && checkRoleSprite.getRoleWidth() == 2) {
							canMove = false;
						}
					}
					// 2. Check double upper
					checkRoleSprite = maps[currentRow - 2][currentCol];
					if (null != checkRoleSprite && checkRoleSprite.getRoleHeight() == 2) {
						canMove = false;
					}
					// 3. Check double upper right
					if (selectedRoleSprite.getRoleWidth() == 2) {
						checkRoleSprite = maps[currentRow - 2][currentCol + 1];
						if (null != checkRoleSprite && checkRoleSprite.getRoleHeight() == 2) {
							canMove = false;
						}
					}
				}
				// 4. Check upper left
				if (currentCol > 0) {
					checkRoleSprite = maps[currentRow - 1][currentCol - 1];
					if (null != checkRoleSprite && checkRoleSprite.getRoleWidth() == 2) {
						canMove = false;
					}
				}
				// 5. Check upper
				checkRoleSprite = maps[currentRow - 1][currentCol];
				if (null != checkRoleSprite) {
					canMove = false;
				}
				// 6. Check upper right
				if (selectedRoleSprite.getRoleWidth() == 2) {
					checkRoleSprite = maps[currentRow - 1][currentCol + 1];
					if (null != checkRoleSprite) {
						canMove = false;
					}
				}
			} else {
				canMove = false;
			}
			if (canMove) {
				maps[currentRow][currentCol] = null;
				maps[currentRow - 1][currentCol] = selectedRoleSprite;
				selectedRoleSprite.setRow(currentRow - 1);
				step++;
			}
			break;
		case KeyCode.DOWN:
			if (currentRow + 1 < GameSettings.MAP_ROW) {
				// 1. Check lower left
				if (currentCol > 0) {
					checkRoleSprite = maps[currentRow + 1][currentCol - 1];
					if (null != checkRoleSprite && checkRoleSprite.getRoleWidth() == 2) {
						canMove = false;
					}
				}
				// 2. Check lower
				checkRoleSprite = maps[currentRow + 1][currentCol];
				if (null != checkRoleSprite) {
					canMove = false;
				}
				// 3. Check lower right
				if (selectedRoleSprite.getRoleWidth() == 2) {
					checkRoleSprite = maps[currentRow + 1][currentCol + 1];
					if (null != checkRoleSprite) {
						canMove = false;
					}
				}
				if (selectedRoleSprite.getRoleHeight() == 2) {
					if (currentRow + 2 < GameSettings.MAP_ROW) {
						// 4. Check double lower left
						if (currentCol > 0) {
							checkRoleSprite = maps[currentRow + 2][currentCol - 1];
							if (null != checkRoleSprite && checkRoleSprite.getRoleWidth() == 2) {
								canMove = false;
							}
						}
						// 5. Check double lower
						checkRoleSprite = maps[currentRow + 2][currentCol];
						if (null != checkRoleSprite) {
							canMove = false;
						}
						// 6. Check double lower right
						if (selectedRoleSprite.getRoleWidth() == 2) {
							checkRoleSprite = maps[currentRow + 2][currentCol + 1];
							if (null != checkRoleSprite) {
								canMove = false;
							}
						}
					} else {
						canMove = false;
					}
				}
			} else {
				canMove = false;
			}
			if (canMove) {
				maps[currentRow][currentCol] = null;
				maps[currentRow + 1][currentCol] = selectedRoleSprite;
				selectedRoleSprite.setRow(currentRow + 1);
				step++;
			}
			break;
		}
		updateStep();
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	private void moveCurser(int dir) {
		int currentRow = selectedRoleSprite.getRow();
		int currentCol = selectedRoleSprite.getCol();
		RoleSprite nextRoleSprite = null;
		switch (dir) {
		case KeyCode.LEFT:
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
			}
			break;
		case KeyCode.RIGHT:
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
			}
			break;
		case KeyCode.UP:
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
			}
			break;
		case KeyCode.DOWN:
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
			}
			break;
		}
		if (null == nextRoleSprite) {
			nextRoleSprite = selectedRoleSprite;
		}
		selectedRoleSprite.setSelected(false);
		selectedRoleSprite = nextRoleSprite;
		selectedRoleSprite.setSelected(true);
		layerManager.paint(graphics, 0, 0);
		this.flushGraphics();
	}

	private void updateStep() {
		try {
			if (step > 9999) {
				step = 9999;
			}
			if (null != stepSprite && 0 < stepSprite.size()) {
				for (int i = 0; i < stepSprite.size(); i++) {
					layerManager.remove((Sprite) stepSprite.elementAt(i));
				}
			}
			stepSprite = NumberImgUtil.updateNumber(step, numberStepImg, GameSettings.STEP_NUMBER_X, GameSettings.STEP_NUMBER_Y, Graphics.TOP
					| Graphics.HCENTER);
			if (null != stepSprite && 0 < stepSprite.size()) {
				for (int i = 0; i < stepSprite.size(); i++) {
					layerManager.insert((Sprite) stepSprite.elementAt(i), 0);
				}
			}
			layerManager.paint(graphics, 0, 0);
			this.flushGraphics();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateGoal() {
		try {
			if (goal > 9999) {
				goal = 9999;
			}
			if (null != goalSprite && 0 < goalSprite.size()) {
				for (int i = 0; i < goalSprite.size(); i++) {
					layerManager.remove((Sprite) goalSprite.elementAt(i));
				}
			}
			goalSprite = NumberImgUtil.updateNumber(goal, numberGoalImg, GameSettings.GOAL_NUMBER_X, GameSettings.GOAL_NUMBER_Y, Graphics.TOP
					| Graphics.HCENTER);
			if (null != goalSprite && 0 < goalSprite.size()) {
				for (int i = 0; i < goalSprite.size(); i++) {
					layerManager.insert((Sprite) goalSprite.elementAt(i), 0);
				}
			}
			layerManager.paint(graphics, 0, 0);
			this.flushGraphics();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
