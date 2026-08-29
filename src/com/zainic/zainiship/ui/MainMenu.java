package com.zainic.zainiship.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import com.zainic.zainiship.input.Mouse;
import com.zainic.zainiship.ui.animation.Tween;
import com.zainic.zainiship.ui.components.Button;

public class MainMenu extends Menu {

	private int buttonWidth;
	private int buttonHeight;
	private int buttonGap;
	private int firstButtonY;
	private MenuButton[] menuButtons;

	public enum Action {
		NONE,
		NEW_GAME,
		LOAD_GAME,
		SETTINGS,
		INFORMATION,
		QUIT_GAME;

		Action() {
		}
	}

	private enum MenuButton {
		NEW_GAME("NewGame", true, Action.NEW_GAME),
		LOAD_GAME("LoadGame", true, Action.LOAD_GAME),
		SETTINGS("Settings", true, Action.SETTINGS),
		INFO("Info", true, Action.INFORMATION),
		QUIT_GAME("QuitGame", true, Action.QUIT_GAME);

		final String assetName;
		final boolean enabled;
		final Action action;

		MenuButton(String assetName, boolean enabled, Action action) {
			this.assetName = assetName;
			this.enabled = enabled;
			this.action = action;
		}
	}

	private final BufferedImage background = loadImage("/menu/mainmenu/mainmenu_background.png");
	private final BufferedImage title = loadImage("/menu/mainmenu/title.png");
	private final Button[] buttons = new Button[MenuButton.values().length];
	private final Tween backgroundAlpha;
	private final Tween titleAlpha, exitTitleAlpha;
	private final Tween[] buttonOffsetX = new Tween[MenuButton.values().length];
	private final Tween[] exitButtonOffsetX = new Tween[MenuButton.values().length];

	public MainMenu(int width, int height) {
		super(width, height);
		this.buttonWidth = designWidth * 1 / 4;
		this.buttonHeight = designHeight * 1 / 8;
		this.buttonGap = designHeight * 1 / 360;
		this.firstButtonY = designHeight * 2 / 7;
		this.menuButtons = MenuButton.values();
		for (int i = 0; i < menuButtons.length; i++) {
			String path = "/buttons/" + menuButtons[i].assetName;
			this.buttons[i] = new Button(getButtonX(), getButtonY(i), buttonWidth, buttonHeight,
					loadImage(path + "_init.png"), loadImage(path + "_hovered.png"),
					loadImage(path + "_clicked.png"), menuButtons[i].enabled);
		}
		backgroundAlpha = new Tween(0, 1, 60, 0, "ease-out");
		titleAlpha = new Tween(0, 1, 30, 30, "ease-in");
		exitTitleAlpha = new Tween(1, 0, 30, 0, "ease-out");
		for (int i = 0; i < buttonOffsetX.length; i++) {
			buttonOffsetX[i] = new Tween(-buttonWidth-getButtonX(), 0, 30, 20 + i * 5, "ease-out");
			exitButtonOffsetX[i] = new Tween(0, -buttonWidth-getButtonX(), 30, ( 5 - i ) * 5, "ease-in");
		}
	}

	public Action getClickedAction(int displayWidth, int displayHeight) {
		if (!buttonsReady()) return Action.NONE;

		UiLayout layout = layout(displayWidth, displayHeight);
		int mouseX = layout.toDesignX(Mouse.getX());
		int mouseY = layout.toDesignY(Mouse.getY());
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].isClicked(mouseX, mouseY)) {
				return menuButtons[i].action;
			}
		}
		return Action.NONE;
	}

	private boolean buttonsReady() {
		return Arrays.stream(buttonOffsetX).allMatch(Tween::isFinished) 
			&& !exiting;
	}

	@Override
	public void startExit(MenuManager.MenuState toState) {
		if (exiting) return;
		super.startExit(toState);
		exitTitleAlpha.reset();
		for (Tween tween : exitButtonOffsetX) tween.reset();
	}

	@Override
	public boolean isExitFinished() {
		return exiting
			&& exitTitleAlpha.isFinished()
			&& Arrays.stream(exitButtonOffsetX).allMatch(Tween::isFinished);
	}

	@Override
	public void onEnter(MenuManager.MenuState fromState) {
		super.onEnter(fromState);
		if (this.fromState.equals(MenuManager.MenuState.LOADING)) {
			backgroundAlpha.reset();
		}
		titleAlpha.reset();
		for (Tween tween : buttonOffsetX) tween.reset();
		exitTitleAlpha.reset();
		for (Tween tween : exitButtonOffsetX) tween.reset();
	}

	@Override
	public void update() {
		super.update();
		if (exiting) {
			exitTitleAlpha.update();
			for (Tween tween : exitButtonOffsetX) tween.update();
			return;
		}
		backgroundAlpha.update();
		titleAlpha.update();
		for (Tween tween : buttonOffsetX) tween.update();
	}

	@Override
	public void render(Graphics g, int displayWidth, int displayHeight) {
		renderBackgroundLayer(g, displayWidth, displayHeight);
		renderOverlay(g, displayWidth, displayHeight, false);
		renderComponents(g, displayWidth, displayHeight);
		renderOverlay(g, displayWidth, displayHeight, true);
	}

	private void renderBackgroundLayer(Graphics g, int displayWidth, int displayHeight) {
		g.drawImage(background, 0, 0, displayWidth, displayHeight, null);
	}

	private void renderOverlay(Graphics g, int displayWidth, int displayHeight, boolean foreground) {
		int titleWidth = designWidth * 3 / 5;
		int titleHeight = titleWidth * title.getHeight() / title.getWidth();
		Graphics2D titleGraphics = (Graphics2D) g.create();
		float alpha = exiting ? (float) exitTitleAlpha.value() : (float) titleAlpha.value();
		titleGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		drawScaled(titleGraphics, title, (designWidth - titleWidth) / 2, 20, titleWidth, titleHeight, displayWidth, displayHeight);
		titleGraphics.dispose();

		if (foreground) {
			float opacity = (float) Math.min(1.0f, Math.max(0.0f, 1.0f - backgroundAlpha.value()));
			Graphics2D overlay = (Graphics2D) g.create();
			overlay.setColor(new Color(0, 0, 0, opacity));
			overlay.fillRect(0, 0, displayWidth, displayHeight);
			overlay.dispose();
		}
	}

	private void renderComponents(Graphics g, int displayWidth, int displayHeight) {
		UiLayout layout = layout(displayWidth, displayHeight);
		int mouseX = layout.toDesignX(Mouse.getX());
		int mouseY = layout.toDesignY(Mouse.getY());
		for (int i = 0; i < buttons.length; i++) {
			float offsetX = exiting ? (float) exitButtonOffsetX[i].value() : (float) buttonOffsetX[i].value();
			buttons[i].renderAt(g, layout, mouseX, mouseY, Mouse.getB() == Mouse.LMB,
					(int) Math.round(offsetX), 0);
		}
	}

	private int getButtonX() {
		return (designWidth - buttonWidth) / 2 - (2 * designWidth / 7);
	}

	private int getButtonY(int buttonIndex) {
		return firstButtonY + buttonIndex * (buttonHeight + buttonGap);
	}
}
