package com.android.glenn_library;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by Admin on 19-11-2016.
 */
public class BaseActivity extends Activity{
    public int height;
    public int width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        height = dm.heightPixels;
        width = dm.widthPixels;
    }
}
