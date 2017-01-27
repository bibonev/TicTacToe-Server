import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * @author Boyan Bonev (bib508)
 * Creates a GUI for the loby
 */
public class LobyGUI implements Runnable {

	LobyModel model;
	String username;
	JFrame frame;
	
	/** Initialise the loby.
	 * @param model
	 * @param username
	 */
	public LobyGUI(LobyModel model, String username){
		this.model = model;
		this.username = username;
	}
	
	/* Start the thread for the loby
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		LobyComponent lc = new LobyComponent(model);
		
		frame = new JFrame("Loby - " + username);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(lc);
		frame.setVisible(true);
		
	}
}
