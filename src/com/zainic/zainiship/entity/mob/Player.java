package com.zainic.zainiship.entity.mob;

import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.graphics.effect.ExplosionEffect;
import com.zainic.zainiship.entity.projectile.BulletProjectile;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.input.Keyboard;
import com.zainic.zainiship.input.Mouse;
import com.zainic.zainiship.audio.Audio;

public class Player extends Mob{
	
	private Keyboard input;
	private Sprite sprite;
	
	private int fireRate = 0;
//	private int currentMouseX = 0 , currentMouseY = 0;
	
	public Player(int x, int y, Keyboard input) {
		this.name = "Player";
		this.sprite = Sprite.player_ship32;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		this.x = x - (this.width / 2);
		this.y = y - (this.height / 2);
		this.mobSpeed = 4;
		this.mobDamage = 100;
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
		if (input.up) ya -= mobSpeed;
		if (input.down) ya += mobSpeed;
		if (input.left) xa -= mobSpeed;
		if (input.right) xa += mobSpeed;
		
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
		
		checkHit();
		checkHealth();
		updateShooting();

	}
	
	
	private void updateShooting() {
		if ((input.space || Mouse.getB() == Mouse.LMB) && fireRate <= 0) {
			shoot((int) x + (this.width / 4), (int) y, -Math.PI/2, new BulletProjectile(), friendly);
			// play shooting sound (loaded in Audio.init())
			if (Audio.PLAYER_SHOOT != null) Audio.PLAYER_SHOOT.play();
			fireRate = BulletProjectile.FIRE_RATE;
		}
	}
	
	public void render(Screen screen) {
		screen.renderPlayer((int) x, (int) y, sprite);
	}
	
	public void destroy() {
		level.addEffect(new ExplosionEffect(this.x + this.width / 2, this.y + this.height / 2));
		if (Audio.PLAYER_EXPLOSION != null) Audio.PLAYER_EXPLOSION.play();
		remove();
	}
	
}
