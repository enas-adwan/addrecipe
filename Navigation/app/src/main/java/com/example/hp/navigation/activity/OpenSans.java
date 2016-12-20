package com.example.hp.navigation.activity;
import android.graphics.Typeface;
import android.content.Context;
/**
 * Created by eodwan on 16‏/12‏/2016.
 */
public class OpenSans {

    private static OpenSans instance;
    private static Typeface typeface;

    public static OpenSans getInstance(Context context) {
        synchronized (OpenSans.class) {
            if (instance == null) {
                instance = new OpenSans();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "font/Boogaloo-Regular.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}