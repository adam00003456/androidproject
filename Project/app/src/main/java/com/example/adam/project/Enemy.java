package com.example.adam.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import com.example.adam.project.Bullet;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Adam on 12/22/2014.
 */
public class Enemy {

    //can implement limited bullets here to make the game harder
    //
    //for now the ship will simply make a bullet every time shoot is called and will fire that
    //bullet from the ship
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private Bitmap bitmap; // the actual bitmap
    private int x;  //the X coordinate
    private int y;  //the Y coordinate
    private boolean touched;// true if spaceship is touched
    public boolean isvisible;
    private Rect boundingbox;
    private int mostrightmovement;
    private int mostleftmovement;
    private int identifier;
    private Explosion mExplosion;
    private final static int NUM_PARTICLES = 25;
    private int currentAlpha = 255;


    public Enemy(Bitmap bitmap, int x, int y, int identifier) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        isvisible = true;
        boundingbox = new Rect(x, y, (x + bitmap.getWidth()), (y + bitmap.getHeight()));
        //CHANGE HARDCODED SPEEDS HERE
        mostrightmovement = x + 300;
        mostleftmovement =  x - 300;
        this.identifier = identifier;
    }

    //grab identifier for removal of the arraylist it was added into
    public int getIdentifier() {return identifier; }

    public void setIdentifier(int newIdentifier) {
        this.identifier = newIdentifier;
    }

    //grab x rightmostboundary of enemyship
    public int getRightMostBoundary(){
        return mostrightmovement;
    }

    //grab x leftmostboundary of enemyship
    public int getLeftMostBoundary(){
        return mostleftmovement;
    }

    //enemy collides with bullet
    public boolean collision(Rect rectofbullet) {
        if (rectofbullet.intersect(boundingbox)) {
            isvisible = false;
            return true;
        }

        return false;
    }

    public void updateBoundingBox(){
        boundingbox.set((this.getX()-(this.getBitmap().getWidth() / 2)),
                (this.getY()-(this.getBitmap().getHeight() / 2)),
                (this.getX()-(this.getBitmap().getWidth() / 2))+this.getBitmap().getWidth(),
                (this.getY()-(this.getBitmap().getHeight() / 2)+this.getBitmap().getHeight()));
    }

    public void shoot(int x, int y) {
        Bullet b = new Bullet(this.x + 50, this.y - 50);
        bullets.add(b);

    }

    public ArrayList<Bullet> bulletarraygetter() {
        return this.bullets;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }

    public void testcollision(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(boundingbox, paint);
    }

    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth() / 2))) {
            if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
                //spaceship was TOUCHED here
                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }
    }

    public void drawExplosion(Canvas canvas){
        if(mExplosion != null)
            mExplosion.update(canvas);
        if (mExplosion != null && mExplosion.isDead()) {
            currentAlpha = 255;
        }
    }
}

