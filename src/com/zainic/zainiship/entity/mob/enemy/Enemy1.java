package com.zainic.zainiship.entity.mob.enemy;

import com.zainic.zainiship.entity.projectile.AlphaProjectile;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.path.Path;

public class Enemy1 extends Enemy{
	
	private int path;
	
	public Enemy1(double spawnPointX, double spawnPointY, int path) {
		super(spawnPointX, spawnPointY);
		sprite = Sprite.enemy_ship32_1;
		this.hitboxAnchorX = 4;
		this.hitboxAnchorY = 0;
		this.hitboxSizeX = 24;
		this.hitboxSizeY = 25;
		this.path = path;
		this.mobSpeed = 0.5;
		this.mobDamage = 20;
		this.health = 100;
		this.armor = 0.0;
		this.fireRate = AlphaProjectile.FIRE_RATE;
	}
	
	public void update() {
		if (fireRate > 0) fireRate--;
		if (path == 0) move(Path.pathOneLeftX(t), Path.pathOneLeftY(t));
		if (path == 1) move(Path.pathOneRightX(t), Path.pathOneRightY(t));
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
			shoot((int) this.x + 8, (int) this.y + 16, Math.PI/2, new AlphaProjectile(), friendly);
			fireRate = AlphaProjectile.FIRE_RATE;
		}
	}
	
	public void render(Screen screen) {
		screen.renderEntity((int) this.x, (int) this.y, this);
	}

}
