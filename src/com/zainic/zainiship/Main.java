package com.zainic.zainiship;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.zainic.zainiship.input.Keyboard;
import com.zainic.zainiship.input.Mouse;
import com.zainic.zainiship.entity.mob.Player;
import com.zainic.zainiship.graphics.Screen;
import com.zainic.zainiship.level.Level;

public class Main extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	private static int width = 600;
	private static int height = width / 16 * 9;
	private static int scale = 2;
	private static String title = "Zainiship";
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	private boolean pause = false;
	private int pauseDelay = 10;
	private int frames = 0;
	private int ticks = 0;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private Screen screen;
	private Level level;
	private Keyboard key;
	private Player player;
	private double xScroll = 0, yScroll = 0;
	
	public Main() {
		Dimension size = new Dimension(width*scale, height*scale);
		this.setPreferredSize(size);
		
		frame = new JFrame();
		screen = new Screen(width, height);
		level = Level.level1;
		level.init(screen);
		key = new Keyboard();
		player = new Player((width >> 1) - 16, (height >> 1) - 16, key);
		player.init(level, screen);
		level.addAlliesMob(player);
		
		Mouse mouse = new Mouse();
		this.addKeyListener(key);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;
		double delta = 0;
		this.requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta > 1) {
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | " + ticks + "ups, " + frames + "fps");
				this.ticks = ticks;
				this.frames = frames;
				ticks = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void tick() {
		key.update();
		if (key.pause && pauseDelay <= 0) {
			pause = pause ^ true;
			pauseDelay = 20;
		}
		pauseDelay--;
		if (!pause) {
			level.update();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3); //triple buffering strategy
			return;
		}
		
		screen.clear();
		//int xScroll = player.getX() - (screen.width >> 1) + 32;
		//int yScroll = player.getY() - (screen.height >> 1) + 32;
		yScroll -= 0.2;
		level.render((int) xScroll, (int) yScroll, screen);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics(); //link between graphics and buffer
		g.setColor(new Color(255, 99, 33));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		//g.setColor(Color.WHITE);
		//g.setFont(new Font("Courier New", Font.PLAIN, 20));
		//g.drawString("X : "+player.x+" Y : "+player.y, 100, 100);
		//g.fillRect(Mouse.getX() - 5, Mouse.getY() - 5, 10, 10);
		//g.fillRect(Mouse.getX() - 5, Mouse.getY() - 5, 10, 10);
		g.drawString(title + " | " + this.ticks + "ups, " + this.frames + "fps", 10, 15);
		g.drawString("Button : " + Mouse.getB(), 10, 30);
		g.drawString("Inside : " + Mouse.isInsideScreen(), 10, 45);
		g.drawString("loc : " + "(" + Mouse.getX() + ", " + Mouse.getY() + ")", 10, 60);
		g.drawString("Allies Entity : " + level.getAlliesEntities().size(), 10, 75);
		g.drawString("Enemies Entity : " + level.getEnemiesEntities().size(), 10, 90);
		g.drawString("Allies Mob : " + level.getAlliesMob().size(), 10, 105);
		g.drawString("Enemies Mob : " + level.getEnemiesMob().size(), 10, 120);
		g.drawString("Allies Projectile : " + level.getAlliesProjectiles().size(), 10, 135);
		g.drawString("Enemies Projectile : " + level.getEnemiesProjectiles().size(), 10, 150);
		g.drawString("Health : " + player.getHealth(), 10, 165);
		g.drawString("Pause : " + pause, 10, 180);
		g.dispose(); //remove the graphics after not used
		bs.show(); //show the buffer that being calculated
	}
	
	public static void main(String[] args) {
		Main game = new Main();
		game.frame.setResizable(false);
		game.frame.setTitle(Main.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}

}
