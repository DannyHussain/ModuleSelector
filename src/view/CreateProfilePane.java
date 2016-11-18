package view;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import model.Course;
import model.Name;
import model.StudentProfile;

/**
 * The CreateProfilePane is the first tab, which is used to
 * create a profile with the users information and generates 
 * the mandatory modules for the selected course
 */

public class CreateProfilePane extends GridPane {

	//Fields
	private ComboBox<Course> courses;
	private TextField txtPNumber, txtFirstname, txtSurname, txtEmail;
	private DatePicker dp = new DatePicker();
	private LocalDate date;
	private Button createprofilebutton;

	public CreateProfilePane() {

		//Layout to set height of the vertical and width of horizontal gaps between rows and columns
		this.setStyle("-fx-background-color:#FFE6E6");
		this.setPadding(new Insets(80, 80, 80, 80));
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);

		//initialise the ComboBox	
		courses = new ComboBox<Course>();


		//Labels and their styles

		Label lblCourse = new Label("Select course: ");
		lblCourse.setTextFill(Color.web("#4C4C4C"));
		lblCourse.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

		Label lblPNumber = new Label("Input P Number: ");
		lblPNumber.setTextFill(Color.web("#4C4C4C"));
		lblPNumber.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

		Label lblFirstname = new Label("First name: ");
		lblFirstname.setTextFill(Color.web("#4C4C4C"));
		lblFirstname.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

		Label lblSurname = new Label("Input surname: ");
		lblSurname.setTextFill(Color.web("#4C4C4C"));
		lblSurname.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

		Label lblEmail = new Label("Input Email: ");
		lblEmail.setTextFill(Color.web("#4C4C4C"));
		lblEmail.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

		Label lblDate = new Label("Input date: ");
		lblDate.setTextFill(Color.web("#4C4C4C"));
		lblDate.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

		//Text fields
		txtFirstname = new TextField();
		txtSurname = new TextField();
		txtPNumber = new TextField();
		txtEmail = new TextField();

		//Date
		dp.setOnAction(event -> { 
			date = dp.getValue();
		});


		//initialise the create profile button
		createprofilebutton = new Button("Create Profile");

		//add the controls and the labels to container
		this.add(lblCourse, 0, 0);
		this.add(courses, 1, 0);

		this.add(lblPNumber, 0, 1);
		this.add(txtPNumber, 1, 1);

		this.add(lblFirstname, 0, 2);
		this.add(txtFirstname, 1, 2);

		this.add(lblSurname, 0, 3);
		this.add(txtSurname, 1, 3);

		this.add(lblEmail, 0, 4);
		this.add(txtEmail, 1, 4);

		this.add(lblDate, 0, 5);
		this.add(dp, 1, 5);

		this.add(new HBox(), 0, 6);
		this.add(createprofilebutton, 1, 6);

	}


	//Handler
	public void addCreateProfileHandler(EventHandler<ActionEvent> handler) {
		createprofilebutton.setOnAction(handler);
	}


	//Methods
	//method populates the combo box with courses
	public void populateComboBox(Course[] mcourses) {
		courses.getItems().addAll(mcourses);
		courses.getSelectionModel().select(0);
	}

	//Returns the selected course
	public Course getCourse(){

		return courses.getSelectionModel().getSelectedItem();
	}

	//Checks if the PNumber field input is valid, if not displays an error with the reason
	public String getPNumber(){
		if(txtPNumber.getText().charAt(0) != 'p' && txtPNumber.getText().charAt(0) != 'P'){
			alertDialogBuilder(AlertType.ERROR, "Error", null, "The PNumber has to start with the letter P");
			txtPNumber.setText("");
		}

		else if(txtPNumber.getText().length() < 9 || txtPNumber.getText().length() > 10 ){
			alertDialogBuilder(AlertType.ERROR, "Error", null, "The PNumber can not be less than 9 characters or greater"
					+ " than 10 characters please recheck your entry!");
			txtPNumber.setText("");
		}

		return txtPNumber.getText();
	}

	////Checks if the firstname and surname fields have valid input, if not displays an error with reason
	public Name getStudentName(){
		if(!txtFirstname.getText().matches("[A-Za-z\\s]+") || !txtSurname.getText().matches("[A-Za-z\\s]+" )){
			alertDialogBuilder(AlertType.ERROR, "Error", null, "The firstname or surname field contains invalid characters"
					+ " please recheck your entry!");
			txtFirstname.setText("");
			txtSurname.setText("");
		}
		else if(txtFirstname.getText().length() < 2 || txtSurname.getText().length() < 2 ){
			alertDialogBuilder(AlertType.ERROR, "Error", null, "The firstname or surname field is too short"
					+ " please recheck your entry!");
			txtFirstname.setText("");
			txtSurname.setText("");
		}
		Name studentName = new Name(txtFirstname.getText(),txtSurname.getText());
		return studentName;
	}

	//Checks if the email entered is valid, if not displays an error with reason
	public String getEmail(){
		if(!txtEmail.getText().contains("@")){
			alertDialogBuilder(AlertType.ERROR, "Error", null, "Please enter a valid email address");
			txtEmail.setText("");
		}
		else if(txtEmail.getText().length() < 10) {
			alertDialogBuilder(AlertType.ERROR, "Error", null, "email address entered is too short");
			txtEmail.setText("");
		}
		return txtEmail.getText();
	}

	public LocalDate getDate(){
		return date;
	}

	//This method will load the previously saved profile and populate data automatically
	public void LoadProfile(StudentProfile x, Name n){

		txtFirstname.setText(n.getFirstName());
		txtSurname.setText(n.getFamilyName());
		txtPNumber.setText(x.getpNumber());
		txtEmail.setText(x.getEmail());
		dp.setValue(x.getDate());
		courses.setValue(x.getCourse());

	}

	//Binding

	public void CreateProfileButtonDisableBind(BooleanBinding property) {
		createprofilebutton.disableProperty().bind(property);
	}

	public BooleanBinding isEmpty() {
		return txtFirstname.textProperty().isEmpty().or(txtSurname.textProperty().isEmpty()
				.or(txtPNumber.textProperty().isEmpty())
				.or(txtEmail.textProperty().isEmpty()).or(dp.valueProperty().isNull()));
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

