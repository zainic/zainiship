package com.zainic.zainiship.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.zainic.zainiship.input.Mouse;

public class MainMenu extends Menu {

	private int buttonWidth;
	private int buttonHeight;
	private int buttonGap;
	private int firstButtonY;

	private enum MenuButton {
		NEW_GAME("NewGame", true),
		LOAD_GAME("LoadGame", true),
		SETTINGS("Settings", true),
		INFO("Info", true),
		QUIT_GAME("QuitGame", true);

		final String assetName;
		final boolean enabled;

		MenuButton(String assetName, boolean enabled) {
			this.assetName = assetName;
			this.enabled = enabled;
		}
	}

	private final BufferedImage background = loadImage("/menu/mainmenu/mainmenu_background.png");
	private final BufferedImage title = loadImage("/menu/mainmenu/title.png");
	private final BufferedImage[] initialButtons = new BufferedImage[MenuButton.values().length];
	private final BufferedImage[] hoveredButtons = new BufferedImage[MenuButton.values().length];
	private final BufferedImage[] clickedButtons = new BufferedImage[MenuButton.values().length];

	public MainMenu(int width, int height) {
		super(width, height);
		this.buttonWidth = width * 1 / 4;
		this.buttonHeight = height * 1 / 8;
		this.buttonGap = height * 1 / 360;
		this.firstButtonY = height * 2 / 7;
		MenuButton[] buttons = MenuButton.values();
		for (int i = 0; i < buttons.length; i++) {
			String path = "/buttons/" + buttons[i].assetName;
			initialButtons[i] = loadImage(path + "_init.png");
			hoveredButtons[i] = loadImage(path + "_hovered.png");
			clickedButtons[i] = loadImage(path + "_clicked.png");
		}
	}

	public boolean isNewGameClicked(int displayWidth, int displayHeight) {
		if (Mouse.getB() != Mouse.LMB) return false;
		return isInsideButton(MenuButton.NEW_GAME.ordinal(), toDesignX(Mouse.getX(), displayWidth), toDesignY(Mouse.getY(), displayHeight));
	}

	public void update() {
		this.time++;
	}

	public void render(Graphics g, int displayWidth, int displayHeight) {
		g.drawImage(background, 0, 0, displayWidth, displayHeight, null);
		int titleWidth = designWidth * 3 / 5;
		int titleHeight = titleWidth * title.getHeight() / title.getWidth();
		drawScaled(g, title, (designWidth - titleWidth) / 2, 20, titleWidth, titleHeight, displayWidth, displayHeight);

		int mouseX = toDesignX(Mouse.getX(), displayWidth);
		int mouseY = toDesignY(Mouse.getY(), displayHeight);
		MenuButton[] buttons = MenuButton.values();
		for (int i = 0; i < buttons.length; i++) {
			boolean hovered = isInsideButton(i, mouseX, mouseY);
			BufferedImage buttonImage = initialButtons[i];
			if (buttons[i].enabled && hovered) {
				buttonImage = Mouse.getB() == Mouse.LMB ? clickedButtons[i] : hoveredButtons[i];
			}
			drawScaled(g, buttonImage, getButtonX(), getButtonY(i), buttonWidth, buttonHeight, displayWidth, displayHeight);
			if (!buttons[i].enabled) {
				g.setColor(new Color(0, 0, 0, 115));
				g.fillRect(scaleX(getButtonX(), displayWidth), scaleY(getButtonY(i), displayHeight), scaleX(buttonWidth, displayWidth), scaleY(buttonHeight, displayHeight));
			}
		}
	}

	private boolean isInsideButton(int buttonIndex, int x, int y) {
		return x >= getButtonX() && x < getButtonX() + buttonWidth && y >= getButtonY(buttonIndex) && y < getButtonY(buttonIndex) + buttonHeight;
	}

	private int getButtonX() {
		return (designWidth - buttonWidth) / 2 - (2 * designWidth / 7);
	}

	private int getButtonY(int buttonIndex) {
		return firstButtonY + buttonIndex * (buttonHeight + buttonGap);
	}
}
