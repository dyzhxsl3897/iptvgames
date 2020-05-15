package com.yunyouhudong.framework.http;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import com.yunyouhudong.framework.json.JSONObject;
import com.yunyouhudong.framework.utils.StringUtils;

public class HttpClient {
	public static JSONObject httpGet(String url, Hashtable params) {
		JSONObject result = null;

		String builtParams = buildParameters(params);

		result = httpRequest(url + builtParams, HttpConnection.GET, null);

		return result;
	}

	public static JSONObject httpPost(String url, JSONObject requestJson) {
		JSONObject result = null;

		result = httpRequest(url, HttpConnection.POST, requestJson);

		return result;
	}

	private static JSONObject httpRequest(String url, String httpMethod, JSONObject requestJson) {
		HttpConnection connection = null;

		InputStream is = null;
		DataOutputStream dos = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		String requestDataInString = requestJson == null ? StringUtils.EMTPY : requestJson.toString();

		byte[] response = null;
		String responseDataInString = null;
		JSONObject responseJson = null;

		try {
			connection = (HttpConnection) Connector.open(url, Connector.READ_WRITE);
			connection.setRequestMethod(httpMethod);
			connection.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
			connection.setRequestProperty("Content-Type", "application/json");
			if (requestDataInString != null) {
				connection.setRequestProperty("Content-Length", "" + requestDataInString.length());
				dos = connection.openDataOutputStream();
				byte[] requestDataInByte = requestDataInString.getBytes();
				for (int i = 0; i < requestDataInByte.length; i++) {
					dos.writeByte(requestDataInByte[i]);
				}
				dos.flush();
			}
			if (connection.getResponseCode() == HttpConnection.HTTP_OK) {
				is = connection.openInputStream();
				if (is != null) {
					int character = -1;
					while ((character = is.read()) != -1) {
						baos.write(character);
					}
					response = baos.toByteArray();
					responseDataInString = new String(response);
					responseJson = new JSONObject(responseDataInString);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.close();
					baos = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return responseJson;
	}

	private static String buildParameters(Hashtable params) {
		if (params == null || params.isEmpty()) {
			return StringUtils.EMTPY;
		}

		Enumeration keys = params.keys();

		StringBuffer buffer = new StringBuffer();
		buffer.append("?");
		boolean isFirstElement = true;
		while (keys.hasMoreElements()) {
			String key = String.valueOf(keys.nextElement());
			String value = String.valueOf(params.get(key));
			if (StringUtils.isNullOrEmpty(key) || StringUtils.isNullOrEmpty(value)) {
				continue;
			}
			if (!isFirstElement) {
				buffer.append("&");
			} else {
				isFirstElement = false;
			}
			buffer.append(key);
			buffer.append("=");
			buffer.append(value);
		}

		return buffer.toString();
	}
}
