package main;


import controller.ModuleController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StudentProfile;
import view.ModuleRootPane;


public class ApplicationLoader extends Application  {

	private ModuleRootPane view;


	public void init() {	
		StudentProfile model = new StudentProfile();
		view = new ModuleRootPane();
		new ModuleController(view, model);
	}


	@Override
	public void start(Stage stage) throws Exception {
		stage.setWidth(1050);
		stage.setHeight(650);
		stage.setTitle("Final Year Module Chooser Tool");
		stage.setScene(new Scene(view));
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}


