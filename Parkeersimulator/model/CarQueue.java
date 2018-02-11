package model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author MinuteJ
 * @version 1.0.0
 */
class CarQueue {
    private final Queue<Car> queue = new LinkedList<>();

    /**
     * Voege een auto toe
     *
     * @param car de auto die toegevoegd gaat worden
     */
    public void addCar(Car car) {
        queue.add(car);
    }

    /**
     * Haal een auto uit de rij
     *
     * @return de auto die eruit is gehaald
     */
    public Car removeCar() {
        return queue.poll();
    }

    /**
     * Geef de volgende auto in de rij
     *
     * @return de volgende auto in de rij
     */
    Car nextCar() {
        return queue.peek();
    }

    /**
     * Geef de grootte van de rij
     *
     * @return de rij van auto's
     */
    public int carsInQueue() {
        return queue.size();
    }
}
