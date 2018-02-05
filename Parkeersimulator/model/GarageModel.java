package model;

import java.awt.*;
/**
 * 
 * @author MinuteJ
 * @version 1.1.0
 */
public class GarageModel {

    private final int numberOfFloors;
    private final int numberOfRows;
    private final int numberOfPlaces;
    private int numberOfOpenSpots;
    private final Car[][][] cars;
    private final double costPerMinute = 0.05;
    private double expectedAdHocRev = 0;
    private double totalInput = 0;
    private double totalOutput = 0;
    public GarageModel(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces - 84;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

    }

    public double calcAdHocRev() {
        for (Car[][] cars1 : cars) {
            for (Car[] cars2 : cars1) {
                for (Car car : cars2) {
                    try {
                        AdHocCar adHocCar = (AdHocCar) car;
                        if (adHocCar.getMinutesLeft() == 0 && adHocCar.getColor() == Color.red) {
                            totalOutput += adHocCar.getParkingTime() * costPerMinute;
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return totalOutput;
    }

    public double calcExpectedAdHocRev() {
        for (Car[][] cars1 : cars) {
            for (Car[] cars2 : cars1) {
                for (Car car : cars2) {
                    try {
                        AdHocCar adHocCar = (AdHocCar) car;
                        if (adHocCar.getColor() == Color.red
                                && adHocCar.getParkingTime() == adHocCar.getMinutesLeft()) {
                            totalInput += adHocCar.getParkingTime() * costPerMinute;
                        }
                        expectedAdHocRev = (totalInput - totalOutput);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return expectedAdHocRev;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenFreeSpots() {
        return numberOfOpenSpots;
    }

    public Car getCarAt(Location location) {
        if (locationIsInvalid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public void setCarAt(Location location, Car car) {
        if (locationIsInvalid(location)) {
            return;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
        }
    }

    public void removeCarAt(Location location) {
        if (locationIsInvalid(location)) {
            return;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
    }

    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null && !location.getReserved()) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    Location getFirstReservedLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null && location.getReserved()) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    private boolean locationIsInvalid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
		return floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0
                || place > numberOfPlaces;
	}

    public int getTotalCars(String type) {
        int total = 0;
        Color color;
        if (type.equals("AD_HOC")) {
            color = Color.red;
        } else {
            color = Color.blue;
        }
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getColor() == color) {
                        total += 1;
                    }
                }
            }
        }
        return total;
    }
}