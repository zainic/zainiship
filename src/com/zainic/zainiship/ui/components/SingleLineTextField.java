package com.zainic.zainiship.ui.components;

import java.awt.Color;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.function.IntPredicate;

import com.zainic.zainiship.input.Keyboard;

/** A centered, single-line text editor rendered in design-space coordinates. */
public final class SingleLineTextField {

	private static final int KEY_REPEAT_DELAY = 20;
	private static final int KEY_REPEAT_INTERVAL = 3;

	private final Keyboard keyboard;
	private final StringBuilder text = new StringBuilder();
	private final int centerX;
	private final int centerY;
	private final int width;
	private final int height;
	private final int maximumLength;
	private final Font font;
	private final Color color;
	private final IntPredicate characterFilter;
	private final Color backgroundColor;
	private final float backgroundAlpha;
	private final Color borderColor;
	private final float borderAlpha;
	private final float borderThickness;

	private int caretIndex;
	private int leftKeyTicks;
	private int rightKeyTicks;
	private int backspaceKeyTicks;
	private int deleteKeyTicks;
	private boolean homeWasDown;
	private boolean endWasDown;

	public SingleLineTextField(Keyboard keyboard, int centerX, int centerY, int width, int height,
			int maximumLength, Font font, Color color, IntPredicate characterFilter) {
		this(keyboard, centerX, centerY, width, height, maximumLength, font, color,
				characterFilter, null, 0.0f, null, 0.0f, 0.0f);
	}

	public SingleLineTextField(Keyboard keyboard, int centerX, int centerY, int width, int height,
			int maximumLength, Font font, Color fontColor, IntPredicate characterFilter, 
			Color backgroundColor, float backgroundAlpha, Color borderColor, float borderAlpha, float borderThickness) {
		if (keyboard == null || font == null || fontColor == null || characterFilter == null) {
			throw new IllegalArgumentException("Text field arguments cannot be null");
		}
		if (width <= 0 || height <= 0 || maximumLength < 0) {
			throw new IllegalArgumentException("Text field dimensions must be positive and length cannot be negative");
		}
		if (backgroundColor == null && backgroundAlpha != 0.0f) {
			throw new IllegalArgumentException("Background color and alpha must be provided together");
		}
		if (!Float.isFinite(backgroundAlpha) || backgroundAlpha < 0.0f || backgroundAlpha > 1.0f) {
			throw new IllegalArgumentException("Background alpha must be between 0 and 1");
		}
		if ((borderColor == null && (borderAlpha != 0.0f || borderThickness != 0.0f))
				|| (borderColor != null && borderThickness <= 0.0f)) {
			throw new IllegalArgumentException("Border color, alpha, and thickness must be provided together");
		}
		if (!Float.isFinite(borderAlpha) || borderAlpha < 0.0f || borderAlpha > 1.0f) {
			throw new IllegalArgumentException("Border alpha must be between 0 and 1");
		}
		if (!Float.isFinite(borderThickness) || borderThickness < 0.0f) {
			throw new IllegalArgumentException("Border thickness cannot be negative");
		}
		this.keyboard = keyboard;
		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.height = height;
		this.maximumLength = maximumLength;
		this.font = font;
		this.color = fontColor;
		this.characterFilter = characterFilter;
		this.backgroundColor = backgroundColor;
		this.backgroundAlpha = backgroundAlpha;
		this.borderColor = borderColor;
		this.borderAlpha = borderAlpha;
		this.borderThickness = borderThickness;
	}

	public void update() {
		for (char character : keyboard.consumeTypedCharacters().toCharArray()) {
			if (text.length() >= maximumLength) break;
			if (characterFilter.test(character)) {
				text.insert(caretIndex, character);
				caretIndex++;
			}
		}

		// Backspace is handled below with predictable game-tick key repeat.
		keyboard.consumeBackspace();
		leftKeyTicks = updateRepeatingKey(KeyEvent.VK_LEFT, leftKeyTicks,
				keyboard.isKeyDown(KeyEvent.VK_CONTROL) ? this::moveToPreviousWord : this::moveLeft);
		rightKeyTicks = updateRepeatingKey(KeyEvent.VK_RIGHT, rightKeyTicks,
				keyboard.isKeyDown(KeyEvent.VK_CONTROL) ? this::moveToNextWord : this::moveRight);
		backspaceKeyTicks = updateRepeatingKey(KeyEvent.VK_BACK_SPACE, backspaceKeyTicks, 
				keyboard.isKeyDown(KeyEvent.VK_CONTROL) ? this::backspaceWord : this::backspace);
		deleteKeyTicks = updateRepeatingKey(KeyEvent.VK_DELETE, deleteKeyTicks, 
				keyboard.isKeyDown(KeyEvent.VK_CONTROL) ? this::deleteWord : this::delete);

		boolean homeDown = keyboard.isKeyDown(KeyEvent.VK_HOME);
		if (homeDown && !homeWasDown) caretIndex = 0;
		homeWasDown = homeDown;

		boolean endDown = keyboard.isKeyDown(KeyEvent.VK_END);
		if (endDown && !endWasDown) caretIndex = text.length();
		endWasDown = endDown;
	}

	public boolean contains(int x, int y) {
		return x >= centerX - width / 2 && x < centerX + width / 2
				&& y >= centerY - height / 2 && y < centerY + height / 2;
	}

	public boolean click(int x, int y, Graphics2D graphics) {
		if (!contains(x, y)) return false;
		graphics.setFont(font);
		FontMetrics metrics = graphics.getFontMetrics();
		int textStartX = centerX - metrics.stringWidth(text.toString()) / 2;
		int closestIndex = 0;
		int closestDistance = Integer.MAX_VALUE;
		for (int i = 0; i <= text.length(); i++) {
			int boundaryX = textStartX + metrics.stringWidth(text.substring(0, i));
			int distance = Math.abs(x - boundaryX);
			if (distance < closestDistance) {
				closestDistance = distance;
				closestIndex = i;
			}
		}
		caretIndex = closestIndex;
		return true;
	}

	public void render(Graphics2D graphics, boolean showCaret) {
		float currentAlpha = ((AlphaComposite) graphics.getComposite()).getAlpha();
		Graphics2D styleGraphics = (Graphics2D) graphics.create();
		Rectangle2D.Float bounds = new Rectangle2D.Float(centerX - width / 2.0f, centerY - height / 2.0f, width, height);
		if (backgroundColor != null) {
			styleGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentAlpha * backgroundAlpha));
			styleGraphics.setColor(backgroundColor);
			styleGraphics.fill(bounds);
		}
		if (borderColor != null) {
			styleGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentAlpha * borderAlpha));
			styleGraphics.setColor(borderColor);
			styleGraphics.setStroke(new BasicStroke(borderThickness));
			float inset = borderThickness / 2.0f;
			styleGraphics.draw(new Rectangle2D.Float(bounds.x + inset, bounds.y + inset,
					bounds.width - borderThickness, bounds.height - borderThickness));
		}
		styleGraphics.dispose();

		graphics.setColor(color);
		graphics.setFont(font);
		FontMetrics metrics = graphics.getFontMetrics();
		String value = text.toString();
		int textX = centerX - metrics.stringWidth(value) / 2;
		int baselineY = centerY + (metrics.getAscent() - metrics.getDescent()) / 2;
		graphics.drawString(value, textX, baselineY);
		if (showCaret) {
			int caretX = textX + metrics.stringWidth(value.substring(0, caretIndex));
			graphics.drawLine(caretX, baselineY - metrics.getAscent(), caretX,
					baselineY + metrics.getDescent());
		}
	}

	public String getText() {
		return text.toString();
	}

	public String getTrimmedText() {
		return getText().trim();
	}

	public boolean isBlank() {
		return getTrimmedText().isEmpty();
	}

	public void clear() {
		text.setLength(0);
		caretIndex = 0;
		leftKeyTicks = rightKeyTicks = backspaceKeyTicks = deleteKeyTicks = 0;
		homeWasDown = endWasDown = false;
		keyboard.consumeTypedCharacters();
		keyboard.consumeBackspace();
	}

	private void moveLeft() {
		if (caretIndex > 0) caretIndex--;
	}

	private void moveToPreviousWord() {
		while (caretIndex > 0 && Character.isWhitespace(text.charAt(caretIndex - 1))) {
			caretIndex--;
		}
		while (caretIndex > 0 && !Character.isWhitespace(text.charAt(caretIndex - 1))) {
			caretIndex--;
		}
	}

	private void moveRight() {
		if (caretIndex < text.length()) caretIndex++;
	}

	private void moveToNextWord() {
		while (caretIndex < text.length() && !Character.isWhitespace(text.charAt(caretIndex))) {
			caretIndex++;
		}
		while (caretIndex < text.length() && Character.isWhitespace(text.charAt(caretIndex))) {
			caretIndex++;
		}
	}

	private void backspace() {
		if (caretIndex > 0) text.deleteCharAt(--caretIndex);
	}

	private void backspaceWord() {
		while (caretIndex > 0 && Character.isWhitespace(text.charAt(caretIndex - 1))) {
			text.deleteCharAt(--caretIndex);
		}
		while (caretIndex > 0 && !Character.isWhitespace(text.charAt(caretIndex - 1))) {
			text.deleteCharAt(--caretIndex);
		}
	}

	private void delete() {
		if (caretIndex < text.length()) text.deleteCharAt(caretIndex);
	}

	private void deleteWord() {
		while (caretIndex < text.length() && !Character.isWhitespace(text.charAt(caretIndex))) {
			text.deleteCharAt(caretIndex);
		}
		while (caretIndex < text.length() && Character.isWhitespace(text.charAt(caretIndex))) {
			text.deleteCharAt(caretIndex);
		}
	}

	private int updateRepeatingKey(int keyCode, int heldTicks, Runnable action) {
		if (!keyboard.isKeyDown(keyCode)) return 0;
		if (heldTicks == 0 || (heldTicks >= KEY_REPEAT_DELAY
				&& (heldTicks - KEY_REPEAT_DELAY) % KEY_REPEAT_INTERVAL == 0)) {
			action.run();
		}
		return heldTicks + 1;
	}
}
