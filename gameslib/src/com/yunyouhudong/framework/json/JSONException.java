package com.yunyouhudong.framework.json;

public class JSONException extends Exception {

	private Throwable cause;

	public JSONException(String message) {
		super(message);
	}

	public JSONException(Throwable t) {
		super(t.getMessage());
		cause = t;
	}

	public Throwable getCause() {
		return cause;
	}

}
