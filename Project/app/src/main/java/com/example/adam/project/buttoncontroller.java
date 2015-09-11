package com.example.adam.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Adam on 8/6/2015.
 */
public class buttonController extends controller {
    public Bitmap bitmapleftarrow; // the actual bitmap
    public Bitmap bitmaprightarrow; // the actual bitmap
    public Bitmap bitmapfirearrow;
    public Context context;


    public buttonController(Context context) {

        this.bitmapleftarrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.leftarrow);
        this.bitmaprightarrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.rightarrow);
        this.bitmapfirearrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.firebutton);
        this.context = context;
    }

    //positionsetter from parent
    public void display(Canvas canvas) {
        this.handleMovement(spaceShip);
        Rect rectangle = new Rect();
        rectangle.set(0, (int)(.80 * canvas.getHeight()), canvas.getWidth(), canvas.getHeight());
        super.maxwidthforship = canvas.getWidth();
        Paint gray = new Paint();
        gray.setColor(Color.GRAY);
        gray.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectangle, gray);
        canvas.drawBitmap(bitmapleftarrow, super.posleftx - (bitmapleftarrow.getWidth() / 2), poslefty - (bitmapleftarrow.getHeight() / 2), null);
        canvas.drawBitmap(bitmaprightarrow, super.posrightx - (bitmaprightarrow.getWidth() / 2), posrighty - (bitmaprightarrow.getHeight() / 2), null);
        canvas.drawBitmap(bitmapfirearrow, super.posfirex - (bitmapfirearrow.getWidth() / 2), posfirey - (bitmapfirearrow.getHeight() / 2), null);
    }


    public boolean onTouch(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            this.handleActionDown((int) event.getX(), (int) event.getY(), this.bitmapleftarrow, 0);
            this.handleActionDown((int) event.getX(), (int) event.getY(), this.bitmaprightarrow, 1);
            this.handleActionDown((int) event.getX(), (int) event.getY(), this.bitmapfirearrow, 2);

            if (this.touchedfire == true && delaytimeforbullet == 0) {
                spaceShip.shoot(spaceShip.getX(), spaceShip.getY());
                delaytimeforbullet = 2000;
            }
        }
        return true;
    }

    public void handleActionDown(int eventX, int eventY, Bitmap image, int chooser) {
        int positionx = 0;
        int positiony = 0;
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
                    touchedright = false;
                    //rightarrow was touched here
                }else if (chooser == 1) {
                    touchedright = true;
                    touchedleft = false;
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
