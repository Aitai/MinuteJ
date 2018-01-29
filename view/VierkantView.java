package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class VierkantView extends JPanel {

	private static final long serialVersionUID = -430455720642004448L;

	private MainView calc;

	public void paintComponent(Graphics g) {
		int amount=100;
		boolean ready=false;
		int counter=1;

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 200, 200);
		g.setColor(Color.RED);

		for(int y=20;y<180 && !ready; y+=5) {
			for(int x=20;x<170 && !ready; x+=5) {
				ready=counter>amount;
				counter++;
				if (!ready) g.fillRect(x, y, 4, 4);
			}
		}
	}
}
