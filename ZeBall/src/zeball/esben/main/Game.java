package zeball.esben.main;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Game extends Canvas implements Runnable {
	
	//Ball variables
	private double mx = 0, my = 0;
	private float x = 0, y = 0;
	private int width = 100, height = 100;
	private double speedX = 0, speedY = 0;
	//private int speed = 5;
	private int buffer = 100;
	
	private boolean running = false;
	private Thread game;
	private static final int WIDTH = 800, HEIGHT = 500;
	private GraphicsContext g;
	
	public Game() {
		super(WIDTH, HEIGHT);
	}
	
	private void init() {
		g = getGraphicsContext2D();
		addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				mx = e.getX();
				my = e.getY();
			}
			
		});
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
				
				System.out.println(x);
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
		
		double deltaX = mx - (x + width / 2);
		double deltaY = my - (y + height / 2);
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		double difference = buffer - distance;
		
		if (difference > 0) {
			speedX = deltaX / distance * difference;
			speedY = deltaY / distance * difference;
		} else {
			speedX = 0;
			speedY = 0;
		}
		
		x -= speedX;
		y -= speedY;
		
		if (x <= 0) {
			x = 0;
		} else if (x >= getWidth() - width) {
			x = (float) (getWidth() - width);
		}
		
		if (y < 0) {
			y = 0;
		} else if (y >= getHeight() - height) {
			y = (float) (getHeight() - height);
		}
		
	}
	
	public void render() {
		g.setFill(Color.ORANGE);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		//
		
		g.fillOval(x, y, width, height);
		
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
