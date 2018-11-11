package startup;

import java.io.IOException;

import client.net.Client;
import server.net.Server;

/**
 * Starts the server thread as well as three client threads
 */
public class Main {

	public static void main(String[] args)
	{

		new Thread(new Server()).start();
	
		for(int i = 0; i<3; i++)
			try {
				new Thread(new Client(i)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}

}
