package com.example.adam.project;

import android.content.Context;
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
    private boolean touched;// true if spaceShip is touched
    public boolean isvisible;
    private Rect boundingbox;
    private int mostrightmovement;
    private int mostleftmovement;
    private Explosion mExplosion;
    private final static int NUM_PARTICLES = 25;
    private int currentAlpha = 255;
    private Paint alphaPaint = new Paint();


    public Enemy(Bitmap bitmap, int x, int y, int identifier) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        isvisible = true;
        boundingbox = new Rect(x, y, (x + bitmap.getWidth()), (y + bitmap.getHeight()));
        //CHANGE HARDCODED SPEEDS HERE
        mostrightmovement = x + 300;
        mostleftmovement =  x - 300;
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
    public boolean collision(Rect rectofbullet, Bullet bullet) {
        if (rectofbullet.intersect(boundingbox)) {
            isvisible = false;
            bullet.setVisibility();
            return true;
        }


        return false;
    }

    public void updateBoundingBox(){
        boundingbox.set((this.getX() - (this.getBitmap().getWidth() / 2)),
                (this.getY() - (this.getBitmap().getHeight() / 2)),
                (this.getX() - (this.getBitmap().getWidth() / 2)) + this.getBitmap().getWidth(),
                (this.getY() - (this.getBitmap().getHeight() / 2) + this.getBitmap().getHeight()));
    }

    //coordinates for the bullet
    public void shoot(int x, int y, int speedofbullet) {
        Bullet b = new Bullet(x - (bitmap.getWidth() / 2) + (this.bitmap.getWidth()/2),
                y - (bitmap.getHeight() / 2) + (this.bitmap.getHeight()), speedofbullet);
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

    public boolean drawExplosion(Canvas canvas){
        if(mExplosion != null)
            mExplosion.update(canvas);
        if (mExplosion != null && mExplosion.isDead()) {
            currentAlpha = 255;
            mExplosion = null;
            return true;
        }
        return false;
    }

    public void afterIfCollisionIsTrue(){
        if(currentAlpha>0){
            currentAlpha=currentAlpha-30;
            alphaPaint.setAlpha(currentAlpha);
        }
    }

    public void ifCollisionIsTrue(Context context){
        if (mExplosion==null || mExplosion.isDead()) {
            mExplosion = new Explosion(NUM_PARTICLES, x-(this.getBitmap().getWidth() / 2),
                    y-(this.getBitmap().getHeight() / 2), context);

        }
    }

}

