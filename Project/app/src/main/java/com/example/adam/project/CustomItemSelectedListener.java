package com.example.adam.project;

import android.view.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by Adam on 7/20/2015.
 */
public class CustomItemSelectedListener implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Toast.makeText(parent.getContext(), "On Item Select: \n" +
                parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
