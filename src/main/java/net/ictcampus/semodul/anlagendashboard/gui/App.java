package net.ictcampus.semodul.anlagendashboard.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main application class.
 * Does the Setup for JavaFX GUI: The GUI is used to trigger requests to the
 * endpoints of the mockup API.
 *
 * @author jve
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        // GetUserById text field and button
        TextField userIdField = new TextField();
        userIdField.setPromptText("User ID");
        Button btn = new Button("Find User by ID");
        btn.setOnAction(e -> System.out.println("Test from GUI: Find user by id " + userIdField.getText()));

        // Create layout and add elements (button)
        VBox vBox = new VBox();
        vBox.getChildren().addAll(userIdField, btn);

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
