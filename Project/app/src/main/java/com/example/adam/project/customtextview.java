package com.example.adam.project;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Adam on 8/24/2015.
 */
public class customTextView extends TextView {

    public customTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/Sabo-Regular.otf"));
    }
}
