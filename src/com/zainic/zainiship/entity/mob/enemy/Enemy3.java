package com.zainic.zainiship.entity.mob.enemy;

import com.zainic.zainiship.entity.projectile.GammaProjectile;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.path.Path;

public class Enemy3 extends Enemy{
	
	private int path;
	
	public Enemy3(double spawnPointX, double spawnPointY, int path) {
		super(spawnPointX, spawnPointY);
		sprite = Sprite.enemy_ship32_3;
		this.hitboxAnchorX = 0;
		this.hitboxAnchorY = 0;
		this.hitboxSizeX = 32;
		this.hitboxSizeY = 26;
		this.path = path;
		this.mobSpeed = 1;
		this.mobDamage = 30;
		this.health = 80;
		this.armor = 0.0;
		this.fireRate = GammaProjectile.FIRE_RATE;
	}
	
	public void update() {
		if (fireRate > 0) fireRate--;
		if (path == 0) move(Path.pathThreeLeftX(t), Path.pathThreeLeftY(t));
		if (path == 1) move(Path.pathThreeRightX(t), Path.pathThreeRightY(t));
		t += mobSpeed;
		checkHit();
		if (health <= 0) {
			remove();
		}
		if (isOutsideScreen() && t > 50) remove();
		updateShooting();
	}
	
	private void updateShooting() {
		if (fireRate <= 0) {
			double angleTarget;
			if (level.getAlliesMob().size() > 0) {
				double xTarget = (level.getAlliesMob().get(level.getAlliesMob().size() - 1).getX() - 16) - (this.x - 16);
				double yTarget = (level.getAlliesMob().get(level.getAlliesMob().size() - 1).getY() - 16) - (this.y - 16);
				angleTarget = Math.atan2(yTarget, xTarget);
			}
			else {
				angleTarget = Math.PI/2;
			}
			shoot((int) this.x + 8, (int) this.y + 16, angleTarget, new GammaProjectile(), friendly);
			fireRate = GammaProjectile.FIRE_RATE;
		}
	}
	
	public void render(Screen screen) {
		screen.renderEntity((int) this.x, (int) this.y, this);
	}

}
