package com.zhongdan.games.wuziqi;

import java.util.Vector;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

public class JsonUtil {

	public static JSONObject gatherInfo(IPlayer humanPlayer, IPlayer aiPlayer) throws JSONException {
		JSONObject result = new JSONObject();

		Vector humanPoints = humanPlayer.getMyPoints();
		JSONArray humanPointsArray = new JSONArray();
		if (null != humanPoints) {
			try {
				for (int i = 0; i < humanPoints.size(); i++) {
					Point point = (Point) humanPoints.elementAt(i);
					JSONObject pointJson = new JSONObject();
					pointJson.put("x", point.getX());
					pointJson.put("y", point.getY());
					humanPointsArray.put(pointJson);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				throw e;
			}
		}

		Vector aiPoints = aiPlayer.getMyPoints();
		JSONArray aiPointsArray = new JSONArray();
		if (null != aiPoints) {
			try {
				for (int i = 0; i < aiPoints.size(); i++) {
					Point point = (Point) aiPoints.elementAt(i);
					JSONObject pointJson = new JSONObject();
					pointJson.put("x", point.getX());
					pointJson.put("y", point.getY());
					aiPointsArray.put(pointJson);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				throw e;
			}
		}
		try {
			result.put("humanPoints", humanPointsArray);
			result.put("aiPoints", aiPointsArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

}
