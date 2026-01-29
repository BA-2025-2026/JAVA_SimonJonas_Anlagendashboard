package net.ictcampus.semodul.anlagendashboard.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.ictcampus.semodul.anlagendashboard.portfoliometrics.*;
import net.ictcampus.semodul.anlagendashboard.user.UserController;
import net.ictcampus.semodul.anlagendashboard.user.UserDao;
import net.ictcampus.semodul.anlagendashboard.user.UserJdbcDao;
import net.ictcampus.semodul.anlagendashboard.user.UserService;

/**
 * Main application class.
 * Does the Setup and rendering for JavaFX GUI: The GUI is used to trigger requests to the
 * endpoints of the API.
 */
public class App extends Application {

    // Dependency Injection
    UserDao userDao = new UserJdbcDao();
    UserService userService = new UserService(userDao);
    UserController userController = new UserController(userService);
    PriceDao priceDao = new PriceJdbcDao();
    UserAssetDao userAssetDao = new UserAssetJdbcDao();
    PortfolioMetricsService portfolioMetricsService = new PortfolioMetricsService(priceDao, userAssetDao);
    PortfolioMetricsController portfolioMetricsController = new PortfolioMetricsController(portfolioMetricsService);

    @Override
    public void start(Stage stage) {
        // GetUserById text field and button
        TextField userIdField = new TextField();
        userIdField.setPromptText("Please enter a User ID (needs to be a positive, whole number)");

        // getUserByIdEndpoint Button and event handling
        Button findUserByIdButton = new Button("Find User by ID");
        findUserByIdButton.setOnAction(e -> {
            try {
                if (userIdField.getText().isBlank()) {
                    throw new IllegalArgumentException("Cannot send a request with empty entry field.");
                }
                int userId = Integer.parseInt(userIdField.getText());
                userController.getUserByIdEndpoint(userId);
                userIdField.clear();
            } catch (NumberFormatException numEx) {
                System.out.println("User ID needs to be a number (integer): " + numEx.getMessage());
            } catch (IllegalArgumentException illArgEx) {
                System.out.println(illArgEx.getMessage());
            }
        });

        // getPortfolioMetricsByUserIdEndpoint Button and event handling
        Button getPortfolioMetricsByUserIdButton = new Button("Get Portfolio Metrics by User ID");
        getPortfolioMetricsByUserIdButton.setOnAction(e -> {
            try {
                if (userIdField.getText().isBlank()) {
                    throw new IllegalArgumentException("Cannot send a request with empty entry field.");
                }
                int userId = Integer.parseInt(userIdField.getText());
                portfolioMetricsController.getPortfolioMetricsByUserIdEndpoint(userId);
                userIdField.clear();
            } catch (NumberFormatException numEx) {
                System.out.println("User ID needs to be a number (integer): " + numEx.getMessage());
            } catch (IllegalArgumentException illArgEx) {
                System.out.println(illArgEx.getMessage());
            }
        });

        // Create layout and add elements (button)
        VBox vBox = new VBox();
        vBox.getChildren().addAll(userIdField, findUserByIdButton, getPortfolioMetricsByUserIdButton);

        // Create scene (window content)
        Scene scene = new Scene(vBox, 600, 400);

        // Configure Stage (window itself)
        stage.setTitle("Anlagendashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
