package net.ictcampus.semodul.anlagendashboard.user;

import net.ictcampus.semodul.anlagendashboard.utility.ErrorDto;
import net.ictcampus.semodul.anlagendashboard.utility.JsonUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint GET /users/{id}
    public void getUserByIdEndpoint(int userId) {
        System.out.println("==== RESPONSE of GET /users/{id} ====");

        try {
            // Call to userService
            UserDto userDto = userService.getUserById(userId);

            if (userDto == null) {
                // Service didn't find a user for the requested id
                ErrorDto errorDto = new ErrorDto("Didn't find a user with id " + userId);
                System.out.println(JsonUtil.toJson(errorDto));
            } else {
                // Success: Return user information
                System.out.println(JsonUtil.toJson(userDto));
            }

        } catch (IllegalArgumentException e) {
            // Catch error from Service
            ErrorDto errorDto = new ErrorDto("Invalid request: " + e.getMessage());
            System.out.println(JsonUtil.toJson(errorDto));
        } catch (RuntimeException e) {
            // Catch error from JDBC etc.
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            System.out.println(JsonUtil.toJson(errorDto));
        }
    }
}
