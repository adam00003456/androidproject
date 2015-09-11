package com.example.adam.project;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.util.Log;

/**
 * Created by Adam on 12/20/2014.
 */

public class mainGameThread extends Thread{
    private boolean running;
    private static final String TAG = mainGameThread.class.getSimpleName();
    private SurfaceHolder surfaceHolder;
    private View gameview;
    public void setRunning(boolean running) {
        this.running = running;
    }

    public mainGameThread(SurfaceHolder surfaceHolder,
                          View gameview)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameview= gameview;
    }

    @Override
    public void run() {
        Canvas canvas;
        Log.d(TAG, "Start of the game loop");
        //used for debugging purposes
        //long tickCount = 0L;
        Log.d(TAG, "Starting of the game loop");
        while (running) {
            //used for debugging purposes
            //tickCount++;
            canvas = null;
            //LOCK CANVAS HERE
            try {
                canvas = this.surfaceHolder.lockCanvas();

                synchronized (surfaceHolder) {
                    //update the game state
                    this.gameview.update();
                    //draw the canvas onto the view or panel
                    //if case used here  because canvas returns null after starting new activity thus there is nothing to
                    //"lock" down and thus the thread does not need to do any more work
                    if (canvas != null) {
                        this.gameview.onDraw(canvas);
                    }else {
                        break;
                    }
                }
            } finally {
                if (canvas != null)
                {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
        //used for debugging purposes
        //Log.d(TAG, "Game loop is executed " + tickCount + "times");
    }

}
