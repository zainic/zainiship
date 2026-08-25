package com.zainic.zainiship.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.zainic.zainiship.input.Mouse;

public class CreateNewGameMenu extends Menu {

	private int slotButtonWidth;
	private int slotButtonHeight;
	private int slotButtonGap;
	private int firstSlotButtonX;
	private int firstSlotButtonY;
	private int backButtonWidth;
	private int backButtonHeight;
	private int titleWidth;
	private int titleHeight;
	private MenuButton[] menuButtons;

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

	private final BufferedImage background = loadImage("/menu/createnewgamemenu/createnewgamemenu_background.png");
	private final BufferedImage title = loadImage("/menu/createnewgamemenu/title.png");
	private final Button[] buttons = new Button[MenuButton.values().length];

	public CreateNewGameMenu(int width, int height) {
		super(width, height);
		this.titleWidth = designWidth * 3 / 5;
		this.titleHeight = titleWidth * title.getHeight() / title.getWidth();
		this.titleWidth += designWidth * 4 / 19;
		this.menuButtons = MenuButton.values();
		BufferedImage backImage = loadImage("/buttons/Back_init.png");
		BufferedImage slotImage = loadImage("/buttons/EmptySlot_init.png");
		this.backButtonWidth = designWidth * 1 / 4;
		this.backButtonHeight = this.backButtonWidth * backImage.getHeight() / backImage.getWidth();
		this.slotButtonWidth = designWidth * 1 / 5;
		this.slotButtonHeight = this.slotButtonWidth * slotImage.getHeight() / slotImage.getWidth();
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
	}

	public Action getClickedAction(int displayWidth, int displayHeight) {
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

	public void update() {
		this.time++;
	}

	public void render(Graphics g, int displayWidth, int displayHeight) {
		g.drawImage(background, 0, 0, displayWidth, displayHeight, null);
		drawScaled(g, title, (designWidth - titleWidth) / 2, 20, this.titleWidth, this.titleHeight, displayWidth, displayHeight);
		UiLayout layout = layout(displayWidth, displayHeight);
		int mouseX = layout.toDesignX(Mouse.getX());
		int mouseY = layout.toDesignY(Mouse.getY());
		for (Button button : buttons) {
			button.render(g, layout, mouseX, mouseY, Mouse.getB() == Mouse.LMB);
		}
	}

	private int getButtonX(MenuButton button) {
		if (button == MenuButton.BACK) {
			return (designWidth - backButtonWidth) / 2 - (2 * designWidth / 7);
		} else {
			int slotIndex = button.ordinal() - MenuButton.SLOT_1.ordinal();
			return firstSlotButtonX + slotIndex * (slotButtonWidth + slotButtonGap);
		}
	}

	private int getButtonY(MenuButton button) {
		if (button == MenuButton.BACK) {
			return designHeight * 8 / 9 - backButtonHeight;
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
}
