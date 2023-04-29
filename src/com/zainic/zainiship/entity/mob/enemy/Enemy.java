package com.zainic.zainiship.entity.mob.enemy;

import com.zainic.zainiship.entity.mob.Mob;
import com.zainic.zainiship.graphics.Sprite;

public abstract class Enemy extends Mob{
	
	protected int health, armor, fireRate;
	protected int t;
	
	public Enemy(int spawnPointX, int spawnPointY){
		goTo(spawnPointX, spawnPointY);
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
		
	}
}
