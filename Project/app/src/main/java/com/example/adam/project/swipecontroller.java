package com.example.adam.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Adam on 8/9/2015.
 */
public class swipeController extends controller {

    private Bitmap swipGrid; // make swipe gestures on
    public Context context;
    private GestureDetectorCompat gDetect;
    private Canvas canvas;

    public swipeController(Context context) {
        this.context = context;
        this.swipGrid =
                BitmapFactory.decodeResource(context.getResources(), R.drawable.touchcontroller);

        gDetect = new GestureDetectorCompat(context, new GestureListener());


    }

    //positionsetter from parent
    public void motionEvent(MotionEvent motionEvent){
        gDetect.onTouchEvent(motionEvent);
    }

    public void display(Canvas canvas) {
        this.handleMovement(spaceShip);
        touchedfire = false;
        this.canvas = canvas;
        Rect rectangle = new Rect();
        rectangle.set(0, (int)(canvas.getHeight() * .80), canvas.getWidth(), canvas.getHeight());
        super.maxwidthforship = canvas.getWidth();
        canvas.drawBitmap(swipGrid, null, rectangle, null);
    }

    //helper method that helps crate a gesture detector object
    class GestureListener extends GestureDetector.SimpleOnGestureListener {
        //class content
        //inner class calculations
        private float flingMin = 100;
        private float velocityMin = 100;

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            if (event.getY() >= (int) (.80 * canvas.getHeight()) && delaytimeforbullet == 0) {
                touchedfire = true;
                delaytimeforbullet = 2000;
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        //event1 is down
        //event2 is move
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            //determine fling events that occur here
            //user move forward through messages on fling up or left
            if (event1.getY() >= (int) (.80 * canvas.getHeight()) || event2.getY() >= (int) (.80 * canvas.getHeight())) {
                boolean left = false;
                //user will move backward through messages on movement down
                //or right
                boolean right = false;
                //calculate change in X position within fling gesture
                float horizontalDiff = event2.getX() - event1.getX();
                //calculate change in Y position within fling gesture
                float verticalDiff = event2.getY() - event1.getY();
                float absHDiff = Math.abs(horizontalDiff);
                float absVDiff = Math.abs(verticalDiff);
                float absVelocityX = Math.abs(velocityX);
                float absVelocityY = Math.abs(velocityY);

                if (absHDiff > absVDiff && absHDiff > flingMin && absVelocityX > velocityMin) {
                    //move forward or backward
                    if (horizontalDiff > 0)
                        right = true;
                    else
                        left = true;

                } else if (absVDiff > flingMin && absVelocityY > velocityMin) {
                    if (verticalDiff > 0) right = true;
                    else left = true;
                }
                //user is cycling through messages
                if (left) {
                    //check message that is not at the end of the array
                    //increase or set back to start
                    touchedleft = true;
                    touchedright = false;
                }
                //user is going backwards(up or right)through messages
                else if (right) {
                    touchedleft = false;
                    touchedright = true;

                }
                return true;


            }
            return true;
        }
    }


        public boolean onTouch(MotionEvent event) {
            this.motionEvent(event);

            if (this.touchedfire == true) {
                spaceShip.shoot(spaceShip.getX(), spaceShip.getY());
            }
            return true;
        }


    }
