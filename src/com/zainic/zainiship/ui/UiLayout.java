package com.zainic.zainiship.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/** Converts coordinates from a menu's fixed design resolution to the display. */
public final class UiLayout {

	private final int designWidth;
	private final int designHeight;
	private final int displayWidth;
	private final int displayHeight;

	public UiLayout(int designWidth, int designHeight, int displayWidth, int displayHeight) {
		this.designWidth = designWidth;
		this.designHeight = designHeight;
		this.displayWidth = displayWidth;
		this.displayHeight = displayHeight;
	}

	public int toDesignX(int value) {
		return value * designWidth / displayWidth;
	}

	public int toDesignY(int value) {
		return value * designHeight / displayHeight;
	}

	public int scaleX(int value) {
		return value * displayWidth / designWidth;
	}

	public int scaleY(int value) {
		return value * displayHeight / designHeight;
	}

	public void draw(Graphics g, BufferedImage image, int x, int y, int width, int height) {
		g.drawImage(image, scaleX(x), scaleY(y), scaleX(width), scaleY(height), null);
	}
}
