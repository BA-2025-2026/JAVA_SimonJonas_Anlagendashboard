package net.ictcampus.semodul.anlagendashboard.utility;

import java.time.LocalDateTime;

/**
 * Use this ErrorDto to package error messages and send them through the controller back to whoever called the API.
 * Timestamp is auto generated. Simply pass in a string with your error message, for example e.getMessage()
 */
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
