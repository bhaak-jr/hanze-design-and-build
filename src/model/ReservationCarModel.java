package src.model;

/**
 * Created by Danny on 7-4-2016.
 */
public class ReservationCarModel extends CarModel {

    private LocationModel reservedLocation;

    public ReservationCarModel(int floor, int row, int place) {
        reservedLocation = new LocationModel(floor, row, place);
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

    public LocationModel getReservedLocationModel() {
        return reservedLocation;
    }



}
