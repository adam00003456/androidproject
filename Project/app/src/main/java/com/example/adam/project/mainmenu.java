package com.example.adam.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.*;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class mainMenu extends Activity {

    private Spinner spinner;
    private Button btnStartGame;
    public static final String KEY_CONTROLLER="controller_type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Point displaySize = new Point();
        this.getWindowManager().getDefaultDisplay().getRealSize(displaySize);

        //use two rectangles (cut portions of the scrolling background image)
        //the y value for one rectangle increases (the bottom rectangle increases)
        //at the same time the y value for the rectange above it decreases, allowing the bottom
        //to increase
        //upon the bottom rectangle hitting the top rectangle, the bottom rectangle begins to grow
        //from the bottom again and the top rectange begins to decrease it's y value again
        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.starrynight);
        Canvas canvas = new Canvas();
        canvas.drawBitmap(bitmap, displaySize.x, displaySize.y,null);*/
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Buttons");
        list.add("Touch");
        list.add("Voice");
        list.add("Accelerometer");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        // item selection listener for spinner
        addListenerOnSpinnerItemSelection();

        //button press listener
        addListenerOnButton();


    }

    //Spinner Data is added here
    public void addListenerOnSpinnerItemSelection(){
            //used custom itemselection in object to keep clean code
            spinner.setOnItemSelectedListener(new customItemSelectedListener());
    }

    //get the selected spinner list value
    public void addListenerOnButton() {
        spinner = (Spinner) findViewById(R.id.spinner);
        btnStartGame = (Button) findViewById(R.id.button);
        btnStartGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mainMenu.this, "On Button Click : " +
                                "\n" + String.valueOf(spinner.getSelectedItem()),
                        Toast.LENGTH_LONG).show();
                buttonClick(String.valueOf(spinner.getSelectedItem()));

            }
        });
    }

    private void buttonClick(String identifierType) {
        //create an intent action
        Intent intent = new Intent("com.example.adam.project.mainActivity");
        switch (identifierType) {
            case "Buttons":
                //put key/value data
                intent.putExtra(KEY_CONTROLLER, "Buttons");
                //startActivity
                startActivity(intent);
                break;
            case "Touch":
                intent.putExtra(KEY_CONTROLLER, "Touch");
                startActivity(intent);
                break;
            case "Voice":
                intent.putExtra(KEY_CONTROLLER, "Voice");
                startActivity(intent);
                break;
            case "Accelerometer":
                intent.putExtra(KEY_CONTROLLER, "Accelerometer");
                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainmenu, menu);
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
}
