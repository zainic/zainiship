package com.zainic.zainiship.entity.mob;

import com.zainic.zainiship.entity.Entity;
import com.zainic.zainiship.entity.projectile.Projectile;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;

public abstract class Mob extends Entity{
	
	protected Sprite sprite;
	protected double health, armor;
	protected double speed;
	protected int dir = 0;
	protected boolean moving = false;
	protected boolean friendly;
	
	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if (!borderCollision(xa, ya, screen)) {
			this.x = x + xa;
			this.y = y + ya;
		}

	}
	
	public void justMove(int xa, int ya) {
		this.x = x + xa;
		this.y = y + ya;
	}
	
	public void goTo(int posX, int posY) {
		this.x = posX;
		this.y = posY;
	}
	
	public void update() {
		
	}

	public void render() {

	}
	
	protected void shoot(int x, int y, double dir, Projectile p, boolean friendly) {
		p.initPos(x, y, dir);
		if (friendly) {
			level.addAlliesProjectile(p);
		}
		else {
			level.addEnemiesProjectile(p);
		}
	}
	
	private boolean borderCollision(int xa, int ya, Screen screen) {
		boolean solid = false;
		int xt = x + xa;
		int yt = y + ya;
		int left = 0 - this.hitboxAnchorX;
		int top = 0 - this.hitboxAnchorY;
		int right = screen.width - (this.hitboxAnchorX + this.hitboxSizeX) + 1;
		int bottom = screen.height - (this.hitboxAnchorY + this.hitboxSizeY) + 1;
		if (xt < left || xt >= right || yt < top || yt >= bottom) {
			solid = true;
		}
		return solid;
	}
	
	public void destroy() {
		
	}

}
