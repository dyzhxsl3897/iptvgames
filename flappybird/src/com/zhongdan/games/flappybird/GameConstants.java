package com.zhongdan.games.flappybird;

public class GameConstants {

	public static class GameStatus {
		public final static int START = 0;
		public final static int PLAYING = 1;
		public final static int LOST = 2;
		public final static int PAUSE = 3;
	}

	public static class GameSettings {
		public static int TIMER = 60;// Refresh the screen every 20ms
		public static int GRAVITY = 2;
	}

	public static class Pipe {
		public static int START_POS_X = 640;
		public static int WIDTH = 45;
		public static int HEIGHT = 25;
		public static int DISTANCE = 200;
		public static int TOTAL = 10;
		public static int SPEED = 4;
	}

	public static class Bird {
		public static int WIDTH = 30;
		public static int HEIGHT = 21;
		public static int MAX_V_SPEED = 25;
	}

}
