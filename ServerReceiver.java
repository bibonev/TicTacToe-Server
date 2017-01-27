import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Boyan Bonve (bib508)
 *	Gets messages from client and puts them in a queue, for another thread to forward to the appropriate client.
 */
public class ServerReceiver extends Thread {
	/**
	 * Fields of the ServerReceiver thread.
	 */
	private String myClientsName;
	private BufferedReader myClient;
	private ClientTable clientTable;
	private Scoreboard scoreBoard;

	/**
	 * @param myClientsName - The client's name.
	 * @param myClient	- Read from the client.
	 * @param clientTable	- Client's table.
	 * @param scoreBoard	- The scoreboard.
	 */
	public ServerReceiver(String myClientsName, BufferedReader myClient, ClientTable clientTable, Scoreboard scoreBoard) {
		this.myClientsName = myClientsName;
		this.myClient = myClient;
		this.clientTable = clientTable;
		this.scoreBoard = scoreBoard;
	}

	/* Start the thread.
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		//Receives messages from the client.
		try {
			while (true) {
				String opponent = myClient.readLine(); //get opponent
				String messageReceive = myClient.readLine(); //get message
				
				//when the answer of a challange is Yes.
				if (messageReceive.equals("Yes")) {
					Message msg = new Message(myClientsName, messageReceive + " " + myClientsName);
					MessageQueue opponentQueue = clientTable.getQueue(opponent);
					if (opponentQueue != null)
						opponentQueue.offer(msg);
					else
						System.err.println("Message for unexistent client " + opponent + ": " + messageReceive);
				}
				//when the client requests for a list with the online users.
				else if (messageReceive.equals("Users")) {
					Message msg = new Message(myClientsName, messageReceive+myClientsName);
					MessageQueue opponentQueue = clientTable.getQueue(myClientsName);
					if (opponentQueue != null)
						opponentQueue.offer(msg);
					else
						System.err.println("Message for unexistent client " + opponent + ": " + messageReceive);
				} 
				//when the client won a game and wants to increase his/her points.
				else if (messageReceive.equals("Increase")) {
					scoreBoard.increase(myClientsName);
				}
				else if (messageReceive.equals("Draw")) {
					Message msg = new Message(myClientsName, messageReceive);
					MessageQueue opponentQueue = clientTable.getQueue(opponent);
					if (opponentQueue != null)
						opponentQueue.offer(msg);
					else
						System.err.println("Message for unexistent client " + opponent + ": " + messageReceive);
				}
				//when the user wants to see the scoreboard
				else if (messageReceive.equals("Scoreboard")) {
					Message msg = new Message(myClientsName, messageReceive);
					MessageQueue opponentQueue = clientTable.getQueue(myClientsName);
					if (opponentQueue != null)
						opponentQueue.offer(msg);
					else
						System.err.println("Message for unexistent client " + opponent + ": " + messageReceive);
				} 
				//when the client had rejected a challange
				else if (messageReceive.equals("No")) {
					Message msg = new Message(myClientsName, messageReceive);
					MessageQueue opponentQueue = clientTable.getQueue(opponent);
					if (opponentQueue != null)
						opponentQueue.offer(msg);
					else
						System.err.println("Message for unexistent client " + opponent + ": " + messageReceive);
				} 
				//when the client interupted the game
				else if (messageReceive.equals("Interupted")) {
					Message msg = new Message(myClientsName, messageReceive);
					MessageQueue opponentQueue = clientTable.getQueue(opponent);
					if (opponentQueue != null)
						opponentQueue.offer(msg);
					else
						System.err.println("Message for unexistent client " + opponent + ": " + messageReceive);
				} 
				//Request a challange, move
				else {
					//checks whether the message is possible to be converted to an integer.
					boolean number = true;
					try {
						@SuppressWarnings("unused")
						int a = Integer.parseInt(messageReceive);
					} catch (NumberFormatException e) {
						number = false;
					}

					//Play a turn, move
					if (opponent != null && number) {
						Message msg = new Message(myClientsName, messageReceive);
						MessageQueue opponentQueue = clientTable.getQueue(opponent);
						if (opponentQueue != null)
							opponentQueue.offer(msg);
						else
							System.err.println("Message for unexistent client " + opponent + ": " + messageReceive);
					} 
					//Sends request
					else if (opponent != null) {
						String question = "Do you want to play?" + myClientsName;
						Message msg = new Message(myClientsName, question);
						MessageQueue opponentQueue = clientTable.getQueue(opponent);
						if (opponentQueue != null) {
							opponentQueue.offer(msg);
						} else {
							System.err.println("Message for unexistent client " + opponent + ": " + question);
						}
					} else {
						myClient.close();
						return;
					}
				}
			}
		} 
		catch (NullPointerException e) {
			System.err.println("Something went wrong with the client " + myClientsName + ".");
			clientTable.remove(myClientsName);
		}
		catch (IOException e) {
			System.err.println("Something went wrong with the client " + myClientsName + ".");
			clientTable.remove(myClientsName);
		}
	}
}
