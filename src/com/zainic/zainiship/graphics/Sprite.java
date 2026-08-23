package com.zainic.zainiship.graphics;

public class Sprite {
	
	private int x, y;
	private SpriteSheet sheet;
	private SpriteEffect effect;
	private int width, height;
	public final int SIZE;
	public int[] pixels;
	public int totalStates;
	
	// Player sprites
	public static Sprite player_ship = new Sprite(64, 0, 0, SpriteSheet.player);
	public static Sprite player_ship32 = new Sprite(32, 0, 0, SpriteSheet.player32);
	
	// Enemy sprites
	public static Sprite enemy_ship_1 = new Sprite(64, 0, 0, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_1 = new Sprite(32, 0, 0, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_2 = new Sprite(64, 0, 1, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_2 = new Sprite(32, 0, 1, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_3 = new Sprite(64, 0, 2, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_3 = new Sprite(32, 0, 2, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_4 = new Sprite(64, 0, 3, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_4 = new Sprite(32, 0, 3, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_5 = new Sprite(64, 1, 0, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_5 = new Sprite(32, 1, 0, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_6 = new Sprite(64, 1, 1, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_6 = new Sprite(32, 1, 1, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_7 = new Sprite(64, 1, 2, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_7 = new Sprite(32, 1, 2, SpriteSheet.enemyship32);

	public static Sprite enemy_ship_8 = new Sprite(64, 1, 3, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_8 = new Sprite(32, 1, 3, SpriteSheet.enemyship32);

	public static Sprite enemy_ship_9 = new Sprite(64, 2, 0, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_9 = new Sprite(32, 2, 0, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_10 = new Sprite(64, 2, 1, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_10 = new Sprite(32, 2, 1, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_11 = new Sprite(64, 2, 2, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_11 = new Sprite(32, 2, 2, SpriteSheet.enemyship32);

	public static Sprite enemy_ship_12 = new Sprite(64, 2, 3, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_12 = new Sprite(32, 2, 3, SpriteSheet.enemyship32);

	public static Sprite enemy_ship_13 = new Sprite(64, 3, 0, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_13 = new Sprite(32, 3, 0, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_14 = new Sprite(64, 3, 1, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_14 = new Sprite(32, 3, 1, SpriteSheet.enemyship32);
	
	public static Sprite enemy_ship_15 = new Sprite(64, 3, 2, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_15 = new Sprite(32, 3, 2, SpriteSheet.enemyship32);

	public static Sprite enemy_ship_16 = new Sprite(64, 3, 3, SpriteSheet.enemyship);
	public static Sprite enemy_ship32_16 = new Sprite(32, 3, 3, SpriteSheet.enemyship32);

	//Projectile Sprites
	public static Sprite bullet_projectile = new Sprite(16, 0, 0, SpriteSheet.projectilesheet);
	
	public static Sprite alpha_projectile = new Sprite(16, 1, 0, SpriteSheet.projectilesheet);
	public static Sprite beta_projectile = new Sprite(16, 1, 1, SpriteSheet.projectilesheet);
	public static Sprite gamma_projectile = new Sprite(16, 1, 2, SpriteSheet.projectilesheet);
	public static Sprite delta_projectile = new Sprite(16, 1, 3, SpriteSheet.projectilesheet);
	public static Sprite epsilon_projectile = new Sprite(16, 2, 0, SpriteSheet.projectilesheet);
	public static Sprite zeta_projectile = new Sprite(16, 2, 1, SpriteSheet.projectilesheet);
	public static Sprite eta_projectile = new Sprite(16, 2, 2, SpriteSheet.projectilesheet);
	public static Sprite theta_projectile = new Sprite(16, 2, 3, SpriteSheet.projectilesheet);
	public static Sprite iota_projectile = new Sprite(16, 3, 0, SpriteSheet.projectilesheet);
	public static Sprite kappa_projectile = new Sprite(16, 3, 1, SpriteSheet.projectilesheet);
	public static Sprite lambda_projectile = new Sprite(16, 3, 2, SpriteSheet.projectilesheet);
	public static Sprite mu_projectile = new Sprite(16, 3, 3, SpriteSheet.projectilesheet);
	public static Sprite nu_projectile = new Sprite(16, 4, 0, SpriteSheet.projectilesheet);
	public static Sprite xi_projectile = new Sprite(16, 4, 1, SpriteSheet.projectilesheet);
	public static Sprite omicron_projectile = new Sprite(16, 4, 2, SpriteSheet.projectilesheet);
	public static Sprite pi_projectile = new Sprite(16, 4, 3, SpriteSheet.projectilesheet);
	public static Sprite rho_projectile = new Sprite(16, 5, 0, SpriteSheet.projectilesheet);
	public static Sprite sigma_projectile = new Sprite(16, 5, 1, SpriteSheet.projectilesheet);
	public static Sprite tau_projectile = new Sprite(16, 5, 2, SpriteSheet.projectilesheet);
	public static Sprite upsilon_projectile = new Sprite(16, 5, 3, SpriteSheet.projectilesheet);
	public static Sprite phi_projectile = new Sprite(16, 6, 0, SpriteSheet.projectilesheet);
	public static Sprite chi_projectile = new Sprite(16, 6, 1, SpriteSheet.projectilesheet);
	public static Sprite psi_projectile = new Sprite(16, 6, 2, SpriteSheet.projectilesheet);
	public static Sprite omega_projectile = new Sprite(16, 6, 3, SpriteSheet.projectilesheet); 

	//Effect Sprites
	public static Sprite default_explosion_effect = new Sprite(48, SpriteEffect.defaultEnemyExplosion);

	public Sprite(int size, SpriteEffect effect) {
		this.SIZE = size;
		this.width = size;
		this.height = size;
		this.pixels = new int[SIZE * SIZE];
		this.x = 0;
		this.effect = effect;
		this.totalStates = effect.getTotalStates();
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.SIZE = size;
		this.width = size;
		this.height = size;
		this.pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
		setColor(color);
	}
	
	public Sprite(int size, int color) {
		this.SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	private void setColor(int color) {
		for (int i = 0; i < this.width * this.height; i++) {
			pixels[i] = color;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}

	public void loadState(int state) {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = effect.pixels[(x + this.x) + (y + (state * effect.SIZE)) * effect.SIZE];
			}
		}
	}

	public int getTotalStates() {
		return this.totalStates;
	}

}
