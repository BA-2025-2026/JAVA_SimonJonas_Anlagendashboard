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
 * endpoints of the mockup API.
 *
 * @author jve
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
        userIdField.setPromptText("User ID");
        Button findUserByIdButton = new Button("Find User by ID");
        // Send a request to getUserByIdEndpoint in the user controller
        findUserByIdButton.setOnAction(e -> {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                userController.getUserByIdEndpoint(userId);
                userIdField.clear();
            } catch (NumberFormatException er) {
                System.out.println("User ID needs to be a number (integer): " + er.getMessage());
            }
        });

        Button getPortfolioMetricsByUserIdButton = new Button("Get Portfolio Metrics by User ID");
        getPortfolioMetricsByUserIdButton.setOnAction(e -> {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                portfolioMetricsController.getPortfolioMetricsByUserIdEndpoint(userId);
                userIdField.clear();
            } catch (NumberFormatException er) {
                System.out.println("User ID needs to be a number (integer): " + er.getMessage());
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
