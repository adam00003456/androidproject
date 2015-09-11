package com.example.adam.project;

/**
 * Created by Adam on 12/21/2014.
 */

public class Bullet {
    private int x, y, speed;
    private boolean visible;


    public Bullet(int startingX, int startingY, int speed) {
        x = startingX;
        y = startingY;
        this.speed = speed;
        visible = true;
    }

    public void setVisibility(){
        visible = false;
    }

    public void updateenemybullet(int heightofscreen) {
        y += speed;
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


    public boolean isVisible() {
        return visible;
    }

}
