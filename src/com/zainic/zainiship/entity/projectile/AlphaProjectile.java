package com.zainic.zainiship.entity.projectile;

import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;

public class AlphaProjectile extends Projectile{
	
	public static final int FIRE_RATE = 30;
	
	public AlphaProjectile() {
		super();
		range = 300;
		speed = 2;
		damage = 10;
		
		sprite = Sprite.alpha_projectile;
		this.hitboxAnchorX = 5;
		this.hitboxAnchorY = 3;
		this.hitboxSizeX = 6;
		this.hitboxSizeY = 10;
	}
	
	public void initPos(int x, int y, double dir) {
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
