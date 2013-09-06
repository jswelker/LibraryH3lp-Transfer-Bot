package welker.transferbot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UI {

	/*
	 * Define properties and get FXML objects
	 */
	@FXML private Button startButton;
	@FXML private Button stopButton;
	@FXML private TextField username;
	@FXML private TextField password;
	@FXML private TextField timeToWait;
	@FXML private TextField transferDestination;
	@FXML private TextField transferMessage;
	@FXML private TextArea activityLog;
	@FXML private ProgressIndicator statusIndicator;
	@FXML private TableView chatTable;
	@FXML private TableColumn chatIDColumn;
	@FXML private TableColumn guestIDColumn;
	@FXML private TableColumn guestIPColumn;
	@FXML private TableColumn originColumn;
	@FXML private TableColumn queueColumn;
	@FXML private TableColumn startTimeColumn;
	@FXML private TableColumn waitColumn;
	
	private MainApp app;
	
	public UI(MainApp mainApp) {

		this.app = mainApp;
	}

	/*
	 * Set events for the UI
	 */
	private void createEvents(){
		
	}
	
	/*
	 * What happens when the Start button is clicked
	 */
	private void clickStart(){
		
	}
	
	
	/*
	 * What happens when the Stop button is clicked
	 */
	private void clickStop(){
		
	}
	
	
	/*
	 * Update the activity log
	 */
	private void updateActivityLog(String message){
		
	}
	
	
	/*
	 * Clear the chat table
	 */
	public void clearChatTable(){
		
	}
	
	
	/*
	 * Add a row to the chat table
	 */
	public void addToChatTable(){
		
	}
	
	
	/*
	 * Validate user input
	 */
	public void validateInput(){
		
	}
	
}
