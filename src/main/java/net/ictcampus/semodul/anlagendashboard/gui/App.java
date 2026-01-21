package net.ictcampus.semodul.anlagendashboard.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
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
        // Simple test button
        Button btn = new Button("Test on Console");
        btn.setOnAction(e -> System.out.println("Test from GUI."));

        // Create layout and add elements (button)
        StackPane root = new StackPane();
        root.getChildren().add(btn);

        // Create scene (window content)
        Scene scene = new Scene(root, 600, 400);

        // Configure Stage (window itself)
        stage.setTitle("Anlagendashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
