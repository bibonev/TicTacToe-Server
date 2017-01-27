import java.io.PrintStream;

/**
 * @author Boyan Bonev (bib508)
 * Repeatedly runs commands from the user and sends them to the server (read by ServerReceiver thread)
 */
public class ClientSender extends Thread {
	
	/**
	 * Fields of the ClientSender thread.
	 */
	private String nickname;
	private PrintStream toServer;
	private NoughtsCrossesModel gameModel;
	private Opponent opponent;
	private Respond respond;
	private Thread threadGame;
	private Thread threadLoby;
	private NoughtsCrossesGUI guiGame;
	private LobyModel lobyModel;
	private boolean sendCount = true; //Sends only once per iteration

	/**
	 * @param nickname 	- The user's nickname
	 * @param toServer 	- Using stream to send info to the server.
	 * @param gameModel 	- The model of the game.
	 * @param opponent 	- The opponent we set and get.
	 * @param respond 	- The respond we set and get.
	 * @param threadGame 	- The thread which starts the board.
	 * @param guiGame 	- The GUI of the board.
	 * @param threadLoby 	- The thread which starts the loby.
	 * @param guiLoby	- The GUI of the loby.
	 * @param lobyModel	- The model of the loby.
	 */
	ClientSender(String nickname, PrintStream toServer, NoughtsCrossesModel gameModel, Opponent opponent, Respond respond, Thread threadGame,
			NoughtsCrossesGUI guiGame, Thread threadLoby, LobyGUI guiLoby, LobyModel lobyModel) {
		this.nickname = nickname;
		this.toServer = toServer;
		this.gameModel = gameModel;
		this.opponent = opponent;
		this.respond = respond;
		this.threadGame = threadGame;
		this.guiGame = guiGame;
		this.threadLoby = threadLoby;
		this.lobyModel = lobyModel;
	}

	/* Start the thread of the client sender. It sends the information to the server.
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		// Tell the server what my nickname is:
		toServer.println(nickname);
		// Start the threads of the board and the loby.
		threadGame.start();
		threadLoby.start();
		
		while (true) {
			//Set the recipient at first to be none. 
			String recipient = "";
			while (!respond.getRespond().equals("Yes")) {
				//While the board is not available and the user is accecing the loby.
				while (lobyModel.getConnection() == null) {
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//When the user requests to see the online users.
					if (lobyModel.getRequestUsers()) {
						toServer.println("Request users");
						toServer.println("Users");
						lobyModel.setRequestUsers(false);
					}
					//When the user requests to see the scoreboard.
					if (lobyModel.getScoreboard()) {
						toServer.println("Request scoreboard");
						toServer.println("Scoreboard");
						lobyModel.setScoreboard(false);
					}
					//When the user rejects a challnage.
					if (lobyModel.getNo()) {
						toServer.println(opponent.getOpponent());
						toServer.println("No");
						opponent.setOpponent(null);
						lobyModel.setNo(false);
					}
					sendCount = true;
				}
				//Set the recepient to the opponent.
				recipient = lobyModel.getConnection();

				try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//When the respond of/from the user/opponent is Yes/Answer-Yes.
				if (respond.getRespond().equals("Yes") || respond.getRespond().equals("Answer - Yes")) {
					//If the user's respond is Yes, he/she sends this respond to the opponent (through the server).
					if (respond.getRespond().equals("Yes")) {
						toServer.println(opponent.getOpponent());
						toServer.println("Yes");
						gameModel.setIsMyTurn(false); 
					} else {
						gameModel.setIsMyTurn(true); //if the user's opponent accept the challange, the initiator starts.
					}

					sendCount = true; // availability to send challanges
					//start new game
					gameModel.newGame();
					gameModel.setWorking(true);
					gameModel.setWin(false);
					//make the board visible
					guiGame.setVisibility(true);

					//While playing
					while (gameModel.getWorking()) {
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//if the users recieves another challange
						if (lobyModel.getNo()) {
							toServer.println(opponent.getSecondOpponent());
							toServer.println("No");
							lobyModel.setNo(false);
						}
						try {
							Thread.sleep(30);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//waits for turn
						if (gameModel.getTurn() != 0) {
							//release the turn
							gameModel.setIsMyTurn(false);
							String cellActiveInt = String.valueOf(gameModel.getTurn());
							//sends the turn
							toServer.println(opponent.getOpponent());
							toServer.println(cellActiveInt);
							//opportunity for thinking 
							gameModel.setTurn(0);
						}
					}
					/**When the game is finished or being interupted by some of the users**/
					guiGame.setVisibility(false);
					//when interupted: sends to the opponent that the users has interupted the game
					if (gameModel.getInteruption()) {
						toServer.println(opponent.getOpponent());
						toServer.println("Interupted");
					}
					//when the game ended with a draw.
					if (gameModel.getWin()) {
						ClientReceiver.infoBoxMessage("You won!", "Result!");
						toServer.println("Increase score");
						toServer.println("Increase");
					}
					//when there is a winner: increase the score in the scoreboard					
					if (gameModel.getDraw()) {
						ClientReceiver.infoBoxMessage("You end the game with a draw!", "Draw!");
						toServer.println(opponent.getOpponent());
						toServer.println("Draw");
					}
					
					//release the connection, the opponent and the answer so that, they should be ready for a new game.
					lobyModel.setConnection(null);
					opponent.setOpponent(null);
					respond.setRespond("End game");
				} else {
					//Makes a challange to an opponent.
					if (sendCount) {
						toServer.println(recipient);
						toServer.println("NotYes");
						sendCount = false;
					}

				}
			}
		}
	}
}
