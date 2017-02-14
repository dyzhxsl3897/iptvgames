package com.zhongdan.games.supermario;
import java.io.IOException;

import javax.microedition.lcdui.Image;
/********本源码下载自源码爱好者_www.codefans.net*********/

public class GameImage {
	Image img;
	
	public Image imglong(String s) {
		try {
			img = Image.createImage("/"+s+".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
