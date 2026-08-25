package com.zainic.zainiship.ui;

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
	private final Button[] buttons = new Button[MenuButton.values().length];

	public MainMenu(int width, int height) {
		super(width, height);
		this.buttonWidth = width * 1 / 4;
		this.buttonHeight = height * 1 / 8;
		this.buttonGap = height * 1 / 360;
		this.firstButtonY = height * 2 / 7;
		MenuButton[] menuButtons = MenuButton.values();
		for (int i = 0; i < menuButtons.length; i++) {
			String path = "/buttons/" + menuButtons[i].assetName;
			this.buttons[i] = new Button(getButtonX(), getButtonY(i), buttonWidth, buttonHeight,
					loadImage(path + "_init.png"), loadImage(path + "_hovered.png"),
					loadImage(path + "_clicked.png"), menuButtons[i].enabled);
		}
	}

	public boolean isNewGameClicked(int displayWidth, int displayHeight) {
		UiLayout layout = layout(displayWidth, displayHeight);
		return buttons[MenuButton.NEW_GAME.ordinal()].isClicked(layout.toDesignX(Mouse.getX()), layout.toDesignY(Mouse.getY()));
	}

	public void update() {
		this.time++;
		if (this.time <= 60) {
			if (this.alphaFade == 1) this.startFadeIn = this.time;
			this.alphaFade = Math.max(0.0f, (60.0f - this.time) / 60.0f);
		}
	}

	public void render(Graphics g, int displayWidth, int displayHeight) {
		g.drawImage(background, 0, 0, displayWidth, displayHeight, null);
		int titleWidth = designWidth * 3 / 5;
		int titleHeight = titleWidth * title.getHeight() / title.getWidth();
		drawScaled(g, title, (designWidth - titleWidth) / 2, 20, titleWidth, titleHeight, displayWidth, displayHeight);

		UiLayout layout = layout(displayWidth, displayHeight);
		int mouseX = layout.toDesignX(Mouse.getX());
		int mouseY = layout.toDesignY(Mouse.getY());
		for (Button button : buttons) {
			button.render(g, layout, mouseX, mouseY, Mouse.getB() == Mouse.LMB);
		}

		//render black screen for fade in or out
		if (this.alphaFade != 0){
			drawBlackFade(g, displayWidth, displayHeight, this.alphaFade);
		}
	}

	private int getButtonX() {
		return (designWidth - buttonWidth) / 2 - (2 * designWidth / 7);
	}

	private int getButtonY(int buttonIndex) {
		return firstButtonY + buttonIndex * (buttonHeight + buttonGap);
	}
}
