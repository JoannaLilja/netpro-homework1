package client.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import client.controller.Controller;
import shared.GameData;


public class Client implements Runnable
{
	
	Controller contr;
	private int id;
	private String serverName = "localhost";
	private int serverPort = 8081;
	Socket socket;
	
	ObjectOutputStream toServer;
	ObjectInputStream fromServer;
	

	

	public Client(int id) throws IOException
	{
		this.id = id;
		
		this.socket = new Socket(serverName, serverPort);


	}
	

    public void run() {

		contr = new Controller(this, id);
		
		try
		{
			sendRequest(".start");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

    }
    
    
    public void sendRequest(String request) throws IOException
    {
    	
    		GameData gameData = null;
    		
    		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream ();
    		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream );
    		
    		
    		objectOutputStream.writeObject(request); 
    		objectOutputStream.flush();
    		objectOutputStream.close();
    		
    		byte[] byteArr = byteOutputStream.toByteArray();
    		int length = byteArr.length;
    		
	    	
    		toServer = new ObjectOutputStream(socket.getOutputStream());
    		
			toServer.write(length);
			toServer.write(byteArr);
			toServer.flush();
		
			
    		fromServer = new ObjectInputStream(socket.getInputStream());
    		
    		
    		
    		length = fromServer.read();
    		
    		byteArr = new byte [length];
    		for(int i = 0; i < length; i++)
    			byteArr[i] = fromServer.readByte();
    			
    				
    		ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(byteArr));
    			
    		try {
				gameData = (GameData) objIn.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		objIn.close();		
			
			contr.updateInfo(gameData);
		
			
			
			
			/*
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String str;	
			while (!(str = fromServer.readLine()).equals("stop"))
			{
				serverResponse.add(str);
			}

			contr.updateInfo(serverResponse);
			*/
       
    }
    
	/*public void guess(String guess) throws IOException
	{
		
			String str;	
			boolean doneListening = false;
			
    		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
    		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream );

			LinkedList<String> serverResponse = new LinkedList<String>();	
			
			objectOutputStream.writeObject(guess); 
    		objectOutputStream.flush();
    		objectOutputStream.close();
    		
    		byte[] byteArr = byteOutputStream.toByteArray();
    		int length = byteArr.length;
    		
			
			toServer.writeInt(length);
			toServer.write(byteArr);
			toServer.flush();

			
			while(doneListening == false)
			{
				str = fromServer.readLine();
				if(str!= null && !str.equals("stop"))
					serverResponse.add(str);
				if (str!= null && str.equals("stop"))
					doneListening = true;
			}			
			
			contr.updateInfo(serverResponse);
        
	}*/

}