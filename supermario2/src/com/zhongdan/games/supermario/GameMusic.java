package com.zhongdan.games.supermario;
import java.io.IOException;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;


public class GameMusic {
	Player pla;
	
	public Player musiclong(String s) {
		try {
			pla = Manager.createPlayer(getClass().getResourceAsStream("/"+s+".mid"),"audio/midi");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
		return pla;
	}

	public void musicstart(Player music) {
		try {
			music.start();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}
	public void musicstop(Player music) {
		try {
			music.stop();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}
}
