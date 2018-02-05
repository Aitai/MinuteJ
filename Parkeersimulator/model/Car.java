package model;

import java.awt.*;
/**
 * De klasse voor het aanmaken van een auto
 * 
 * @author MinuteJ
 * 1.0.0
 */
public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;

    /**
     * Constructor for objects of class Car
     */
    Car() {

    }

    /**
     * Geef de locatie terug
     * @return de locatie
     */
    public Location getLocation() {
        return location;
    }
    
    /**
     * Pas de locatie aan
     * 
     * @param location De locatie van de auto
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Krijg de minuten die de auto nog heeft
     * @return de overige minuten
     */
    public int getMinutesLeft() {
        return minutesLeft;
    }

    /**
     * Pas de overige minuten aan
     * 
     * @param minutesLeft de minuten die nog over zijn
     */
    void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    /**
     * Kijk of de auto aan het betalen is of niet
     * @return true als de auto aan het betalen is
     */
    public boolean getIsPaying() {
        return isPaying;
    }

    /**
     * Pas aan of de auto aan het betalen is of niet
     * 
     * @param isPaying true als de de auto aan het betalen is
     */
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    /**
     * Kijk of de auto nog moet betalen of niet
     * @return true als de auto nog moet betalen
     */
    public boolean getHasToPay() {
        return hasToPay;
    }

    /**
     * Pas aan of de auto nog moet betalen of niet
     * @param hasToPay true als de auto nog moet betalen
     */
    void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    /**
     * Haalt de overige minuten naar beneden
     */
    public void tick() {
        minutesLeft--;
    }

    /**
     * Abstracte methode om de kleur te krijgen
     * @return de kleur
     */
    public abstract Color getColor();
}