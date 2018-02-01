package model;

import view.AbstractView;
import view.CarGraph;
import view.InfoView;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Simulator extends ViewModel implements Runnable {

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private static final String RES = "3";

	private CarQueue entranceCarQueue;
	private CarQueue entrancePassQueue;
	private CarQueue entranceResQueue;
	private CarQueue paymentCarQueue;
	private CarQueue exitCarQueue;
	private GarageModel garageModel;
	private boolean running;
	private Thread t;
	private String dayString;
	private String fullMinute;
	private String fullHour;

	private int day = 0;
	private int hour = 10;
	private int minute = -1;

	private int tickPause = 128;

	private int weekDayArrivals; // average number of arriving cars per hour
	private int weekendArrivals = 200; // average number of arriving cars per hour
	private int weekDayPassArrivals; // average number of arriving cars per hour
	private int weekendPassArrivals = 5; // average number of arriving cars per hour
	private int weekDayResArrivals; // average number of arriving cars per hour
	private int weekendResArrivals = 50; // average number of arriving cars per hour

	int enterSpeed = 3; // number of cars that can enter per minute
	int paymentSpeed = 7; // number of cars that can pay per minute
	int exitSpeed = 5; // number of cars that can leave per minute

	public Simulator() {
		entranceCarQueue = new CarQueue();
		entrancePassQueue = new CarQueue();
		entranceResQueue = new CarQueue();
		paymentCarQueue = new CarQueue();
		exitCarQueue = new CarQueue();
		garageModel = new GarageModel(3, 6, 28);

	}

	private void playExitSound() {
		try {
			InputStream inputStream = getClass().getResourceAsStream("../media/splat.au");
			AudioStream audioStream = new AudioStream(inputStream);
			AudioPlayer.player.start(audioStream);
		} catch (Exception e) {
		}
	}

	public void run() {
		running = true;
		while (running) {
			tick();
		}
	}

	public String getLabel() {
		return daysOfTheWeek() + "   " + fullHour() + ":" + fullMinute();
	}

	public void realTime() {
		tickPause = 60000;
	}

	public void tick() {
		daysOfTheWeek();
		eveningArrivals();
		advanceTime();
		handleExit();
		updateViews();
		// Pause.
		try {
			Thread.sleep(tickPause);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		handleEntrance();
		InfoView.setDateTimeLabel(daysOfTheWeek() + "   " + fullHour() + ":" + fullMinute());
		InfoView.setCarQueueLabel("Aantal normale auto's in de rij: " + entranceCarQueue.carsInQueue());
		InfoView.setPassResQueueLabel(
				"Aantal abonnement houders/gereserveerden in de rij: " + entrancePassQueue.carsInQueue());
		InfoView.setpaymentCarQueueLabel("Aantal betalende in de rij: " + paymentCarQueue.carsInQueue());
		InfoView.setexitCarQueueLabel("Aantal auto's in de rij voor de uitgang: " + exitCarQueue.carsInQueue());
		CarGraph.setVal();
		InfoView.setRevenueLabel("Ad hoc omzet: " + round(garageModel.calcAdHocRev(), 2));
		InfoView.setExpectedRevenueLabel("Verwachte ad hoc omzet: " + round(garageModel.calcExpectedAdHocRev(), 2));
		if (exitCarQueue.carsInQueue() > 0) {
			System.out.println("test");
			playExitSound();
		}
		;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public void tickFast() {
		daysOfTheWeek();
		eveningArrivals();
		advanceTime();
		handleExit();
		updateViews();
		handleEntrance();
		garageModel.calcAdHocRev();
		garageModel.calcExpectedAdHocRev();
	}

	public void eveningArrivals() {
		if (hour >= 22 || hour <= 7) {
			if (day <= 5) {
				weekDayArrivals = 40;
				weekDayPassArrivals = 20;
				weekDayResArrivals = 5;
			}
		} else {
			weekDayArrivals = 100;
			weekDayPassArrivals = 50;
			weekDayResArrivals = 20;
		}
	}

	private void advanceTime() {
		// Advance the time by one minute.
		minute++;
		while (minute > 59) {
			minute -= 60;
			hour++;
		}
		while (hour > 23) {
			hour -= 24;
			day++;
		}
		while (day > 6) {
			day -= 7;
		}
	}

	public String fullHour() {
		if (hour < 10) {
			fullHour = "0" + hour;
		} else {
			fullHour = "" + hour;
		}
		return fullHour;
	}

	public String fullMinute() {
		if (minute < 10) {
			fullMinute = "0" + minute;
		} else {
			fullMinute = "" + minute;
		}
		return fullMinute;
	}

	public String daysOfTheWeek() {
		switch (day) {
		case 0:
			dayString = "Monday";
			break;
		case 1:
			dayString = "Tuesday";
			break;
		case 2:
			dayString = "Wednesday";
			break;
		case 3:
			dayString = "Thursday";
			break;
		case 4:
			dayString = "Friday";
			break;
		case 5:
			dayString = "Saturday";
			break;
		case 6:
			dayString = "Sunday";
			break;
		default:
			dayString = "ERRROR!!!!";
			break;

		}
		return dayString;
	}

	private void handleEntrance() {
		carsArriving();
		carsEntering(entrancePassQueue);
		carsEntering(entranceCarQueue);
		carsEntering(entranceResQueue);
	}

	private void handleExit() {
		carsReadyToLeave();
		carsPaying();
		carsLeaving();
	}

	private void updateViews() {
		garageModel.tick();
		// Update the car park view.
		for (AbstractView av : views) {
			av.updateView();
		}
	}

	private void carsArriving() {
		int numberOfCars = getNumberOfCars(weekDayArrivals, weekendArrivals);
		addArrivingCars(numberOfCars, AD_HOC);
		numberOfCars = getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
		addArrivingCars(numberOfCars, PASS);
		numberOfCars = getNumberOfCars(weekDayResArrivals, weekendResArrivals);
		addArrivingCars(numberOfCars, RES);
	}

	private void carsEntering(CarQueue queue) {
		int i = 0;
		// Remove car from the front of the queue and assign to a parking space.
		while (queue.carsInQueue() > 0 && garageModel.getNumberOfOpenSpots() > 0 && i < enterSpeed) {
			Car car = queue.removeCar();
			Location freeLocation = garageModel.getFirstFreeLocation();
			garageModel.setCarAt(freeLocation, car);
			i++;
		}
	}

	public void startPauze() {
		if (running == false) {
			t = new Thread(this);
			t.start();
			running = true;
		} else {
			running = false;
		}
	}

	public void ffMinute() {
		tickFast();
		InfoView.setDateTimeLabel(daysOfTheWeek() + "   " + fullHour() + ":" + fullMinute());
		InfoView.setRevenueLabel("Ad hoc omzet: " + round(garageModel.calcAdHocRev(), 2));
		InfoView.setExpectedRevenueLabel("Verwachte ad hoc omzet: " + round(garageModel.calcExpectedAdHocRev(), 2));
	}

	public void ffHour() {
		for (int i = 0; i < 60; i++) {
			tickFast();
			InfoView.setDateTimeLabel(daysOfTheWeek() + "   " + fullHour() + ":" + fullMinute());
			InfoView.setRevenueLabel("Ad hoc omzet: " + round(garageModel.calcAdHocRev(), 2));
			InfoView.setExpectedRevenueLabel("Verwachte ad hoc omzet: " + round(garageModel.calcExpectedAdHocRev(), 2));
		}
	}

	public void ffDay() {
		for (int i = 0; i < 60 * 24; i++) {
			tickFast();
			InfoView.setDateTimeLabel(daysOfTheWeek() + "   " + fullHour() + ":" + fullMinute());
			InfoView.setRevenueLabel("Ad hoc omzet: " + round(garageModel.calcAdHocRev(), 2));
			InfoView.setExpectedRevenueLabel("Verwachte ad hoc omzet: " + round(garageModel.calcExpectedAdHocRev(), 2));
		}
	}

	public void faster() {
		if (tickPause != 1) {
			tickPause /= 2;
		}
	}

	public void slower() {
		if (tickPause <= 256) {
			tickPause *= 2;
		}
	}

	private void carsReadyToLeave() {
		// Add leaving cars to the payment queue.
		Car car = garageModel.getFirstLeavingCar();
		while (car != null) {
			if (car.getHasToPay()) {
				car.setIsPaying(true);
				paymentCarQueue.addCar(car);
			} else {
				carLeavesSpot(car);
			}
			car = garageModel.getFirstLeavingCar();
		}
	}

	private void carsPaying() {
		// Let cars pay.
		int i = 0;
		while (paymentCarQueue.carsInQueue() > 0 && i < paymentSpeed) {
			Car car = paymentCarQueue.removeCar();
			// TODO Handle payment.
			carLeavesSpot(car);
			i++;
		}
	}

	private void carsLeaving() {
		// Let cars leave.
		int i = 0;
		while (exitCarQueue.carsInQueue() > 0 && i < exitSpeed) {
			exitCarQueue.removeCar();
			i++;
		}
	}

	private int getNumberOfCars(int weekDay, int weekend) {
		Random random = new Random();

		// Get the average number of cars that arrive per hour.
		int averageNumberOfCarsPerHour = day < 5 ? weekDay : weekend;

		// Calculate the number of cars that arrive this minute.
		double standardDeviation = averageNumberOfCarsPerHour * 0.3;
		double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
		return (int) Math.round(numberOfCarsPerHour / 60);
	}

	private void addArrivingCars(int numberOfCars, String type) {
		// Add the cars to the back of the queue.
		switch (type) {
		case AD_HOC:
			for (int i = 0; i < numberOfCars; i++) {
				entranceCarQueue.addCar(new AdHocCar());
			}
			break;
		case PASS:
			for (int i = 0; i < numberOfCars; i++) {
				entrancePassQueue.addCar(new ParkingPassCar());
			}
			break;
		case RES:
			for (int i = 0; i < numberOfCars; i++) {
				entrancePassQueue.addCar(new ReservedCar());
			}
			break;
		}
	}

	private void carLeavesSpot(Car car) {
		garageModel.removeCarAt(car.getLocation());
		exitCarQueue.addCar(car);
	}

	public GarageModel getGarageModel() {
		return garageModel;
	}
}