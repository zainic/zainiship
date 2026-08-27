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
	protected boolean inAnimation;
	protected int time;

	public Menu(int width, int height) {
		this.designWidth = width;
		this.designHeight = height;
		this.time = 0;
		this.inAnimation = false;
	}

	public void update() {
		this.time++;
	}

	public void onEnter() {
		this.time = 0;
	}

	public int getTime() {
		return this.time;
	}

	public void render(Graphics g, int displayWidth, int displayHeight) {
		
	}

	protected UiLayout layout(int displayWidth, int displayHeight) {
		return new UiLayout(designWidth, designHeight, displayWidth, displayHeight);
	}

	protected void drawScaled(Graphics g, BufferedImage image, int x, int y, int width, int height, int displayWidth, int displayHeight) {
		layout(displayWidth, displayHeight).draw(g, image, x, y, width, height);
	}

	protected int toDesignX(int value, int displayWidth) {
		return layout(displayWidth, designHeight).toDesignX(value);
	}

	protected int toDesignY(int value, int displayHeight) {
		return layout(designWidth, displayHeight).toDesignY(value);
	}

	protected int scaleX(int value, int displayWidth) {
		return layout(displayWidth, designHeight).scaleX(value);
	}

	protected int scaleY(int value, int displayHeight) {
		return layout(designWidth, displayHeight).scaleY(value);
	}

	protected BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(MainMenu.class.getResource(path));
		} catch (IOException | IllegalArgumentException e) {
			throw new IllegalStateException("Could not load menu asset: " + path, e);
		}
	}

	public boolean getStatusAnimation() {
		return inAnimation;
	}

}
