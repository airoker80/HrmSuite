package com.harati.hrmsuite.Helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by User on 8/15/2017.
 */

public class CustomTextViewRegular extends android.support.v7.widget.AppCompatTextView {


    public CustomTextViewRegular(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf");
        this.setTypeface(face);
    }

    public CustomTextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf");
        this.setTypeface(face);
    }

    public CustomTextViewRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}