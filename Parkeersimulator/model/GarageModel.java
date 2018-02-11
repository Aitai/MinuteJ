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
	private int numberOfOpenFreeSpots;
	private int numberOfOpenPassSpots = 84;
	private final Car[][][] cars;

	private double costPerMinute = 0.05;
	private double totalInput = 0;
	private double adHocRev = 0;
	private double expectedAdHocRev = 0;
	public int numberOfAdHocCars = 0;

	/**
	 * Maak een nieuw Garagemodel aan met de gegeven grootte
	 *
	 * @param numberOfFloors
	 *            de hoeveelheid etages
	 * @param numberOfRows
	 *            de hoeveelheid rijen per etage
	 * @param numberOfPlaces
	 *            de hoeveelheid plekken per rij
	 */
	public GarageModel(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
		this.numberOfFloors = numberOfFloors;
		this.numberOfRows = numberOfRows;
		this.numberOfPlaces = numberOfPlaces;
		this.numberOfOpenFreeSpots = numberOfFloors * numberOfRows * numberOfPlaces - 88;
		cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

	}

	/**
	 * Zet de waarde van de kosten per minuut voor ad hoc auto's
	 *
	 * @param a
	 */
	public void setCostPerMinute(double a) {
		costPerMinute = a;
	}

	/**
	 * Krijg de kosten per minuut voor ad hoc auto's
	 *
	 * @return kosten ad hoc auto's
	 */
	public double getCostPerMinute() {
		return costPerMinute;
	}

	/**
	 * Bereken de totale omzet van de normale auto's
	 *
	 * @return de behaalde omzet
	 */
	public double calcAdHocRev() {
		for (Car[][] cars1 : cars) {
			for (Car[] cars2 : cars1) {
				for (Car car : cars2) {
					try {
						AdHocCar adHocCar = (AdHocCar) car;
						if (adHocCar.getMinutesLeft() == 0) {
							adHocRev += adHocCar.getParkingTime() * costPerMinute;
						}
					} catch (Exception ignored) {
					}
				}
			}
		}
		return adHocRev;
	}

	/**
	 * Bereken de verwachte omzet van de normale auto's
	 *
	 * @return de verwachte omzet
	 */
	public double calcExpectedAdHocRev() {
		for (Car[][] cars1 : cars) {
			for (Car[] cars2 : cars1) {
				for (Car car : cars2) {
					try {
						AdHocCar adHocCar = (AdHocCar) car;
						if (adHocCar.getParkingTime() == adHocCar.getMinutesLeft()) {
							totalInput += adHocCar.getParkingTime() * costPerMinute;
						}
						expectedAdHocRev = (totalInput - adHocRev);
					} catch (Exception ignored) {
					}
				}
			}
		}
		return expectedAdHocRev;
	}

	public double getAdHocRev() {
		return adHocRev;
	}
	public double getExpectedAdHocRev() {
		return expectedAdHocRev;
	}
	/**
	 * Krijg de hoeveelheid etages
	 *
	 * @return de hoeveelheid etages
	 */
	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	/**
	 * Krijg de hoeveelheid rijen
	 *
	 * @return de hoeveelheid rijen
	 */
	public int getNumberOfRows() {
		return numberOfRows;
	}

	/**
	 * Krijg de hoeveelheid plekken
	 *
	 * @return de hoeveelheid plekken
	 */
	public int getNumberOfPlaces() {
		return numberOfPlaces;
	}

	/**
	 * Krijg de hoeveelheid vrije parkeerplekken
	 *
	 * @return de hoeveelheid vrije parkeerplekken
	 */
	public int getNumberOfOpenFreeSpots() {
		return numberOfOpenFreeSpots;
	}

	/**
	 * Krijg de hoeveelheid vrije parkeerplekken voor abonnementen
	 *
	 * @return de hoeveelheid vrije parkeerplekken voor abonnementen
	 */
	public int getNumberOfOpenPassSpots() {
		return numberOfOpenPassSpots;
	}

	/**
	 * Krijg de auto van een locatie
	 *
	 * @param location
	 *            de locatie
	 * @return de auto op de locatie
	 */
	public Car getCarAt(Location location) {
		if (locationIsInvalid(location)) {
			return null;
		}
		return cars[location.getFloor()][location.getRow()][location.getPlace()];
	}

	/**
	 * Zet een ad hoc of gereserveerde auto op een bepaalde locatie
	 *
	 * @param location
	 *            de locatie waar de auto kan staan
	 * @param car
	 *            de auto voor die locatie
	 */
	public void setCarAt(Location location, Car car) {
		if (locationIsInvalid(location)) {
			return;
		}
		Car oldCar = getCarAt(location);
		if (oldCar == null) {
			cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
			car.setLocation(location);
			numberOfOpenFreeSpots--;
		}
	}

	/**
	 * Zet een abonnement auto op één van de gereserveerde plekken
	 *
	 * @param location
	 * 				de locatie waar de auto kan staan
	 * @param car
	 * 				de auto voor die locatie
	 */
	public void setPassCarAt(Location location, Car car) {
		if (locationIsInvalid(location)) {
			return;
		}
		Car oldCar = getCarAt(location);
		if (oldCar == null) {
			cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
			car.setLocation(location);
			numberOfOpenPassSpots--;
		}
	}

	/**
	 * Verwijder de auto van een locatie
	 *
	 * @param location
	 *            de locatie waar de auto verwijderd moet worden
	 */
	public void removeCarAt(Location location, String type) {
		if (locationIsInvalid(location)) {
			return;
		}
		Car car = getCarAt(location);
		if (car == null) {
			return;
		}
		cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
		car.setLocation(null);
		if (type.equals("PASS")) {
			numberOfOpenPassSpots++;
		} else {
			numberOfOpenFreeSpots++;
		}
	}

	/**
	 * Krijg de eerstvolgende vrije locatie
	 *
	 * @return de vrije locatie
	 */
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

	/**
	 * Krijg de eerstvolgende gereserveerde locatie
	 *
	 * @return de eerste locatie
	 */
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

	/**
	 * Krijg de eerste auto die weg gaat
	 *
	 * @return de auto die weg gaat
	 */
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

	/**
	 * Haal een overige minuut van een bepaalde auto af
	 */
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

	/**
	 * Check of de locatie bestaat
	 *
	 * @param location
	 *            de locatie die getest wordt
	 * @return true als de locatie bestaat
	 */
	private boolean locationIsInvalid(Location location) {
		int floor = location.getFloor();
		int row = location.getRow();
		int place = location.getPlace();
		return floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0
				|| place > numberOfPlaces;
	}

	/**
	 * Krijg het maximaal van het aantal auto's van een type
	 *
	 * @param type
	 *            het type auto's waarop getest wordt
	 * @return het maximaal als een getal
	 */
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