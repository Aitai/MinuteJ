package model;

import java.awt.*;
import java.util.Random;

/**
 * Een klasse voor ad hoc auto's met de kleur rood
 *
 * @author MinuteJ 1.0.0
 */
public class AdHocCar extends Car {
    private static final Color COLOR = Color.red;
    private final int stayMinutes;

    /**
     * Creër een nieuwe ad hoc auto, bepaal hoelang deze blijft en of hij moet
     * betalen
     */
    public AdHocCar() {
        Random random = new Random();
        this.stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    /**
     * @return de parkeer tijd in minuten
     */
    public int getParkingTime() {
        return stayMinutes;
    }

    /**
     * @return de kleur van de auto
     */
    public Color getColor() {
        return COLOR;
    }
}
