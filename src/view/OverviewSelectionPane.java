package view;

import java.io.IOException;
import java.io.PrintWriter;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * The OverviewSelectionPane is the final tab, which simply displays
 * overall details that have been submitted, these include
 * chosen modules and their details(credits, mandatory etc)
 * and a save overview button to save profile into a text file
 */


public class OverviewSelectionPane extends GridPane{

	//Fields
	private TextArea overview;
	private Button savebutton;

	public OverviewSelectionPane() {


		this.setPadding(new Insets(80, 80, 80, 80));
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-background-color:#FFE6E6;");

		//Overview Box
		HBox overviewbox = new HBox();
		overview = new TextArea("Overview will appear here");
		overview.setFont(Font.font("Verdana",FontWeight.BOLD,FontPosture.ITALIC, 12));
		overview.setEditable(false);
		//overview.setPrefSize(700, 300);
		overview.setPrefSize(REMAINING, REMAINING);
		overview.setWrapText(true);
		overviewbox.setPadding(new Insets(50,0,0,20));
		overviewbox.getChildren().add(overview);
		this.add(overviewbox, 0, 0);

		//SaveOverview Button
		savebutton = new Button("Save Overview");
		savebutton.setPrefSize(100, 30);

		HBox button = new HBox(savebutton);
		button.setPadding(new Insets(20,0,20,0));
		button.setAlignment(Pos.CENTER);
		this.add(button, 0,1);

	}

	//Handler
	public void addSaveOverviewHandler(EventHandler<ActionEvent> handler) {
		savebutton.setOnAction(handler);
	}

	//Methods
	//sets the text to a specified value
	public void setResults(String x) {
		overview.setText(x);
	}

	//Saves the profile to a text file using a print writer
	public void saveProfile() throws IOException{
		PrintWriter writer = new PrintWriter("Profile.txt");

		writer.println(overview.getText());
		writer.close();
	}

	//Binding

	public void SaveOverviewButtonDisableBind(BooleanBinding property) {
		savebutton.disableProperty().bind(property);
	}

	public BooleanBinding isEmpty() {
		return overview.textProperty().isEqualTo("Overview will appear here");

	}
}

