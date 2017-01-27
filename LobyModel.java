import java.util.ArrayList;
import java.util.Observable;

/**
 * @author Boyan Bonev (bib508)
 *	The model of the loby.
 */
public class LobyModel extends Observable {

	/**
	 * Fields of the LobyModel.
	 */
	private String opponent = null;
	private ArrayList<String> users;
	private boolean no = false;
	private boolean request = false;
	private boolean scoreboard = false;
	private boolean quit = false;

	/**
	 * Constructor
	 */
	public LobyModel() {

	}

	/**
	 * Set connection with an opponent.
	 * @param opponent
	 */
	public void setConnection(String opponent) {
		this.opponent = opponent;
		setChanged();
		notifyObservers();
	}

	/**
	 * Get the connection (the opponent).
	 * @return
	 */
	public String getConnection() {
		return this.opponent;
	}

	/**
	 * Set users to be used in the JList.
	 * @param users
	 */
	public void setUsers(ArrayList<String> users) {
		this.users = users;
		setChanged();
		notifyObservers();
	}

	/**
	 * Get users to be displayed in the JList.
	 * @return
	 */
	public ArrayList<String> getUsers() {
		return this.users;
	}

	/**
	 * Get the request to see the online users.
	 * @return
	 */
	public boolean getRequestUsers() {
		return this.request;
	}

	/**
	 * Set a request to see the online users.
	 * @param request
	 */
	public void setRequestUsers(boolean request) {
		this.request = request;
	}

	/**
	 * Get No as a response.
	 * @return
	 */
	public boolean getNo() {
		return this.no;
	}

	/**
	 * Set No as a response.
	 * @param no
	 */
	public void setNo(boolean no) {
		this.no = no;
	}
	
	/**
	 * Get the request to to shown scoreboard.
	 * @return
	 */
	public boolean getScoreboard() {
		return this.scoreboard ;
	}

	/**
	 * Set the request to see the scoreboard.
	 * @param scoreboard
	 */
	public void setScoreboard(boolean scoreboard) {
		this.scoreboard = scoreboard;
	}
	
	public boolean getQuit() {
		return this.quit;
	}
	
	public void setQuit(boolean quit) {
		this.quit = quit;
	}
}
