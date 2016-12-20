package com.example.hp.navigation.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by eodwan on 16‏/12‏/2016.
 */
public class NativelyCustomTextViewfont extends TextView {

    public NativelyCustomTextViewfont(Context context) {
        super(context);
        setTypeface(font.getInstance(context).getTypeFace());
    }

    public NativelyCustomTextViewfont(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(font.getInstance(context).getTypeFace());
    }

    public NativelyCustomTextViewfont(Context context, AttributeSet attrs,
                                      int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(font.getInstance(context).getTypeFace());
    }

}