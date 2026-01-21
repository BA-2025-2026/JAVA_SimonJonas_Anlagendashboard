package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * EnemyManager is used to controll the individual Enemy Threads.
 * It consists of declaring, initialising and finally spawning the Enemy Objects at random.
 *
 * @author mosermichae
 */
public class EnemyManager {

    private final List<Rectangle> enemies = new ArrayList<>();
    private static final double ENEMY_SPEED = 5;
    private static final int SPAWN_INTERVAL = 500;  // 0.5 seconds
    private boolean gameOver = false;
    private final Random random = new Random();

    /**
     * We use Timelines to manage the individual movements of the enemy objects.
     *
     * @param root Spawn enemies on the correct Window
     */
    public void startEnemyLogic(Pane root) {
        Timeline spawnTimeline = new Timeline(new KeyFrame(Duration.millis(SPAWN_INTERVAL), e -> spawnEnemy(root)));
        spawnTimeline.setCycleCount(Timeline.INDEFINITE);
        spawnTimeline.play();

        Timeline moveTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> moveEnemies()));
        moveTimeline.setCycleCount(Timeline.INDEFINITE);
        moveTimeline.play();
    }

    /**
     * Method to spawn a new Enemy.
     * No Enemies will be spawned, if the gamestate "gameover" is true
     *
     * @param root Window in which enemies will be placed
     */
    private void spawnEnemy(Pane root) {
        if (gameOver) return;

        Rectangle enemy = new Rectangle(20, 20, Color.RED);
        enemy.setTranslateX(random.nextInt(380));  // Random X position
        enemy.setTranslateY(random.nextInt(380));  // Random Y position
        root.getChildren().add(enemy);
        enemies.add(enemy);
    }

    /**
     * Method to move all enemies at random
     * Enemies move in a random direction each update step
     * directionX and directionY can be either -1, 0, or 1
     */
    private void moveEnemies() {
        if (gameOver) return;

        for (Rectangle enemy : enemies) {
            int directionX = random.nextInt(3) - 1;
            int directionY = random.nextInt(3) - 1;

            enemy.setTranslateX(enemy.getTranslateX() + directionX * ENEMY_SPEED);
            enemy.setTranslateY(enemy.getTranslateY() + directionY * ENEMY_SPEED);
        }
    }

    /**
     * Collision-Detection Method. If the Bounds of an enemy rectangle or the player rectangle overlap.
     * If Overlapping occurs, the method will return true and the gameOver Flag will be triggered.
     *
     * @param playerRectangle Player Object which is being moved
     * @return false if no collision is detected, true if collision has occured
     */
    public boolean checkCollisions(Rectangle playerRectangle) {
        for (Rectangle enemy : enemies) {
            if (playerRectangle.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                gameOver = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Method to stop all enemy movement
     */
    public void stopEnemies() {
        gameOver = true;
        enemies.clear();
    }
}
