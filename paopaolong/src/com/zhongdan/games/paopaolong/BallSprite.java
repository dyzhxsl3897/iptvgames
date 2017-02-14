package com.zhongdan.games.paopaolong;

import java.io.IOException;
import java.util.Random;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class BallSprite {

	private int color;
	private Image image;
	private Sprite sprite;

	public BallSprite createBigRandomBall() {
		Random rnd = new Random();
		int color = (rnd.nextInt() >>> 1) % 4;
		if (color == MyGameConstants.BallColor.BLUE) {
			return createBigBlueBall();
		} else if (color == MyGameConstants.BallColor.PURPLE) {
			return createBigPurpleBall();
		} else if (color == MyGameConstants.BallColor.RED) {
			return createBigRedBall();
		} else {
			return createBigYellowBall();
		}
	}

	public BallSprite createSmallRandomBall() {
		Random rnd = new Random();
		int color = (rnd.nextInt() >>> 1) % 4;
		if (color == MyGameConstants.BallColor.BLUE) {
			return createSmallBlueBall();
		} else if (color == MyGameConstants.BallColor.PURPLE) {
			return createSmallPurpleBall();
		} else if (color == MyGameConstants.BallColor.RED) {
			return createSmallRedBall();
		} else {
			return createSmallYellowBall();
		}
	}

	public BallSprite createBigBlueBall() {
		try {
			this.image = ImageUtil.resizeImage(Image.createImage("/blueBall.png"), MyGameConstants.WaitingBall.WIDTH,
					MyGameConstants.WaitingBall.HEIGHT);
			this.sprite = new Sprite(this.image);
			this.color = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public BallSprite createSmallBlueBall() {
		try {
			this.image = ImageUtil.resizeImage(Image.createImage("/blueBall.png"), MyGameConstants.NextBall.WIDTH, MyGameConstants.NextBall.HEIGHT);
			this.sprite = new Sprite(this.image);
			this.color = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public BallSprite createBigPurpleBall() {
		try {
			this.image = ImageUtil.resizeImage(Image.createImage("/purpleBall.png"), MyGameConstants.WaitingBall.WIDTH,
					MyGameConstants.WaitingBall.HEIGHT);
			this.sprite = new Sprite(this.image);
			this.color = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public BallSprite createSmallPurpleBall() {
		try {
			this.image = ImageUtil.resizeImage(Image.createImage("/purpleBall.png"), MyGameConstants.NextBall.WIDTH, MyGameConstants.NextBall.HEIGHT);
			this.sprite = new Sprite(this.image);
			this.color = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public BallSprite createBigRedBall() {
		try {
			this.image = ImageUtil.resizeImage(Image.createImage("/redBall.png"), MyGameConstants.WaitingBall.WIDTH,
					MyGameConstants.WaitingBall.HEIGHT);
			this.sprite = new Sprite(this.image);
			this.color = 2;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public BallSprite createSmallRedBall() {
		try {
			this.image = ImageUtil.resizeImage(Image.createImage("/redBall.png"), MyGameConstants.NextBall.WIDTH, MyGameConstants.NextBall.HEIGHT);
			this.sprite = new Sprite(this.image);
			this.color = 2;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public BallSprite createBigYellowBall() {
		try {
			this.image = ImageUtil.resizeImage(Image.createImage("/yellowBall.png"), MyGameConstants.WaitingBall.WIDTH,
					MyGameConstants.WaitingBall.HEIGHT);
			this.sprite = new Sprite(this.image);
			this.color = 3;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public BallSprite createSmallYellowBall() {
		try {
			this.image = ImageUtil.resizeImage(Image.createImage("/yellowBall.png"), MyGameConstants.NextBall.WIDTH, MyGameConstants.NextBall.HEIGHT);
			this.sprite = new Sprite(this.image);
			this.color = 3;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}
