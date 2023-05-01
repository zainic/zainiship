package com.zainic.zainiship.entity;

import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.level.Level;

public abstract class Entity {
	
	private boolean removed = false;
	protected double x, y;
	protected Level level;
	protected Screen screen;
	protected int hitboxAnchorX, hitboxAnchorY;
	protected int hitboxSizeX, hitboxSizeY;
	
	public void init(Level level, Screen screen) {
		this.level = level;
		this.screen = screen;
	}
	
	public void init(Level level) {
		this.level = level;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public int getHitboxAnchorX() {
		return this.hitboxAnchorX;
	}
	
	public int getHitboxAnchorY() {
		return this.hitboxAnchorY;
	}
	
	public int getHitboxSizeX() {
		return this.hitboxSizeX;
	}
	
	public int getHitboxSizeY() {
		return this.hitboxSizeY;
	}
	
	public void setX(int posX) {
		this.x = posX;
	}
	
	public void setY(int posY) {
		this.y = posY;
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen) {
		
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}

}
