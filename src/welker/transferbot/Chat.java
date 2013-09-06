package welker.transferbot;

import java.util.Date;


public class Chat {

	/*
	 * Define properties
	 */
	private String chatID;
	private String guestID;
	private String guestIP;
	private String guestOrigin;
	private String queueName;
	private int waitTime;
	private Date startTime;
	
	//constructor needs to accept a JSON object/array and send it to parseJSON()
	
	
	/*
	 * Parses a JSON object to this object's properties
	 */
	private void parseJSON(){
		
	}
	
	/*
	 * Transfers this message to the specified queue
	 */
	public void transfer(String newQueue){
		
	}
	
}
