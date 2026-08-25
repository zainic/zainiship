package com.zainic.zainiship.ui;

import java.awt.Graphics;

import com.zainic.zainiship.input.Mouse;

/** Owns menu state, input handling, rendering, and transitions within the menu flow. */
public final class MenuManager {

	public enum MenuResult {
		NONE,
		START_GAME
	}

	private enum MenuState {
		LOADING,
		MAIN_MENU,
		CREATE_NEW_GAME
	}

	private MenuState state = MenuState.LOADING;
	private final LoadingMenu loadingMenu;
	private final MainMenu mainMenu;
	private final CreateNewGameMenu createNewGameMenu;

	public MenuManager(int designWidth, int designHeight) {
		loadingMenu = new LoadingMenu(designWidth, designHeight);
		mainMenu = new MainMenu(designWidth, designHeight);
		createNewGameMenu = new CreateNewGameMenu(designWidth, designHeight);
	}

	public MenuResult update(int displayWidth, int displayHeight) {
		switch (state) {
			case LOADING:
				loadingMenu.update();
				if (loadingMenu.getProgressBar() >= 100 && loadingMenu.getAlphaFade() >= 1.0f) {
					state = MenuState.MAIN_MENU;
				}
				return MenuResult.NONE;
			case MAIN_MENU:
				mainMenu.update();
				if (Mouse.consumeLeftClick() && mainMenu.isNewGameClicked(displayWidth, displayHeight)) {
					state = MenuState.CREATE_NEW_GAME;
				}
				return MenuResult.NONE;
			case CREATE_NEW_GAME:
				createNewGameMenu.update();
				if (!Mouse.consumeLeftClick()) {
					return MenuResult.NONE;
				}
				CreateNewGameMenu.Action action = createNewGameMenu.getClickedAction(displayWidth, displayHeight);
				if (action == CreateNewGameMenu.Action.BACK) {
					state = MenuState.MAIN_MENU;
					return MenuResult.NONE;
				}
				return action.selectsSaveSlot() ? MenuResult.START_GAME : MenuResult.NONE;
			default:
				throw new IllegalStateException("Unhandled menu state: " + state);
		}
	}

	public void render(Graphics g, int displayWidth, int displayHeight) {
		switch (state) {
			case LOADING:
				loadingMenu.render(g, displayWidth, displayHeight);
				return;
			case MAIN_MENU:
				mainMenu.render(g, displayWidth, displayHeight);
				return;
			case CREATE_NEW_GAME:
				createNewGameMenu.render(g, displayWidth, displayHeight);
				return;
			default:
				throw new IllegalStateException("Unhandled menu state: " + state);
		}
	}
}
