package model;

import java.awt.*;
import java.util.Random;

/**
 * Een klasse voor gereserveerde auto's met de kleur groen
 * 
 * @author MinuteJ
 * @version 1.0.0
 */
public class ReservedCar extends Car {
	private static final Color COLOR = Color.green;

	/**
	 * Creeer een nieuwe een gereserveerde auto, bepaal hoelang deze blijft en of hij
	 * moet betalen
	 */
	public ReservedCar() {
		Random random = new Random();
		int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
		this.setMinutesLeft(stayMinutes);
		this.setHasToPay(false);
	}

	/**
	 * @return geeft de kleur van de auto
	 */
	public Color getColor() {
		return COLOR;
	}
}
