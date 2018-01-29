package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class VierkantView extends JPanel {

	private static final long serialVersionUID = -430455720642004448L;
	int amount=50;
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void addCalculateListener(ActionListener knop){

		MainView.calculateButton.addActionListener(knop);
	}

	public void paintComponent(Graphics g) {
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
