package com.zainic.zainiship.path;

public class Path {
	
	public static int pathOneLeftX(int t) {
		return (int) (0.05*t + 1);
	}
	
	public static int pathOneLeftY(int t) {
		return (int) (5 - 0.05*t);
	}
	
	public static int pathOneRightX(int t) {
		return (int) (-0.05*t - 1);
	}
	
	public static int pathOneRightY(int t) {
		return (int) (5 - 0.05*t);
	}
	
}
