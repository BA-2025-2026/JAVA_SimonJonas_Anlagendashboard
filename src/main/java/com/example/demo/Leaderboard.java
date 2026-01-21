package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

/**
 * Closes the current game window and starts a new game instance
 *
 * @author mosermichae
 */
public class Leaderboard {

    public Leaderboard(Player player, int timeElapsed, Stage currentStage) {
        show(player, timeElapsed, currentStage);
    }

    /**
     *
     * @param player username which has been supplied previously
     * @param timeElapsed How long did the player survive
     * @param currentStage new window instead of the game window
     */
    private void show(Player player, int timeElapsed, Stage currentStage) {
        Stage leaderboardStage = new Stage();
        Pane root = new Pane();

        Text leaderboardText = new Text(50, 50, "Leaderboard");
        Text playerScore = new Text(50, 100, player.getPlayerName() + " - Time: " + timeElapsed);  // Use player's name from Player object

        Button retryButton = new Button("Retry");
        retryButton.setTranslateX(50);
        retryButton.setTranslateY(150);
        retryButton.setOnAction(e -> {
            leaderboardStage.close();
            restartGame(currentStage);  // Restart the game on button click
        });

        root.getChildren().addAll(leaderboardText, playerScore, retryButton);

        Scene scene = new Scene(root, 400, 400);
        leaderboardStage.setTitle("Leaderboard");
        leaderboardStage.setScene(scene);
        leaderboardStage.show();
    }

    /**
     * Give the Player an Option to restart the game
     *
     * @param currentStage
     */
    private void restartGame(Stage currentStage) {
        currentStage.close();
        MainApp mainApp = new MainApp();
        mainApp.start(new Stage());
    }
}
