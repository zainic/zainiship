package com.zainic.zainiship.entity.projectile;

import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;

public class GammaProjectile extends Projectile{
	
	public static final int FIRE_RATE = 50;
	
	public GammaProjectile() {
		super();
		range = 500;
		speed = 2;
		damage = 10;
		
		sprite = Sprite.gamma_projectile;
		this.hitboxAnchorX = 3;
		this.hitboxAnchorY = 3;
		this.hitboxSizeX = 10;
		this.hitboxSizeY = 10;
	}
	
	public void initPos(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update() {
		move();
	}
	
	public void render(Screen screen) {
		screen.renderProjectile((int) x, (int) y, this);
	}
	
}

