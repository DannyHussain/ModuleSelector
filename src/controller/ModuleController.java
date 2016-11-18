
package controller;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Course;
import model.Module;
import model.Name;
import model.StudentProfile;
import view.CreateProfilePane;
import view.ModuleMenuBar;
import view.ModuleRootPane;
import view.OverviewSelectionPane;
import view.SelectModulesPane;

public class ModuleController {

	//Fields
	private ModuleRootPane view;
	private CreateProfilePane cps;
	private SelectModulesPane sm;
	private OverviewSelectionPane os;
	private StudentProfile model;
	private ModuleMenuBar mb;


	public ModuleController(ModuleRootPane view, StudentProfile model) {

		//initialise the model and view fields
		this.model = model;
		this.view = view;
		cps = view.getCreateProfilePane();
		sm = view.getSelectModulesPane();
		os = view.getOverviewSelectionPane();
		mb = view.getMenuBar();

		//populate the ComboBox in view by calling method to setup and get courses
		cps.populateComboBox(setupAndGetCourses());

		//attach the event handlers to view
		this.attachEventHandlers();

		//attach bindings within the view
		this.attachBindings();
	}

	private void attachEventHandlers() {

		//attaching event handlers as inner classes
		cps.addCreateProfileHandler(new CreateProfileHandler());
		sm.addResetHandler(new ResetHandler());
		sm.addAddHandler((new AddHandler()));
		sm.addRemoveHandler(new RemoveHandler());
		sm.addSubmitHandler(new SubmitHandler());
		os.addSaveOverviewHandler(new SaveOverviewHandler());
		mb.addSaveProfileHandler(new SaveProfileHandler());
		mb.addLoadProfileHandler(new LoadProfileHandler());
		mb.addAboutHandler(new AboutHandler());
		mb.addExitHandler(new ExitHandler());

	}

	//Attach the bindings
	private void attachBindings() {
		cps.CreateProfileButtonDisableBind(cps.isEmpty());
		sm.SubmitButtonDisableBind(sm.isNotFull());
		sm.AddButtonDisableBind(sm.isFull());
		os.SaveOverviewButtonDisableBind(os.isEmpty());
	}

	//CreateProfileHandler, that manages the actions for the CreateProfilePane
	private class CreateProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			model.setCourse(cps.getCourse());
			model.setpNumber(cps.getPNumber());
			model.setStudentName(cps.getStudentName());
			model.setEmail(cps.getEmail());
			model.setDate(cps.getDate());

			//Only populate lists if no errors are thrown
			if(cps.isEmpty().getValue() == false){
				sm.populateModules(model.getCourse());
				view.changeTab(1);
			}

		}
	}

	//ResetHandler, simply resets the unselected and selected modules back to their normal state
	private class ResetHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			sm.resetModules();
			
		}
	}

	//AddHandler, used to add modules from the unselected list to the selected list
	private class AddHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {	

			sm.addSelectedModule();

		}
	}

	//RemoveHandler, used to remove modules from the selected list back to the unselected list
	private class RemoveHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {	

			sm.removeSelectedModule();

		}
	}

	//SubmitHandler, used to submit the modules and then gives an overview of the details entered
	private class SubmitHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {	

			sm.getContents().forEach(modules -> model.addSelectedModule(modules));
			os.setResults(getOverview(model.toString()));
			view.changeTab(2);
			
		}
	}

	//SaveOverviewHandler, used to save the overview to a text file
	private class SaveOverviewHandler implements EventHandler<ActionEvent> {		
		public void handle(ActionEvent e) {	
			
			try {
				os.saveProfile();
				alertDialogBuilder(AlertType.INFORMATION, "Success", "Overview Successfully Saved!", null);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	//SaveProfileHandler, used to save the profile to binary format
	private class SaveProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {	
	
			try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("SavedProfile.dat"));) {
				if(model.getSelectedModules() != null){
				out.writeObject(model);
				out.close();
				alertDialogBuilder(AlertType.INFORMATION, "SUCCESS!", "Save successful", "Profile has been saved as SavedProfile.dat");
				}
			}
			catch (IOException ioExcep){
				System.out.println(ioExcep);
			}

		}
	}

	//LoadProfileHandler, loads the previously saved file and restores the previous view
	private class LoadProfileHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {

			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("SavedProfile.dat"));) {

				model = (StudentProfile) ois.readObject();	

				alertDialogBuilder(AlertType.INFORMATION, "SUCCESS!", "Load successful", "Profile has been load from SavedProfile.dat");
			}
			catch (IOException ioExcep){
				System.out.println("There has been an error loading your file");
			}
			catch (ClassNotFoundException c) {
				System.out.println("The class has not been found");
			}

			sm.clearLists();
			model.getSelectedModules().forEach(sm.getContents()::add);

			sm.loadUnselectedModules(cps.getCourse());
			model.getSelectedModules().forEach(n -> sm.removeUnselectedModules(n));

			sm.getContents().forEach(x -> model.addSelectedModule(x));
			os.setResults(getOverview(model.toString()));

			cps.LoadProfile(model, model.getStudentName());


		}
	}

	//AboutHandler, simply displays information about the app 
	private class AboutHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {

			alertDialogBuilder(AlertType.INFORMATION, "About", null, "The Final Year Module Chooser, was offically completed on the"
					+ " 02/04/2016, it is an app that allows DMU students to choose their final year modules, the app was created by Danish Hussain");

		}
	}

	//ExitHandler, used to gracefully close down the app window using the exit button
	private class ExitHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {

			System.exit(0);

		}
	}

	//Method used to populate the unselected and selected lists with modules
	private Course[] setupAndGetCourses() {
		Module ctec3903 = new Module("CTEC3903", "Software Development Methods", 15, true);
		Module imat3451 = new Module("IMAT3451", "Computing Project", 30, true);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigerous Systems", 15, true);
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigerous Systems", 15, false);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false);
		Module ctec3426 = new Module("CTEC3426", "Telematics", 15, false);
		Module ctec3604 = new Module("CTEC3604", "Multi-service Networks", 30, false);
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false);
		Module imat3404 = new Module("IMAT3404", "Mobile Robotics", 15, false);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false);
		Module imat3429 = new Module("IMAT3429", "Privacy and Data Protection", 15, false);
		Module imat3902 = new Module("IMAT3902", "Computing Ethics", 15, false);
		Module imat3426_CompSci = new Module("IMAT3426", "Information Systems Strategy and Services", 30, false);
		Course compSci = new Course("Computer Science");
		compSci.addModule(ctec3903);
		compSci.addModule(imat3451);
		compSci.addModule(ctec3902_CompSci);
		compSci.addModule(ctec3110);
		compSci.addModule(ctec3426);
		compSci.addModule(ctec3604);
		compSci.addModule(ctec3410);
		compSci.addModule(ctec3904);
		compSci.addModule(ctec3905);
		compSci.addModule(imat3410);
		compSci.addModule(imat3404);
		compSci.addModule(imat3406);
		compSci.addModule(imat3429);
		compSci.addModule(imat3902);
		compSci.addModule(imat3426_CompSci);
		Course softEng = new Course("Software Engineering");
		softEng.addModule(ctec3903);
		softEng.addModule(imat3451);
		softEng.addModule(ctec3902_SoftEng);
		softEng.addModule(ctec3110);
		softEng.addModule(ctec3426);
		softEng.addModule(ctec3604);
		softEng.addModule(ctec3410);
		softEng.addModule(ctec3904);
		softEng.addModule(ctec3905);
		softEng.addModule(imat3410);
		softEng.addModule(imat3404);
		softEng.addModule(imat3406);
		softEng.addModule(imat3429);
		softEng.addModule(imat3902);
		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;
		return courses;
	}

	//Dialog builder which is used to display any errors with validation checks
	private void alertDialogBuilder(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	//Method to display the overview to a desirable view

	public String getOverview(String x){
		Name n = model.getStudentName();	
		ArrayList<String> a = new ArrayList<String>();

		for (Module t : model.getSelectedModules()){

			a.add("Module Code: " + t.getModuleCode() + " Credits: " + t.getCredits() +  "\nMandatory on course? : " + t.isMandatory() + "\n" 
					+ "Module Name: " + t.getModuleName() + "\n\n");

		}
		String replace = a.toString()
				.replace(", ","")  
				.replace("[","") 
				.replace("]","");

		x = "Name: " + n.getFullName() + "\nP-Number: " + model.getpNumber() +
				"\nEmail: " + model.getEmail() + "\nDate: " + model.getDate() + "\nCourse: " + model.getCourse() + "\n\nSelected Modules: \n\n" + replace; 


		return x;
	}

}
