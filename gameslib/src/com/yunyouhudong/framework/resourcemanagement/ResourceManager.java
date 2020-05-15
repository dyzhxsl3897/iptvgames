package com.yunyouhudong.framework.resourcemanagement;

import java.util.Hashtable;

import javax.microedition.lcdui.Image;
import javax.microedition.media.Player;

import com.yunyouhudong.framework.game.YunyouGameCanvas;
import com.yunyouhudong.framework.resourcemanagement.audios.AudioUtil;
import com.yunyouhudong.framework.resourcemanagement.images.ImageUtil;

public class ResourceManager {

	private YunyouGameCanvas gameCanvas;
	private String gameName;

	private Hashtable imageResources;
	private Hashtable audioResources;

	public ResourceManager(YunyouGameCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
		this.gameName = this.gameCanvas.getMidlet().getGameName();

		this.imageResources = new Hashtable();
		this.audioResources = new Hashtable();
	}

	public Image loadImageFromLocal(String imageName) {
		Image image = ImageUtil.createImageFromLocal(imageName);
		if (null != image)
			this.imageResources.put(imageName, image);
		return image;
	}

	public Image loadImageFromServer(String imageName) {
		Image image = ImageUtil.createImageFromServer(this.gameName, imageName);
		if (null != image)
			this.imageResources.put(imageName, image);
		return image;
	}

	public Player loadAudioFromLocal(String audioName) {
		Player player = AudioUtil.createAudioFromLocal(audioName);
		if (null != player)
			this.audioResources.put(audioName, player);
		return player;
	}

	public Player loadAudioFromServer(String audioName) {
		Player player = AudioUtil.createAudioFromServer(this.gameName, audioName);
		if (null != player)
			this.audioResources.put(audioName, player);
		return player;
	}

	public Image getImage(String imageName) {
		if (this.imageResources.containsKey(imageName)) {
			return (Image) this.imageResources.get(imageName);
		} else {
			return null;
		}
	}

	public Player getAudio(String audioName) {
		if (this.audioResources.containsKey(audioName)) {
			return (Player) this.audioResources.get(audioName);
		} else {
			return null;
		}
	}

	public void clearImageResource() {
		this.imageResources.clear();
	}

	public void clearAudioResource() {
		this.audioResources.clear();
	}

	public void clearAllResource() {
		this.clearImageResource();
		this.clearAudioResource();
		System.gc();
	}

}
