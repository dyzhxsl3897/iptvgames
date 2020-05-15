package com.yunyouhudong.framework.json;

public class JSONStringer extends JSONWriter {
	public JSONStringer() {
		super(new StringWriter());
	}

	public String toString() {
		return mode == 'd' ? writer.toString() : null;
	}
}