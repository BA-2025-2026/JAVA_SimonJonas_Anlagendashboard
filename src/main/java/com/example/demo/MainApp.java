package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application class.
 * Handles the game loop, player input, collision checks,
 * and switching between game screens.
 *
 * @author mosermichae
 */
public class MainApp extends Application {

    private Player player;
    private Rectangle playerRectangle;

    private Text timerText;
    private double timeElapsed = 0;

    private boolean gameOver = false;

    private Stage currentStage; // To keep track of the current game window

    private EnemyManager enemyManager;

    // Timelines for movement, timer, and enemy spawn/movement
    private Timeline movementTimeline;
    private Timeline timerTimeline;

    @Override
    public void start(Stage primaryStage) {
        // Passes a reference to startGame so WelcomeScreen can start the game
        new WelcomeScreen(primaryStage, this::startGame);
    }

    private void startGame(Player player) {
        this.player = player;  // Assign the Player object passed from WelcomeScreen

        currentStage = new Stage();
        resetGameVariables();

        playerRectangle = new Rectangle(30, 30, Color.BLUE);
        playerRectangle.setTranslateX(player.getX());
        playerRectangle.setTranslateY(player.getY());

        Pane root = new Pane();
        root.getChildren().add(playerRectangle);

        timerText = new Text(10, 20, "Time: 0");
        root.getChildren().add(timerText);

        Scene scene = new Scene(root, 400, 400);

        scene.setOnKeyPressed(this::handleKeyPress);
        scene.setOnKeyReleased(this::handleKeyRelease);

        // Runs roughly 60 times per second to update movement and collisions
        movementTimeline = new Timeline(
                new KeyFrame(Duration.millis(16), e -> updateGame())
        );
        movementTimeline.setCycleCount(Timeline.INDEFINITE);
        movementTimeline.play();

        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();

        // Initialize the enemy manager and start the enemy logic
        enemyManager = new EnemyManager();
        enemyManager.startEnemyLogic(root);

        currentStage.setTitle("Smooth Square Movement with Timer");
        currentStage.setScene(scene);
        currentStage.show();
    }

    /**
     * Reset game variables to start a fresh instance without previous values
     */
    private void resetGameVariables() {
        timeElapsed = 0;
        gameOver = false;
    }

    /**
     * WASD Movement Bindings for the player
     * The Release of the Buttons will be handled in the below method handleKeyRelease()
     *
     * @param event the key which has been pressed
     */
    private void handleKeyPress(KeyEvent event) {
        if (gameOver) return;  // Don't allow movement if the game is over

        switch (event.getCode()) {
            case W:
                player.setMoveUp(true);
                break;
            case A:
                player.setMoveLeft(true);
                break;
            case S:
                player.setMoveDown(true);
                break;
            case D:
                player.setMoveRight(true);
                break;
        }
    }

    /**
     * WASD Movement Bindings for the player
     * The Press of the Buttons will be handled in the above method handleKeyPress()
     *
     * @param event the key which has been pressed
     */
    private void handleKeyRelease(KeyEvent event) {
        if (gameOver) return;  // Don't allow movement if the game is over

        switch (event.getCode()) {
            case W:  // Stop moving up
                player.setMoveUp(false);
                break;
            case A:  // Stop moving left
                player.setMoveLeft(false);
                break;
            case S:  // Stop moving down
                player.setMoveDown(false);
                break;
            case D:  // Stop moving right
                player.setMoveRight(false);
                break;
        }
    }

    /**
     * Update the game / move the player and update the position on the Window
     */
    private void updateGame() {
        // Move the player based on key presses
        player.move();

        // Update the player's position on the screen
        playerRectangle.setTranslateX(player.getX());
        playerRectangle.setTranslateY(player.getY());

        // Check for collisions with enemies
        if (enemyManager.checkCollisions(playerRectangle)) {
            stopGame();  // End the game on collision
        }
    }

    /**
     * Update the timer (increment the time and update the display
     */
    private void updateTimer() {
        if (gameOver) return;  // Don't update the timer if the game is over

        timeElapsed++;
        timerText.setText("Time: " + timeElapsed);  // Update the timer text
    }

    /**
     * Stop the game and show the game over state (LeaderBoard)
     */
    private void stopGame() {
        gameOver = true;
        timerText.setText("Game Over! Time: " + timeElapsed);
        enemyManager.stopEnemies();  // Stop enemy movement
        new Leaderboard(player, (int) timeElapsed, currentStage);  // Show leaderboard
    }

    public static void main(String[] args) {
        launch(args);
    }
}
