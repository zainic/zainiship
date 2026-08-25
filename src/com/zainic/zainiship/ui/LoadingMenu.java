package com.zainic.zainiship.ui;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;

public class LoadingMenu extends Menu {

	private int time;
	private int backgroundWidth;
	private int backgroundHeight;
	private int xOffset;
	private int yOffset;
	private int[] pixels, background;
	private float titleAlpha, barAlpha;
	private double barProgress;
	private int yTitleOffset;
	private String[] loadingTextList;
	private String[] subTextList;
	private String currentLoadingText;
	private String currentSubText;
	private String measuredLoadingText;
	private String measuredSubText;
	private int loadingTextWidth;
	private int subTextWidth;
	private boolean backgroundDirty = true;
	private final BufferedImage image;

	private final BufferedImage backgroundImage = loadImage("/menu/loadingmenu/loadingmenu_background.png");
	private final BufferedImage titleImage = loadImage("/menu/loadingmenu/title.png");
	private final BufferedImage unfilledloadingbarImage = loadImage("/menu/loadingmenu/unfilledloadingbar.png");
	private final BufferedImage filledloadingbarImage = loadImage("/menu/loadingmenu/filledloadingbar.png");

	public LoadingMenu(int width, int height) {
		super(width, height);
		this.time = 0;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		this.backgroundWidth = backgroundImage.getWidth();
		this.backgroundHeight = backgroundImage.getHeight();
		this.background = new int[backgroundWidth * backgroundHeight];
		backgroundImage.getRGB(0, 0, backgroundWidth, backgroundHeight, background, 0, backgroundWidth);

		this.loadingTextList = new String[] {
			"Initializing systems",
			"Loading game assets",
			"Preparing the world",
			"Loading player data",
			"Generating environment",
			"Spawning enemies",
			"Almost ready",
			"Entering game"
		};
		this.subTextList = new String[] {
			"Fly fast. Miss nothing.",
			"The ground hurts.",
			"Bullets are faster than apologies.",
			"Altitude is your friend. Usually.",
			"More missiles = better strategy.",
			"Dodging is optional. Technically.",
			"Enemies hate this one simple trick: shooting them.",
			"Landing is just controlled crashing.",
			"Don't forget which way is up.",
			"If in doubt, shoot.",
			"No refunds for crashed aircraft.",
			"Pilot skill issue detected.",
			"Warning: Flying may occur.",
			"Achievement unlocked: Still Alive.",
			"Caution: Objects ahead may be closer than they appear.",
			"The eject button is not a cup holder.",
			"Flying upside down is still flying.",
			"Mission difficulty: We lied.",
			"Good news: You have missiles.",
			"Bad news: So do they.",
			"The enemy can't hit you if you crash first.",
			"Pro tip: Don't test the armor with your face.",
			"Radar detects enemies, not common sense.",
			"Your plane is faster than your excuses.",
			"Landing successfully is optional.",
			"Flying is easy. Stopping is complicated.",
			"The nearest mountain is your responsibility.",
			"Ammo count: Never enough.",
			"Explosions improve everything.",
			"This mission is totally under control.",
			"Absolutely nothing can go wrong.",
			"Famous last words: \"Watch this.\"",
			"Pilot confidence: 100%. Pilot skill: Loading..."
		};
	}

	public void update() {
		this.time++;
		this.xOffset = (int) ((Math.sin(this.time * 0.005) * (this.backgroundWidth - this.designWidth) / 2) + (this.backgroundWidth - this.designWidth) / 2);
		this.yOffset = 0;
		this.backgroundDirty = true;
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
			double boost;
			if (this.time <= 300) {
				boost = 2 ;
			}
			else {
				boost = 0;
			}
			this.barProgress = Math.min(1.0f, this.barProgress + ( boost + 10 * Math.exp(- Math.random() * 10)) / 1000.0f);
			int textIndex = (int) Math.min(loadingTextList.length - 1, (this.barProgress * (loadingTextList.length)));
			this.currentLoadingText = loadingTextList[textIndex];
			if (this.time % 120 == 0 || this.time == 151) this.currentSubText = subTextList[(int) (Math.random() * subTextList.length)];
		}
	}

	public void render(Graphics g, int displayWidth, int displayHeight) {
		// The game can render more than once per update; only rebuild this pixel buffer
		// after the scrolling offset has changed.
		if (backgroundDirty) {
			renderBackground(this.xOffset, this.yOffset);
			backgroundDirty = false;
		}
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

		// Render the loading text
		if (currentLoadingText != null && currentSubText != null) {
			g.setColor(TEXT_COLOR_WHITE);
			g.setFont(ORBITRON_BOLD_20);
			if (!currentLoadingText.equals(measuredLoadingText)) {
				FontMetrics metrics = g.getFontMetrics(ORBITRON_BOLD_20);
				loadingTextWidth = metrics.stringWidth(currentLoadingText);
				measuredLoadingText = currentLoadingText;
			}
			g.drawString(currentLoadingText, (designWidth - loadingTextWidth) / 2, designHeight * 4 / 7 + barHeight + 10);
			g.setFont(ORBITRON_REGULAR_15);
			if (!currentSubText.equals(measuredSubText)) {
				FontMetrics metrics = g.getFontMetrics(ORBITRON_REGULAR_15);
				subTextWidth = metrics.stringWidth(currentSubText);
				measuredSubText = currentSubText;
			}
			g.drawString(currentSubText, (designWidth - subTextWidth) / 2, designHeight * 4 / 7 + barHeight + 35);
		}
	}

	private void drawScaledFromLeft(Graphics g, BufferedImage image, double progress, int x, int y, int width, int height, int displayWidth, int displayHeight) {

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

	public int getProgressBar() {
		return (int) (this.barProgress * 100);
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
