package com.zainic.zainiship.graphics;

import com.zainic.zainiship.graphics.SpriteSheet;

public class Sprite {
	
	private int x, y;
	private SpriteSheet sheet;
	private int width, height;
	public final int SIZE;
	public int[] pixels;
	
	// Player sprites
	public static Sprite player_ship = new Sprite(64, 0, 0, SpriteSheet.player);
	public static Sprite player_ship32 = new Sprite(32, 0, 0, SpriteSheet.player32);
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.SIZE = size;
		this.width = size;
		this.height = size;
		this.pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
		setColor(color);
	}
	
	public Sprite(int size, int color) {
		this.SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	private void setColor(int color) {
		for (int i = 0; i < this.width * this.height; i++) {
			pixels[i] = color;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}

}
