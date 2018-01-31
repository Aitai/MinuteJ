package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import model.Simulator;

public class GarageController extends AbstractController {
	private static final long serialVersionUID = 3253955925290497248L;
	private JButton startPauze;
	private JButton step;
	private JButton steps;
	private JButton faster;
	private JButton slower;

	public GarageController(Simulator simulator) {
		super(simulator);

		startPauze = new JButton("Start/Pauze");
		startPauze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.startPauze();
			}
		});

		step = new JButton("Stap");
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.step();
			}
		});

		steps = new JButton("100 Stappen");
		steps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.steps();
			}
		});

		faster = new JButton("Sneller");
		faster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.faster();
			}
		});

		slower = new JButton("Langzamer");
		slower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.slower();
			}
		});

		add(startPauze);
		add(step);
		add(steps);
		add(faster);
		add(slower);

		setBackground(Color.BLACK);

		setVisible(true);
	}

}
