package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeScreen {

    public interface OnStartGame {
        void startGame(Player player);  // Modify the callback to accept Player instead of just playerName
    }

    public WelcomeScreen(Stage primaryStage, OnStartGame onStartGameCallback) {
        show(primaryStage, onStartGameCallback);
    }

    private void show(Stage primaryStage, OnStartGame onStartGameCallback) {
        Pane root = new Pane();
        Text welcomeText = new Text(100, 100, "Welcome to the Game!");
        Text namePrompt = new Text(100, 150, "Enter your name:");

        TextField nameField = new TextField();
        nameField.setTranslateX(100);
        nameField.setTranslateY(170);

        Button startButton = new Button("Start Game");
        startButton.setTranslateX(100);
        startButton.setTranslateY(200);

        startButton.setOnAction(e -> {
            String playerName = nameField.getText();  // Get the player's name
            if (playerName.isEmpty()) {
                playerName = "Player";  // Default name if nothing is entered
            }

            Player player = new Player(200, 200, 5.0, playerName);  // Create a Player object with the name
            primaryStage.close();  // Close the welcome screen
            onStartGameCallback.startGame(player);  // Notify MainApp to start the game with the Player object
        });

        root.getChildren().addAll(welcomeText, namePrompt, nameField, startButton);

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Welcome Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}