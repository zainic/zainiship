package com.zainic.zainiship.level;

import java.util.ArrayList;
import java.util.List;

import com.zainic.zainiship.screen.Screen;
import com.zainic.zainiship.entity.Entity;


public class Level {

	protected int width, height;
	
	private List<Entity> entities = new ArrayList<Entity>();
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		generateLevel();
	}
	
	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}
	
	protected void generateLevel() {
		
	}
	
	protected void loadLevel(String path) {
		
	}
	
	private void time() {
		
	}
	
	public List<Entity> getProjectiles() {
		return entities;
	}
	
	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
	}
	
	public void render(Screen screen) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
	}
	
	public void add(Entity e) {
		entities.add(e);
	}
	
}