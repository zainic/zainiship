package com.zainic.zainiship.entity.projectile;

import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;

public class BulletProjectile extends Projectile {
	
	public static final int FIRE_RATE = 15;
	
	public BulletProjectile() {
		super();
		range = 200;
		speed = 3;
		damage = 20;
		
		sprite = Sprite.bullet_projectile;
		this.hitboxAnchorX = 4;
		this.hitboxAnchorY = 1;
		this.hitboxSizeX = 8;
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
	
	protected void move() {
		x += nx;
		y += ny;
		if (distance() > range) remove();
	}
	
	private double distance() {
		double dist = 0;
		dist = Math.sqrt(((xOrigin - x) * (xOrigin - x)) + ((yOrigin - y) * (yOrigin - y)));
		return dist;
	}

}

