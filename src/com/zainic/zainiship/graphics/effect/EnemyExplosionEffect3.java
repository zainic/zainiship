package com.zainic.zainiship.graphics.effect;

import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.graphics.Sprite;
import com.zainic.zainiship.graphics.SpriteEffect;

public class EnemyExplosionEffect3 extends Effect {
    
    public EnemyExplosionEffect3(double x, double y) {
        super(x, y);
        this.name = "EnemyExplosionEffect3";
		this.sprite = new Sprite(48, SpriteEffect.enemyExplosion3);
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
        justMove(-this.width / 2, -this.height / 2);

        this.time = 0;
        this.life = 60;
        this.totalStates = sprite.getTotalStates();
    }

    public void update() {
        this.time++;
        if (this.time > this.life) remove();
        else {
            this.state = (this.totalStates - 1) * (this.time / this.life);
            this.sprite.loadState((int) this.state);
        }
    }

    public void render(Screen screen) {
		screen.renderEffect((int) this.x, (int) this.y, this);
	}
}
