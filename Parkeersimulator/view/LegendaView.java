package view;

import java.awt.Color;
import java.awt.Graphics;

public class LegendaView extends AbstractView {
	private static final long serialVersionUID = 961956082556411201L;

	public LegendaView() {
	}

	@Override
	public void paintComponent(Graphics g) {

		int x = getWidth() - 350;
		int y = getHeight() - 250;
		g.setColor(Color.black);
		Color[] colors = new Color[5];
		colors[0] = Color.red;
		colors[1] = Color.blue;
		colors[2] = Color.green;
		colors[3] = Color.lightGray;
		colors[4] = Color.white;

		int i = 1;
		for (Color color : colors) {
			g.setColor(color);
			g.fillRect(x + 20, y + 20 * i, 25, 15);
			i++;
		}
		g.setColor(Color.black);
		g.drawString("Ad hoc auto", x + 60, y + 35);
		g.drawString("Auto met abonnement", x + 60, y + 55);
		g.drawString("Gereserveerde plek", x + 60, y + 75);
		g.drawString("Lege plek voor abonnees", x + 60, y + 95);
		g.drawString("Lege plek voor ad hoc auto's", x + 60, y + 115);
	}

	@Override
	public void updateView() {
	}

}
