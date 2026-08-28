package com.zainic.zainiship.ui;

// import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/** A design-space image button with shared visual state and hit testing. */
public final class Button {

	// private static final Color DISABLED_OVERLAY = new Color(0, 0, 0, 115);

	private final int x;
	private final int y;
	private final int width;
	private final int height;
	private final BufferedImage normalImage;
	private final BufferedImage hoveredImage;
	private final BufferedImage pressedImage;
	private final boolean enabled;

	public Button(int x, int y, int width, int height, BufferedImage normalImage,
			BufferedImage hoveredImage, BufferedImage pressedImage, boolean enabled) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.normalImage = normalImage;
		this.hoveredImage = hoveredImage;
		this.pressedImage = pressedImage;
		this.enabled = enabled;
	}

	public boolean contains(int designX, int designY) {
		return designX >= x && designX < x + width
				&& designY >= y && designY < y + height;
	}

	public boolean isClicked(int designX, int designY) {
		return enabled && contains(designX, designY);
	}

	public boolean isClickedAt(int designX, int designY, int offsetX, int offsetY) {
		return enabled && designX >= x + offsetX && designX < x + offsetX + width
				&& designY >= y + offsetY && designY < y + offsetY + height;
	}

	public void render(Graphics g, UiLayout layout, int mouseX, int mouseY, boolean mousePressed) {
		renderAt(g, layout, mouseX, mouseY, mousePressed, 0, 0);
	}

	/** Renders at an animated offset; hit testing uses the same offset. */
	public void renderAt(Graphics g, UiLayout layout, int mouseX, int mouseY, boolean mousePressed,
			int offsetX, int offsetY) {
		BufferedImage image = normalImage;
		if (isClickedAt(mouseX, mouseY, offsetX, offsetY)) {
			image = mousePressed ? pressedImage : hoveredImage;
		}
		layout.draw(g, image, x + offsetX, y + offsetY, width, height);

		if (!enabled) {
			g.fillRect(layout.scaleX(x + offsetX), layout.scaleY(y + offsetY), layout.scaleX(width), layout.scaleY(height));
		}
	}

	public int getButtonWidth() {
		return normalImage.getWidth();
	}

	public int getButtonHeight() {
		return normalImage.getHeight();
	}
}
