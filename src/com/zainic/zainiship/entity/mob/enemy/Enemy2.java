package com.zainic.zainiship.entity.mob.enemy;

import com.zainic.zainiship.audio.Audio;
import com.zainic.zainiship.entity.projectile.BetaProjectile;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.path.Path;

public class Enemy2 extends Enemy{
	
	private int path;
	
	public Enemy2(double spawnPointX, double spawnPointY, int path) {
		super(spawnPointX, spawnPointY);
		this.name = "Enemy2";
		sprite = Sprite.enemy_ship32_2;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		justMove(-this.width / 2, -this.height / 2);
		this.hitboxAnchorX = 6;
		this.hitboxAnchorY = 0;
		this.hitboxSizeX = 20;
		this.hitboxSizeY = 31;
		this.path = path;
		this.mobSpeed = 1.5;
		this.mobDamage = 30;
		this.health = 50;
		this.armor = 0.0;
		this.fireRate = BetaProjectile.FIRE_RATE;
	}
	
	public void update() {
		if (fireRate > 0) fireRate--;
		if (path == 0) move(Path.pathTwoX(t), Path.pathTwoY(t));
		t += mobSpeed;
		checkHit();
		checkHealth();
		if (isOutsideScreen() && t > 50) remove();
		updateShooting();
	}
	
	private void updateShooting() {
		if (fireRate <= 0) {
			shoot((int) this.x + (this.width / 4), (int) this.y + (this.height / 2), Math.PI/2, new BetaProjectile(), friendly);
			if (Audio.SHOOT != null) Audio.SHOOT.play();
			fireRate = BetaProjectile.FIRE_RATE;
		}
	}
	
	public void render(Screen screen) {
		screen.renderEntity((int) this.x, (int) this.y, this);
	}

}
