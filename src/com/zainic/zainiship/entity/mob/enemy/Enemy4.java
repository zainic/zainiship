package com.zainic.zainiship.entity.mob.enemy;

import com.zainic.zainiship.audio.Audio;
import com.zainic.zainiship.entity.projectile.DeltaProjectile;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.path.Path;

public class Enemy4 extends Enemy{
	
	private int path;
	private double savedAngle;
	
	public Enemy4(double spawnPointX, double spawnPointY, int path) {
		super(spawnPointX, spawnPointY);
		this.name = "Enemy4";
		sprite = Sprite.enemy_ship32_4;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		justMove(-this.width / 2, -this.height / 2);
		this.hitboxAnchorX = 2;
		this.hitboxAnchorY = 0;
		this.hitboxSizeX = 28;
		this.hitboxSizeY = 30;
		this.path = path;
		this.mobSpeed = 5;
		this.mobDamage = 20;
		this.health = 80;
		this.armor = 0.0;
		this.fireRate = DeltaProjectile.FIRE_RATE;
	}
	
	public void update() {
		if (fireRate > 0) fireRate--;
		double angleTarget;
		if (level.getAlliesMob().size() > 0) {
			if (t <= 350) {
				double xTarget = (level.getAlliesMob().get(level.getAlliesMob().size() - 1).getX() - (this.width / 2)) - (this.x - (this.width / 2));
				double yTarget = (level.getAlliesMob().get(level.getAlliesMob().size() - 1).getY() - (this.height / 2)) - (this.y - (this.height / 2));
				angleTarget = Math.atan2(yTarget, xTarget);
				savedAngle = angleTarget;
			}
			else {
				angleTarget = savedAngle;
				this.mobSpeed = 2;
			}
		}
		else {
			angleTarget = Math.PI/2;
		}
		if (path == 0) move(Path.pathFourX(t, angleTarget), Path.pathFourY(t, angleTarget));
		t += mobSpeed;
		checkHit();
		checkHealth();
		if (isOutsideScreen() && t > 200) remove();
		updateShooting();
	}
	
	private void updateShooting() {
		if (fireRate <= 0) {
			double angleTarget;
			if (level.getAlliesMob().size() > 0) {
				double xTarget = (level.getAlliesMob().get(level.getAlliesMob().size() - 1).getX() - (this.width / 2)) - (this.x - (this.width / 2));
				double yTarget = (level.getAlliesMob().get(level.getAlliesMob().size() - 1).getY() - (this.height / 2)) - (this.y - (this.height / 2));
				angleTarget = Math.atan2(yTarget, xTarget);
			}
			else {
				angleTarget = Math.PI/2;
			}
			shoot((int) this.x + (this.width / 4), (int) this.y + (this.height / 2), angleTarget, new DeltaProjectile(), friendly);
			if (Audio.ENEMY_SHOOT != null) Audio.ENEMY_SHOOT.play();
			fireRate = DeltaProjectile.FIRE_RATE;
		}
	}
	
	public void render(Screen screen) {
		screen.renderEntity((int) this.x, (int) this.y, this);
	}

}
