package com.zainic.zainiship.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zainic.zainiship.ui.font.CustomFont;

public abstract class Menu {

	protected static final Color TEXT_COLOR_WHITE = Color.WHITE;
	protected static final Font ORBITRON_BOLD_20 = CustomFont.orbitron20.get("Bold");
	protected static final Font ORBITRON_REGULAR_15 = CustomFont.orbitron15.get("Regular");

	protected int designWidth;
	protected int designHeight;
	protected int time;

	public Menu(int width, int height) {
		this.designWidth = width;
		this.designHeight = height;
		this.time = 0;
	}

	public void update() {
		this.time++;
		// No dynamic elements to update in the main menu for now
	}

	public int getTime() {
		return this.time;
	}

	public void render(Graphics g, int displayWidth, int displayHeight) {
		
	}

	protected void drawScaled(Graphics g, BufferedImage image, int x, int y, int width, int height, int displayWidth, int displayHeight) {
		g.drawImage(image, scaleX(x, displayWidth), scaleY(y, displayHeight), scaleX(width, displayWidth), scaleY(height, displayHeight), null);
	}

	protected int toDesignX(int value, int displayWidth) {
		return value * designWidth / displayWidth;
	}

	protected int toDesignY(int value, int displayHeight) {
		return value * designHeight / displayHeight;
	}

	protected int scaleX(int value, int displayWidth) {
		return value * displayWidth / designWidth;
	}

	protected int scaleY(int value, int displayHeight) {
		return value * displayHeight / designHeight;
	}

	protected BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(MainMenu.class.getResource(path));
		} catch (IOException | IllegalArgumentException e) {
			throw new IllegalStateException("Could not load menu asset: " + path, e);
		}
	}
}
