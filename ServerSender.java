import java.io.PrintStream;
import java.util.ArrayList;

/**
 * @author Boyan Bonev (bib508)
 * Continuously reads from message queue for a particular client, forwarding to the client.
 */
public class ServerSender extends Thread {
	private MessageQueue queue;
	private PrintStream toClient;
	private ClientTable table;
	private Scoreboard scoreBoard;

	/**
	 * @param queue			- Message queue, full with messages/requsts.
	 * @param toClient		- Stream which will send the messages.
	 * @param table			- Table with users and their message queues.
	 * @param scoreBoard	- Scoreboard with the points of the users.
	 */
	public ServerSender(MessageQueue queue, PrintStream toClient, ClientTable table, Scoreboard scoreBoard) {
		this.queue = queue;
		this.toClient = toClient;
		this.table = table;
		this.scoreBoard = scoreBoard;
	}

	/* Start the thread.
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		//Sending...
		while (true) {
			//Get the message to send and distinguish its type:
			Message msg = queue.take();
			//Sends positive response to the challanger.
			if (msg.toString().contains("Yes")) {
				String opponent = msg.toString().substring(4);
				toClient.println(msg);
				toClient.println(opponent);
			} 
			//Sends the scoreboard to the client.
			else if (msg.toString().equals("Scoreboard")) {
				toClient.println("Scoreboard");
				ArrayList<String> board = scoreBoard.get();
				String sendBoard = board.toString();
				sendBoard = sendBoard.substring(1, sendBoard.length()-1);
				toClient.println(sendBoard);
			} 
			//Sends the online users to the client.
			else if (msg.toString().contains("Users")) {
				toClient.println("Users");
				String except = msg.toString().substring(5);
				String users = table.getUsers(except);
				toClient.println(users);
			} 
			//Sends "no" as an answer to the challanger.
			else if (msg.toString().equals("No")) {
				toClient.println(msg);
			} 
			//Sends "Draw" if the game ended with a draw.
			else if (msg.toString().equals("Draw")) {
				toClient.println(msg);
			}
			//Sends request for playing to the opponent.
			else if (msg.toString().contains("Do you want to play?")) {
				String opponent = msg.toString().substring(20);
				toClient.println(msg);
				toClient.println(opponent);
			} 
			//Sends a move.
			else {
				toClient.println(msg);
				toClient.println("Already given.");
			}
		}
	}
}
