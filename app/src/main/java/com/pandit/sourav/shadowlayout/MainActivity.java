package com.pandit.sourav.shadowlayout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);

        linearLayout.setPadding(25, 25, 25, 25);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);



        linearLayout.addView(getShadowRectLayout());

        setContentView(linearLayout);


    }
/*
    private CardView getFancyTextView() {
        CardView cardView=new CardView(this);
        cardView.setRadius(25);
        cardView.setPadding(0,0,0,0);
        cardView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        cardView.setCardBackgroundColor(0xfffafafb);
        TextView textView=new TextView(this);
        textView.setPadding(20,5,20,5);
        textView.setBackground(null);

        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setText("Sourav Pandit");
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        cardView.addView(textView);
        return cardView;
    }private CardView getFancySmallTextView() {
        CardView cardView=new CardView(this);
        cardView.setRadius(25);
        cardView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        cardView.setPadding(0,0,0,0);
        cardView.setCardBackgroundColor(0xfffafafb);

        TextView textView=new TextView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        textView.setBackground(null);
        textView.setPadding(20,0,20,0);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setText("Focus Softnet");
        cardView.addView(textView);
        return cardView;
    }*/

    private View getShadowAngleLayout() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        return linearLayout;

    }



    private View getShadowRectLayout() {
        ShadowRectLayout rectLayout = new ShadowRectLayout(this);

        rectLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rectLayout.setShadowRadius(10);
        rectLayout.setOffSetX(0);
        rectLayout.setOffSetY(5);
        rectLayout.setRoundCornerRadius(5);
        rectLayout.setShadowColor(0xff00ff00);
//        rectLayout.setResDrawable(R.drawable.metting_img);
//        rectLayout.setImgGradientColor1(0x5b9F76C4);
//        rectLayout.setImgGradientColor2(0xC1AE4F72);
        LinearLayout linearLayout1 = new LinearLayout(this);
        LinearLayout.MarginLayoutParams linearLayout1Param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        linearLayout1Param.setMargins(50,50,50,50);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
//        linearLayout1.setPadding(0,60,0,0);
//        linearLayout1.setBackgroundColor(0x62DEC87A);
        linearLayout1.setGravity(Gravity.CENTER);
        linearLayout1.addView(getlinearLayout());
        linearLayout1.addView(getlinearLayout());
        linearLayout1.addView(getlinearLayout());
        linearLayout1.addView(getlinearLayout());
        linearLayout1.addView(getlinearLayout());
        linearLayout1.addView(getlinearLayout());

        rectLayout.addView(linearLayout1);
        return rectLayout;
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


    public View getTextView() {
        TextView textView = new TextView(this);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//        textView.setPadding(dpToPixel(10), dpToPixel(5), dpToPixel(10), dpToPixel(5));
//        textView.setTextColor(Color.WHITE);
        textView.setText("Hello Android");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setGravity(Gravity.CENTER);
//        layoutParams.setMargins(10,0,10,15);
        textView.setLayoutParams(layoutParams );
//        textView.setBackground(getResources().getDrawable(R.drawable.shadow_text_pad));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(50);
        gradientDrawable.setColor(0x96bedeFF);
//        textView.setBackgroundColor(0x77E27177);
//        textView.setBackground(gradientDrawable);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        return textView;
    }

    public View getlinearLayout() {

        LinearLayout linearLayout1 = new LinearLayout(this);
//        linearLayout1.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams linearLayout1Param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        linearLayout1.setLayoutParams(linearLayout1Param);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
        linearLayout1.setGravity(Gravity.CENTER);
//        linearLayout1.setBackgroundColor(0x7995CFE7);


//        linearLayout1.addView(getTextView());
//        linearLayout1.addView(getTextView());
//        linearLayout1.addView(getTextView());
//        linearLayout1.addView(getTextView());
        linearLayout1.addView(getTextView());
        linearLayout1.addView(getTextView());
        linearLayout1.addView(getTextView());
//        linearLayout1.addView(getTextView());
        return linearLayout1;
    }
}