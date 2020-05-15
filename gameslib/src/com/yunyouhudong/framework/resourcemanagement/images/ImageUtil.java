package com.yunyouhudong.framework.resourcemanagement.images;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javax.microedition.lcdui.Image;

import com.yunyouhudong.framework.constants.GameProps;
import com.yunyouhudong.framework.http.HttpClient;

public class ImageUtil {

	private static final String imageApiUrl = GameProps.getProperty("imageurl");

	public static Image createImageFromLocal(String imageName) {
		Image img = null;
		try {
			img = Image.createImage(imageName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static Image createImageFromServer(String gameName, String imageName) {
		Image img = null;
		InputStream imageInputStream = null;
		try {
			Hashtable params = new Hashtable();
			params.put("gamename", gameName);
			params.put("imagename", imageName);

			byte[] imageByte = HttpClient.httpGetResource(imageApiUrl, params);
			if (imageByte != null && imageByte.length > 0) {
				imageInputStream = new ByteArrayInputStream(imageByte);
			}

			img = Image.createImage(imageInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (imageInputStream != null) {
				try {
					imageInputStream.close();
					imageInputStream = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return img;
	}

}
