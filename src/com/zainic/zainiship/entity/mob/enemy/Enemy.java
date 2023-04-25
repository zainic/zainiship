package com.zainic.zainiship.entity.mob.enemy;

import com.zainic.zainiship.entity.mob.Mob;

public abstract class Enemy extends Mob{
	
	protected int health, armor, fireRate;
	
	public Enemy(int spawnPointX, int spawnPointY){
		goTo(spawnPointX, spawnPointY);
	}
	
	public void update() {
		
	}
	
	public void render() {
		
	}
}
