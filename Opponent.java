
/**
 * @author Boyan Bonev (bib508)
 * Sets and gets the opponent.
 */
public class Opponent {
	/**
	 * Fields for initilising the empty opponent at first
	 */
	private String opp = "";
	private String secondOpp = "";
	
	/**
	 * Constructor
	 */
	public Opponent (){
		
	}
	
	/** Get the opponent
	 * @return opponent
	 */
	public String getOpponent() {
		return this.opp;
	}
	
	/** Set the opponent
	 * @param opp
	 */
	public void setOpponent(String opp) {
		this.opp = opp;
	}
	
	/** Get the second opponent
	 * @return opponent
	 */
	public String getSecondOpponent() {
		return this.secondOpp;
	}
	
	/** Set the second opponent
	 * @param opp
	 */
	public void setSecondOpponent(String secondOpp) {
		this.secondOpp = secondOpp;
	}
}
