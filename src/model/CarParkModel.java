package src.model;

import java.util.Random;

/**
 * Created by Bas Haaksema on 05-Apr-16.
 */
public class CarParkModel extends AbstractModel implements Runnable {
    private boolean run;

    private CarQueueModel entranceCarQueue;  // A queue for the cars to enter
    private CarQueueModel paymentCarQueue;   // A queue for the cars to spend their dolla dolla bill y'all
    private CarQueueModel exitCarQueue;      // A queue for the cars to exit

    private int day = 0;                // Initial value for the current day
    private int hour = 0;               // Initial value for the current hour
    private int minute = 0;             // Initial value for the current minute

    private int tickPause = 100;

    int weekDayArrivals = 50;           // Average number of cars per hour
    int weekendArrivals = 90;           // Average number of cars per hour

    int enterSpeed = 3;                 // Number of cars that can enter per minute/iteration
    int paymentSpeed = 10;              // Number of cars that can pay per minute/iteration
    int exitSpeed = 9;                  // Number of cars that can exit/leave per minute/iteration

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private CarModel[][][] cars;

    public CarParkModel(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        entranceCarQueue = new CarQueueModel();
        paymentCarQueue = new CarQueueModel();
        exitCarQueue = new CarQueueModel();

        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        cars = new CarModel[numberOfFloors][numberOfRows][numberOfPlaces];

        notifyViews();
    }

    public void start() {
        new Thread(this).start();
    }

    public void stop() {
        run=false;
    }

    @Override
    public void run() {
        run = true;
        while(run) {
            tick();
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //A pretty complex class with a lot of code in it.
    //In summary; Every step advances the time with just 1 minute.
    //So when you called the 100 steps button, the time advances with 100 minutes.
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
            CarModel car = new AdHocCarModel();

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
            CarModel car = entranceCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // Find a space for this car.
            LocationModel freeLocation = getFirstFreeLocation();
            if (freeLocation != null) {
                setCarAt(freeLocation, car);
                int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                car.setMinutesLeft(stayMinutes);
            }
        }

        // Perform car park tick. This method loops over all the places / garage spots,
        // finds a car and then removes a minute from their stayMinutes timer.
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    LocationModel location = new LocationModel(floor, row, place);
                    CarModel car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }

        // Add leaving cars to the exit queue. The getFirstLeavingCar method checks for cars who's time is up.
        // The while loop stops when all current spots are not yet ready to leave. In every loop a car is sent to the
        // paymentCarQueue
        while (getFirstLeavingCar() != null) {
            CarModel car = getFirstLeavingCar();
            // TODO temporarily mabye. Even the parkingpassholders get this field set to true because it then skips the car on the next loop
            car.setIsPaying(true);
            if (car.getIsParkingPassHolder()) {
                // If the first Leaving car is a parkingPassHolder then remove the car and add them to
                // the exitCarQueue immediately
                removeCarAt(car.getLocation());
                exitCarQueue.addCar(car);
                continue;   // Skip the paymentCarQueue add() method
            }
            paymentCarQueue.addCar(car);
        }

        // Remove cars from the paymentCarQueue queue and add them to the exitCarQueue.
        // The amount of payments are limited by the integer in the paymentSpeed field just like entranceCarQueue
        for (int i = 0; i < paymentSpeed; i++) {
            CarModel car = paymentCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // TODO Handle payment.
            removeCarAt(car.getLocation());
            exitCarQueue.addCar(car);
        }

        // Remove cars from the garage completely, again, limited by the amount of the exitSpeed field.
        for (int i = 0; i < exitSpeed; i++) {
            CarModel car = exitCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // Bye!
        }

        // Update the car park view (fill the colors etc) when all these calculations are done.
        notifyViews();

        // Pause.
        // I have no idea about threads and exceptions at this moment.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateView() {
        notifyViews();
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public CarModel getCarAt(LocationModel location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    private boolean setCarAt(LocationModel location, CarModel car) {
        if (!locationIsValid(location)) {
            return false;
        }
        CarModel oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            return true;
        }
        return false;
    }

    private CarModel removeCarAt(LocationModel location) {
        if (!locationIsValid(location)) {
            return null;
        }
        CarModel car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        return car;
    }

    private LocationModel getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    LocationModel location = new LocationModel(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    private CarModel getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    LocationModel location = new LocationModel(floor, row, place);
                    CarModel car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    private boolean locationIsValid(LocationModel location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }
}