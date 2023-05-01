package com.zainic.zainiship.entity.mob;

import com.zainic.zainiship.entity.Entity;
import com.zainic.zainiship.entity.projectile.Projectile;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;

public abstract class Mob extends Entity{
	
	protected Sprite sprite;
	protected double health, armor;
	protected double mobSpeed, mobDamage;
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
	
	public double getMobSpeed() {
		return mobSpeed;
	}
	
	public double getMobDamage() {
		return mobDamage;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getArmor() {
		return armor;
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
	
	public boolean getHitStatusBy(Entity e, boolean allies) {
		boolean hitX, hitY;
		if (friendly ^ allies) {
			if (x < e.getX()){
				if (x + hitboxAnchorX + hitboxSizeX - e.getX() - e.getHitboxAnchorX() > 0) {
					hitX = true;
				}
				else {
					hitX = false;
				}
			}
			else {
				if (e.getX() + e.getHitboxAnchorX() + e.getHitboxSizeX() - x - hitboxAnchorX > 0) {
					hitX = true;
				}
				else {
					hitX = false;
				}
			}
			if (y < e.getY()){
				if (y + hitboxAnchorY + hitboxSizeY - e.getY() - e.getHitboxAnchorY() > 0) {
					hitY = true;
				}
				else {
					hitY = false;
				}
			}
			else {
				if (e.getY() + e.getHitboxAnchorY() + e.getHitboxSizeY() - y - hitboxAnchorY > 0) {
					hitY = true;
				}
				else {
					hitY = false;
				}
			}
			return hitX && hitY;
		}
		else {
			return false;
		}
		
	} 
	
	public void checkHit() {
		for (int i = 0; i < level.getEnemiesProjectiles().size(); i++) {
			Projectile p = level.getEnemiesProjectiles().get(i);
			if (getHitStatusBy(p, false)) {
				health -= p.getDamage();
			}
		}
		for (int i = 0; i < level.getAlliesProjectiles().size(); i++) {
			Projectile p = level.getAlliesProjectiles().get(i);
			if (getHitStatusBy(p, true)) {
				health -= p.getDamage();
			}
		}
		for (int i = 0; i < level.getEnemiesMob().size(); i++) {
			Mob e = level.getEnemiesMob().get(i);
			if (getHitStatusBy(e, false)) {
				health -= e.getMobDamage();
			}
		}
		for (int i = 0; i < level.getAlliesMob().size(); i++) {
			Mob e = level.getAlliesMob().get(i);
			if (getHitStatusBy(e, true)) {
				health -= e.getMobDamage();
			}
		}
	}
	
	public void destroy() {
		remove();
	}

}
