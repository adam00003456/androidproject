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
public class buttoncontroller extends controller {
    public Bitmap bitmapleftarrow; // the actual bitmap
    public Bitmap bitmaprightarrow; // the actual bitmap
    public Bitmap bitmapfirearrow;
    public Context context;


    public buttoncontroller(Context context) {

        this.bitmapleftarrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.leftarrow);
        this.bitmaprightarrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.rightarrow);
        this.bitmapfirearrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.firebutton);
        this.context = context;
    }

    //positionsetter from parent
    public void display(Canvas canvas) {
        Rect rectangle = new Rect();
        rectangle.set(0, (int)(.80 * canvas.getHeight()), canvas.getWidth(), canvas.getHeight());
        super.maxwidthforship = canvas.getWidth();
        //canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
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
            this.handleMovement(spaceship);
            if (this.touchedfire == true && delaytimeforbullet == 0) {
                spaceship.shoot(spaceship.getX(), spaceship.getY());
                delaytimeforbullet = 2000;
            }
        }
        return true;
    }



}
