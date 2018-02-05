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

public class Simulator extends ViewModel implements Runnable {

    private static final String AD_HOC = "AD_HOC";
    private static final String PASS = "PASS";
    private static final String RES = "RES";
    Calendar calendar = new GregorianCalendar(2018, 1, 4, 9, 59, 0);
    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute
    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue entranceResQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private GarageModel garageModel;
    private boolean running;
    private Thread t;
    private String dayString;
    private String monthString;
    private int tickPause = 128;
    private int passHolders = 84;
    private int weekDayArrivals; // average number of arriving cars per hour
    private int weekendArrivals = 150; // average number of arriving cars per hour
    private int weekDayPassArrivals; // average number of arriving cars per hour
    private int weekendPassArrivals = 5; // average number of arriving cars per hour
    private int weekDayResArrivals; // average number of arriving cars per hour
    private int weekendResArrivals = 50; // average number of arriving cars per hour

    public Simulator() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        entranceResQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        garageModel = new GarageModel(3, 6, 28);

    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
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
        CarGraph.setVal();
        setLabels();

        if (exitCarQueue.carsInQueue() > 0) {
            System.out.println("test");
//			playExitSound();
        }
	}

    public void setLabels() {
        InfoView.setDayLabel(daysOfTheWeek());
        InfoView.setMonthLabel(monthName());
        InfoView.setDayOfMonthLabel("  " + calendar.get(Calendar.DAY_OF_MONTH));
        InfoView.setTimeLabel(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
        InfoView.setCarQueueLabel("Aantal normale auto's in de rij: " + entranceCarQueue.carsInQueue());
        InfoView.setPassResQueueLabel("Aantal abonnementhouders/gereserveerden in de rij: " + entrancePassQueue.carsInQueue());
        InfoView.setpaymentCarQueueLabel("Aantal betalenden in de rij: " + paymentCarQueue.carsInQueue());
        InfoView.setexitCarQueueLabel("Aantal auto's in de rij voor de uitgang: " + exitCarQueue.carsInQueue());
        InfoView.setRevenueLabel("Ad hoc omzet: \u20AC" + round(garageModel.calcAdHocRev(), 2));
        InfoView.setExpectedRevenueLabel("Verwachte ad hoc omzet: \u20AC" + round(garageModel.calcExpectedAdHocRev(), 2));
        InfoView.setFreeSpots("Aantal lege plekken: " + garageModel.getNumberOfOpenFreeSpots());
    }

    public void tickFast() {
        eveningArrivals();
        advanceTime();
        handleExit();
        updateViews();
        handleEntrance();
        garageModel.calcAdHocRev();
        garageModel.calcExpectedAdHocRev();
    }

    public void eveningArrivals() {
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

    private void advanceTime() {
        // Advance the time by one minute.
        calendar.add(Calendar.MINUTE, 1);
    }

    public String daysOfTheWeek() {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                dayString = "Maandag";
                break;
            case 2:
                dayString = "Dinsdag";
                break;
            case 3:
                dayString = "Woensdag";
                break;
            case 4:
                dayString = "Donderdag";
                break;
            case 5:
                dayString = "Vrijdag";
                break;
            case 6:
                dayString = "Zaterdag";
                break;
            case 7:
                dayString = "Zondag";
                break;
            default:
                dayString = "Geen geldige dag!";
                break;
        }
        return dayString;
    }

    public String monthName() {
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

    private void handleEntrance() {
        carsArriving();
        carsEntering(entranceCarQueue);
        carsEntering(entrancePassQueue);
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
        int numberOfCars = getTotalCars(weekDayArrivals, weekendArrivals, AD_HOC);
        addArrivingCars(numberOfCars, AD_HOC);
        numberOfCars = getTotalCars(weekDayPassArrivals, weekendPassArrivals, PASS);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars = getTotalCars(weekDayResArrivals, weekendResArrivals, RES);
        addArrivingCars(numberOfCars, RES);
    }

    private void carsEntering(CarQueue queue) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.
        //TODO Tweede wachtrij toevoegen voor mensen met een abonnement.
        while (queue.carsInQueue() > 0 && garageModel.getNumberOfOpenFreeSpots() > 0 && i < enterSpeed) {
            Location freeLocation = garageModel.getFirstFreeLocation();

            Location freeReservedLocation = garageModel.getFirstReservedLocation();
            Car car = queue.nextCar();
            if (car.getColor() == Color.blue) {
                garageModel.setCarAt(freeReservedLocation, car);
                queue.removeCar();
            } else {
                garageModel.setCarAt(freeLocation, car);
                queue.removeCar();
            }
            i++;
        }
    }

    public void startPauze() {
        if (!running) {
            t = new Thread(this);
            t.start();
            running = true;
        } else {
            running = false;
        }
    }

    public void ffMinute() {
        tickFast();
        setLabels();
    }

    public void ffHour() {
        for (int i = 0; i < 60; i++) {
            tickFast();
        }
        setLabels();
    }

    public void ffDay() {
        for (int i = 0; i < 60 * 24; i++) {
            tickFast();
        }
        setLabels();
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

    private void addArrivingCars(int totalCars, String type) {
        // Add the cars to the back of the queue.
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

    private void carLeavesSpot(Car car) {
        garageModel.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    public GarageModel getGarageModel() {
        return garageModel;
    }
}