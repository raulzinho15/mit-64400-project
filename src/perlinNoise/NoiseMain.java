package perlinNoise;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class NoiseMain extends Application {
	
	private static final int WIDTH = 3920;
	private static final int HEIGHT = 2160;

	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) throws Exception {
		
		// Sets up AnchorPane
		final AnchorPane pane = new AnchorPane();
		pane.setPrefSize(WIDTH, HEIGHT);
		
		// Sets up black background
		final Rectangle background = new Rectangle(WIDTH, HEIGHT, Color.BLACK);
		pane.getChildren().add(background);
		
		// Displays perlin noise
		for (int y = 0; y < HEIGHT; y += 3) {
			for (int x = 0; x < WIDTH; x += 3) {
				final double noiseVal = Noise.noise(new Vector2D(x/100.0, y/100.0), WIDTH/100);
				final int pixVal = (int)(128 + noiseVal*128);
				
				final Rectangle pixel = new Rectangle(3, 3, Color.WHITE);
				pixel.setFill(Color.rgb(pixVal, pixVal, pixVal));
				pixel.setLayoutX(x);
				pixel.setLayoutY(y);
				pane.getChildren().add(pixel);
			}
		}
		System.out.println("Finished");
		
		// Sets up the scene
		stage.setScene(new Scene(pane));
		stage.show();
		stage.setFullScreen(true);
	}

}
