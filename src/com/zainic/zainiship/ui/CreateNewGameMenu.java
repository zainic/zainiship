package com.zainic.zainiship.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import com.zainic.zainiship.input.Mouse;
import com.zainic.zainiship.save.SaveData;
import com.zainic.zainiship.save.SaveManager;
import com.zainic.zainiship.ui.animation.Tween;
import com.zainic.zainiship.ui.components.Button;

public class CreateNewGameMenu extends Menu {
	private static final DateTimeFormatter SAVED_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
	private static final DateTimeFormatter SAVED_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private int backgroundWidth;
	private int backgroundHeight;
	private int slotButtonWidth;
	private int slotButtonHeight;
	private int slotButtonGap;
	private int firstSlotButtonX;
	private int firstSlotButtonY;
	private int backButtonWidth;
	private int backButtonHeight;
	private int confirmationButtonWidth;
	private int confirmationButtonHeight;
	private int titleWidth;
	private int titleHeight;
	private int[] pixels, background;
	private MenuButton[] menuButtons;
	private boolean backgroundDirty;
	private final SaveManager saveManager;
	private final SaveData[] slotData = new SaveData[SaveManager.SLOT_COUNT];
	private int confirmationSlot = -1;

	public enum Action {
		NONE(-1),
		BACK(-1),
		SELECT_SAVE_SLOT_1(0),
		SELECT_SAVE_SLOT_2(1),
		SELECT_SAVE_SLOT_3(2),
		SELECT_SAVE_SLOT_4(3),
		CONFIRM_REPLACE(-1),
		CANCEL_REPLACE(-1);

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
		SLOT_4("Slot", true, Action.SELECT_SAVE_SLOT_4),
		CANCEL_REPLACE("Back_only", true, Action.CANCEL_REPLACE),
		CONFIRM_REPLACE("OK_only", true, Action.CONFIRM_REPLACE);

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
	private final BufferedImage confirmationWindowImage = loadImage("/menu/createnewgamemenu/confirmation_window.png");
	private final BufferedImage emptySlotInitialImage = loadImage("/buttons/EmptySlot_init.png");
	private final BufferedImage emptySlotHoveredImage = loadImage("/buttons/EmptySlot_hovered.png");
	private final BufferedImage emptySlotClickedImage = loadImage("/buttons/EmptySlot_clicked.png");
	private final BufferedImage filledSlotInitialImage = loadImage("/buttons/FilledSlot_init.png");
	private final BufferedImage filledSlotHoveredImage = loadImage("/buttons/FilledSlot_hovered.png");
	private final BufferedImage filledSlotClickedImage = loadImage("/buttons/FilledSlot_clicked.png");
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

	public CreateNewGameMenu(int width, int height, SaveManager saveManager) {
		super(width, height);
		this.saveManager = saveManager;
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
		this.backButtonWidth = designWidth * 1 / 5;
		this.backButtonHeight = this.backButtonWidth * sampleBackImage.getHeight() / sampleBackImage.getWidth();
		this.slotButtonWidth = designWidth * 1 / 5;
		this.slotButtonHeight = this.slotButtonWidth * emptySlotInitialImage.getHeight() / emptySlotInitialImage.getWidth();
		this.slotButtonGap = designWidth * 1 / 360;
		this.firstSlotButtonX = (designWidth - (4*slotButtonWidth + 3*slotButtonGap)) / 2;
		this.firstSlotButtonY = designHeight * 2 / 7;
		BufferedImage sampleConfirmationImage = loadImage("/buttons/OK_only_init.png");
		this.confirmationButtonWidth = designWidth * 9 / 64;
		this.confirmationButtonHeight = confirmationButtonWidth
				* sampleConfirmationImage.getHeight() / sampleConfirmationImage.getWidth();
		for (int i = 0; i < menuButtons.length; i++) {
			createButton(i);
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
		if (confirmationSlot >= 0) {
			if (buttons[MenuButton.CONFIRM_REPLACE.ordinal()].isClicked(mouseX, mouseY)) {
				return MenuButton.CONFIRM_REPLACE.action;
			}
			if (buttons[MenuButton.CANCEL_REPLACE.ordinal()].isClicked(mouseX, mouseY)) {
				return MenuButton.CANCEL_REPLACE.action;
			}
			return Action.NONE;
		}
		for (int i = 0; i < buttons.length; i++) {
			if (menuButtons[i] == MenuButton.CONFIRM_REPLACE
					|| menuButtons[i] == MenuButton.CANCEL_REPLACE) continue;
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
		confirmationSlot = -1;
		refreshSlots();
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
		if (confirmationSlot >= 0) {
			for (MenuButton mButton: menuButtons){
				if (mButton.equals(MenuButton.BACK) || mButton.assetName.equals("Slot")) {
					buttons[mButton.ordinal()].setEnabled(false);
				}
			}
		}
		else {
			for (MenuButton mButton: menuButtons){
				buttons[mButton.ordinal()].setEnabled(true);
			}
		}
		titleAlpha.update();
		backButtonOffsetX.update();
		for (Tween tween : slotButtonAlpha) tween.update();
		for (Tween tween : slotButtonOffsetY) tween.update();
	}

	@Override
	public void render(Graphics g, int displayWidth, int displayHeight) {
		renderBackgroundLayer(g, displayWidth, displayHeight);
		renderOverlay(g, displayWidth, displayHeight, false);
		renderComponents(g, displayWidth, displayHeight);
		if (confirmationSlot >= 0) renderOverwriteConfirmation(g, displayWidth, displayHeight);
	}

	private void renderBackgroundLayer(Graphics g, int displayWidth, int displayHeight) {
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
	}

	private void renderOverlay(Graphics g, int displayWidth, int displayHeight, boolean foreground) {
		Graphics2D titleGraphics = (Graphics2D) g.create();
		float alpha = this.exiting ? (float) exitTitleAlpha.value() : (float) titleAlpha.value();
		titleGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		drawScaled(titleGraphics, titleImage, (designWidth - titleWidth) / 2, 20, titleWidth, titleHeight, displayWidth, displayHeight);
		titleGraphics.dispose();
		
		if (foreground) {
			// Do nothing
		}
	}

	private void renderComponents(Graphics g, int displayWidth, int displayHeight) {
		UiLayout layout = layout(displayWidth, displayHeight);
		int mouseX = layout.toDesignX(Mouse.getX());
		int mouseY = layout.toDesignY(Mouse.getY());
		for (int i = 0; i < buttons.length; i++) {
			MenuButton menuButton = menuButtons[i];
			if (menuButton == MenuButton.BACK) {
				int offset = this.exiting ? (int) exitBackButtonOffsetX.value() : (int) backButtonOffsetX.value();
				buttons[i].renderAt(g, layout, mouseX, mouseY, Mouse.getB() == Mouse.LMB, 
					offset, 0);
			}
			else if (menuButton.assetName.equals("Slot")) {
				int slotIndex = i - MenuButton.SLOT_1.ordinal();
				Graphics2D buttonGraphics = (Graphics2D) g.create();
				float alphaB = this.exiting ? (float) exitSlotButtonAlpha[slotIndex].value()
						: (float) slotButtonAlpha[slotIndex].value();
				buttonGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaB));
				buttons[i].renderAt(buttonGraphics, layout, mouseX, mouseY, Mouse.getB() == Mouse.LMB, 
					0, (int) slotButtonOffsetY[slotIndex].value());
				SaveData data = slotData[slotIndex];
				if (data != null) {
					buttonGraphics.setColor(Color.WHITE);
					int slotX = getButtonX(menuButton);
					int slotY = getButtonY(menuButton) + (int) slotButtonOffsetY[slotIndex].value();
					int centerX = layout.scaleX(slotX + slotButtonWidth / 2);

					buttonGraphics.setFont(ORBITRON_BOLD.deriveFont(21f));
					drawCenteredText(buttonGraphics, "Slot " + (slotIndex + 1),
							centerX, layout.scaleY(slotY + slotButtonHeight * 16 / 100));
					buttonGraphics.setFont(ORBITRON_REGULAR.deriveFont(15f));
					String playerName = data.getPlayerName().length() <= 16 ? data.getPlayerName() : data.getPlayerName().substring(0, 16);
					drawCenteredText(buttonGraphics, playerName,
							centerX, layout.scaleY(slotY + slotButtonHeight * 25 / 100));

					buttonGraphics.setFont(ORBITRON_BOLD.deriveFont(21f));
					drawCenteredText(buttonGraphics, "Level " + data.getHighestUnlockedLevel(),
							centerX, layout.scaleY(slotY + slotButtonHeight * 40 / 100));
					buttonGraphics.setFont(ORBITRON_REGULAR.deriveFont(11f));
					drawCenteredText(buttonGraphics, "Playtime : " + formatPlaytime(data.getPlaytimeSeconds()),
							centerX, layout.scaleY(slotY + slotButtonHeight * 50 / 100));

					buttonGraphics.setFont(ORBITRON_REGULAR.deriveFont(13f));
					drawCenteredText(buttonGraphics, "Last Saved",
							centerX, layout.scaleY(slotY + slotButtonHeight * 65 / 100));
					buttonGraphics.setFont(ORBITRON_REGULAR.deriveFont(11f));
					drawCenteredText(buttonGraphics, formatLastSavedTime(data.getLastSavedEpochMillis()),
							centerX, layout.scaleY(slotY + slotButtonHeight * 71 / 100));
					drawCenteredText(buttonGraphics, formatLastSavedDate(data.getLastSavedEpochMillis()),
							centerX, layout.scaleY(slotY + slotButtonHeight * 77 / 100));
				}
				buttonGraphics.dispose();
			}
		}
	}

	private void renderOverwriteConfirmation(Graphics g, int displayWidth, int displayHeight) {
		UiLayout layout = layout(displayWidth, displayHeight);
		Graphics2D dialogGraphics = (Graphics2D) g.create();
		dialogGraphics.setColor(new Color(0, 0, 0, 175));
		dialogGraphics.fillRect(0, 0, displayWidth, displayHeight);

		int dialogWidth = designWidth * 7 / 16;
		int dialogHeight = dialogWidth * confirmationWindowImage.getHeight() / confirmationWindowImage.getWidth();
		int dialogX = (designWidth - dialogWidth) / 2;
		int dialogY = (designHeight - dialogHeight) / 2;
		layout.draw(dialogGraphics, confirmationWindowImage, dialogX, dialogY, dialogWidth, dialogHeight);

		dialogGraphics.setFont(ORBITRON_BOLD.deriveFont(20f));
		dialogGraphics.setColor(Color.WHITE);
		drawCenteredText(dialogGraphics, "Replace save slot " + (confirmationSlot + 1) + "?",
				displayWidth / 2, layout.scaleY(dialogY + dialogHeight / 4));
		dialogGraphics.setFont(ORBITRON_REGULAR.deriveFont(15f));
		drawCenteredText(dialogGraphics, "Existing progress will be replaced after entering a new name.",
				displayWidth / 2, layout.scaleY(dialogY + dialogHeight * 2 / 5));

		int mouseX = layout.toDesignX(Mouse.getX());
		int mouseY = layout.toDesignY(Mouse.getY());
		buttons[MenuButton.CANCEL_REPLACE.ordinal()].render(
				dialogGraphics, layout, mouseX, mouseY, Mouse.getB() == Mouse.LMB);
		buttons[MenuButton.CONFIRM_REPLACE.ordinal()].render(
				dialogGraphics, layout, mouseX, mouseY, Mouse.getB() == Mouse.LMB);
		dialogGraphics.dispose();
	}

	private String formatPlaytime(long totalSeconds) {
		long hours = totalSeconds / 3600;
		long minutes = (totalSeconds % 3600) / 60;
		long seconds = totalSeconds % 60;
		return String.format("%dh %02dm %02ds", hours, minutes, seconds);
	}

	private String formatLastSavedTime(long epochMillis) {
		return formatLastSaved(epochMillis, SAVED_TIME_FORMAT, "--:--:--");
	}

	private String formatLastSavedDate(long epochMillis) {
		return formatLastSaved(epochMillis, SAVED_DATE_FORMAT, "--/--/----");
	}

	private String formatLastSaved(long epochMillis, DateTimeFormatter formatter, String fallback) {
		if (epochMillis <= 0L) return fallback;
		return formatter.format(Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()));
	}

	private int getButtonX(MenuButton button) {
		if (button == MenuButton.BACK) {
			return (designWidth - backButtonWidth) / 2 - (5 * designWidth / 14);
		} else if (button.assetName.equals("Slot")) {
			int slotIndex = button.ordinal() - MenuButton.SLOT_1.ordinal();
			return firstSlotButtonX + slotIndex * (slotButtonWidth + slotButtonGap);
		} else if (button == MenuButton.CANCEL_REPLACE) {
			return designWidth / 2 - designWidth / 80 - confirmationButtonWidth;
		} else if (button == MenuButton.CONFIRM_REPLACE) {
			return designWidth / 2 + designWidth / 80;
		}
		throw new IllegalArgumentException("Unsupported menu button: " + button);
	}

	private int getButtonY(MenuButton button) {
		if (button == MenuButton.BACK) {
			return designHeight * 11 / 12 - backButtonHeight;
		} else if (button.assetName.equals("Slot")) {
			return firstSlotButtonY;
		} else if (button == MenuButton.CANCEL_REPLACE || button == MenuButton.CONFIRM_REPLACE) {
			return designHeight / 2 + designHeight / 16;
		}
		throw new IllegalArgumentException("Unsupported menu button: " + button);
	}

	public void requestOverwriteConfirmation(int slotIndex) {
		if (slotData[slotIndex] == null) throw new IllegalArgumentException("Save slot is empty");
		confirmationSlot = slotIndex;
	}

	public void closeOverwriteConfirmation() {
		confirmationSlot = -1;
	}

	public SaveData getSlotData(int slotIndex) {
		return slotData[slotIndex];
	}

	private void refreshSlots() {
		for (int slotIndex = 0; slotIndex < SaveManager.SLOT_COUNT; slotIndex++) {
			try {
				slotData[slotIndex] = saveManager.exists(slotIndex) ? saveManager.load(slotIndex) : null;
			} catch (Exception exception) {
				System.err.println("Could not read save slot " + (slotIndex + 1) + ": " + exception.getMessage());
				slotData[slotIndex] = null;
			}
			createButton(MenuButton.SLOT_1.ordinal() + slotIndex);
		}
	}

	private boolean isExist(int indexSlot){
		return slotData[indexSlot] != null;
	}

	private void createButton(int index) {
		MenuButton menuButton = menuButtons[index];
		if (!menuButton.assetName.equals("Slot")) {
			int buttonWidth = menuButton == MenuButton.BACK ? backButtonWidth : confirmationButtonWidth;
			int buttonHeight = menuButton == MenuButton.BACK ? backButtonHeight : confirmationButtonHeight;
			String path = "/buttons/" + menuButton.assetName;
			buttons[index] = new Button(getButtonX(menuButton), getButtonY(menuButton), buttonWidth, buttonHeight,
					loadImage(path + "_init.png"), loadImage(path + "_hovered.png"),
					loadImage(path + "_clicked.png"), menuButton.enabled);
			return;
		}
		boolean filled = isExist(index - MenuButton.SLOT_1.ordinal());
		buttons[index] = new Button(getButtonX(menuButton), getButtonY(menuButton), slotButtonWidth, slotButtonHeight,
				filled ? filledSlotInitialImage : emptySlotInitialImage,
				filled ? filledSlotHoveredImage : emptySlotHoveredImage,
				filled ? filledSlotClickedImage : emptySlotClickedImage,
				menuButton.enabled);
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
