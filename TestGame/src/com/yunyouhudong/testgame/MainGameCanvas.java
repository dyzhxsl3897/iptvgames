package com.yunyouhudong.testgame;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.midlet.MIDlet;

import com.yunyouhudong.framework.constants.GameProps;
import com.yunyouhudong.framework.constants.KeyCode;
import com.yunyouhudong.framework.http.ApiFacade;
import com.yunyouhudong.framework.resourcemanagement.audios.AudioUtil;
import com.yunyouhudong.framework.resourcemanagement.images.ImageUtil;

public class MainGameCanvas extends GameCanvas {

	private MIDlet midlet;
	private Graphics graphics;
	private int line = 0;
	private int start = 20;
	private int height = 15;
	private LayerManager layerManager = new LayerManager();
	Player player1 = AudioUtil.createAudioFromServer("TestGame", "game_bg_music.wav");
	Player player2 = AudioUtil.createAudioFromServer("TestGame", "score.wav");

	protected MainGameCanvas(MIDlet midlet) {
		super(false);
		this.setFullScreenMode(true);
		this.graphics = this.getGraphics();

		String userId = GameProps.getProperty("userid");
		drawString(userId);

		int loginTimes = ApiFacade.getUserLoginTimes(userId);
		drawString(String.valueOf(loginTimes));

		Image image = ImageUtil.createImageFromServer("TestGame", "fight_stage_bg.jpg");
		Sprite sprite = new Sprite(image);
		layerManager.append(sprite);
		layerManager.paint(graphics, 0, 0);

		try {
			player1.realize();
			player1.prefetch();
			player2.realize();
			player2.prefetch();
			player1.start();
		} catch (MediaException e) {
			e.printStackTrace();
		}

		this.flushGraphics();
	}

	protected void keyPressed(int keyCode) {
		if (KeyCode.OK.contains(new Integer(keyCode))) {
			try {
				player2.start();
			} catch (MediaException e) {
				e.printStackTrace();
			}
		}
	}

	private void drawString(String string) {
		this.graphics.drawString(string, start, nextLine(), 0);
		this.flushGraphics();
	}

	private int nextLine() {
		return line++ * height;
	}

}
