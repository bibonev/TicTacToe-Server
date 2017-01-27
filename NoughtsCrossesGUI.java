import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * @author Boyan Bonev (bib508)
 *	Initialise the GUI
 */
public class NoughtsCrossesGUI implements Runnable {
	NoughtsCrossesModel model;
	JFrame frame;
	String username;

	public NoughtsCrossesGUI(NoughtsCrossesModel model, String username) {
		this.model = model;
		this.username = username;
	}

	/* Start the thread.
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		//Initialise components
		NoughtsCrossesComponent comp = new NoughtsCrossesComponent(model);

		//JFrame with a board
		frame = new JFrame("Board: " + username);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		//Action Listener for the "X" button
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				model.setInteruption(true);
				model.setWorking(false);
			}
		});

		frame.add(comp);
		frame.setVisible(false);
	}

	/** Change the visibility when necessary.
	 * @param value
	 */
	public void setVisibility(boolean value) {
		frame.setVisible(value);
	}
}
