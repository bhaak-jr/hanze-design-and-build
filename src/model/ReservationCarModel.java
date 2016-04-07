package src.model;

/**
 * Created by Danny on 7-4-2016.
 */
public class ReservationCarModel extends CarModel {

    private LocationModel location;

    public ReservationCarModel(int floor, int row, int place) {
        location = new LocationModel(floor, row, place);
    }

    public LocationModel getReservationSpot() {
        return location;
    }

}
