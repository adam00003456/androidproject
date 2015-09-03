package com.example.adam.project;

/**
 * Created by Adam on 12/20/2014.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;



import java.util.ArrayList;


public class View extends SurfaceView
        implements SurfaceHolder.Callback {

    private static final String TAG = View.class.getSimpleName();
    private MainGameThread thread;
    private Spaceship spaceship;
    private String controllertype = "";
    private controller hud;
    private int numberofenemies = 4;
    private ArrayList<Enemy> enemylist = new ArrayList<Enemy>();
    private int enemx = 10;
    private int enemy = 50;
    private boolean goingright = true;
    public Context context;
    private Activity Activityusedtocreate;
    private Bitmap backgroundimagebitmap;
    private WindowManager wm;
    private int widthofscreen = 0;
    private int heightofscreen = 0;
    private Vibrator v;



    public View(Context context){

        super(context);
        // add callback to surface holder to receive events from
        // the surface
        getHolder().addCallback(this);
        this.context = context;
        backgroundimagebitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.starrynightone);
        v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
        //create a game loop that will handle constant updating and drawing to screen
        thread = new MainGameThread(getHolder(), this);
        //view must be focusable so it can handle events that
        //occur on the surface
        setFocusable(true);


    }

    public void loadplayerandenemies(){
        //run a for loop to populate an arraylist with the amount of enemies you have
        for (int i = 0; i < numberofenemies; i++) {
            //hardcoded coords for simplicity
            Enemy eo = new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy), enemx, enemy, i);
            enemylist.add(eo);
            enemx += 400;
        }


        //create spaceship and load it's different stages bitmaps
        spaceship = new Spaceship(BitmapFactory.decodeResource(getResources(), R.drawable.galaga), 20,
                (int)(heightofscreen*.76));
        Bitmap spaceshipBitMapToAddOne = BitmapFactory.decodeResource(getResources(),
                R.drawable.galagahitone);
        spaceship.addToArrayList(spaceshipBitMapToAddOne);
        Bitmap spaceshipBitMapToAddTwo = BitmapFactory.decodeResource(getResources(),
                R.drawable.galagahittwo);
        spaceship.addToArrayList(spaceshipBitMapToAddTwo);




    }

    public void setWindowManager(WindowManager wm){
        this.wm = wm;
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        widthofscreen = metrics.widthPixels;
        heightofscreen = metrics.heightPixels;
    }

    public void setActivity(Activity activity) {
        Activityusedtocreate = activity;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,
    int width, int height){
    }

    public accelerometercontroller getteracceleratorHud() {
        return (accelerometercontroller)hud;
    }

    public voicecontroller gettergestureHud() {
        return (voicecontroller)hud;
    }

    public void setcontroller(){

        //set positions of the HUD images
        if(controllertype.equals("Buttons")) {
            hud = new buttoncontroller(context);
            hud.PositionSetter((int) (.15 * widthofscreen), (int) (.90 * heightofscreen), (int) (.85 * widthofscreen),
                    (int) (.90 * heightofscreen), (int) (.54 * widthofscreen), (int)(.90 * heightofscreen));
            hud.spaceshipobjectsetter(spaceship);

        }
        else if(controllertype.equals("Touch")){
            hud = new swipecontroller(context);
            hud.spaceshipobjectsetter(spaceship);
        }
        else if(controllertype.equals("Voice")){
            hud = new voicecontroller(Activityusedtocreate, context);
            hud.spaceshipobjectsetter(spaceship);
        }
        else if(controllertype.equals("Accelerometer")){
            hud = new accelerometercontroller(Activityusedtocreate);
            hud.spaceshipobjectsetter(this.spaceship);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is destroyed.");
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e){
                Log.d(TAG, "Thread was not able to join correctly.");
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }


    //consider moving this logic to respective controllers and just calling the onTouchEvent of
    //the object
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(controllertype.equals("Buttons")) {
            return ((buttoncontroller)hud).onTouch(event);
        }
        else if(controllertype.equals("Touch")){
            return ((swipecontroller)hud).onTouch(event);
        }

        else if(controllertype.equals("Voice")){
            ((voicecontroller)hud).onTouch();
            return true;
        }


        return true;

    }



    public void setControllerType(String controllertype) {
        this.controllertype = controllertype;
    }

    @Override
    protected void onDraw(Canvas canvas) {


        canvas.drawBitmap(backgroundimagebitmap, 0, 0, null);
        if(spaceship.getHitstaken()<3) {
            spaceship.draw(canvas);
            spaceship.updateBoundingBox();
            spaceship.testcollision(canvas);
        }else{
            Intent gameOverScreen = new Intent(this.getContext(), gameover.class);
            this.getContext().startActivity(gameOverScreen);
        }

        spaceship.drawExplosion(canvas);

        //all the enemies are destroyed so present the game over screen
        if (enemylist.isEmpty()) {
            Intent gameOverScreen = new Intent(this.getContext(), gameover.class);
            this.getContext().startActivity(gameOverScreen);
        }
        for (int i = 0; i < enemylist.size(); i++) {
            //hardcoded coords for simplicity
            Enemy eo = enemylist.get(i);
            //check for explosion for each enemy ship

            if (eo.drawExplosion(canvas) == true){
                eo.getBitmap().recycle();
                enemylist.remove(eo);
            }
            if (eo.isvisible == true) {
                eo.draw(canvas);
                eo.updateBoundingBox();
                eo.testcollision(canvas);
            }
            for (int itter = 0; itter < eo.bulletarraygetter().size(); itter++) {
                Bullet bullet = eo.bulletarraygetter().get(itter);
                if (bullet.isVisible() && eo.isvisible) {
                    Rect rectangle = new Rect();
                    rectangle.set(bullet.getX()-20, bullet.getY()-10, bullet.getX(), bullet.getY());
                    Paint yellow = new Paint();
                    yellow.setColor(Color.YELLOW);
                    yellow.setStyle(Paint.Style.FILL);
                    canvas.drawRect(rectangle, yellow);
                    //COLLISION OF PLAYERSHIP WITH BULLET HERE
                    spaceship.collision(rectangle, bullet);
                    if (spaceship.hit == true){
                        spaceship.ifCollisionIsTrue(this.context);
                        v.vibrate(500);
                    }
                    //spaceship(PLAYER)collides with bullet
                    //gameover occurs here
                    //if (spaceship.isvisible == false) {

                        //Intent gameOverScreen = new Intent(this.getContext(), gameover.class);
                        //this.getContext().startActivity(gameOverScreen);
                    //}
                }
            }
        }
        if(controllertype.equals("Buttons")) {

            ((buttoncontroller) hud).display(canvas);
        }
        else if(controllertype.equals("Touch")) {

            ((swipecontroller)hud).display(canvas);
        }
        else if(controllertype.equals("Voice")){
            ((voicecontroller)hud).display(canvas);
        }
        else if(controllertype.equals("Accelerometer")){

            ((accelerometercontroller)hud).display(canvas);
        }

        for (int i = 0; i < spaceship.bulletarraygetter().size(); i++) {
            Bullet bullet = spaceship.bulletarraygetter().get(i);
            if (bullet.isVisible()) {
                Rect rectangle = new Rect();
                rectangle.set(bullet.getX()-20, bullet.getY()-10, bullet.getX(), bullet.getY());
                Paint yellow = new Paint();
                yellow.setColor(Color.YELLOW);
                yellow.setStyle(Paint.Style.FILL);
                canvas.drawRect(rectangle, yellow);
                for (int itter = 0; itter < enemylist.size(); itter++) {
                    Enemy eo = enemylist.get(itter);
                    //ENEMYSHIP GETS HIT WITH BULLET HERE
                    if (eo.collision(rectangle, bullet) == true) {
                        //remove the destroyed enemyship from here
                        eo.ifCollisionIsTrue(this.context);


                    }
                }
            }
        }

    }


    public void update() {

        if (spaceship.hit == true){
            spaceship.afterIfCollisionIsTrue();
        }
        //call update method of each spaceship here
        //in update we will be be moving the spaceship based
        //off the barriers that surround it
        hud.updatebulletdelay();
        if(controllertype.equals("Voice")){
            ((voicecontroller)hud).update();
        }


        for (int enemyindex = 0; enemyindex <enemylist.size(); enemyindex++){
            Enemy currentenemy = enemylist.get(enemyindex);
            if (currentenemy.getX() <= currentenemy.getLeftMostBoundary()){
                goingright = true;
            }
            else if (currentenemy.getX() >=  currentenemy.getRightMostBoundary()){
                goingright = false;
            }

            if (goingright){
                currentenemy.setX(currentenemy.getX() + 5);
            } else {
                currentenemy.setX(currentenemy.getX() - 5);
            }
        }
        //call update method of each bullet here
        //in update we will be decreasing the y value of
        //the bullet
        //
        //as a result, the position of the image should
        //be updated before it is drawn
        //thus having the effect of traveling up the screen
        for (int i = 0; i < spaceship.bulletarraygetter().size(); i++) {
            Bullet bullet = spaceship.bulletarraygetter().get(i);
            if (bullet.isVisible()) {
                bullet.update();
            } else if (!bullet.isVisible()) {
                spaceship.bulletarraygetter().remove(i);
            }
        }
        for (int itter = 0; itter < enemylist.size(); itter++) {
            //update the alpha countdown for any exploding enemy ships
            if (enemylist.get(itter).isvisible == true){
                spaceship.afterIfCollisionIsTrue();
            }
            if (enemylist.get(itter).bulletarraygetter().size() == 0) {
                enemylist.get(itter).shoot(enemylist.get(itter).getX(), enemylist.get(itter).getY());
            } else {
                if (enemylist.get(itter).bulletarraygetter().get(0).isVisible()) {
                    enemylist.get(itter).bulletarraygetter().get(0).updateenemybullet(heightofscreen);
                } else if (!enemylist.get(itter).bulletarraygetter().get(0).isVisible()) {
                    enemylist.get(itter).bulletarraygetter().remove(0);
                }
            }
        }
    }

}
