package com.zainic.zainiship.entity.mob.enemy;

import com.zainic.zainiship.entity.projectile.AlphaProjectile;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.path.Path;

public class Enemy1 extends Enemy{
	
	private int path;
	
	public Enemy1(int spawnPointX, int spawnPointY, int path) {
		super(spawnPointX, spawnPointY);
		sprite = Sprite.enemy_ship32_1;
		this.hitboxAnchorX = 4;
		this.hitboxAnchorY = 0;
		this.hitboxSizeX = 24;
		this.hitboxSizeY = 25;
		this.path = path;
		this.health = 100;
		this.armor = 0;
		this.fireRate = AlphaProjectile.FIRE_RATE;
	}
	
	public void update() {
		if (fireRate > 0) fireRate--;
		if (path == 0) justMove(Path.pathOneLeftX(t), Path.pathOneLeftY(t));
		if (path == 1) justMove(Path.pathOneRightX(t), Path.pathOneRightY(t));
		t++;
		if (t > 200) remove();
		updateShooting();
	}
	
	private void updateShooting() {
		if (fireRate <= 0) {
			shoot(getX() + 8, getY(), Math.PI/2, new AlphaProjectile());
			fireRate = AlphaProjectile.FIRE_RATE;
		}
	}
	
	public void render(Screen screen) {
		screen.renderEntity(getX(), getY(), this);
	}

}
