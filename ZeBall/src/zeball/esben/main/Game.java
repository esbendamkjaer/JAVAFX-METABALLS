package zeball.esben.main;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import zeball.esben.main.object.Ball;

public class Game extends Canvas {
	
	private boolean running = false;
	private static final int WIDTH = 800, HEIGHT = 500;
	private GraphicsContext g;
	
	private Ball ball;
	
	
	private AnimationTimer gameLoop = new AnimationTimer() {
		//Game Loop Variables
		private int ticksPerSecond = 60;
		private double nsPerTick = 1000000000d / ticksPerSecond;
		
		private long lastTime = System.nanoTime();
		
		private double delta = 0d;
		
		private long timer = System.currentTimeMillis();
		private int ticks = 0;
		
		@Override
		public void handle(long now) {
			delta += (now-lastTime) / nsPerTick;
			lastTime = now;			
			
			while (delta >= 1) {
				tick();
				render();
				ticks++;
				delta -= 1;
			}
			
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				System.out.println("TICKS: " + ticks);
				ticks = 0;
			}
		}
	};
	
	public Game() {
		super(WIDTH, HEIGHT);
	}
	
	private void init() {
		g = getGraphicsContext2D();
		ball = new Ball(this);
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
		
		init();
		gameLoop.start();
		
		running = true;
	}
	
	public void stop() {
		if (!running) return;
		running = false;
		
		gameLoop.stop();
	}
	
	
	
}
