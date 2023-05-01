package com.zainic.zainiship.entity.mob;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		int xt = (int) x + xa;
		int yt = (int) y + ya;
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
//			Set<Integer> itsX = new HashSet<Integer>();
//			Set<Integer> itsY = new HashSet<Integer>();
//			Set<Integer> thatsX = new HashSet<Integer>();
//			Set<Integer> thatsY = new HashSet<Integer>();
//			itsX.addAll(IntStream.rangeClosed((int) x + hitboxAnchorX, (int) x + hitboxAnchorX + hitboxSizeX).boxed().collect(Collectors.toList()));
//			itsY.addAll(IntStream.rangeClosed((int) y + hitboxAnchorY, (int) y + hitboxAnchorY + hitboxSizeY).boxed().collect(Collectors.toList()));
//			thatsX.addAll(IntStream.rangeClosed((int) e.getX() + e.getHitboxAnchorX(), (int) e.getX() + e.getHitboxAnchorX() + e.getHitboxSizeX()).boxed().collect(Collectors.toList()));
//			thatsY.addAll(IntStream.rangeClosed((int) e.getY() + e.getHitboxAnchorY(), (int) e.getY() + e.getHitboxAnchorY() + e.getHitboxSizeY()).boxed().collect(Collectors.toList()));
//			Set<Integer> xIntersection = new HashSet<Integer>(itsX);
//			Set<Integer> yIntersection = new HashSet<Integer>(itsY);
//			xIntersection.retainAll(thatsX);
//			yIntersection.retainAll(thatsY);
//			hitX = !xIntersection.isEmpty();
//			hitY = !yIntersection.isEmpty();
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
				p.remove();
			}
		}
		for (int i = 0; i < level.getAlliesProjectiles().size(); i++) {
			Projectile p = level.getAlliesProjectiles().get(i);
			if (getHitStatusBy(p, true)) {
				health -= p.getDamage();
				p.remove();
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
