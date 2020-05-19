package com.yunyouhudong.framework.resourcemanagement.audios;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

import com.yunyouhudong.framework.constants.GameProps;
import com.yunyouhudong.framework.http.HttpClient;

public class AudioUtil {

	private static final String audioApiUrl = GameProps.getProperty("audiourl");

	public static Player createAudioFromLocal(String audioName) {
		Player player = null;
		try {
			player = Manager.createPlayer(AudioUtil.class.getResourceAsStream(audioName), "audio/x-wav");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
		return player;
	}

	public static Player createAudioFromServer(String gameName, String audioName) {
		Player player = null;
		InputStream audioInputStream = null;
		try {
			Hashtable params = new Hashtable();
			params.put("gamename", gameName);
			params.put("audioname", audioName);

			byte[] audioByte = HttpClient.httpGetResource(audioApiUrl, params);
			if (audioByte != null && audioByte.length > 0) {
				audioInputStream = new ByteArrayInputStream(audioByte);
			}

			player = Manager.createPlayer(audioInputStream, "audio/x-wav");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		} finally {
			if (audioInputStream != null) {
				try {
					audioInputStream.close();
					audioInputStream = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return player;
	}

}
