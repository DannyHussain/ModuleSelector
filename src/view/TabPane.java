package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;

public class TabPane extends Control {

	private TabPane tabPane = new TabPane();
	private ComboBox<String> choices;
	
	public TabPane() {
		this.setStyle("-fx-background-color: #D6D6D6;");	
		this.setPadding(new Insets(10, 10, 10, 10));
		
		Tab csp = new Tab("Create Profile");
	    Tab sm = new Tab("Select Modules");
	    Tab os = new Tab("Overview Selection");
	    
	    ObservableList<String> list = FXCollections.observableArrayList("Input P number:"
	    		,"Input first name", "Input username", "Input email", "Input date");
	    choices = new ComboBox<String>(list);
	    choices.getSelectionModel().select(0);
	    choices.setPrefSize(90, 30);
	
	    this.getChildren().addAll(tabPane);
	    
	    
}

	
	

	
	
}
