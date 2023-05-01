package com.zainic.zainiship.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zainic.zainiship.entity.mob.enemy.Enemy1;

public class Level1 extends Level {

	public Level1(String path) {
		super(path);
	}

	@Override
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(Level1.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			this.width = w;
			this.height = h;
			background = new int[w * h];
			image.getRGB(0, 0, w, h, background, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not load the level");
		}
	}
	
	@Override
	protected void generateLevel() {
		
	}
	
	public void addTimeEvent() {
		if (this.time > 100 && this.time < 1000) {
			if ((this.time - 100) % 50 == 0) {
				addEnemiesMob(new Enemy1(100 - 16, -32, 0));
				addEnemiesMob(new Enemy1(500 - 16, -32, 1));
			}
		}
	}

}
