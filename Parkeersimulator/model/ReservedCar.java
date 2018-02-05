package model;
/**
 * 
 * @author MinuteJ
 * @version 1.0.0
 */
import java.awt.*;
import java.util.Random;

public class ReservedCar extends Car {
    private static final Color COLOR = Color.green;

    public ReservedCar() {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }

    public Color getColor() {
        return COLOR;
    }
}
