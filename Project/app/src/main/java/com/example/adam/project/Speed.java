package com.example.adam.project;

/**
 * Created by Adam on 12/21/2014.
 */
import com.example.adam.project.controller;

public class Speed {
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_LEFT = -1;
    public static final int DIRECTION_UP = -1;
    public static final int DIRECTION_DOWN = 1;

    private float xv = 1; // velocity on X axis
    private float yv = 1; // velocity on the Y axis

    private int xDirection = DIRECTION_RIGHT;
    private int yDirection = DIRECTION_LEFT;

    public Speed() {
        this.xv = 1;
        this.yv = 1;
    }

    public Speed(float xv, float yv) {
        this.xv = xv;
        this.yv = yv;
    }

    public void setSpeedofSpaceship(float xv, float yv) {
        this.xv = xv;
        this.yv = 1100;
    }

    public float getXv() {
        return xv;
    }

    public float getYv() {
        return yv;
    }

    public void setYv(float yv) {
        this.yv = yv;
    }

    public int getxDirection() {
        return xDirection;
    }

    public void setxDirection(int xDirection) {
        this.xDirection = xDirection;
    }

    public int getyDirection() {
        return yDirection;
    }

    public void setyDirection() {
        this.yDirection = yDirection;
    }

    //changes direction of X axis of the spaceship
    public void changeXDirection() {
        xDirection = xDirection * -1;
    }

    //changes direction of Y axis
    public void changeYDirection() {
        yDirection = yDirection * -1;
    }
}
