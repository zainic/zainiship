package com.zainic.zainiship.ui;

import java.awt.Color;
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
	private MenuButton[] buttons;

	private enum MenuButton {
		BACK("Back", true),
		SLOT_1("Slot", true),
		SLOT_2("Slot", true),
		SLOT_3("Slot", true),
		SLOT_4("Slot", true);

		final String assetName;
		final boolean enabled;

		MenuButton(String assetName, boolean enabled) {
			this.assetName = assetName;
			this.enabled = enabled;
		}
	}

	private final BufferedImage background = loadImage("/menu/createnewgamemenu/createnewgamemenu_background.png");
	private final BufferedImage title = loadImage("/menu/createnewgamemenu/title.png");
	private final BufferedImage[] initialButtons = new BufferedImage[MenuButton.values().length];
	private final BufferedImage[] hoveredButtons = new BufferedImage[MenuButton.values().length];
	private final BufferedImage[] clickedButtons = new BufferedImage[MenuButton.values().length];

	public CreateNewGameMenu(int width, int height) {
		super(width, height);
		this.titleWidth = designWidth * 3 / 5;
		this.titleHeight = titleWidth * title.getHeight() / title.getWidth();
		this.titleWidth += designWidth * 4 / 19;
		this.buttons = MenuButton.values();
		for (int i = 0; i < this.buttons.length; i++) {
			String path;
			if (this.buttons[i] == MenuButton.BACK){
				path = "/buttons/" + this.buttons[i].assetName;
			}
			else {
				boolean loadExist = isExist(i - 1);
				if (loadExist){
					path = "/buttons/Filled" + this.buttons[i].assetName;
				}
				else{
					path = "/buttons/Empty" + this.buttons[i].assetName;
				}
			}
			initialButtons[i] = loadImage(path + "_init.png");
			hoveredButtons[i] = loadImage(path + "_hovered.png");
			clickedButtons[i] = loadImage(path + "_clicked.png");
		}
		this.backButtonWidth = designWidth * 1 / 4;
		this.backButtonHeight = this.backButtonWidth * initialButtons[MenuButton.BACK.ordinal()].getHeight() / initialButtons[MenuButton.BACK.ordinal()].getWidth();
		this.slotButtonWidth = designWidth * 1 / 5;
		this.slotButtonHeight = this.slotButtonWidth * initialButtons[MenuButton.SLOT_1.ordinal()].getHeight() / initialButtons[MenuButton.SLOT_1.ordinal()].getWidth();
		this.slotButtonGap = designWidth * 1 / 360;
		this.firstSlotButtonX = (designWidth - (4*slotButtonWidth + 3*slotButtonGap)) / 2;
		this.firstSlotButtonY = designHeight * 2 / 7;
	}

	public boolean isSlotGameClicked(int slotIndex, int displayWidth, int displayHeight) {
		return isInsideButton(buttons[MenuButton.SLOT_1.ordinal() + slotIndex], toDesignX(Mouse.getX(), displayWidth), toDesignY(Mouse.getY(), displayHeight));
	}

	public boolean isBackClicked(int displayWidth, int displayHeight) {
		return isInsideButton(buttons[MenuButton.BACK.ordinal()], toDesignX(Mouse.getX(), displayWidth), toDesignY(Mouse.getY(), displayHeight));
	}

	public void update() {
		this.time++;
	}

	public void render(Graphics g, int displayWidth, int displayHeight) {
		g.drawImage(background, 0, 0, displayWidth, displayHeight, null);
		drawScaled(g, title, (designWidth - titleWidth) / 2, 20, this.titleWidth, this.titleHeight, displayWidth, displayHeight);
		int mouseX = toDesignX(Mouse.getX(), displayWidth);
		int mouseY = toDesignY(Mouse.getY(), displayHeight);
		for (int i = 0; i < buttons.length; i++) {
			boolean hovered = isInsideButton(buttons[i], mouseX, mouseY);
			BufferedImage buttonImage = initialButtons[i];
			if (buttons[i].enabled && hovered) {
				buttonImage = Mouse.getB() == Mouse.LMB ? clickedButtons[i] : hoveredButtons[i];
			}
			if (buttons[i] == MenuButton.BACK) {
				drawScaled(g, buttonImage, getButtonX(buttons[i]), getButtonY(buttons[i]), backButtonWidth, backButtonHeight, displayWidth, displayHeight);
			} else {
				drawScaled(g, buttonImage, getButtonX(buttons[i]), getButtonY(buttons[i]), slotButtonWidth, slotButtonHeight, displayWidth, displayHeight);
			}
			if (!buttons[i].enabled) {
				g.setColor(new Color(0, 0, 0, 115));
				if (buttons[i] == MenuButton.BACK) {
					g.fillRect(scaleX(getButtonX(buttons[i]), displayWidth), scaleY(getButtonY(buttons[i]), displayHeight), scaleX(backButtonWidth, displayWidth), scaleY(backButtonHeight, displayHeight));
				} else {
					g.fillRect(scaleX(getButtonX(buttons[i]), displayWidth), scaleY(getButtonY(buttons[i]), displayHeight), scaleX(slotButtonWidth, displayWidth), scaleY(slotButtonHeight, displayHeight));
				}
			}
		}
	}

	private boolean isInsideButton(MenuButton button, int x, int y) {
		if (button == MenuButton.BACK) {
			return x >= getButtonX(button) && x < getButtonX(button) + backButtonWidth && y >= getButtonY(button) && y < getButtonY(button) + backButtonHeight;
		} else {
			return x >= getButtonX(button) && x < getButtonX(button) + slotButtonWidth && y >= getButtonY(button) && y < getButtonY(button) + slotButtonHeight;
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

	public String[] getButtons(){
		String[] textButtons = new String[buttons.length];
		for (int i = 0; i < buttons.length; i++) {
			textButtons[i] = buttons[i].name();
		}
		return textButtons;
	}
}
