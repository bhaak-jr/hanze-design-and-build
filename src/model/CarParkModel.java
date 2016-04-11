package src.model;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;

/**
 * Model subclass.
 * Created by Bas Haaksema on 05-Apr-16.
 */
public class CarParkModel extends AbstractModel implements Runnable {
    private boolean run;

    private CarQueue entranceCarQueue;  // A queue for the cars to enter
    private CarQueue paymentCarQueue;   // A queue for the cars to spend their dolla dolla bill y'all
    private CarQueue exitCarQueue;      // A queue for the cars to exit

    private int day = 0;                // Initial value for the current day
    private int hour = 0;               // Initial value for the current hour
    private int minute = 0;             // Initial value for the current minute

    private int tickPause = 50;

    private int weekDayArrivals = 50;           // Average number of cars per hour
    private int weekendArrivals = 90;           // Average number of cars per hour

    private int enterSpeed = 3;                 // Number of cars that can enter per minute/iteration
    private int paymentSpeed = 10;              // Number of cars that can pay per minute/iteration
    private int exitSpeed = 9;                  // Number of cars that can exit/leave per minute/iteration

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private Car[][][] cars;

    private boolean reservationCarPassHolder = false; //@dirty pease think of a better method currently used in the reserve method

    public CarParkModel(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();

        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        notifyViews();
    }

    public void start() {
        if (!run) {
            new Thread(this).start();
        }
    }

    public void stop() {
        run = false;
    }

    @Override
    public void run() {
        run = true;
        while (run) {
            tick();
        }
    }

    // TODO Secure threading
    public void run(int numberOfSteps) {
        if (!run) {
            new Thread(() -> {
                for (int i = 0; i < numberOfSteps; i++) {
                    tick();
                }
            }).start();
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
        int numberOfCarsPerMinute = (int) Math.round(numberOfCarsPerHour / 60);

        // Add the cars to the back of the queue.
        // Lets say that the average numberOfCarsPerMinute is 9. That means that in it's current iteration/tick
        // we create 9 new AdHocCars() and we all add them to our entranceCarQueue
        for (int i = 0; i < numberOfCarsPerMinute; i++) {
            Car car = new AdHocCar();

            // Generate a random boolean and add it to the car field
            boolean randomBool = new Random().nextBoolean();
            car.setIsParkingPassHolder(randomBool); // if true, the car is a parking pass holder

            // Generate a random int to see if the driver is a bad parker or not
            int randomInt = new Random().nextInt(100);
            car.setIsBadParker(randomInt);

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
            if (car instanceof ReservationCar) {
                ReservationCar reservationCar = (ReservationCar) car;
                Location reservedLocation = getFirstReservedLocation(reservationCar);
                setCarAt(reservedLocation, car);
                int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                car.setMinutesLeft(stayMinutes);
                continue;
            }
            // Find a space for this car.
            Location freeLocation = getFirstFreeLocation();
            if (freeLocation != null) {
                setCarAt(freeLocation, car);
                if(car.getIsBadParker()) { // TODO Generates 2 different times so 1 car leaves before the other
                    Location secondFreeLocation = getFirstFreeLocation();
                    setCarAt(secondFreeLocation, car);
                }
                int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                car.setMinutesLeft(stayMinutes);
            }
        }

        // Perform car park tick. This method loops over all the places / garage spots,
        // finds a car and then removes a minute from their stayMinutes timer.
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
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
            Car car = getFirstLeavingCar();
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
            Car car = paymentCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // TODO Handle payment.
            removeCarAt(car.getLocation());
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

        // Update the car park view (fill the colors etc) when all these calculations are done.
        notifyViews();

        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setTickPause(int myTickPause) { tickPause = myTickPause; }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    private boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            return true;
        }
        return false;
    }

    private Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        return car;
    }

    private Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    private Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        return !(floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces);
    }

    /**
     * Get the amount of current spots free (empty locations)
     *
     * @return int Free spots
     */
    private int getFreeLocationAmount() {
        int freeLocationAmount = 0;
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car == null) {
                        freeLocationAmount++;
                    }
                }
            }
        }
        return freeLocationAmount;
    }

    /**
     * Get the amount of current spots free (empty locations)
     *
     * @return int Free spots
     */
    public int getAmountOfCarsInThePark() {
        int totalSpace = (numberOfFloors * numberOfRows * numberOfPlaces);
        return totalSpace - getFreeLocationAmount();
    }

    /**
     * TODO Check this.
     * Might be a little bit dirty because I used a concrete class.
     * I did this mainly because the school assignment says I had to make a new reservation customer/car class.
     * @param   car
     * @return  Location
     */
    private Location getFirstReservedLocation(ReservationCar car) {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    if (floor == car.getReservationFloor() && row == car.getReservationRow() && place == car.getReservationPlace()) {
                        return car.getReservedLocationModel();
                    }
                }
            }
        }
        return null;
    }

    public void reserve() {
        JFrame reserveFrame = new JFrame("Reserve a spot!");
        reserveFrame.setSize(600, 200);

        Container reserveFrameContainer = reserveFrame.getContentPane();

        JTextField floor = new JTextField(5);
        JTextField row = new JTextField(5);
        JTextField place = new JTextField(5);
        JCheckBox parkpass = new JCheckBox();
        JButton reserveButton = new JButton("Reserve!");

        JPanel reservePanel = new JPanel();
        reservePanel.add(new JLabel("Floor"));
        reservePanel.add(floor);
        reservePanel.add(new JLabel("Row"));
        reservePanel.add(row);
        reservePanel.add(new JLabel("Place"));
        reservePanel.add(place);
        reservePanel.add(new JLabel("Is this a parking pass holder(check box)?"));
        reservePanel.add(parkpass);
        reservePanel.add(reserveButton);



        parkpass.addItemListener(e ->  {
            reservationCarPassHolder = e.getStateChange() == ItemEvent.SELECTED;
        });
        reserveButton.addActionListener(e ->  {
                try {
                    int floorNumber = Integer.parseInt(floor.getText()) - 1;
                    int rowNumber = Integer.parseInt(row.getText()) - 1;
                    int placeNumber = Integer.parseInt(place.getText()) - 1;
                    if(floorNumber < numberOfFloors && rowNumber < numberOfRows && placeNumber < numberOfPlaces) {
                        if(!reservationCarPassHolder) { //when false, handle it as a normal reservation
                            entranceCarQueue.addCar(new ReservationCar(floorNumber, rowNumber, placeNumber));
                        }else{ //add a parkingpasscar
                            Car car = new ReservationCar(floorNumber, rowNumber, placeNumber);
                            car.setIsParkingPassHolder(true);
                            entranceCarQueue.addCar(car);
                        }
                        JOptionPane.showMessageDialog(reserveFrame, "This car has reserved it's spot. Please add another car or press start.");
                    } else {
                        throw new NullPointerException();
                    }
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(reserveFrame, "Please enter correct data");
                } catch(NullPointerException ex) {
                    JOptionPane.showMessageDialog(reserveFrame, "Place doesn't exist");
                }
        });
        reserveFrameContainer.add(reservePanel);

        reserveFrame.pack();
        reserveFrame.setVisible(true);
    }

    /**
     * Get the size of the entrance car queue
     * @return int Size of the entrance car queue
     */
    public int getEntranceCarQueueSize(){
        return entranceCarQueue.getSize();
    }

    /**
     * Get the size of the payment car queue
     * @return int Size of the entrance car queue
     */
    public int getPaymentCarQueueSize(){
        return paymentCarQueue.getSize();
    }

    /**
     * Get the size of the exit car queue
     * @return int Size of the exit car queue
     */
    public int getExitCarQueueSize(){
        return exitCarQueue.getSize();
    }

    /**
     * Get the current time
     * @return String The current time
     */
    public String getCurrentTime(){
        return "day: " + day + " hour: " + hour + " minute: " + minute;
    }

    /**
     * Get the enter speed
     * @return int The enterspeed
     */
    public int getEnterSpeed(){
        return enterSpeed;
    }

    /**
     * Get the exit speed
     * @return int The exit speed
     */
    public int getExitSpeed(){
        return exitSpeed;
    }

    /**
     * Get the payment speed
     * @return int The payment speed
     */
    public int getPaymentSpeed(){
        return paymentSpeed;
    }
}
