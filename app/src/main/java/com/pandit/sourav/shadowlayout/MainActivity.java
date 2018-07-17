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
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private int resId = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);

        linearLayout.setPadding(25, 25, 25, 25);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setGravity(Gravity.CENTER);

        ShadowRectLayout rectLayout = new ShadowRectLayout(this);
        rectLayout.setClipToPadding(false);

//        rectLayout.setLayoutParams(new LinearLayout.LayoutParams((int) (getScreenWidth() / 1.3), (int) (getScreenWidth() / 1.3)));
        rectLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        rectLayout.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth(), ViewGroup  .LayoutParams.MATCH_PARENT));
        rectLayout.setShadowRadius(dpToPixel(5));
//        rectLayout.setShadowColor(Color.RED);
        rectLayout.setShadowColor(Color.GRAY);
        rectLayout.setOffSetX(0);
        rectLayout.setOffSetY(0);
        rectLayout.setRoundCornerRadius(65);
//        rectLayout.setResDrawable(R.drawable.metting_img);
        rectLayout.setImgGradientColor1(0xFF9F76C4);
//        rectLayout.setImgGradientColor2(0x0097CC52);
        linearLayout.addView(rectLayout);
        LinearLayout linearLayout1=new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
//        linearLayout1.setBackgroundColor(0xffb54545);
        linearLayout1.setGravity(Gravity.CENTER);
        linearLayout1.addView(getlinearLayout());
        linearLayout1.addView(getlinearLayout());
        linearLayout1.addView(getlinearLayout());
        linearLayout1.addView(getlinearLayout());


        rectLayout.addView(linearLayout1);


        setContentView(linearLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "THis is it", Toast.LENGTH_SHORT).show();
            }
        });

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
        textView.setPadding(dpToPixel(10), dpToPixel(5), dpToPixel(10), dpToPixel(5));
//        textView.setTextColor(Color.WHITE);
        textView.setText("Hello Android");
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(50);
        gradientDrawable.setColor(0x96FFFFFF);
//        textView.setBackgroundColor(0x77E27177);
        textView.setBackground(gradientDrawable);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        return textView;
    }

    public View getlinearLayout() {

        LinearLayout linearLayout1 = new LinearLayout(this);
//        linearLayout1.setGravity(Gravity.CENTER);
        linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setGravity(Gravity.CENTER);
        linearLayout1.setBackgroundColor(0x7995CFE7);


        linearLayout1.addView(getTextView());
        linearLayout1.addView(getTextView());
        linearLayout1.addView(getTextView());
        linearLayout1.addView(getTextView());
        linearLayout1.addView(getTextView());
        return linearLayout1;
    }
}