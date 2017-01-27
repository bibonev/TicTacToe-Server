import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

/**
 * @author Boyan Bonev (bib508)
 * Gets messages from other clients via the server (by the ServerSender thread).
 */
public class ClientReceiver extends Thread {

	/**
	 * Fields of the ClientReceiver thread.
	 */
	private BufferedReader fromServer;
	private NoughtsCrossesModel modelGame;
	private Opponent opponent;
	private Respond respond;
	private LobyModel lobyModel;

	/**
	 * @param fromServer	- Buffer which reads from the server.
	 * @param modelGame	- The model of the game.
	 * @param opponent	- Set the opponent to the client.
	 * @param respond	- Get and set a respond.
	 * @param lobyModel	- The model of the loby.
	 */
	ClientReceiver(BufferedReader fromServer, NoughtsCrossesModel modelGame, Opponent opponent, Respond respond, LobyModel lobyModel) {
		this.fromServer = fromServer;
		this.modelGame = modelGame;
		this.opponent = opponent;
		this.respond = respond;
		this.lobyModel = lobyModel;
	}

	/* Start the thread.
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		// Print to the user whatever we get from the server:
		try {
			while (true) {
				//Read the "type" message from the server.
				String receive = fromServer.readLine();
				//When there is such username.
				if (receive.contains("Exist")) {
					infoBoxMessage("Your username has been set to: " + receive.substring(5), "Username already exist!");
				}
				//When the challange has been accepted.
				else if (receive.contains("Yes")) {
					respond.setRespond("Answer - Yes");
					String opponentString = fromServer.readLine();
					opponent.setOpponent(opponentString);
					lobyModel.setConnection(opponentString);
				}
				//When the user had required the list of online users.
				else if (receive.equals("Users")) {
					String users = fromServer.readLine();
					ArrayList<String> usersList = new ArrayList<String>(Arrays.asList(users.split(", ")));
					lobyModel.setUsers(usersList);
				}
				//When the user had required the scoreboard from the server.
				else if (receive.equals("Scoreboard")) {
					String board = fromServer.readLine();
					ArrayList<String> boardList = new ArrayList<String>(Arrays.asList(board.split(", ")));
					lobyModel.setUsers(boardList);
				} 
				//When the challange has been rejected.
				else if (receive.equals("No")) {
					lobyModel.setConnection(null);
					infoBoxMessage("The opponent rejected the request. (he/she might be in another game)", "Answer");
				} 
				//When the game ended with a draw
				else if (receive.equals("Draw")) {
					infoBoxMessage("You end your game with a draw", "Draw!");
					modelGame.setWorking(false);
				}
				//When the game has been interupted by the opponent.
				else if (receive.equals("Interupted")) {
					modelGame.setWorking(false);
					//No point of geting the opponent, but its better to read everything you receive.
					@SuppressWarnings("unused")
					String interupOpp = fromServer.readLine(); 
				} 
				// Challange, Move, Answer
				else {
					//Set the opponent if its not been given before that.
					String opponentString = fromServer.readLine();
					if (!modelGame.getWorking()) {
						if (!opponentString.equals("Already given.")) {
							this.opponent.setOpponent(opponentString);
						} 
					}
					
					//Receives a move
					String question = "Do you want to play?";
					if (receive != null && !receive.contains(question)) {
						try {
							modelGame.turn(Character.getNumericValue(receive.charAt(0)) - 1,
									Character.getNumericValue(receive.charAt(1)) - 1);
							//if, as a result of that move, there is a winner, this user lost
							if(modelGame.whoWon()!= 0) {
								infoBoxMessage("You lost", "Result!"); //pop-up message occurs
								modelGame.setWorking(false); //The game ends
							} else {
							modelGame.setIsMyTurn(true); //the client's turn
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}

					} 
					//Receives a challange
					else if (receive.contains(question)) {
						if (!modelGame.getWorking()) {
							//Answer through confirmation dialog window with Yes/No.
							int response = infoBox("You have been challenged by " + opponentString);
							if (response == JOptionPane.YES_OPTION) {
								respond.setRespond("Yes");
								lobyModel.setConnection(opponentString); //establish a connection with the given opponent
							} else if (response == JOptionPane.NO_OPTION) {
								opponent.setOpponent(opponentString);
							//a field that changes to true in order to say the sender to send a negative response to the challanger
								lobyModel.setNo(true); 
							}
						} else {
							infoBoxMessage("You have been chalanged by " + opponentString + ". You are already in a game.", "Request");
							opponent.setSecondOpponent(opponentString);
							lobyModel.setNo(true);
						}
					} else {
						fromServer.close(); // Probably no point.
						throw new NullPointerException("Got null from server"); // Caught below
					}
				}
			}
		} 
		catch (NullPointerException e) {
			System.out.println("Server seems to have died.");
			System.exit(1); // Give up.
		}
		catch (IOException e) {
			System.out.println("Server seems to have died.");
			System.exit(1); // Give up.
		}
	}

	public static int infoBox(String infoMessage) {
		return JOptionPane.showConfirmDialog(null, infoMessage, "Request to play.", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
	}
	
	public static void infoBoxMessage(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
