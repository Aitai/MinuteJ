package view;

import java.awt.Dimension;

import javax.swing.JLabel;

import model.Simulator;

public class InfoView extends AbstractView {
	public static JLabel dateTime = new JLabel();
	public static JLabel queue = new JLabel();

//	super(simulator);
	private static final long serialVersionUID = -3260703954764103294L;
	public InfoView(Simulator simulator) {
		simulator.addView(this);
		add(dateTime);
		add(queue);
	}
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}
	public static void setDateTimeLabel(String label) {
		dateTime.setText(label);
	}
	public static void setQueueLabel(String label) {
		queue.setText(label);
	}
	@Override
	public void updateView() {
	}
}