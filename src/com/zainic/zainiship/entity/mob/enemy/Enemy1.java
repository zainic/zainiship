package com.zainic.zainiship.entity.mob.enemy;

import com.zainic.zainiship.audio.Audio;
import com.zainic.zainiship.entity.projectile.AlphaProjectile;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.path.Path;

public class Enemy1 extends Enemy{
	
	private int path;
	
	public Enemy1(double spawnPointX, double spawnPointY, int path) {
		super(spawnPointX, spawnPointY);
		this.name = "Enemy1";
		sprite = Sprite.enemy_ship32_1;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		justMove(-this.width / 2, -this.height / 2);
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
		checkHealth();
		if (isOutsideScreen() && t > 50) remove();
		updateShooting();
	}
	
	private void updateShooting() {
		if (fireRate <= 0) {
			shoot((int) this.x + (this.width / 4), (int) this.y + (this.height / 2), Math.PI/2, new AlphaProjectile(), friendly);
			if (Audio.ENEMY_SHOOT != null) Audio.ENEMY_SHOOT.play();
			fireRate = AlphaProjectile.FIRE_RATE;
		}
	}
	
	public void render(Screen screen) {
		screen.renderEntity((int) this.x, (int) this.y, this);
	}

}
