package view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

class BarChart extends JPanel {
	private final Map<Color, Integer> bars = new LinkedHashMap<>();

	/**
	 * 17. Add new bar to chart 18.
	 *
	 * @param color
	 *            color to display bar 19.
	 * @param random
	 *            size of bar 20.
	 */

	public void addBar(Color color, Integer random) {
		bars.put(color, random);
		repaint();
	}

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

	public Dimension getPreferredSize() {
		return new Dimension(bars.size() * 10 + 2, 50);
	}

}