package zeball.esben.main;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import zeball.esben.main.object.Ball;

public class Game extends Canvas implements Runnable {
	
	private boolean running = false;
	private Thread game;
	private static final int WIDTH = 800, HEIGHT = 500;
	private GraphicsContext g;
	
	private Ball ball;
	
	public Game() {
		super(WIDTH, HEIGHT);
	}
	
	private void init() {
		g = getGraphicsContext2D();
		ball = new Ball(this);
	}
	
	@Override
	public void run() {
		
		init();
		
		int ticksPerSecond = 60;
		double nsPerTick = 1000000000 / ticksPerSecond;
		
		long lastTime = System.nanoTime();
		
		double delta = 0d;
		
		long timer = System.currentTimeMillis();
		
		int ticks = 0;
		
		while (running) {
			long now = System.nanoTime();
			delta += (now-lastTime) / nsPerTick;
			lastTime = now;			
			
			while (delta >= 1) {
				tick();
				render();
				ticks += 1;
				delta -= 1;
			}
			
			
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				System.out.println("TICKS: " + ticks);
				ticks = 0;				
			}
			
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		stop();
	}
	
	public void tick() {
		
		ball.tick();
		
	}
	
	public void render() {
		g.setFill(Color.ORANGE);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		//
				
		ball.render(g);
		
		//
		
	}
	
	public void start() {
		if (running) return;
		game = new Thread(this, "ZeBall");
		game.start();
		running = true;
	}
	
	public void stop() {
		if (!running) return;
		running = false;
		try {
			game.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
