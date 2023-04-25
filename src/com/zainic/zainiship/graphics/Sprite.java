package com.zainic.zainiship.graphics;

public class Sprite {
	
	private int x, y;
	private SpriteSheet sheet;
	private int width, height;
	public final int SIZE;
	public int[] pixels;
	
	// Player sprites
	public static Sprite player_ship = new Sprite(64, 0, 0, SpriteSheet.player);
	public static Sprite player_ship32 = new Sprite(32, 0, 0, SpriteSheet.player32);
	
	// Enemy sprites
	public static Sprite enemy_ship_1 = new Sprite(64, 0, 0, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_1 = new Sprite(32, 0, 0, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_2 = new Sprite(64, 0, 1, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_2 = new Sprite(32, 0, 1, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_3 = new Sprite(64, 0, 2, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_3 = new Sprite(32, 0, 2, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_4 = new Sprite(64, 0, 3, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_5 = new Sprite(32, 0, 3, SpriteSheet.enemyship32);
	
	//Projectile Sprites
	public static Sprite bullet_projectile = new Sprite(16, 0, 0, SpriteSheet.projectilesheet);
	public static Sprite alpha_projectile = new Sprite(16, 1, 0, SpriteSheet.projectilesheet);
	
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
