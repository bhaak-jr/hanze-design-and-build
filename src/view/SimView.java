package src.view;
import javax.swing.*;

import src.model.Car;
import src.model.Location;

import java.awt.*;

//Import simulator object needed for the buttons.
//Please think about better ways of doing this and share it with me ~Bas.
import static src.main.Simulator.sim;

public class SimView extends JFrame {
    private CarParkView carParkView;    // Inner class CarParkView (soon to be an extern class?)
    private JButton stepButton1;        // Button for 1 step
    private JButton stepButton100;      // Button for 100 steps
    private JPanel panel1;              // The panel which holds the step 1 and step 100 buttons
    private int numberOfFloors;         // Number of floors
    private int numberOfRows;           // Number of rows
    private int numberOfPlaces;         // Number of places
    private Car[][][] cars;             // 3 dimensional array to hold Car objects

    /**
     * This constructor does a lot of things. It instantiates the number of floors/rows/places.
     * It then instantiates the cars field as a multidimensional array with their respective number
     * of data it can hold from floors, rows, places (every floor can hold 6 rows in total, every row can
     * hold 30 places in total), etc. All these places can be filled with Car objects.
     *
     * @param numberOfFloors the number of floors to be created (as seen in the GUI)
     * @param numberOfRows   the number of rows to be created (as seen in the GUI)
     * @param numberOfPlaces the number of places to be created (as seen in the GUI)
     */
    public SimView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        // Instantiate a new CarParkView object without parameters.
        // defaults to (int 0, int 0). This is object is the heart of our fancy designed simulation.
        carParkView = new CarParkView();

        /****************************************************
            We're talking about the main JFrame from here.
         ****************************************************/

        // Get the content pane / container from the current JFrame
        Container contentPane = getContentPane();
        
        // Make a button for 1 step
        stepButton1 = new JButton(); // Make the button
        stepButton1.setText("1 step"); // Set the text
        // To ensure that the button does something, we add an action listener
        stepButton1.addActionListener(e -> {
            // ^ We used a lambda as a shortcut to create an anonymous inner class
            sim.run(1); // We imported the static Simulator field (SimController object) to call this object's run method 1 time.
        });

        // Make a button for 100 steps
        stepButton100 = new JButton(); // Make the button
        stepButton100.setText("100 steps"); //set the text
        // To ensure that the button does something, we add an action listener
        stepButton100.addActionListener(e -> {
            // ^ We used a lambda as a shortcut to create an anonymous inner class
            sim.run(100); // We imported the static Simulator (SimController object) to call this object's run method 100 times.
        });
        
        // Make a new panel
        // And add the two buttons (step 1 and step 100)
        panel1 = new JPanel();
        panel1.add(stepButton1);
        panel1.add(stepButton100);

        // Add the panels to the main Container of the main JFrame
        contentPane.add(panel1, BorderLayout.NORTH); // Add the panel to position north
        contentPane.add(carParkView, BorderLayout.CENTER); // Add the panel to position center
        //contentPane.add(population, BorderLayout.SOUTH);
        pack(); // Arrange everything
        setVisible(true);   // Make the frame visible

        updateView(); // Call the updateView() method in this class.
    }

    /**
     * Creates the main image of garage in the main JFrame.
     */
    public void updateView() {
        carParkView.updateView();
    }

    /**
     * Get the number of floors
     * @return number of floors
     */
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Get the number of rows
     * @return  number of rows
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Get the number of places
     * @return number of places
     */
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     * Get a Car object based on the location.
     * @param location  A location object
     * @return Car      The car that's parked on that location
     */
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * Set a car on a certain location
     * @param location  A location object
     * @param car       A car object
     * @return          Returns true if it's successfully set, else false.
     */
    public boolean setCarAt(Location location, Car car) {
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

    /**
     * Remove a car on a certain location. Sets the location to null on a car object, and sets coordinates in the
     * 3 dimensional cars array also to null.
     * @param location  A location object
     * @return Car The recently removed car object
     */
    public Car removeCarAt(Location location) {
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

    /**
     * Loops over all the floors. Gets the first free location.
     * @return Location     A location object
     */
    public Location getFirstFreeLocation() {
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

    /**
     * Loops over all the floors, and when the car has no minutes left on it's timer, and is not paying at the
     * same time, then return the first car that's encountered.
     * @return Car  The Car object
     */
    public Car getFirstLeavingCar() {
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

    /**
     Loops over all floors to get all the Cars if the spot is not empty, then call the tick method.
     (Removing -1 minute from their timer)
     */
    public void tick() {
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
    }


    /**
     * Checks if a location is valid.
     */
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

    /**
     * Inner class, is able to generate the big main image you see (Image of the garage, fills the colors etc).
     */
    private class CarParkView extends JPanel {
        
        private Dimension size;
        private Image carParkImage;    
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0);
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }
    
        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }

        /**
         * This method updates the view of garage. If for example, the window changes, or there is a car
         * on a certain spot in the garage, it updates the color to red etc. Pretty sure updateView
         *  does all the work in creating the garage graphics instead of the paintComponent() method (or in combination?)
         */
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
            for(int floor = 0; floor < getNumberOfFloors(); floor++) {
                for(int row = 0; row < getNumberOfRows(); row++) {
                    for(int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        // If there isn't a car on the location spot, make it white, else red
                        Color color = car == null ? Color.white : Color.red;
                        // If there is a car assign on the location AND it's a parkingpass holder
                        // overwrite the red color and make it blue
                        if(car != null && car.getIsParkingPassHolder()) {
                            color = Color.blue;
                        }
                        drawPlace(graphics, location, color);
                    }
                }
            }
            repaint();
        }
    
        /**
         * Paint a place on this car park view in a given color.
         * This is the red color you see when a car parks in the garage.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }

}
