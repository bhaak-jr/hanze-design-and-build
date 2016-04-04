package src.controller;
import java.awt.event.ActionListener;
import java.util.Random;

import src.model.AdHocCar;
import src.model.Car;
import src.model.CarQueue;
import src.model.Location;
import src.view.SimView;

public class SimController /* extends AbstractController implements ActionListener */ {

    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimView simView;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;

    int weekDayArrivals= 50; // average number of cars per hour
    int weekendArrivals = 90; // average number of cars per hour

    int enterSpeed = 3; // number of cars per minute
    int paymentSpeed = 10; // number of cars that can pay per minute
    int exitSpeed = 9; // number of cars per minute

    /**
     * Instantiate the fields of SimController
     */
    public SimController() {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simView = new SimView(3, 6, 30); // Creates a new SimView with 3 floors, 6 rows and 30 places in total
    }

    // TODO FROM HERE
    /**
     * Altered version of the original run method,
     * now takes a int parameter.
     * @param numberOfSteps
     */
    public void run(int numberOfSteps) {
        Thread t = new Thread(new Runnable() { //New thread seperate from the other thread
            public void run() {
                for (int i = 0; i < numberOfSteps; i++) {
                    tick();
                }
            }
        });
        t.start(); //Start the thread. Thread dies when his task is finished
    }

    private void tick() {
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

        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDayArrivals
                : weekendArrivals;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.1;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        int numberOfCarsPerMinute = (int)Math.round(numberOfCarsPerHour / 60);

        // Add the cars to the back of the queue.
        for (int i = 0; i < numberOfCarsPerMinute; i++) {
            Car car = new AdHocCar();
            entranceCarQueue.addCar(car);
        }

        // Remove car from the front of the queue and assign to a parking space.
        for (int i = 0; i < enterSpeed; i++) {
            Car car = entranceCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // Find a space for this car.
            Location freeLocation = simView.getFirstFreeLocation();
            if (freeLocation != null) {
                simView.setCarAt(freeLocation, car);
                int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                car.setMinutesLeft(stayMinutes);
            }
        }

        // Perform car park tick.
        simView.tick();

        // Add leaving cars to the exit queue.
        while (simView.getFirstLeavingCar() != null) {
            Car car = simView.getFirstLeavingCar();
            car.setIsPaying(true);
            paymentCarQueue.addCar(car);
        }

        // Let cars pay.
        for (int i = 0; i < paymentSpeed; i++) {
            Car car = paymentCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // TODO Handle payment.
            simView.removeCarAt(car.getLocation());
            exitCarQueue.addCar(car);
        }

        // Let cars leave.
        for (int i = 0; i < exitSpeed; i++) {
            Car car = exitCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // Bye!
        }

        /** "This code is obsolete by now, but I'll leave it like this for nostalgia's sake" - Danny */
        //  for (int i = 0; i < exitSpeed; i++) {
        //      Car car = simView.getFirstLeavingCar();
        //      if (car == null) {
        //          break;
        //      }
        //      simView.removeCarAt(car.getLocation());
        //  }

        // Update the car park view.
        simView.updateView();

        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
