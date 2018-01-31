package view;

import java.awt.Dimension;

import javax.swing.JLabel;

import model.Simulator;

public class InfoView extends AbstractView {
	public static JLabel a = new JLabel("bla");

//	super(simulator);
	private static final long serialVersionUID = -3260703954764103294L;
	public InfoView(Simulator simulator) {
		simulator.addView(this);
		add(a);
	}
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}
	public static void setLabel(String label) {
		a.setText(label);
	}
	@Override
	public void updateView() {
	}
}
