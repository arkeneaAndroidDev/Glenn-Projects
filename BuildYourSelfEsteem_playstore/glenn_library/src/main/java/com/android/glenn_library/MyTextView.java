package com.android.glenn_library;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
          Typeface tf = Typeface.createFromAsset(context.getAssets(), "gothic_1.ttf");
           // Typeface tf = Typeface.createFromAsset(context.getAssets(), "MVBoli.ttf");
             setTypeface(tf);
        }
    }

}