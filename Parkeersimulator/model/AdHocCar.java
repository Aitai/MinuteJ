package model;

import java.awt.*;
import java.util.Random;

public class AdHocCar extends Car {
    private static final Color COLOR = Color.red;
    private final int stayMinutes;

    public AdHocCar() {
        Random random = new Random();
        this.stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    public int getParkingTime() {
        return stayMinutes;
    }

    public Color getColor() {
        return COLOR;
    }
}
