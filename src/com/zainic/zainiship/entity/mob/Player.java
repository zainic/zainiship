package com.zainic.zainiship.entity.mob;

import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.entity.projectile.BulletProjectile;
import com.zainic.zainiship.entity.projectile.Projectile;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.input.Keyboard;

public class Player extends Mob{
	
	private Keyboard input;
	private Sprite sprite;
	
	private int fireRate = 0;
	
	public Player(int x, int y, Keyboard input) {
		setX(x);
		setY(y);
		sprite = Sprite.player_ship32;
		this.hitboxAnchorX = 4;
		this.hitboxAnchorY = 6;
		this.hitboxSizeX = 24;
		this.hitboxSizeY = 25;
		this.input = input;
		fireRate = BulletProjectile.FIRE_RATE;
	}
	
	public void update() {
		if (fireRate > 0) fireRate--;
		int xa = 0, ya = 0;
		if (input.up) ya-=2;
		if (input.down) ya+=2;
		if (input.left) xa-=2;
		if (input.right) xa+=2;
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			moving = true;
		}
		else {
			moving = false;
		}
		
		clear();
		updateShooting();

	}
	
	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
		
	}
	
	private void updateShooting() {
		if (input.space && fireRate <= 0) {
			shoot(getX() + 8, getY(), -Math.PI/2);
			fireRate = BulletProjectile.FIRE_RATE;
		}
	}
	
	public void render(Screen screen) {
		screen.renderPlayer(getX(), getY(), sprite);
	}

}
