package com.company.iptvgames.framework.utils;

import java.util.Random;

public class RandomUtil {

	private static Random random = new Random();

	public static int nextInt(int n) {
		return random.nextInt(n);
	}

	public static int nextInt(int from, int to) {
		return random.nextInt(to - from) + from;
	}
}
