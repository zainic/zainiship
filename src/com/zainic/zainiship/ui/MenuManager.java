package com.zainic.zainiship.ui;

import java.awt.Graphics;

import com.zainic.zainiship.input.Mouse;

/** Owns menu state, input handling, rendering, and transitions within the menu flow. */
public final class MenuManager {

	public enum MenuResult {
		NONE,
		START_GAME
	}

	public enum MenuState {
		LOADING,
		MAIN_MENU,
		CREATE_NEW_GAME,
		LOAD_GAME,
		SETTINGS,
		INFORMATION,
		QUIT_GAME
	}

	private MenuState state = MenuState.LOADING;
	private MenuState nextState, fromState;
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
				if (loadingMenu.getProgressBar() >= 100) {
					fromState = state;
					state = MenuState.MAIN_MENU;
					activeMenu().onEnter(fromState);
				}
				return MenuResult.NONE;
			case MAIN_MENU:
				mainMenu.update();
				if (mainMenu.isExitFinished()) {
					Mouse.consumeLeftClick();
					fromState = state;
					state = nextState;
					activeMenu().onEnter(fromState);
					return MenuResult.NONE;
				}
				if (mainMenu.isExiting()) {
					Mouse.consumeLeftClick();
					return MenuResult.NONE;
				}
				if (!Mouse.consumeLeftClick()) {
					return MenuResult.NONE;
				}
				MainMenu.Action mainMenuAction = mainMenu.getClickedAction(displayWidth, displayHeight);
				if (!(mainMenuAction.equals(MainMenu.Action.NONE))) {
					mainMenu.startExit();
				}
				switch(mainMenuAction) {
					case NEW_GAME:
						nextState = MenuState.CREATE_NEW_GAME;
						break;
					case LOAD_GAME:
						nextState = MenuState.CREATE_NEW_GAME;
						break;
					case SETTINGS:
						nextState = MenuState.CREATE_NEW_GAME;
						break;
					case INFORMATION:
						nextState = MenuState.CREATE_NEW_GAME;
						break;
					case QUIT_GAME:
						nextState = MenuState.CREATE_NEW_GAME;
						break;
					default:
						nextState = MenuState.MAIN_MENU;
				}
				return MenuResult.NONE;
			case CREATE_NEW_GAME:
				createNewGameMenu.update();
				if (createNewGameMenu.isExitFinished()) {
					Mouse.consumeLeftClick();
					fromState = state;
					state = nextState;
					activeMenu().onEnter(fromState);
					return MenuResult.NONE;
				}
				if (createNewGameMenu.isExiting()) {
					Mouse.consumeLeftClick();
					return MenuResult.NONE;
				}
				if (!Mouse.consumeLeftClick()) {
					return MenuResult.NONE;
				}
				CreateNewGameMenu.Action createNewMenuAction = createNewGameMenu.getClickedAction(displayWidth, displayHeight);
				if (!(createNewMenuAction.equals(CreateNewGameMenu.Action.NONE))) {
					createNewGameMenu.startExit();
				}
				switch(createNewMenuAction) {
					case BACK:
						nextState = MenuState.MAIN_MENU;
						return MenuResult.NONE;
					case SELECT_SAVE_SLOT_1:
						return MenuResult.START_GAME;
					case SELECT_SAVE_SLOT_2:
						return MenuResult.START_GAME;
					case SELECT_SAVE_SLOT_3:
						return MenuResult.START_GAME;
					case SELECT_SAVE_SLOT_4:
						return MenuResult.START_GAME;
					default:
						nextState = MenuState.CREATE_NEW_GAME;
						return MenuResult.NONE;
				}
			default:
				throw new IllegalStateException("Unhandled menu state: " + state);
		}
	}

	private Menu activeMenu() {
		switch (state) {
			case LOADING: return loadingMenu;
			case MAIN_MENU: return mainMenu;
			case CREATE_NEW_GAME: return createNewGameMenu;
			default: throw new IllegalStateException("Unhandled menu state: " + state);
		}
	}

	public void render(Graphics g, int displayWidth, int displayHeight) {
		activeMenu().render(g, displayWidth, displayHeight);
	}
}
