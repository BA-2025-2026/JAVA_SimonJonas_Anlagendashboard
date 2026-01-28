package net.ictcampus.semodul.anlagendashboard.utility;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String message) {
        super(message);
    }
    public NoDataFoundException() {
        super("Error due to no data found.");
    }
}
