package src.model;

/**
 * Created by Danny on 7-4-2016.
 */
public class ReservationCar extends Car {

    private Location reservedLocation;

    public ReservationCar(int floor, int row, int place) {
        reservedLocation = new Location(floor, row, place);
    }

    public int getReservationFloor() {
        return reservedLocation.getFloor();
    }

    public int getReservationRow() {
        return reservedLocation.getRow();
    }

    public int getReservationPlace() {
        return reservedLocation.getPlace();
    }

    public Location getReservedLocationModel() {
        return reservedLocation;
    }



}
