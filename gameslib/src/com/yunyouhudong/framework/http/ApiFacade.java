package com.yunyouhudong.framework.http;

import java.util.Hashtable;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.yunyouhudong.framework.constants.GameProps;
import com.yunyouhudong.framework.utils.StringUtils;

public class ApiFacade {

	/*
	private static final String apiBaseUrl = GameProps.getProperty("apiurl");

	public static int getUserLoginTimes(String userId) {
		String url = ApiRoute.User.GET_USER_LOGIN_TIMES;
		Hashtable params = new Hashtable();
		params.put("userid", userId);

		JSONObject responseJson = HttpClient.httpGet(apiBaseUrl + url, params);
		int result = 0;
		try {
			String loginTimes = String.valueOf(responseJson.get("loginTimes"));
			if (!StringUtils.isNullOrEmpty(loginTimes)) {
				result = Integer.parseInt(loginTimes);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}
*/
}
