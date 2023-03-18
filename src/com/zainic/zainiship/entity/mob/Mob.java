package com.zainic.zainiship.entity.mob;

import com.zainic.zainiship.entity.Entity;
import com.zainic.zainiship.graphics.Screen;
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
		
		if (!borderCollision(xa, ya, screen)) {
			setX(getX() + xa);
			setY(getY() + ya);
		}

	}
	
	public void update() {
		
	}

	public void render() {

	}
	
	private boolean borderCollision(int xa, int ya, Screen screen) {
		boolean solid = false;
		int xt = getX() + xa;
		int yt = getY() + ya;
		if (xt < 0 || xt >= screen.width - 64 || yt < 0 || yt >= screen.height - 64) {
			solid = true;
		}
		return solid;
	}

}
