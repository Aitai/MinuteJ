package model;

import java.awt.Color;

public class GarageModel {

	private int numberOfFloors;
	private int numberOfRows;
	private int numberOfPlaces;
	private int numberOfOpenSpots;
	private Car[][][] cars;

	public GarageModel(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
		this.numberOfFloors = numberOfFloors;
		this.numberOfRows = numberOfRows;
		this.numberOfPlaces = numberOfPlaces;
		this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces - 4;
		cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

	}

	private double costPerMinute = 0.05;

	private double expectedAdHocRev = 0;
	private double totalInput = 0;
	private double totalOutput = 0;

	public double calcAdHocRev() {
		for (Car[][] cars1 : cars) {
			for (Car[] cars2 : cars1) {
				for (Car car : cars2) {
					try {
						AdHocCar adHocCar = (AdHocCar) car;
						if (adHocCar.getMinutesLeft() == 0 && adHocCar.getColor() == Color.red) {
							totalOutput += adHocCar.getParkingTime() * costPerMinute;
						}
					} catch (Exception b) {
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
					} catch (Exception b) {
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

	public int getNumberOfOpenSpots() {
		return numberOfOpenSpots;
	}

	public Car getCarAt(Location location) {
		if (!locationIsValid(location)) {
			return null;
		}
		return cars[location.getFloor()][location.getRow()][location.getPlace()];
	}

	public boolean setCarAt(Location location, Car car) {
		if (!locationIsValid(location)) {
			return false;
		}
		Car oldCar = getCarAt(location);
		if (oldCar == null) {
			cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
			car.setLocation(location);
			numberOfOpenSpots--;
			return true;
		}
		return false;
	}

	public Car removeCarAt(Location location) {
		if (!locationIsValid(location)) {
			return null;
		}
		Car car = getCarAt(location);
		if (car == null) {
			return null;
		}
		cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
		car.setLocation(null);
		numberOfOpenSpots++;
		return car;
	}

	Location getNearestLocation(Location loc1, Location loc2) {
		Location nearest;
		if (loc1.getFloor() < loc2.getFloor()) {
			nearest = loc1;
		} else if (loc1.getRow() < loc2.getRow() && loc1.getFloor() <= loc2.getFloor()) {
			nearest = loc1;
		} else if (loc1.getPlace() < loc2.getPlace() && loc1.getRow() <= loc2.getRow()
				&& loc1.getFloor() <= loc2.getFloor()) {
			nearest = loc1;
		} else {
			nearest = loc2;
		}
		return nearest;
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

	private boolean locationIsValid(Location location) {
		int floor = location.getFloor();
		int row = location.getRow();
		int place = location.getPlace();
		if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0
				|| place > numberOfPlaces) {
			return false;
		}
		return true;
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