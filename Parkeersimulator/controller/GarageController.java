package controller;

import model.Simulator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GarageController extends AbstractController {
    private static final long serialVersionUID = 3253955925290497248L;

	public GarageController(Simulator simulator) {
        super();

		JButton startPauze = new JButton("Start/Pauze");
        startPauze.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulator.startPauze();
            }
        });

		JButton step = new JButton("1 minuut");
        step.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulator.ffMinute();
            }
        });

		JButton ffHour = new JButton("1 uur");
        ffHour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulator.ffHour();
            }
        });

		JButton ffDay = new JButton("1 dag");
        ffDay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulator.ffDay();
            }
        });

		JButton faster = new JButton("Sneller");
        faster.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulator.faster();
            }
        });

		JButton slower = new JButton("Langzamer");
        slower.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulator.slower();
            }
        });

		JButton realTime = new JButton("Realtime");
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

//		setBackground(Color.BLACK);

        setVisible(true);
    }

}
