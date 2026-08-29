package com.zainic.zainiship.ui.components;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.zainic.zainiship.ui.UiLayout;

/** A design-space image button with shared visual state and hit testing. */
public final class Button {

	private final int x;
	private final int y;
	private final int width;
	private final int height;
	private final BufferedImage normalImage;
	private final BufferedImage hoveredImage;
	private final BufferedImage pressedImage;
	private boolean enabled;

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

	public void renderAt(Graphics g, UiLayout layout, int mouseX, int mouseY, boolean mousePressed,
			int offsetX, int offsetY) {
		BufferedImage image = normalImage;
		if (isClickedAt(mouseX, mouseY, offsetX, offsetY)) {
			image = mousePressed ? pressedImage : hoveredImage;
		}
		if (enabled) {
			layout.draw(g, image, x + offsetX, y + offsetY, width, height);
		}
		else {
			Graphics2D buttonGraphics = (Graphics2D) g.create();
			float currentAlpha = ((AlphaComposite) (buttonGraphics.getComposite())).getAlpha();
			buttonGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentAlpha * 0.5f));
			layout.draw(buttonGraphics, image, x + offsetX, y + offsetY, width, height);
		}
	}

	public int getButtonWidth() {
		return normalImage.getWidth();
	}

	public int getButtonHeight() {
		return normalImage.getHeight();
	}

	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
}
