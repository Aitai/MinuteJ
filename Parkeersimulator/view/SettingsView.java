package view;

/**
 * 
 * @author MinuteJ
 * @version 1.0.0
 */
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Simulator;

public class SettingsView extends AbstractView {
	private static final long serialVersionUID = -2458498389310217800L;
	private final JFrame settingsWindow;

	private JTextField t1;
	private JTextField t2;
	private final Simulator simulator;

	public SettingsView(Simulator simulator) {

		this.simulator = simulator;

		settingsWindow = new JFrame();
		settingsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel settingsPanel = settingsPanel();

		settingsWindow.add(settingsPanel);
		settingsWindow.pack();
		settingsWindow.setResizable(false);
		settingsWindow.setVisible(true);

	}

	private JPanel settingsPanel() {
		JPanel content = new JPanel(new GridLayout(3, 2));

		JLabel l1 = new JLabel("Tickpauze:");
		t1 = new JTextField(String.valueOf(simulator.getTickPause()));

		JLabel l2 = new JLabel("Abonnementen:");
		t2 = new JTextField(String.valueOf(simulator.getNumberOfPasses()));

		content.add(l1);
		content.add(t1);
		content.add(l2);
		content.add(t2);

		content.add(updateButton());
		content.add(cancelButton());

		return content;
	}

	private JButton cancelButton() {
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(e -> settingsWindow.dispose());

		return cancel;
	}

	private JButton updateButton() {

		JButton update = new JButton("Update");
		update.addActionListener(e -> {
			try {
				String tickPause = t1.getText();
				if (Integer.parseInt(tickPause) < 1) {
					throw new Exception("a");
				}
				simulator.setTickPause(Integer.parseInt(tickPause));

				String numberOfPasses = t2.getText();
				if (Integer.parseInt(numberOfPasses) < 1 || Integer.parseInt(numberOfPasses) > 84) {
					throw new Exception("b");
				}
				simulator.setNumberOfPasses(Integer.parseInt(numberOfPasses));

				settingsWindow.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(settingsWindow, "Voer een getal 1 of groter tickpauze in.", "Error",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		return update;
	}

	public void updateView() {
		repaint();
	}
}