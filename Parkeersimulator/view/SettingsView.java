package view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Simulator;

/**
 * De klasse voor alle instellingen
 *
 * @author MinuteJ
 * @version 1.0.0
 */
public class SettingsView extends AbstractView {
	private static final long serialVersionUID = -2458498389310217800L;
	private final JFrame settingsWindow;

	private JTextField t1, t2, t3, t4, t5, t6, t7, t8, t9;
	private final Simulator simulator;

	/**
	 * Maak een nieuwe instellingen menu aan
	 * @param simulator
	 */
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
		JPanel content = new JPanel(new GridLayout(10, 2));

		JLabel l1 = new JLabel("Tickpauze in milliseconden:");
		t1 = new JTextField(String.valueOf(simulator.getTickPause()));

		JLabel l2 = new JLabel("Abonnementen:");
		t2 = new JTextField(String.valueOf(simulator.getNumberOfPasses()));

		JLabel l3 = new JLabel("Ingang auto's per minuut:");
		t3 = new JTextField(String.valueOf(simulator.getEnterSpeed()));

		JLabel l4 = new JLabel("Uitgang auto's per minuut:");
		t4 = new JTextField(String.valueOf(simulator.getExitSpeed()));

		JLabel l5 = new JLabel("Betalers per minuut:");
		t5 = new JTextField(String.valueOf(simulator.getPaymentSpeed()));

		JLabel l6 = new JLabel("Ad hoc auto's per uur in het weekend:");
		t6 = new JTextField(String.valueOf(simulator.getWeekendArrivals()));

		JLabel l7 = new JLabel("Auto's met abonnement per uur in het weekend:");
		t7 = new JTextField(String.valueOf(simulator.getWeekendPassArrivals()));

		JLabel l8 = new JLabel("Gereserveerde auto's per uur in het weekend:");
		t8 = new JTextField(String.valueOf(simulator.getWeekendResArrivals()));

		JLabel l9 = new JLabel("Euro per minuut voor ad hoc auto's:");
		t9 = new JTextField(String.valueOf(simulator.getCostPerMinute()));

		content.add(l1);
		content.add(t1);
		content.add(l2);
		content.add(t2);
		content.add(l3);
		content.add(t3);
		content.add(l4);
		content.add(t4);
		content.add(l5);
		content.add(t5);
		content.add(l6);
		content.add(t6);
		content.add(l7);
		content.add(t7);
		content.add(l8);
		content.add(t8);
		content.add(l9);
		content.add(t9);

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
					throw new Exception();
				}
				simulator.setTickPause(Integer.parseInt(tickPause));

				String numberOfPasses = t2.getText();
				if (Integer.parseInt(numberOfPasses) < 0) {
					throw new Exception();
				}
				simulator.setNumberOfPasses(Integer.parseInt(numberOfPasses));

				String enterSpeed = t3.getText();
				if (Integer.parseInt(enterSpeed) < 0) {
					throw new Exception();
				}
				simulator.setEnterSpeed(Integer.parseInt(enterSpeed));

				String exitSpeed = t4.getText();
				if (Integer.parseInt(exitSpeed) < 0) {
					throw new Exception();
				}
				simulator.setExitSpeed(Integer.parseInt(exitSpeed));

				String paymentSpeed = t5.getText();
				if (Integer.parseInt(paymentSpeed) < 0) {
					throw new Exception();
				}
				simulator.setPaymentSpeed(Integer.parseInt(paymentSpeed));

				String weekendArrivals = t6.getText();
				if (Integer.parseInt(weekendArrivals) < 0) {
					throw new Exception();
				}
				simulator.setWeekendArrivals(Integer.parseInt(weekendArrivals));

				String weekendPassArrivals = t7.getText();
				if (Integer.parseInt(weekendPassArrivals) < 0) {
					throw new Exception();
				}
				simulator.setWeekendPassArrivals(Integer.parseInt(weekendPassArrivals));

				String weekendResArrivals = t8.getText();
				if (Integer.parseInt(weekendResArrivals) < 0) {
					throw new Exception();
				}
				simulator.setWeekendResArrivals(Integer.parseInt(weekendResArrivals));

				String costPerMinute = t8.getText();
				if (Integer.parseInt(costPerMinute) < 0) {
					throw new Exception();
				}
				simulator.setCostPerMinute(Integer.parseInt(costPerMinute));

				settingsWindow.dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(settingsWindow, "Voer een getal groter dan 1 in.", "Error",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		return update;
	}

	public void updateView() {
		repaint();
	}
}