package com.pandit.sourav.shadowlayout.temp;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pandit.sourav.shadowlayout.R;

public class WorkOnitActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean childVisible1 = false;
    private boolean childVisible2 = false;
    private boolean firstClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootView());
    }


    private FrameLayout getRootView() {
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout linearLayoutmain = new LinearLayout(this);
        linearLayoutmain.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.END;
        linearLayoutmain.setLayoutParams(layoutParams);
        linearLayoutmain.setGravity(Gravity.END | Gravity.CENTER);

        LinearLayout linearLayout1 = new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        layoutParams.gravity = Gravity.END;
        linearLayout1.setLayoutParams(layoutParams);
        linearLayout1.setGravity(Gravity.END | Gravity.CENTER);


        linearLayout1.addView(getRadioGroupView(R.id.radio_group2));
        LinearLayout linearLayout = new LinearLayout(this);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.LTGRAY);
        drawable.setCornerRadius(40);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        layoutParams.gravity = Gravity.END;
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setGravity(Gravity.END | Gravity.CENTER);
        linearLayout.addView(getRadioGroupView(R.id.radio_group));


        final FloatingActionButton fab = new FloatingActionButton(this);
        fab.setId(R.id.fab);

        fab.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        fab.setSize(FloatingActionButton.SIZE_MINI);
//        fab.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{Color.GREEN}));
        fab.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.star_on));

        fab.setOnClickListener(this);


        final FloatingActionButton fab2 = new FloatingActionButton(this);
        fab2.setId(R.id.fab2);

        fab2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        fab2.setSize(FloatingActionButton.SIZE_MINI);
//        fab.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{Color.GREEN}));
        fab2.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.star_on));
        fab2.setOnClickListener(this);


        linearLayout.addView(fab);
        linearLayout1.addView(fab2);
        linearLayoutmain.addView(linearLayout);
        linearLayoutmain.addView(linearLayout1);
        frameLayout.addView(linearLayoutmain);
        return frameLayout;
    }

    private RadioGroup getRadioGroupView(int idGroup) {
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setId(idGroup);
        radioGroup.setAlpha(0);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.LTGRAY);
        drawable.setCornerRadius(40);
        radioGroup.setBackground(drawable);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setPadding(10, 2, 40, 2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, -40, 0);
        radioGroup.setLayoutParams(params);

        RadioButton radioButton = new RadioButton(this);
        radioButton.setPadding(0, 0, 20, 0);
        radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        radioButton.setText("Default Root");
        radioGroup.addView(radioButton);

        RadioButton radioButton1 = new RadioButton(this);
        radioButton1.setPadding(0, 0, 20, 0);
        radioButton1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        radioButton1.setText("Custom Root");
        radioGroup.addView(radioButton1);

        return radioGroup;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
            case R.id.fab2:
                renderFloatingView(view.getId());
                break;

        }
    }

    private void renderFloatingView(int inID) {
        if (!firstClick) {
            if (inID == R.id.fab) {
                renderViews(true, false);
            } else if (inID == R.id.fab2) {
                renderViews(false, true);

            }
            firstClick = true;
            return;
        }

        if (childVisible1 && childVisible2/*||!firstClick*/) {
            if (inID == R.id.fab) {
                renderViews(true, false);
            } else if (inID == R.id.fab2) {
                renderViews(false, true);

            }
        } else if (childVisible1&&!childVisible2) {
            if (inID == R.id.fab) {
                renderViews(false, false);
            } else if (inID == R.id.fab2) {
                renderViews(false, true);

            }
//            renderViews(true, false);
        } else if (!childVisible1&&childVisible2) {
            if (inID == R.id.fab) {
                renderViews(true, false);
            } else if (inID == R.id.fab2) {
                renderViews(false, false);

            }
//            renderViews(false, true);
        }else if (!childVisible1&&!childVisible2){
            if (inID == R.id.fab) {
                renderViews(true,false);
            } else if (inID == R.id.fab2) {
                renderViews(false, true);

            }

        }

    }

    private void renderViews(boolean childVisible1, boolean childVisible2) {

//if (this.childVisible1==childVisible1){}
        RadioGroup radioGroup = findViewById(R.id.radio_group);

        if (!childVisible1) {
            radioGroup.setTranslationX(radioGroup.getWidth());
            radioGroup.setAlpha(1);
            radioGroup.animate().translationX(0);
        } else {
            radioGroup.animate().translationX(radioGroup.getWidth());
            radioGroup.animate().setStartDelay(100).alpha(0);
        }
        this.childVisible1 = !childVisible1;


        RadioGroup radioGroup2 = findViewById(R.id.radio_group2);
        if (!childVisible2) {
            radioGroup2.setTranslationX(radioGroup2.getWidth());
            radioGroup2.setAlpha(1);
            radioGroup2.animate().translationX(0);
        } else {
            radioGroup2.animate().translationX(radioGroup2.getWidth());
            radioGroup2.animate().setStartDelay(100).alpha(0);
        }
        this.childVisible2 = !childVisible2;
    }
}
