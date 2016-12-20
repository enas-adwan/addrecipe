package com.example.hp.navigation.activity;
import android.util.AttributeSet;
import android.widget.TextView;
import  android.content.Context;
/**
 * Created by eodwan on 16‏/12‏/2016.
 */
public class NativelyCustomTextView extends TextView {

    public NativelyCustomTextView(Context context) {
        super(context);
        setTypeface(OpenSans.getInstance(context).getTypeFace());
    }

    public NativelyCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(OpenSans.getInstance(context).getTypeFace());
    }

    public NativelyCustomTextView(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(OpenSans.getInstance(context).getTypeFace());
    }

}