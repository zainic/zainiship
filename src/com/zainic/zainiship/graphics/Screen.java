package com.zainic.zainiship.graphics;

import com.zainic.zainiship.entity.mob.enemy.Enemy;
import com.zainic.zainiship.entity.projectile.Projectile;
import com.zainic.zainiship.graphics.effect.Effect;
import com.zainic.zainiship.level.Level;

public class Screen {
	
	public int width, height;
	public int[] pixels;
	public int xOffset, yOffset;
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x000000;
		}
	}

	public int blendColors(int bg, int fg) {
		int alpha = (fg >>> 24) & 0xff;
		if (alpha == 0) return bg;
		if (alpha == 0xff) return fg;
		int invAlpha = 0xff - alpha;
		int bgR = (bg >> 16) & 0xff;
		int bgG = (bg >> 8) & 0xff;
		int bgB = bg & 0xff;
		int fgR = (fg >> 16) & 0xff;
		int fgG = (fg >> 8) & 0xff;
		int fgB = fg & 0xff;
		int r = (fgR * alpha + bgR * invAlpha) / 0xff;
		int g = (fgG * alpha + bgG * invAlpha) / 0xff;
		int b = (fgB * alpha + bgB * invAlpha) / 0xff;
		return 0xff000000 | (r << 16) | (g << 8) | b;
	}

	public void renderBackground(int xp, int yp, Level level) {
		for (int y = 0; y < level.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < level.getWidth(); x++) {
				int xa = x + xp;
				if (x < 0 || x >= this.width || y < 0 || y >= this.height) break;
				if (xa < 0 || xa >= level.getWidth()) {
					xa = Math.floorMod(xa, level.getWidth());
				}
				if (ya < 0 || ya >= level.getHeight()) {
					ya = Math.floorMod(ya, level.getHeight());
				}
				pixels[x + y * width] = level.getBackground()[xa + ya * level.getWidth()];
			}
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int idx = xa + ya * width;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				int alpha = (col >>> 24) & 0xff;
				if (alpha == 0xff) {
					pixels[idx] = col;
				} else if (alpha != 0) {
					pixels[idx] = blendColors(pixels[idx], col);
				}
			}
		}
		
	}
	
	public void renderPlayer(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int idx = xa + ya * width;
				int col = sprite.pixels[x + y * sprite.SIZE];
				int alpha = (col >>> 24) & 0xff;
				if (alpha == 0xff) {
					if (col != 0xff160702) pixels[idx] = col;
				} else if (alpha != 0) {
					pixels[idx] = blendColors(pixels[idx], col);
				}
			}
		}
	}

	public void renderProjectile(int xp, int yp, Projectile p) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp;
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int idx = xa + ya * width;
				int col = p.getSprite().pixels[x + y * p.getSpriteSize()];
				int alpha = (col >>> 24) & 0xff;
				if (alpha == 0xff) {
					if (col != 0xff160702) pixels[idx] = col;
				} else if (alpha != 0) {
					pixels[idx] = blendColors(pixels[idx], col);
				}
			}
		}
	}
	
	public void renderEntity(int xp, int yp, Enemy e) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < e.getSpriteSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < e.getSpriteSize(); x++) {
				int xa = x + xp;
				if (xa < -e.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int idx = xa + ya * width;
				int col = e.getSprite().pixels[x + y * e.getSpriteSize()];
				int alpha = (col >>> 24) & 0xff;
				if (alpha == 0xff) {
					if (col != 0xff160702) pixels[idx] = col;
				} else if (alpha != 0) {
					pixels[idx] = blendColors(pixels[idx], col);
				}
			}
		}
	}

	public void renderEffect(int xp, int yp, Effect e) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < e.getSpriteSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < e.getSpriteSize(); x++) {
				int xa = x + xp;
				if (xa < -e.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int idx = xa + ya * width;
				int col = e.getSprite().pixels[x + y * e.getSpriteSize()];
				int alpha = (col >>> 24) & 0xff;
				if (alpha == 0xff) {
					if (col != 0xff160702) pixels[idx] = col;
				} else if (alpha != 0) {
					pixels[idx] = blendColors(pixels[idx], col);
				}
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;		
	}

}
