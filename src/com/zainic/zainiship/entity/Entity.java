package com.zainic.zainiship.entity;

import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.level.Level;

public abstract class Entity {
	
	private int x, y;
	private boolean removed = false;
	protected Level level;
	protected Screen screen;
	
	public void init(Level level, Screen screen) {
		this.level = level;
		this.screen = screen;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
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
