package welker.transferbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import javafx.concurrent.Task;

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
	 * Creates a new daemon thread to run the main pollChats method. The separate thread allows the UI
	 * to remain responsive when the controller is busy.
	 */
	public void createTask(){
		
		this.isWorking = true;
		
		//create a thread for this activity and then start running it
		class ThreadedTask extends Task{
			@Override
			protected Object call() throws Exception {
				pollChats();
				return null;
			}
		}
		//create the thread and set it as a daemon so that it closes when the main thread closes
		Thread thread = new Thread(new ThreadedTask());
		thread.setDaemon(true);
		thread.start();
	}
	
	
	/*
	 * Main method that calls the commands to fetch chats, transfer them, and write to the UI
	 */
	private void pollChats(){
		while(isWorking){
			//define an array of Chat objects
			ArrayList<Chat> chats = new ArrayList();
			
			//try to fetch chats to fill our Chat array
			chats = fetchChats();
			transferChats(chats);
			updateUI(chats);
		}
	}
	
	
	private ArrayList<Chat> fetchChats(){
		//define an array of Chat objects
		ArrayList<Chat> chats = new ArrayList();
		
		//get remote JSON
		HttpResponse response = doHttpRequest();
		
		//loop through JSON and return a Chat object
		//send each JSON line to the constructor of the Chat object
		if(response.getStatusLine().getStatusCode() == 200){
			try{
				//turn it into a string
				InputStream stream = response.getEntity().getContent();
				
				//turn the string into an array, casting the JSONArray to a List
				List jsonList = (List)JSONValue.parse(convertStreamToString(stream));
				
				//loop through the list
				for( Iterator<Object> i = jsonList.iterator(); i.hasNext(); ){
					//cast the JSONObject to a Map
					Map json = (Map)i.next();
					
					//create a new Chat object and put it in the main ArrayList
					Chat chat = new Chat(json);
					chats.add(chat);
				}
				
				
			}catch(ClientProtocolException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		return chats;
	}
	
	private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
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
	
	
	/*
	 * This method allows the UI to tell the controller when the Stop command has been issued.
	 */
	public void stop(){
		this.isWorking = false;
	}
	
	/*
	 * Do the HTTP request to get the chat data
	 */
	public HttpResponse doHttpRequest(){
		HttpClient client = null;
		HttpResponse response = null;
		try{
			//create an SSLContext object to ignore unrecognized SSL certificates
			SSLContext context = SSLContext.getInstance("SSL");
			context.init(null, new TrustManager[] { new X509TrustManager() {
	            public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	            }

	            public void checkClientTrusted(X509Certificate[] certs,
	                            String authType) {
	            }

	            public void checkServerTrusted(X509Certificate[] certs,
	                            String authType) {
	            }
			} }, new SecureRandom());
			SSLSocketFactory sf = new SSLSocketFactory(context);
			Scheme httpsScheme = new Scheme("https", 443, sf);
			SchemeRegistry sr = new SchemeRegistry();
			sr.register(httpsScheme);
			ClientConnectionManager cm = new SingleClientConnManager(sr);
			
			//create an HttpClient object
			System.out.println("Creating HttpClient...");
			client = new DefaultHttpClient(cm);
			System.out.println("Setting HttpClient params...");
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
			
			System.out.println("Setting URI..");
			Date date = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
			URI uri = new URI("https://us.libraryh3lp.com/2011-12-03/conversations/"+ ft.format(date) +"?format=json");
			System.out.println("Setting HttpGet...");
			//make an HTTP HEAD request to the url
			HttpGet get = new HttpGet();
			get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
			get.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
			get.setURI(uri);
			response = client.execute(get);
			System.out.println("Received response.");
			//check the results and save them to variables
			
		}catch(IOException e){
			e.printStackTrace();
			ui.updateActivityLog("Failed to fetch chat data from LibraryH3lp.");
			return null;
		}catch(KeyManagementException e){
			e.printStackTrace();
			ui.updateActivityLog("Failed to fetch chat data from LibraryH3lp.");
			return null;
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
			ui.updateActivityLog("Failed to fetch chat data from LibraryH3lp.");
			return null;
		}catch(URISyntaxException e){
			e.printStackTrace();
			ui.updateActivityLog("Failed to fetch chat data from LibraryH3lp.");
			return null;
		}finally{
			client.getConnectionManager().shutdown();
			return response;
		}
	}
}
