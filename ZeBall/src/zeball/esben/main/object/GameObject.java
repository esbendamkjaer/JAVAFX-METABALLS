package zeball.esben.main.object;

import javafx.scene.canvas.GraphicsContext;

public interface GameObject {

	public void tick();
	
	public void render(GraphicsContext g);
	
}
