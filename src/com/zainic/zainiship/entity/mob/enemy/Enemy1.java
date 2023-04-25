package com.zainic.zainiship.entity.mob.enemy;

import com.zainic.zainiship.entity.projectile.AlphaProjectile;
import com.zainic.zainiship.entity.projectile.Projectile;
import com.zainic.zainiship.graphics.Sprite;

public class Enemy1 extends Enemy{
	
	public Enemy1(int spawnPointX, int spawnPointY) {
		super(spawnPointX, spawnPointY);
		sprite = Sprite.enemy_ship32_1;
		this.hitboxAnchorX = 4;
		this.hitboxAnchorY = 0;
		this.hitboxSizeX = 24;
		this.hitboxSizeY = 25;
		this.health = 100;
		this.armor = 0;
		this.fireRate = AlphaProjectile.FIRE_RATE;
	}
	
	public void update() {
		move(0, 1);
		updateShooting();
	}
	
	private void updateShooting() {
		if (fireRate <= 0) {
			shoot(getX() + 8, getY(), Math.PI/2, new AlphaProjectile());
			fireRate = AlphaProjectile.FIRE_RATE;
		}
	}

}
