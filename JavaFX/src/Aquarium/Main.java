package Aquarium;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
		stage.setScene(new Scene(root, 640, 480));
		stage.setTitle("Калькулятор объёма аквариума");
		stage.show();
	}
}
