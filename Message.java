/**
 * @author Martin Escardo
 *
 */
public class Message {

  private final String sender;
  private final String cellActive;

  Message(String sender, String cellActive) {
    this.sender = sender;
    this.cellActive = cellActive;
  }

  public String getSender() {
    return sender;
  }

  public String getCellActive() {
    return cellActive;
  }

  public String toString() {
    return cellActive;
  }
}
