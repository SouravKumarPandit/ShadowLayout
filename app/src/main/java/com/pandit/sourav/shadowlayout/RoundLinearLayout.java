package com.pandit.sourav.shadowlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

@SuppressLint("AppCompatCustomView")
public class RoundLinearLayout extends LinearLayout {

    //    private Paint paint2;
//    private Paint paint;

    public RoundLinearLayout(Context context) {
        this(context, null);
    }

    public RoundLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
//        paint = new Paint();
//        paint.setColor(Color.WHITE);
//        paint.setAntiAlias(true);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//
//        paint2 = new Paint();
//        paint2.setXfermode(null);
    }

    public void setRound(int shadowRound) {
        int padLeft = 0, padTop = 0, padRight = 0, padBottom = 0;
//        int padLeft = (int) dpToPixel(2), padTop = (int) dpToPixel(2), padRight = (int) dpToPixel(2), padBottom = (int) dpToPixel(2);


        if (RoundLinearLayout.this.getWidth() > RoundLinearLayout.this.getHeight()) {
            if (shadowRound < getWidth() / 10) {
                padLeft = shadowRound;
                padRight = shadowRound;
                setPadding(padLeft, padTop, padRight, padBottom);

            } else {
                int sidePadding = getHeight() / 3;
                setPadding(sidePadding, padTop, sidePadding, padBottom);
            }

        } else if (RoundLinearLayout.this.getWidth() < RoundLinearLayout.this.getHeight()) {
            if (shadowRound < getHeight() / 8) {
                padTop = shadowRound;
                padBottom = shadowRound;
                setPadding(padLeft, padTop, padRight, padBottom);
            } else {
                int sidePadding = getWidth() / 3;
                setPadding(padLeft, sidePadding, padRight, sidePadding);
            }

        } else {
            if (shadowRound < getWidth() / 3 || shadowRound < getHeight() / 3) {
                padLeft = shadowRound / 2;
                padRight = shadowRound / 2;
                padTop = shadowRound / 2;
                padBottom = shadowRound / 2;
                setPadding(padLeft, padTop, padRight, padBottom);
            }

        }
        invalidate();
    }

    public static float dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }


}