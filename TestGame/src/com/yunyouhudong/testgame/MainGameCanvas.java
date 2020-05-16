package com.yunyouhudong.testgame;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

import com.yunyouhudong.framework.constants.GameProps;
import com.yunyouhudong.framework.constants.KeyCode;
import com.yunyouhudong.framework.game.YunyouGameCanvas;
import com.yunyouhudong.framework.game.YunyouMIDlet;
import com.yunyouhudong.framework.http.ApiFacade;

public class MainGameCanvas extends YunyouGameCanvas {

	private int line = 0;
	private int start = 20;
	private int height = 15;
	private LayerManager layerManager = new LayerManager();

	public MainGameCanvas(YunyouMIDlet midlet, boolean suppressKeyEvents) {
		super(midlet, suppressKeyEvents);

		loadResource();

		startGame();
	}

	private void startGame() {
		String userId = GameProps.getProperty("userid");
		drawString(userId);

		int loginTimes = ApiFacade.getUserLoginTimes(userId);
		drawString(String.valueOf(loginTimes));

		Image image = this.getResourceManager().getImage("fight_stage_bg.jpg");
		Sprite sprite = new Sprite(image);
		layerManager.append(sprite);
		layerManager.paint(this.getGraphics(), 0, 0);

		this.flushGraphics();

		Player player1 = this.getResourceManager().getAudio("game_bg_music.wav");
		try {
			player1.start();
		} catch (MediaException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.getResourceManager().clearAllResource();
	}

	private void loadResource() {
		this.getResourceManager().loadImageFromServer("fight_stage_bg.jpg");

		Player player1 = this.getResourceManager().loadAudioFromServer("game_bg_music.wav");
		Player player2 = this.getResourceManager().loadAudioFromServer("score.wav");

		try {
			player1.realize();
			player1.prefetch();
			player2.realize();
			player2.prefetch();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

	protected void keyPressed(int keyCode) {
		if (KeyCode.OK.contains(new Integer(keyCode))) {
			try {
				Player hitAudio = this.getResourceManager().getAudio("score.wav");
				hitAudio.start();
			} catch (MediaException e) {
				e.printStackTrace();
			}
		}
	}

	private void drawString(String string) {
		this.getGraphics().drawString(string, start, nextLine(), 0);
		this.flushGraphics();
	}

	private int nextLine() {
		return line++ * height;
	}

}
