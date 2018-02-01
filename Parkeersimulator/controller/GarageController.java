package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Simulator;

public class GarageController extends AbstractController {
	private static final long serialVersionUID = 3253955925290497248L;
	private JButton startPauze;
	private JButton step;
	private JButton ffHour;
	private JButton ffDay;
	private JButton faster;
	private JButton slower;
	private JButton realTime;

	public GarageController(Simulator simulator) {
		super(simulator);

		startPauze = new JButton("Start/Pauze");
		startPauze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.startPauze();
			}
		});

		step = new JButton("1 minuut");
		step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.ffMinute();
			}
		});

		ffHour = new JButton("1 uur");
		ffHour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.ffHour();
			}
		});

		ffDay = new JButton("1 dag");
		ffDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.ffDay();
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

		realTime = new JButton("Realtime");
		realTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.realTime();
			}
		});

		add(startPauze);
		add(step);
		add(ffHour);
		add(ffDay);
		add(faster);
		add(slower);
		add(realTime);

		setBackground(Color.BLACK);

		setVisible(true);
	}

}
