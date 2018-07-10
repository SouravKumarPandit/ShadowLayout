package com.pandit.sourav.shadowlayout;

import android.content.res.Resources;
import android.graphics.Color;
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
//        rectLayout.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth()/2, getScreenWidth()/2));
        rectLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rectLayout.setShadowRadius(15);
//        rectLayout.setShadowColor(Color.RED);
        rectLayout.setShadowColor(Color.GRAY);
        rectLayout.setOffSetX(0);
        rectLayout.setOffSetY(0);
//        rectLayout.setShadowLeft(false);
//        rectLayout.setShadowRight(false);
//        rectLayout.setShadowBottom(false);
//        rectLayout.setShadowTop(false);
        rectLayout.setRoundCornerRadius(0);
        linearLayout.addView(rectLayout);


        LinearLayout linearLayout1 = new LinearLayout(this);
//        linearLayout1.setGravity(Gravity.CENTER);
        linearLayout1.setBackgroundColor(0x7995CFE7);

        TextView textView = new TextView(this);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        textView.setPadding(0,0,dpToPixel(15),0);
        textView.setText("Hello Android");
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setBackgroundColor(0x77E27177);
        linearLayout1.addView(textView);

//        TextView testView = new TextView(this);
//        testView.setTextColor(Color.DKGRAY);
//        testView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//        testView.setText("Hello Android");
//        testView.setLayoutParams(new LinearLayout.LayoutParams(200, 100));
//        testView.setBackgroundColor(0x77E27177);

        rectLayout.addView(linearLayout1);
//        rectLayout.addView(testView);


        setContentView(linearLayout);


//        setContentView(R.layout.activity_main);
//        this.seekBar = (AppCompatSeekBar) findViewById(R.id.seekbar);
//        this.seekBar2 = (AppCompatSeekBar) findViewById(R.id.seekbar2);
//        final ShadowRectLayout shadowRectLayout = findViewById(R.id.shadow3);
//        shadowRectLayout.setBaseBackgroundColor(Color.BLACK);
//        shadowRectLayout.setBaseBackgroundColor(getResources().getColor(R.color.sample_color));
//        shadowRectLayout.setRoundCornerRadius(30);
//        shadowRectLayout.setOffSetX(0);
//        shadowRectLayout.setOffSetY(6);
//            shadowRectLayout.setShadowColor(Color.GREEN);

/*

        RoundLinearLayout roundLayout = shadowRectLayout.getRoundLinearLayout();

        roundLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < 5; i++) {
            TextView textView = new TextView(this);
            textView.setText("HELLO");
//            textView.setBackgroundColor(Color.BLUE);
            textView.setPadding(15, 10, 15, 10);
//            textView.setBackgroundColor(getResources().getColor(R.color.child_color));
            textView.setTextColor(Color.BLACK);
            roundLayout.addView(textView);
        }
        RoundLinearLayout inerlayout = shadowRectLayout.getRoundLinearLayout();
        final View view = inerlayout.getChildAt(0);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                shadowRectLayout.setRoundCornerRadius(progress);
                view.setPadding(15, 0, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                shadowRectLayout.setRoundCornerRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
*/

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