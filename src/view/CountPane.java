package view;

import java.awt.*;

import model.*;

public class CountPane extends AbstractDisplayPane {
	private static final long serialVersionUID = -7503377039578042533L;

	public CountPane(Model model) {
		super(model);
		setSize(200, 200);
	}

	public void paintComponent(Graphics g) {
		int amount=getModel().getAmount();
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

