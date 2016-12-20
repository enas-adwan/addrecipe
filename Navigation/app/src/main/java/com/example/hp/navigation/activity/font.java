package com.example.hp.navigation.activity;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by eodwan on 16‏/12‏/2016.
 */
public class font {

    private static OpenSans instance;
    private static Typeface typeface;

    public static OpenSans getInstance(Context context) {
        synchronized (OpenSans.class) {
            if (instance == null) {
                instance = new OpenSans();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "font/aramisi.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}
