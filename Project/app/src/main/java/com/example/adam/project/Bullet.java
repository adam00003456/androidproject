package com.example.adam.project;

/**
 * Created by Adam on 12/21/2014.
 */

public class Bullet {
    private int x, y, speed;
    private boolean visible;


    public Bullet(int startingX, int startingY) {
        x = startingX;
        y = startingY;
        speed = 7;
        visible = true;
    }

    public void setVisibility(){
        visible = false;
    }

    public void updateenemybullet(int heightofscreen) {
        y += speed;
        //we can make this more dynamic
        if (y > (int)(.90 * heightofscreen)) {
            visible = false;
        }
    }

    public void update() {
        y -= speed;
        if (y <= 0) {
            visible = false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setSpeed(int speed) { this.speed = speed; }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) { this.x = x; }
}
