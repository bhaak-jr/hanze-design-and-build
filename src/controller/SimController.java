package src.controller;
import java.awt.event.ActionListener;
import java.util.Random;

import src.model.AdHocCar;
import src.model.Car;
import src.model.CarQueue;
import src.model.Location;
import src.view.SimView;

import javax.swing.*;

public class SimController /* extends AbstractController implements ActionListener */ {

    private CarQueue entranceCarQueue;  // A queue for the cars to enter
    private CarQueue paymentCarQueue;   // A queue for the cars to spend their dolla dolla bill y'all
    private CarQueue exitCarQueue;      // A queue for the cars to exit
    private SimView simView;            // This SimView object is our main view, displaying our main garage graphics

    private int day = 0;                // Initial value for the current day
    private int hour = 0;               // Initial value for the current hour
    private int minute = 0;             // Initial value for the current minute

    private int tickPause = 100;

    int weekDayArrivals = 50;           // Average number of cars per hour
    int weekendArrivals = 90;           // Average number of cars per hour

    int enterSpeed = 3;                 // Number of cars that can enter per minute/iteration
    int paymentSpeed = 10;              // Number of cars that can pay per minute/iteration
    int exitSpeed = 9;                  // Number of cars that can exit/leave per minute/iteration

    /**
     * Instantiate the fields of SimController
     */
    public SimController() {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simView = new SimView(3, 6, 30); // Creates a new SimView with 3 floors, 6 rows and 30 places in total
    }

    /**
     * When the button 1 step or 100 steps is called, it runs this method.
     * @param numberOfSteps
     */
    public void run(int numberOfSteps) {
        Thread t = new Thread(new Runnable() { // New thread seperate from the other thread
            public void run() {
                // Depending on which button you pressed, call the thick() method an x amount of times.
                for (int i = 0; i < numberOfSteps; i++) {
                    tick();
                }
            }
        });
        t.start(); //Start the thread. Thread dies when his task is finished
    }

    /**
     * A pretty complex class with a lot of code in it.
     * In summary; Every step advances the time with just 1 minute.
     * So when you called the 100 steps button, the time advances with 100 minutes.
     */
    private void tick() {
        // Advance the time by one minute.
        minute++;
        // Reset the minute timer if it's over 59 minutes
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        // Reset the hour timer if it's over 23 hours
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        // Reset the day timer if it's over 6 days
        while (day > 6) {
            day -= 7;
        }

        // Create a new Random object
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        // If the current day is lower than 5 (monday, tuesday, wednesday, thursday), this
        // variable holds the value of weekDayArrivals field, else (friday, saturday, sunday) it holds
        // the value of the weekendArrivals field
        int averageNumberOfCarsPerHour = day < 5
                ? weekDayArrivals
                : weekendArrivals;

        // Calculate the number of cars that arrive this minute/current tick()
        double standardDeviation = averageNumberOfCarsPerHour * 0.1;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        int numberOfCarsPerMinute = (int)Math.round(numberOfCarsPerHour / 60);

        // Add the cars to the back of the queue.
        // Lets say that the average numberOfCarsPerMinute is 9. That means that in it's current iteration/tick
        // we create 9 new AdHocCars() and we all add them to our entranceCarQueue
        for (int i = 0; i < numberOfCarsPerMinute; i++) {
            Car car = new AdHocCar();

            // Generate a random boolean and add it to the car field
            boolean randomBool = new Random().nextBoolean();
            car.setIsParkingPassHolder(randomBool); // if true, the car is a parking pass holder

            entranceCarQueue.addCar(car);
        }

        // Remove car from the front of the queue and assign to a parking space.
        // As we have added let's say 9 Cars to our entranceCarQueue, we have just space to let 3 cars (see field, int enterSpeed = 3)
        // enter the garage per minute/tick/iteration. So it loops 3 times and then assigns the first 3 cars to their reserved spot.
        // All cars drive immediately to the first free spot at the moment. The car then gets assigned their stayMinutes.
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

        // Perform car park tick. This method loops over all the places / garage spots,
        // finds a car and then removes a minute from their stayMinutes timer.
        simView.tick();

        // Add leaving cars to the exit queue. The getFirstLeavingCar method checks for cars who's time is up.
        // The while loop stops when all current spots are not yet ready to leave. In every loop a car is sent to the
        // paymentCarQueue
        while (simView.getFirstLeavingCar() != null) {
            Car car = simView.getFirstLeavingCar();
            // TODO temporarily mabye. Even the parkingpassholders get this field set to true because it then skips the car on the next loop
            car.setIsPaying(true);
            if (car.getIsParkingPassHolder()) {
                // If the first Leaving car is a parkingPassHolder then remove the car and add them to
                // the exitCarQueue immediately
                simView.removeCarAt(car.getLocation());
                exitCarQueue.addCar(car);
                continue;   // Skip the paymentCarQueue add() method
            }
            paymentCarQueue.addCar(car);
        }

        // Remove cars from the paymentCarQueue queue and add them to the exitCarQueue.
        // The amount of payments are limited by the integer in the paymentSpeed field just like entranceCarQueue
        for (int i = 0; i < paymentSpeed; i++) {
            Car car = paymentCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // TODO Handle payment.
            simView.removeCarAt(car.getLocation());
            exitCarQueue.addCar(car);
        }

        // Remove cars from the garage completely, again, limited by the amount of the exitSpeed field.
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

        // Update the car park view (fill the colors etc) when all these calculations are done.
        simView.updateView();

        // Pause.
        // I have no idea about threads and exceptions at this moment.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
