package zeball.esben.main.window;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import zeball.esben.main.Game;

public class Frame extends Application {
	
	private Game game;
	
	public Frame() {
		game = new Game();
	}
	
	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("ZeBall");
		
		Group root = new Group();
		
		game = new Game();
		
		root.getChildren().add(game);
		
		primaryStage.setScene(new Scene(root, 800, 500));
		primaryStage.setResizable(false);
		primaryStage.show();
		game.start();
	}
	
	@Override
	public void stop() throws Exception {
		game.stop();
		super.stop();
	}
	
}
