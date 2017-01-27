import java.util.Observable;

/**
 * @author Boyan Bonve (bib508)
 *	Model of the game.
 */
public class NoughtsCrossesModel extends Observable {
	private NoughtsCrosses oxo;
	private boolean isMyTurn;
	private int turn;
	private boolean working = false;
	private boolean interupt;
	private boolean win = false;
	private boolean draw = false;

	/** 
	 * Initialise constructor with an object of NoughtsCrosses
	 * @param oxo
	 */
	public NoughtsCrossesModel(NoughtsCrosses oxo) {
		super();
		this.oxo = oxo;
	}

	/**
	 * Get symbol at given location.
	 * @param i the row
	 * @param j the column
	 * @return the symbol at that location
	 */
	public int get(int i, int j) {
		return oxo.get(i, j);
	}

	/** 
	 * Is it cross's turn?
	 * @return true if it is cross's turn, false for nought's turn
	 */
	public boolean isCrossTurn() {
		return oxo.isCrossTurn();
	}

	/**
	 * Let the player whose turn it is play at a particular location.
	 * @param i the row
	 * @param j the column
	 */
	public void turn(int i, int j) {
		oxo.turn(i, j);
		setChanged();
		notifyObservers();
	}

	/**
	 * Determine who (if anyone) has won.
	 * @return CROSS if cross has won, NOUGHT if nought has won, oetherwise BLANK
	 */
	public int whoWon() {
		return oxo.whoWon();
	}

	/**
	 * Start a new game
	 */
	public void newGame() {
		oxo.newGame();
		setChanged();
		notifyObservers();
	}

	/** Get wheather is client's turn or not.
	 * @return the turn
	 */
	public boolean getIsMyTurn() {
		return this.isMyTurn;
	}

	/** Set which turn it should be.
	 * @param turn
	 */
	public void setIsMyTurn(boolean turn) {
		this.isMyTurn = turn;
		setChanged();
		notifyObservers();
	}

	/** Get the client's turn.
	 * @return turn
	 */
	public int getTurn() {
		return this.turn;
	}

	/** Set the client's turn.
	 * @param turn
	 */
	public void setTurn(int turn) {
		this.turn = turn;
		setChanged();
		notifyObservers();
	}

	/** Get whether is playing a game or not.
	 * @return state of playing a game (true or false)
	 */
	public boolean getWorking() {
		return this.working;
	}

	/** Set playing game.
	 * @param working
	 */
	public void setWorking(boolean working) {
		this.working = working;
	}

	/** Get interuption.
	 * @return interuption
	 */
	public boolean getInteruption() {
		return this.interupt;
	}

	/** Set interuption.
	 * @param interupt
	 */
	public void setInteruption(boolean interupt) {
		this.interupt = interupt;
	}

	/** Get wheather the game ended with a win or not.
	 * @return game ended with a win?
	 */
	public boolean getWin() {
		return this.win;
	}

	/** Seting that the game ended with a win.
	 * @param win
	 */
	public void setWin(boolean win) {
		this.win = win;
	}
	
	/** Get wheather the game ended with a draw or not.
	 * @return game ended with a draw?
	 */
	public boolean getDraw() {
		return this.draw;
	}

	/** Seting that the game ended with a draw.
	 * @param draw
	 */
	public void setDraw(boolean draw) {
		this.draw = draw;
	}
}
