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

/**
 * Created by Adam on 8/10/2015.
 */
public class accelerometerController extends controller implements SensorEventListener {

    public Activity activity;
    private SensorManager sensorManager;
    private Sensor senAccelerometer;
    private float last_x, last_y, last_z;


    public accelerometerController(Activity activity) {
        this.activity = activity;
        this.sensorManager = (SensorManager) this.activity.getSystemService(Context.SENSOR_SERVICE);
        this.senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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
        canvas.drawText("Accelerometer Mode", (canvas.getWidth() / 15), (int) (canvas.getHeight() * .90), white);
    }


    public void onPause() {
        sensorManager.unregisterListener(this);
    }


    public void onResume(){
        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }


    public void handleMovement(spaceShip spaceShip) {

        if (touchedleft == true && touchedright == false) {
            int tempx;
            tempx = spaceShip.getX();
            tempx -= 100;
            if (tempx >= 0)
                spaceShip.setX(tempx);
            touchedleft = false;
            touchedright = false;
        }
        else if (touchedright == true && touchedleft == false) {
            int tempx;
            tempx = spaceShip.getX();
            tempx += 100;
            if (tempx <= maxwidthforship)
                spaceShip.setX(tempx);
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
                    this.handleMovement(spaceShip);
                }
                else if (x > 0) {
                    touchedleft = true;
                    touchedright = false;
                    this.handleMovement(spaceShip);
                }
            } else {
                if (y > 4) {
                        if(delaytimeforbullet == 0) {
                            delaytimeforbullet = 2000;
                            spaceShip.shoot(spaceShip.getX(), spaceShip.getY());
                        }

                }
            }

            last_x = x;
            last_y = y;
            last_z = z;


        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        return;
    }
}
