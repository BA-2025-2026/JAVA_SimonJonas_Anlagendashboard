package com.example.demo;

public class Player {
    private double x, y;
    private double speed;
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private String playerName;

    public Player(double startX, double startY, double speed, String playerName) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.playerName = playerName;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public String getPlayerName() { return playerName; }

    public void move() {
        if (moveUp) y -= speed;
        if (moveDown) y += speed;
        if (moveLeft) x -= speed;
        if (moveRight) x += speed;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }
    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }
    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }
    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }
}
