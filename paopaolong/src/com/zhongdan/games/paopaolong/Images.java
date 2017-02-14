package com.zhongdan.games.paopaolong;

import java.io.IOException;

import javax.microedition.lcdui.Image;

public class Images {

	public static Image background;

	public static Image getBackground() {
		if (background == null) {
			try {
				background = Image.createImage("/background.png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return background;
	}

}
