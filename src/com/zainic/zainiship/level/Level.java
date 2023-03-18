package com.zainic.zainiship.level;

import java.util.ArrayList;
import java.util.List;

import com.zainic.zainiship.level.Level;
import com.zainic.zainiship.entity.Entity;
import com.zainic.zainiship.graphics.Screen;


public class Level {

	protected int width, height;
	protected int[] background;
	
	private List<Entity> entities = new ArrayList<Entity>();
	
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
	
	private void time() {
		
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.renderBackground(xScroll, yScroll, this);
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
	}
	
	public void add(Entity e) {
		entities.add(e);
	}
	
}
