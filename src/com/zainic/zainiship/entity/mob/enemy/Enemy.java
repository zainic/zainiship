package com.zainic.zainiship.entity.mob.enemy;

import com.zainic.zainiship.audio.Audio;
import com.zainic.zainiship.entity.mob.Mob;
import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.graphics.effect.ExplosionEffect;

public abstract class Enemy extends Mob{
	
	protected int fireRate;
	protected double t;
	
	public Enemy(double spawnPointX, double spawnPointY){
		goTo(spawnPointX, spawnPointY);
		this.friendly = false;
	}
	
	public void goTo(double posX, double posY) {
		this.x = posX;
		this.y = posY;
	}
	
	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		this.x = x + mobSpeed*xa;
		this.y = y + mobSpeed*ya;
	}

	public void update() {
		
	}
	
	public void render() {
		
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	public void destroy() {
		level.addEffect(new ExplosionEffect(this.x + this.width / 2, this.y + this.height / 2));
		if (Audio.ENEMY_EXPLOSION != null) Audio.ENEMY_EXPLOSION.play();
		remove();
	}
}
