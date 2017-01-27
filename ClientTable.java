// Each nickname has a different incomming-message queue.

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ClientTable {

	private ConcurrentMap<String,MessageQueue> queueTable  = new ConcurrentHashMap<String,MessageQueue>();
  //using ConcurentHashMap avoids using synchronised keyword as concurrent read/writes on the table without locking the entire table
  
  	private String users;
  
  /**
   * Constructor
   */
  	public ClientTable(){
	  this.users = "";
  	}

  /**
   * Add a user to the queue
   * @param nickname
   */
  	public void add(String nickname) {
	  queueTable.put(nickname, new MessageQueue());
	}

  // Returns null if the nickname is not in the table:
	public MessageQueue getQueue(String nickname) {
		return queueTable.get(nickname);
	}
  
  /**
   * Remove the user from the queue 
   * @param username
   */
	public void remove(String username) {
	  queueTable.remove(username);
	}
  
	/** Get the online users from the table.
	 * @return
	 */
	public String getUsers(String except){
	  users = "";
	  for ( String key : queueTable.keySet() ) {
		  if (!key.equals(except)) {
		    users += key +  ", ";
		  }
		}
	  return users;
	}

}
