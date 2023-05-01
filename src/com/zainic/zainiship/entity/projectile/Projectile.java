package com.zainic.zainiship.entity.projectile;

import com.zainic.zainiship.entity.Entity;
import com.zainic.zainiship.graphics.Sprite;

public abstract class Projectile extends Entity {
	
	protected int xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nx, ny;
	protected double distance;
	protected double speed, range, damage;
	
	public Projectile() {
		this.xOrigin = 0;
		this.yOrigin = 0;
	}
	
	public void initPos(int x, int y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
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
		
	}
	

}
