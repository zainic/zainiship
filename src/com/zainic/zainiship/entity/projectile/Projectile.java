package com.zainic.zainiship.entity.projectile;

import com.zainic.zainiship.entity.Entity;
import com.zainic.zainiship.graphics.Sprite;

public abstract class Projectile extends Entity {
	
	protected double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double nx, ny;
	protected double distance;
	protected double speed, range, damage;
	
	public Projectile() {
		this.xOrigin = 0;
		this.yOrigin = 0;
	}
	
	public void initPos(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public double getRange() {
		return range;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	protected void move() {
		x += nx;
		y += ny;
		if (distance() > range || isOutsideScreen()) remove();
	}
	
	private double distance() {
		double dist = 0;
		dist = Math.sqrt(((xOrigin - x) * (xOrigin - x)) + ((yOrigin - y) * (yOrigin - y)));
		return dist;
	}
	
}
