package com.example.adam.project;

import com.example.adam.project.Spaceship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Adam on 12/20/2014.
 */
public class controller {
    protected boolean touchedleft = false;// true if leftarrow is touched
    protected boolean touchedright = false;// true if rightarrow is touched
    protected boolean touchedfire = false;// true if firebutton is touched
    protected int posleftx;
    protected int poslefty;
    protected int posrightx;
    protected int posrighty;
    protected int posfirex;
    protected int posfirey;
    protected int maxwidthforship;
    protected Spaceship spaceship;
    protected int delaytimeforbullet = 0;


    public void PositionSetter(int posleftX, int posleftY, int posrightX, int posrightY, int posfireX, int posfireY) {
        this.posleftx = posleftX;
        this.poslefty = posleftY;
        this.posrightx = posrightX;
        this.posrighty = posrightY;
        this.posfirex = posfireX;
        this.posfirey = posfireY;
    }

    public void handleMovement(Spaceship spaceship) {

        if (touchedleft == true && touchedright == false) {
            int tempx;
            tempx = spaceship.getX();
            tempx -= 30;
            if (tempx >= 0)
                spaceship.setX(tempx);
            touchedleft = false;
            touchedright = false;
        }
        else if (touchedright == true && touchedleft == false) {
            int tempx;
            tempx = spaceship.getX();
            tempx += 30;
            if (tempx <= maxwidthforship)
                spaceship.setX(tempx);
            touchedright = false;
            touchedleft = false;
        }

    }

    public void updatebulletdelay(){
        if (delaytimeforbullet > 0){
            delaytimeforbullet = delaytimeforbullet - 20;
        }
    }

    public void spaceshipobjectsetter(Spaceship spaceship) {

        this.spaceship = spaceship;
    }

    public void handleActionDown(int eventX, int eventY, Bitmap image, int chooser) {
        int positionx = 0;
        int positiony = 0;
        boolean  buttonpresser = false;
        if (chooser == 0) {
            positionx = posleftx;
            positiony = poslefty;
        } else if (chooser == 1) {
            positionx = posrightx;
            positiony = posrighty;
        } else if (chooser == 2) {
            positionx = posfirex;
            positiony = posfirey;
        }

        if (eventX >= (positionx - image.getWidth() / 2)&&(eventX <= (positionx + image.getWidth()/2))) {
            if (eventY >= (positiony - image.getHeight() / 2) && (positiony <= (positiony + image.getHeight() / 2))) {
                //leftarrow was touched here
                if (chooser == 0) {
                    touchedleft = true;
                //rightarrow was touched here
                }else if (chooser == 1) {
                    touchedright = true;
                //firebutton was touched here
                } else if (chooser == 2) {
                    touchedfire = true;
                }
            }
        //else if everything remains the same it should be false

        } else {
            if (chooser == 0) {
                touchedleft = false;
            }else if (chooser == 1){
                touchedright = false;
            } else if (chooser == 2) {
                touchedfire = false;
            }
        }
    }
}
