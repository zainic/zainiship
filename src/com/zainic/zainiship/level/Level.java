package com.zainic.zainiship.level;

import java.util.ArrayList;
import java.util.List;

import com.zainic.zainiship.level.Level;
import com.zainic.zainiship.entity.Entity;
import com.zainic.zainiship.entity.projectile.Projectile;
import com.zainic.zainiship.graphics.Screen;


public class Level {

	protected int width, height;
	protected int[] background;
	protected int time;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	
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
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		addTimeEvent();
		this.time++;
		clear();
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		//screen.setOffset(xScroll, yScroll);
		screen.renderBackground(xScroll, yScroll, this);
		
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		
	}
	
	private void clear() {
		for (int i = 0; i < getEntities().size(); i++) {
			Entity e = getEntities().get(i);
			if (e.isRemoved()) getEntities().remove(i);
		}
		
	}
	
	public void add(Entity e) {
		e.init(this);
		entities.add(e);
	}
	
	public void addProjectile(Projectile p) {
		p.init(this);
		projectiles.add(p);
	}
	
	public void addTimeEvent() {
		
	}
	
}
