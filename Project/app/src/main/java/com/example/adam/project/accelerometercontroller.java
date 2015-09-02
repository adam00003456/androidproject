package com.example.adam.project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;

/**
 * Created by Adam on 8/10/2015.
 */
public class accelerometercontroller extends controller implements SensorEventListener {

    public Activity activity;
    private SensorManager sensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private int secs = 0;




    public accelerometercontroller(Activity activity) {
        this.activity = activity;
        this.sensorManager = (SensorManager) this.activity.getSystemService(Context.SENSOR_SERVICE);
        this.senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void sensorevent(SensorEvent sensorEvent){
        this.onSensorChanged(sensorEvent);
    }

    public void spaceshipsetter(Spaceship spaceship) {
        this.spaceship = spaceship;
    }



    public void display(Canvas canvas) {

        Rect rectangle = new Rect();
        rectangle.set(0, (int)(canvas.getHeight() * .80), canvas.getWidth(), canvas.getHeight());
        super.maxwidthforship = canvas.getWidth();
        Paint grey = new Paint();
        grey.setColor(Color.GRAY);
        grey.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectangle, grey);
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        white.setTextSize(100);
        canvas.drawText("Accelerometer Mode", (canvas.getWidth() / 15), (int)(canvas.getHeight() * .90), white);


    }


    public void onPause() {
        sensorManager.unregisterListener(this);
    }


    public void onResume(){
        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void handleMovement(Spaceship spaceship) {

        if (touchedleft == true && touchedright == false) {
            int tempx;
            tempx = spaceship.getX();
            tempx -= 100;
            if (tempx >= 0)
                spaceship.setX(tempx);
            touchedleft = false;
            touchedright = false;
        }
        else if (touchedright == true && touchedleft == false) {
            int tempx;
            tempx = spaceship.getX();
            tempx += 100;
            if (tempx <= maxwidthforship)
                spaceship.setX(tempx);
            touchedright = false;
            touchedleft = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            if (Math.abs(x) > Math.abs(y)) {
                if(x < 0) {
                    touchedright=true;
                    touchedleft = false;
                    this.handleMovement(spaceship);
                }
                else if (x > 0) {
                    touchedleft = true;
                    touchedright = false;
                    this.handleMovement(spaceship);
                }
            } else {
                if (y > 8) {
                        if(delaytimeforbullet == 0) {
                            delaytimeforbullet = 2000;
                            spaceship.shoot(spaceship.getX(), spaceship.getY());
                        }

                }
            }

            last_x = x;
            last_y = y;
            last_z = z;


        }

    }

    public void delay(long Time) {
        while (secs != 50){
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int secs = (int) (timeInMilliseconds / 1000);
            if (secs == 50) {
                touchedfire = false;
                break;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
