package com.yunyouhudong.framework.utils;

import java.util.Enumeration;

public class StringUtil {

	public static String EMTPY = "";

	/**
	 * Return true if str is null, or "", or "null"(ignore case)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		return (null == str || "".equals(str) || "null".equalsIgnoreCase(str));
	}

	/**
	 * Return "" if str is empty, otherwise return str
	 * 
	 * @param str
	 * @return
	 */
	public static String defaultIfEmpty(String str) {
		if (!isNullOrEmpty(str)) {
			return str;
		}
		return StringUtil.EMTPY;
	}

	/**
	 * Return "" if both str1 and str2 are empty<br>
	 * Return str1 if str1 is not empty<br>
	 * Return str2 if str1 is empty and str2 is not empty
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String defaultIfEmpty(String str1, String str2) {
		if (!isNullOrEmpty(str1)) {
			return str1;
		}
		if (!isNullOrEmpty(str2)) {
			return str2;
		}
		return StringUtil.EMTPY;
	}

	/**
	 * This method will return all strings present in collection, joined by separator
	 * 
	 * @param collection
	 * @param separator
	 * @return
	 */
	public static String join(Enumeration collection, String separator) {
		if (collection == null || !collection.hasMoreElements()) {
			return StringUtil.EMTPY;
		}

		StringBuffer buffer = new StringBuffer();
		boolean isFirstElement = true;
		while (collection.hasMoreElements()) {
			if (!isFirstElement) {
				buffer.append(separator);
			} else {
				isFirstElement = false;
			}
			buffer.append(collection.nextElement());
		}

		return buffer.toString();
	}

}
