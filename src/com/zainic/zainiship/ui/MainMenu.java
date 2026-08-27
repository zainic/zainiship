package com.zainic.zainiship.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.image.BufferedImage;

import com.zainic.zainiship.input.Mouse;
import com.zainic.zainiship.ui.animation.Tween;

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
	private final Tween backgroundAlpha;
	private final Tween titleAlpha;
	private final Tween[] buttonOffsetX = new Tween[MenuButton.values().length];

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
		backgroundAlpha = new Tween(0, 1, 60, 0, "ease-out");
		titleAlpha = new Tween(0, 1, 30, 30, "ease-in");
		for (int i = 0; i < buttonOffsetX.length; i++) {
			buttonOffsetX[i] = new Tween(-buttonWidth-getButtonX(), 0, 30, 20 + i * 5, "ease-out");
		}
	}

	public boolean isNewGameClicked(int displayWidth, int displayHeight) {
		UiLayout layout = layout(displayWidth, displayHeight);
		int index = MenuButton.NEW_GAME.ordinal();
		return buttons[index].isClickedAt(layout.toDesignX(Mouse.getX()), layout.toDesignY(Mouse.getY()),
				(int) Math.round(buttonOffsetX[index].value()), 0);
	}

	@Override
	public void onEnter() {
		super.onEnter();
		backgroundAlpha.reset();
		titleAlpha.reset();
		for (Tween tween : buttonOffsetX) tween.reset();
	}

	@Override
	public void update() {
		super.update();
		backgroundAlpha.update();
		titleAlpha.update();
		for (Tween tween : buttonOffsetX) tween.update();
	}

	@Override
	public void render(Graphics g, int displayWidth, int displayHeight) {
		// Render Background
		g.drawImage(background, 0, 0, displayWidth, displayHeight, null);

		// Render title Main Menu
		int titleWidth = designWidth * 3 / 5;
		int titleHeight = titleWidth * title.getHeight() / title.getWidth();
		Graphics2D titleGraphics = (Graphics2D) g.create();
		titleGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) titleAlpha.value()));
		drawScaled(titleGraphics, title, (designWidth - titleWidth) / 2, 20, titleWidth, titleHeight, displayWidth, displayHeight);
		titleGraphics.dispose();

		// Render buttons in Main Menu
		UiLayout layout = layout(displayWidth, displayHeight);
		int mouseX = layout.toDesignX(Mouse.getX());
		int mouseY = layout.toDesignY(Mouse.getY());
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].renderAt(g, layout, mouseX, mouseY, Mouse.getB() == Mouse.LMB,
					(int) Math.round(buttonOffsetX[i].value()), 0);
		}

		// Render Black Screen
		float opacity = (float) (Math.min(1.0f, Math.max(0.0f, 1.0f - backgroundAlpha.value())));
		Graphics2D overlay = (Graphics2D) g.create();
		overlay.setColor(new Color(0, 0, 0, opacity));
		overlay.fillRect(0, 0, displayWidth, displayHeight);
		overlay.dispose();
	}

	private int getButtonX() {
		return (designWidth - buttonWidth) / 2 - (2 * designWidth / 7);
	}

	private int getButtonY(int buttonIndex) {
		return firstButtonY + buttonIndex * (buttonHeight + buttonGap);
	}
}
