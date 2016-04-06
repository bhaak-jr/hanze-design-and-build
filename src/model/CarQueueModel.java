package src.model;

import java.util.LinkedList;
import java.util.Queue;

class CarQueueModel {
    private Queue<CarModel> queue = new LinkedList<>();

    boolean addCar(CarModel car) {
        return queue.add(car);
    }

    CarModel removeCar() {
        return queue.poll();
    }

}
