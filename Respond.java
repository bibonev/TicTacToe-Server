
/**
 * @author Boyan Bonev (bib508)
 *	Sets and gets the respond from the clients.
 */
public class Respond {
	
private String rsp = ""; // empty respond at firts as a field
	
	/**
	 * Constructor
	 */
	public Respond (){
		
	}
	
	/** get the respond
	 * @return respond
	 */
	public String getRespond() {
		return this.rsp;
	}
	
	/** set the respond
	 * @param rsp
	 */
	public void setRespond(String rsp) {
		this.rsp = rsp;
	}
}
