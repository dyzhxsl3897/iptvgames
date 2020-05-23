package com.yunyouhudong.framework.constants;

import com.yunyouhudong.framework.game.YunyouMIDlet;
import com.yunyouhudong.framework.utils.StringUtil;

public class GameProps {

	private static YunyouMIDlet mid;

	public static void initializeProps(YunyouMIDlet midlet) {
		mid = midlet;
	}

	public static String getProperty(String key) {
		return StringUtil.defaultIfEmpty(mid.getAppProperty(key));
	}

}
