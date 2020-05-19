package com.zhongdan.games.wuziqi;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import org.json.me.JSONObject;

public class AiService {

	private String url;
	private JSONObject requestBody;

	public static JSONObject calNextStep(String url, JSONObject requestBody) {
		HttpConnection connection = null;
		InputStream is = null;
		DataOutputStream dos = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] response = null;
		String resultString = null;
		JSONObject resultJson = null;

		try {
			connection = (HttpConnection) Connector.open(url, Connector.READ_WRITE);
			connection.setRequestMethod(HttpConnection.POST);
			connection.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
			connection.setRequestProperty("Content-Type", "application/json");
			String data = requestBody.toString();
			if (data != null) {
				connection.setRequestProperty("Content-Length", "" + data.length());
			}
			if (data != null) {
				dos = connection.openDataOutputStream();
				byte[] requestBodyInByte = data.getBytes();
				for (int i = 0; i < requestBodyInByte.length; i++) {
					dos.writeByte(requestBodyInByte[i]);
				}
				dos.flush();
			}
			if (connection.getResponseCode() == HttpConnection.HTTP_OK) {
				is = connection.openInputStream();
				if (is != null) {
					int ch = -1;
					while ((ch = is.read()) != -1) {
						bos.write(ch);
					}
					response = bos.toByteArray();
					resultString = new String(response);
					resultJson = new JSONObject(resultString);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.close();
					bos = null;
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
		return resultJson;
	}

	public void run() {
		AiService.calNextStep(url, requestBody);
	}

}
