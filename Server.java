// Usage:
//        java Server
//
// There is no provision for ending the server gracefully.  It will
// end if (and only if) something exceptional happens.


import java.net.*;
import java.io.*;

public class Server {
	

   public static void main(String [] args) {

   
	if (args.length != 1) {
      System.err.println("Usage: java Server port");
      System.exit(1); // Give up.
    }
	
	String port = args[0];
	
    // This will be shared by the server threads:
    ClientTable clientTable = new ClientTable();

    // Open a server socket:
    ServerSocket serverSocket = null;
    
    //Scoreboard
    Scoreboard scoreBoard = new Scoreboard();

    // We must try because it may fail with a checked exception:
    try {
      serverSocket = new ServerSocket(Integer.parseInt(port));
    } 
    catch (IOException e) {
      System.err.println("Couldn't listen on port " + Integer.parseInt(port));
      System.exit(1); // Give up.
    }

    // Good. We succeeded. But we must try again for the same reason:
    try { 
      // We loop for ever, as servers usually do:

      while (true) {
        // Listen to the socket, accepting connections from new clients:
        Socket socket = serverSocket.accept();

        // This is so that we can use readLine():
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream toClient = new PrintStream(socket.getOutputStream());
        // We ask the client what its name is:
        String clientName = fromClient.readLine();
       
        int i = 1;
        boolean exist = false;
    	while (clientTable.getQueue(clientName) != null) {
    		clientName += i;
    		i++;
    		exist = true;
    	}
        // For debugging:
    	if (exist) {
	    	toClient.println("Exist" + clientName);
    	}
    	
    	System.out.println(clientName + " connected");
        // We add the client to the table:
        clientTable.add(clientName);
        scoreBoard.add(clientName);
        
        // We create and start a new thread to read from the client:
        (new ServerReceiver(clientName, fromClient, clientTable, scoreBoard)).start();

        // We create and start a new thread to write to the client:
        (new ServerSender(clientTable.getQueue(clientName), toClient, clientTable, scoreBoard)).start();
      }
    } 
    catch (IOException e) {
      // Lazy approach:
      System.err.println("IO error " + e.getMessage());
      // A more sophisticated approach could try to establish a new
      // connection. But this is beyond this simple exercise.
    }
  }
  
}
