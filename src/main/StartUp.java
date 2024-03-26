package main;

import domein.DomeinController;
import domein.TegelGebied;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.SpelApplicatie;

public class StartUp extends Application
	{
	@Override
	public void start(Stage stage) throws Exception
		{
				Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

				Scene scene = new Scene(root);

				stage.setScene(scene);
				stage.show();
		}

	public static void main(String[] args) {
		DomeinController dc = new DomeinController();
		//new SpelApplicatie(dc).start();
		launch();
	}
	}
