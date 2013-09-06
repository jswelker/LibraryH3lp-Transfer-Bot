package welker.transferbot;

import java.util.ArrayList;

public class Controller {
	
	/*
	 * Define properties
	 */
	private boolean isWorking;
	
	private UI ui;
	
	public Controller(UI ui) {

		this.ui = ui;
	}

	/*
	 * Main method that calls the commands to fetch chats, transfer them, and write to the UI
	 */
	private void processChats(){
		//define an array of Chat objects
		ArrayList<Chat> chats = new ArrayList();
		
		//try to fetch chats to fill our Chat array
		
		chats = fetchChats();
		transferChats(chats);
		updateUI(chats);
	}
	
	private ArrayList<Chat> fetchChats(){
		//define an array of Chat objects
		ArrayList<Chat> chats = new ArrayList();
		
		//get remote JSON
		
		
		//loop through JSON and return a Chat object
		//send each JSON line to the constructor of the Chat object
		
		return chats;
	}
	
	/*
	 * Loops through Chat objects and determines whether its wait time is long enough to be transferred. If so, it calls
	 * Chat.transfer().
	 */
	private void transferChats(ArrayList<Chat> chats){
		
		
		
	}
	
	/*
	 * Updates the UI regarding transferred and outstanding waiting chats. Also creates special "no chats found" message.
	 */
	private void updateUI( ArrayList<Chat> chats ){
		
	}
	
	
	
	
}
