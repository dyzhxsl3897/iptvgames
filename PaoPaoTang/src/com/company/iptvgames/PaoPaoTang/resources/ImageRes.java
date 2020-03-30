package com.company.iptvgames.PaoPaoTang.resources;

import java.util.Hashtable;

import javax.microedition.lcdui.Image;

public class ImageRes {

	Hashtable imageTable = new Hashtable();

	private ImageRes() {
	}

	private static class SingletonHolder {
		private static final ImageRes instance = new ImageRes();
	}

	public static ImageRes getInstance() {
		return SingletonHolder.instance;
	}

	public void loadImage(String imageName, Image image) {
		this.imageTable.put(imageName, image);
	}

	public Image getImage(String imageName) {
		return (Image) this.imageTable.get(imageName);
	}

}
