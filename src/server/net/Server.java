package server.net;


import java.net.Socket;

import java.net.ServerSocket;
import java.io.IOException;

public class Server implements Runnable
{
	
	private static final int LINGER_TIME = 50000;
	private int portNo = 8081;
	

	public void run()
	{

		try
        {

        	ServerSocket listeningSocket = new ServerSocket(portNo);
        	
        	while (true)
        	{
        		
        		Socket clientSocket = listeningSocket.accept();
        		        		
        		clientSocket.setSoLinger(true, LINGER_TIME);            	
        		
        		Thread handler;

        		handler = new Thread(new RequestHandler(clientSocket));
        		handler.setPriority(Thread.MAX_PRIORITY);
        		handler.start();

        	}
        	
        } catch (IOException e)
        {
        	e.printStackTrace();
        }
    }
    



}
