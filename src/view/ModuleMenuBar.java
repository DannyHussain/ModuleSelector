package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

public class ModuleMenuBar extends MenuBar {

	//Fields
	private MenuItem  SaveStudentData,LoadStudentData, Exit, About;

	public ModuleMenuBar() {      

		Menu menu;

		//Build the first menu on the menu bar (First button with choices)
		menu = new Menu("File");

		LoadStudentData = new MenuItem("Load Journey Data");
		LoadStudentData.setAccelerator(KeyCombination.keyCombination("SHORTCUT+L"));
		menu.getItems().add(LoadStudentData);

		SaveStudentData = new MenuItem("Save Student Data");
		SaveStudentData.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
		menu.getItems().add(SaveStudentData);

		//adding a separator
		menu.getItems().add( new SeparatorMenuItem());

		Exit = new MenuItem("Exit");
		Exit.setAccelerator(KeyCombination.keyCombination("SHORTCUT+X"));
		menu.getItems().add(Exit);


		//add the menu to this menu bar
		this.getMenus().add(menu);

		//Adding the second button onto the menu
		menu = new Menu("Help");
		About = new MenuItem("About");
		About.setAccelerator(KeyCombination.keyCombination("SHORTCUT+A"));
		menu.getItems().add(About);

		this.getMenus().add(menu); 

	}

	//Handlers
	public void addLoadProfileHandler(EventHandler<ActionEvent> handler) {
		LoadStudentData.setOnAction(handler);
	}

	public void addSaveProfileHandler(EventHandler<ActionEvent> handler) {
		SaveStudentData.setOnAction(handler);
	}

	public void addExitHandler(EventHandler<ActionEvent> handler) {
		Exit.setOnAction(handler);	
	}

	public void addAboutHandler(EventHandler<ActionEvent> handler) {
		About.setOnAction(handler);
	}


}
