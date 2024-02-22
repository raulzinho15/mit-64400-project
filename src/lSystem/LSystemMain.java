package lSystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import perlinNoise.Vector2D;

public class LSystemMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		
		// Sets up the AnchorPane
		final AnchorPane pane = new AnchorPane();
		
		// Sets up the background
		final Rectangle background = new Rectangle(3400, 1800, Color.BLACK);
		pane.getChildren().add(background);
		
		// Sets up the fractal L-system
		final Letter[] axiom = {Letter.MOVE};
		final Rule[] rules = {
			new Rule(Letter.MOVE, new Letter[] {
				Letter.MOVE, Letter.RIGHT, Letter.MOVE, Letter.LEFT, Letter.MOVE,
				Letter.LEFT, Letter.MOVE, Letter.RIGHT, Letter.MOVE
			}),
		};
		final LSystem lSystem = new LSystem(axiom, rules);
		lSystem.steps(7);
		
		// Constructs the fractal
		int direction = 3;
		Vector2D pos = new Vector2D(3300, 1700);
		for (Letter action : lSystem.getWord()) {

			// Moves in the current direction
			if (action == Letter.MOVE) {
				
				// Stores the line endpoints
				Vector2D oldPos = pos;
				final double scale = 1;
				pos = pos.add(directionVector(direction).scale(scale));
				
				// Draws the line
				final Line line = new Line(pos.x, pos.y, oldPos.x, oldPos.y);
				line.setStroke(Color.WHITE);
				line.setStrokeWidth(1);
				pane.getChildren().add(line);
				
			// Turns right
			} else if (action == Letter.RIGHT)
				direction = (direction+1)%4;
				
			// Turns left
			else if (action == Letter.LEFT)
				direction = (direction+3)%4;
		}
		
		// Sets up the stage
		stage.setScene(new Scene(pane));
		stage.show();
	}
	
	private static Vector2D directionVector(int direction) {
		switch (direction) {
		case 0:
			return Vector2D.UP;
		case 1:
			return Vector2D.RIGHT;
		case 2:
			return Vector2D.DOWN;
		case 3:
			return Vector2D.LEFT;
		default:
			return Vector2D.ZERO;
		}
	}

}
