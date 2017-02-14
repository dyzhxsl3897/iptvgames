package com.zhongdan.games.paopaolong;

import java.io.IOException;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

public class MusicUtil {

	public Player createMusic(String musicPath) {
		Player player = null;
		try {
			player = Manager.createPlayer(getClass().getResourceAsStream(musicPath), "audio/x-wav");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
		return player;
	}

	public void musicStart(Player player) {
		try {
			player.start();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

	public void musicStop(Player player) {
		try {
			player.stop();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

}
