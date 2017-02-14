package com.zhongdan.games.paopaolong;

import java.io.IOException;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class ArrowSprite {

	private int angle;
	private Sprite sprite;

	public ArrowSprite createArrow() {
		Image img = null;
		try {
			img = Image.createImage("/arrows.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.angle = 0;
		this.sprite = new Sprite(img, 142, 82);
		sprite.setFrame(1);
		return this;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
		int frameSeqLength = sprite.getFrameSequenceLength();
		if (angle >= 0) {
			sprite.setFrame((angle / MyGameConstants.GameSettings.ANGLE_SINGLE_MOVE + frameSeqLength) % frameSeqLength);
		} else {
			sprite.setFrame((angle / MyGameConstants.GameSettings.ANGLE_SINGLE_MOVE + frameSeqLength) % (frameSeqLength / 2) + frameSeqLength / 2);
		}
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}
