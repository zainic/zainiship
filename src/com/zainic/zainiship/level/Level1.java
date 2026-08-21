package com.zainic.zainiship.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zainic.zainiship.entity.mob.enemy.Enemy1;
import com.zainic.zainiship.entity.mob.enemy.Enemy2;
import com.zainic.zainiship.entity.mob.enemy.Enemy3;
import com.zainic.zainiship.entity.mob.enemy.Enemy4;

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
				addEnemiesMob(new Enemy1(screen.getWidth() * 1/9, -32, 0));
				addEnemiesMob(new Enemy1(screen.getWidth() * 8/9, -32, 1));
			}
		}
		if (this.time > 1300 && this.time <= 2050) {
			if ((this.time - 1000) % 150 == 0) {
				addEnemiesMob(new Enemy2(screen.getWidth() * 1/11, -32, 0));
				addEnemiesMob(new Enemy2(screen.getWidth() * 2/11, -32, 0));
				addEnemiesMob(new Enemy2(screen.getWidth() * 3/11, -32, 0));
				addEnemiesMob(new Enemy2(screen.getWidth() * 4/11, -32, 0));
				addEnemiesMob(new Enemy2(screen.getWidth() * 5/11, -32, 0));
				addEnemiesMob(new Enemy2(screen.getWidth() * 6/11, -32, 0));
				addEnemiesMob(new Enemy2(screen.getWidth() * 7/11, -32, 0));
				addEnemiesMob(new Enemy2(screen.getWidth() * 8/11, -32, 0));
				addEnemiesMob(new Enemy2(screen.getWidth() * 9/11, -32, 0));
				addEnemiesMob(new Enemy2(screen.getWidth() * 10/11, -32, 0));
			}
		}
		if (this.time > 2200 && this.time <= 3000) {
			if ((this.time - 2200) % 100 == 0) {
				addEnemiesMob(new Enemy3(screen.getWidth() * 1/5, -32, 0));
				addEnemiesMob(new Enemy3(screen.getWidth() * 2/5, -32, 1));
				addEnemiesMob(new Enemy3(screen.getWidth() * 3/5, -32, 0));
				addEnemiesMob(new Enemy3(screen.getWidth() * 4/5, -32, 1));
			}
		}
		if (this.time > 3200 && this.time <= 5000) {
			if ((this.time - 3200) % 100 == 0) {
				for (int i = 0; i < 2; i++) {
					int rand1 = (int) (Math.random() * 3);
					int rand2;
					switch (rand1) {
						case 0:
							rand2 = (int) (Math.random() * 7) + 1;
							addEnemiesMob(new Enemy4(screen.getWidth() * rand2/8, -32, 0));
							System.out.println("Enemy4 added at time: " + this.time + " with rand1: " + rand1 + " and rand2: " + rand2);
							break;
						case 1:
							rand2 = (int) (Math.random() * 4) + 1;
							addEnemiesMob(new Enemy4(-32, screen.getHeight() * rand2/5, 0));
							System.out.println("Enemy4 added at time: " + this.time + " with rand1: " + rand1 + " and rand2: " + rand2);
							break;
						case 2:
							rand2 = (int) (Math.random() * 4) + 1;
							addEnemiesMob(new Enemy4(screen.getWidth() + 32, screen.getHeight() * rand2/5,0));
							System.out.println("Enemy4 added at time: " + this.time + " with rand1: " + rand1 + " and rand2: " + rand2);
							break;
						default:
							break;	
					}
				}
			}
		}
		if (this.time >= 5500) {
			this.time = 0;
		}
	}

}
