package src.model;

import java.util.LinkedList;
import java.util.Queue;

class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    boolean addCar(Car car) {
        return queue.add(car);
    }

    Car removeCar() {
        return queue.poll();
    }

    /*
     * Get the size of a queue
     * @return int The size of the queue
     */
    public int getSize(){
        return queue.size();
    }

}
