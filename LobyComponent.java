import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * @author Boyan Bonev (bib508)
 *	Components for the loby.
 */
@SuppressWarnings("serial")
public class LobyComponent extends JPanel{
	public LobyComponent (LobyModel model) {
		super();
		
		LobyPanel lp = new LobyPanel(model);
		
		setLayout(new BorderLayout());
		add(lp, BorderLayout.CENTER);
	}
}
