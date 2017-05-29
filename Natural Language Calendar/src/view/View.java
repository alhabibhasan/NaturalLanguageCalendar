package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import control.Control;
/**
* Created by: Al Habib Hasan and Adriel Aiach-Kohen
*/
public class View extends JFrame implements Observer {

	Control control;

	private JTabbedPane tabs;
	private JPanel tab1 = new JPanel(new BorderLayout());
	private JPanel tab2 = new JPanel(new BorderLayout());
	private JList<String> tab1area = new JList<String>();
	private JList<String> tab2area = new JList<String>();
	JTextField jtf1 = new JTextField();
	JTextField jtf2 = new JTextField();

	public View(Control control) {
		this.control = control;
		setContent();
	}

	public void setContent() {
		setMinimumSize(new Dimension(1080, 800));

		setTabPane();

		tabs = new JTabbedPane();

		tabs.addTab("Calendar", null, tab1, "");

		tabs.addTab("Reminders", null, tab2, "");

		jtf1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				control.getCal().updateLastMessage(jtf1.getText());
			}
		});

		jtf2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				control.getReminder().updateLastMessage(jtf2.getText());
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(new JFrame(), "Are you sure to close this window?", "Really Closing?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					control.getCal().saveCalData("cal");
					control.getReminder().saveCalData("rem");
					System.exit(0);
				} else {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				}
			}

			@Override
			public void windowOpened(WindowEvent we) {
				tab1area.setModel(control.getCal().getMessage());
				tab2area.setModel(control.getReminder().getMessage());
			}
		});

		addJListListeners();

		add(tabs);
		control.getCal().loadCalData("cal");
		control.getReminder().loadCalData("rem");
		pack();
		setVisible(true);
	}

	private void addJListListeners() {
		tab1area.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					System.out.println(tab1area.getSelectedIndex());
					control.getCal().removeMessage(tab1area.getSelectedIndex());
				}

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		tab2area.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					System.out.println(tab2area.getSelectedIndex());
					control.getReminder().removeMessage(tab2area.getSelectedIndex());
				}

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void setTabPane() {

		tab1area.setFocusable(false);
		tab2area.setFocusable(false);

		JScrollPane jpane1 = new JScrollPane(tab1area);
		JScrollPane jpane2 = new JScrollPane(tab2area);

		jpane1.setPreferredSize(new Dimension(1080, 700));
		jpane2.setPreferredSize(new Dimension(1080, 700));

		tab1.add(jpane1, BorderLayout.NORTH);
		tab2.add(jpane2, BorderLayout.NORTH);

		tab1.add(jtf1, BorderLayout.SOUTH);
		tab2.add(jtf2, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable o, Object arg) {

		if (control.getLastCalledListener().equals("rem")) {
			tab2area.setModel(control.getReminder().getMessage());
			jtf2.setText("");
		} else {
			tab1area.setModel(control.getCal().getMessage());
			jtf1.setText("");
		}

	}

}
