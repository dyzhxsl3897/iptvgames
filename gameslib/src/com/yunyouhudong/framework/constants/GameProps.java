package com.yunyouhudong.framework.constants;

import javax.microedition.midlet.MIDlet;

import com.yunyouhudong.framework.utils.StringUtils;

public class GameProps {

	private static MIDlet mid;

	public static void initializeProps(MIDlet midlet) {
		mid = midlet;
	}

	public static String getProperty(String key) {
		return StringUtils.defaultIfEmpty(mid.getAppProperty(key));
	}
}
