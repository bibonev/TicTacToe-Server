import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * @author Boyan Bonev (bib508)
 * Loby - JList, Buttons (Connection, Refresh, Scoreboard)
 *
 */
@SuppressWarnings("serial")
public class LobyPanel extends JPanel {
	/**
	 * Fields of the LobyPanel.
	 */
	@SuppressWarnings("rawtypes")
	JList list;
	@SuppressWarnings("rawtypes")
	DefaultListModel listModel;
	ArrayList<String> users;

	/**
	 * Initialise Panel
	 * @param model
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LobyPanel(LobyModel model) {
		super();

		setLayout(new BorderLayout());
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane pane = new JScrollPane(list);

		JButton refresh = new JButton("Refresh");
		JButton connect = new JButton("Connect");
		JButton scoreboard = new JButton("Scoreboard");
		JButton exit = new JButton("Exit");

		JPanel buttons = new JPanel();
		buttons.add(refresh);
		buttons.add(connect);
		buttons.add(scoreboard);
		buttons.add(exit);

		refresh.addActionListener(e -> {
			model.setRequestUsers(true);
			try {
				//wait to receive the required information.
				Thread.sleep(1000); 
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			listModel.removeAllElements();
			users = model.getUsers();
			for (int i = 0; i < users.size(); i++)
				listModel.addElement(users.get(i));
		});

		connect.addActionListener(e -> {
			model.setConnection(list.getSelectedValue().toString());
		});
		
		scoreboard.addActionListener(e -> {
			model.setScoreboard(true);
			try {
				//wait to receive the required information.
				Thread.sleep(1000);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			listModel.removeAllElements();
			users = model.getUsers();
			for (int i = 0; i < users.size(); i++)
				listModel.addElement(users.get(i));
		});
		
		exit.addActionListener(e -> { 
			System.exit(0);
		});

		add(pane, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
	}
}
