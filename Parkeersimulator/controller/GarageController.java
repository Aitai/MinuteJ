package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Simulator;

public class GarageController extends AbstractController {
	private static final long serialVersionUID = 3253955925290497248L;
	private JButton start;
	private JButton pauze;
	private JButton stap;

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

		stap = new JButton("Stap");
		stap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.tick();
			}
		});

		add(start);
		add(pauze);
		add(stap);

		setVisible(true);
	}

}
