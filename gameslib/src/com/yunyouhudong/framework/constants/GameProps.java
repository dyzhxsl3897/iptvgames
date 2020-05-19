package com.yunyouhudong.framework.constants;

import javax.microedition.midlet.MIDlet;

import com.sun.midp.io.Properties;
import com.yunyouhudong.framework.utils.StringUtil;

public class GameProps {

	private static MIDlet mid;
	private static Properties props = new Properties();

	public static void initializeProps(MIDlet midlet) {
		mid = midlet;
	}

	public static String getProperty(String key) {
		return StringUtil.defaultIfEmpty(mid.getAppProperty(key), props.getProperty(key));
	}

	public static void setProperty(String key, String value) {
		props.setProperty(key, value);
	}
}
