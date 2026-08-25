package com.zainic.zainiship.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	protected double alphaFade;
	protected int startFadeIn;
	protected int startFadeOut;

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

	protected void performAnimationIn () {

	}

	protected void performAnimation () {

	}

	protected void performAnimationOut () {

	}

	protected void drawBlackFade(Graphics g, int displayWidth, int displayHeight, double alpha) {
		Graphics2D fadeGraphics = (Graphics2D) g.create();
		fadeGraphics.setColor(new Color(0, 0, 0, (int) (alpha * 255)));
		fadeGraphics.fillRect(0, 0, displayWidth, displayHeight);
		fadeGraphics.dispose();
	}

	public double getAlphaFade(){
		return this.alphaFade;
	}

}
