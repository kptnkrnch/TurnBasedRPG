package engine;

import graphics.AnimationController;

public class Direction {
	public static final int NONE = -1;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	public static int Opposite(int direction) {
		switch(direction) {
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		}
		return 0;
	}
	
	public static boolean IsOpposite(int direction1, int direction2) {
		if (Opposite(direction1) == direction2) {
			return true;
		} else {
			return false;
		}
	}
	
	public static int ResolveAnimation(String animation) {
		switch(animation) {
		case AnimationController.LEFT:
			return LEFT;
		case AnimationController.RIGHT:
			return RIGHT;
		case AnimationController.UP:
			return UP;
		case AnimationController.DOWN:
			return DOWN;
		}
		return -1;
	}
}
