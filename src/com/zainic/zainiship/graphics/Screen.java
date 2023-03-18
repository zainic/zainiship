package com.zainic.zainiship.graphics;

import com.zainic.zainiship.level.Level;

public class Screen {
	
	public int width, height;
	public int[] pixels;
	public int xOffset, yOffset;
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x000000;
		}
	}

	public void renderBackground(int xp, int yp, Level level) {
		for (int y = 0; y < level.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < level.getWidth(); x++) {
				int xa = x + xp;
				if (x < 0 || x >= this.width || y < 0 || y >= this.height) break;
				if (xa < 0 || xa >= level.getWidth()) {
					xa %= level.getWidth();
				}
				if (ya < 0 || ya >= level.getHeight()) {
					ya %= level.getHeight();
				}
				pixels[x + y * width] = level.getBackground()[xa + ya * level.getWidth()];
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;		
	}
}
