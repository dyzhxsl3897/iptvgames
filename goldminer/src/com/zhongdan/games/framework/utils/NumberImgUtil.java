package com.zhongdan.games.framework.utils;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class NumberImgUtil {

	public static void updateNumber(Graphics graphics, int number, Image numberImg, int x, int y, int pos) throws Exception {
		Vector numberSprites = new Vector();

		int width = numberImg.getWidth();
		int height = numberImg.getHeight();

		int numberSplit = number;
		int digitNo = 0;
		if (number > 0) {
			while (numberSplit > 0) {
				Sprite s = new Sprite(numberImg, width / 11, height);
				s.setFrame(numberSplit % 10);
				numberSprites.addElement(s);
				numberSplit /= 10;
				digitNo++;
			}
		} else {
			digitNo++;
			Sprite s = new Sprite(numberImg, width / 11, height);
			s.setFrame(0);
			numberSprites.addElement(s);
		}

		if (pos == (Graphics.LEFT | Graphics.TOP)) {
			for (int i = digitNo - 1; i >= 0; i--) {
				Sprite s = (Sprite) numberSprites.elementAt(i);
				int posX = x + width / 11 * (digitNo - i - 1);
				int posY = y;
				s.setPosition(posX, posY);
				graphics.drawRegion(numberImg, width / 11 * s.getFrame(), 0, width / 11, height, Sprite.TRANS_NONE, posX, posY, Graphics.TOP
						| Graphics.LEFT);
			}
		} else if (pos == (Graphics.RIGHT | Graphics.TOP)) {
			for (int i = 0; i < digitNo; i++) {
				Sprite s = (Sprite) numberSprites.elementAt(i);
				int posX = x - width / 11 * i;
				int posY = y;
				s.setPosition(posX, posY);
				graphics.drawRegion(numberImg, width / 11 * s.getFrame(), 0, width / 11, height, Sprite.TRANS_NONE, posX, posY, Graphics.TOP
						| Graphics.LEFT);
			}
		} else if (pos == (Graphics.HCENTER | Graphics.TOP)) {
			for (int i = digitNo - 1; i >= 0; i--) {
				Sprite s = (Sprite) numberSprites.elementAt(i);
				int posX = x + width / 11 * (digitNo - i - 1);
				int posY = y;
				posX -= ((digitNo % 2 == 0) ? digitNo / 2 * width / 10 : digitNo / 2 * width / 10 + width / 20);
				s.setPosition(posX, posY);
				graphics.drawRegion(numberImg, width / 11 * s.getFrame(), 0, width / 11, height, Sprite.TRANS_NONE, posX, posY, Graphics.TOP
						| Graphics.LEFT);
			}
		} else {
			throw new Exception("Number Image has error!");
		}
	}

}
