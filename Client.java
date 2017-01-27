import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Boyan Bonev (bib508)
 *	Run a client to play a Tic-Tac-Toe game.
 */
class Client {

	public static void main(String[] args) {

		// Check correct usage:
		if (args.length != 3) {
			System.err.println("Usage: java Client <user nickname> <port number> <machine name>");
			System.exit(1); // Give up.
		} 

		// Initialize information:
		String nickname = args[0];
		String port = args[1];
		String hostname = args[2];

		
		// Open sockets:
		PrintStream toServer = null;
		BufferedReader fromServer = null;
		Socket server = null;

		try {
			server = new Socket(hostname, Integer.parseInt(port));
			toServer = new PrintStream(server.getOutputStream());
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + hostname);
			System.exit(1); // Give up.
		} catch (IOException e) {
			System.err.println("The server doesn't seem to be running " + e.getMessage());
			System.exit(1); // Give up.
		}
		
		//Initialise objects for getting and setting the opponent and the responses. 
		Opponent opp = new Opponent();
		Respond rsp = new Respond();

		//Initialise the model of the game and the game itself.
		NoughtsCrossesModel model = new NoughtsCrossesModel(new NoughtsCrosses());
		NoughtsCrossesGUI guiGame = new NoughtsCrossesGUI(model, nickname);

		//Initialise the model of the loby and the loby itself.
		LobyModel lobyModel = new LobyModel();
		LobyGUI guiLoby = new LobyGUI(lobyModel, nickname);

		//Initialise the two threads - loby and board.
		Thread threadGame = new Thread(guiGame);
		Thread threadLoby = new Thread(guiLoby);

		// Create two client threads:
		ClientSender sender = new ClientSender(nickname, toServer, model, opp, rsp, threadGame, guiGame, threadLoby,
				guiLoby, lobyModel);
		ClientReceiver receiver = new ClientReceiver(fromServer, model, opp, rsp, lobyModel);

		// Run them in parallel:
		sender.start();
		receiver.start();

		// Wait for them to end and close sockets.
		try {
			sender.join();
			toServer.close();
			receiver.join();
			fromServer.close();
			server.close();
		} catch (IOException e) {
			System.err.println("Something wrong " + e.getMessage());
			System.exit(1); // Give up.
		} catch (InterruptedException e) {
			System.err.println("Unexpected interruption " + e.getMessage());
			System.exit(1); // Give up.
		}
	}
}
