package com.zainic.zainiship.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadingMenu {

	private int designWidth;
	private int designHeight;
	private int time;
	private int backgroundWidth;
	private int backgroundHeight;
	private int xOffset;
	private int yOffset;
	private int[] pixels, background;
	private float titleAlpha, barAlpha, barProgress;
	private int yTitleOffset;
	private final BufferedImage image;

	private final BufferedImage backgroundImage = loadImage("/menu/loadingmenu/loadingmenu_background.png");
	private final BufferedImage titleImage = loadImage("/menu/loadingmenu/title.png");
	private final BufferedImage unfilledloadingbarImage = loadImage("/menu/loadingmenu/unfilledloadingbar.png");
	private final BufferedImage filledloadingbarImage = loadImage("/menu/loadingmenu/filledloadingbar.png");

	public LoadingMenu(int width, int height) {
		this.designWidth = width;
		this.designHeight = height;
		this.time = 0;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		this.backgroundWidth = backgroundImage.getWidth();
		this.backgroundHeight = backgroundImage.getHeight();
		this.background = new int[backgroundWidth * backgroundHeight];
		backgroundImage.getRGB(0, 0, backgroundWidth, backgroundHeight, background, 0, backgroundWidth);
	}

	public void update() {
		this.time++;
		this.xOffset = (int) ((Math.sin(this.time * 0.005) * (this.backgroundWidth - this.designWidth) / 2) + (this.backgroundWidth - this.designWidth) / 2);
		this.yOffset = 0;
		this.titleAlpha = Math.min(1.0f, this.time / 60.0f);
		if (this.time > 60) {
			this.yTitleOffset = (int) (- Math.min( 60, this.time - 60));
		} else {
			this.yTitleOffset = 0;
		}
		if (this.time > 120) {
			this.barAlpha = Math.min(1.0f, (this.time - 120) / 30.0f);
		} else {
			this.barAlpha = 0.0f;
		}
		if (this.time > 150) {
			this.barProgress = Math.min(1.0f, (this.time - 150) / 1000.0f);
		}
	}

	public void render(Graphics g, int displayWidth, int displayHeight) {
		// Render the background
		renderBackground(this.xOffset, this.yOffset);
		g.drawImage(this.image, 0, 0, displayWidth, displayHeight, null);

		// Render the title with alpha transparency
		int titleWidth = designWidth * 4 / 5;
		int titleHeight = titleWidth * titleImage.getHeight() / titleImage.getWidth();
		Graphics2D titleGraphics = (Graphics2D) g.create();
		titleGraphics.setComposite(
			AlphaComposite.getInstance(AlphaComposite.SRC_OVER, titleAlpha)
		);
		drawScaled(titleGraphics, titleImage, (designWidth - titleWidth) / 2, designHeight / 3 + yTitleOffset, titleWidth, titleHeight, displayWidth, displayHeight);
		titleGraphics.dispose();

		// Render the unfilled and filled loading bar with alpha transparency
		int barWidth = designWidth * 4 / 5;
		int barHeight = barWidth * unfilledloadingbarImage.getHeight() / unfilledloadingbarImage.getWidth();
		Graphics2D barGraphics = (Graphics2D) g.create();
		barGraphics.setComposite(
			AlphaComposite.getInstance(AlphaComposite.SRC_OVER, barAlpha)
		);
		drawScaled(barGraphics, unfilledloadingbarImage, (designWidth - barWidth) / 2, designHeight * 4 / 7 , barWidth, barHeight, displayWidth, displayHeight);
		drawScaledFromLeft(barGraphics, filledloadingbarImage, this.barProgress, (designWidth - barWidth) / 2, designHeight * 4 / 7 , barWidth, barHeight, displayWidth, displayHeight);
		barGraphics.dispose();
	}

	private void drawScaled(Graphics g, BufferedImage image, int x, int y, int width, int height, int displayWidth, int displayHeight) {
		g.drawImage(image, scaleX(x, displayWidth), scaleY(y, displayHeight), scaleX(width, displayWidth), scaleY(height, displayHeight), null);
	}

	// private int toDesignX(int value, int displayWidth) {
	// 	return value * designWidth / displayWidth;
	// }

	// private int toDesignY(int value, int displayHeight) {
	// 	return value * designHeight / displayHeight;
	// }

	private int scaleX(int value, int displayWidth) {
		return value * displayWidth / designWidth;
	}

	private int scaleY(int value, int displayHeight) {
		return value * displayHeight / designHeight;
	}

	private BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(MainMenu.class.getResource(path));
		} catch (IOException | IllegalArgumentException e) {
			throw new IllegalStateException("Could not load menu asset: " + path, e);
		}
	}

	private void drawScaledFromLeft(Graphics g, BufferedImage image, float progress, int x, int y, int width, int height, int displayWidth, int displayHeight) {

		progress = Math.max(0.0f, Math.min(1.0f, progress));

		int sourceRight = (int) (image.getWidth() * progress);
		int destinationRight = x + (int) (width * progress);

		if (sourceRight <= 0) return;

		g.drawImage(
			image,

			scaleX(x, displayWidth),
			scaleY(y, displayHeight),
			scaleX(destinationRight, displayWidth),
			scaleY(y + height, displayHeight),

			0, 0,
			sourceRight, image.getHeight(),

			null
		);
	}

	public int getTime() {
		return this.time;
	}

	public void renderBackground(int xp, int yp) {
		for (int y = 0; y < this.backgroundHeight; y++) {
			int ya = y + yp;
			for (int x = 0; x < this.backgroundWidth; x++) {
				int xa = x + xp;
				if (x < 0 || x >= this.designWidth || y < 0 || y >= this.designHeight) break;
				if (xa < 0 || xa >= this.backgroundWidth) {
					xa = Math.floorMod(xa, this.backgroundWidth);
				}
				if (ya < 0 || ya >= this.backgroundHeight) {
					ya = Math.floorMod(ya, this.backgroundHeight);
				}
				this.pixels[x + y * this.designWidth] = this.background[xa + ya * this.backgroundWidth];
			}
		}
	}
}
