package com.pandit.sourav.shadowlayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private AppCompatSeekBar seekBar;
    private AppCompatSeekBar seekBar2;
    private int resId = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.seekBar = (AppCompatSeekBar) findViewById(R.id.seekbar);
        this.seekBar2 = (AppCompatSeekBar) findViewById(R.id.seekbar2);
        final ShadowRectLayout shadowRectLayout = findViewById(R.id.shadow3);
//        shadowRectLayout.setBaseBackgroundColor(Color.BLACK);
//        shadowRectLayout.setBaseBackgroundColor(getResources().getColor(R.color.sample_color));
//        shadowRectLayout.setShadowRoundRadius(30);
//        shadowRectLayout.setOffSetX(0);
//        shadowRectLayout.setOffSetY(6);
//            shadowRectLayout.setShadowColor(Color.GREEN);


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
//                shadowRectLayout.setShadowRoundRadius(progress);
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

                shadowRectLayout.setShadowRoundRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

}