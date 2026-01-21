package net.ictcampus.semodul.anlagendashboard.user;

import net.ictcampus.semodul.anlagendashboard.utility.ErrorDto;
import net.ictcampus.semodul.anlagendashboard.utility.JsonUtil;

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
            // TODO: Get userDto from service once simon has the method ready.
            UserDto userDto = new UserDto();

            String json = JsonUtil.toJson(userDto);
            System.out.println(json);

        } catch (RuntimeException e) {
            // Create error dto from error message
            ErrorDto errorDto = new ErrorDto(e.getMessage());

            // Convert to JSON and output on console
            String json = JsonUtil.toJson(errorDto);
            System.out.println(json);
        }
    }

}
