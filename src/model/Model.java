package model;

import view.GaragePane;

/**
 * @author Chris Roscher
 * @version 01-02-2017
 */
public class Model implements Runnable {
	
	private GaragePane carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;

//	private int amount;
//	private boolean run;

//	public AtomicBoolean runGarage = new AtomicBoolean(true);
	 private boolean runGarage = true;

	 public boolean getRunGarage() {
	 	return this.runGarage;
	 }

//	public void startGarage() {
////		Thread t1 = new Thread(Simulator());
//			(new Thread(new Garage())).start();
//	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void run() {
//		run=true;
//		while(run) {
//			setAmount(getAmount()+1);
//			if (getAmount()==360) stop();
//			try {
//				Thread.sleep(10);
//			} catch (Exception e) {}
//		}
//	}
public void updateView() {
    carParkView.updateView();
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

public int getNumberOfOpenSpots(){
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

public Location getFirstFreeLocation() {
    for (int floor = 0; floor < getNumberOfFloors(); floor++) {
        for (int row = 0; row < getNumberOfRows(); row++) {
            for (int place = 0; place < getNumberOfPlaces(); place++) {
                Location location = new Location(floor, row, place);
                if (getCarAt(location) == null) {
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
    if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
        return false;
    }
    return true;
}
}