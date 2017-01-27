import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Boyan Bonev (bib508)
 *	Scoreboard that keeps the scores of the users that have been connected (and still are) to the server.
 */
public class Scoreboard {
	private ConcurrentMap<String, Integer> scoreboard = new ConcurrentHashMap<String, Integer>();
	// using ConcurentHashMap avoids using synchronised keyword as concurrent read/writes on the table without locking the entire table
	
	/**
	 * Constructor
	 */
	public Scoreboard() {
	}
	
	/** Add user to the table.
	 * @param nickname - the user
	 */
	public void add(String nickname) {
		scoreboard.put(nickname, 0);
	}
	
	/** Get the score of a particular user
	 * @param nickname - the particular user
	 * @return the score
	 */
	public int getScore(String nickname) {
		return scoreboard.get(nickname);
	}
	
	/** Increase the score of a particular user
	 * @param nickname - the user
	 */
	public void increase(String nickname) {
		int oldValue = this.getScore(nickname);
		scoreboard.replace(nickname, oldValue, oldValue + 1);
	}
	
	/** Get the scoreboard (users + their scores)
	 * @return scoreboard as ArrayList
	 */
	public ArrayList<String> get() {
		ArrayList<String> scores = new ArrayList<>();
		for (String key:  scoreboard.keySet()) {
			scores.add(key + " - " + scoreboard.get(key));
		}
		
		return scores;
	}
}
