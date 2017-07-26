package com.zhongdan.games.tetris;

import java.util.Random;
import java.util.TimerTask;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

public class DropdownTask extends TimerTask {

	private MyGameCanvas canvas;
	private Graphics graphics;

	public DropdownTask(MyGameCanvas canvas, Graphics graphics) {
		this.canvas = canvas;
		this.graphics = graphics;
	}

	public void run() {
		LayerManager layerManager = canvas.getLayerManager();
		BrickItem movingBrick = canvas.getMovingBrick();
		BrickItem nextBrick = canvas.getNextBrick();
		boolean canMoveDown = movingBrick.moveDown();
		if (canMoveDown) {
			layerManager.paint(graphics, 0, 0);
			canvas.flushGraphics();
		} else {
			// Clear lines
			int clearedLineNo = clearLines();
			// Update score
			canvas.setScore(canvas.getScore() + MyGameConstants.GameSettings.SCORE[clearedLineNo] * canvas.getCurrentLevel());
			canvas.setLine(canvas.getLine() + clearedLineNo);
			// Remove current nextBrick
			for (int i = 0; i < canvas.getNextBrick().getSprites().size(); i++) {
				Sprite sprite = (Sprite) canvas.getNextBrick().getSprites().elementAt(i);
				layerManager.remove(sprite);
			}
			// Play next brick
			int type = nextBrick.getType();
			int dir = nextBrick.getDir();
			canvas.setMovingBrick(BrickItem.createNewBrick(type, dir, canvas, false));
			Random rnd = new Random();
			type = (rnd.nextInt() >>> 1) % 7;
			dir = (rnd.nextInt() >>> 1) % 4;
			canvas.setNextBrick(BrickItem.createNewBrick(type, dir, canvas, true));
			// Repaint
			layerManager.paint(graphics, 0, 0);
			canvas.flushGraphics();
			// Check if game over
			if (!canvas.getMovingBrick().canMove(0, MyGameConstants.Brick.HEIGHT)) {
				canvas.setPlaying(false);
				cancel();
			}
		}
	}

	private int clearLines() {
		int clearedLineNo = 0;
		LayerManager layerManager = canvas.getLayerManager();
		Sprite[][] allBrickSprite = canvas.getAllBrickSprite();
		BrickItem movingBrick = canvas.getMovingBrick();
		Vector movingSprites = movingBrick.getSprites();
		// First put moving brick into whole board
		for (int i = 0; i < movingSprites.size(); i++) {
			try {
				Sprite sprite = (Sprite) movingSprites.elementAt(i);
				int x = sprite.getX();
				int y = sprite.getY();
				int row = (y - MyGameConstants.Brick.START_TOP) / MyGameConstants.Brick.HEIGHT;
				int col = (x - MyGameConstants.Brick.START_LEFT) / MyGameConstants.Brick.WIDTH;
				allBrickSprite[row][col] = sprite;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Remove the line if it's full
		for (int i = MyGameConstants.Playboard.ROW_NO - 1; i >= 0; i--) {
			boolean isLineFull = true;
			for (int j = 0; j < MyGameConstants.Playboard.COL_NO; j++) {
				if (allBrickSprite[i][j] == null) {
					isLineFull = false;
					break;
				}
			}
			if (isLineFull) {
				clearedLineNo++;
				// Clear the line
				for (int col = 0; col < MyGameConstants.Playboard.COL_NO; col++) {
					layerManager.remove(allBrickSprite[i][col]);
					allBrickSprite[i][col] = null;
				}
				// Move all above bricks down for a line
				for (int row = i - 1; row >= 0; row--) {
					for (int col = 0; col < MyGameConstants.Playboard.COL_NO; col++) {
						allBrickSprite[row + 1][col] = allBrickSprite[row][col];
						if (allBrickSprite[row][col] != null) {
							int x = allBrickSprite[row][col].getX();
							int y = allBrickSprite[row][col].getY();
							allBrickSprite[row][col].setPosition(x, y + MyGameConstants.Brick.WIDTH);
						}
					}
				}
				i++;
			}
		}
		return clearedLineNo;
	}
}
