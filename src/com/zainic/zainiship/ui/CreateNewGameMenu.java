package com.zainic.zainiship.ui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import com.zainic.zainiship.input.Mouse;
import com.zainic.zainiship.ui.animation.Tween;

public class CreateNewGameMenu extends Menu {

	private int backgroundWidth;
	private int backgroundHeight;
	private int slotButtonWidth;
	private int slotButtonHeight;
	private int slotButtonGap;
	private int firstSlotButtonX;
	private int firstSlotButtonY;
	private int backButtonWidth;
	private int backButtonHeight;
	private int titleWidth;
	private int titleHeight;
	private int[] pixels, background;
	private MenuButton[] menuButtons;
	private boolean backgroundDirty;

	public enum Action {
		NONE(-1),
		BACK(-1),
		SELECT_SAVE_SLOT_1(0),
		SELECT_SAVE_SLOT_2(1),
		SELECT_SAVE_SLOT_3(2),
		SELECT_SAVE_SLOT_4(3);

		private final int saveSlotIndex;

		Action(int saveSlotIndex) {
			this.saveSlotIndex = saveSlotIndex;
		}

		public boolean selectsSaveSlot() {
			return saveSlotIndex >= 0;
		}

		public int getSaveSlotIndex() {
			return saveSlotIndex;
		}
	}

	private enum MenuButton {
		BACK("Back", true, Action.BACK),
		SLOT_1("Slot", true, Action.SELECT_SAVE_SLOT_1),
		SLOT_2("Slot", true, Action.SELECT_SAVE_SLOT_2),
		SLOT_3("Slot", true, Action.SELECT_SAVE_SLOT_3),
		SLOT_4("Slot", true, Action.SELECT_SAVE_SLOT_4);

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
	private final BufferedImage backgroundImage = loadImage("/menu/createnewgamemenu/createnewgamemenu_background.png");
	private final BufferedImage titleImage = loadImage("/menu/createnewgamemenu/title.png");
	private final Button[] buttons = new Button[MenuButton.values().length];
	private final Tween backgroundOffsetY, exitBackgroundOffsetY;
	private final Tween titleAlpha, exitTitleAlpha;
	private final Tween backButtonOffsetX, exitBackButtonOffsetX;
	private final Tween[] slotButtonOffsetY = new Tween[
		(int) Arrays.stream(MenuButton.values())
        .filter(button -> button.assetName.equals("Slot"))
        .count()
	];
	private final Tween[] slotButtonAlpha = new Tween[
		(int) Arrays.stream(MenuButton.values())
        .filter(button -> button.assetName.equals("Slot"))
        .count()
	];
	private final Tween[] exitSlotButtonAlpha = new Tween[
		(int) Arrays.stream(MenuButton.values())
        .filter(button -> button.assetName.equals("Slot"))
        .count()
	];

	public CreateNewGameMenu(int width, int height) {
		super(width, height);
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		this.backgroundWidth = backgroundImage.getWidth();
		this.backgroundHeight = backgroundImage.getHeight();
		this.background = new int[backgroundWidth * backgroundHeight];
		backgroundImage.getRGB(0, 0, backgroundWidth, backgroundHeight, background, 0, backgroundWidth);
		this.titleWidth = designWidth * 3 / 5;
		this.titleHeight = titleWidth * titleImage.getHeight() / titleImage.getWidth();
		this.titleWidth += designWidth * 4 / 19;
		this.menuButtons = MenuButton.values();
		BufferedImage sampleBackImage = loadImage("/buttons/Back_init.png");
		BufferedImage sampleSlotImage = loadImage("/buttons/EmptySlot_init.png");
		this.backButtonWidth = designWidth * 1 / 5;
		this.backButtonHeight = this.backButtonWidth * sampleBackImage.getHeight() / sampleBackImage.getWidth();
		this.slotButtonWidth = designWidth * 1 / 5;
		this.slotButtonHeight = this.slotButtonWidth * sampleSlotImage.getHeight() / sampleSlotImage.getWidth();
		this.slotButtonGap = designWidth * 1 / 360;
		this.firstSlotButtonX = (designWidth - (4*slotButtonWidth + 3*slotButtonGap)) / 2;
		this.firstSlotButtonY = designHeight * 2 / 7;
		for (int i = 0; i < menuButtons.length; i++) {
			MenuButton menuButton = menuButtons[i];
			String path = getButtonAssetPath(menuButton, i);
			int buttonWidth = menuButton == MenuButton.BACK ? backButtonWidth : slotButtonWidth;
			int buttonHeight = menuButton == MenuButton.BACK ? backButtonHeight : slotButtonHeight;
			buttons[i] = new Button(getButtonX(menuButton), getButtonY(menuButton), buttonWidth, buttonHeight,
					loadImage(path + "_init.png"), loadImage(path + "_hovered.png"),
					loadImage(path + "_clicked.png"), menuButton.enabled);
		}
		this.backgroundOffsetY = new Tween(designHeight, 0, 30, 0, "ease-in-out");
		this.exitBackgroundOffsetY = new Tween(0, designHeight, 30, 30, "ease-in-out");
		this.titleAlpha = new Tween(0, 1, 30, 30, "ease-in");
		this.exitTitleAlpha = new Tween(1, 0, 30, 0, "ease-out");
		this.backButtonOffsetX = new Tween(-backButtonWidth-getButtonX(MenuButton.BACK), 0, 30, 30, "ease-in");
		this.exitBackButtonOffsetX = new Tween(0, -backButtonWidth-getButtonX(MenuButton.BACK), 30, 0, "ease-out");
		for (int i = 0; i < slotButtonOffsetY.length; i++) {
			slotButtonOffsetY[i] = new Tween(-slotButtonWidth / 5, 0, 30, 30, "ease-out");
		}
		for (int i = 0; i < slotButtonAlpha.length; i++) {
			slotButtonAlpha[i] = new Tween(0, 1, 30, 30, "ease-out");
			exitSlotButtonAlpha[i] = new Tween(1, 0, 30, 0, "ease-in");
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
		return backButtonOffsetX.isFinished()
				&& Arrays.stream(slotButtonOffsetY).allMatch(Tween::isFinished)
				&& Arrays.stream(slotButtonAlpha).allMatch(Tween::isFinished)
				&& !exiting;
	}

	@Override
	public void startExit(MenuManager.MenuState toState) {
		if (exiting) return;
		super.startExit(toState);
		if (toState != MenuManager.MenuState.ENTER_NAME) {
			exitBackgroundOffsetY.reset();
		}
		exitTitleAlpha.reset();
		exitBackButtonOffsetX.reset();
		for (Tween tween : exitSlotButtonAlpha) tween.reset();
	}

	@Override
	public boolean isExitFinished() {
		if (!exiting) return false;

		boolean commonAnimationsFinished = exitTitleAlpha.isFinished()
			&& exitBackButtonOffsetX.isFinished()
			&& Arrays.stream(exitSlotButtonAlpha).allMatch(Tween::isFinished);

		switch (exitToState) {
			case ENTER_NAME:
				return commonAnimationsFinished;
			case MAIN_MENU:
				return commonAnimationsFinished && exitBackgroundOffsetY.isFinished();
			default:
				throw new IllegalStateException("Unsupported Create New Game exit: " + exitToState);
		}
	}

	@Override
	public void onEnter(MenuManager.MenuState fromState){
		super.onEnter(fromState);
		backgroundDirty = true;
		if (this.fromState != MenuManager.MenuState.ENTER_NAME) {
			backgroundOffsetY.reset();
			exitBackgroundOffsetY.reset();
		}
		titleAlpha.reset();
		backButtonOffsetX.reset();
		for (Tween tween : slotButtonAlpha) tween.reset();
		for (Tween tween : slotButtonOffsetY) tween.reset();
		exitTitleAlpha.reset();
		exitBackButtonOffsetX.reset();
		for (Tween tween : exitSlotButtonAlpha) tween.reset();
	}

	@Override
	public void update() {
		this.time++;
		backgroundDirty = true;
		if (exiting) {
			if (exitToState != MenuManager.MenuState.ENTER_NAME) {
				exitBackgroundOffsetY.update();
			}
			exitTitleAlpha.update();
			exitBackButtonOffsetX.update();
			for (Tween tween : exitSlotButtonAlpha) tween.update();
			return;
		}
		if (this.fromState != MenuManager.MenuState.ENTER_NAME) {
			backgroundOffsetY.update();
		}
		titleAlpha.update();
		backButtonOffsetX.update();
		for (Tween tween : slotButtonAlpha) tween.update();
		for (Tween tween : slotButtonOffsetY) tween.update();
	}

	@Override
	public void render(Graphics g, int displayWidth, int displayHeight) {
		// Render Background
		if (backgroundDirty) {
			if (exiting) {
				renderBackground(0, (int) exitBackgroundOffsetY.value());
			}
			else {
				renderBackground(0, (int) backgroundOffsetY.value());
			}
			
			backgroundDirty = false;
		}
		g.drawImage(this.image, 0, 0, displayWidth, displayHeight, null);

		// Render Title Create New Game Menu
		Graphics2D titleGraphics = (Graphics2D) g.create();
		float alpha = this.exiting ? (float) exitTitleAlpha.value() : (float) titleAlpha.value();
		titleGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		drawScaled(titleGraphics, titleImage, (designWidth - titleWidth) / 2, 20, titleWidth, titleHeight, displayWidth, displayHeight);
		titleGraphics.dispose();

		// Render Buttons Create New Game Menu
		UiLayout layout = layout(displayWidth, displayHeight);
		int mouseX = layout.toDesignX(Mouse.getX());
		int mouseY = layout.toDesignY(Mouse.getY());
		for (int i = 0; i < buttons.length; i++) {
			if (MenuButton.values()[i].equals(MenuButton.BACK)) {
				int offset = this.exiting ? (int) exitBackButtonOffsetX.value() : (int) backButtonOffsetX.value();
				buttons[i].renderAt(g, layout, mouseX, mouseY, Mouse.getB() == Mouse.LMB, 
					offset, 0);
			}
			else {
				Graphics2D buttonGraphics = (Graphics2D) g.create();
				float alphaB = this.exiting ? (float) exitSlotButtonAlpha[i-1].value() : (float) slotButtonAlpha[i-1].value();
				buttonGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaB));
				buttons[i].renderAt(buttonGraphics, layout, mouseX, mouseY, Mouse.getB() == Mouse.LMB, 
					0, (int) slotButtonOffsetY[i-1].value());
				buttonGraphics.dispose();
			}
		}
	}

	private int getButtonX(MenuButton button) {
		if (button == MenuButton.BACK) {
			return (designWidth - backButtonWidth) / 2 - (5 * designWidth / 14);
		} else {
			int slotIndex = button.ordinal() - MenuButton.SLOT_1.ordinal();
			return firstSlotButtonX + slotIndex * (slotButtonWidth + slotButtonGap);
		}
	}

	private int getButtonY(MenuButton button) {
		if (button == MenuButton.BACK) {
			return designHeight * 11 / 12 - backButtonHeight;
		} else {
			return firstSlotButtonY;
		}
	}

	private boolean isExist(int indexSlot){
		return false;
	}

	private String getButtonAssetPath(MenuButton button, int index) {
		if (button == MenuButton.BACK) {
			return "/buttons/" + button.assetName;
		}
		return "/buttons/" + (isExist(index - 1) ? "Filled" : "Empty") + button.assetName;
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
