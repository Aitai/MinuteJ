package view;

import java.awt.Dimension;

import javax.swing.JLabel;

import model.Simulator;

public class InfoView extends AbstractView {
	public static JLabel dateTime = new JLabel();
	public static JLabel queue = new JLabel();
	public static JLabel revenue = new JLabel();
	public static JLabel expectedRevenue = new JLabel();
	public static JLabel entranceCarQueue = new JLabel();
	public static JLabel entrancePassResQueue = new JLabel();
	public static JLabel paymentCarQueue = new JLabel();
	public static JLabel exitCarQueue = new JLabel();
	public static JLabel freeSpots = new JLabel();

	private static final long serialVersionUID = -3260703954764103294L;
	public InfoView(Simulator simulator) {
		simulator.addView(this);
		add(dateTime);
		add(queue);
		add(revenue);
		add(expectedRevenue);

		add(entranceCarQueue);
		add(entrancePassResQueue);
		add(paymentCarQueue);
		add(exitCarQueue);
		add(freeSpots);
	}
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}
	public static void setDateTimeLabel(String label) {
		dateTime.setText(label);
	}
	public static void setCarQueueLabel(String label) {
		entranceCarQueue.setText(label);
	}

	public static void setRevenueLabel(String label) {
		revenue.setText(label);
	}
	public static void setExpectedRevenueLabel(String label) {
		expectedRevenue.setText(label);
	}

	public static void setPassResQueueLabel(String label) {
		entrancePassResQueue.setText(label);
	}
	public static void setpaymentCarQueueLabel(String label) {
		paymentCarQueue.setText(label);
	}
	public static void setexitCarQueueLabel(String label) {
		exitCarQueue.setText(label);
	}
	
	public static void setFreeSpots(String label) {
		freeSpots.setText(label);
	}

	@Override
	public void updateView() {
	}
}