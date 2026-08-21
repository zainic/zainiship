package com.zainic.zainiship.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteEffect {
    private String path;
	
	public final int SIZE;
	public int[] pixels;
    public int totalFrames;
	
	public static SpriteEffect playerExplosion = new SpriteEffect("/spritesheets/effect/enemyexplosion.png", 48);
	public static SpriteEffect defaultEnemyExplosion = new SpriteEffect("/spritesheets/effect/enemy2explosion.png", 48);
	public static SpriteEffect enemyExplosion1 = new SpriteEffect("/spritesheets/effect/enemyexplosion.png", 48);
    public static SpriteEffect enemyExplosion2 = new SpriteEffect("/spritesheets/effect/enemyexplosion.png", 48);
    public static SpriteEffect enemyExplosion3 = new SpriteEffect("/spritesheets/effect/enemyexplosion.png", 48);

	public SpriteEffect(String path, int size) {
		this.path = path;
		this.SIZE = size;
		load();
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteEffect.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
            totalFrames = h / SIZE;
            pixels = new int[SIZE*SIZE*totalFrames];
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

    public int getTotalStates() {
        return totalFrames;
    }
}
