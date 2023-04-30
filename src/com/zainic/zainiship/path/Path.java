package com.zainic.zainiship.path;

public class Path {
	
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
	
}
