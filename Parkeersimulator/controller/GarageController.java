package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;

import model.Simulator;

public class GarageController extends AbstractController {
	private static final long serialVersionUID = 3253955925290497248L;
	private JButton start;
	private JButton pauze;
	private JButton step;
	private JButton steps;
	private JButton faster;
	private JButton slower;

	public GarageController(Simulator simulator) {
		super(simulator);

		start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.start();
			}
		});

		pauze = new JButton("Pauze");
		pauze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.pauze();
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

		add(start);
		add(pauze);
		add(step);
		add(steps);
		add(faster);
		add(slower);

		setVisible(true);
	}

}
