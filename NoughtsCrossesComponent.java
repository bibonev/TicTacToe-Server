import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * @author Boyan Bonev (bib508)
 * Components of the game
 */
@SuppressWarnings("serial")
public class NoughtsCrossesComponent extends JPanel
{
	/** Initialise the components
	 * @param model
	 */
	public NoughtsCrossesComponent(NoughtsCrossesModel model)
	{
		super();
		
		BoardView board = new BoardView(model); //Board
		model.addObserver(board);
		
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);
	}
}
