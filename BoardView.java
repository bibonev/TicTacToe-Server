import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.Observer;
import java.util.Observable;
import java.awt.GridLayout;

/**
 * @author Boyan Bonev (bib508)
 *	Board with buttons.
 */
@SuppressWarnings("serial")
public class BoardView extends JPanel implements Observer
{
	private NoughtsCrossesModel model;
	private JButton[][] cell;
	
	/** Initialise a board.
	 * @param model
	 */
	public BoardView(NoughtsCrossesModel model)
	{
		super();
		
		// initialise model
		this.model = model;
		
		//create array of buttons
		cell = new JButton[3][3];
		
		//set layout of panel
		setLayout(new GridLayout(3, 3));
		
		//for each square in grid:create a button; place on panel
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				cell[i][j] = new JButton(" ");
				final int x = i; final int y = j;
				cell[i][j].addActionListener(e-> {
					//when clicked a cell, it calls turn method and set the coordinates of the cell to it.
					model.turn(x, y);
					model.setTurn(((x+1) * 10) + (y+1));
					
					//if theere is a win - the game ends.
					if (model.whoWon() != 0) {
						model.setWin(true);
						model.setWorking(false);
					} else {
						boolean draw = true; //checks whether the game was a draw or not.
						for(int k = 0; k < 3; k++) {
							for(int m = 0; m < 3; m++) {
								if (cell[k][m].isEnabled()) {
									draw = false;
								}
							}
						}
						if (draw) {
							model.setDraw(true);
							model.setWorking(false);
						}
					}
				});
				add(cell[i][j]);
			}
		}
	}
	
	/** Update the board.
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj)
	{
		// for each square do the following:
		// if it's a NOUGHT, put O on button
		// if it's a CROSS, put X on button
		// else put     on button
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(model.get(i, j) == NoughtsCrosses.CROSS)
				{
					cell[i][j].setText("X");
					cell[i][j].setEnabled(false);
				}
				else if(model.get(i, j) == NoughtsCrosses.NOUGHT)
				{
					cell[i][j].setText("O");
					cell[i][j].setEnabled(false);
				}
				else
				{
					cell[i][j].setText(" ");
					boolean notOver = (model.whoWon() ==
						NoughtsCrosses.BLANK && model.getIsMyTurn());
					cell[i][j].setEnabled(notOver);
				}
			}
		}
		repaint();
	}
}	
					
					
		
		
