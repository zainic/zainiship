package com.zainic.zainiship.entity.projectile;

import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;

public class DeltaProjectile extends Projectile{
	
	public static final int FIRE_RATE = 300;
	
	public DeltaProjectile() {
		super();
		range = 1000;
		speed = 4;
		damage = 15;
		
		this.name = "DeltaProjectile";
		sprite = Sprite.delta_projectile;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		this.hitboxAnchorX = 1;
		this.hitboxAnchorY = 1;
		this.hitboxSizeX = 14;
		this.hitboxSizeY = 14;
	}
	
	public void initPos(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update() {
		move();
	}
	
	public void render(Screen screen) {
		screen.renderProjectile((int) x, (int) y, this);
	}
	
}

