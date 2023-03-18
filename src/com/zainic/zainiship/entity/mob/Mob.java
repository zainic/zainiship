package com.zainic.zainiship.entity.mob;

import com.zainic.zainiship.entity.Entity;
import com.zainic.zainiship.graphics.Sprite;

public abstract class Mob extends Entity{
	
	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
	
	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		setX(getX() + xa);
		setY(getY() + ya);

	}
	
	public void update() {
		
	}

	public void render() {

	}

}
