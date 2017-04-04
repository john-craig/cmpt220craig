import javafx.animation.Animation;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Tester extends Application{
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override public void start(Stage primaryStage){
		Circle circle = new Circle(100, 100, 25, Color.BLUE);
		Pane pane = new Pane(circle);
		Scene scene = new Scene(pane, 400, 400);
		scene.setOnKeyPressed(e ->{
			switch(e.getCode()){
				case UP: circle.setCenterY(circle.getCenterY() - 10); break;
				case DOWN: circle.setCenterY(circle.getCenterY() + 10); break;
				case LEFT: circle.setCenterX(circle.getCenterX() - 10); break;
				case RIGHT: circle.setCenterX(circle.getCenterX() + 10); break;
			}
		});
		
		primaryStage.setTitle("Tester");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}