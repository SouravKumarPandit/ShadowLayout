package com.pandit.sourav.shadowlayout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private int resId = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setPadding(25,25,25,25);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setGravity(Gravity.CENTER);

        ShadowRectLayout rectLayout = new ShadowRectLayout(this);
        rectLayout.setClipToPadding(false);
        rectLayout.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth()/2, getScreenWidth()/2));
//        rectLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rectLayout.setShadowRadius(dpToPixel(5));

//        rectLayout.setShadowColor(Color.RED);
        rectLayout.setShadowColor(Color.GRAY);
        rectLayout.setOffSetX(0);
        rectLayout.setOffSetY(0);
//        rectLayout.setShadowLeft(false);
//        rectLayout.setShadowRight(false);
//        rectLayout.setShadowBottom(false);
//        rectLayout.setShadowTop(false);
        rectLayout.setRoundCornerRadius(500);
        linearLayout.addView(rectLayout);


        LinearLayout linearLayout1 = new LinearLayout(this);
//        linearLayout1.setGravity(Gravity.CENTER);
        linearLayout1.setBackgroundColor(0x7995CFE7);

        TextView textView = new TextView(this);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setPadding(dpToPixel(10),dpToPixel(5),dpToPixel(10),dpToPixel(5));
//        textView.setTextColor(Color.WHITE);
        textView.setText("Hello Android");
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        GradientDrawable gradientDrawable=new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(50);
        gradientDrawable.setColor(0x96FFFFFF);
//        textView.setBackgroundColor(0x77E27177);
        textView.setBackground(gradientDrawable);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        linearLayout1.addView(textView);


        rectLayout.addView(linearLayout1);

        setContentView(linearLayout);

    }




    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }


}