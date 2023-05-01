package com.zainic.zainiship.path;

public class Path {
	
	// -- Path 1 ------------------------------------------------
	public static double pathOneLeftX(double t) {
		return (0.025*t + 1);
	}
	
	public static double pathOneLeftY(double t) {
		return (3 - 0.025*t);
	}
	
	public static double pathOneRightX(double t) {
		return (-0.025*t - 1);
	}
	
	public static double pathOneRightY(double t) {
		return (3 - 0.025*t);
	}
	
	// -- Path 2 ------------------------------------------------
	public static double pathTwoX(double t) {
		return 0;
	}
	
	public static double pathTwoY(double t) {
		return 0.0015*t*t - 0.12*t + 3;
	}
	
	// -- Path 3 ------------------------------------------------
	public static double pathThreeLeftX(double t) {
		return 3*Math.sin(t*0.1);
	}
	
	public static double pathThreeLeftY(double t) {
		return 1;
	}
	
	public static double pathThreeRightX(double t) {
		return -3*Math.sin(t*0.1);
	}
	
	public static double pathThreeRightY(double t) {
		return 1;
	}
	
}
