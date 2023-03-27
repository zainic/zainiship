package com.zainic.zainiship.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener{

	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	private static boolean mouseInScreen = false;
	
	public static final int RMB = 3;
	public static final int LMB = 1;
	
	public static int getX() {
		return mouseX;
	}

	public static int getY() {
		return mouseY;
	}
	
	public static int getB() {
		return mouseB;
	}
	
	public static boolean isInsideScreen() {
		return mouseInScreen;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseB = -1;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseInScreen = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseInScreen = false;
	}

}
