package com.zainic.zainiship.ui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.zainic.zainiship.input.Mouse;
import com.zainic.zainiship.ui.animation.Tween;

public class EnterNameMenu extends Menu {

	private int backgroundWidth;
	private int backgroundHeight;
	private int backButtonWidth;
	private int backButtonHeight;
	private int okButtonWidth;
	private int okButtonHeight;
	private int enterNameWindowWidth;
	private int enterNameWindowHeight;
	private int leftEnterNameWindowWidth;
	private int leftEnterNameWindowHeight;
	private int midEnterNameWindowWidth;
	private int midEnterNameWindowHeight;
	private int rightEnterNameWindowWidth;
	private int rightEnterNameWindowHeight;
	private int backgroundEnterNameWindowWidth;
	private int backgroundEnterNameWindowHeight;
	private int[] pixels, background;
	private MenuButton[] menuButtons;
	private boolean backgroundDirty;

	public enum Action {
		NONE,
		BACK,
		OK;

		Action() {
		}
	}

	private enum MenuButton {
		BACK("Back_only", true, Action.BACK),
		OK("OK_only", true, Action.OK);

		final String assetName;
		final boolean enabled;
		final Action action;

		MenuButton(String assetName, boolean enabled, Action action) {
			this.assetName = assetName;
			this.enabled = enabled;
			this.action = action;
		}
	}

	private final BufferedImage image;
	private final BufferedImage backgroundImage = loadImage("/menu/entername/entername_background.png");
	private final BufferedImage leftEnterNameWindowImage = loadImage("/menu/entername/enternamewindow_left.png");
	private final BufferedImage midEnterNameWindowImage = loadImage("/menu/entername/enternamewindow_mid.png");
	private final BufferedImage rightEnterNameWindowImage = loadImage("/menu/entername/enternamewindow_right.png");
	private final BufferedImage backgroundEnterNameWindowImage = loadImage("/menu/entername/enternamewindow_background.png");
	private final Button[] buttons = new Button[MenuButton.values().length];
	private final Tween leftWindowOffsetY, rightWindowOffsetY;
	private final Tween leftWindowOffsetX, midWindowWidth, rightWindowOffsetX;
	private final Tween leftWindowAlpha, backgroundWindowAlpha, rightWindowAlpha;
	private final Tween exitLeftWindowOffsetY, exitRightWindowOffsetY;
	private final Tween exitLeftWindowOffsetX, exitMidWindowWidth, exitRightWindowOffsetX;
	private final Tween exitLeftWindowAlpha, exitBackgroundWindowAlpha, exitRightWindowAlpha;
	private final Tween backButtonOffsetY, exitBackButtonOffsetY;
	private final Tween okButtonOffsetY, exitOkButtonOffsetY;
	private final Tween backButtonAlpha, exitBackButtonAlpha;
	private final Tween okButtonAlpha, exitOkButtonAlpha;

	public EnterNameMenu(int width, int height) {
		super(width, height);
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		this.backgroundWidth = backgroundImage.getWidth();
		this.backgroundHeight = backgroundImage.getHeight();
		this.background = new int[backgroundWidth * backgroundHeight];
		backgroundImage.getRGB(0, 0, backgroundWidth, backgroundHeight, background, 0, backgroundWidth);
		this.enterNameWindowWidth = designWidth * 25 / 32;
		int sourceWindowWidth = leftEnterNameWindowImage.getWidth()
				+ midEnterNameWindowImage.getWidth()
				+ rightEnterNameWindowImage.getWidth();
		this.enterNameWindowHeight = enterNameWindowWidth
				* midEnterNameWindowImage.getHeight() / sourceWindowWidth;
		this.leftEnterNameWindowWidth = enterNameWindowWidth
				* leftEnterNameWindowImage.getWidth() / sourceWindowWidth;
		this.leftEnterNameWindowHeight = enterNameWindowHeight;
		this.midEnterNameWindowWidth = enterNameWindowWidth
				* midEnterNameWindowImage.getWidth() / sourceWindowWidth;
		this.midEnterNameWindowHeight = enterNameWindowHeight;
		this.rightEnterNameWindowWidth = enterNameWindowWidth
				* rightEnterNameWindowImage.getWidth() / sourceWindowWidth;
		this.rightEnterNameWindowHeight = enterNameWindowHeight;
		this.backgroundEnterNameWindowWidth = enterNameWindowWidth
				* backgroundEnterNameWindowImage.getWidth() / sourceWindowWidth;
		this.backgroundEnterNameWindowHeight = enterNameWindowWidth
				* backgroundEnterNameWindowImage.getHeight() / sourceWindowWidth;
		this.menuButtons = MenuButton.values();
		BufferedImage sampleBackImage = loadImage("/buttons/Back_only_init.png");
		BufferedImage sampleOkImage = loadImage("/buttons/OK_only_init.png");
		this.backButtonWidth = designWidth * 9 / 64;
		this.backButtonHeight = this.backButtonWidth * sampleBackImage.getHeight() / sampleBackImage.getWidth();
		this.okButtonWidth = designWidth * 9 / 64;
		this.okButtonHeight = this.okButtonWidth * sampleOkImage.getHeight() / sampleOkImage.getWidth();
		for (int i = 0; i < menuButtons.length; i++) {
			MenuButton menuButton = menuButtons[i];
			String path = getButtonAssetPath(menuButton, i);
			int buttonWidth, buttonHeight;
			switch (menuButton) {
				case BACK:
					buttonWidth = backButtonWidth; 
					buttonHeight = backButtonHeight;
					break;
				case OK:
					buttonWidth = okButtonWidth;
					buttonHeight = okButtonHeight;
					break;
				default:
					buttonWidth = 100;
					buttonHeight = 100;
					break;
			}
			buttons[i] = new Button(getButtonX(menuButton), getButtonY(menuButton), buttonWidth, buttonHeight,
					loadImage(path + "_init.png"), loadImage(path + "_hovered.png"),
					loadImage(path + "_clicked.png"), menuButton.enabled);
		}
		// Tween Initialization
		// ==================== WINDOW POSITION Y ====================
		this.leftWindowOffsetY = new Tween(designHeight, 0, 30, 0, "ease-out");
		this.exitLeftWindowOffsetY = new Tween(0, designHeight, 30, 60, "ease-in");
		this.rightWindowOffsetY = new Tween(designHeight, 0, 30, 0, "ease-out");
		this.exitRightWindowOffsetY = new Tween(0, designHeight, 30, 60, "ease-in");
		// ==================== WINDOW POSITION X ====================
		this.leftWindowOffsetX = new Tween(0, -midEnterNameWindowWidth / 2, 30, 30, "linear");
		this.exitLeftWindowOffsetX = new Tween(-midEnterNameWindowWidth / 2, 0, 30, 30, "linear");
		this.midWindowWidth = new Tween(0, midEnterNameWindowWidth, 30, 30, "linear");
		this.exitMidWindowWidth = new Tween(midEnterNameWindowWidth, 0, 30, 30, "linear");
		this.rightWindowOffsetX = new Tween(0, midEnterNameWindowWidth / 2, 30, 30, "linear");
		this.exitRightWindowOffsetX = new Tween(midEnterNameWindowWidth / 2, 0, 30, 30, "linear");
		// ==================== WINDOW ALPHA ====================
		this.leftWindowAlpha = new Tween(0, 1, 30, 0, "linear");
		this.exitLeftWindowAlpha = new Tween(1, 0, 30, 60, "linear");
		this.backgroundWindowAlpha = new Tween(0, 1, 30, 60, "linear");
		this.exitBackgroundWindowAlpha = new Tween(1, 0, 30, 0, "linear");
		this.rightWindowAlpha = new Tween(0, 1, 30, 0, "linear");
		this.exitRightWindowAlpha = new Tween(1, 0, 30, 60, "linear");
		// ==================== BACK BUTTON ====================
		this.backButtonOffsetY = new Tween(-backButtonHeight / 2, 0, 30, 90, "ease-out");
		this.exitBackButtonOffsetY = new Tween(0, backButtonHeight / 2, 30, 0, "ease-in");
		this.backButtonAlpha = new Tween(0, 1, 30, 90, "linear");
		this.exitBackButtonAlpha = new Tween(1, 0, 30, 0, "linear");
		// ==================== OK BUTTON ====================
		this.okButtonOffsetY = new Tween(-okButtonHeight / 2, 0, 30, 90, "ease-out");
		this.exitOkButtonOffsetY = new Tween(0, okButtonHeight / 2, 30, 0, "ease-in");
		this.okButtonAlpha = new Tween(0, 1, 30, 90, "linear");
		this.exitOkButtonAlpha = new Tween(1, 0, 30, 0, "linear");
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
		return leftWindowOffsetY.isFinished()
				&& rightWindowOffsetY.isFinished()
				&& leftWindowOffsetX.isFinished()
				&& midWindowWidth.isFinished()
				&& rightWindowOffsetX.isFinished()
				&& leftWindowAlpha.isFinished()
				&& backgroundWindowAlpha.isFinished()
				&& rightWindowAlpha.isFinished()
				&& backButtonOffsetY.isFinished()
				&& okButtonOffsetY.isFinished()
				&& backButtonAlpha.isFinished()
				&& okButtonAlpha.isFinished()
				&& !exiting;
	}

	@Override
	public void startExit(MenuManager.MenuState toState) {
		if (exiting) return;
		super.startExit(toState);
		exitLeftWindowOffsetY.reset();
		exitRightWindowOffsetY.reset();
		exitLeftWindowOffsetX.reset();
		exitMidWindowWidth.reset();
		exitRightWindowOffsetX.reset();
		exitLeftWindowAlpha.reset();
		exitBackgroundWindowAlpha.reset();
		exitRightWindowAlpha.reset();
		exitBackButtonOffsetY.reset();
		exitOkButtonOffsetY.reset();
		exitBackButtonAlpha.reset();
		exitOkButtonAlpha.reset();
	}

	@Override
	public boolean isExitFinished() {
		return exiting
			&& exitLeftWindowOffsetY.isFinished()
			&& exitRightWindowOffsetY.isFinished()
			&& exitLeftWindowOffsetX.isFinished()
			&& exitMidWindowWidth.isFinished()
			&& exitRightWindowOffsetX.isFinished()
			&& exitLeftWindowAlpha.isFinished()
			&& exitBackgroundWindowAlpha.isFinished()
			&& exitRightWindowAlpha.isFinished()
			&& exitBackButtonOffsetY.isFinished()
			&& exitOkButtonOffsetY.isFinished()
			&& exitBackButtonAlpha.isFinished()
			&& exitOkButtonAlpha.isFinished();
	}

	@Override
	public void onEnter(MenuManager.MenuState fromState){
		super.onEnter(fromState);
		backgroundDirty = true;
		leftWindowOffsetY.reset();
		rightWindowOffsetY.reset();
		leftWindowOffsetX.reset();
		midWindowWidth.reset();
		rightWindowOffsetX.reset();
		leftWindowAlpha.reset();
		backgroundWindowAlpha.reset();
		rightWindowAlpha.reset();
		backButtonOffsetY.reset();
		okButtonOffsetY.reset();
		backButtonAlpha.reset();
		okButtonAlpha.reset();
		exitLeftWindowOffsetY.reset();
		exitRightWindowOffsetY.reset();
		exitLeftWindowOffsetX.reset();
		exitMidWindowWidth.reset();
		exitRightWindowOffsetX.reset();
		exitLeftWindowAlpha.reset();
		exitBackgroundWindowAlpha.reset();
		exitRightWindowAlpha.reset();
		exitBackButtonOffsetY.reset();
		exitOkButtonOffsetY.reset();
		exitBackButtonAlpha.reset();
		exitOkButtonAlpha.reset();
	}

	@Override
	public void update() {
		this.time++;
		backgroundDirty = true;
		if (exiting) {
			exitLeftWindowOffsetY.update();
			exitRightWindowOffsetY.update();
			exitLeftWindowOffsetX.update();
			exitMidWindowWidth.update();
			exitRightWindowOffsetX.update();
			exitLeftWindowAlpha.update();
			exitBackgroundWindowAlpha.update();
			exitRightWindowAlpha.update();
			exitBackButtonOffsetY.update();
			exitOkButtonOffsetY.update();
			exitBackButtonAlpha.update();
			exitOkButtonAlpha.update();
			return;
		}
		leftWindowOffsetY.update();
		rightWindowOffsetY.update();
		leftWindowOffsetX.update();
		midWindowWidth.update();
		rightWindowOffsetX.update();
		leftWindowAlpha.update();
		backgroundWindowAlpha.update();
		rightWindowAlpha.update();
		backButtonOffsetY.update();
		okButtonOffsetY.update();
		backButtonAlpha.update();
		okButtonAlpha.update();
	}

	@Override
	public void render(Graphics g, int displayWidth, int displayHeight) {
		// Render Background
		if (backgroundDirty) {
			renderBackground(0, 0);
			backgroundDirty = false;
		}
		g.drawImage(this.image, 0, 0, displayWidth, displayHeight, null);

		// ==== Render Window ====
		// initiate variable
		int centerX = designWidth / 2;
		int centerY = designHeight / 2;
		int sideWidth = Math.max(leftEnterNameWindowWidth, rightEnterNameWindowWidth);
		int windowHeight = enterNameWindowHeight;
		int leftX;
		int rightX;
		int midWidth;
		int leftY;
		int rightY;
		float leftAlpha;
		float rightAlpha;
		float backgroundAlpha;

		// ============================================================
		// GET CURRENT ANIMATION VALUES
		// ============================================================
		if (exiting) {
			midWidth = (int) exitMidWindowWidth.value();
			leftX = centerX - sideWidth + (int) exitLeftWindowOffsetX.value();
			rightX = centerX + (int) exitRightWindowOffsetX.value();
			leftY = centerY - windowHeight / 2 + (int) exitLeftWindowOffsetY.value() - designHeight / 12;
			rightY = centerY - windowHeight / 2 + (int) exitRightWindowOffsetY.value() - designHeight / 12;

			leftAlpha = (float) exitLeftWindowAlpha.value();
			rightAlpha = (float) exitRightWindowAlpha.value();
			backgroundAlpha = (float) exitBackgroundWindowAlpha.value();
		}
		else {
			midWidth = (int) midWindowWidth.value();
			leftX = centerX - sideWidth + (int) leftWindowOffsetX.value();
			rightX = centerX + (int) rightWindowOffsetX.value();
			leftY = centerY - windowHeight / 2 + (int) leftWindowOffsetY.value() - designHeight / 12;
			rightY = centerY - windowHeight / 2 + (int) rightWindowOffsetY.value() - designHeight / 12;

			leftAlpha = (float) leftWindowAlpha.value();
			rightAlpha = (float) rightWindowAlpha.value();
			backgroundAlpha = (float) backgroundWindowAlpha.value();
		}


		// ============================================================
		// MIDDLE WINDOW POSITION
		// ============================================================

		int midX = centerX - midWidth / 2;
		int midY = centerY - windowHeight / 2 - designHeight / 12;

		// ============================================================
		// BACKGROUND WINDOW
		// ============================================================

		if (backgroundAlpha > 0) {
			Graphics2D bgGraphics = (Graphics2D) g.create();
			bgGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, backgroundAlpha));
			drawScaled(bgGraphics, backgroundEnterNameWindowImage,
					centerX - backgroundEnterNameWindowWidth / 2,
					centerY - backgroundEnterNameWindowHeight / 2 - designHeight / 12,
					backgroundEnterNameWindowWidth, backgroundEnterNameWindowHeight,
					displayWidth, displayHeight);
			bgGraphics.dispose();
		}

		// ============================================================
		// MIDDLE WINDOW
		// ============================================================

		if (midWidth > 0) {
			drawScaled(g, midEnterNameWindowImage, midX, midY, midWidth, windowHeight, displayWidth, displayHeight);
		}

		// ============================================================
		// LEFT WINDOW
		// ============================================================

		Graphics2D leftGraphics = (Graphics2D) g.create();
		leftGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, leftAlpha));
		drawScaled(leftGraphics, leftEnterNameWindowImage, leftX, leftY, sideWidth, windowHeight, displayWidth, displayHeight);
		leftGraphics.dispose();

		// ============================================================
		// RIGHT WINDOW
		// ============================================================

		Graphics2D rightGraphics = (Graphics2D) g.create();
		rightGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, rightAlpha));
		drawScaled(rightGraphics, rightEnterNameWindowImage, rightX, rightY, sideWidth, windowHeight, displayWidth, displayHeight);
		rightGraphics.dispose();

		// ==== Render Buttons ====
		// initiate variable
		UiLayout layout = layout(displayWidth, displayHeight);
		int mouseX = layout.toDesignX(Mouse.getX());
		int mouseY = layout.toDesignY(Mouse.getY());
		for (int i = 0; i < buttons.length; i++) {
			int offset = 0;
			float alphaB = 0;
			Graphics2D buttonGraphics = (Graphics2D) g.create();
			if (MenuButton.values()[i].equals(MenuButton.BACK)) {
				offset = this.exiting ? (int) exitBackButtonOffsetY.value() : (int) backButtonOffsetY.value();
				alphaB = this.exiting ? (float) exitBackButtonAlpha.value() : (float) backButtonAlpha.value();
			}
			else if (MenuButton.values()[i].equals(MenuButton.OK)) {
				offset = this.exiting ? (int) exitOkButtonOffsetY.value() : (int) okButtonOffsetY.value();
				alphaB = this.exiting ? (float) exitOkButtonAlpha.value() : (float) okButtonAlpha.value();
			}
			buttonGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaB));
			buttons[i].renderAt(buttonGraphics, layout, mouseX, mouseY, Mouse.getB() == Mouse.LMB, 
					0, offset);
			buttonGraphics.dispose();
		}
	}

	private int getButtonX(MenuButton button) {
		if (button == MenuButton.BACK) {
			return (designWidth - backButtonWidth) / 2 - (backButtonWidth * 1 / 2);
		} else if (button == MenuButton.OK){
			return (designWidth - okButtonWidth) / 2 + (okButtonWidth * 1 / 2);
		}
		return 0;
	}

	private int getButtonY(MenuButton button) {
		if (button == MenuButton.BACK) {
			return (designHeight + enterNameWindowHeight) / 2 - backButtonHeight + designHeight / 24;
		} else if (button == MenuButton.OK){
			return (designHeight + enterNameWindowHeight) / 2 - okButtonHeight + designHeight / 24;
		}
		return 0;
	}

	private String getButtonAssetPath(MenuButton button, int index) {
		return "/buttons/" + button.assetName;
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
