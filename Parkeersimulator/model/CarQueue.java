package model;

import java.util.LinkedList;
import java.util.Queue;

class CarQueue {
    private final Queue<Car> queue = new LinkedList<>();

    public void addCar(Car car) {
        queue.add(car);
    }

    public Car removeCar() {
        return queue.poll();
    }

    Car nextCar() {
        return queue.peek();
    }

    public int carsInQueue() {
        return queue.size();
    }
}
