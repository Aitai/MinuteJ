package model;

import java.awt.*;
import java.util.Random;

/**
 * Een klasse voor abonnement auto's met de kleur blauw
 *
 * @author MinuteJ
 * @version 1.0.0
 */
public class ParkingPassCar extends Car {
    private static final Color COLOR = Color.blue;

    /**
     * Creëer een nieuwe abonnement auto, bepaal hoelang deze blijft en of hij moet
     * betalen
     */
    public ParkingPassCar() {
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
