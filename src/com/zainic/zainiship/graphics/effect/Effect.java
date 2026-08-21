package com.zainic.zainiship.graphics.effect;

import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.level.Level;

public abstract class Effect {
	
	private boolean removed = false;
	protected Sprite sprite, totalSprite;
	protected String name;
	protected int width, height;
	protected double x, y;
	protected Level level;
	protected Screen screen;

	protected double time;
	protected double life;
	protected double state;
	protected int totalStates;

	public Effect(double x, double y) {
		goTo((int) x, (int) y);
	}
	
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

	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}

	public void justMove(int xa, int ya) {
		this.x = x + xa;
		this.y = y + ya;
	}
	
	public void goTo(int posX, int posY) {
		this.x = posX;
		this.y = posY;
	}

}
