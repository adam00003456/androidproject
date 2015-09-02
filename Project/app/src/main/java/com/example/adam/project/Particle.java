package com.example.adam.project;

/**
 * Created by Adam on 8/29/2015.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Particle {

    public static final int ALIVE = 0;
    public static final int DEAD = 1;
    private int mState;
    private Bitmap mBitmap;
    private float mX, mY;
    private double mXV, mYV;
    private float mAge;
    private float mLifetime;
    private Paint mPaint;
    private int mAlpha;
    private static Bitmap mBase;

    private double randomDouble(double min, double max){
        return min + (max - min) * Math.random();
    }

    public boolean isAlive() {
        return mState == ALIVE;
    }

    public Particle(int x, int y, int lifetime,
                    int maxSpeed, int maxScale,
                    Context c) {
        mX = x;
        mY = y;
        mState = ALIVE;
        if (mBase == null){
            mBase = BitmapFactory.decodeResource(c.
                    getResources(), R.drawable.fire);
        }
        int newWidth = (int) (mBase.getWidth()*
                randomDouble(1.01, maxScale));
        int newHeight = (int) (mBase.getHeight()*
        randomDouble(1.01, maxScale));
        mBitmap = Bitmap.createScaledBitmap(mBase,
                newWidth, newHeight, true);
        mLifetime = lifetime;
        mAge = 0;
        mAlpha = 0xff;
        //offers more options to choose from random while limiting the speed with maxSpeed so the
        //particle does not fly off the screen
        mXV = (randomDouble(0, maxSpeed * 2) - maxSpeed);
        mYV = (randomDouble(0, maxSpeed * 2) - maxSpeed);
        mPaint = new Paint();
        //in extreme levels of high value randomness
        //for example if both velocities are at 50, they must be scaled down or they will go too fast
        if (mXV * mXV + mYV * mYV > maxSpeed * maxSpeed) {
            mXV *= 0.7;
            mYV *= 0.7;
        }
    }

    public void update() {
        if (mState != DEAD) {
            mX += mXV;
            mY += mYV;
            if (mAlpha <= 0) {
                mState = DEAD;
            }else{
                mAge++;
                float factor = (mAge/mLifetime) * 2;
                //this is done to achieve a fading effect
                mAlpha = (int) (0xff - (0xff * factor));
                mPaint.setAlpha(mAlpha);
            }
            if(mAge >= mLifetime) {
                mState = DEAD;

            }
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mX, mY, mPaint);
    }

}
