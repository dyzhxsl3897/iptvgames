package com.game.tank;

import java.util.Random;

public class Test {
	public static void main(String[] args) {
		Random rdm=new Random();
		for (int i = 0; i < 20; i++) {
			System.out.println(rdm.nextInt(4));
		}
	}
}
