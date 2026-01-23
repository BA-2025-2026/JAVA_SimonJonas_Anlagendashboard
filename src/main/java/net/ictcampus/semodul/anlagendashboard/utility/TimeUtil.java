package net.ictcampus.semodul.anlagendashboard.utility;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Contains helper methods for working with timestamps
 */
public class TimeUtil {

    // Prevent creation of instances (private keyword)
    private TimeUtil() {}

    /**
     * @param timestampOne first timestamp
     * @param timestampTwo second timestamp
     * @return difference between the timestamps in milliseconds
     */
    public static long deltaMillis(LocalDateTime timestampOne, LocalDateTime timestampTwo) {
        return Math.abs(Duration.between(timestampOne, timestampTwo).toMillis());
    }
}
