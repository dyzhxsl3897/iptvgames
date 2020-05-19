package com.yunyouhudong.testgame;

import java.util.Vector;

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
import com.yunyouhudong.framework.utils.StringUtil;

public class MainGameCanvas extends YunyouGameCanvas {

	private LayerManager layerManager = new LayerManager();
	private Sprite bgSprite;

	public MainGameCanvas(YunyouMIDlet midlet, boolean suppressKeyEvents) {
		super(midlet, suppressKeyEvents);

		loadResources();

		startGame();

		exitGame();
	}

	private void exitGame() {
	}

	private void startGame() {
		this.layerManager.append(bgSprite);
		this.layerManager.paint(this.getGraphics(), 0, 0);
		this.flushGraphics();
		Image img = this.getResourceManager().getImage("fight_stage_bg.jpg");

		String userId = GameProps.getProperty("userid");
		int loginTimes = ApiFacade.getUserLoginTimes(userId);

		System.out.println(userId + "logged in " + String.valueOf(loginTimes) + " time(s)");

		Vector strs = new Vector();
		strs.addElement("a");
		strs.addElement("b");
		strs.addElement("c");
		System.out.println(StringUtil.join(strs.elements(), ","));
	}

	protected void keyPressed(int keyCode) {
		if (KeyCode.OK.contains(new Integer(keyCode))) {
			Player ply = this.getResourceManager().getAudio("score.wav");
			try {
				if (ply.getState() != Player.STARTED) {
					ply.setLoopCount(-1);
					ply.start();
				}
			} catch (MediaException e) {
				e.printStackTrace();
			}
		} else if (KeyCode.UP.contains(new Integer(keyCode))) {
			Player ply = this.getResourceManager().getAudio(GameConstants.AUDIO_NAME);
			try {
				ply.stop();
				ply.setMediaTime(0);
			} catch (MediaException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loading(int progress) {
		// ....
	}

	private void loadResources() {
		Image img = this.getResourceManager().loadImageFromServer("fight_stage_bg.jpg");
		bgSprite = new Sprite(img);
		loading(10);

		Player ply = this.getResourceManager().loadAudioFromServer("score.wav");
		try {
			ply.realize();
			ply.prefetch();
		} catch (MediaException e) {
			e.printStackTrace();
		}
		loading(100);
	}
}
