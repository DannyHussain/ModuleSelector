package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.VBox;

public class ModuleRootPane extends VBox {
	
	//Fields
	private ModuleMenuBar menuBar;
	private CreateProfilePane csp;
	private SelectModulesPane sm;
	private OverviewSelectionPane os;
	private TabPane tabsContainer;
	

	public ModuleRootPane() {
	
		//Creating instance of classes
		menuBar = new ModuleMenuBar();
		csp = new CreateProfilePane();
		sm = new SelectModulesPane();
		os = new OverviewSelectionPane();
		
		
		
		//Creating tabs and storing into a container
		tabsContainer = new TabPane();
		tabsContainer.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		Tab t1 = new Tab("Create Profile", csp);
		Tab t2 = new Tab("Select Modules", sm);
		Tab t3 = new Tab("Overview Selection", os);
		tabsContainer.getTabs().addAll(t1, t2, t3);

	

		//add the menu bar and tabs container
		this.getChildren().addAll(menuBar, tabsContainer);
	}
	
	//Methods
	//Returns the create profile pane
	public CreateProfilePane getCreateProfilePane() {
		return csp;
	}

	//Returns the select modules pane
	public SelectModulesPane getSelectModulesPane() {
		return sm;
	}
	
	//Returns the overview selection pane
	public OverviewSelectionPane getOverviewSelectionPane() {
		return os;
	}
	
	//Returns the module menu bar
	public ModuleMenuBar getMenuBar() {
		return menuBar;
	}
	
	//Method to automatically change tab when submit button is pressed in select modules pane
	public void changeTab(int index) {
		tabsContainer.getSelectionModel().select(index);
	}
	
}
