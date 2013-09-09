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
		boolean isValid = validateInput();
		if(isValid){
			//send off to the controller to start processing input
			this.app.getController().createTask();
			
			//freeze certain fields and buttons
			freezeUI();
		}
	}
	
	
	/*
	 * What happens when the Stop button is clicked
	 */
	private void clickStop(){
		
		//tell the controller to stop working
		this.app.getController().stop();
		
		//reset the UI
		resetUI();
		
	}
	
	
	/*
	 * Update the activity log
	 */
	public void updateActivityLog(String message){
		
	}
	
	
	/*
	 * Clear the chat table
	 */
	private void clearChatTable(){
		
	}
	
	
	/*
	 * Add a row to the chat table
	 */
	private void addToChatTable(){
		
	}
	
	
	/*
	 * Validate user input
	 */
	private boolean validateInput(){
		boolean isError = false;
		
		if(!validateUsername()){
			isError = true;
		}
		
		if(!validatePassword()){
			isError = true;
		}
		
		if(!validateTransferDestination()){
			isError = true;
		}
		
		if(!validateTransferMessage()){
			isError = true;
		}
		
		if(!validateTimeToWait()){
			isError = true;
		}
		
		return isError;
		
		
	}
	
	
	private boolean validateUsername(){
		boolean isValidated = true;
		
		return isValidated;
	}
	
	private boolean validatePassword(){
		boolean isValidated = true;
		
		return isValidated;
	}
	
	private boolean validateDestination(){
		boolean isValidated = true;
		
		return isValidated;
	}
	
	private boolean validateTransferDestination(){
		boolean isValidated = true;
		
		return isValidated;
	}
	
	private boolean validateTransferMessage(){
		boolean isValidated = true;
		
		return isValidated;
	}
	
	private boolean validateTimeToWait(){
		boolean isValidated = true;
		
		return isValidated;
	}
	
	/*
	 * Disable fields and buttons and show the working indicator
	 */
	public void freezeUI(){
		//disable input fields and start button
		username.setDisable(true);
		password.setDisable(true);
		transferDestination.setDisable(true);
		transferMessage.setDisable(true);
		timeToWait.setDisable(true);
		startButton.setDisable(true);
		
		//enable stop button
		stopButton.setDisable(false);
		
		//show the status indicator
		statusIndicator.setVisible(true);
	}
	
	
	/*
	 * Return the UI to its original state
	 */
	public void resetUI(){
		//enable input fields and start button
		username.setDisable(false);
		password.setDisable(false);
		transferDestination.setDisable(false);
		transferMessage.setDisable(false);
		timeToWait.setDisable(false);
		startButton.setDisable(false);
		
		//disable stop button
		stopButton.setDisable(true);
		
		//hide the status indicator
		statusIndicator.setVisible(false);
		
	}
	
}
