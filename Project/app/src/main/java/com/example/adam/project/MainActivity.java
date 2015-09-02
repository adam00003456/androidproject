package com.example.adam.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.util.Log;
import android.widget.ToggleButton;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String controllertype;
    /** The current direction that the user is being
     *  prompted to gesture towards.*/
    private Path.Direction mCurrentDirection;
    public View mView;
    private ToggleButton toggleButton;
    private WindowManager wm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen it


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mView = new View(MainActivity.this);
        mView.setActivity(MainActivity.this);
        //get passed intent from mainmenu
        Intent intent = getIntent();
        //get controller type from intent
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Log.d(TAG, "View was added successfully.");
        //setContentView(new View(this));
        mView.setWindowManager(wm);
        mView.loadplayerandenemies();
        controllertype = intent.getStringExtra("controller_type");
        mView.setControllerType(controllertype);
        mView.setcontroller();
        //set NEW CONTENT VIEW HERE
        setContentView(mView);
        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.themesong);
        mediaPlayer.start();

    }

    @Override
    public void onPause() {
        super.onPause();

        if(controllertype.equals("Accelerometer")) {
            mView.getteracceleratorHud().onPause();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (controllertype.equals("Accelerometer")) {
            mView.getteracceleratorHud().onResume();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }
}
