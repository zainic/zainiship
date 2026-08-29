package com.zainic.zainiship.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{

	private boolean[] keys = new boolean[1000];
	private final StringBuilder typedCharacters = new StringBuilder();
	private boolean backspacePressed;
	public boolean up, down, left, right, space, pause, enter;
	
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE];
		pause = keys[KeyEvent.VK_P];
		enter = keys[KeyEvent.VK_ENTER];
	}
	
	@Override
	public synchronized void keyTyped(KeyEvent e) {
		char character = e.getKeyChar();
		if (!Character.isISOControl(character)) {
			typedCharacters.append(character);
		}
	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode >= 0 && keyCode < keys.length) {
			keys[keyCode] = true;
		}
		if (keyCode == KeyEvent.VK_BACK_SPACE) {
			backspacePressed = true;
		}
	}

	@Override
	public synchronized void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode >= 0 && keyCode < keys.length) {
			keys[keyCode] = false;
		}
	}

	public synchronized String consumeTypedCharacters() {
		String characters = typedCharacters.toString();
		typedCharacters.setLength(0);
		return characters;
	}

	public synchronized boolean consumeBackspace() {
		boolean pressed = backspacePressed;
		backspacePressed = false;
		return pressed;
	}

	public synchronized boolean isKeyDown(int keyCode) {
		return keyCode >= 0 && keyCode < keys.length && keys[keyCode];
	}

}

