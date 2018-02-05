package model;
/**
 * 
 * @author MinuteJ
 * @version 1.0.0
 */
public class Location {

    private final int floor;
    private final int row;
    private final int place;
    private Boolean reserved = false;

    /**
     * Constructor for objects of class Location
     */
    public Location(int floor, int row, int place) {
        this.floor = floor;
        this.row = row;
        this.place = place;
        if (row == 0) {
            reserved = true;
        }
    }

    /**
     * Implement content equality.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location other = (Location) obj;
            return floor == other.getFloor() && row == other.getRow() && place == other.getPlace();
        } else {
            return false;
        }
    }

    /**
     * Return a string of the form floor,row,place.
     *
     * @return A string representation of the location.
     */
    public String toString() {
        return floor + "," + row + "," + place;
    }


    public boolean getReserved() {
        return reserved;
    }

    /**
     * Use the 10 bits for each of the floor, row and place values. Except for very
     * big car parks, this should give a unique hash code for each (floor, row,
     * place) tupel.
     *
     * @return A hashcode for the location.
     */
    public int hashCode() {
        return (floor << 20) + (row << 10) + place;
    }

    /**
     * @return The floor.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * @return The row.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return The place.
     */
    public int getPlace() {
        return place;
    }

}