package com.zainic.zainiship.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zainic.zainiship.entity.mob.enemy.Enemy1;
import com.zainic.zainiship.entity.mob.enemy.Enemy2;
import com.zainic.zainiship.entity.mob.enemy.Enemy3;

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
		if (this.time > 100 && this.time <= 1000) {
			if ((this.time - 100) % 100 == 0) {
				addEnemiesMob(new Enemy1(this.width * 1/9, -32, 0));
				addEnemiesMob(new Enemy1(this.width * 8/9, -32, 1));
			}
		}
		if (this.time > 1300 && this.time <= 2050) {
			if ((this.time - 1000) % 150 == 0) {
				addEnemiesMob(new Enemy2(this.width * 1/11, -32, 0));
				addEnemiesMob(new Enemy2(this.width * 2/11, -32, 0));
				addEnemiesMob(new Enemy2(this.width * 3/11, -32, 0));
				addEnemiesMob(new Enemy2(this.width * 4/11, -32, 0));
				addEnemiesMob(new Enemy2(this.width * 5/11, -32, 0));
				addEnemiesMob(new Enemy2(this.width * 6/11, -32, 0));
				addEnemiesMob(new Enemy2(this.width * 7/11, -32, 0));
				addEnemiesMob(new Enemy2(this.width * 8/11, -32, 0));
				addEnemiesMob(new Enemy2(this.width * 9/11, -32, 0));
				addEnemiesMob(new Enemy2(this.width * 10/11, -32, 0));
			}
		}
		if (this.time > 2200 && this.time <= 3000) {
			if ((this.time - 2200) % 100 == 0) {
				addEnemiesMob(new Enemy3(this.width * 1/5, -32, 0));
				addEnemiesMob(new Enemy3(this.width * 2/5, -32, 1));
				addEnemiesMob(new Enemy3(this.width * 3/5, -32, 0));
				addEnemiesMob(new Enemy3(this.width * 4/5, -32, 1));
			}
		}
	}

}
