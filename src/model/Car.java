package src.model;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean isParkingPassHolder;
    private boolean isBadParker;

    /**
     * Constructor for objects of class Car
     */
    Car() {

    }

    Location getLocation() {
        return location;
    }

    void setLocation(Location location) {
        this.location = location;
    }

    int getMinutesLeft() {
        return minutesLeft;
    }

    void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    boolean getIsPaying() {
        return isPaying;
    }

    void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getIsParkingPassHolder() {
        return isParkingPassHolder;
    }

    void setIsParkingPassHolder(boolean bool) {
        this.isParkingPassHolder = bool;
    }

    public void setIsBadParker(int probability) {
        if(probability < 10) {
            isBadParker = true;
        }
    }

    public boolean getIsBadParker() {
        return isBadParker;
    }

    /**
     * Removes a minute from their minutesLeft field. The car is getting removed when their time is up.
     */
    void tick() {
        minutesLeft--;
    }

}