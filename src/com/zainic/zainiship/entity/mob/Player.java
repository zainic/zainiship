package com.zainic.zainiship.entity.mob;

import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.entity.Entity;
import com.zainic.zainiship.entity.projectile.BulletProjectile;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.input.Keyboard;
import com.zainic.zainiship.input.Mouse;

public class Player extends Mob{
	
	private Keyboard input;
	private Sprite sprite;
	
	private int fireRate = 0;
//	private int currentMouseX = 0 , currentMouseY = 0;
	
	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.sprite = Sprite.player_ship32;
		this.speed = 2;
		this.health = 100;
		this.armor = 0;
		this.friendly = true;
		this.hitboxAnchorX = 4;
		this.hitboxAnchorY = 6;
		this.hitboxSizeX = 24;
		this.hitboxSizeY = 25;
		this.input = input;
		fireRate = BulletProjectile.FIRE_RATE;
	}
	
	public void update() {
		if (fireRate > 0) fireRate--;
		int xa = 0, ya = 0;
		if (input.up) ya -= speed;
		if (input.down) ya += speed;
		if (input.left) xa -= speed;
		if (input.right) xa += speed;
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			moving = true;
		}
		else {
			moving = false;
//			if (Mouse.isInsideScreen() && (currentMouseX != (int) Mouse.getX()/2 - 16 && currentMouseY != (int) Mouse.getY()/2 - 16)){
//				currentMouseX = (int) Mouse.getX()/2 - 16; 
//				currentMouseY = (int) Mouse.getY()/2 - 16;
//				goTo(currentMouseX, currentMouseY);
//			}
		}
		
		
		if (health <= 0) {
			destroy();
		}
		
		updateShooting();

	}
	
	
	private void updateShooting() {
		if ((input.space || Mouse.getB() == Mouse.LMB) && fireRate <= 0) {
			shoot(x + 8, y, -Math.PI/2, new BulletProjectile(), friendly);
			fireRate = BulletProjectile.FIRE_RATE;
		}
	}
	
	public void render(Screen screen) {
		screen.renderPlayer(x, y, sprite);
	}
	
	public boolean getHitStatusBy(Entity e) {
		return false;
	}
	
	public void destroy() {
		remove();
	}
	
}
