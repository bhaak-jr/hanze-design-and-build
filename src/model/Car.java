package src.model;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean isParkingPassHolder;
    /**
     * Constructor for objects of class Car
     */
    public Car() {

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    public boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getIsParkingPassHolder() {
        return isParkingPassHolder;
    }

    public void setIsParkingPassHolder(boolean bool) {
        this.isParkingPassHolder = bool;
    }

    /**
     * Removes a minute from their minutesLeft field. The car is getting removed when their time is up.
     */
    public void tick() {
        minutesLeft--;
    }

}