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
		int left = 0 - this.hitboxAnchorX;
		int top = 0 - this.hitboxAnchorY;
		int right = screen.width - (this.hitboxAnchorX + this.hitboxSizeX) + 1;
		int bottom = screen.height - (this.hitboxAnchorY + this.hitboxSizeY) + 1;
		if (xt < left || xt >= right || yt < top || yt >= bottom) {
			solid = true;
		}
		return solid;
	}

}