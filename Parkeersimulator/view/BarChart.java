package view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Klasse voor een staafdiagram view
 * 
 * @author MinuteJ
 * @version 1.0.0
 */
class BarChart extends JPanel {
	private final Map<Color, Integer> bars = new LinkedHashMap<>();

	/**
	 * Voeg een nieuwe staaf toe aan het diagram
	 *
	 * @param color
	 *            kleur van de staaf
	 * @param random
	 *            grootte van de staaf
	 */
	public void addBar(Color color, Integer random) {
		bars.put(color, random);
		repaint();
	}

	/**
	 * Teken de diagram
	 */
	protected void paintComponent(Graphics g) {

		// determine longest bar
		int max = Integer.MIN_VALUE;
		for (Integer value : bars.values()) {
			max = Math.max(max, value);
		}
		// paint bars
		int width = (getWidth() / bars.size()) - 2;
		int x = 1;
		for (Color color : bars.keySet()) {
			int value = bars.get(color);
			int height = (int) ((getHeight() - 5) * ((double) value / max));
			g.setColor(color);
			g.fillRect(x, getHeight() - height, width, height);
			g.setColor(Color.black);
			g.drawRect(x, getHeight() - height, width, height);
			x += (width + 2);
		}
	}

	@Override
	/**
	 * Geeft de grootte van het diagram
	 */
	public Dimension getPreferredSize() {
		return new Dimension(bars.size() * 10 + 2, 50);
	}

}