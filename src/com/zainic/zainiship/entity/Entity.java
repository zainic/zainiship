package com.zainic.zainiship.entity;

import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.level.Level;

public abstract class Entity {
	
	private int x, y;
	private boolean removed = false;
	protected Level level;
	
	public void init(Level level) {
		this.level = level;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
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
