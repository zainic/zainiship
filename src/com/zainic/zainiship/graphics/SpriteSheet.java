package com.zainic.zainiship.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	
	public final int SIZE;
	public int[] pixels;
	
	public static SpriteSheet player = new SpriteSheet("/spritesheets/player/spriteships.png", 256);
	public static SpriteSheet player32 = new SpriteSheet("/spritesheets/player/spriteships32.png", 128);
	public static SpriteSheet enemyship = new SpriteSheet("/spritesheets/enemies/ship/enemyshipsheet.png", 256);
	public static SpriteSheet enemyship32 = new SpriteSheet("/spritesheets/enemies/ship/enemyshipsheet32.png", 128);
	
	public static SpriteSheet projectilesheet = new SpriteSheet("/spritesheets/projectiles/projectilesheet.png", 64);

	
	public SpriteSheet(String path, int size) {
		this.path = path;
		this.SIZE = size;
		pixels = new int[SIZE*SIZE];
		load();
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

