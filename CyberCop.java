
/**
 * 95-712 Homework 3
 * Name: Lakshay Sethi
 * Andrew ID: lsethi
 */

package hw3;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CyberCop extends Application{

	public static final String DEFAULT_PATH = "data"; //folder name where data files are stored
	public static final String DEFAULT_HTML = "/CyberCop.html"; //local HTML
	public static final String APP_TITLE = "Cyber Cop"; //displayed on top of app

	CCView ccView = new CCView();
	CCModel ccModel = new CCModel();

	CaseView caseView; //UI for Add/Modify/Delete menu option

	GridPane cyberCopRoot;
	Stage stage;

	static Case currentCase; //points to the case selected in TableView.

	public static void main(String[] args) {
		launch(args);
	}

	/** start the application and show the opening scene */
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.setTitle("Cyber Cop");
		cyberCopRoot = ccView.setupScreen();  
		setupBindings();
		Scene scene = new Scene(cyberCopRoot, ccView.ccWidth, ccView.ccHeight);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		ccView.webEngine.load(getClass().getResource(DEFAULT_HTML).toExternalForm());
		primaryStage.show();
	}

	/** setupBindings() binds all GUI components to their handlers.
	 * It also binds disableProperty of menu items and text-fields 
	 * with ccView.isFileOpen so that they are enabled as needed
	 */
	void setupBindings() {
		//write your code here
	    
	    //Buttons disabled as given in the HW video demonstrations
	    ccView.closeFileMenuItem.setDisable(true);
	    ccView.addCaseMenuItem.setDisable(true);
	    ccView.modifyCaseMenuItem.setDisable(true);
	    ccView.deleteCaseMenuItem.setDisable(true);

	    ccView.searchButton.setDisable(true);
	    ccView.clearButton.setDisable(true);
	    
	    ccView.titleTextField.setDisable(true);
	    ccView.caseTypeTextField.setDisable(true);
	    ccView.caseNumberTextField.setDisable(true);
	    ccView.yearComboBox.setDisable(true);
	    
	    //HW3 Addition
	    ccView.saveFileMenuItem.setDisable(true);
	    ccView.caseCountChartMenuItem.setDisable(true);
	    
	    //Handler to open as per the user inputs
	    ccView.openFileMenuItem.setOnAction(new OpenMenuItemHandler());
	    ccView.closeFileMenuItem.setOnAction(new CloseMenuItemHandler());
	    ccView.exitMenuItem.setOnAction(new ExitMenuItemHandler());
	    
	    ccView.addCaseMenuItem.setOnAction(new CaseMenuHandler());
	    ccView.modifyCaseMenuItem.setOnAction(new CaseMenuHandler());
	    ccView.deleteCaseMenuItem.setOnAction(new CaseMenuHandler());
	    
	    ccView.searchButton.setOnAction(new SearchButtonHandler());
	    ccView.clearButton.setOnAction(new ClearButtonHandler());
	    
	    //HW3 Addition
	    ccView.saveFileMenuItem.setOnAction(new SaveMenuItemHandler());
	    ccView.caseCountChartMenuItem.setOnAction(new caseChartMenuItemHandler());
	    
	    //Listener to track the current selections
	    ccView.caseTableView.getSelectionModel().selectedItemProperty().addListener((observale, oldValue, newValue) -> {
	        
	        //To check if user has made a selection
	        if(newValue != null) {
	            currentCase = newValue;
	            ccView.titleTextField.setText(currentCase.getCaseTitle());
                ccView.caseTypeTextField.setText(currentCase.getCaseType());
                ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
                ccView.caseNotesTextArea.setText(currentCase.getCaseNotes());
                ccView.yearComboBox.setValue(currentCase.toString());
                
                if (currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) {  //if no link in data
                    URL url = getClass().getClassLoader().getResource(DEFAULT_HTML);  //default html
                    if (url != null) ccView.webEngine.load(url.toExternalForm());
                } else if (currentCase.getCaseLink().toLowerCase().startsWith("http")){  //if external link
                    ccView.webEngine.load(currentCase.getCaseLink());
                } else {
                    URL url = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim());  //local link
                    if (url != null) ccView.webEngine.load(url.toExternalForm());
                }
	        }
	    });

		//The following is some helper code to display web-pages. It is commented out to start with
		//Uncomment it to plug it in your code as needed.  

//		if (currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) {  //if no link in data
//			URL url = getClass().getClassLoader().getResource(DEFAULT_HTML);  //default html
//			if (url != null) ccView.webEngine.load(url.toExternalForm());
//		} else if (currentCase.getCaseLink().toLowerCase().startsWith("http")){  //if external link
//			ccView.webEngine.load(currentCase.getCaseLink());
//		} else {
//			URL url = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim());  //local link
//			if (url != null) ccView.webEngine.load(url.toExternalForm());
//		}
	}
	
	
	class OpenMenuItemHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(DEFAULT_PATH));
            fileChooser.setTitle("Select file");
 
            File userFile = fileChooser.showOpenDialog(stage);
            
            if(userFile != null) {
                
                String userFileStr = userFile.getAbsolutePath();
                ccModel.readCases(userFileStr);
                
                ccView.closeFileMenuItem.setDisable(false);
                ccView.addCaseMenuItem.setDisable(false);
                ccView.modifyCaseMenuItem.setDisable(false);
                ccView.deleteCaseMenuItem.setDisable(false);
                
                ccView.searchButton.setDisable(false);
                ccView.clearButton.setDisable(false);
                
                ccView.titleTextField.setDisable(false);
                ccView.caseTypeTextField.setDisable(false);
                ccView.caseNumberTextField.setDisable(false);
                ccView.yearComboBox.setDisable(false);
                
                ccView.openFileMenuItem.setDisable(true);
                
                //HW3 Addition
                ccView.saveFileMenuItem.setDisable(false);
                ccView.caseCountChartMenuItem.setDisable(false);
                
                //Binding the Table View and setting its values
                ccView.caseTableView.setItems(ccModel.caseList);
                
                
                //Setting the text in fields
                currentCase = ccModel.caseList.get(0);
                ccView.titleTextField.setText(currentCase.getCaseTitle());
                ccView.caseTypeTextField.setText(currentCase.getCaseType());
                ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
                ccView.caseNotesTextArea.setText(currentCase.getCaseNotes());
                ccView.yearComboBox.setValue(currentCase.toString());
                
                ccModel.buildYearMapAndList();
                FXCollections.sort(ccModel.yearList);
                ccView.yearComboBox.setItems(ccModel.yearList);
                
                ccView.caseTableView.getSelectionModel().selectFirst();
                
                if (currentCase.getCaseLink() == null || currentCase.getCaseLink().isBlank()) {  //if no link in data
                    URL url = getClass().getClassLoader().getResource(DEFAULT_HTML);  //default html
                    if (url != null) ccView.webEngine.load(url.toExternalForm());
                } else if (currentCase.getCaseLink().toLowerCase().startsWith("http")){  //if external link
                    ccView.webEngine.load(currentCase.getCaseLink());
                } else {
                    URL url = getClass().getClassLoader().getResource(currentCase.getCaseLink().trim());  //local link
                    if (url != null) ccView.webEngine.load(url.toExternalForm());
                }
                
                //HW 3 new addition to get the case count
                ccView.messageLabel.setText(Integer.toString(ccModel.caseList.size()) + " cases."); 
            }
        }
    }
    
	
    class CloseMenuItemHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

            //Enabling and disabling buttons as per the HW requirement
            ccView.caseTableView.setItems(null);
            ccView.yearComboBox.setItems(null);
            
            ccView.closeFileMenuItem.setDisable(true);
            ccView.addCaseMenuItem.setDisable(true);
            ccView.modifyCaseMenuItem.setDisable(true);
            ccView.deleteCaseMenuItem.setDisable(true);

            ccView.searchButton.setDisable(true);
            ccView.clearButton.setDisable(true);
            
            ccView.titleTextField.setDisable(true);
            ccView.caseTypeTextField.setDisable(true);
            ccView.caseNumberTextField.setDisable(true);
            ccView.yearComboBox.setDisable(true);
            
            ccView.openFileMenuItem.setDisable(false);
            
            //HW3 Addition
            ccView.saveFileMenuItem.setDisable(true);
            ccView.caseCountChartMenuItem.setDisable(true);
            
            ccView.titleTextField.setText("");
            ccView.caseTypeTextField.setText("");
            ccView.caseNumberTextField.setText("");
            ccView.caseNotesTextArea.clear();
            ccView.yearComboBox.setValue("");
            
            ccView.messageLabel.setText("");
            
            ccModel.caseList = FXCollections.observableArrayList();
            ccModel.yearList = FXCollections.observableArrayList();
            
            currentCase = null;
            ccView.webEngine.load(getClass().getResource(DEFAULT_HTML).toExternalForm());
            //Re initializing ccModel to a new CCModel object
            ccModel = new CCModel();
        }
        
    }
    
    
    class ExitMenuItemHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            Platform.exit();
        }
    }
    
    
    class SearchButtonHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            
            String title = ccView.titleTextField.getText().trim();
            String type = ccView.caseTypeTextField.getText().trim();
            String number = ccView.caseNumberTextField.getText().trim();
            String year = ccView.yearComboBox.getValue();
            
            if(title.isEmpty()) {
                title = null;
            }
            
            if(type.isEmpty()) {
                type = null;
            }
            
            if(number.isEmpty()) {
                number = null;
            }
            
            if(year.isEmpty()) {
                year = null;
            }
          
            if(title == null && type == null && number == null && year == null) {
                ccView.caseTableView.setItems(ccModel.caseList);
                currentCase = ccModel.caseList.get(0);
                
                ccView.titleTextField.setText(currentCase.getCaseTitle());
                ccView.caseTypeTextField.setText(currentCase.getCaseType());
                ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
                ccView.caseNotesTextArea.setText(currentCase.getCaseNotes());
                ccView.yearComboBox.setValue(currentCase.toString());
                
                ccView.caseTableView.getSelectionModel().selectFirst();
            }
            
            else {
                ObservableList<Case> searchedCase = (ObservableList<Case>) ccModel.searchCases(title, type, year, number);
                if(searchedCase.size() > 0) {
                    ccView.caseTableView.setItems(searchedCase);
                    ccView.titleTextField.setText(searchedCase.get(0).getCaseTitle());
                    ccView.caseTypeTextField.setText(searchedCase.get(0).getCaseType());
                    ccView.caseNumberTextField.setText(searchedCase.get(0).getCaseNumber());
                    ccView.caseNotesTextArea.setText(searchedCase.get(0).getCaseNotes());
                    ccView.yearComboBox.setValue(searchedCase.get(0).toString());
                    
                    ccView.caseTableView.getSelectionModel().selectFirst();
                }
                
                else {
                    ccView.caseTableView.setItems(null);
                    ccView.titleTextField.setText("");
                    ccView.caseTypeTextField.setText("");
                    ccView.caseNumberTextField.setText("");
                    ccView.caseNotesTextArea.clear();
                    ccView.yearComboBox.setValue("");
                }
            }
        }
    }
    
    
    class ClearButtonHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            
            ccView.titleTextField.setText("");
            ccView.caseTypeTextField.setText("");
            ccView.caseNumberTextField.setText("");
            ccView.yearComboBox.setValue("");
        }   
    }
    
    
    class CaseMenuHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            
            MenuItem menu = (MenuItem) event.getSource();
            
            //When Add Case menu button is pressed
            if(menu.getText().equals("Add case")) {
                caseView = new AddCaseView("Add Case");
                caseView.buildView().show();
                caseView.updateButton.setOnAction(new AddButtonHandler());
                caseView.clearButton.setOnAction(new ClearCaseButtonHandler());
                caseView.closeButton.setOnAction(new CloseCaseButtonHandler());
            }
            
            //When Modify Case menu button is pressed
            else if(menu.getText().equals("Modify case")) {
                caseView = new ModifyCaseView("Modify Case");
                
                String title = currentCase.getCaseTitle();
                String type = currentCase.getCaseType();
                String date = currentCase.getCaseDate();
                String number = currentCase.getCaseNumber();
                String category = currentCase.getCaseCategory();
                String link = currentCase.getCaseLink();
                String notes = currentCase.getCaseNotes();
                
                caseView.titleTextField.setText(title);
                caseView.caseTypeTextField.setText(type);
                caseView.caseDatePicker.setValue(LocalDate.parse(date));
                caseView.caseNumberTextField.setText(number);
                caseView.categoryTextField.setText(category);
                caseView.caseLinkTextField.setText(link);
                caseView.caseNotesTextArea.setText(notes);
                
                caseView.buildView().show();
                
                caseView.updateButton.setOnAction(new ModifyButtonHandler());
                caseView.clearButton.setOnAction(new ClearCaseButtonHandler());
                caseView.closeButton.setOnAction(new CloseCaseButtonHandler());
            }
            
            //When Delete Case menu button is pressed
            else if(menu.getText().equals("Delete case")) {
                caseView = new DeleteCaseView("Delete Case");
                
                String title = currentCase.getCaseTitle();
                String type = currentCase.getCaseType();
                String date = currentCase.getCaseDate();
                String number = currentCase.getCaseNumber();
                String category = currentCase.getCaseCategory();
                String link = currentCase.getCaseLink();
                String notes = currentCase.getCaseNotes();
                
                caseView.titleTextField.setText(title);
                caseView.caseTypeTextField.setText(type);
                caseView.caseDatePicker.setValue(LocalDate.parse(date));
                caseView.caseNumberTextField.setText(number);
                caseView.categoryTextField.setText(category);
                caseView.caseLinkTextField.setText(link);
                caseView.caseNotesTextArea.setText(notes);
                
                caseView.buildView().show();
                
                caseView.updateButton.setOnAction(new DeleteButtonHandler());
                caseView.clearButton.setOnAction(new ClearCaseButtonHandler());
                caseView.closeButton.setOnAction(new CloseCaseButtonHandler());
            }
        }
    }
    
    
    class AddButtonHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            
            String title = caseView.titleTextField.getText().trim();
            String type = caseView.caseTypeTextField.getText().trim();
            String date = caseView.caseDatePicker.getValue().toString();
            String number = caseView.caseNumberTextField.getText().trim();
            String category = caseView.categoryTextField.getText().trim();
            String link = caseView.caseLinkTextField.getText().trim();
            String notes = caseView.caseNotesTextArea.getText().trim();
            
            //HW3 addition
            String message;
            if(date.isBlank() || title.isBlank() || type.isBlank() || number.isBlank()) {
                message = "Case must have date, title, type, and number";
                
                try {
                    throw new DataException(message);
                }
                catch(DataException e) {
                    System.out.println("Caught entry blank in AddButtonHandler");
                    e.showAlert();
                }
            }
            else if(ccModel.caseMap.containsKey(number)) {
                message = "Duplicate case number";
                try {
                    throw new DataException(message);
                }
                catch(DataException e) {
                    System.out.println("Caught Duplicate numbe in AddButtonHandler");
                    e.showAlert();
                }
            }
            else {
                Case newAddCase = new Case(date, title, type, number, link, category, notes);
                ccModel.caseList.add(newAddCase);
                ccView.messageLabel.setText(Integer.toString(ccModel.caseList.size()) + " cases.");
            } 
        }
    }
    
    
    class ModifyButtonHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            
            String title = caseView.titleTextField.getText().trim();
            String type = caseView.caseTypeTextField.getText().trim();
            String date = caseView.caseDatePicker.getValue().toString();
            String number = caseView.caseNumberTextField.getText().trim();
            String category = caseView.categoryTextField.getText().trim();
            String link = caseView.caseLinkTextField.getText().trim();
            String notes = caseView.caseNotesTextArea.getText().trim();
            
            //HW3 addition
            String message;
            if(date.isBlank() || title.isBlank() || type.isBlank() || number.isBlank()) {
                message = "Case must have date, title, type, and number";
                
                try {
                    throw new DataException(message);
                }
                catch(DataException e) {
                    System.out.println("Caught entry blank in ModifyButtonHandler");
                    e.showAlert();
                }
            }
            else if(ccModel.caseMap.containsKey(number)) {
                message = "Duplicate case number";
                try {
                    throw new DataException(message);
                }
                catch(DataException e) {
                    System.out.println("Caught Duplicate number in ModifyButtonHandler");
                    e.showAlert();
                }
            }
            else {
                currentCase.setCaseDate(date);
                currentCase.setCaseTitle(title);
                currentCase.setCaseType(type);
                currentCase.setCaseNumber(number);
                currentCase.setCaseLink(link);
                currentCase.setCaseCategory(category);
                currentCase.setCaseNotes(notes);
                
                ccView.titleTextField.setText(currentCase.getCaseTitle());
                ccView.caseTypeTextField.setText(currentCase.getCaseType());
                ccView.caseNumberTextField.setText(currentCase.getCaseNumber());
                ccView.caseNotesTextArea.setText(currentCase.getCaseNotes());
                ccView.yearComboBox.setValue(currentCase.toString());
            }
        }
    }
    
    
    class DeleteButtonHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            
            int idx = ccView.caseTableView.getSelectionModel().getSelectedIndex();
            
            if (ccModel.caseList.size() > idx) {
                ccModel.caseList.remove(idx);
            }
            
            else if (ccModel.caseList.size() == 0) {
                ccView.caseTableView.getSelectionModel().clearSelection();
            }
        }
    }
    
    //New Handler to clear the Add/Modify/Delete case windows
    class ClearCaseButtonHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            
            caseView.titleTextField.setText("");
            caseView.caseTypeTextField.setText("");
            caseView.caseDatePicker.setValue(LocalDate.now());
            caseView.caseNumberTextField.setText("");
            caseView.categoryTextField.setText("");
            caseView.caseLinkTextField.setText("");
            caseView.caseNotesTextArea.clear();
        }   
    }
    
    //New Handler to close the Add/Modify/Delete case windows
    class CloseCaseButtonHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            caseView.stage.close();
        }   
    }
    
    //HW 3 Addition New Handler to save the table
    class SaveMenuItemHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(DEFAULT_PATH));
            fileChooser.setTitle("Save file");
            
            File userFile = fileChooser.showSaveDialog(stage);
            
            if(userFile != null) {
                String userFileStr = userFile.getAbsolutePath();
                
                if(ccModel.writeCases(userFileStr)) {
                    ccView.messageLabel.setText(userFile.getName().toString() + " saved.");  
                }
            }
        }
    }
    
    //HW 3 Addition New Handler to show chart
    class caseChartMenuItemHandler implements EventHandler <ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            ccView.showChartView(ccModel.yearMap);
        }
    }
}

