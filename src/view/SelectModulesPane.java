package view;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Course;
import model.Module;

/**
 * The SelectModulesPane is the second tab, which is used to
 * allow the user to choose their modules and allows them to 
 * add, remove, reset and finally submit their
 * choices at the end which then generates the overview.
 */

public class SelectModulesPane extends GridPane {

	//Fields
	private Button reset, remove, add, submit;
	private TextField currentcredits;
	private int credits, creditcheck;
	private ListView<Module> unselected;
	private ListView<Module> selected;
	private ObservableList<Module> unselecteditems = FXCollections.observableArrayList ();
	private ObservableList<Module> selecteditems = FXCollections.observableArrayList ();
	private ObservableList<Module> tempitems = FXCollections.observableArrayList();

	public SelectModulesPane() {

		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-background-color:#FFE6E6;");

		//Labels and styling
		Label a = new Label("Unselected Modules");
		a.setTextFill(Color.web("#4C4C4C"));
		a.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		a.setPadding(new Insets(40,0,0,40));

		Label b = new Label("Selected Modules");
		b.setTextFill(Color.web("#4C4C4C"));
		b.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		b.setPadding(new Insets(40,0,0,60));

		Label c = new Label("Current Credits");
		c.setTextFill(Color.web("#4C4C4C"));
		c.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		c.setPadding(new Insets(10,0,0,350));

		this.add(a,0,0);
		this.add(b,1,0);
		this.add(c,0,6);

		//Text field for credits
		currentcredits = new TextField();
		HBox creditsbox = new HBox(currentcredits);
		currentcredits.setText("0");
		currentcredits.setEditable(false);
		currentcredits.setPrefSize(50, 20);
		creditsbox.setPadding(new Insets(10,0,0,10));
		this.add(creditsbox,1,6);

		//Lists + Boxes
		unselected = new ListView<Module>();

		HBox unselectedbox = new HBox(unselected);
		unselectedbox.setPadding(new Insets(0,0,0,40));
		unselected.setItems(unselecteditems);
		unselected.setPrefSize(REMAINING, REMAINING);
		this.add(unselectedbox,0,1);

		selected = new ListView<Module>();

		HBox selectedbox = new HBox(selected);
		selectedbox.setPadding(new Insets(0,30,0,60));
		selected.setItems(selecteditems);
		selected.setPrefSize(REMAINING, REMAINING);
		this.add(selectedbox,1,1);


		//Buttons
		reset = new Button("Reset");
		reset.setPrefSize(60, 40);
		remove = new Button("Remove");
		remove.setPrefSize(60, 40);
		add = new Button("Add");
		add.setPrefSize(60, 40);
		submit = new Button("Submit");
		submit.setPrefSize(60, 40);

		HBox buttons1 = new HBox(reset, remove);
		buttons1.setSpacing(10);
		buttons1.setPadding(new Insets(30,0,40,320));
		buttons1.setAlignment(Pos.CENTER_RIGHT);
		this.add(buttons1,0,8);

		HBox buttons2 = new HBox(add, submit);
		buttons2.setSpacing(10);
		buttons2.setPadding(new Insets(30,0,40,10));
		this.add(buttons2,1,8);

	}

	//Handlers
	public void addResetHandler(EventHandler<ActionEvent> handler) {
		reset.setOnAction(handler);
	}


	public void addRemoveHandler(EventHandler<ActionEvent> handler) {
		remove.setOnAction(handler);
	}

	public void addAddHandler(EventHandler<ActionEvent> handler) {
		add.setOnAction(handler);
	}

	public void addSubmitHandler(EventHandler<ActionEvent> handler) {
		submit.setOnAction(handler);
	}

	//Methods

	//Method used to reset the modules back to their original state
	public void resetModules(){
		if(selecteditems.isEmpty() == true){
			alertDialogBuilder(AlertType.WARNING, "Warning", null, "There is nothing to reset");
		}
		for(Module x : selecteditems){
			if(x.isMandatory() == false) {
				unselecteditems.add(x);
				tempitems.add(x);
			}
		}

		for(Module x : tempitems){
			selecteditems.remove(x);
		}
	}

	//Populate both lists with modules (mandatory and non mandatory) and update the credits
	public void populateModules(Course course){
		unselected.getItems().clear();
		selected.getItems().clear();
		credits = 0;

		for(Module x : course.getModulesOnCourse()){
			if(x.isMandatory() == false) {
				unselecteditems.add(x);
				x.getCredits();	
			}
			else{
				selecteditems.add(x);
				credits = credits + x.getCredits();
				currentcredits.setText(String.valueOf(credits));	
			}
		}
	}

	//Method allows the user to choose a module from the unselected list and puts it in the selected list
	public void addSelectedModule(){
		if(unselected.getSelectionModel().isEmpty() == true){
			alertDialogBuilder(AlertType.WARNING, "Warning", null, "Please select an item before adding");
		}
		else{
			creditcheck = credits + unselected.getSelectionModel().getSelectedItem().getCredits();
			if(creditcheck > 120 ){

			}
			else {
				credits = creditcheck;
				currentcredits.setText(String.valueOf(credits));
				selecteditems.add(unselected.getSelectionModel().getSelectedItem());
				unselecteditems.remove(unselected.getSelectionModel().getSelectedItem());
			}
		}
	}

	//Method allows the user to remove a module from the selected list and puts it back in the unselected list (Except mandatory modules)
	public void removeSelectedModule(){
		if(selected.getSelectionModel().getSelectedItem() == null){
			alertDialogBuilder(AlertType.WARNING, "Warning", null, "Please selected an item before removing");
		}

		else if(selected.getSelectionModel().getSelectedItem().isMandatory() == true){
			alertDialogBuilder(AlertType.ERROR, "Error", null, "Cannot remove mandatory modules");
		}
		else {
			credits = credits - selected.getSelectionModel().getSelectedItem().getCredits();	
			currentcredits.setText(String.valueOf(credits));
			unselecteditems.add(selected.getSelectionModel().getSelectedItem());
			selecteditems.remove(selected.getSelectionModel().getSelectedItem());
		}
	}

	//ObservableList which returns the selected modules
	public ObservableList<Module> getContents() {
		return selecteditems;
	}


	//Methods used to restore profile back to previous state
	public void loadUnselectedModules(Course l){
		for(Module x : l.getModulesOnCourse()){
			if(x.isMandatory() != true) {
				unselecteditems.add(x);
			}
		}
	}

	public void removeUnselectedModules(Module m){
		unselecteditems.remove(m);
		credits = 120;
		currentcredits.setText(String.valueOf(credits));
	}

	//Method to clear both lists
	public void clearLists() {
		unselecteditems.clear();
		selecteditems.clear();
	}


	//Binding

	//Disables submit button until the specification is matched
	public void SubmitButtonDisableBind(BooleanBinding property) {
		submit.disableProperty().bind(property);
	}

	//Disables the add button until the specification is matched
	public void AddButtonDisableBind(BooleanBinding property) {
		add.disableProperty().bind(property);
	}

	public BooleanBinding isNotFull() {
		credits = 120;
		return currentcredits.textProperty().isNotEqualTo(String.valueOf(credits));

	}

	public BooleanBinding isFull() {
		credits = 120;
		return currentcredits.textProperty().isEqualTo(String.valueOf(credits));

	}

	//Dialog Builder For Validation Checks
	private void alertDialogBuilder(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
