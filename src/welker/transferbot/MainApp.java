package welker.transferbot;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private VBox rootLayout;
	
	private Controller controller;
	private UI ui;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("LibraryH3lp Transfer Bot");
		this.primaryStage.setResizable(false);
		
		//load the FXML layout
		try{
			//load the root layout from the fxml file
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("MainUI.fxml"));
			
			//create the controller class for handling interface events
			this.ui = new UI(this);
			loader.setController(ui);
			
			//show the fxml
			rootLayout = (VBox) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(IOException e){
			//throw exception if load fails
			e.printStackTrace();
		}finally{
			//create the process controller class for handling the logic of processing the data
			this.controller = new Controller(this.ui);
		}
	}
	
	public Controller getController(){
		return this.controller;
	}
	
}
