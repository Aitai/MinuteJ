package model;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import view.AbstractView;
import view.CarGraph;
import view.InfoView;

import java.awt.*;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * De klasse voor alle data (manipulatie) van de simulatie
 *
 * @author MinuteJ
 * @version 1.3.0
 */
public class Simulator extends ViewModel implements Runnable {

	private static final String AD_HOC = "AD_HOC";
	private static final String PASS = "PASS";
	private static final String RES = "RES";
	private final Calendar calendar = new GregorianCalendar(2018, 1, 6, 10, 0, 0);
	private final CarQueue entranceCarQueue;
	private final CarQueue entrancePassQueue;
	private final CarQueue entranceResQueue;
	private final CarQueue paymentCarQueue;
	private final CarQueue exitCarQueue;
	private final GarageModel garageModel;
	private boolean running;
	private int tickPause = 128;

	private int enterSpeed = 3;
	private int exitSpeed = 5;
	private int paymentSpeed = 7;
	private int passHolders = 84;
	private int weekendArrivals = 150; // average number of arriving cars per hour
	private int weekendPassArrivals = 5; // average number of arriving cars per hour
	private int weekendResArrivals = 50; // average number of arriving cars per hour

	private int weekDayArrivals; // average number of arriving cars per hour
	private int weekDayPassArrivals; // average number of arriving cars per hour
	private int weekDayResArrivals; // average number of arriving cars per hour

	/**
	 * Maak een nieuwe simulator instantie
	 */
	public Simulator() {
		entranceCarQueue = new CarQueue();
		entrancePassQueue = new CarQueue();
		entranceResQueue = new CarQueue();
		paymentCarQueue = new CarQueue();
		exitCarQueue = new CarQueue();
		garageModel = new GarageModel(3, 6, 28);
	}

	/**
	 * Rond een double af
	 *
	 * @param value
	 *            de waarde van het getal
	 * @param places
	 *            het decimaal waarop afgerond moet worden
	 * @return
	 */
	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * Pas de tickpause aan
	 *
	 * @param a
	 *            de duur van de pauze
	 */
	public void setTickPause(int a) {
		tickPause = a;
	}

	/**
	 * Krijg de tickpause
	 *
	 * @return de waarde van de tickpause
	 */
	public int getTickPause() {
		return tickPause;
	}

	/**
	 * Pas de hoeveelheid pashouders aan
	 *
	 * @param a
	 *            de hoeveelheid
	 */
	public void setNumberOfPasses(int a) {
		passHolders = a;
	}

	/**
	 * Krijg de hoeveelheid pashouders
	 *
	 * @return de hoeveeleheid pashouders
	 */
	public int getNumberOfPasses() {
		return passHolders;
	}

	public void setEnterSpeed(int a) {
		enterSpeed = a;
	}

	public int getEnterSpeed() {
		return enterSpeed;
	}

	public void setExitSpeed(int a) {
		exitSpeed = a;
	}

	public int getExitSpeed() {
		return exitSpeed;
	}

	public void setPaymentSpeed(int a) {
		paymentSpeed = a;
	}

	public int getPaymentSpeed() {
		return paymentSpeed;
	}

	public void setWeekendArrivals(int a) {
		weekendArrivals = a;
	}

	public int getWeekendArrivals() {
		return weekendArrivals;
	}

	public void setWeekendPassArrivals(int a) {
		weekendPassArrivals = a;
	}

	public int getWeekendPassArrivals() {
		return weekendPassArrivals;
	}

	public void setWeekendResArrivals(int a) {
		weekendResArrivals = a;
	}

	public int getWeekendResArrivals() {
		return weekendResArrivals;
	}

	public void setCostPerMinute(double a) {
		garageModel.setCostPerMinute(a);
	}

	public double getCostPerMinute() {
		return garageModel.getCostPerMinute();
	}

	/**
	 * Speel een geluidje af bij het afsluiten
	 */
	private void playExitSound() {
		try {
			InputStream inputStream = getClass().getResourceAsStream("../media/splat.au");
			AudioStream audioStream = new AudioStream(inputStream);
			AudioPlayer.player.start(audioStream);
		} catch (Exception ignored) {
		}
	}

	/**
	 * Open het programma
	 */
	public void run() {
		running = true;
		while (running) {
			tick();
		}
	}

	/**
	 * zet de afspeelsnelheid naar realiteit
	 */
	public void realTime() {
		tickPause = 60000;
	}

	/**
	 * Update de de simulatie met 1 stap
	 */
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
		CarGraph.setVal();
		setLabels();

		if (exitCarQueue.carsInQueue() > 0) {
			System.out.println("test");
			// playExitSound();
		}
	}

	/**
	 * Update de views
	 */
	public void setLabels() {
		InfoView.setDayLabel(daysOfTheWeek());
		InfoView.setMonthLabel(monthName());
		InfoView.setDayOfMonthLabel("  " + calendar.get(Calendar.DAY_OF_MONTH));
		InfoView.setTimeLabel(
				String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
		InfoView.setCarQueueLabel("Aantal normale auto's in de rij: " + entranceCarQueue.carsInQueue());
		InfoView.setPassResQueueLabel(
				"Aantal abonnementhouders/gereserveerden in de rij: " + entrancePassQueue.carsInQueue());
		InfoView.setpaymentCarQueueLabel("Aantal betalenden in de rij: " + paymentCarQueue.carsInQueue());
		InfoView.setexitCarQueueLabel("Aantal auto's in de rij voor de uitgang: " + exitCarQueue.carsInQueue());
		InfoView.setRevenueLabel("Ad hoc omzet: \u20AC" + round(garageModel.calcAdHocRev(), 2));
		InfoView.setExpectedRevenueLabel(
				"Verwachte ad hoc omzet: \u20AC" + round(garageModel.calcExpectedAdHocRev(), 2));
		InfoView.setFreeSpots("Aantal lege ad hoc plekken: " + garageModel.getNumberOfOpenFreeSpots());
		InfoView.setPassSpots("Aantal lege abonnement plekken: " + garageModel.getNumberOfOpenPassSpots());
		InfoView.setCostLabel("Kosten per minuut voor ad hoc auto's: \u20AC" + garageModel.getCostPerMinute());
	}

	/**
	 * Zet een snelle stap in de simulatie
	 */
	private void tickFast() {
		eveningArrivals();
		advanceTime();
		handleExit();
		updateViews();
		handleEntrance();
		garageModel.calcAdHocRev();
		garageModel.calcExpectedAdHocRev();
	}

	/**
	 * Pas de simulatie aan zodat er minder auto's in de nacht komen
	 */
	private void eveningArrivals() {
		if (calendar.get(Calendar.HOUR_OF_DAY) >= 22 || calendar.get(Calendar.HOUR_OF_DAY) <= 7) {
			if (calendar.get(Calendar.DAY_OF_WEEK) <= 5) {
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

	/**
	 * Advance the time by one minute
	 */
	private void advanceTime() {
		// Advance the time by one minute.
		calendar.add(Calendar.MINUTE, 1);
	}

	/**
	 * Krijg de dag van de week
	 *
	 * @return de dag
	 */
	private String daysOfTheWeek() {
		String dayString;
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case 2:
			dayString = "Maandag";
			break;
		case 3:
			dayString = "Dinsdag";
			break;
		case 4:
			dayString = "Woensdag";
			break;
		case 5:
			dayString = "Donderdag";
			break;
		case 6:
			dayString = "Vrijdag";
			break;
		case 7:
			dayString = "Zaterdag";
			break;
		case 1:
			dayString = "Zondag";
			break;
		default:
			dayString = "Geen geldige dag!";
			break;
		}
		return dayString;
	}

	/**
	 * Krijg de maand
	 *
	 * @return de maand
	 */
	private String monthName() {
		String monthString;
		switch (calendar.get(Calendar.MONTH)) {
		case 0:
			monthString = "Januari";
			break;
		case 1:
			monthString = "Februari";
			break;
		case 2:
			monthString = "Maart";
			break;
		case 3:
			monthString = "April";
			break;
		case 4:
			monthString = "Mei";
			break;
		case 5:
			monthString = "Juni";
			break;
		case 6:
			monthString = "Juli";
			break;
		case 7:
			monthString = "Augustus";
			break;
		case 8:
			monthString = "September";
			break;
		case 9:
			monthString = "Oktober";
			break;
		case 10:
			monthString = "November";
			break;
		case 11:
			monthString = "December";
			break;
		default:
			monthString = "Geen geldige maand!";
			break;
		}
		return monthString;
	}

	/**
	 * Methode die de ingangen regelt
	 */
	private void handleEntrance() {
		carsArriving();
		carsEntering(entranceCarQueue);
		carsEntering(entrancePassQueue);
		carsEntering(entranceResQueue);
	}

	/**
	 * Methode die de uitgangen regelt
	 */
	private void handleExit() {
		carsReadyToLeave();
		carsPaying();
		carsLeaving();
	}

	/**
	 * Update alle views
	 */
	public void updateViews() {
		garageModel.tick();
		for (AbstractView av : views) {
			av.updateView();
		}
	}

	/**
	 * Methode dat ervoor zorgt dat de auto's aankomen
	 */
	private void carsArriving() {
		int numberOfCars = getTotalCars(weekDayArrivals, weekendArrivals, AD_HOC);
		addArrivingCars(numberOfCars, AD_HOC);
		numberOfCars = getTotalCars(weekDayPassArrivals, weekendPassArrivals, PASS);
		addArrivingCars(numberOfCars, PASS);
		numberOfCars = getTotalCars(weekDayResArrivals, weekendResArrivals, RES);
		addArrivingCars(numberOfCars, RES);
	}

	/**
	 * Haal de eerste auto uit de rij en plaats deze op een parkeerplaats
	 *
	 * @param queue
	 *            de rij waarvan de auto gehaald moet worden
	 */
	private void carsEntering(CarQueue queue) {
		int i = 0;
		// Remove car from the front of the queue and assign to a parking space.
		while (queue.carsInQueue() > 0 && i < enterSpeed) {
			Location freeLocation = garageModel.getFirstFreeLocation();
			Location freeReservedLocation = garageModel.getFirstReservedLocation();
			Car car = queue.nextCar();

			if ((car.getColor() == Color.red || car.getColor() == Color.green)
					&& garageModel.getNumberOfOpenFreeSpots() > 0) {
				garageModel.setCarAt(freeLocation, car);
				queue.removeCar();
			} else if (car.getColor() == Color.blue && garageModel.getNumberOfOpenPassSpots() > 0) {
				garageModel.setPassCarAt(freeReservedLocation, car);
				queue.removeCar();
			}
			i++;
		}
	}

	/**
	 * De methode achter de functie van de start/pauze knop
	 */
	public void startPause() {
		if (!running) {
			Thread t = new Thread(this);
			t.start();
			running = true;
		} else {
			running = false;
		}
	}

	/**
	 * De methode achter de 1 minuut vooruit knop
	 */
	public void ffMinute() {
		tickFast();
		setLabels();
	}

	/**
	 * De methode achter de 1 uur vooruit knop
	 */
	public void ffHour() {
		for (int i = 0; i < 60; i++) {
			tickFast();
		}
		setLabels();
	}

	/**
	 * De methode achter de 100 stappen vooruit optie in de menubalk
	 */
	public void ffHundred() {
		for (int i = 0; i < 100; i++) {
			tickFast();
		}
		setLabels();
	}

	/**
	 * De methode achter de 1 dag vooruit knop
	 */
	public void ffDay() {
		for (int i = 0; i < 60 * 24; i++) {
			tickFast();
		}
		setLabels();
	}

	/**
	 * De methode achter de sneller knop
	 */
	public void faster() {
		if (tickPause != 1) {
			tickPause /= 2;
		}
	}

	/**
	 * De methode achter de langzamer knop
	 */
	public void slower() {
		if (tickPause <= 256) {
			tickPause *= 2;
		}
	}

	/**
	 * Methode die auto's toevoegd aan de rij om te betalen
	 */
	private void carsReadyToLeave() {
		Car car = garageModel.getFirstLeavingCar();
		while (car != null) {
			if (car.getHasToPay()) {
				car.setIsPaying(true);
				paymentCarQueue.addCar(car);
			} else if (car.getColor() == Color.blue) {
				carLeavesSpot(car, PASS);
				System.out.println("leave pass");
			} else if (car.getColor() == Color.green) {
				System.out.println("leave res");
				carLeavesSpot(car, RES);
			}
			car = garageModel.getFirstLeavingCar();
		}
	}

	/**
	 * Methode die auto's laat betalen
	 */
	private void carsPaying() {
		int i = 0;
		while (paymentCarQueue.carsInQueue() > 0 && i < paymentSpeed) {
			Car car = paymentCarQueue.removeCar();
			if (car.getColor() == Color.red) {
				System.out.println("pay ad hoc");

				carLeavesSpot(car, AD_HOC);
			}
			i++;
		}
	}

	/**
	 * Methode die ervoor zorgt dat de auto's de garage kunnen verlaten
	 */
	private void carsLeaving() {
		int i = 0;
		while (exitCarQueue.carsInQueue() > 0 && i < exitSpeed) {
			exitCarQueue.removeCar();
			i++;
		}
	}

	/**
	 * Geef het totaal aantal auto's terug
	 *
	 * @param weekDay aantal auto's per uur doordeweeks
	 * @param weekend aantal auto's per uur 's weekends
	 * @param type auto
	 * @return
	 */
	private int getTotalCars(int weekDay, int weekend, String type) {
		Random random = new Random();
		int averageNumberOfCarsPerHour = calendar.get(Calendar.DAY_OF_WEEK) < 6 ? weekDay : weekend;

		// Calculate the number of cars that arrive this minute.
		double standardDeviation = averageNumberOfCarsPerHour * 0.3;
		double totalCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
		int totalCars = (int) Math.round(totalCarsPerHour / 60);

		int parkedParkingPass = garageModel.getTotalCars("ParkingPass") + entrancePassQueue.carsInQueue();
		if (parkedParkingPass >= passHolders && type.equals(PASS)) {
			return 0;
		} else if (type.equals(PASS) && totalCars >= (passHolders - parkedParkingPass)) {
			return passHolders - parkedParkingPass;
		}
		return totalCars;
	}

	/**
	 * Voeg aankomende auto's toe aan het eind van de rij
	 *
	 * @param totalCars
	 * @param type
	 */
	private void addArrivingCars(int totalCars, String type) {
		switch (type) {
		case AD_HOC:
			for (int i = 0; i < totalCars; i++) {
				entranceCarQueue.addCar(new AdHocCar());
			}
			break;
		case PASS:
			for (int i = 0; i < totalCars; i++) {
				entrancePassQueue.addCar(new ParkingPassCar());
			}
			break;
		case RES:
			for (int i = 0; i < totalCars; i++) {
				entranceResQueue.addCar(new ReservedCar());
			}
			break;
		}
	}

	/**
	 * Methode dat een auto van zijn parkeerplek afhaalt
	 *
	 * @param car
	 */
	private void carLeavesSpot(Car car, String type) {
		garageModel.removeCarAt(car.getLocation(), type);
		exitCarQueue.addCar(car);
	}

	/**
	 * Geeft het garagemodel terug
	 *
	 * @return garagemodel
	 */
	public GarageModel getGarageModel() {
		return garageModel;
	}
}