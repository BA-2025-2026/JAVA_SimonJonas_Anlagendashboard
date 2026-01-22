package net.ictcampus.semodul.anlagendashboard.utility;

import java.time.LocalDateTime;

public class ErrorDto {
    private String message;
    private String timestamp;

    public ErrorDto(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
