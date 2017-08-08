package zeball.esben.main.object;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import zeball.esben.main.Game;

public class Ball implements GameObject {

	private double mx = 0, my = 0;
	private float x = 0, y = 0;
	private int width = 100, height = 100;
	private double speedX = 0, speedY = 0;
	private float speed = 0.05f;
	private int buffer = 200;
	
	private Game game;
	
	public Ball(Game game) {
		this.game = game;
		game.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				mx = e.getX();
				my = e.getY();
			}
			
		});
	}
	
	@Override
	public void tick() {
		
		double deltaX = mx - (x + width / 2);
		double deltaY = my - (y + height / 2);
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		double difference = buffer - distance;
		
		if (difference > 0) {
			speedX = deltaX / distance * difference * speed;
			speedY = deltaY / distance * difference * speed;
		} else {
			speedX = 0;
			speedY = 0;
		}
		
		x -= speedX;
		y -= speedY;
		
		if (x <= 0) {
			x = 0;
		} else if (x >= game.getWidth() - width) {
			x = (float) (game.getWidth() - width);
		}
		
		if (y < 0) {
			y = 0;
		} else if (y >= game.getHeight() - height) {
			y = (float) (game.getHeight() - height);
		}
		
	}

	@Override
	public void render(GraphicsContext g) {
		
		g.fillOval(x, y, width, height);
		
	}

}
