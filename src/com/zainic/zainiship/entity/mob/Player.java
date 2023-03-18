package com.zainic.zainiship.entity.mob;

import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.input.Keyboard;

public class Player extends Mob{
	
	private Keyboard input;
	private Sprite sprite;
	
	public Player(int x, int y, Keyboard input) {
		setX(x);
		setY(y);
		this.input = input;
	}
	
	public void update() {
		int xa = 0, ya = 0;
		if (input.up) ya-=2;
		if (input.down) ya+=2;
		if (input.left) xa-=2;
		if (input.right) xa+=2;
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			moving = true;
		}
		else {
			moving = false;
		}

	}
	
	public void render(Screen screen) {
		sprite = Sprite.player_ship;
		screen.renderPlayer(getX(), getY(), sprite);
	}

}
