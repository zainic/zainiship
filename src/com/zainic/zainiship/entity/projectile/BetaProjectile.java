package com.zainic.zainiship.entity.projectile;

import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;

public class BetaProjectile extends Projectile{
	
	public static final int FIRE_RATE = 50;
	
	public BetaProjectile() {
		super();
		range = 500;
		speed = 4;
		damage = 20;
		
		sprite = Sprite.beta_projectile;
		this.hitboxAnchorX = 5;
		this.hitboxAnchorY = 1;
		this.hitboxSizeX = 6;
		this.hitboxSizeY = 13;
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

