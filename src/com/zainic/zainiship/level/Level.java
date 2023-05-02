package com.zainic.zainiship.level;

import java.util.ArrayList;
import java.util.List;

import com.zainic.zainiship.level.Level;
import com.zainic.zainiship.entity.Entity;
import com.zainic.zainiship.entity.mob.Mob;
import com.zainic.zainiship.entity.projectile.Projectile;
import com.zainic.zainiship.graphics.Screen;


public class Level {

	protected int width, height;
	protected int[] background;
	protected int time;
	protected Screen screen;
	
	private List<Entity> alliesEntities = new ArrayList<Entity>();
	private List<Entity> enemiesEntities = new ArrayList<Entity>();
	private List<Mob> alliesMob = new ArrayList<Mob>();
	private List<Mob> enemiesMob = new ArrayList<Mob>();
	private List<Projectile> alliesProjectiles = new ArrayList<Projectile>();
	private List<Projectile> enemiesProjectiles = new ArrayList<Projectile>();
	
	public static Level level1 = new Level1("/levels/level1/background.png");
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		generateLevel();
	}
	
	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}
	
	public void init(Screen screen) {
		this.screen = screen;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int[] getBackground() {
		return background;
	}
	
	protected void generateLevel() {
		
	}
	
	protected void loadLevel(String path) {
		
	}
	
	public List<Entity> getAlliesEntities() {
		return alliesEntities;
	}
	
	public List<Entity> getEnemiesEntities() {
		return enemiesEntities;
	}
	
	public List<Mob> getAlliesMob() {
		return alliesMob;
	}
	
	public List<Mob> getEnemiesMob() {
		return enemiesMob;
	}
	
	public List<Projectile> getAlliesProjectiles() {
		return alliesProjectiles;
	}
	
	public List<Projectile> getEnemiesProjectiles() {
		return enemiesProjectiles;
	}
	
	public void update() {
		for (int i = 0; i < alliesEntities.size(); i++) {
			alliesEntities.get(i).update();
		}
		for (int i = 0; i < enemiesEntities.size(); i++) {
			enemiesEntities.get(i).update();
		}
		addTimeEvent();
		this.time++;
		clear();
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		//screen.setOffset(xScroll, yScroll);
		screen.renderBackground(xScroll, yScroll, this);
		
		for (int i = alliesEntities.size() - 1; i >= 0 ; i--) {
			alliesEntities.get(i).render(screen);
		}
		for (int i = enemiesEntities.size() - 1; i >= 0 ; i--) {
			enemiesEntities.get(i).render(screen);
		}
		
	}
	
	private void clear() {
		for (int i = 0; i < getAlliesEntities().size(); i++) {
			Entity e = getAlliesEntities().get(i);
			if (e.isRemoved()) getAlliesEntities().remove(i);
		}
		for (int i = 0; i < getEnemiesEntities().size(); i++) {
			Entity e = getEnemiesEntities().get(i);
			if (e.isRemoved()) getEnemiesEntities().remove(i);
		}
		for (int i = 0; i < getAlliesMob().size(); i++) {
			Mob e = getAlliesMob().get(i);
			if (e.isRemoved()) getAlliesMob().remove(i);
		}
		for (int i = 0; i < getEnemiesMob().size(); i++) {
			Mob e = getEnemiesMob().get(i);
			if (e.isRemoved()) getEnemiesMob().remove(i);
		}
		for (int i = 0; i < getAlliesProjectiles().size(); i++) {
			Projectile p = getAlliesProjectiles().get(i);
			if (p.isRemoved()) getAlliesProjectiles().remove(i);
		}
		for (int i = 0; i < getEnemiesProjectiles().size(); i++) {
			Projectile p = getEnemiesProjectiles().get(i);
			if (p.isRemoved()) getEnemiesProjectiles().remove(i);
		}
	}
	
	public void addAllies(Entity e) {
		e.init(this, screen);
		alliesEntities.add(e);
	}
	
	public void addEnemies(Entity e) {
		e.init(this, screen);
		enemiesEntities.add(e);
	}
	
	public void addAlliesMob(Mob e) {
		e.init(this, screen);
		alliesEntities.add(e);
		alliesMob.add(e);
	}
	
	public void addEnemiesMob(Mob e) {
		e.init(this, screen);
		enemiesEntities.add(e);
		enemiesMob.add(e);
	}
	
	public void addAlliesProjectile(Projectile p) {
		p.init(this, screen);
		alliesEntities.add(p);
		alliesProjectiles.add(p);
	}
	
	public void addEnemiesProjectile(Projectile p) {
		p.init(this, screen);
		enemiesEntities.add(p);
		enemiesProjectiles.add(p);
	}
	
	public void addTimeEvent() {
		
	}
	
}
