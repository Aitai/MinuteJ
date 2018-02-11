package model;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import view.*;
import java.awt.*;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * De klasse voor alle data (manipulatie) van de draaiende simulatie
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
	MainView mainView;
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

	private int numberOfAdHocCars = 0;
	private int numberOfParkingPassCars = 0;
	private int numberOfResCars = 0;

	/**
	 * Maakt een nieuwe simulator instantie
	 */
	public Simulator() {
		entranceCarQueue = new CarQueue();
		entrancePassQueue = new CarQueue();
		entranceResQueue = new CarQueue();
		paymentCarQueue = new CarQueue();
		exitCarQueue = new CarQueue();
		garageModel = new GarageModel(3, 6, 28);
		mainView = new MainView(this);
	}

	/**
	 * Rondt een double af
	 *
	 * @param value
	 *            De waarde van het getal
	 * @param places
	 *            Het decimaal waarop afgerond moet worden
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
	 * Past De tickpause aan
	 *
	 * @param a
	 *            de duur van de pauze
	 */
	public void setTickPause(int a) {
		tickPause = a;
	}

	/**
	 * Krijgt de tickpause
	 *
	 * @return De waarde van de tickpause
	 */
	public int getTickPause() {
		return tickPause;
	}

	/**
	 * Past de hoeveelheid pashouders aan
	 *
	 * @param a
	 *            De hoeveelheid
	 */
	public void setNumberOfPasses(int a) {
		passHolders = a;
	}

	/**
	 * Krijgt de hoeveelheid pashouders
	 *
	 * @return De hoeveeleheid pashouders
	 */
	public int getNumberOfPasses() {
		return passHolders;
	}

	/**
	 * Zet het aantal auto's dat kan binnen komen per minuut
	 *
	 * @param a
	 */
	public void setEnterSpeed(int a) {
		enterSpeed = a;
	}

	/**
	 * Krijgt het aantal auto's dat binnen komt per minuut
	 *
	 * @return Aantal auto's dat kan binnen komen per minuut
	 */
	public int getEnterSpeed() {
		return enterSpeed;
	}

	/**
	 * Zet het aantal auto's dat kan uitrijden per minuut
	 *
	 * @param a
	 */
	public void setExitSpeed(int a) {
		exitSpeed = a;
	}

	/**
	 * Krijgt het aantal auto's dat kan wegrijden per minuut
	 *
	 * @return Het aantal auto's dat kan wegrijden per minuut
	 */
	public int getExitSpeed() {
		return exitSpeed;
	}

	/**
	 * Zet de snelheid van het betalen per minuut
	 *
	 * @param a
	 */
	public void setPaymentSpeed(int a) {
		paymentSpeed = a;
	}

	/**
	 * Krijgt de snelheid van het betalen per minuut
	 *
	 * @return De snelheid van het betalen per minuut
	 */
	public int getPaymentSpeed() {
		return paymentSpeed;
	}

	/**
	 * Zet het aantal ad hoc auto's die komen in het weekend
	 *
	 * @param a
	 */
	public void setWeekendArrivals(int a) {
		weekendArrivals = a;
	}

	/**
	 * Krijgt het aantal ad hoc auto's die komen in het weekend
	 *
	 * @return Het aantal ad hoc auto's die komen in het weekend
	 */
	public int getWeekendArrivals() {
		return weekendArrivals;
	}

	/**
	 * Zet aantal abonnement houders die komen in het weekend
	 *
	 * @param a
	 */
	public void setWeekendPassArrivals(int a) {
		weekendPassArrivals = a;
	}

	/**
	 * Krijgt aantal abonnement houders die komen in het weekend
	 *
	 * @return Aantal abonnement houders die komen in het weekend
	 */
	public int getWeekendPassArrivals() {
		return weekendPassArrivals;
	}

	/**
	 * Zet de aantal gereserveerde auto's die komen in het weekend
	 *
	 * @param a
	 */
	public void setWeekendResArrivals(int a) {
		weekendResArrivals = a;
	}

	/**
	 * Krijgt de aantal gereserveerde auto's in het weekend
	 *
	 * @return Het aantal gereserveerde auto's in het weekend
	 */
	public int getWeekendResArrivals() {
		return weekendResArrivals;
	}

	/**
	 * Zet de kosten per minuut voor ad hoc auto's
	 *
	 * @param a
	 */
	public void setCostPerMinute(double a) {
		garageModel.setCostPerMinute(a);
	}

	/**
	 * Krijgt de kosten van ad hoc auto's per minuut
	 *
	 * @return Kosten per minuut voor ad hoc auto's
	 */
	public double getCostPerMinute() {
		return garageModel.getCostPerMinute();
	}

	/**
	 * Speelt een geluidje af wanneer er een of meer auto's in de wachtrij bij de uitgang staan
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
	 * Zorgt ervoor dat er een tick wordt uitgevoerd zolang de boolean running waar is
	 */
	public void run() {
		running = true;
		while (running) {
			tick();
		}
	}

	/**
	 * Zet de afspeelsnelheid naar echte tijd
	 */
	public void realTime() {
		tickPause = 60000;
	}

	/**
	 * Updatet de simulatie met 1 stap
	 */
	private void tick() {
		daysOfTheWeek();
		eveningArrivals();
		advanceTime();
		handleExit();
		updateViews();
		updateCharts();
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
			 playExitSound();
		}
	}

	/**
	 * Updatet de views
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
		InfoView.setAdHocCars("Aantal ad hoc auto's in de garage " + numberOfAdHocCars);
		InfoView.setParkingPassCars("Aantal abonnement auto's in de garage " + numberOfParkingPassCars);
		InfoView.setResCars("Aantal gereserveerde auto's in de garage " + numberOfResCars);
	}

	/**
	 * Zet een stap in de simulatie zonder de tickpauze
	 */
	private void tickFast() {
		eveningArrivals();
		advanceTime();
		handleExit();
		updateViews();
		handleEntrance();
		updateCharts();
		garageModel.calcAdHocRev();
		garageModel.calcExpectedAdHocRev();
	}

	/**
	 * Past de simulatie aan zodat er minder auto's in de nacht komen
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
	 * Update alle diagrammen
	 */
	public void updateCharts() {
		mainView.pieChart.createPiePiece("Ad hoc auto's", numberOfAdHocCars);
		mainView.pieChart.createPiePiece("Abonnement auto's", numberOfParkingPassCars);
		mainView.pieChart.createPiePiece("Gereserveerde auto's", numberOfResCars);
		mainView.pieChart.createPiePiece("Vrije ad hoc plaatsen", garageModel.getNumberOfOpenFreeSpots());
		mainView.pieChart.createPiePiece("Vrije abonnee plaatsen", garageModel.getNumberOfOpenPassSpots());

		mainView.barChart.updateBarChart(garageModel.calcExpectedAdHocRev(), garageModel.calcAdHocRev());

		if(calendar.get(Calendar.HOUR_OF_DAY)>0) {
			mainView.lineChart.updateChart(numberOfAdHocCars, "Ad hoc", ""+calendar.get(Calendar.HOUR_OF_DAY));
		}
		else {
			mainView.lineChart.clearChart();
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
	 * Zet een nummer 1-7 om in de naam van de dag
	 *
	 * @return De dag
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
	 * Zet een nummer 0-11 om in de naam van die maand
	 *
	 * @return De maand
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
	 * Updatet alle views
	 */
	public void updateViews() {
		garageModel.tick();
		for (AbstractView av : views) {
			av.updateView();
		}
	}

	/**
	 * Methode die ervoor zorgt dat de auto's aankomen
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
	 * Haalt de eerste auto uit de rij en plaatst deze op een parkeerplaats
	 *
	 * @param queue
	 *            De rij waarvan de auto gehaald moet worden
	 */
	private void carsEntering(CarQueue queue) {
		int i = 0;
		// Verwijdert een auto van het begin van de rij en wijst deze een plek toe.
		while (queue.carsInQueue() > 0 && i < enterSpeed) {
			Location freeLocation = garageModel.getFirstFreeLocation();
			Location freeReservedLocation = garageModel.getFirstReservedLocation();
			Car car = queue.nextCar();
			if ((car.getColor() == Color.red || car.getColor() == Color.green)
					&& garageModel.getNumberOfOpenFreeSpots() > 0) {
				garageModel.setCarAt(freeLocation, car);
				queue.removeCar();
				if(car.getColor() == Color.green) {
					numberOfResCars++;
				} else if(car.getColor() == Color.red) {
					numberOfAdHocCars++;
				}
			} else if (car.getColor() == Color.blue && garageModel.getNumberOfOpenPassSpots() > 0) {
				garageModel.setPassCarAt(freeReservedLocation, car);
				queue.removeCar();
				numberOfParkingPassCars++;
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
	 * Methode die auto's toevoegt aan de rij om te betalen
	 */
	private void carsReadyToLeave() {
		Car car = garageModel.getFirstLeavingCar();
		while (car != null) {
			if (car.getHasToPay()) {
				car.setIsPaying(true);
				paymentCarQueue.addCar(car);
				numberOfAdHocCars--;
			} else if (car.getColor() == Color.blue) {
				carLeavesSpot(car, PASS);
				numberOfParkingPassCars--;
			} else if (car.getColor() == Color.green) {
				carLeavesSpot(car, RES);
				numberOfResCars--;
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
	 * Krijgt het aantal ad hoc auto's die zich op dat moment in de garage bevinden
	 *
	 * @return Het aantal ad hoc auto's in de garage
	 */
	public int getNumberOfAdHocCars() {
		return numberOfAdHocCars;
	}

	/**
	 * Krijgt het aantal abonnement auto's die zich op dat moment in de garage bevinden
	 *
	 * @return Het aantal abonnement auto's in de garage
	 */
	public int getNumberOfParkingPassCars() {
		return numberOfParkingPassCars;
	}

	/**
	 * Krijgt het aantal gereserveerde auto's die zich op dat moment in de garage bevinden
	 *
	 * @return Het aantal gereserveerde auto's in de garage
	 */
	public int getNumberOfResCars() {
		return numberOfResCars;
	}

	/**
	 * Voegt aankomende auto's toe aan het eind van de rij
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
	 * Methode die een auto van zijn parkeerplek afhaalt
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