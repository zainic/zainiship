package com.zainic.zainiship.entity;

import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.level.Level;

public abstract class Entity {
	
	private boolean removed = false;
	protected int x, y;
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
