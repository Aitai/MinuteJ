package view;

import java.awt.*;

import model.*;

public class PiePane extends AbstractDisplayPane {
	private static final long serialVersionUID = 5455934187803194147L;

	public PiePane(Model model) {
		super(model);
		setSize(200, 200);
	}

	public void paintComponent(Graphics g) {
		int amount=getModel().getAmount();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 200, 200);
		g.setColor(Color.BLUE);
		g.fillArc(10, 10, 180, 180, 0, amount);
	}	
}
