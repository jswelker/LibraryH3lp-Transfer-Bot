package welker.transferbot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;


public class Chat {

	/*
	 * Define properties
	 */
	private String chatID;
	private String guestID;
	private String guestIP;
	private String guestOrigin;
	private String queueName;
	private Date startTime;
	private Date acceptedTime;
	private long waitTime;
	
	//constructor needs to accept a JSON object/array and send it to parseJSON()
	public Chat(Map json) {
		parseJSON(json);
		
		
	}
	
	
	
	/*
	 * Parses a JSON object to this object's properties
	 */
	private void parseJSON(Map json){
		//get elements from the json object
		this.chatID = json.get("id").toString();
		this.guestID = json.get("guest").toString();
		this.guestIP = json.get("ip").toString();
		this.guestOrigin = json.get("referrer").toString();
		this.queueName = json.get("queue").toString();
		
		try {
			this.startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(json.get("started").toString());
			
			if(json.get("accepted") == null){
				this.acceptedTime = null;
				calculateWaitTime(this.startTime, new Date());
			}else{
				this.acceptedTime  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(json.get("accepted").toString());
				calculateWaitTime(this.startTime, this.acceptedTime);
			}
		} catch (ParseException e) {
			this.waitTime = 0;
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Calculate the wait time from the start time and the accepted time
	 */
	private void calculateWaitTime(Date start, Date end){
		//subtract the two times and convert milliseconds to seconds
		this.waitTime = end.getTime() - start.getTime() * 1000;
	}

	
	/*
	 * Transfers this message to the specified queue using an HTTP POST request
	 */
	public void transfer(String newQueue, String message, String username, String password){
		//create a string for the results message to display to the user.
		String resultsMessage = "";
		
		//send a transfer notification message and get a results message back
		resultsMessage += sendTransferMessage(message, username, password);
		
		//Do the HTTP post request to the LibraryH3lp API to transfer the chat to a new queue.
		//Receive a results message in return.
		resultsMessage += TransferViaAPI(newQueue);
		
	}
	
	
	/*
	 * Send a message to the user about transferring
	 */
	private String sendTransferMessage(String message, String username, String password){
		//create a string for holding the results of the sending activity
		String resultsMessage = "";
		
		//make connection
		Connection con = makeXMPPConnection(username, password);
		
		//send the message
		if(con != null){
			boolean success = sendXMPP(con, message);
			if(success){
				resultsMessage += "\r\nTransfer message successfully send for chat " + this.chatID;
			}else{
				resultsMessage += "\r\nError sending transfer message for chat " + this.chatID;
			}
		}else{
			resultsMessage += "\r\nError making a connection to chat " + this.chatID;
		}
			
		//disconnect
		if( con.isConnected() ){
			con.disconnect();
		}
		
		return resultsMessage;
	}
	
	private Connection makeXMPPConnection(String username, String password){
		// Create the configuration for this new connection
		ConnectionConfiguration config = new ConnectionConfiguration("libraryh3lp.com", 5222);
		config.setCompressionEnabled(true);
		config.setSASLAuthenticationEnabled(true);
		
		// Create the connection and log in
		Connection con = new XMPPConnection(config);
		try {
			con.connect();
			con.login(username, password);
		} catch (XMPPException e) {
			e.printStackTrace();
			con = null;
		}
		
		return con;
	}
	
	
	/*
	 * Send the XMPP message
	 */
	private boolean sendXMPP(Connection con, String messageText){
		
		//create a chat
		ChatManager chatmanager = con.getChatManager();
		org.jivesoftware.smack.Chat chat = chatmanager.createChat(this.guestID, new MessageListener() {
		    public void processMessage(org.jivesoftware.smack.Chat chat, Message message) {
		        //we don't care to do anything with messages we receive
		    }
		});
		
		//create a message
		Message message = new Message();
		message.setBody(messageText);
		message.setProperty("favoriteColor", "green");
		
		//send the message
		try {
		    chat.sendMessage(message);
		    return true;
		}
		catch (XMPPException e) {
		    System.out.println("Error sending message.");
		    e.printStackTrace();
		    return false;
		}
	}
	
	
	/*
	 * Send a POST request to the LibraryH3lp API to transfer the chat to a new queue
	 */
	private String TransferViaAPI(String newQueue){
		String resultsMessage = "";
		
		//create http client
		createHTTPClient();
		
		//do POST
		doPost();
		
		return resultsMessage;
	}
	
	
	public String getChatID() {
		return chatID;
	}

	public String getGuestID() {
		return guestID;
	}

	public String getGuestIP() {
		return guestIP;
	}

	public String getGuestOrigin() {
		return guestOrigin;
	}

	public String getQueueName() {
		return queueName;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public Date getStartTime() {
		return startTime;
	}
	
}
