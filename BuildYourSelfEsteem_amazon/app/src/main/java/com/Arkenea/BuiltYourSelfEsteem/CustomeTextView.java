
package com.Arkenea.BuiltYourSelfEsteem;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomeTextView extends TextView {

    public CustomeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CustomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomeTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "gothic_1.ttf");
            setTypeface(tf);
        }
    }

}
